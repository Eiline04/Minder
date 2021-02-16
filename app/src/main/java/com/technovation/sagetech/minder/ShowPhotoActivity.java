package com.technovation.sagetech.minder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.technovation.sagetech.minder.authentication.StoreUserData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ShowPhotoActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private TextView photoName;
    private ImageView imageView;
   // private HashMap<String,Object> hm;
    private ArrayList<StoreUserData> mList;
    private StoreUserData aux;

    private RecyclerViewAdapter adapter;

    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firebaseFirestore;
    //private DocumentReference firebaseFirestore = FirebaseFirestore.getInstance()
          //  .collection("Users").document(FirebaseAuth.getInstance().getCurrentUser().getUid());
    private ProgressDialog progressDialog = new ProgressDialog(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_user_data);

        recyclerView = findViewById(R.id.recyclerView);
        imageView = findViewById(R.id.m_image);
        photoName = findViewById(R.id.photoName_TextView);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        firebaseAuth = FirebaseAuth.getInstance();
        String user_id = firebaseAuth.getCurrentUser().getUid();
        firebaseFirestore = FirebaseFirestore.getInstance();

        //hm = new HashMap<>();
        mList= new ArrayList<StoreUserData>();
        adapter = new RecyclerViewAdapter(this,mList);
        recyclerView.setAdapter(adapter);

        progressDialog.setMessage("Va rugam sa asteptati...");
        progressDialog.show();

       /* firebaseFirestore.collection("Users").document(user_id).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()) {
                    Map<String, Object> photo = documentSnapshot.getData();
                    for( String i : photo.keySet()) {
                        photoName.setText("Nume: " + i);
                        //imageView.setImageResource();
                        //mList.add();
                    }
                    adapter.notifyDataSetChanged();

                } else {
                    Toast.makeText(ShowPhotoActivity.this, "Document does not exist", Toast.LENGTH_SHORT).show();
                }
            }
        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(ShowPhotoActivity.this, "Error!", Toast.LENGTH_SHORT).show();
                       // Log.d( e.toString());
            }
        });*/

        firebaseFirestore.collection("Users").document(user_id).addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(DocumentSnapshot documentSnapshot, FirebaseFirestoreException e) {

                if (e != null) {
                    Toast.makeText(ShowPhotoActivity.this,"Eroare ", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (documentSnapshot != null && documentSnapshot.exists()) {
                    Map<String, Object> photo = documentSnapshot.getData();

                    for(String i : photo.keySet()){



                    }

                } else {
                    Toast.makeText(ShowPhotoActivity.this,"Nu exista date salvate", Toast.LENGTH_SHORT).show();
                }

            }
        });


////Toast.makeText(ShowPhotoActivity.this,"Eroare "+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
    }



}