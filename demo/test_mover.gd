extends Node3D

func _ready():
	print("Mover demo started!")
	print("Swipe on the screen to control the cube.")
	print("Mode 0 (default): Rotate the cube")
	print("Mode 1: Move the cube")
	
	# Get the Mover node
	var mover = $Mover
	if mover:
		print("Mover node found!")
		print("Initial control mode: ", mover.control_mode)
		print("Move speed: ", mover.move_speed)
		print("Rotation speed: ", mover.rotation_speed)
