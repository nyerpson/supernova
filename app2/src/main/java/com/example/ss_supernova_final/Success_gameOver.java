package com.example.ss_supernova_final;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Success_gameOver extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_success_game_over);

        // BACKGROUND MUSIC
        // type of music is decided by the victory state in Map
        LoopMediaPlayer.stopMediaPlayer();
        LoopMediaPlayer.create(this, GlobalVariables.ambiance);
    }

    //return to home screen
    public void returnToStart(View view){
        Intent my_intent=new Intent(getBaseContext(),MainActivity.class);
        startActivity(my_intent);
    }
}