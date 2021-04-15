package com.technovation.sagetech.minder;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.technovation.sagetech.minder.localNotifications.AlarmReceiver;
import com.technovation.sagetech.minder.quizzez.TestTrueFalse.Test1_TrueFalse;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    private Button startBtn, settings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        GlobalUtilities.TOTAL_SCORE = 0;
        GlobalUtilities.GLOBAL_INDEX = 0;

        startBtn = findViewById(R.id.startBtn);
        settings = findViewById(R.id.settings);

        //------------Start Test1_TrueFalse Activity---------------
        startBtn.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, Test1_TrueFalse.class));
        });

        //------------Start SettingsActivity---------------
        settings.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, SettingsActivity.class));
        });

    }


}