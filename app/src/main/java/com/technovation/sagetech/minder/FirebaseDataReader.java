package com.technovation.sagetech.minder;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.StorageReference;
import com.technovation.sagetech.minder.recycler_photo.RecyclerModel;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

public class FirebaseDataReader extends AppCompatActivity {


    private StorageReference storageReference;
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firebaseFirestore;

    private String user_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        firebaseAuth = FirebaseAuth.getInstance();
        user_id = firebaseAuth.getCurrentUser().getUid();
        firebaseFirestore = FirebaseFirestore.getInstance();

    }


    public HashMap<String, Object> getUI_and_image(){
        HashMap<String,Object> dataFromDB = new HashMap<String, Object>();
        firebaseFirestore.collection("Users").document(user_id).get().
                addOnCompleteListener(task -> {

                    if(task.isSuccessful()){
                        if(task.getResult().exists()){

                           // HashMap<String, Object> dbData = (HashMap<String, Object>) task.getResult().getData();
                            dataFromDB.putAll((HashMap<String, Object>)task.getResult().getData());
                            //ArrayList<String > key = new ArrayList<>();
                            //ArrayList<String> value = new ArrayList<>();
                            //int index = 0;

                            /*for(String stringKey : dbData.get().keySet()) {
                                // key.set(index, stringKey);
                                //value.set(index, (String) dbData.get(stringKey));

                                String modelUrl = (String) dbData.get().get(stringKey);
                                mList.add(new RecyclerModel(stringKey, modelUrl));

                                // mList.add(value.get(index));
                                //index++;
                            }*/

                        }

                    }else{
                        Toast.makeText(FirebaseDataReader.this,"Nu exista date salvate", Toast.LENGTH_SHORT).show();
                    }

                });
        return dataFromDB;
    }

}
