package com.example.ss_supernova_final;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.TextView;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class Asteroid_miniGame extends AppCompatActivity implements SensorEventListener, SurfaceHolder.Callback{

    boolean lose;
    Paint white_text;
    Bitmap asteroid;
    Bitmap player;
    SurfaceHolder holder=null;
    Animator my_animator;
    int timer=20;

    float acc_x=0;
    float acc_y=0;

    private int soundFile;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asteroid_mini_game);

        white_text=new Paint();
        white_text.setColor(Color.WHITE);
        white_text.setTextSize(100);

        asteroid= Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.asteroid),230,230,false);
        player= Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.player),100,120,false);

        SensorManager manager=(SensorManager)getSystemService(Context.SENSOR_SERVICE);
        Sensor accelerometer=manager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        if(accelerometer!=null){
            manager.registerListener(this,accelerometer,SensorManager.SENSOR_DELAY_NORMAL,SensorManager.SENSOR_DELAY_UI);
        }

        SurfaceView my_surface=findViewById(R.id.surfaceView);
        my_surface.getHolder().addCallback(this);
        my_surface.setZOrderOnTop(true);


        my_animator=new Animator(this);
        my_animator.start();

        // Audio Elements
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            AudioAttributes audioAttributes = new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_ASSISTANCE_SONIFICATION)
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .build();
            GlobalVariables.soundPool = new SoundPool.Builder()
                    .setMaxStreams(6)
                    .setAudioAttributes(audioAttributes)
                    .build();
        }
        else { GlobalVariables.soundPool = new SoundPool(6, AudioManager.STREAM_MUSIC, 0); }

        soundFile = GlobalVariables.soundPool.load(this, R.raw.blaat, 1);
        // TO USE: paste this line with the correct sound file wherever you would like the sound to play (see the Memory Game's commented version for more information on the parameters, such as looping or speed)
        //GlobalVariables.soundPool.play(NAMEOFSOUNDFILE, 1, 1, 0, 0, 1);

        // BACKGROUND MUSIC
        GlobalVariables.ambiance = R.raw.minigame3;
        LoopMediaPlayer.stopMediaPlayer();
        LoopMediaPlayer.create(this, GlobalVariables.ambiance);

        new CountDownTimer(20000, 1000) {

            public void onTick(long millisUntilFinished) {

                TextView mTextField = findViewById(R.id.timer);

                mTextField.setText("seconds remaining: " + millisUntilFinished / 1000);
            }

            //winstate
            public void onFinish() {
                GlobalVariables.winState+=1;
                finish();

            }

        }.start();

    }


    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        acc_x=sensorEvent.values[0];
        acc_y=sensorEvent.values[1];
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    int asteroid_x_position=1000;
    int asteroid_y_position=600;
    int player_x_position=30;
    int player_y_position=0;
    //int random = ThreadLocalRandom.current().nextInt(0, 1200);
    int random = 600;
    int min = 0;
    int max=1500;

    public void update(int width, int height){

        asteroid_x_position-=50;

        //player_x_position-=acc_x*2;
        player_y_position+=acc_y*2;

        if(player_x_position<0)player_x_position=0;
        else if(player_x_position>width-200)player_x_position=width-200;

        if(player_y_position<0)player_y_position=0;
        else if(player_y_position>height-200)player_y_position=height-200;

        if(asteroid_x_position<-100){
            asteroid_x_position=width+200;
            Random r = new Random();
            int i1 = r.nextInt(height - 1) + 1;
            //max=height;
            //random = Random.nextInt(max-min+1)+min;
            asteroid_y_position=i1;
        }

        //FAIL -- GO TO MONSTER ENCOUNTER
        if(Math.abs(player_x_position-asteroid_x_position)<85 && Math.abs(player_y_position-asteroid_y_position)<120 ) {
            //go to monster;

            asteroid_x_position=width+200;


            finish();
            Intent my_intent=new Intent(getBaseContext(),Monster_encounter.class);
            startActivity(my_intent);

        }



    }

    public void draw(){

        if(holder==null)return;

        Canvas c=holder.lockCanvas();

        update(c.getWidth(),c.getHeight());

        c.drawColor(Color.rgb(5,10,15));


        //c.drawRect(0,0, 200,200,red_fill);

        //c.drawCircle(c.getWidth()/2,c.getHeight()/2,100,white_stroke);



        c.drawBitmap(asteroid,asteroid_x_position,asteroid_y_position,null);

        c.drawBitmap(player,player_x_position,player_y_position,null);

        //c.drawText(message, 20, c.getHeight()-20, white_text);

        holder.unlockCanvasAndPost(c);
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        holder=surfaceHolder;
        draw();
    }

    @Override
    public void surfaceChanged( SurfaceHolder surfaceHolder, int format, int width, int height) {
        holder=surfaceHolder;
    }

    @Override
    public void surfaceDestroyed( SurfaceHolder surfaceHolder) {
        holder=null;
    }

    @Override
    public void onDestroy(){

        my_animator.finish();
        SensorManager manager=(SensorManager) getSystemService(Context.SENSOR_SERVICE);
        manager.unregisterListener(this,manager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER));

        super.onDestroy();
    }
    /*
    public void fail(View view){
        //go to monster
        Intent my_intent=new Intent(getBaseContext(),Monster_encounter.class);
        startActivity(my_intent);
    }
     */

    //player avoids asteroids for 20 seconds = success
    public void finishSuccess(View view){
        //return to map
        //NOT FUNCTIONAL RIGHT NOW -- CHANGE TO WORK WITH TIMER
        //if(timer<1){
            //add global minigame score +1
           // finish();
        }

    }
