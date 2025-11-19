package com.godot.carcontrol;

import org.godotengine.godot.Godot;
import org.godotengine.godot.plugin.GodotPlugin;
import org.godotengine.godot.plugin.SignalInfo;
import androidx.annotation.NonNull;
import java.util.Set;

/**
 * Godot Android Plugin for Car Control
 * This plugin allows Java code to control the 3D car in Godot
 */
public class CarControlPlugin extends GodotPlugin {
    
    private Godot godotInstance;
    
    public CarControlPlugin(Godot godot) {
        super(godot);
        this.godotInstance = godot;
    }
    
    @NonNull
    @Override
    public String getPluginName() {
        return "CarControlPlugin";
    }
    
    /**
     * Called from Java to make the car move forward
     */
    public void moveForward() {
        runOnGLThread(new Runnable() {
            @Override
            public void run() {
                godotInstance.callDeferred("java_move_forward");
            }
        });
    }
    
    /**
     * Called from Java to make the car move backward
     */
    public void moveBackward() {
        runOnGLThread(new Runnable() {
            @Override
            public void run() {
                godotInstance.callDeferred("java_move_backward");
            }
        });
    }
    
    /**
     * Called from Java to stop the car movement
     */
    public void stopMovement() {
        runOnGLThread(new Runnable() {
            @Override
            public void run() {
                godotInstance.callDeferred("java_stop_movement");
            }
        });
    }
    
    /**
     * Called from Java to accelerate the car
     */
    public void accelerate() {
        runOnGLThread(new Runnable() {
            @Override
            public void run() {
                godotInstance.callDeferred("java_accelerate");
            }
        });
    }
    
    /**
     * Called from Java to decelerate/brake the car
     */
    public void decelerate() {
        runOnGLThread(new Runnable() {
            @Override
            public void run() {
                godotInstance.callDeferred("java_decelerate");
            }
        });
    }
    
    /**
     * Called from Java to stop speed changes
     */
    public void stopSpeedChange() {
        runOnGLThread(new Runnable() {
            @Override
            public void run() {
                godotInstance.callDeferred("java_stop_speed_change");
            }
        });
    }
    
    /**
     * Called from Java to reset the car
     */
    public void resetCar() {
        runOnGLThread(new Runnable() {
            @Override
            public void run() {
                godotInstance.callDeferred("java_reset");
            }
        });
    }
    
    /**
     * Helper method to run code on GL thread
     */
    private void runOnGLThread(Runnable runnable) {
        godotInstance.runOnUiThread(runnable);
    }
}
