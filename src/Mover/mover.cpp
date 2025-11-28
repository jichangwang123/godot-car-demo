#include "mover.h"
#include "godot_cpp/core/class_db.hpp"
#include "godot_cpp/variant/utility_functions.hpp"

Mover::Mover() {
move_speed = 5.0f;
rotation_speed = 1.0f;
is_touching = false;
last_touch_position = Vector2(0, 0);
control_mode = 0; // Default to rotation mode
}

Mover::~Mover() {
}

void Mover::_bind_methods() {
// Bind property setters and getters
ClassDB::bind_method(D_METHOD("set_move_speed", "speed"), &Mover::set_move_speed);
ClassDB::bind_method(D_METHOD("get_move_speed"), &Mover::get_move_speed);
ClassDB::bind_method(D_METHOD("set_rotation_speed", "speed"), &Mover::set_rotation_speed);
ClassDB::bind_method(D_METHOD("get_rotation_speed"), &Mover::get_rotation_speed);
ClassDB::bind_method(D_METHOD("set_control_mode", "mode"), &Mover::set_control_mode);
ClassDB::bind_method(D_METHOD("get_control_mode"), &Mover::get_control_mode);

// Register properties
ADD_PROPERTY(PropertyInfo(Variant::FLOAT, "move_speed", PROPERTY_HINT_RANGE, "0.1,100.0,0.1"), "set_move_speed", "get_move_speed");
ADD_PROPERTY(PropertyInfo(Variant::FLOAT, "rotation_speed", PROPERTY_HINT_RANGE, "0.1,10.0,0.1"), "set_rotation_speed", "get_rotation_speed");
ADD_PROPERTY(PropertyInfo(Variant::INT, "control_mode", PROPERTY_HINT_ENUM, "Rotate,Move"), "set_control_mode", "get_control_mode");
}

// Setters and Getters
void Mover::set_move_speed(float p_speed) {
move_speed = p_speed;
}

float Mover::get_move_speed() const {
return move_speed;
}

void Mover::set_rotation_speed(float p_speed) {
rotation_speed = p_speed;
}

float Mover::get_rotation_speed() const {
return rotation_speed;
}

void Mover::set_control_mode(int p_mode) {
control_mode = p_mode;
}

int Mover::get_control_mode() const {
return control_mode;
}

// Input handling
void Mover::_input(const Ref<InputEvent> &p_event) {
// Handle screen touch
Ref<InputEventScreenTouch> touch = p_event;
if (touch.is_valid()) {
if (touch->is_pressed()) {
handle_touch_begin(touch->get_position());
} else {
handle_touch_end();
}
return;
}

// Handle screen drag
Ref<InputEventScreenDrag> drag = p_event;
if (drag.is_valid() && is_touching) {
handle_touch_drag(drag->get_position(), drag->get_relative());
return;
}
}

void Mover::_process(double p_delta) {
// This can be used for smooth interpolation if needed
}

void Mover::handle_touch_begin(const Vector2 &p_position) {
is_touching = true;
last_touch_position = p_position;
UtilityFunctions::print("Touch began at: ", p_position);
}

void Mover::handle_touch_drag(const Vector2 &p_position, const Vector2 &p_relative) {
if (!is_touching) {
return;
}

if (control_mode == 0) {
// Rotation mode: rotate the node based on drag
Vector3 current_rotation = get_rotation();

// Horizontal drag rotates around Y axis (yaw)
float delta_yaw = -p_relative.x * rotation_speed * 0.01f;

// Vertical drag rotates around X axis (pitch)
float delta_pitch = -p_relative.y * rotation_speed * 0.01f;

current_rotation.y += delta_yaw;
current_rotation.x += delta_pitch;

// Clamp pitch to avoid gimbal lock
current_rotation.x = Math::clamp(current_rotation.x, -1.5f, 1.5f);

set_rotation(current_rotation);

UtilityFunctions::print("Rotating node: ", current_rotation);
} else {
// Move mode: translate the node based on drag
Vector3 current_position = get_position();

// Horizontal drag moves along X axis
float delta_x = p_relative.x * move_speed * 0.01f;

// Vertical drag moves along Z axis (forward/backward)
float delta_z = p_relative.y * move_speed * 0.01f;

current_position.x += delta_x;
current_position.z += delta_z;

set_position(current_position);

UtilityFunctions::print("Moving node: ", current_position);
}

last_touch_position = p_position;
}

void Mover::handle_touch_end() {
is_touching = false;
UtilityFunctions::print("Touch ended");
}
