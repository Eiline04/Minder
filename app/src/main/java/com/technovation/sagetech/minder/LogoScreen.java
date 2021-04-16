package com.technovation.sagetech.minder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.technovation.sagetech.minder.authentication.LoginActivity;
import com.technovation.sagetech.minder.onBoardingSlider.OnBoarding;

public class LogoScreen extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private String current_user_id;

    private Animation topAnimation;
    private Animation bottomAnimation;
    private static int SPLASH_SCREEN_DURATION = 2000;

    private SharedPreferences onBoardingScreen;

    private ImageView benjieImage;
    private TextView minderText;

    @Override
    protected void onStart() {
        super.onStart();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        mAuth = FirebaseAuth.getInstance();
        current_user_id = mAuth.getUid();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logo);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        topAnimation = AnimationUtils.loadAnimation(this, R.anim.top_animation);
        bottomAnimation = AnimationUtils.loadAnimation(this, R.anim.bottom_animation);

        minderText = findViewById(R.id.minderLogoTextView);
        benjieImage = findViewById(R.id.benjieLogoImageView);

        minderText.setAnimation(bottomAnimation);
        benjieImage.setAnimation(topAnimation);

        new Handler().postDelayed(() -> {

            onBoardingScreen = getSharedPreferences("onBoardingScreen",MODE_PRIVATE);

            boolean isFirstTime = onBoardingScreen.getBoolean("firstTime",true);

            startActivity(new Intent(LogoScreen.this, OnBoarding.class));
                 finish();

//            if(isFirstTime){
//
//                SharedPreferences.Editor editor = onBoardingScreen.edit();
//                editor.putBoolean("firstTime",false);
//                editor.commit();
//
//                startActivity(new Intent(LogoScreen.this, OnBoarding.class));
//                 finish();
//
//            }else {
//                if (current_user_id == null) {
//                    startActivity(new Intent(LogoScreen.this, LoginActivity.class));
//
//                } else {
//                    startActivity(new Intent(LogoScreen.this, MainActivity.class));
//                }
//                finish();
//
//            }


        }, SPLASH_SCREEN_DURATION);
    }
}