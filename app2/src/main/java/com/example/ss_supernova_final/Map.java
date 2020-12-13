package com.example.ss_supernova_final;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.Random;

public class Map extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
    }

    @Override
    public void onResume() {
        super.onResume();

        if(GlobalVariables.winState==6) {
            finish();
            Intent my_intent=new Intent(getBaseContext(), Died_gameOver.class);
            my_intent.putExtra("caption","You Win!");
            startActivity(my_intent);
        }
        if(GlobalVariables.gameOver) {
            finish();
            Intent my_intent=new Intent(getBaseContext(), Died_gameOver.class);
            my_intent.putExtra("caption","Game Over");
            startActivity(my_intent);
        }
    }

    // Randomize activity
    public void randomizeMinigame(View view){
        Random rand = new Random();
        /* // Method 1
           // Add more cases and change the random bound to adjust probability
                    // Current probabilities give 2/7 chance for any normal minigames, and 1/7 for monster encounter
                    // Randoms include 0 and exclude the bound
           // Uses the fallthrough feature of switch statements
        int n = rand.nextInt(7);
        switch(n) {
            case 1: case 2:
                goToAsteroid(view);
                break;
            case 3: case 4:
                goToMemory(view);
                break;
            case 5: case 6:
                goToWires(view);
                break;
            default:    // if 0
                goToMonster(view);
        }
         */

        // Method 2
        // Change the if values and the random bound to adjust probability
                // Current probabilities give 3/10 chance for any normal minigames, and 1/10 for monster encounter
                // Randoms include 0 and exclude the bound
        int n = rand.nextInt(10);
        if(n < 3) { goToAsteroid(view); }        // if n = 0, 1, 2
        else if(n < 6) { goToMemory(view); }     // if n = 3, 4, 5
        else if(n < 9) { goToWires(view); }      // if n = 6, 7, 8
        else { goToMonster(view); }            // if n = 9
    }

    // Go to asteroid game
    public void goToAsteroid(View view){
        Button b = (Button)view;
        Intent my_intent=new Intent(getBaseContext(),Asteroid_miniGame.class);
        my_intent.putExtra("sectionTitle", b.getText());
        startActivity(my_intent);
    }
    // Go to memory game
    public void goToMemory(View view){
        Button b = (Button)view;
        Intent my_intent=new Intent(getBaseContext(),Memory_miniGame.class);
        my_intent.putExtra("sectionTitle", b.getText());
        startActivity(my_intent);
    }
    // Go to wires game
    public void goToWires(View view){
        Button b = (Button)view;
        Intent my_intent=new Intent(getBaseContext(),Wires_miniGame.class);
        my_intent.putExtra("sectionTitle", b.getText());
        startActivity(my_intent);
    }
    // Go to monster game
    public void goToMonster(View view){
        Intent my_intent=new Intent(getBaseContext(),Monster_encounter.class);
        startActivity(my_intent);
    }
}