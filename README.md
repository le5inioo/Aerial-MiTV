# Aerial-MiTV

小米电视 S65 Mini LED（MiTV-MFTR0）定制桌面，fork 自 [Aerial](https://github.com/pengchujin/aerial-tv)。[下载 APK](https://github.com/le5inioo/Aerial-MiTV/releases/latest) · [English](README.en.md)

## 功能

磁贴壁纸 · 视频壁纸 · 个人图床 · 屏保 · 五语言

## 安装

电视开 ADB（设置 → 关于 → 连按「产品型号」，再到 设置 → 账号与安全 → 打开 ADB 调试），与电脑同网段：

```bash
git clone https://github.com/le5inioo/Aerial-MiTV.git && cd Aerial-MiTV
TV=<电视IP> BUILD=1 KEEP_ADB=1 DISABLE_STOCK_HOME=1 tools/install-ts.sh
```

也可下 [APK](https://github.com/le5inioo/Aerial-MiTV/releases/latest) 并用 `APK_FILE=` 指定；升级重跑即可，设置保留。`DISABLE_STOCK_HOME=1` 会停用原桌面，Aerial 挂了就黑屏——白名单桌面没验证前别加。

## 黑屏急救

`tools/rescue.sh <电视IP>`（`--scan` 找电视，`--check` 只诊断），Windows 双击 `tools/rescue.bat`；还原原桌面 `TV=<IP> tools/restore.sh`。待机时 Wi-Fi 关闭会断 ADB，按键测试用真遥控器。

## 开发

JDK 17 + Android SDK 36：`./gradlew assembleStandardRelease`。接手见 [docs/HANDOFF.md](docs/HANDOFF.md)。

## 许可

[MIT](LICENSE)，上游归属见 [NOTICE.md](NOTICE.md)。
