package com.example.ss_supernova_final;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class EndingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ending);

        Bundle extras = getIntent().getExtras();
        if (extras!=null) {
            TextView text = findViewById(R.id.endingText);
            text.setText(extras.getString("caption"));
        }
    }

    //return to home screen
    public void returnToStart(View view){
        Intent my_intent=new Intent(getBaseContext(),MainActivity.class);
        startActivity(my_intent);
    }
}