package com.technovation.sagetech.minder.quizzez.TestWhoIsInPhoto;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.technovation.sagetech.minder.R;
import com.technovation.sagetech.minder.quizzez.TestWhatIsInPhoto.WhatIsInPhotoModel;
import com.technovation.sagetech.minder.quizzez.TestWordsFittingPhotos.WordFittingPhoto;
import com.technovation.sagetech.minder.quizzez.TestWordsFittingPhotos.WordFittingPhotoModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class WhoIsInPhoto extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firebaseFirestore;

    private String user_id;
    private Integer localQuestionNumber;
    private static final Integer NUMBER_OF_QUESTIONS = 5;
    private List<WhoIsInPhotoModel> questions;

    private ProgressDialog progressDialog;

    private ImageButton firstImage;
    private ImageButton secondImage;

    private TextView questionNumber;
    private TextView resultText;
    private TextView personsName;

    private ArrayList<List<String>> combinationOptions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_test2_who_photo);

        progressDialog = new ProgressDialog(this);

        firstImage = findViewById(R.id.firstImageBtn);
        secondImage = findViewById(R.id.secondImageBtn);

//        firebaseAuth = FirebaseAuth.getInstance();
//        user_id = firebaseAuth.getCurrentUser().getUid();
//        firebaseFirestore = FirebaseFirestore.getInstance();

        questionNumber = findViewById(R.id.currentExerciceIndex);
        resultText = findViewById(R.id.imageResultText);
        personsName = findViewById(R.id.guestionTextView);
        resultText.setVisibility(View.INVISIBLE);
    }

    @Override
    protected void onStart(){
        super.onStart();
        localQuestionNumber = 0;

        firebaseAuth = FirebaseAuth.getInstance();
        user_id = firebaseAuth.getCurrentUser().getUid();
        firebaseFirestore = FirebaseFirestore.getInstance();

        //--------------------Get the Question data from Firestore Database------------------------
        firebaseFirestore.collection("Users").document(user_id).get().addOnCompleteListener(this::onLoadData);
    }

    private void onLoadData(Task<DocumentSnapshot> task) {
        if (task.isSuccessful()) {

            Toast.makeText(WhoIsInPhoto.this, "Datele au fost obtinute cu succes", Toast.LENGTH_SHORT).show();

            questions = task.getResult().getData().entrySet().stream()
                    .map(entry -> new WhoIsInPhotoModel(entry.getKey(), String.valueOf(entry.getValue())))
                    .collect(Collectors.toList());


            Collections.shuffle(questions);
            setQuestionAndAnswers();

        } else {
            Toast.makeText(WhoIsInPhoto.this, getString(R.string.error_get_quiz), Toast.LENGTH_SHORT).show();
        }

    }

    private ArrayList<List<String>> getCombinations(List<WhoIsInPhotoModel> questions) {

        WhoIsInPhotoModel model1, model2;
        List<String> optionsArray;

        for (int i = 0; i < questions.size() - 1; i++) {
            model1 = questions.get(i);
            model2 = questions.get(i + 1);
            optionsArray = Arrays.asList(model1.getPhotoUri(), model2.getPhotoUri(), model1.getName());
            combinationOptions.add(optionsArray);
            optionsArray.clear();
            optionsArray = Arrays.asList(model1.getPhotoUri(), model2.getPhotoUri(), model2.getName());
            combinationOptions.add(optionsArray);
        }
        Collections.shuffle(combinationOptions);
        return combinationOptions;
    }

    @SuppressLint("CheckResult")
    private void setImage(Object firstPhotoUri, Object secondPhotoUri) {

        //---------First image-----------------
        Glide.with(WhoIsInPhoto.this)
                .load(firstPhotoUri)
                .into(firstImage);
        firstImage.setContentDescription(String.valueOf(firstPhotoUri));

        //---------Second image-----------------
        Glide.with(WhoIsInPhoto.this)
                .load(secondPhotoUri)
                .into(secondImage);
        secondImage.setContentDescription(String.valueOf(secondPhotoUri));
    }

    private void setQuestionAndAnswers() {
        this.combinationOptions = getCombinations(questions);
        List<String> combinationData = combinationOptions.get(localQuestionNumber);

        setImage(combinationData.get(0), combinationData.get(1));
        personsName.setText(combinationData.get(2));
        questionNumber.setText(String.valueOf(localQuestionNumber + 12));
        resultText.setVisibility(View.INVISIBLE);
    }

    private void buttonListener(Button button) {
        String answer = String.valueOf(button.getContentDescription());
        Boolean isCorrect = questions.get(localQuestionNumber).isCorrect(answer);

        resultText.setVisibility(View.VISIBLE);
        resultText.setBackgroundColor(isCorrect ? Color.GREEN : Color.RED);
        resultText.setText(isCorrect ? "Corect!" : "Gresit!");

        localQuestionNumber += 1;
        if (localQuestionNumber >= Math.min(NUMBER_OF_QUESTIONS, combinationOptions.size())) {
            Toast.makeText(WhoIsInPhoto.this, "Să trecem la urmatoarele întrebări!", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(WhoIsInPhoto.this, WhoIsInPhoto.class));
            finish();
        } else {
            button.postDelayed(this::setQuestionAndAnswers, 1000);
        }
    }

}