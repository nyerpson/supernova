package com.example.ss_supernova_final;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GestureDetectorCompat;

import android.content.Intent;
import android.graphics.PointF;
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


                        break;

                    case MotionEvent.ACTION_UP:
                        //if location released is a certain area and is a certain battery, check a boolean

                        if(y>1000&&view==image){

                            return false;

                        }else if(y>1000&&view==image2){


                        }else if (y>1000&&view==image3) {
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