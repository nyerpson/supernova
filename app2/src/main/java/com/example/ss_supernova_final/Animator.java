package com.example.ss_supernova_final;

public class Animator extends Thread{

    Asteroid_miniGame surfaceActivity;
    boolean is_running=false;

    public Animator(Asteroid_miniGame activity){
        surfaceActivity=activity;
    }

    public void run(){
        is_running=true;

        while(is_running){

            surfaceActivity.draw();

            try {
                sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


        }
    }

    public void finish(){
        is_running=false;
    }

}
