package com.technovation.sagetech.minder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;

import com.google.firebase.auth.FirebaseAuth;
import com.technovation.sagetech.minder.authentication.LoginActivity;

public class LogoScreen extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private String current_user_id;

    @Override
    protected void onStart() {
        super.onStart();

        if(current_user_id==null){
            startActivity(new Intent(LogoScreen.this, LoginActivity.class));
        }else{
            startActivity(new Intent(LogoScreen.this, MainActivity.class));
        }
        finish();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logo);

        getWindow() .setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        mAuth = FirebaseAuth.getInstance();
        current_user_id = mAuth.getUid();


    }
}