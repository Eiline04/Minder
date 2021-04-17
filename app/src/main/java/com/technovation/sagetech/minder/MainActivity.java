package com.technovation.sagetech.minder;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;

import com.technovation.sagetech.minder.localNotifications.AlarmReceiver;
import com.technovation.sagetech.minder.quizzez.TestTrueFalse.Test1_TrueFalse;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    private ImageButton startBtn;
    private Button settings;
    private Button tutorials;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        GlobalUtilities.TOTAL_SCORE = 0;
        GlobalUtilities.GLOBAL_INDEX = 0;

        startBtn = findViewById(R.id.startBtn);
        settings = findViewById(R.id.settings);
        tutorials = findViewById(R.id.tutorials);

        //------------Start Test1_TrueFalse Activity---------------
        startBtn.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, Test1_TrueFalse.class));
        });

        //------------Start SettingsActivity---------------
        settings.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, SettingsActivity.class));
        });

        //------------Start SettingsActivity---------------
        tutorials.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, VideoTutorials.class));
        });
    }


}