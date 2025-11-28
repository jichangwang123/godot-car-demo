# Godot Car Demo - Touch-Controlled Node Extension

This repository provides a GDExtension for Godot 4.0+ that includes a touch-controllable 3D node system.

## Features

### Mover Node
A Node3D subclass that responds to touch/swipe input for controlling 3D objects:
- **Two Control Modes**: Rotation and Movement
- **Touch/Swipe Controls**: Intuitive screen-based control
- **Configurable Properties**: Speed and behavior settings
- **Complete Documentation**: XML docs and README

See [demo/README.md](./demo/README.md) for detailed usage instructions.

## Quick Start

### Prerequisites
- Python 3.6+
- SCons (`pip install scons`)
- C++ compiler (GCC, Clang, or MSVC)
- Godot 4.0+

### Building

1. Clone with submodules:
```shell
git clone --recursive https://github.com/jichangwang123/godot-car-demo.git
cd godot-car-demo
```

2. Build the extension:
```shell
# Linux
scons platform=linux target=template_debug

# Windows
scons platform=windows target=template_debug

# macOS
scons platform=macos target=template_debug
```

3. Open `demo/project.godot` in Godot and run!

## Project Structure

### Source Files
* `src/Mover/` - Touch-controlled Mover node implementation
* `src/example_class.*` - Example class from template
* `src/register_types.*` - GDExtension registration

### Demo Project
* `demo/` - Complete Godot project with test scene
* `demo/bin/` - Compiled libraries and .gdextension file
* `demo/README.md` - Detailed usage guide

### Documentation
* `doc_classes/` - XML documentation for Godot classes
* `SECURITY_SUMMARY.md` - Security analysis and recommendations

## Contents
* Preconfigured source files for C++ development of the GDExtension ([src/](./src/))
* A working Godot demo project in [demo/](./demo) to test the GDExtension
* godot-cpp as a submodule (`godot-cpp/`)
* Mover node - touch-controllable 3D node
* Complete documentation and examples

## Development

### Configuring an IDE
Generate a compilation database for IDE support:
```shell
scons compiledb=yes
```

### Adding Features
1. Create new class files in `src/`
2. Register in `src/register_types.cpp`
3. Add to build files (`SConstruct`, `CMakeLists.txt`)
4. Document in `doc_classes/`

## CI/CD

This repository includes GitHub Actions workflows for cross-platform builds. Check the Actions tab for automated build artifacts.

## License

MIT License - See LICENSE.md for details.

## Using as a Template

You can use this as a template for your own GDExtension projects:
1. Click "Use this template" on GitHub
2. Modify the library name in `SConstruct`
3. Update the `.gdextension` file
4. Implement your own nodes and classes

For more template usage details, see the original godot-cpp-template documentation.
