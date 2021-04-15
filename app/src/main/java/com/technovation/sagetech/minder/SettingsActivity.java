package com.technovation.sagetech.minder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.technovation.sagetech.minder.authentication.LoginActivity;
import com.technovation.sagetech.minder.authentication.StoreUserData;
import com.technovation.sagetech.minder.localNotifications.MainNotificationActivity;
import com.technovation.sagetech.minder.recycler_photo.ShowPhotoActivity;

public class SettingsActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private Button signOut;
    private Button toSUDActivity;
    private Button toAlarmSettings;
    private Button showAllBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        mAuth = FirebaseAuth.getInstance();
        signOut = findViewById(R.id.signOutButton);
        toSUDActivity = findViewById(R.id.toStoreUDBtn);
        toAlarmSettings = findViewById(R.id.alarmSettings);
        showAllBtn = findViewById(R.id.show_Allphoto);

        //--------------Open ShowPhotoActivity------------------
        showAllBtn.setOnClickListener(v -> startActivity( new Intent(SettingsActivity.this , ShowPhotoActivity.class)));

        //-------------------SignOut the user-------------------
        signOut.setOnClickListener(v -> {
            mAuth.signOut();
            startActivity(new Intent(SettingsActivity.this, LoginActivity.class));
            finish();
        });

        //------------Open StoreUserData Activity---------------
        toSUDActivity.setOnClickListener(v -> startActivity(new Intent(SettingsActivity.this, StoreUserData.class)));

        //------------Open Alarm & Notification Activity---------------
        toAlarmSettings.setOnClickListener(v -> startActivity(new Intent(SettingsActivity.this, MainNotificationActivity.class)));
    }
}