package com.example.ss_supernova_final;

public class Animator2 extends Thread{

    Monster_encounter surfaceActivity;
    boolean is_running = false;

    public Animator2(Monster_encounter activity){

        surfaceActivity=activity;

    }

    public void run(){

        boolean is_running = true;

        while(is_running){

           surfaceActivity.draw();

            try{
                sleep(50);
            } catch (InterruptedException e){
                e.printStackTrace();
            }

        }

    }

    public void finish(){
        is_running=false;
    }



}
