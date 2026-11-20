# Aerial-MiTV

Launcher for the Xiaomi TV S65 Mini LED (MiTV-MFTR0), forked from [Aerial](https://github.com/pengchujin/aerial-tv). [Download APK](https://github.com/le5inioo/Aerial-MiTV/releases/latest) · [中文](README.md)

## The TV

| Item | Value |
|---|---|
| Brand / model | Xiaomi MiTV-MFTR0 |
| Retail name | Xiaomi TV S65 Mini LED (L65MA-SPL) |
| CPU | mt5879 · armeabi-v7a · 4 cores |
| RAM | 3.7 GB usable (4 GB nominal) |
| OS | Android 11 (SDK 30) |
| Firmware | HyperOS 2.0.5.0 |

Apps only ever see 1080p (1920×1080 / 60 Hz); the panel's real resolution is not readable, so do not plan for nominal 4K.

## What this fork changes

| Change | Where | Why |
|---|---|---|
| Package renamed to `com.ts.launcher` | `app/build.gradle.kts` | The firmware only hands HOME to whitelisted packages; `com.ts.launcher` is on the list, `app.orchard.tv` is not |
| PatchWall disabled | `tools/install-ts.sh` (`DISABLE_STOCK_HOME=1`) | Otherwise HOME resolves to a chooser and the stock launcher flashes on a Home press |
| Home guard | `guard/HomeGuardService.kt` | Switches back when the stock launcher comes to the front; revived by a notification listener after standby; boot/wake/revival all wait 1.5 s before taking over |
| Conditional key filtering | `guard/HomeGuardService.filterKeys` | Intercepts HOME while Aerial is in front to avoid the flash; not in other apps |
| Developer mode and ADB kept on | `guard/GuardSetup.kt` | Writes back `development_settings_enabled` / `adb_enabled` on boot and watches them with a `ContentObserver`; standby resets ADB |
| 10-minute PIN grace | `ui/home/PinPad.kt` | One correct PIN covers the next 10 minutes |

## Features

| Feature | What it does | Where |
|---|---|---|
| **One wallpaper per tile** | Each tile in the top row gets its own wallpaper, swapped as focus moves and stored by slot index; builtin, phone-uploaded photo or video, Bing daily, or "inherit" | Personalize → Wallpaper → per tile |
| **Video wallpapers** | Phone-uploaded videos as wallpaper, muted loops; a key press freezes the current frame | same |
| **Personal image host** | Multiple named links; wallpaper and screensaver each pick one or more hosts or all aggregated, changes apply at once | Personalize → Wallpaper / Screensaver → Image host |
| **Playback speed** | 0.5×–1.5× in 0.1 steps for video wallpapers and the screensaver | Personalize → Wallpaper / Screensaver |
| **Fade between wallpapers** | Every wallpaper switch is a 650 ms dissolve | automatic |
| **Wake splash** | 5 s spinner when waking from standby, then fades into Home; not on cold starts or restarts | Personalize → TV System → Wake animation |
| **Single-video screensaver** | Loop one specific video as the screensaver | Personalize → Screensaver |
| **Flip clock screensaver** | FlipClock-style split-flap cards, a flap every second, slight drift against burn-in | Personalize → Screensaver → Flip clock |
| **Auto-brightness fix** | Fixes the dead brightness slider under Xiaomi's auto-brightness | Control Center → Brightness |
| **Developer mode at boot** | Reopens developer options and ADB after every boot | Personalize → TV System |
| **Five languages** | zh, en, ru, uk, kk | — |

## Scripts

| Script | What it does |
|---|---|
| `tools/rescue.sh` / `tools/rescue.bat` | Black-screen recovery: connect → diagnose → re-enable the stock launcher → hand HOME back → wake. `--scan` searches the subnet, `--check` only diagnoses. Double-click `rescue.bat` on Windows |
| `tools/restore.sh` | Restores the stock launcher: `pm enable` first, then hand HOME over; `--uninstall` removes Aerial too |
| `tools/install-ts.sh` | Installs, sets as HOME, optionally disables the stock launcher; `KEEP_ADB=1` keeps ADB, `BUILD=1` builds first |
| `tools/dev-mode.sh` | Opens developer mode and ADB once |

**Order is a hard constraint**: do not disable `com.mitv.tvhome` until a whitelisted launcher is installed and verified, or HOME falls to `FallbackHome` and the TV boots to a black screen.

## Install

Enable ADB on the TV (Settings → About → tap "Model" repeatedly, then Settings → Account & Security → ADB debugging) and put it on the same network as the PC:

```bash
git clone https://github.com/le5inioo/Aerial-MiTV.git && cd Aerial-MiTV
TV=<TV-IP> BUILD=1 KEEP_ADB=1 DISABLE_STOCK_HOME=1 tools/install-ts.sh
```

Or download an [APK](https://github.com/le5inioo/Aerial-MiTV/releases/latest) and pass `APK_FILE=`. Upgrading is the same command; settings survive.

## Black screen

`tools/rescue.sh <TV-IP>` (`--scan` to find a TV, `--check` to diagnose only); on Windows double-click `tools/rescue.bat`. Restore the stock launcher with `TV=<IP> tools/restore.sh`. ADB drops in standby when Wi-Fi sleeps, so test keys with the real remote.

## Building

JDK 17 + Android SDK 36: `./gradlew assembleStandardRelease`. Architecture in [docs/ARCHITECTURE.md](docs/ARCHITECTURE.md), handover in [docs/HANDOFF.md](docs/HANDOFF.md).

## License

[MIT](LICENSE); upstream attribution in [NOTICE.md](NOTICE.md).
