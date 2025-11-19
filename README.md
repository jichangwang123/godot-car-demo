"# 3D Car Android Control Demo

一个使用 Godot 4.4 和 Java 原生 Android 开发的 3D 小车控制项目。

## 项目简介

本项目实现了一个可以通过 Android 界面按钮控制的 3D 小车。使用 Godot 4.4 进行界面设计和 3D 建模，通过 Java 原生 Android 代码与 Godot 引擎交互，实现对小车的控制。

## 功能特性

- **前进/后退控制**: 点击按钮控制小车前进或后退
- **加速/减速控制**: 独立的加速和刹车按钮
- **重置功能**: 一键重置小车位置和状态
- **实时状态显示**: 显示当前小车的状态和速度
- **3D 物理模拟**: 使用 Godot 的 VehicleBody3D 实现真实的车辆物理

## 项目结构

```
godot-car-demo/
├── project.godot              # Godot 项目配置文件
├── main.tscn                  # 主场景文件
├── main.gd                    # 主场景脚本
├── car.tscn                   # 3D 小车场景
├── car.gd                     # 小车控制脚本
├── icon.svg                   # 项目图标
├── export_presets.cfg         # Android 导出配置
└── android/                   # Android 插件目录
    └── plugins/
        └── car_control/
            ├── build.gradle                    # Gradle 构建配置
            ├── car_control.gdap               # Godot 插件配置
            └── src/main/
                ├── AndroidManifest.xml         # Android 清单文件
                └── java/com/godot/carcontrol/
                    ├── CarControlPlugin.java   # Godot 插件类
                    └── CarControlActivity.java # 原生 Android 界面
```

## 技术栈

- **Godot Engine**: 4.4
- **编程语言**: GDScript (Godot), Java (Android)
- **Android SDK**: 最低 API 21，目标 API 34
- **3D 物理**: Godot VehicleBody3D

## 快速开始

### 前置要求

1. Godot Engine 4.4 或更高版本
2. Android Studio (用于编译 Android 插件)
3. Android SDK (API 21+)
4. JDK 17 或更高版本

### 在 Godot 编辑器中运行

1. 克隆本仓库
2. 使用 Godot 4.4 打开项目
3. 点击运行按钮 (F5) 或导出到 Android 设备

### 构建 Android 插件

1. 将 Godot Android 库文件 (godot-lib.aar) 放入 `android/plugins/car_control/libs/` 目录
2. 在 Android Studio 中打开 `android/plugins/car_control/` 目录
3. 构建项目生成 AAR 文件
4. 将生成的 `car_control.aar` 放入插件目录

### 导出到 Android

1. 在 Godot 中打开 "项目" -> "导出"
2. 选择 "Android" 预设
3. 配置签名和路径
4. 点击 "导出项目" 或 "导出并运行"

## 控制说明

### Godot UI 控制

- **Forward ↑**: 按住使小车向前移动
- **Backward ↓**: 按住使小车向后移动
- **Accelerate ++**: 按住加速小车
- **Brake --**: 按住减速/刹车
- **Reset**: 重置小车到初始位置

### Java API 接口

CarControlPlugin 提供以下 Java 方法：

```java
// 移动控制
moveForward()      // 开始向前移动
moveBackward()     // 开始向后移动
stopMovement()     // 停止移动

// 速度控制
accelerate()       // 开始加速
decelerate()       // 开始刹车
stopSpeedChange()  // 停止速度变化

// 重置
resetCar()         // 重置小车
```

## 代码示例

### GDScript (Godot)

```gdscript
# 在 main.gd 中调用小车控制
car.move_forward()
car.accelerate()
car.stop_movement()
```

### Java (Android)

```java
// 通过插件控制小车
CarControlPlugin carPlugin = new CarControlPlugin(godot);
carPlugin.moveForward();
carPlugin.accelerate();
```

## 自定义开发

### 修改小车参数

在 `car.gd` 中可以调整以下参数：

```gdscript
var max_speed = 20.0        # 最大速度
var acceleration = 10.0     # 加速度
var brake_force = 5.0       # 刹车力度
var steering_angle = 0.5    # 转向角度
```

### 添加新功能

1. 在 `car.gd` 中添加新的控制方法
2. 在 `main.gd` 中添加对应的 `java_*` 方法
3. 在 `CarControlPlugin.java` 中添加 Java 接口
4. 在 UI 中添加相应的按钮

## 常见问题

### Q: 如何更改小车模型？
A: 编辑 `car.tscn` 文件，替换 MeshInstance3D 的 mesh 属性。

### Q: 如何调整摄像机视角？
A: 在 `car.tscn` 中调整 Camera3D 节点的位置和旋转。

### Q: Android 导出失败怎么办？
A: 确保已安装 Android 构建模板和正确的 SDK 版本。

## 许可证

本项目采用 MIT 许可证。详见 LICENSE 文件。

## 贡献

欢迎提交 Issue 和 Pull Request！

## 联系方式

如有问题，请提交 Issue 或联系项目维护者。

---

## English Version

# 3D Car Android Control Demo

A 3D car control project using Godot 4.4 and native Android development with Java.

## Features

- Forward/Backward control with button press
- Independent Accelerate/Brake controls
- Reset functionality
- Real-time status and speed display
- 3D physics simulation using VehicleBody3D

## Quick Start

1. Clone the repository
2. Open with Godot 4.4
3. Run (F5) or export to Android device

## Controls

- **Forward ↑**: Hold to move forward
- **Backward ↓**: Hold to move backward
- **Accelerate ++**: Hold to speed up
- **Brake --**: Hold to slow down
- **Reset**: Reset car position and state

## License

MIT License" 
