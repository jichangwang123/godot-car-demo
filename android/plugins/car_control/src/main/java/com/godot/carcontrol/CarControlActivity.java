package com.godot.carcontrol;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.graphics.Color;

/**
 * Native Android Activity for controlling the 3D car
 * This provides an alternative UI using native Android widgets
 */
public class CarControlActivity extends Activity {
    
    private CarControlPlugin carPlugin;
    private TextView statusText;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        // Create main layout
        LinearLayout mainLayout = new LinearLayout(this);
        mainLayout.setOrientation(LinearLayout.VERTICAL);
        mainLayout.setGravity(Gravity.CENTER);
        mainLayout.setPadding(50, 50, 50, 50);
        mainLayout.setBackgroundColor(Color.parseColor("#2C3E50"));
        
        // Status text
        statusText = new TextView(this);
        statusText.setText("Car Control Ready");
        statusText.setTextSize(24);
        statusText.setTextColor(Color.WHITE);
        statusText.setGravity(Gravity.CENTER);
        statusText.setPadding(20, 20, 20, 40);
        mainLayout.addView(statusText);
        
        // Forward button
        Button forwardBtn = createButton("Forward ↑", Color.parseColor("#3498DB"));
        forwardBtn.setOnTouchListener((v, event) -> {
            if (event.getAction() == android.view.MotionEvent.ACTION_DOWN) {
                if (carPlugin != null) {
                    carPlugin.moveForward();
                    statusText.setText("Moving Forward");
                }
            } else if (event.getAction() == android.view.MotionEvent.ACTION_UP) {
                if (carPlugin != null) {
                    carPlugin.stopMovement();
                    statusText.setText("Idle");
                }
            }
            return true;
        });
        mainLayout.addView(forwardBtn);
        
        // Backward button
        Button backwardBtn = createButton("Backward ↓", Color.parseColor("#3498DB"));
        backwardBtn.setOnTouchListener((v, event) -> {
            if (event.getAction() == android.view.MotionEvent.ACTION_DOWN) {
                if (carPlugin != null) {
                    carPlugin.moveBackward();
                    statusText.setText("Moving Backward");
                }
            } else if (event.getAction() == android.view.MotionEvent.ACTION_UP) {
                if (carPlugin != null) {
                    carPlugin.stopMovement();
                    statusText.setText("Idle");
                }
            }
            return true;
        });
        mainLayout.addView(backwardBtn);
        
        // Accelerate button
        Button accelerateBtn = createButton("Accelerate ++", Color.parseColor("#2ECC71"));
        accelerateBtn.setOnTouchListener((v, event) -> {
            if (event.getAction() == android.view.MotionEvent.ACTION_DOWN) {
                if (carPlugin != null) {
                    carPlugin.accelerate();
                    statusText.setText("Accelerating");
                }
            } else if (event.getAction() == android.view.MotionEvent.ACTION_UP) {
                if (carPlugin != null) {
                    carPlugin.stopSpeedChange();
                    statusText.setText("Idle");
                }
            }
            return true;
        });
        mainLayout.addView(accelerateBtn);
        
        // Brake button
        Button brakeBtn = createButton("Brake --", Color.parseColor("#E74C3C"));
        brakeBtn.setOnTouchListener((v, event) -> {
            if (event.getAction() == android.view.MotionEvent.ACTION_DOWN) {
                if (carPlugin != null) {
                    carPlugin.decelerate();
                    statusText.setText("Braking");
                }
            } else if (event.getAction() == android.view.MotionEvent.ACTION_UP) {
                if (carPlugin != null) {
                    carPlugin.stopSpeedChange();
                    statusText.setText("Idle");
                }
            }
            return true;
        });
        mainLayout.addView(brakeBtn);
        
        // Reset button
        Button resetBtn = createButton("Reset", Color.parseColor("#F39C12"));
        resetBtn.setOnClickListener(v -> {
            if (carPlugin != null) {
                carPlugin.resetCar();
                statusText.setText("Car Reset");
            }
        });
        mainLayout.addView(resetBtn);
        
        setContentView(mainLayout);
    }
    
    private Button createButton(String text, int color) {
        Button button = new Button(this);
        button.setText(text);
        button.setTextSize(20);
        button.setTextColor(Color.WHITE);
        button.setBackgroundColor(color);
        button.setPadding(40, 30, 40, 30);
        
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
            LayoutParams.MATCH_PARENT,
            LayoutParams.WRAP_CONTENT
        );
        params.setMargins(0, 0, 0, 20);
        button.setLayoutParams(params);
        
        return button;
    }
    
    public void setCarPlugin(CarControlPlugin plugin) {
        this.carPlugin = plugin;
    }
}
