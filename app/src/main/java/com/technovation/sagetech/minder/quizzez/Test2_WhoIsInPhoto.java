package com.technovation.sagetech.minder.quizzez;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.technovation.sagetech.minder.FirebaseDataReader;
import com.technovation.sagetech.minder.R;
import com.technovation.sagetech.minder.recycler_photo.RecyclerModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicReference;

public class Test2_WhoIsInPhoto extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firebaseFirestore;



    private ProgressDialog progressDialog;

    private FirebaseDataReader firebaseDataReader;
   //HashMap<String, Object> dbData;

    private String user_id;
    private ImageButton firstImage, secondImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test2_who_photo);

        progressDialog = new ProgressDialog(this);
        //firebaseDataReader = new FirebaseDataReader();
        firstImage = findViewById(R.id.firstImageBtn);
        secondImage = findViewById(R.id.secondImageBtn);
       // dbData = firebaseDataReader.getUI_and_image();

        firebaseAuth = FirebaseAuth.getInstance();
        user_id = firebaseAuth.getCurrentUser().getUid();
        firebaseFirestore = FirebaseFirestore.getInstance();

       /* firebaseAuth = FirebaseAuth.getInstance();
        user_id = firebaseAuth.getCurrentUser().getUid();
        firebaseFirestore = FirebaseFirestore.getInstance();*/

       /* ArrayList<String > key = new ArrayList<>();
        ArrayList<String> value = new ArrayList<>();
        int index = 0;

        for(String stringKey : dbData.keySet()){
            key.set(index, stringKey);
            value.set(index, (String) dbData.get(stringKey));
            index++;
        }*/
       // HashMap<String,Object> dataFromDB = new HashMap<String, Object>();
        firebaseFirestore.collection("Users").document(user_id).get().
                addOnCompleteListener(task -> {

                    if(task.isSuccessful()){
                        if(task.getResult().exists()){

                            HashMap<String, Object> dbData = (HashMap<String, Object>) task.getResult().getData();
                            //dataFromDB.putAll((HashMap<String, Object>)task.getResult().getData());
                            //ArrayList<String > key = new ArrayList<>();
                            ArrayList<String> value = new ArrayList<>();
                            int index = 0;

                            for(String stringKey : dbData.keySet()) {
                                // key.set(index, stringKey);
                                value.set(index, (String) dbData.get(stringKey));

                                // mList.add(value.get(index));
                                index++;
                            }
                            setImage(value.get(0), value.get(1));
                        }

                    }else{
                        Toast.makeText(Test2_WhoIsInPhoto.this,"Nu exista date salvate", Toast.LENGTH_SHORT).show();
                    }

                });
        //setImage(value.get(0), value.get(1));
        
    }


    @SuppressLint("CheckResult")
    private void setImage(String firstPhotoUri, String secondPhotoUri){
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(R.mipmap.ic_launcher);

        //---------First image-----------------
        Glide.with(Test2_WhoIsInPhoto.this)
                .setDefaultRequestOptions(requestOptions)
                .load(firstPhotoUri)
                .into(firstImage);

        //---------Second image-----------------
        Glide.with(Test2_WhoIsInPhoto.this)
                .setDefaultRequestOptions(requestOptions)
                .load(secondPhotoUri)
                .into(secondImage);

    }

}