# Development Guide / 开发指南

## Table of Contents / 目录

1. [Setting Up Development Environment / 开发环境设置](#setup)
2. [Code Structure / 代码结构](#structure)
3. [Adding New Features / 添加新功能](#features)
4. [Debugging / 调试](#debugging)
5. [Testing / 测试](#testing)
6. [Common Tasks / 常见任务](#tasks)

---

<a name="setup"></a>
## 1. Setting Up Development Environment / 开发环境设置

### Requirements / 需求

- **Godot 4.4** or later
- **JDK 17** or later (for Android plugin)
- **Android Studio** (optional, but recommended)
- **Git** for version control

### First Time Setup / 首次设置

```bash
# Clone the repository
git clone https://github.com/jichangwang123/godot-car-demo.git
cd godot-car-demo

# Open in Godot Editor
# File -> Open Project -> Select project.godot
```

### Android Development Setup / Android 开发设置

```bash
# Navigate to plugin directory
cd android/plugins/car_control

# Place Godot Android library
mkdir -p libs
# Copy godot-lib.release.aar to libs/

# Build plugin
./gradlew assembleRelease
```

---

<a name="structure"></a>
## 2. Code Structure / 代码结构

### File Organization / 文件组织

```
Project Root
├── Core Godot Files
│   ├── project.godot        # Project configuration
│   ├── main.tscn            # Main scene
│   ├── main.gd              # Main controller
│   ├── car.tscn             # Car scene
│   └── car.gd               # Car logic
│
├── Documentation
│   ├── README.md            # Main documentation
│   ├── QUICKSTART.md        # Quick start guide
│   ├── ARCHITECTURE.md      # Architecture overview
│   ├── ANDROID_PLUGIN_GUIDE.md  # Plugin guide
│   └── DEVELOPMENT.md       # This file
│
└── Android Plugin
    └── android/plugins/car_control/
        ├── build.gradle     # Build configuration
        ├── src/main/java/com/godot/carcontrol/
        │   ├── CarControlPlugin.java    # Main plugin
        │   ├── CarControlActivity.java  # Native UI
        │   └── example/
        │       └── UsageExample.java    # Usage examples
        └── src/main/AndroidManifest.xml
```

### Code Style / 代码风格

#### GDScript

```gdscript
# Use snake_case for variables and functions
var current_speed = 0.0
func move_forward():
    pass

# Use UPPER_CASE for constants
const MAX_SPEED = 20.0

# Document public functions
## Makes the car move forward
## @return: void
func move_forward():
    is_moving_forward = true
```

#### Java

```java
// Use camelCase for variables and methods
private float currentSpeed = 0.0f;
public void moveForward() {
    // Implementation
}

// Use UPPER_CASE for constants
private static final float MAX_SPEED = 20.0f;

// Document public methods
/**
 * Makes the car move forward
 */
public void moveForward() {
    // Implementation
}
```

---

<a name="features"></a>
## 3. Adding New Features / 添加新功能

### Example: Adding Steering Control / 示例：添加转向控制

#### Step 1: Update car.gd

```gdscript
# Add steering variables
var steering_input = 0.0

# Add control functions
func steer_left():
    steering_input = -1.0
    print("Car: Steering left")

func steer_right():
    steering_input = 1.0
    print("Car: Steering right")

func stop_steering():
    steering_input = 0.0
    print("Car: Steering centered")

# Update physics process
func _physics_process(delta):
    # Apply steering to front wheels
    $WheelFrontLeft.steering = steering_input * steering_angle
    $WheelFrontRight.steering = steering_input * steering_angle
    
    # ... rest of physics code
```

#### Step 2: Update main.gd

```gdscript
# Add UI buttons in _ready()
$CanvasLayer/Control/LeftButton.pressed.connect(_on_left_pressed)
$CanvasLayer/Control/LeftButton.button_up.connect(_on_left_released)

$CanvasLayer/Control/RightButton.pressed.connect(_on_right_pressed)
$CanvasLayer/Control/RightButton.button_up.connect(_on_right_released)

# Add callbacks
func _on_left_pressed():
    car.steer_left()
    status_label.text = "Status: Turning Left"

func _on_left_released():
    car.stop_steering()
    status_label.text = "Status: Idle"

# Add Java interface functions
func java_steer_left():
    _on_left_pressed()

func java_steer_right():
    _on_right_pressed()

func java_stop_steering():
    _on_left_released()
```

#### Step 3: Update main.tscn

Add two new buttons to the UI:

```
[node name="LeftButton" type="Button" parent="CanvasLayer/Control"]
# Configure position and appearance
text = "Left ←"

[node name="RightButton" type="Button" parent="CanvasLayer/Control"]
# Configure position and appearance
text = "Right →"
```

#### Step 4: Update CarControlPlugin.java

```java
/**
 * Called from Java to steer left
 */
public void steerLeft() {
    runOnGLThread(new Runnable() {
        @Override
        public void run() {
            godotInstance.callDeferred("java_steer_left");
        }
    });
}

/**
 * Called from Java to steer right
 */
public void steerRight() {
    runOnGLThread(new Runnable() {
        @Override
        public void run() {
            godotInstance.callDeferred("java_steer_right");
        }
    });
}

/**
 * Called from Java to stop steering
 */
public void stopSteering() {
    runOnGLThread(new Runnable() {
        @Override
        public void run() {
            godotInstance.callDeferred("java_stop_steering");
        }
    });
}
```

#### Step 5: Test

1. Run in Godot editor to test basic functionality
2. Export to Android to test Java integration
3. Verify steering works correctly

---

<a name="debugging"></a>
## 4. Debugging / 调试

### Godot Debugging / Godot 调试

```gdscript
# Use print() for simple logging
print("Current speed: ", current_speed)

# Use print_debug() to include file and line number
print_debug("Debug info")

# Use breakpoints in Godot editor
# Click on line number to add breakpoint
```

### Android Debugging / Android 调试

#### Enable USB Debugging / 启用 USB 调试

1. On Android device: Settings -> About Phone -> Tap "Build Number" 7 times
2. Settings -> Developer Options -> Enable "USB Debugging"
3. Connect device to computer

#### View Logs / 查看日志

```bash
# View all logs
adb logcat

# Filter Godot logs
adb logcat | grep Godot

# Filter your plugin logs
adb logcat | grep CarControl

# Clear logs
adb logcat -c
```

#### Android Studio Debugging / Android Studio 调试

1. Open `android/plugins/car_control` in Android Studio
2. Set breakpoints in Java code
3. Attach debugger to running process:
   - Run -> Attach Debugger to Android Process
   - Select your app

### Common Issues / 常见问题

#### Issue: Plugin not loaded / 插件未加载

```bash
# Check plugin configuration
cat android/plugins/car_control/car_control.gdap

# Verify AAR file exists
ls android/plugins/car_control/*.aar

# Check Android export settings in Godot
```

#### Issue: Car not moving / 小车不移动

```gdscript
# Add debug logging in car.gd
func _physics_process(delta):
    print("Speed: ", current_speed)
    print("Forward: ", is_moving_forward)
    print("Accelerating: ", is_accelerating)
    print("Engine Force: ", engine_force)
```

#### Issue: Java calls not working / Java 调用不工作

```java
// Add logging in plugin
public void moveForward() {
    Log.d("CarControl", "moveForward called");
    runOnGLThread(new Runnable() {
        @Override
        public void run() {
            Log.d("CarControl", "Calling Godot");
            godotInstance.callDeferred("java_move_forward");
        }
    });
}
```

---

<a name="testing"></a>
## 5. Testing / 测试

### Manual Testing Checklist / 手动测试清单

- [ ] Car moves forward when Forward button pressed
- [ ] Car moves backward when Backward button pressed
- [ ] Car accelerates when Accelerate button pressed
- [ ] Car brakes when Brake button pressed
- [ ] Car stops when buttons released
- [ ] Reset button works correctly
- [ ] Speed display updates in real-time
- [ ] Status label updates correctly
- [ ] No crashes or errors in console

### Test Scenarios / 测试场景

#### Scenario 1: Basic Movement / 基本移动

1. Press Forward
2. Press Accelerate
3. Wait 2 seconds
4. Release both buttons
5. Verify car moves and stops

#### Scenario 2: Direction Change / 方向改变

1. Press Forward + Accelerate
2. Wait 2 seconds
3. Release both
4. Press Backward + Accelerate
5. Wait 2 seconds
6. Release both

#### Scenario 3: Emergency Stop / 紧急停止

1. Press Forward + Accelerate
2. Build up speed (3 seconds)
3. Release Accelerate
4. Press Brake
5. Verify car stops quickly

---

<a name="tasks"></a>
## 6. Common Tasks / 常见任务

### Adjusting Car Physics / 调整小车物理

```gdscript
# In car.gd
var max_speed = 20.0        # Increase for faster car
var acceleration = 10.0     # Increase for quicker acceleration
var brake_force = 5.0       # Increase for stronger brakes
var steering_angle = 0.5    # Increase for sharper turns
```

### Changing Camera View / 改变摄像机视角

```gdscript
# In car.tscn, select Camera3D node
# Adjust transform:
# - Higher Y position = higher view
# - Larger Z position = further back
# - Rotation X = angle looking down
```

### Customizing UI / 自定义 UI

```gdscript
# In main.tscn, select button nodes
# Adjust properties:
# - theme_override_colors/font_color: Button text color
# - theme_override_font_sizes/font_size: Text size
# - offset_left/right/top/bottom: Button position
```

### Adding Sound Effects / 添加音效

```gdscript
# In car.gd
@onready var engine_sound = $AudioStreamPlayer

func _physics_process(delta):
    # Adjust pitch based on speed
    engine_sound.pitch_scale = 1.0 + abs(current_speed) / max_speed
```

### Building for Release / 发布构建

```bash
# 1. Update version in project.godot
[application]
config/version="1.0.0"

# 2. Update Android version in export_presets.cfg
version/code=1
version/name="1.0"

# 3. Build plugin
cd android/plugins/car_control
./gradlew assembleRelease

# 4. Export from Godot
# Project -> Export -> Android -> Export Project
```

---

## Tips and Best Practices / 技巧和最佳实践

### Performance / 性能

- Use `@onready` for node references to improve startup time
- Cache commonly accessed nodes
- Avoid creating new objects in `_physics_process()`
- Use object pooling for frequently created/destroyed objects

### Code Organization / 代码组织

- Keep related code together
- Use signals for loose coupling
- Document complex logic
- Follow consistent naming conventions

### Version Control / 版本控制

```bash
# Good commit messages
git commit -m "Add steering control functionality"
git commit -m "Fix: Car not stopping when brake released"
git commit -m "Docs: Update README with new features"

# Use branches for features
git checkout -b feature/add-turbo-boost
git checkout -b fix/car-physics-bug
```

---

## Resources / 资源

- [Godot Documentation](https://docs.godotengine.org/)
- [Godot Android Plugin Tutorial](https://docs.godotengine.org/en/stable/tutorials/platform/android/android_plugin.html)
- [VehicleBody3D Class Reference](https://docs.godotengine.org/en/stable/classes/class_vehiclebody3d.html)
- [Android Developer Guide](https://developer.android.com/docs)

---

## Contributing / 贡献

We welcome contributions! Please:

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Test thoroughly
5. Submit a pull request

---

Happy Coding! / 编程愉快！
