package com.example.ss_supernova_final;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GestureDetectorCompat;

import android.content.Intent;
import android.graphics.PointF;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
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

    /*Animator my_animator;

    private GestureDetectorCompat mGestureDetector;

    PointF pointA = new PointF(10, 100);
    PointF pointB = new PointF(500, 400);
    PointF pointC = new PointF(500, 90000);

    private LineView mLineView;

     */

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

        mainLayout = (RelativeLayout) findViewById(R.id.main);
        image = (ImageView) findViewById(R.id.image);
        image2 = (ImageView) findViewById(R.id.image2);
        image3 = (ImageView) findViewById(R.id.image3);

        image.setOnTouchListener(onTouchListener());
        image2.setOnTouchListener(onTouchListener());
        image3.setOnTouchListener(onTouchListener());
        /*
        mGestureDetector = new GestureDetectorCompat(this, new GestureListener());

        mLineView = (LineView) findViewById (R.id.LineView);

        mLineView.setPointA(pointA);
        mLineView.setPointB(pointB);
        mLineView.draw();

        my_animator=new Animator(this);
        my_animator.start();

         */

    }




    private OnTouchListener onTouchListener() {
        return new OnTouchListener() {

            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View view, MotionEvent event) {

                final int x = (int) event.getRawX();
                final int y = (int) event.getRawY();



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
                            //Log.d("Notes", "Location "+x+" "+y);
                            Toast.makeText(Wires_miniGame.this,
                                    "You moved battery 1 correctly!", Toast.LENGTH_SHORT)
                                    .show();

                            return false;

                        }else if(y>1000&&view==image2){

                        Toast.makeText(Wires_miniGame.this,
                                    "You moved battery 2 correctly!", Toast.LENGTH_SHORT)
                                    .show();



                                }else if (y>1000&&view==image3){
                            Toast.makeText(Wires_miniGame.this,
                                    "You moved battery 3 correctly!", Toast.LENGTH_SHORT)
                                    .show();
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


    /*

    private class GestureListener extends GestureDetector.SimpleOnGestureListener{


        public GestureListener() {
            super();
        }

        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            Toast.makeText(Wires_miniGame.this, "thing has been tapped", Toast.LENGTH_SHORT).show();
            return super.onSingleTapUp(e);
        }

        @Override
        public void onLongPress(MotionEvent e) {
            Toast.makeText(Wires_miniGame.this, "thing has been tapped", Toast.LENGTH_SHORT).show();
            super.onLongPress(e);
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            Toast.makeText(Wires_miniGame.this, "thing has been tapped", Toast.LENGTH_SHORT).show();
            return super.onFling(e1, e2, velocityX, velocityY);
        }

        @Override
        public boolean onDown(MotionEvent e) {
            Toast.makeText(Wires_miniGame.this, "thing has been tapped", Toast.LENGTH_SHORT).show();
            return super.onDown(e);
        }

        @Override
        public boolean onDoubleTap(MotionEvent e) {
            Toast.makeText(Wires_miniGame.this, "thing has been tapped", Toast.LENGTH_SHORT).show();
            return super.onDoubleTap(e);
        }

        @Override
        public boolean onDoubleTapEvent(MotionEvent e) {
            return super.onDoubleTapEvent(e);
        }

        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
            Toast.makeText(Wires_miniGame.this, "thing has been tapped", Toast.LENGTH_SHORT).show();

            return super.onSingleTapConfirmed(e);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
        mGestureDetector.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

    public void update (int width, int height){

    }
    public void draw(){

    }


    //successfully complete, return to map
    // r: may be better to close the activity as opposed to starting a new one so probably an endActivity or something
    public void finishSuccess(View view){
        Intent my_intent=new Intent(getBaseContext(),Map.class);
        startActivity(my_intent);
    }

    //fail, go to monster
    //r: another one where you should probably close the activity first if possible
    public void fail(View view){
        Intent my_intent=new Intent(getBaseContext(),Monster_encounter.class);
        startActivity(my_intent);
    }

    //r: for when you need the actual void to change activities and not the button void. I mean I guess we can keep the other methods too I'll see
    public void gameWin(){

    }

    public void gameLose(){

    }


    //r: surfaceview stuff

    @Override
    public void surfaceCreated(@NonNull SurfaceHolder surfaceHolder) {

    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder surfaceHolder) {

    }

     */
}