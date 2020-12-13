package com.example.ss_supernova_final;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GestureDetectorCompat;

import android.content.Intent;
import android.graphics.PointF;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class Wires_miniGame extends AppCompatActivity{

    static boolean active = false;

    public int image_id;
    private static final String DEBUGTAG = "JWP";
    private ViewGroup mainLayout;
    private ImageView image;
    private ImageView image2;
    private ImageView image3;

    private int xDelta;
    private int yDelta;

    private int switchup;
    private int switchdown;
    private int soundThing;
    private int soundThing2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wires_mini_game);

        // Extras Bundle
        Bundle extras = getIntent().getExtras();
        if(extras!=null) {
            //TextView sectionTitle = findViewById(R.id.sectionTitleWires);
            //sectionTitle.setText(extras.getString("sectionTitle"));
        }

        mainLayout = (RelativeLayout) findViewById(R.id.main);
        image = (ImageView) findViewById(R.id.image);
        image2 = (ImageView) findViewById(R.id.image2);
        image3 = (ImageView) findViewById(R.id.image3);

        image.setOnTouchListener(onTouchListener());
        image2.setOnTouchListener(onTouchListener());
        image3.setOnTouchListener(onTouchListener());

        // Audio Elements
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            AudioAttributes audioAttributes = new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_ASSISTANCE_SONIFICATION)
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .build();
            // Ctrl+B on the USAGE variable to go to the AudioAttributes Class and see what the various variables mean
            // The description for each variable is shown ABOVE each declaration in the AudioAttributes class
            GlobalVariables.soundPool = new SoundPool.Builder()
                    .setMaxStreams(6)
                    .setAudioAttributes(audioAttributes)
                    .build();
            // MaxStreams refers to how many sound clips can be played at once (6 in this case)
        }
        else {
            GlobalVariables.soundPool = new SoundPool(6, AudioManager.STREAM_MUSIC, 0);
            // maxStreams refers to how many sound clips are going to be loaded into the soundPool (6 in this case)
            // STREAM_MUSIC means the audio will be played through any connected devices, and srcQuality is fine at 0
            // Technically, this doesn't have to be here because we ARE using a higher SDK than Lollipop, but ¯\_(ツ)_/¯
        }


        soundThing = GlobalVariables.soundPool.load(this, R.raw.beepc3, 1);
        soundThing2 = GlobalVariables.soundPool.load(this, R.raw.boop4, 1);

        switchup = GlobalVariables.soundPool.load(this, R.raw.switchup, 1);
        switchdown = GlobalVariables.soundPool.load(this, R.raw.switchdown, 1);

        // TO USE: paste this line with the correct sound file wherever you would like the sound to play (see the Memory Game's commented version for more information on the parameters, such as looping or speed)


        // BACKGROUND MUSIC
        GlobalVariables.ambiance = R.raw.minigame2;
        LoopMediaPlayer.stopMediaPlayer();
        LoopMediaPlayer.create(this, GlobalVariables.ambiance);


        new CountDownTimer(15000, 1000) {

            public void onTick(long millisUntilFinished) {
                TextView mTextField = findViewById(R.id.timer);

                mTextField.setText("SECONDS REMAINING: " + millisUntilFinished / 1000);
            }

            //lose
            public void onFinish() {

                if(active) {
                    finish();
                    Intent my_intent = new Intent(getBaseContext(), Monster_encounter.class);
                    startActivity(my_intent);
                }
            }

        }.start();
    }

    @Override
    public void onStart() {
        super.onStart();
        active = true;
    }

    @Override
    public void onStop() {
        super.onStop();
        active = false;
    }

    @Override
    protected void onPause() {
        super.onPause();
        LoopMediaPlayer.stopMediaPlayer();

    }

    @Override
    protected void onDestroy() {


        LoopMediaPlayer.stopMediaPlayer();
        super.onDestroy();
    }

    private OnTouchListener onTouchListener() {
        return new OnTouchListener() {

            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View view, MotionEvent event) {

                final int x = (int) event.getRawX();
                final int y = (int) event.getRawY();

                //int xbthree = image3.getLeft();
                int ybthree = image3.getTop();
                int ybtwo = image2.getTop();
                int ybone = image.getTop();


                ///return false;

                switch (event.getAction() & MotionEvent.ACTION_MASK) {

                    case MotionEvent.ACTION_DOWN:
                        RelativeLayout.LayoutParams lParams = (RelativeLayout.LayoutParams) view.getLayoutParams();

                        xDelta = x - lParams.leftMargin;
                        yDelta = y - lParams.topMargin;

                        GlobalVariables.soundPool.play(soundThing, 1, 1, 0, 0, 1);

                        break;

                    case MotionEvent.ACTION_UP:
                        //if location released is a certain area and is a certain battery, check a boolean

                        if(y>1000&&view==image){

                            GlobalVariables.soundPool.play(soundThing2, 1, 1, 0, 0, 1);
                            return false;


                        }else if(y>1000&&view==image2){

                            GlobalVariables.soundPool.play(soundThing2, 1, 1, 0, 0, 1);

                        }else if (y>1000&&view==image3) {

                            GlobalVariables.soundPool.play(soundThing2, 1, 1, 0, 0, 1);
                            //Toast.makeText(Wires_miniGame.this,
                                    //"You moved battery 3 correctly!", Toast.LENGTH_SHORT)
                                    //.show();
                        }

                        //winstate
                        if(ybthree>1000&&ybtwo>1000&&ybone>1000){

                                    GlobalVariables.winState += 1;
                                    finish();
                        }
                        break;

                    case MotionEvent.ACTION_MOVE:
                        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) view
                                .getLayoutParams();
                        layoutParams.leftMargin = x - xDelta;
                        layoutParams.topMargin = y - yDelta;
                        layoutParams.rightMargin = 0;
                        layoutParams.bottomMargin = 0;
                        view.setLayoutParams(layoutParams);
                        break;
                }

                mainLayout.invalidate();
                return true;
            }
        };
    }



}