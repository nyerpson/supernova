package com.example.ss_supernova_final;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;

public class Memory_miniGame extends AppCompatActivity implements SurfaceHolder.Callback {

            /*
    public void setMiniGameBundles(String gameId, int imageId, String titleText) {
        Intent intent = new Intent(getBaseContext(), gameId.class);
        //intent.putExtra("image", imageId);
        intent.putExtra("title", titleText);
        // Any other extras to send (how much health is remaining, if that's tracked in Main/Map? you can make that a global instead too)
        startActivity(intent);
    }
     */

    SurfaceHolder holder = null;

    // Design Elements
    Button b1;
    Button b2;
    Button b3;
    Button b4;
    Button b5;
    Button b6;
    Button b7;
    Button b8;
    Button b9;

    // SurfaceView Elements
    Paint health_color;
    Paint level_color;

    // Other
    int level;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memory_mini_game);

        /*
        Bundle extras = getIntent().getExtras();
        if(extras!=null) {
            hintImage = findViewById(R.id.hintImage);
            hintImage.setImageResource(extras.getInt("image"));
            hintDesc = findViewById(R.id.hintDesc);
            hintDesc.setText(extras.getString("text"));
            bottomButton = findViewById(R.id.buttonHintBack);
            bottomButton.setText(extras.getString("bottomButton"));
        }
         */

        // Design Elements
        b1 = findViewById(R.id.b1);
        b2 = findViewById(R.id.b2);
        b3 = findViewById(R.id.b3);
        b4 = findViewById(R.id.b4);
        b5 = findViewById(R.id.b5);
        b6 = findViewById(R.id.b6);
        b7 = findViewById(R.id.b7);
        b8 = findViewById(R.id.b8);
        b9 = findViewById(R.id.b9);

        health_color = new Paint();
        health_color.setColor(Color.RED);
        level_color = new Paint();
        level_color.setColor(Color.WHITE);

        SurfaceView surface = findViewById(R.id.surfaceView);
        surface.getHolder().addCallback(this);

        // Initialize Game
        level = 1;
        // get extras bundles for things like the section title
        flashSequence(generateSequence(level));
    }

    public int[] generateSequence(int level) {
        /* Steps
        1. choose a numbered sequence of randomized(?) squares
            - create arrays of different orders?
            - randomize, or have a few sets pre-made?
            - need different length sequences to choose from for each "level"
            - allow for pressing certain keys multiple times? Or just every button can only be pressed once?
        2. flash said sequence for a few seconds for the player to memorize
            - momentarily change the color/number of the button, then revert?
            - need to count time (pause timer while flashing?)
        3. keep track of player input to determine if the sequence was correct
            - gray out already-pushed buttons, or not?
            a. if the player gets the sequence wrong, reset to step 2
            b. if the player gets the sequence right, repeat from step 1 with a more difficult sequence

        FIRST RUN:
        - No double dipping, but don't gray out buttons after being pressed
            - idk how to do a Simon Says type thing, so I'll do just a static color/number change for now
        - Art buttons will have no numbers; all will either be exactly uniform without identification or all sorts of different colored buttons, but no numbers
         */

        // STEP 1 : CHOOSE/CREATE NUMBERED SEQUENCE

        // first run: set sequences, one for each level; just level 1 for now
        if(level==1) {
            int[] lvlonesequence1 = {3, 5, 7, 2, 4};
            return lvlonesequence1;
        }
        else if(level==2) {

        }
        else if(level==3) {

        }

        return null;
    }

    public void flashSequence(int[] s) {
        for(int i = 0; i < s.length; i++) {
            if (s[i] == 1) { b1.setText("" + (i + 1)); b1.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.colorPrimary)); }
            if (s[i] == 2) { b2.setText("" + (i + 1)); b2.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.colorPrimary)); }
            if (s[i] == 3) { b3.setText("" + (i + 1)); b3.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.colorPrimary)); }
            if (s[i] == 4) { b4.setText("" + (i + 1)); b4.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.colorPrimary)); }
            if (s[i] == 5) { b5.setText("" + (i + 1)); b5.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.colorPrimary)); }
            if (s[i] == 6) { b6.setText("" + (i + 1)); b6.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.colorPrimary)); }
            if (s[i] == 7) { b7.setText("" + (i + 1)); b7.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.colorPrimary)); }
            if (s[i] == 8) { b8.setText("" + (i + 1)); b8.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.colorPrimary)); }
            if (s[i] == 9) { b9.setText("" + (i + 1)); b9.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.colorPrimary)); }
        }
    }

    public void onGameButtonClick() {
        // STEP 3: TRACK PLAYER SEQUENCE INPUT


    }

    public void draw() {
        if(holder==null) return;

        Canvas c = holder.lockCanvas();
        update(c.getWidth(), c.getHeight());
        c.drawColor(Color.BLACK);
        // Level Indicators
        c.drawCircle(374, 85, 30, level_color);
        c.drawCircle(487, 85, 30, level_color);
        c.drawCircle(600, 85, 30, level_color);
        // Health Bar
        c.drawCircle(324, 1095, 50, health_color);
        c.drawCircle(487, 1095, 50, health_color);
        c.drawCircle(650, 1095, 50, health_color);
        holder.unlockCanvasAndPost(c);
    }

    public void update(int width, int height) {
        // Music Updates
        //checkAmbiance();

        // check win condition?
        // check level stage?

    }

    //successfully complete, return to map
    public void finishSuccess(View view){
        finish();
    }

    //fail, go to monster
    public void fail(View view){
        Intent my_intent=new Intent(getBaseContext(),Monster_encounter.class);
        startActivity(my_intent);
        // need to finish this activity somehow
    }

    @Override
    public void surfaceCreated(@NonNull SurfaceHolder surfaceHolder) {
        holder = surfaceHolder;
        draw();
    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder surfaceHolder, int i, int i1, int i2) {
        holder = surfaceHolder;
    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder surfaceHolder) {
        holder = null;
    }
}