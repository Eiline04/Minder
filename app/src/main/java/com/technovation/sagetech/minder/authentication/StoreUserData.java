package com.technovation.sagetech.minder.authentication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.technovation.sagetech.minder.MainActivity;
import com.technovation.sagetech.minder.R;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import id.zelory.compressor.Compressor;

public class StoreUserData extends AppCompatActivity {

    private ImageView userImage;
    private EditText userName;
    private Button submit;

    //Variables for the images
    private ProgressDialog progressDialog;
    private Uri imageUri = null;
    private StorageReference storageReference;
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firebaseFirestore;
    private String user_id;
    private Bitmap compressed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_user_data);

        userImage = findViewById(R.id.user_image);
        userName = findViewById(R.id.user_name);
        submit = findViewById(R.id.submit);

        firebaseAuth = FirebaseAuth.getInstance();
        user_id = firebaseAuth.getCurrentUser().getUid();


        firebaseFirestore = FirebaseFirestore.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();

        progressDialog = new ProgressDialog(this);


        //-------------Choose the image from the phone--------------

        userImage.setOnClickListener(new View.OnClickListener() {
                                         @Override
                                         public void onClick(View view) {
                                             if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                                                 if (ContextCompat.checkSelfPermission(StoreUserData.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

                                                     Toast.makeText(StoreUserData.this, "Nu există permisiune", Toast.LENGTH_LONG).show();
                                                     ActivityCompat.requestPermissions(StoreUserData.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);

                                                 } else {
                                                     chooseImage();
                                                 }

                                             } else {
                                                 chooseImage();
                                             }

                                         }

                                     });

        //------------------Store the data in Firebase------------
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            progressDialog.setMessage("Se salvează datele...");
            progressDialog.show();

            final String username = userName.getText().toString();

            if(!TextUtils.isEmpty(username) && imageUri != null){

                File newFile = new File(imageUri.getPath());
                try {
                    compressed = new Compressor(StoreUserData.this)
                                  .setMaxHeight(125)
                                  .setMaxWidth(125)
                                  .setQuality(50)
                                  .compressToBitmap(newFile);

                } catch (IOException e) {
                    e.printStackTrace();
                }

                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                compressed.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);
                byte[] thumb = byteArrayOutputStream.toByteArray();


                //!!!!!
                UploadTask image_path = storageReference.child("user_image").child(user_id+"jpg").putBytes(thumb);

                image_path.addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {

                        if(task.isSuccessful()){

                            storeUserData(task, username);

                        }else{
                            String error = task.getException().getMessage();
                            Toast.makeText(StoreUserData.this, "(Eroare): " + error, Toast.LENGTH_LONG).show();
                            progressDialog.dismiss();
                        }

                    }
                });


            }else{
                Toast.makeText(StoreUserData.this, "Completați câmpul cu numele", Toast.LENGTH_SHORT).show();
            }

            }
        });


    }

    private void storeUserData(Task<UploadTask.TaskSnapshot> task, String username) {

        Uri download_uri;
        if(task!=null){
            download_uri = task.getResult().getDownloadUrl();
        }else{
            download_uri = imageUri;
        }


        //---!!!!!------------------Se face asocierea dintre nume si poza!
        Map<String,String> userData = new HashMap<>();
        userData.put(username, download_uri.toString());

        //----!!Colectia------------------------
        firebaseFirestore.collection("Users").document(user_id).collection("NumePersoane").document().set(userData).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if(task.isSuccessful()){

                    progressDialog.dismiss();
                    Toast.makeText(StoreUserData.this,"Datele au fost salvate cu succes",Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(StoreUserData.this, MainActivity.class));

                }else{
                    Toast.makeText(StoreUserData.this,"Firestore Error"+task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private void chooseImage() {
        CropImage.activity()
                .setGuidelines(CropImageView.Guidelines.ON)
                .setAspectRatio(1, 1)
                .start(StoreUserData.this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {

                imageUri = result.getUri();
                userImage.setImageURI(imageUri);


            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {

                Exception error = result.getError();

            }

        }
    }
}