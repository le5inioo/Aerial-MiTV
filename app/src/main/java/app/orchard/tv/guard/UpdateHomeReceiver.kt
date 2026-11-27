package app.orchard.tv.guard

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.PowerManager
import android.os.SystemClock
import android.provider.Settings
import app.orchard.tv.MainActivity
import app.orchard.tv.system.DiagLog
import kotlin.concurrent.thread

/**
 * Replacing the APK force-stops Aerial and its Home activity goes away. On MiTV-MFTR0 the
 * firmware then leaves the video player's diagnose page in front, and the guard deliberately does
 * nothing while Aerial is the resolved HOME app, so the TV looks stuck outside the launcher.
 *
 * MY_PACKAGE_REPLACED is delivered only to this package and survives the force-stop, so it is the
 * reliable moment to put Home back. Only when Home was in front when Aerial died: a viewer inside
 * another app must stay there (the same rule BootReceiver and the update path use).
 */
class UpdateHomeReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action != Intent.ACTION_MY_PACKAGE_REPLACED) return
        val app = context.applicationContext
        val pending = goAsync()
        thread(name = "AerialReplaceHome") {
            try {
                // Let the firmware finish putting its own page up before covering it.
                SystemClock.sleep(SETTLE_MS)
                if ((app.getSystemService(Context.POWER_SERVICE) as? PowerManager)?.isInteractive != true) {
                    DiagLog.i("replaced: screen off; leaving Home alone")
                    return@thread
                }
                if (!GuardSetup.isUserUnlocked(app)) {
                    DiagLog.i("replaced: user locked; leaving Home alone")
                    return@thread
                }
                if (runCatching { Settings.Global.getString(app.contentResolver, "tv_power_mode") }.getOrNull() == "QUICK") {
                    DiagLog.i("replaced: quick standby; leaving Home alone")
                    return@thread
                }
                if (!GuardSetup.wasHomeInFront(app)) {
                    DiagLog.i("replaced: Home was not in front; leaving the viewer's app alone")
                    return@thread
                }
                DiagLog.i("replaced: Home was in front; bringing Aerial back")
                runCatching { app.startActivity(MainActivity.bringBackIntent(app)) }
                    .onFailure { DiagLog.i("replaced: could not bring Home back: ${it.message}") }
            } finally {
                pending.finish()
            }
        }
    }

    private companion object {
        const val SETTLE_MS = 2_000L
    }
}
