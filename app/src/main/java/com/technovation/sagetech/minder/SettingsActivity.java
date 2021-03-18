package com.technovation.sagetech.minder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.technovation.sagetech.minder.authentication.LoginActivity;
import com.technovation.sagetech.minder.authentication.StoreUserData;

public class SettingsActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private Button signOut, toSUDActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        mAuth = FirebaseAuth.getInstance();
        signOut = findViewById(R.id.signOutButton);
        toSUDActivity = findViewById(R.id.toStoreUDBtn);

        //------------------SignOut the user------------------
        signOut.setOnClickListener(v -> {
            mAuth.signOut();
            startActivity(new Intent(SettingsActivity.this, LoginActivity.class));
            finish();
        });

        //------------Open StoreUserData Activity---------------
        toSUDActivity.setOnClickListener(v -> {
            startActivity(new Intent(SettingsActivity.this, StoreUserData.class));
            finish();
        });
    }
}