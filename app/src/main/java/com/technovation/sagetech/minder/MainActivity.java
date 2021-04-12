package com.technovation.sagetech.minder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.technovation.sagetech.minder.quizzez.TestTrueFalse.Test1_TrueFalse;

public class MainActivity extends AppCompatActivity {

    private Button startBtn, settings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startBtn = findViewById(R.id.startBtn);
        settings = findViewById(R.id.settings);

        //------------Start Test1_TrueFalse Activity---------------
        startBtn.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, Test1_TrueFalse.class));
            finish();
        });

        //------------Start SettingsActivity---------------
        settings.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, SettingsActivity.class));
            finish();
        });
    }


}