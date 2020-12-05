package com.example.ss_supernova_final;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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