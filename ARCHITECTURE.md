# Architecture Overview / 架构概览

## System Architecture / 系统架构

```
┌─────────────────────────────────────────────────────────┐
│                    Android Application                   │
│                      (User Interface)                    │
└───────────────────────┬─────────────────────────────────┘
                        │
                        │ Java API Calls
                        │
┌───────────────────────▼─────────────────────────────────┐
│              CarControlPlugin.java                       │
│           (Godot Android Plugin / Bridge)                │
│                                                           │
│  Methods:                                                 │
│  - moveForward()                                          │
│  - moveBackward()                                         │
│  - accelerate()                                           │
│  - decelerate()                                           │
│  - resetCar()                                             │
└───────────────────────┬─────────────────────────────────┘
                        │
                        │ JNI / Godot CallDeferred
                        │
┌───────────────────────▼─────────────────────────────────┐
│                    main.gd                               │
│              (Godot Main Controller)                     │
│                                                           │
│  Java Interface Methods:                                 │
│  - java_move_forward()                                   │
│  - java_move_backward()                                  │
│  - java_accelerate()                                     │
│  - java_decelerate()                                     │
│  - java_reset()                                          │
└───────────────────────┬─────────────────────────────────┘
                        │
                        │ Direct Function Calls
                        │
┌───────────────────────▼─────────────────────────────────┐
│                     car.gd                               │
│               (3D Car Controller)                        │
│                                                           │
│  Control Methods:                                        │
│  - move_forward()                                        │
│  - move_backward()                                       │
│  - stop_movement()                                       │
│  - accelerate()                                          │
│  - decelerate()                                          │
│  - stop_speed_change()                                   │
│  - reset_car()                                           │
│                                                           │
│  Physics Properties:                                     │
│  - current_speed                                         │
│  - is_accelerating                                       │
│  - is_moving_forward                                     │
└───────────────────────┬─────────────────────────────────┘
                        │
                        │ Physics Engine Updates
                        │
┌───────────────────────▼─────────────────────────────────┐
│              VehicleBody3D (car.tscn)                    │
│                 (3D Physics Object)                      │
│                                                           │
│  Components:                                             │
│  - Car Body (MeshInstance3D)                             │
│  - 4 Wheels (VehicleWheel3D)                             │
│  - Collision Shape                                       │
│  - Camera                                                │
│                                                           │
│  Physics:                                                │
│  - Mass: 500kg                                           │
│  - Engine Force                                          │
│  - Steering                                              │
└─────────────────────────────────────────────────────────┘
```

## Data Flow / 数据流

### Control Flow (User -> Car) / 控制流（用户 -> 小车）

```
User Presses Button
       │
       ▼
Android UI (Button)
       │
       ▼
CarControlPlugin.moveForward()
       │
       ▼
Godot.callDeferred("java_move_forward")
       │
       ▼
main.gd.java_move_forward()
       │
       ▼
car.gd.move_forward()
       │
       ▼
Set is_moving_forward = true
       │
       ▼
_physics_process() calculates speed
       │
       ▼
VehicleBody3D applies engine_force
       │
       ▼
Car Moves!
```

### UI Update Flow (Car -> Display) / UI 更新流（小车 -> 显示）

```
VehicleBody3D (current state)
       │
       ▼
car.gd._physics_process()
       │
       ▼
Updates current_speed
       │
       ▼
main.gd._process()
       │
       ▼
car.get_current_speed()
       │
       ▼
Update SpeedLabel.text
       │
       ▼
Display on Screen
```

## Component Details / 组件详情

### 1. VehicleBody3D (Physics Layer)

- **Purpose**: Provides realistic vehicle physics simulation
- **Properties**:
  - Mass: 500 kg
  - Engine force: Applied based on current_speed
  - Friction: Handled by wheel physics
  - Gravity: Standard physics

### 2. car.gd (Logic Layer)

- **Purpose**: Manages car state and controls
- **State Variables**:
  - `current_speed`: Current velocity
  - `is_moving_forward/backward`: Direction state
  - `is_accelerating/decelerating`: Speed change state
- **Parameters**:
  - `max_speed`: 20.0 m/s
  - `acceleration`: 10.0 m/s²
  - `brake_force`: 5.0 m/s²

### 3. main.gd (Controller Layer)

- **Purpose**: Bridges UI and car logic
- **Responsibilities**:
  - Handle button signals
  - Update UI labels
  - Provide Java interface
  - Manage scene state

### 4. CarControlPlugin.java (Bridge Layer)

- **Purpose**: Enable Java-Godot communication
- **Method**: Uses Godot's plugin system
- **Thread Safety**: Ensures GL thread execution
- **Communication**: JNI + CallDeferred pattern

### 5. Android UI (Presentation Layer)

- **Two Implementations**:
  1. Godot UI (main.tscn): Built-in Godot Control nodes
  2. Native UI (CarControlActivity.java): Pure Android widgets
- **Features**:
  - Touch/press event handling
  - Real-time status display
  - Responsive layout

## Threading Model / 线程模型

```
┌─────────────────────────────────────────────────┐
│            Android Main Thread                   │
│  - UI Events                                     │
│  - Button Presses                                │
└────────────────┬────────────────────────────────┘
                 │
                 │ runOnUiThread()
                 │
┌────────────────▼────────────────────────────────┐
│            Godot GL Thread                       │
│  - callDeferred() calls                          │
│  - Scene tree operations                         │
│  - _physics_process()                            │
│  - _process()                                    │
└──────────────────────────────────────────────────┘
```

**Important**: All Godot operations must run on the GL thread. The plugin uses `runOnUiThread()` and `callDeferred()` to ensure thread safety.

## Extension Points / 扩展点

### 1. Add Steering Control / 添加转向控制

```gdscript
# In car.gd
var steering_input = 0.0

func steer_left():
    steering_input = -1.0

func steer_right():
    steering_input = 1.0

func _physics_process(delta):
    $WheelFrontLeft.steering = steering_input * steering_angle
    $WheelFrontRight.steering = steering_input * steering_angle
```

### 2. Add Speed Limiter / 添加速度限制器

```gdscript
# In car.gd
var speed_limit_enabled = false
var speed_limit = 10.0

func _physics_process(delta):
    if speed_limit_enabled:
        current_speed = clamp(current_speed, -speed_limit, speed_limit)
```

### 3. Add Sensors / 添加传感器

```java
// In CarControlPlugin.java
public float getCurrentSpeed() {
    // Call GDScript function and return value
    return (float) godotInstance.callv("get_car_speed", new Object[]{});
}
```

### 4. Add Multiplayer / 添加多人游戏

- Use Godot's high-level multiplayer API
- Sync car position and state across network
- Add player identification

## Performance Considerations / 性能考虑

1. **Physics Updates**: 60 FPS (_physics_process)
2. **UI Updates**: 60 FPS (_process)
3. **Java Calls**: Asynchronous via callDeferred
4. **Memory**: Minimal overhead from plugin

## Best Practices / 最佳实践

1. **Always use callDeferred** when calling from Java
2. **Handle button release** to stop actions
3. **Reset state** properly to avoid bugs
4. **Test on target device** for performance
5. **Use logging** for debugging Java-Godot communication
