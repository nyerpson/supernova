package com.example.ss_supernova_final;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Asteroid_miniGame extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asteroid_mini_game);
    }

    public void finishSuccess(View view){
        //return to map
        Intent my_intent=new Intent(getBaseContext(),Map.class);
        startActivity(my_intent);
    }

    public void fail(View view){
        //go to monster
        Intent my_intent=new Intent(getBaseContext(),Monster_encounter.class);
        startActivity(my_intent);
    }
}