package com.example.ss_supernova_final;

import android.util.Log;

public class Animator extends Thread {
    Memory_miniGame surfaceActivity;
    boolean isRunning = false;

    public Animator(Memory_miniGame activity) {
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