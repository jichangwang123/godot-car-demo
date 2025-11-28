# Godot Car Demo - Touch-Controlled Node Extension

This project provides a GDExtension for Godot 4.x that includes a `Mover` node for touch/swipe-based control of 3D objects.

## Features

The `Mover` class extends `Node3D` and provides:

- **Touch/Swipe Input Support**: Control nodes using screen touch and drag gestures
- **Two Control Modes**:
  - **Rotation Mode (0)**: Swipe to rotate the node (default)
  - **Movement Mode (1)**: Swipe to move the node
- **Configurable Properties**:
  - `move_speed`: Controls the movement speed (default: 5.0)
  - `rotation_speed`: Controls the rotation speed (default: 1.0)
  - `control_mode`: Switch between rotation (0) and movement (1) modes

## Building the Extension

### Prerequisites

- Python 3.6+
- SCons build tool
- C++ compiler (GCC, Clang, or MSVC)
- Git (for submodules)

### Build Steps

1. Clone the repository with submodules:
```bash
git clone --recursive https://github.com/jichangwang123/godot-car-demo.git
cd godot-car-demo
```

2. If you already cloned without `--recursive`, initialize the submodule:
```bash
git submodule update --init --recursive
```

3. Install SCons if not already installed:
```bash
pip install scons
```

4. Build the extension:
```bash
# For Linux debug build
scons platform=linux target=template_debug

# For Linux release build
scons platform=linux target=template_release

# For Windows debug build (on Windows)
scons platform=windows target=template_debug

# For macOS debug build (on macOS)
scons platform=macos target=template_debug
```

The compiled libraries will be placed in:
- `bin/<platform>/` - Build output
- `demo/bin/<platform>/` - Copy for the demo project

## Using the Mover Node

### In the Godot Editor

1. Open the `demo/project.godot` in Godot 4.x
2. The `Mover` node will be available in the node creation menu under Node3D
3. Add a `Mover` node to your scene
4. Add a visual child node (e.g., MeshInstance3D) to see the movement

### Configuration

Select the Mover node in the editor to configure:

- **Move Speed**: How fast the node moves (0.1 - 100.0)
- **Rotation Speed**: How fast the node rotates (0.1 - 10.0)
- **Control Mode**: 
  - `Rotate`: Swipe to rotate the node
  - `Move`: Swipe to move the node

### Input Controls

- **Touch/Click and Drag**: 
  - In rotation mode: Horizontal drag rotates around Y-axis (yaw), vertical drag rotates around X-axis (pitch)
  - In movement mode: Horizontal drag moves along X-axis, vertical drag moves along Z-axis

### Code Example

```gdscript
extends Node3D

func _ready():
    # Get the Mover node
    var mover = $Mover
    
    # Configure the mover
    mover.control_mode = 0  # Rotation mode
    mover.rotation_speed = 2.0
    mover.move_speed = 10.0
    
    # Switch to movement mode after 5 seconds
    await get_tree().create_timer(5.0).timeout
    mover.control_mode = 1  # Movement mode
```

## Demo Project

The `demo/` directory contains a simple test scene:
- `main.tscn`: A scene with a Mover node and a cube
- `test_mover.gd`: A script that prints information about the Mover node

To run the demo:
1. Open `demo/project.godot` in Godot
2. Press F5 to run the project
3. Touch/click and drag to control the cube

## Architecture

### Source Files

- `src/Mover/mover.h`: Mover class header
- `src/Mover/mover.cpp`: Mover class implementation
- `src/register_types.cpp`: GDExtension registration
- `src/example_class.cpp`: Example class from template

### Input Handling

The Mover node handles three types of input events:
1. `InputEventScreenTouch`: Detects touch begin/end
2. `InputEventScreenDrag`: Detects swipe/drag motion
3. Also works with mouse input on desktop

### Properties

All properties are exposed to Godot and can be modified in:
- The Inspector panel
- GDScript code
- Other GDExtensions

## Development

### Adding New Features

1. Edit the source files in `src/Mover/`
2. Rebuild using `scons`
3. Test in the Godot editor

### Debugging

Build with debug symbols:
```bash
scons platform=linux target=template_debug
```

Use Godot's built-in debugger and the `UtilityFunctions::print()` calls in the code.

## Troubleshooting

### Error: "GDExtension dynamic library not found"

If you see an error like:
```
ERROR: GDExtension dynamic library not found: 'res://bin/mover.gdextension'.
```

**Solution**: You need to build the extension for your platform first.

1. Make sure you have initialized the godot-cpp submodule:
   ```bash
   git submodule update --init --recursive
   ```

2. Build the extension for your platform:
   ```bash
   # On Windows (requires MSVC or MinGW)
   scons platform=windows target=template_debug
   
   # On Linux
   scons platform=linux target=template_debug
   
   # On macOS
   scons platform=macos target=template_debug
   ```

3. After building, the library will be copied to `demo/bin/<platform>/`
4. Restart Godot and reload the project

**Note**: The `entry_symbol` in the `.gdextension` file is correct and doesn't need to be changed.

### Missing Libraries After Clone

The `demo/bin/<platform>/` directories only contain `.gitkeep` files by default. You **must** build the extension for your platform before running the demo project in Godot.

### Build Errors

If you encounter build errors:
- Make sure you have the required compiler installed (MSVC on Windows, GCC/Clang on Linux/macOS)
- Install SCons: `pip install scons`
- Make sure godot-cpp submodule is initialized
- Check that Python 3.6+ is installed

## License

This project uses the MIT License. See LICENSE.md for details.

## Contributing

Contributions are welcome! Please feel free to submit issues or pull requests.
