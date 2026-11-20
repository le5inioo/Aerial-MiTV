# Aerial-MiTV

Launcher for the Xiaomi TV S65 Mini LED (MiTV-MFTR0), forked from [Aerial](https://github.com/pengchujin/aerial-tv). [Download APK](https://github.com/le5inioo/Aerial-MiTV/releases/latest) · [中文](README.md)

## Features

- One wallpaper per tile: focus swaps the picture, remembered by slot index
- Video wallpapers loop muted and freeze on a key press; screensaver: flip clock / aerial / video / photos / image host / system
- Image host with multiple named links; wallpaper and screensaver pick hosts separately, or aggregate them
- Five languages, playback speed, auto-brightness, ADB restored at boot

## Install

Enable ADB on the TV (Settings → About → tap "Model" repeatedly, then Settings → Account & Security → ADB debugging) and put it on the same network as the PC:

```bash
git clone https://github.com/le5inioo/Aerial-MiTV.git && cd Aerial-MiTV
TV=<TV-IP> BUILD=1 KEEP_ADB=1 DISABLE_STOCK_HOME=1 tools/install-ts.sh
```

Or download an [APK](https://github.com/le5inioo/Aerial-MiTV/releases/latest) and pass `APK_FILE=`. Upgrading is the same command; settings survive. `DISABLE_STOCK_HOME=1` disables the stock launcher — if Aerial fails the screen goes black, so do not add it until a whitelisted launcher is verified.

## Black screen

`tools/rescue.sh <TV-IP>` (`--scan` to find a TV, `--check` to diagnose only); on Windows double-click `tools/rescue.bat`. Restore the stock launcher with `TV=<IP> tools/restore.sh`. ADB drops in standby when Wi-Fi sleeps, so test keys with the real remote.

## Building

JDK 17 + Android SDK 36: `./gradlew assembleStandardRelease`. Handover notes in [docs/HANDOFF.md](docs/HANDOFF.md).

## License

[MIT](LICENSE); upstream attribution in [NOTICE.md](NOTICE.md).
