package com.technovation.sagetech.minder.quizzez;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.technovation.sagetech.minder.MainActivity;
import com.technovation.sagetech.minder.R;
import com.technovation.sagetech.minder.SettingsActivity;

import java.util.ArrayList;
import java.util.HashMap;

public class Test2_WhoIsInPhoto extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firebaseFirestore;

    private ProgressDialog progressDialog;

    private String user_id;
    private ImageButton firstImage, secondImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_test2_who_photo);

        progressDialog = new ProgressDialog(this);
        firstImage = findViewById(R.id.firstImageBtn);
        secondImage = findViewById(R.id.secondImageBtn);
        firebaseAuth = FirebaseAuth.getInstance();
        user_id = firebaseAuth.getCurrentUser().getUid();
        firebaseFirestore = FirebaseFirestore.getInstance();

    }

    @Override
    protected void onStart(){
        super.onStart();
        firebaseFirestore.collection("Users").document(user_id).get().
                addOnCompleteListener(task -> {

                    if(task.isSuccessful()){
                        if(task.getResult().exists()){

                            HashMap<String, Object> dbData = (HashMap<String, Object>) task.getResult().getData();
                            ArrayList<Object> value = new ArrayList<>();

                            for(String stringKey : dbData.keySet()) {
                            value.add(dbData.get(stringKey));
                            }

                            setImage(value.get(1), value.get(2));

//                            secondImage.setOnClickListener(v -> {
//                                startActivity(new Intent(Test2_WhoIsInPhoto.this, WhatIsInPhoto.class));
//                                finish();
//                            });
                        }

                    }else{
                        Toast.makeText(Test2_WhoIsInPhoto.this,"Nu exista date salvate", Toast.LENGTH_SHORT).show();
                    }

                });
    }

    @SuppressLint("CheckResult")
    private void setImage(Object firstPhotoUri, Object secondPhotoUri){

        //---------First image-----------------
        Glide.with(Test2_WhoIsInPhoto.this)
                .load(firstPhotoUri)
                .into(firstImage);

        //---------Second image-----------------
        Glide.with(Test2_WhoIsInPhoto.this)
                .load(secondPhotoUri)
                .into(secondImage);

    }

}