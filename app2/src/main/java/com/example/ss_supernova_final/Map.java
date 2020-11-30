package com.example.ss_supernova_final;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Map extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
    }

    //randomize activity
    public void randomizeMinigame(View view){

    }

    //PLACEHOLDER - go to asteroid game
    public void goToAsteroid(View view){
        Intent my_intent=new Intent(getBaseContext(),Asteroid_miniGame.class);
        startActivity(my_intent);
    }

    //PLACEHOLDER - go to memory game
    public void goToMemory(View view){
        Intent my_intent=new Intent(getBaseContext(),Memory_miniGame.class);
        startActivity(my_intent);
    }

    //PLACEHOLDER - go to wires game
    public void goToWires(View view){
        Intent my_intent=new Intent(getBaseContext(),Wires_miniGame.class);
        startActivity(my_intent);
    }
}