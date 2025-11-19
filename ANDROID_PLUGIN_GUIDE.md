# Android Plugin 使用说明

## CarControlPlugin 集成指南

这个插件允许 Java 代码与 Godot 引擎交互，控制 3D 小车。

## 插件编译步骤

### 1. 准备工作

**系统要求：**
- JDK 17 或更高版本
- Gradle 7.0 或更高版本（或使用 Gradle Wrapper）
- Android SDK (API 21-34)

下载 Godot Android 库：
- 从 [Godot 官方下载页](https://godotengine.org/download/android) 下载 Android 库
- 或者从你的 Godot 编辑器中导出 Android 构建模板

### 2. 设置项目

```bash
# 创建 libs 目录
mkdir -p android/plugins/car_control/libs

# 复制 Godot Android 库
cp godot-lib.release.aar android/plugins/car_control/libs/
```

### 3. 使用 Android Studio 构建

1. 打开 Android Studio
2. 选择 "Open an existing project"
3. 打开 `android/plugins/car_control` 目录
4. 等待 Gradle 同步完成（会自动下载依赖）
5. 构建项目: Build -> Make Project
6. 生成的 AAR 文件位于 `build/outputs/aar/`

### 4. 使用 Gradle 命令行构建

```bash
cd android/plugins/car_control

# 使用 Gradle Wrapper (推荐)
# Windows:
gradlew.bat assembleRelease

# Linux/Mac:
./gradlew assembleRelease

# 或使用系统 Gradle
gradle assembleRelease
```

**注意：** 首次构建时，Gradle 会自动下载 Android Gradle Plugin 和其他依赖，这可能需要几分钟时间。

### 5. 故障排除

#### 问题：找不到 Android Gradle Plugin

**错误信息：**
```
Plugin [id: 'com.android.library'] was not found
```

**解决方案：**
- 确保 `build.gradle` 包含 `buildscript` 部分和仓库配置
- 确保 `settings.gradle` 包含 `pluginManagement` 配置
- 检查网络连接，确保可以访问 Google 和 Maven 仓库
- 如果使用代理，在 `gradle.properties` 中配置代理设置：
  ```
  systemProp.http.proxyHost=your.proxy.host
  systemProp.http.proxyPort=8080
  systemProp.https.proxyHost=your.proxy.host
  systemProp.https.proxyPort=8080
  ```

## 在 Godot 中使用插件

### 1. 安装插件

将生成的 `car_control.aar` 文件复制到 `android/plugins/car_control/` 目录。

### 2. 启用插件

在 Godot 编辑器中：
1. 项目 -> 项目设置 -> 插件
2. 启用 "CarControlPlugin"

### 3. 导出设置

在导出 Android 时：
1. 项目 -> 导出
2. 选择 Android 预设
3. 在 "自定义构建" 中启用 "Use Gradle Build"
4. 勾选 "CarControlPlugin"

## API 文档

### Java 接口

```java
CarControlPlugin plugin = new CarControlPlugin(godot);

// 移动控制
plugin.moveForward();      // 开始向前
plugin.moveBackward();     // 开始向后
plugin.stopMovement();     // 停止移动

// 速度控制
plugin.accelerate();       // 加速
plugin.decelerate();       // 刹车
plugin.stopSpeedChange();  // 停止速度变化

// 重置
plugin.resetCar();         // 重置小车
```

### GDScript 接口

```gdscript
# 这些函数在 main.gd 中定义，由 Java 调用
func java_move_forward()
func java_move_backward()
func java_stop_movement()
func java_accelerate()
func java_decelerate()
func java_stop_speed_change()
func java_reset()
```

## 自定义 Android UI

`CarControlActivity.java` 提供了一个原生 Android UI 示例。你可以：

1. 修改按钮样式和布局
2. 添加更多控制选项
3. 集成其他 Android 功能（传感器、GPS 等）

## 故障排除

### 问题：找不到 Godot 类

**解决方案**：确保 `godot-lib.aar` 在 `libs/` 目录中。

### 问题：插件未加载

**解决方案**：
1. 检查 `car_control.gdap` 文件是否正确
2. 确保在导出设置中启用了插件
3. 检查 Android 日志

### 问题：调用失败

**解决方案**：
1. 确保在 GL 线程上调用
2. 使用 `callDeferred` 延迟调用
3. 检查 Godot 场景树中的节点名称

## 进阶使用

### 添加信号

在 `CarControlPlugin.java` 中：

```java
@Override
public Set<SignalInfo> getPluginSignals() {
    Set<SignalInfo> signals = new HashSet<>();
    signals.add(new SignalInfo("car_speed_changed", Float.class));
    return signals;
}

// 发送信号
emitSignal("car_speed_changed", 15.5f);
```

在 Godot 中连接信号：

```gdscript
var plugin = Engine.get_singleton("CarControlPlugin")
plugin.car_speed_changed.connect(_on_speed_changed)

func _on_speed_changed(speed):
    print("Speed: ", speed)
```

## 许可证

MIT License
