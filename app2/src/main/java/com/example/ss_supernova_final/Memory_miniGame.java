package com.example.ss_supernova_final;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

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

    // Game Elements
    Button b1;
    Button b2;
    Button b3;
    Button b4;
    Button b5;
    Button b6;
    Button b7;
    Button b8;
    Button b9;
    int level;
    boolean levelComplete;
    boolean gameComplete;
    int clickCount;
    Button[] playerSequence;
    Button[] gameSequence;
    Button[] buttonList;
    int[] buttonIdList;

    // SurfaceView Elements
    Paint level1_color;
    Paint level2_color;
    Paint level3_color;

    // Other
    Button bottomButton;
    SurfaceHolder holder = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memory_mini_game);

        // get extras bundles for things like the section title
        Bundle extras = getIntent().getExtras();
        if(extras!=null) {
            TextView sectionTitle = findViewById(R.id.sectionTitle);
            sectionTitle.setText(extras.getString("sectionTitle"));
        }

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
        buttonList = new Button[] {b1, b2, b3, b4, b5, b6, b7, b8, b9};
        buttonIdList = new int[] {R.id.b1, R.id.b2, R.id.b3, R.id.b4, R.id.b5, R.id.b6, R.id.b7, R.id.b8, R.id.b9};
        bottomButton = findViewById((R.id.bottomButton));

        level1_color = new Paint();
        level1_color.setColor(Color.WHITE);
        level2_color = new Paint();
        level2_color.setColor(Color.WHITE);
        level3_color = new Paint();
        level3_color.setColor(Color.WHITE);

        SurfaceView surface = findViewById(R.id.surfaceView);
        surface.getHolder().addCallback(this);

        draw();

        // Initialize Game
        level = 1;
        levelComplete = false;
        gameComplete = false;
        clickCount = 0;
        generateSequence(level);
        flashSequence();
    }

    /* Steps
        0. Show a popup with a button for READY? to give players a second to recognize the game? A countdown? A button to start the level? Start automatically?
        1. Choose a numbered sequence of randomized squares
            - need different length sequences to choose from for each "level"
            - allow for pressing certain keys multiple times? Every button can only be pressed once currently
        2. Flash said sequence for a few seconds for the player to memorize
            - momentarily change the color/number of the button, then revert?
            - need to count time (pause timer while flashing?)
        3. Keep track of player input to determine if the sequence was correct
            a. if the player gets the sequence wrong, reset; allow player to re-flash the sequence
            b. if the player gets the sequence right, repeat from step 1 with a more difficult sequence

        FIRST RUN:
        - No double dipping, but don't gray out buttons after being pressed
        - Art buttons will have no numbers; all will either be exactly uniform without identification or all sorts of different colored buttons, but no numbers
         */

    public void generateSequence(int level) {
        // STEP 1 : CHOOSE/CREATE NUMBERED SEQUENCE
        buttonFunctions(1); // Remove all button tints and text
        tickCount = 0;

        if(level==1) {
            gameSequence = randomizeSequence(5);
            playerSequence = new Button[5];
        }
        else if(level==2) {
            gameSequence = randomizeSequence(7);
            playerSequence = new Button[7];
        }
        else if(level==3) {
            gameSequence = randomizeSequence(9);
            playerSequence = new Button[9];
        }
        // gameSequence shows the order (in indices) and button to press (the element at said index)
    }

    public Button[] randomizeSequence(int length) {
        Random rand = new Random();
        // Create a copy of the buttonList array
        Button[] tempSequence = new Button[buttonList.length];
        for(int i = 0; i < buttonList.length; i++) {
            tempSequence[i] = buttonList[i];
        }
        // Fisher-Yates Shuffle - shuffle the full button array
        for(int i = tempSequence.length - 1; i > 0; i--) {
            int j = rand.nextInt(i+1);
            Button temp = tempSequence[i];
            tempSequence[i] = tempSequence[j];
            tempSequence[j] = temp;
        }
        // Then copy the shuffled values over to the new sequence, only up to the specified length
        Button[] newSequence = new Button[length];
        for(int i = 0; i < length; i++) {
            newSequence[i] = tempSequence[i];
        }
        return newSequence;
    }

    int tickCount = 0;
    public void flashSequence() {
        // STEP 2: FLASH THE GENERATED SEQUENCE
        bottomButton.setEnabled(false);      // disable flash button
        buttonFunctions(3);             // disable game button click-ability
        buttonFunctions(1);             // reset all buttons

        new CountDownTimer(gameSequence.length * 500, gameSequence.length * 500 / (gameSequence.length+2)) {
            public void onTick(long millisUntilFinished) {
                //Toast.makeText(Memory_miniGame.this, "Tick Count: "+tickCount, Toast.LENGTH_SHORT).show();
                if(tickCount==0) { tickCount++; }
                else if(tickCount>0 && tickCount<=gameSequence.length) {
                    gameSequence[tickCount-1].setText(""+tickCount);
                    gameSequence[tickCount-1].setBackgroundTintList(ContextCompat.getColorStateList(Memory_miniGame.this, R.color.colorPrimary));
                    tickCount++;
                }
                else { tickCount++; }
            }
            public void onFinish() {
                buttonFunctions(1);
                buttonFunctions(2);
                bottomButton.setEnabled(true);    // enable flash button
                tickCount = 0;
            }
        }.start();
    }

    public void onGameButtonClick(View view) {
        // STEP 3: TRACK PLAYER SEQUENCE INPUT
        // FIRST: ADD BUTTON PRESSED TO PLAYER ARRAY
        for(int i = 0; i < buttonList.length; i++) {
            if(buttonIdList[i]==view.getId()) {                                                                                 // 1. Iterate through list of button Ids to get which one was pressed
                playerSequence[clickCount] = buttonList[i];                                                                     // 2. Add pressed button to player sequence array
                buttonList[i].setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.colorPrimaryDark));   // DEBUG: Feedback that the right button was pressed
                clickCount++;                                                                                                   // 3. Increment click count for adding new array elements
            }
        }
        // SECOND: CHECK THAT BOTH ARRAYS ARE THE SAME
        for(int i = 0; i < clickCount; i++) {
            if(playerSequence[i]!=gameSequence[i]) {  // If the sequences differ:
                buttonFunctions(1);             // 1. Reset all button tints and text
                buttonFunctions(4);             // 2. Reset the player sequence and clickCount
            }
        }
        // THIRD: IF CLICK COUNT HAS REACHED THE ARRAY LENGTH, IT MEANS THE SEQUENCE WAS COMPLETED
        if (gameSequence.length==clickCount) {
            buttonFunctions(1);
            clickCount = 0;
            levelComplete = true;
            if(level==1 && levelComplete) {
                level1_color.setColor(Color.GREEN);
                draw();
                level++;
                generateSequence(level);
                flashSequence();
                levelComplete = false;
            }
            if(level==2 && levelComplete) {
                level2_color.setColor(Color.GREEN);
                draw();
                level++;
                generateSequence(level);
                flashSequence();
                levelComplete = false;
            }
            if(level==3 && levelComplete) {
                level3_color.setColor(Color.GREEN);
                draw();
                gameComplete = true;
                bottomButton.setText("RETURN TO MAP");
                buttonFunctions(3);     // disable click-ability
                // stop timer, victory feedback (change button color/image?), etc.
            }
        }
    }

    //flash sequence again; if game complete, return to map
    public void onBottomClick(View view){
        if(!gameComplete) { flashSequence(); }
        else { finish(); }
    }

    public void buttonFunctions(int type) {
        // Type = 1: reset all button tints and text
        // Type = 2: set all buttons enabled
        // Type = 3: set all buttons disabled
        // Type = 4: reset player sequence + clickCount
        if(type==1) {
            for (int i = 0; i < buttonList.length; i++) {
                buttonList[i].setBackgroundTintList(null);
                buttonList[i].setText("");
            }
        }
        if(type==2) {
            for (int i = 0; i < buttonList.length; i++) {
                buttonList[i].setClickable(true);
            }
        }
        if(type==3) {
            for (int i = 0; i < buttonList.length; i++) {
                buttonList[i].setClickable(false);
            }
        }
        if(type==4) {
            for (int i = 0; i < playerSequence.length; i++) {
                playerSequence[i] = null;
                clickCount = 0;
            }
        }
    }

    public void draw() {
        if(holder==null) return;
        Canvas c = holder.lockCanvas();
        c.drawColor(Color.BLACK);
        // Level Indicators
        c.drawCircle(374, 85, 30, level1_color);
        c.drawCircle(487, 85, 30, level2_color);
        c.drawCircle(600, 85, 30, level3_color);
        holder.unlockCanvasAndPost(c);
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