# Aerial-MiTV

小米电视 S65 Mini LED（MiTV-MFTR0）定制桌面，fork 自 [Aerial](https://github.com/pengchujin/aerial-tv)。[下载 APK](https://github.com/le5inioo/Aerial-MiTV/releases/latest) · [English](README.en.md)

## 这台电视

| 项目 | 值 |
|---|---|
| 品牌 / 型号 | 小米（Xiaomi）MiTV-MFTR0 |
| 零售型号 | 小米电视 S65 Mini LED 臻享版（L65MA-SPL） |
| CPU | mt5879 · armeabi-v7a · 4 核心 |
| 内存 | 3.7 GB 可用（标称 4 GB） |
| 系统 | Android 11（SDK 30） |
| 固件 | HyperOS 2.0.5.0 |

应用层只有 1080p（1920×1080 / 60 Hz），面板物理分辨率读不到，不要按标称 4K 估。

## 本分支做了什么

相对上游的改动：

| 改动 | 位置 | 为什么 |
|---|---|---|
| 包名换成 `com.ts.launcher` | `app/build.gradle.kts` | 固件只把 HOME 交给白名单包名；`com.ts.launcher` 在白名单里，`app.orchard.tv` 不在 |
| 停用 PatchWall | `tools/install-ts.sh`（`DISABLE_STOCK_HOME=1`） | 不停用的话 HOME 会解析成桌面选择框，按主页键会闪一下原桌面 |
| 桌面守护 | `guard/HomeGuardService.kt` | 原桌面到前台就切回来；待机被杀后靠通知监听复活；开机/亮屏/复活都等 1.5 秒再接管 |
| 条件按键过滤 | `guard/HomeGuardService.filterKeys` | Aerial 在前台时先拦主页键，省掉闪原桌面；在别的 App 里不拦 |
| 守住开发者模式与 ADB | `guard/GuardSetup.kt` | 开机写回 `development_settings_enabled`、`adb_enabled` 等并用 `ContentObserver` 盯着；小米待机会把 ADB 关掉 |
| 家长密码 10 分钟宽限 | `ui/home/PinPad.kt` | 输对一次密码后 10 分钟内进设置不再重复要 |

## 功能

| 功能 | 说明 | 入口 |
|---|---|---|
| **每块磁贴一张壁纸** | 第一排每个磁贴各配一张壁纸，焦点移到哪张就换哪张，按序号记住；可选内置图、手机传的照片/视频、每日一图或跟随默认 | 个性化 → 壁纸 → 每块磁贴 |
| **视频壁纸** | 手机传的视频当壁纸，静音循环，按键停在当前帧 | 同上 |
| **个人图床** | 多条链接、每条可命名；壁纸与屏保各选一个或多个图床或全部聚合，增删即时生效 | 个性化 → 壁纸 / 屏保 → 图床 |
| **播放倍速** | 视频当壁纸或屏保时的速度，0.5–1.5 倍、每 0.1 一档 | 个性化 → 壁纸 / 屏保 |
| **壁纸淡入淡出** | 壁纸切换 650 ms 溶解过渡 | 自动 |
| **唤醒动画** | 待机唤醒播 5 秒转圈动画再淡入桌面，冷启动和重启不播 | 个性化 → 电视系统 → 唤醒动画 |
| **屏保单视频** | 指定某一个视频循环播放作屏保 | 个性化 → 屏保 |
| **翻页时钟屏保** | FlipClock 式翻页时钟，每秒翻页，整体轻微漂移防烧屏 | 个性化 → 屏保 → 翻页时钟 |
| **自动感光亮度** | 修小米自动感光下亮度条没反应 | 控制中心 → 亮度 |
| **开机开启开发者模式** | 每次开机把开发者选项和 ADB 重新打开 | 个性化 → 电视系统 |
| **五语言** | 中 / 英 / 俄 / 乌 / 哈 | — |

## 脚本

| 脚本 | 用途 |
|---|---|
| `tools/rescue.sh` / `tools/rescue.bat` | 黑屏应急：连接 → 诊断 → 启用原桌面 → 交还 HOME → 唤醒。`--scan` 扫网段，`--check` 只看诊断。Windows 双击 `rescue.bat` |
| `tools/restore.sh` | 还原原桌面，先 `pm enable` 再把 HOME 交回去；`--uninstall` 顺便卸载 Aerial |
| `tools/install-ts.sh` | 安装、设为桌面、按需停用原桌面；`KEEP_ADB=1` 保持 ADB，`BUILD=1` 先编译 |
| `tools/dev-mode.sh` | 单独打开开发者模式和 ADB |

**顺序是硬约束**：白名单桌面没装好并验证前，不要停用 `com.mitv.tvhome`，否则 HOME 落到 `FallbackHome`、开机一直黑屏。

## 安装

电视开 ADB（设置 → 关于 → 连按「产品型号」，再到 设置 → 账号与安全 → 打开 ADB 调试），与电脑同网段：

```bash
git clone https://github.com/le5inioo/Aerial-MiTV.git && cd Aerial-MiTV
TV=<电视IP> BUILD=1 KEEP_ADB=1 DISABLE_STOCK_HOME=1 tools/install-ts.sh
```

也可下 [APK](https://github.com/le5inioo/Aerial-MiTV/releases/latest) 并用 `APK_FILE=` 指定；升级重跑即可，设置保留。

## 黑屏急救

`tools/rescue.sh <电视IP>`（`--scan` 找电视，`--check` 只诊断），Windows 双击 `tools/rescue.bat`；还原原桌面 `TV=<IP> tools/restore.sh`。待机时 Wi-Fi 关闭会断 ADB，按键测试用真遥控器。

## 开发

JDK 17 + Android SDK 36：`./gradlew assembleStandardRelease`。架构见 [docs/ARCHITECTURE.md](docs/ARCHITECTURE.md)，接手见 [docs/HANDOFF.md](docs/HANDOFF.md)。

## 许可

[MIT](LICENSE)，上游归属见 [NOTICE.md](NOTICE.md)。
