extends Node3D

# Reference to the car
@onready var car = $Car

# UI Labels
@onready var status_label = $CanvasLayer/Control/StatusLabel
@onready var speed_label = $CanvasLayer/Control/SpeedLabel

func _ready():
	print("Main scene ready")
	# Connect button signals
	$CanvasLayer/Control/ForwardButton.pressed.connect(_on_forward_pressed)
	$CanvasLayer/Control/ForwardButton.button_up.connect(_on_forward_released)
	
	$CanvasLayer/Control/BackwardButton.pressed.connect(_on_backward_pressed)
	$CanvasLayer/Control/BackwardButton.button_up.connect(_on_backward_released)
	
	$CanvasLayer/Control/AccelerateButton.pressed.connect(_on_accelerate_pressed)
	$CanvasLayer/Control/AccelerateButton.button_up.connect(_on_accelerate_released)
	
	$CanvasLayer/Control/DecelerateButton.pressed.connect(_on_decelerate_pressed)
	$CanvasLayer/Control/DecelerateButton.button_up.connect(_on_decelerate_released)
	
	$CanvasLayer/Control/ResetButton.pressed.connect(_on_reset_pressed)

func _process(_delta):
	# Update speed display
	var current_speed = car.get_current_speed()
	speed_label.text = "Speed: %.1f" % abs(current_speed)

# Button callbacks
func _on_forward_pressed():
	car.move_forward()
	status_label.text = "Status: Moving Forward"

func _on_forward_released():
	car.stop_movement()
	status_label.text = "Status: Idle"

func _on_backward_pressed():
	car.move_backward()
	status_label.text = "Status: Moving Backward"

func _on_backward_released():
	car.stop_movement()
	status_label.text = "Status: Idle"

func _on_accelerate_pressed():
	car.accelerate()
	status_label.text = "Status: Accelerating"

func _on_accelerate_released():
	car.stop_speed_change()
	status_label.text = "Status: Idle"

func _on_decelerate_pressed():
	car.decelerate()
	status_label.text = "Status: Braking"

func _on_decelerate_released():
	car.stop_speed_change()
	status_label.text = "Status: Idle"

func _on_reset_pressed():
	car.reset_car()
	car.transform.origin = Vector3(0, 2, 0)
	car.rotation = Vector3.ZERO
	status_label.text = "Status: Reset"

# Functions that can be called from Java/Android
func java_move_forward():
	_on_forward_pressed()

func java_move_backward():
	_on_backward_pressed()

func java_stop_movement():
	_on_forward_released()

func java_accelerate():
	_on_accelerate_pressed()

func java_decelerate():
	_on_decelerate_pressed()

func java_stop_speed_change():
	_on_accelerate_released()

func java_reset():
	_on_reset_pressed()
