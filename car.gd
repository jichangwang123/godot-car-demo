extends VehicleBody3D

# Car movement parameters
var max_speed = 20.0
var acceleration = 10.0
var brake_force = 5.0
var steering_angle = 0.5

# Current state
var current_speed = 0.0
var is_accelerating = false
var is_decelerating = false
var is_moving_forward = false
var is_moving_backward = false

func _ready():
	# Set up the vehicle physics
	mass = 500.0
	
func _physics_process(delta):
	# Handle forward/backward movement
	var direction = 0.0
	if is_moving_forward:
		direction = 1.0
	elif is_moving_backward:
		direction = -1.0
	
	# Handle acceleration/deceleration
	if is_accelerating and direction != 0.0:
		current_speed = move_toward(current_speed, max_speed * direction, acceleration * delta)
	elif is_decelerating:
		current_speed = move_toward(current_speed, 0.0, brake_force * delta)
	else:
		# Natural deceleration
		current_speed = move_toward(current_speed, 0.0, brake_force * 0.5 * delta)
	
	# Apply engine force
	engine_force = current_speed * 50.0

# Functions to be called from UI or Java
func move_forward():
	is_moving_forward = true
	is_moving_backward = false
	print("Car: Moving forward")

func move_backward():
	is_moving_backward = true
	is_moving_forward = false
	print("Car: Moving backward")

func stop_movement():
	is_moving_forward = false
	is_moving_backward = false
	print("Car: Stopped movement")

func accelerate():
	is_accelerating = true
	is_decelerating = false
	print("Car: Accelerating")

func decelerate():
	is_decelerating = true
	is_accelerating = false
	print("Car: Decelerating")

func stop_speed_change():
	is_accelerating = false
	is_decelerating = false
	print("Car: No speed change")

func get_current_speed():
	return current_speed

func reset_car():
	current_speed = 0.0
	is_accelerating = false
	is_decelerating = false
	is_moving_forward = false
	is_moving_backward = false
	linear_velocity = Vector3.ZERO
	angular_velocity = Vector3.ZERO
