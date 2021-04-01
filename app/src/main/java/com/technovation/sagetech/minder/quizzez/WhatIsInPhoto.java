package com.technovation.sagetech.minder.quizzez;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
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
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.technovation.sagetech.minder.R;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class WhatIsInPhoto extends AppCompatActivity {

    private FirebaseFirestore firebaseFirestore;

    private int localQuestionNumber;
    private static final Integer NUMBER_OF_QUESTIONS = 5;
    List<ImageNameQuestionModel> questions;

    private ImageButton objectImage;
    private TextView resultText;

    private Button firstOption;
    private Button secondOption;
    private Button thirdOption;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        firebaseFirestore = FirebaseFirestore.getInstance();
        setContentView(R.layout.activity_what_is_in_photo);

        objectImage = findViewById(R.id.firstObjectWhatIsImage);
        firstOption = findViewById(R.id.firstOption);
        secondOption = findViewById(R.id.secondOption);
        thirdOption = findViewById(R.id.thirdOption);

        resultText = findViewById(R.id.imageResultText);
        resultText.setVisibility(View.INVISIBLE);

        //--------------------Get the Question data from Firestore Database------------------------
        firebaseFirestore.collection("Tests").document("WhatIsInPhoto").get().addOnCompleteListener(this::onLoadData);

        //-------------------The buttons Listeners----------------
        firstOption.setOnClickListener(button -> buttonListener((Button) button));
        secondOption.setOnClickListener(button -> buttonListener((Button) button));
        thirdOption.setOnClickListener(button -> buttonListener((Button) button));
    }

    @Override
    protected void onStart() {
        super.onStart();
        localQuestionNumber = 0;
    }


    private void onLoadData(Task<DocumentSnapshot> task) {
        if (task.isSuccessful()) {

            Toast.makeText(WhatIsInPhoto.this, "Datele au fost obtinute cu succes", Toast.LENGTH_SHORT).show();

            questions = new ArrayList<>();
            for (Map.Entry<String, Object> entry : task.getResult().getData().entrySet()) {
                questions.add(new ImageNameQuestionModel(entry.getKey(), (Objects) entry.getValue()));
            }

            Collections.shuffle(questions);
            setQuestionAndAnswers();

        } else {
            Toast.makeText(WhatIsInPhoto.this, getString(R.string.error_get_quiz), Toast.LENGTH_SHORT).show();
        }

    }

    @SuppressLint("CheckResult")
    private void setImage(Object firstPhotoUri) {

        //---------First image-----------------
        Glide.with(WhatIsInPhoto.this)
                .load(firstPhotoUri)
                .into(objectImage);
    }

    private void setQuestionAndAnswers() {
        ImageNameQuestionModel model = questions.get(localQuestionNumber);
        setImage(model.getPhotoUri());
        firstOption.setText(model.getOptions().get(0));
        secondOption.setText(model.getOptions().get(1));
        thirdOption.setText(model.getOptions().get(2));
        resultText.setVisibility(View.INVISIBLE);
    }

    private void buttonListener(Button button) {

        String answer = (String) button.getText();
        Boolean isCorrect = questions.get(localQuestionNumber).isCorrect(answer);

        resultText.setVisibility(View.VISIBLE);
        resultText.setBackgroundColor(isCorrect ? Color.GREEN : Color.RED);
        resultText.setText(isCorrect ? "Corect!" : "Gresit!");

        localQuestionNumber += 1;
        if (localQuestionNumber >= Math.min(NUMBER_OF_QUESTIONS, questions.size())) {
            Toast.makeText(WhatIsInPhoto.this, "Să trecem la urmatoarele întrebări!", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(WhatIsInPhoto.this, WhatIsInPhoto.class));
            finish();
        } else {
            button.postDelayed(this::setQuestionAndAnswers, 1000);
        }
    }

}