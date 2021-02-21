package com.technovation.sagetech.minder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.technovation.sagetech.minder.authentication.LoginActivity;
import com.technovation.sagetech.minder.authentication.StoreUserData;
import com.technovation.sagetech.minder.quizzez.Test1;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private Button signOut, toSUDActivity, startBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();
        signOut = findViewById(R.id.signOutButton);
        toSUDActivity = findViewById(R.id.toStoreUDBtn);
        startBtn = findViewById(R.id.startBtn);

        //------------------SignOut the user------------------
        signOut.setOnClickListener(v -> {
            mAuth.signOut();
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
            finish();
        });

        //------------Open StoreUserData Activity---------------
        toSUDActivity.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, StoreUserData.class));
            finish();
        });

        //------------Start Test1 Activity---------------
        startBtn.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, Test1.class));
            finish();
        });
    }
}