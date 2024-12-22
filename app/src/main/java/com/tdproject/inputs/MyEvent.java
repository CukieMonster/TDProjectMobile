package com.tdproject.inputs;

import android.view.MotionEvent;

// This class has specific Android functionality
public class MyEvent {

    private MotionEvent event;

    public MyEvent(MotionEvent event) {
        this.event = event;
    }

    public int getX() {
        return (int) event.getX();
    }

    public int getY() {
        return (int) event.getY();
    }
}
