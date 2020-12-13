package com.example.ss_supernova_final;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.provider.Settings;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.TextView;


public class Monster_encounter extends AppCompatActivity implements SensorEventListener, SurfaceHolder.Callback{

    Bitmap pegasus;

    Bitmap background;
    Bitmap cloud;
    Bitmap cloud2;
    Bitmap heart;

    Bitmap monster;

    Paint scoreText;
    Paint styleText;

    SurfaceHolder holder = null;

    Animator2 my_animator;

    float timeRemaining;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);





        setContentView(R.layout.activity_monster_encounter);

        pegasus= Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.player), 200, 200, false);
        cloud= Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.tentaclej), 200, 800, false);
        cloud2= Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.tentaclej), 200, 800, false);
        monster= Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.face), 300, 300, false);
        heart= Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.heartj), 200, 200, false);
        background= Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.monsterbg), 1500, 2000, false);


        scoreText=new Paint();
        scoreText.setColor(Color.WHITE);
        scoreText.setTextSize(80);

        styleText=new Paint();
        styleText.setColor(Color.WHITE);
        styleText.setTextSize(50);

        SensorManager manager = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
        Sensor accel = manager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        if (accel!=null){
            manager.registerListener(this, accel, SensorManager.SENSOR_DELAY_NORMAL, SensorManager.SENSOR_DELAY_UI);
        }

        SurfaceView my_surface = findViewById(R.id.surfaceViewHe);
        my_surface.getHolder().addCallback(this);

        my_animator=new Animator2(this);
        my_animator.start();

        new CountDownTimer(20000, 1000) {

            public void onTick(long millisUntilFinished) {
                timeRemaining = millisUntilFinished / 1000;

                TextView mTextField = findViewById(R.id.timer);
                mTextField.setText("Time Left: " + millisUntilFinished / 1000);

            }

            //winstate
            public void onFinish() {
                finish();
            }

        }.start();
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {

        acc_x=sensorEvent.values[0];
        acc_y=sensorEvent.values[1];
        acc_z=sensorEvent.values[2];
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    float acc_x = 0;
    float acc_y = 0;
    float acc_z = 0;

    int pegasus_x_position=0; //left right
    int pegasus_y_position=1200; //up down

    //cloud 1
    int cloud_x_position=300;
    int cloud_y_position=0;

    //cloud 2
    int cloud2_x_position=700;
    int cloud2_y_position=0;


    //where game events happen
    public void update (int width, int height){


        int[] myIntArray = new int[]{100, 300, 500, 700, 900, 0};
        int randomItem = myIntArray[(int) Math.floor(Math.random()*myIntArray.length)];


        //pegasus behavior
        pegasus_x_position-=acc_x*3;
        pegasus_y_position =1200;
        //cloud behavior

        //pegasus boundaries
        if (pegasus_x_position<0)pegasus_x_position=0;
        else if(pegasus_x_position>width-200)pegasus_x_position=width-200;

        //tentacle boundaries and behaviour

            cloud_y_position+=10;
            cloud2_y_position+=7;

        if (cloud_y_position>600){

            cloud_y_position=0;

            randomItem = myIntArray[(int) Math.floor(Math.random()*myIntArray.length)];
            cloud_x_position=randomItem;

        }

        if (cloud2_y_position>600){

            cloud2_y_position=0;
            randomItem = myIntArray[(int) Math.floor(Math.random()*myIntArray.length)];
            cloud2_x_position=randomItem;

        }

        //losing conditions
        if(Math.abs(pegasus_y_position-cloud_y_position)<700 && Math.abs(pegasus_x_position-cloud_x_position)<100){

            cloud_y_position=0;
            cloud2_y_position=0;
            GlobalVariables.playerHealth-=1;
            Log.d("Notes", "ended program" + GlobalVariables.playerHealth);
            finish();

        } else if(Math.abs(pegasus_y_position-cloud2_y_position)<700 && Math.abs(pegasus_x_position-cloud2_x_position)<100) {

            cloud2_y_position=0;
            cloud_y_position=0;
            GlobalVariables.playerHealth-=1;
            Log.d("Notes", "ended program2" + GlobalVariables.playerHealth);
            finish();


        }

        if(GlobalVariables.playerHealth==0){
            GlobalVariables.gameOver = true;
            //ripple effect into the othr activities by closing them all and opening gameover activity when reach menu

        }

    }

    public void addPoint(){
    }


    public void draw(){

        if(holder==null)return;

        //cloud behavior again in case
        //cloud_y_position+=10;
        //cloud2_y_position+=10;


        Canvas c=holder.lockCanvas();

        update(c.getWidth(),c.getHeight());

        //draw all assets
        c.drawColor(Color.rgb(128,128,128));


        c.drawBitmap(background, 0,0, null);

        if(GlobalVariables.playerHealth==3){
            c.drawBitmap(heart, 250,1450, null);
            c.drawBitmap(heart, 500,1450, null);
            c.drawBitmap(heart, 750,1450, null);
        }else if(GlobalVariables.playerHealth==2){
            c.drawBitmap(heart, 250,1450, null);
            c.drawBitmap(heart, 500,1450, null);
        }else if(GlobalVariables.playerHealth==1){
            c.drawBitmap(heart, 500,1450, null);
        }


        //c.drawBitmap(monster, 300,100, null);
        c.drawBitmap(cloud, cloud_x_position,cloud_y_position, null);
        c.drawBitmap(cloud2, cloud2_x_position,cloud2_y_position, null);
        c.drawBitmap(pegasus, pegasus_x_position,1200, null);
        //c.drawText("Clouds reduce hearts.", +530, +1600, styleText);
        //c.drawText("Time Left: "+ timeRemaining, +220, +200, scoreText);

        //c.drawText("Monster Encounter!", 300, 60, styleText);

        holder.unlockCanvasAndPost(c);
    }

    @Override
    public void surfaceCreated( SurfaceHolder surfaceHolder) {

        holder = surfaceHolder;
        draw();

    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder surfaceHolder) {

        holder=null;
    }

    @Override
    public void onDestroy(){

        my_animator.finish();
        SensorManager manager = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
        manager.unregisterListener(this,manager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER));
        super.onDestroy();

    }

    /*
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

     */
}