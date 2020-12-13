package com.example.ss_supernova_final;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        GlobalVariables.ambiance = R.raw.maintheme;
        LoopMediaPlayer.stopMediaPlayer();
        LoopMediaPlayer.create(this, GlobalVariables.ambiance);
    }

    public void onStartGamePressed(View view){
        Intent my_intent=new Intent(getBaseContext(),Map.class);
        startActivity(my_intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        LoopMediaPlayer.create(this, GlobalVariables.ambiance);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        LoopMediaPlayer.stopMediaPlayer();
    }

}