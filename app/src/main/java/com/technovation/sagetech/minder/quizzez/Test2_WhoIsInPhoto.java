package com.technovation.sagetech.minder.quizzez;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.technovation.sagetech.minder.R;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

public class Test2_WhoIsInPhoto extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firebaseFirestore;
    private ProgressDialog progressDialog;

    private String user_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test2_who_photo);

        firebaseAuth = FirebaseAuth.getInstance();
        user_id = firebaseAuth.getCurrentUser().getUid();
        firebaseFirestore = FirebaseFirestore.getInstance();

    }


private Map<String, Object> photoUI_name(){
    AtomicReference<HashMap<String, Object>> data = null;
    firebaseFirestore.collection("Users").document(user_id).get().
            addOnCompleteListener(task -> {
                if(task.isSuccessful()){
                    progressDialog.dismiss();
                    if(task.getResult().exists()){
                        HashMap<String,Object> dbData = (HashMap<String, Object>) task.getResult().getData();
                        data.set(dbData);
                    }

                }else{
                    Toast.makeText(Test2_WhoIsInPhoto.this,"Nu exista date salvate", Toast.LENGTH_SHORT).show();
                }
            });
    return (Map<String, Object>) data;
}


}