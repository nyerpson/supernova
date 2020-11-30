package com.example.ss_supernova_final;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Monster_encounter extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monster_encounter);
    }

    //successfully defeat, return to map
    public void finishSuccess(View view){
        Intent my_intent=new Intent(getBaseContext(),Map.class);
        startActivity(my_intent);
    }

    //ran out of lives, game over
    public void fail(View view){
        //lose all lives
        Intent my_intent=new Intent(getBaseContext(),Died_gameOver.class);
        startActivity(my_intent);
    }
}