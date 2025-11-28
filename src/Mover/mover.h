#pragma once

#include "godot_cpp/classes/node3d.hpp"
#include "godot_cpp/classes/input_event.hpp"
#include "godot_cpp/classes/input_event_screen_drag.hpp"
#include "godot_cpp/classes/input_event_screen_touch.hpp"
#include "godot_cpp/variant/vector2.hpp"
#include "godot_cpp/variant/vector3.hpp"

using namespace godot;

class Mover : public Node3D {
GDCLASS(Mover, Node3D)

private:
// Movement properties
float move_speed;
float rotation_speed;
bool is_touching;
Vector2 last_touch_position;

// Mode: 0 = rotate node, 1 = move node
int control_mode;

protected:
static void _bind_methods();

public:
Mover();
~Mover() override;

// Getters and Setters
void set_move_speed(float p_speed);
float get_move_speed() const;

void set_rotation_speed(float p_speed);
float get_rotation_speed() const;

void set_control_mode(int p_mode);
int get_control_mode() const;

// Input handling
void _input(const Ref<InputEvent> &p_event) override;
void _process(double p_delta) override;

// Touch input handlers
void handle_touch_begin(const Vector2 &p_position);
void handle_touch_drag(const Vector2 &p_position, const Vector2 &p_relative);
void handle_touch_end();
};
