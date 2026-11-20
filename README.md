<div align="center">

<img src="docs/brand/icon.png" width="96" alt="Aerial 图标">

# Aerial · 小米电视定制分支

给小米电视 S65 Mini LED（MiTV-MFTR0）改的桌面，fork 自 [Aerial](https://github.com/pengchujin/aerial-tv)。

<a href="https://github.com/le5inioo/Aerial-MiTV/releases/latest"><img alt="下载 APK" src="https://img.shields.io/badge/%E4%B8%8B%E8%BD%BD%20APK-34a853?style=for-the-badge&logo=android&logoColor=white"></a>
<a href="https://github.com/le5inioo/Aerial-MiTV/releases/latest"><img alt="版本" src="https://img.shields.io/github/v/release/le5inioo/Aerial-MiTV?style=flat-square&label=%E7%89%88%E6%9C%AC&color=2997ff"></a>
<a href="LICENSE"><img alt="许可证 MIT" src="https://img.shields.io/badge/%E8%AE%B8%E5%8F%AF%E8%AF%81-MIT-555555?style=flat-square"></a>
<img alt="Android 11 / API 30" src="https://img.shields.io/badge/Android%2011%20%2F%20API%2030-3ddc84?style=flat-square&logo=android&logoColor=white">

![Aerial 首页](docs/brand/home.jpg)

</div>

[English](README.en.md)

## 关于

上游负责功能、其它机型和安装脚本；这里只管这台电视的定制，代码仍是 MIT（归属见 [NOTICE.md](NOTICE.md)）。

实测机型 MiTV-MFTR0 / L65MA-SPL，mt5879，3.7 GB 可用，Android 11，HyperOS 2.0.5.0。应用层只有 1080p（1920×1080 / 60 Hz），面板真实分辨率读不到，别按标称 4K 估。完整实测记录见 [docs/devices/xiaomi-mitv-mftr0.md](docs/devices/xiaomi-mitv-mftr0.md)。

## 功能

- **每块磁贴一张壁纸**：焦点移到哪张就换哪张，按序号记住；可选内置图、手机传的照片/视频、每日一图或跟随默认。
- **视频壁纸**：手机传的视频静音循环，按键停在当前帧；屏保可用单视频循环，或翻页时钟 / 航拍 / 照片 / 图床 / 系统内置。
- **个人图床**：多条链接、每条可命名；壁纸与屏保各选一个或多个图床或全部聚合，增删即时生效。
- **其它**：倍速 0.5–1.5×（每 0.1 一档）、壁纸 650 ms 淡入淡出、待机唤醒动画、自动感光亮度修复、开机自动恢复开发者模式与 ADB。
- **五语言**：中 / 英 / 俄 / 乌 / 哈。

磁贴图 69 张见 [docs/tile-icons/](docs/tile-icons/)（文件名即应用名，手机设置页上传）。细节见 [docs/ARCHITECTURE.md](docs/ARCHITECTURE.md)。

## 安装

电视开 ADB（设置 → 关于 → 连按「产品型号」进开发者模式，再到 设置 → 账号与安全 打开「ADB 调试」），和电脑同网段：

```bash
git clone https://github.com/le5inioo/Aerial-MiTV.git
cd Aerial-MiTV
TV=<电视IP> BUILD=1 KEEP_ADB=1 DISABLE_STOCK_HOME=1 tools/install-ts.sh
```

也可直接下 [Release](https://github.com/le5inioo/Aerial-MiTV/releases/latest) 的 APK，用 `APK_FILE=` 指定。升级重跑即可，设置保留。

`DISABLE_STOCK_HOME=1` 停用原桌面 `com.mitv.tvhome`：换来主页键即时响应、不闪原桌面，代价是 Aerial 挂了就黑屏——装前确认 ADB 开着、`tools/rescue.sh` 在手边。不加则靠守护抢主页键，会闪一下原桌面。**白名单桌面没装好并验证前，不要停用 `com.mitv.tvhome`**，否则 HOME 落到 `FallbackHome`，开机一直黑屏。

## 黑屏了怎么办

黑屏即 HOME 没有归属，ADB 是唯一入口：

```bash
tools/rescue.sh              # 默认地址
tools/rescue.sh <电视IP>
tools/rescue.sh --scan       # 扫网段找电视
tools/rescue.sh --check      # 只看诊断，不改动
```

Windows 双击 `tools/rescue.bat`。还原原桌面：`TV=<电视IP> tools/restore.sh`（加 `--uninstall` 顺便卸载）。

## 已知限制

- 待机时 Wi-Fi 关闭，ADB 断、远程唤不醒；待机行为只能用实体遥控器测。
- 注入按键不走无障碍过滤，主页键回桌面必须用真遥控器测。
- 信号源只在这台的 8 个输入上验过，换固件要重新对编号。

## 开发

JDK 17 + Android SDK 36：`./gradlew assembleStandardRelease`。接手说明见 [docs/HANDOFF.md](docs/HANDOFF.md)。

## 许可

[MIT](LICENSE)，上游版权归 [pengchujin/aerial-tv](https://github.com/pengchujin/aerial-tv) 作者，详见 [NOTICE.md](NOTICE.md)。Inter 字体 [SIL OFL](FONT-LICENSE-OFL.txt)；天气城市表 [GeoNames](https://www.geonames.org)（CC BY 4.0）、数据 [Open-Meteo](https://open-meteo.com)；航拍影片归 Apple，由其服务器直接播放。
