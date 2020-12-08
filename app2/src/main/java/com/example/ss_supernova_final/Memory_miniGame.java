package com.example.ss_supernova_final;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;

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

    SurfaceHolder holder = null;

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
    Animator animator;

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

        animator = new Animator(this);
        animator.start();

        // Initialize Game
        level = 1;
        levelComplete = false;
        gameComplete = false;
        clickCount = 0;     // counter for which button in the sequence we're in
        // get extras bundles for things like the section title
        generateSequence(level);
        flashSequence();
    }

    /* Steps
        0. Show a popup with a button for READY? to give players a second to recognize the game? A countdown? A button to start the level?
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

    public Button[] generateSequence(int level) {
        // STEP 1 : CHOOSE/CREATE NUMBERED SEQUENCE

        // Remove all button tints and text
        buttonFunctions(1);
        tickCount = 0;

        // first run: set sequences, create randomized sequences in the future
        if(level==1) {
            gameSequence = new Button[] {b3, b5, b7, b2, b4};
            //gameSequence = randomizeSequence(5);
            playerSequence = new Button[5];
        }
        else if(level==2) {
            gameSequence = new Button[] {b2, b6, b4, b7, b9, b1, b5};
            //gameSequence = randomizeSequence(7);
            playerSequence = new Button[7];
        }
        else if(level==3) {
            gameSequence = new Button[] {b8, b3, b5, b7, b1, b2, b4, b6, b9};
            //gameSequence = randomizeSequence(9);
            playerSequence = new Button[9];
        }
        return gameSequence;
        // gameSequence shows the order (in indices) and button to press (in elements)
    }

    public Button[] randomizeSequence(int length) {
         List<Button> tempList = Arrays.asList(buttonList);
         Collections.shuffle(tempList);
         Button[] shuffledArray = new Button[9];
         tempList.toArray(shuffledArray);

         Button[] newSequence = new Button[length];
         for(int i = 0; i < length; i++) {
             newSequence[i] = shuffledArray[i];
         }
         return newSequence;
    }

    int tickCount = 0;
    public void flashSequence() {
        // STEP 2: FLASH THE GENERATED SEQUENCE
        buttonFunctions(3);     // disable clickability
        buttonFunctions(1);     // reset all buttons
        //


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
                tickCount = 0;
            }
        }.start();
    }

    // NOTE: THERE'S A WEIRD DELAY BETWEEN THE SEQUENCE FADING AND THE BUTTONS ACTUALLY REGISTERING THE INPUT AND CHANGING COLOR
    // NOTE: OKAY, NOT ACTUALLY A "DELAY" BUT IT WON'T COLOR/REGISTER THE FIRST PLAYER CLICK AND THEN GO ON AS NORMAL ON THE NEXT CLICK?

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
            if(playerSequence[i]!=gameSequence[i]) {
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
                level++;
                generateSequence(level);
                flashSequence();
                levelComplete = false;
            }
            if(level==2 && levelComplete) {
                level2_color.setColor(Color.GREEN);
                level++;
                generateSequence(level);
                flashSequence();
                levelComplete = false;
            }
            if(level==3 && levelComplete) {
                level3_color.setColor(Color.GREEN);
                gameComplete = true;
                bottomButton.setText("RETURN TO MAP");
                // stop timer, victory feedback, etc.
            }
        }
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
        update(c.getWidth(), c.getHeight());
        c.drawColor(Color.BLACK);
        // Level Indicators
        c.drawCircle(374, 85, 30, level1_color);
        c.drawCircle(487, 85, 30, level2_color);
        c.drawCircle(600, 85, 30, level3_color);
        holder.unlockCanvasAndPost(c);
    }

    public void update(int width, int height) {
        // Music Updates
        //checkAmbiance();
    }

    //flash sequence again; if game complete, return to map
    public void onBottomClick(View view){
        if(!gameComplete) { flashSequence(); }      // NEED TO MAKE SURE YOU CAN ONLY FLASH SEQUENCE
        else { finish(); }
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