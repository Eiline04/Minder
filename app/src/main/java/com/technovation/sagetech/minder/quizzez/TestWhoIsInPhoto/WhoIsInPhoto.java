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
import com.technovation.sagetech.minder.GlobalUtilities;
import com.technovation.sagetech.minder.MainActivity;
import com.technovation.sagetech.minder.R;
import com.technovation.sagetech.minder.quizzez.TestWhatIsInPhoto.WhatIsInPhotoModel;
import com.technovation.sagetech.minder.quizzez.TestWordsFittingPhotos.WordFittingPhoto;
import com.technovation.sagetech.minder.quizzez.TestWordsFittingPhotos.WordFittingPhotoModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class WhoIsInPhoto extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firebaseFirestore;

    private String user_id;
    private Integer localQuestionNumber;
    private static final Integer NUMBER_OF_QUESTIONS = 5;
    private List<WhoIsInPhotoModel> questions;

    private View loadingWidget;

    private ImageButton firstImage;
    private ImageButton secondImage;

    private TextView questionTextView;
    private TextView resultText;
    private TextView personsName;
    private TextView questionNumber;

    private HashMap<String,Object> dbData;

    private ArrayList<List<String>> combinationOptions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test2_who_photo);

        personsName = findViewById(R.id.questionTextView);
        firstImage = findViewById(R.id.firstImageBtn);
        secondImage = findViewById(R.id.secondImageBtn);

        loadingWidget = findViewById(R.id.loadingProgress);
        questionNumber = findViewById(R.id.currentExerciceIndex);
        questionTextView = findViewById(R.id.instructionsTextView);
        resultText = findViewById(R.id.imageResultText);

        resultText.setVisibility(View.INVISIBLE);
        setVisibilityForAll(View.INVISIBLE);
        //-------------------The buttons Listeners----------------
        firstImage.setOnClickListener(view -> buttonListener((View) view));
        secondImage.setOnClickListener(view -> buttonListener((View) view));
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

            dbData = (HashMap<String, Object>) task.getResult().getData();

            questions = task.getResult().getData().entrySet().stream()
                    .map(entry -> new WhoIsInPhotoModel(entry.getKey(), String.valueOf(entry.getValue())))
                    .collect(Collectors.toList());

            Collections.shuffle(questions, new Random(System.currentTimeMillis() * System.currentTimeMillis()));
            setQuestionAndAnswers();

        } else {
            Toast.makeText(WhoIsInPhoto.this, getString(R.string.error_get_quiz), Toast.LENGTH_SHORT).show();
        }

    }

    private void buttonListener(View view) {
        //------------MAKING THE BUTTONS nonCLICKABLE-----------------
        GlobalUtilities.setTwoClickableFalse(firstImage,secondImage);

        String answer =String.valueOf(view.getContentDescription());
        String givenName = String.valueOf(personsName.getText());
        boolean isCorrect = answer.equals(String.valueOf(dbData.get(givenName)));

        resultText.setVisibility(View.VISIBLE);
        resultText.setBackgroundColor(isCorrect ? Color.GREEN : Color.RED);
        resultText.setText(isCorrect ? "Corect!" : "Gresit!");

        localQuestionNumber += 1;

        if (localQuestionNumber >= Math.min(NUMBER_OF_QUESTIONS, combinationOptions.size())) {
            Toast.makeText(WhoIsInPhoto.this, "Să trecem la urmatoarele întrebări!", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(WhoIsInPhoto.this, MainActivity.class));
            finish();
        } else {
            view.postDelayed(this::setQuestionAndAnswers, 1000);
        }
    }

    private void setQuestionAndAnswers() {
        combinationOptions = getCombinations(questions);
        ArrayList<String> combinationData = new ArrayList<>();
        combinationData.addAll(combinationOptions.get(localQuestionNumber));

        setImage(combinationData.get(0), combinationData.get(1));
        personsName.setText(String.valueOf(combinationData.get(2)));
        questionNumber.setText(String.valueOf(GlobalUtilities.setGLOBAL_INDEX()));
        resultText.setVisibility(View.INVISIBLE);

        //------------MAKING THE BUTTONS CLICKABLE AGAIN-----------------
        GlobalUtilities.setTwoClickableTrue(firstImage,secondImage);
    }

    private ArrayList<List<String>> getCombinations(List<WhoIsInPhotoModel> questions) {
        ArrayList<List<String>> combinare = new ArrayList<>();
        WhoIsInPhotoModel model1, model2;
        ArrayList<String> optionsArray = new ArrayList<>();

        for (int i = 0; i < questions.size() - 1; i++) {
            model1 = questions.get(i);
            for(int j = i + 1; j < questions.size();j++){
                model2 = questions.get(j);
                optionsArray.add(0,model1.getPhotoUri());
                optionsArray.add(1,model2.getPhotoUri());
                optionsArray.add(2,model1.getName());
                combinare.add(optionsArray);
                optionsArray = new ArrayList<>();
                combinare = new ArrayList<>(combinare);

                optionsArray.add(0,model1.getPhotoUri());
                optionsArray.add(1,model2.getPhotoUri());
                optionsArray.add(2,model2.getName());
                combinare.add(optionsArray);
                optionsArray = new ArrayList<>();
                combinare = new ArrayList<>(combinare);
            }
        }
        combinare.size();
        Collections.shuffle(combinare, new Random(System.currentTimeMillis() * System.currentTimeMillis()));
        return combinare;
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

        setVisibilityForAll(View.VISIBLE);
    }

    /**
     * Sets the visibility for all widgets
     * Loading widget has the visibility flipped
     *
     * @param visibility View.VISIBLE or View.INVISIBLE
     */
    private void setVisibilityForAll(int visibility) {
        loadingWidget.setVisibility(visibility == View.VISIBLE ? View.INVISIBLE : View.VISIBLE);
       personsName.setVisibility(visibility);
        questionTextView.setVisibility(visibility);
        questionNumber.setVisibility(visibility);
        firstImage.setVisibility(visibility);
        secondImage.setVisibility(visibility);
    }

}