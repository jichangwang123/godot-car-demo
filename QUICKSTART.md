# 快速开始指南 / Quick Start Guide

## 中文版

### 第一步：打开项目

1. 下载并安装 [Godot 4.4](https://godotengine.org/download)
2. 克隆或下载本项目
3. 在 Godot 编辑器中点击 "导入" 按钮
4. 选择项目目录中的 `project.godot` 文件
5. 点击 "导入并编辑"

### 第二步：在编辑器中测试

1. 项目打开后，主场景 `main.tscn` 应该会自动加载
2. 点击编辑器右上角的 "运行项目" 按钮 (或按 F5)
3. 你应该能看到一个 3D 小车在平台上
4. 使用屏幕底部的按钮控制小车：
   - **Forward ↑**: 按住使小车向前移动
   - **Backward ↓**: 按住使小车向后移动
   - **Accelerate ++**: 按住加速
   - **Brake --**: 按住刹车
   - **Reset**: 重置小车位置

### 第三步：导出到 Android (可选)

#### 准备工作

1. 下载 [Android Studio](https://developer.android.com/studio)
2. 安装 Android SDK (API 21+)
3. 在 Godot 中配置 Android 导出模板：
   - 编辑器 -> 编辑器设置 -> 导出 -> Android
   - 设置 Android SDK 路径

#### 导出项目

1. 项目 -> 导出
2. 添加 "Android" 导出预设（如果尚未添加）
3. 配置签名和其他设置
4. 点击 "导出项目" 或 "导出并运行"（如果设备已连接）

### 第四步：使用 Android 插件 (高级)

如果你想从 Java 代码控制小车：

1. 参考 `ANDROID_PLUGIN_GUIDE.md` 了解详细步骤
2. 编译 Android 插件
3. 在导出时启用插件
4. 使用 `CarControlPlugin.java` 提供的 API

---

## English Version

### Step 1: Open the Project

1. Download and install [Godot 4.4](https://godotengine.org/download)
2. Clone or download this project
3. In Godot editor, click "Import" button
4. Select the `project.godot` file in the project directory
5. Click "Import & Edit"

### Step 2: Test in Editor

1. After the project opens, the main scene `main.tscn` should load automatically
2. Click the "Run Project" button in the top-right corner (or press F5)
3. You should see a 3D car on a platform
4. Use the buttons at the bottom of the screen to control the car:
   - **Forward ↑**: Hold to move forward
   - **Backward ↓**: Hold to move backward
   - **Accelerate ++**: Hold to speed up
   - **Brake --**: Hold to brake
   - **Reset**: Reset car position

### Step 3: Export to Android (Optional)

#### Prerequisites

1. Download [Android Studio](https://developer.android.com/studio)
2. Install Android SDK (API 21+)
3. Configure Android export templates in Godot:
   - Editor -> Editor Settings -> Export -> Android
   - Set Android SDK path

#### Export the Project

1. Project -> Export
2. Add "Android" export preset (if not already added)
3. Configure signing and other settings
4. Click "Export Project" or "Export and Run" (if device is connected)

### Step 4: Use Android Plugin (Advanced)

If you want to control the car from Java code:

1. Refer to `ANDROID_PLUGIN_GUIDE.md` for detailed steps
2. Compile the Android plugin
3. Enable the plugin when exporting
4. Use the API provided by `CarControlPlugin.java`

---

## 常见问题 / FAQ

### Q: 小车不移动怎么办？
**A**: 确保同时按下方向按钮（Forward/Backward）和加速按钮（Accelerate）。

### Q: How do I make the car move?
**A**: Press both a direction button (Forward/Backward) AND the Accelerate button.

### Q: 如何修改小车的速度？
**A**: 编辑 `car.gd` 文件，修改 `max_speed` 和 `acceleration` 变量。

### Q: How to change the car's speed?
**A**: Edit the `car.gd` file and modify the `max_speed` and `acceleration` variables.

### Q: 可以添加转向控制吗？
**A**: 可以！在 `car.gd` 中添加转向逻辑，并在 UI 中添加左右按钮。参考 Godot 的 VehicleWheel3D 文档。

### Q: Can I add steering controls?
**A**: Yes! Add steering logic in `car.gd` and add left/right buttons in the UI. Refer to Godot's VehicleWheel3D documentation.

---

## 项目结构 / Project Structure

```
godot-car-demo/
├── project.godot              # Project configuration
├── main.tscn                  # Main scene (3D environment + UI)
├── main.gd                    # Main scene controller
├── car.tscn                   # 3D car scene
├── car.gd                     # Car physics and controls
├── icon.svg                   # Project icon
├── export_presets.cfg         # Export configuration
├── README.md                  # Main documentation
├── ANDROID_PLUGIN_GUIDE.md    # Android plugin guide
├── LICENSE                    # MIT License
└── android/                   # Android plugin
    └── plugins/
        └── car_control/       # Java plugin for Android integration
```

---

## 获取帮助 / Get Help

- 查看 [README.md](README.md) 获取完整文档
- 查看 [ANDROID_PLUGIN_GUIDE.md](ANDROID_PLUGIN_GUIDE.md) 了解 Android 插件
- 提交 Issue 报告问题或请求功能
- 参考 [Godot 官方文档](https://docs.godotengine.org/)

---

祝你玩得开心！/ Have fun!
