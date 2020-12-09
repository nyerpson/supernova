package com.example.ss_supernova_final;

import android.util.Log;

public class Animator extends Thread {
    Asteroid_miniGame surfaceActivity;
    boolean isRunning = false;

    public Animator(Asteroid_miniGame activity) {
        surfaceActivity = activity;
    }

    public void run() {
        Log.d("Example", "The animator is running");
        isRunning = true;
        while(isRunning)
        {
            surfaceActivity.draw();
            try {
                sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void finish() {
        isRunning = false;
    }
}
