<div align="center">

<img src="docs/brand/icon.png" width="96" alt="Aerial icon">

# Aerial · Xiaomi TV fork

A launcher tuned for one TV: the Xiaomi TV S65 Mini LED (MiTV-MFTR0). Forked from [Aerial](https://github.com/pengchujin/aerial-tv).

<a href="https://github.com/le5inioo/Aerial-MiTV/releases/latest"><img alt="Download APK" src="https://img.shields.io/badge/Download%20APK-34a853?style=for-the-badge&logo=android&logoColor=white"></a>
<a href="https://github.com/le5inioo/Aerial-MiTV/releases/latest"><img alt="Version" src="https://img.shields.io/github/v/release/le5inioo/Aerial-MiTV?style=flat-square&label=version&color=2997ff"></a>
<a href="LICENSE"><img alt="License MIT" src="https://img.shields.io/badge/license-MIT-555555?style=flat-square"></a>
<img alt="Android 11 / API 30" src="https://img.shields.io/badge/Android%2011%20%2F%20API%2030-3ddc84?style=flat-square&logo=android&logoColor=white">

![Aerial home](docs/brand/home.jpg)

</div>

[中文](README.md) · English

## About

Upstream handles the features, other devices and the install scripts; this repo only covers this one TV. Still MIT (upstream attribution in [NOTICE.md](NOTICE.md)).

Measured on MiTV-MFTR0 / L65MA-SPL, mt5879, 3.7 GB usable RAM, Android 11, HyperOS 2.0.5.0. Apps only ever see 1080p (1920×1080 / 60 Hz); the panel's real resolution is not readable, so do not plan for nominal 4K. Full measurements are in [docs/devices/xiaomi-mitv-mftr0.md](docs/devices/xiaomi-mitv-mftr0.md) (Chinese).

## Features

- **One wallpaper per tile**: focus swaps the picture, remembered by slot index; builtin, phone-uploaded photo or video, Bing daily, or inherit.
- **Video wallpapers**: muted loops, a key press freezes the current frame; the screensaver can loop one video, or use flip clock / aerial / photos / image host / system.
- **Image host**: multiple named links; wallpaper and screensaver each pick one or more hosts or all aggregated, changes apply at once.
- **Also**: playback speed 0.5×–1.5× in 0.1 steps, 650 ms wallpaper dissolve, wake splash, auto-brightness fix, developer mode and ADB restored at boot.
- **Five languages**: zh, en, ru, uk, kk.

Tile art: 69 pictures in [docs/tile-icons/](docs/tile-icons/) (file name is the app, uploaded from the phone page). Details in [docs/ARCHITECTURE.md](docs/ARCHITECTURE.md).

## Install

Turn on ADB on the TV (Settings → About → tap "Model" repeatedly, then Settings → Account & Security → ADB debugging), and put the TV on the same network:

```bash
git clone https://github.com/le5inioo/Aerial-MiTV.git
cd Aerial-MiTV
TV=<TV-IP> BUILD=1 KEEP_ADB=1 DISABLE_STOCK_HOME=1 tools/install-ts.sh
```

Or grab an APK from [Releases](https://github.com/le5inioo/Aerial-MiTV/releases/latest) and pass `APK_FILE=`. Upgrading is the same command; settings survive.

`DISABLE_STOCK_HOME=1` disables the stock `com.mitv.tvhome`: instant Home key with no flash, at the cost of a black screen if Aerial ever breaks — keep ADB open and `tools/rescue.sh` at hand. Without it the guard fights PatchWall for the Home key (brief flash). **Never disable `com.mitv.tvhome` until a whitelisted launcher is installed and verified**, or HOME falls to `FallbackHome` and the TV boots to a black screen.

## Black screen

A black screen means nothing owns HOME; ADB is the only way in:

```bash
tools/rescue.sh              # default address
tools/rescue.sh <TV-IP>
tools/rescue.sh --scan       # find a TV with ADB open
tools/rescue.sh --check      # diagnose only, change nothing
```

On Windows, double-click `tools/rescue.bat`. To restore the stock launcher: `TV=<TV-IP> tools/restore.sh` (add `--uninstall` to remove Aerial too).

## Known limits

- ADB dies in standby with Wi-Fi off; remote wake cannot bring it back. Standby behaviour can only be tested with the physical remote.
- Injected keys skip the accessibility filter — use the real remote to test the Home key.
- Source switching was only verified against this unit's 8 inputs.

## Building

JDK 17 and Android SDK 36: `./gradlew assembleStandardRelease`. Handover notes in [docs/HANDOFF.md](docs/HANDOFF.md).

## License

[MIT](LICENSE). Upstream copyright belongs to the [pengchujin/aerial-tv](https://github.com/pengchujin/aerial-tv) authors — see [NOTICE.md](NOTICE.md). Inter is under the [SIL Open Font License](FONT-LICENSE-OFL.txt); weather city data from [GeoNames](https://www.geonames.org) (CC BY 4.0), weather from [Open-Meteo](https://open-meteo.com); aerial films belong to Apple and stream from Apple's servers.
