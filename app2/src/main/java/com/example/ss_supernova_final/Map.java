package com.example.ss_supernova_final;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class Map extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

    }


    @Override
    public void onResume() {
        super.onResume();

        if(GlobalVariables.winState==6){
            finish();
            Intent my_intent=new Intent(getBaseContext(),Success_gameOver.class);
            //my_intent.putExtra("caption","You Win!");
            startActivity(my_intent);

        }

        if(GlobalVariables.gameOver==true){

            finish();

            Intent my_intent=new Intent(getBaseContext(),Died_gameOver.class);
            //my_intent.putExtra("caption","Game Over");
            startActivity(my_intent);

        }


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