package com.godot.carcontrol.example;

import android.os.Bundle;
import com.godot.carcontrol.CarControlPlugin;
import org.godotengine.godot.Godot;

/**
 * 使用示例：如何从 Java 代码控制 3D 小车
 * 
 * 这个示例展示了如何在自定义的 Android 代码中使用 CarControlPlugin
 */
public class UsageExample {
    
    private CarControlPlugin carPlugin;
    
    /**
     * 初始化插件
     * @param godot Godot 实例
     */
    public void initialize(Godot godot) {
        carPlugin = new CarControlPlugin(godot);
    }
    
    /**
     * 示例 1: 简单的前进和后退
     */
    public void exampleForwardBackward() {
        // 前进 2 秒
        carPlugin.moveForward();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        // 停止
        carPlugin.stopMovement();
        
        // 后退 2 秒
        carPlugin.moveBackward();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        // 停止
        carPlugin.stopMovement();
    }
    
    /**
     * 示例 2: 加速前进
     */
    public void exampleAccelerateForward() {
        // 开始前进
        carPlugin.moveForward();
        
        // 加速
        carPlugin.accelerate();
        
        // 保持加速 3 秒
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        // 停止加速
        carPlugin.stopSpeedChange();
        
        // 停止移动
        carPlugin.stopMovement();
    }
    
    /**
     * 示例 3: 紧急刹车
     */
    public void exampleEmergencyBrake() {
        // 开始前进并加速
        carPlugin.moveForward();
        carPlugin.accelerate();
        
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        // 紧急刹车
        carPlugin.decelerate();
        
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        // 完全停止
        carPlugin.stopMovement();
        carPlugin.stopSpeedChange();
    }
    
    /**
     * 示例 4: 复位小车
     */
    public void exampleReset() {
        // 重置小车到初始位置
        carPlugin.resetCar();
    }
    
    /**
     * 示例 5: 组合控制 - 前进加速然后后退
     */
    public void exampleCombinedControl() {
        // 1. 前进并加速
        carPlugin.moveForward();
        carPlugin.accelerate();
        
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        // 2. 刹车
        carPlugin.decelerate();
        
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        // 3. 停止并准备后退
        carPlugin.stopMovement();
        carPlugin.stopSpeedChange();
        
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        // 4. 后退
        carPlugin.moveBackward();
        carPlugin.accelerate();
        
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        // 5. 完全停止
        carPlugin.stopMovement();
        carPlugin.stopSpeedChange();
        
        // 6. 重置
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        carPlugin.resetCar();
    }
}
