package com.technovation.sagetech.minder.quizzez;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.technovation.sagetech.minder.R;

import java.util.ArrayList;
import java.util.HashMap;

public class WhatIsInPhoto extends AppCompatActivity {

    private FirebaseFirestore firebaseFirestore;

    private ImageButton firstObjectImage, secondObjectImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_what_is_in_photo);

        firstObjectImage = findViewById(R.id.firstObjectWhatIsImage);
        secondObjectImage = findViewById(R.id.secondObjectWhatIsImage);
        firebaseFirestore = FirebaseFirestore.getInstance();
    }

    @Override
    protected void onStart(){
        super.onStart();
        firebaseFirestore.collection("Tests").document("WhatIsInPhoto").get().
                addOnCompleteListener(task -> {

                    if(task.isSuccessful()){
                        if(task.getResult().exists()){

                            HashMap<String, Object> dbData = (HashMap<String, Object>) task.getResult().getData();
                            ArrayList<String> keyImagesUri = new ArrayList<>();

                            keyImagesUri.addAll(dbData.keySet());

//                            for(String stringKey : dbData.keySet()) {
//                                keyImagesUri.add(dbData.get(stringKey));
//                            }

                            setImage(keyImagesUri.get(1), keyImagesUri.get(2));
                        }

                    }else{
                        Toast.makeText(WhatIsInPhoto.this,"Nu exista date salvate", Toast.LENGTH_SHORT).show();
                    }

                });
    }

    @SuppressLint("CheckResult")
    private void setImage(Object firstPhotoUri, Object secondPhotoUri){

        //---------First image-----------------
        Glide.with(WhatIsInPhoto.this)
                .load(firstPhotoUri)
                .into(firstObjectImage);

        //---------Second image-----------------
        Glide.with(WhatIsInPhoto.this)
                .load(secondPhotoUri)
                .into(secondObjectImage);

    }

}