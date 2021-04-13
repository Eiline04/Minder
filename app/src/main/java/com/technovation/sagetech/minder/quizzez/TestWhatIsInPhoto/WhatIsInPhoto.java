package com.technovation.sagetech.minder.quizzez.TestWhatIsInPhoto;

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
import com.technovation.sagetech.minder.GlobalUtilities;
import com.technovation.sagetech.minder.MainActivity;
import com.technovation.sagetech.minder.R;
import com.technovation.sagetech.minder.quizzez.TestWhoIsInPhoto.WhoIsInPhoto;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.Objects;
import java.util.Random;

public class WhatIsInPhoto extends AppCompatActivity {

    private FirebaseFirestore firebaseFirestore;

    private Integer localQuestionNumber;
    private static final Integer NUMBER_OF_QUESTIONS = 5;
    private ArrayList<WhatIsInPhotoModel> questions;

    private View loadingWidget;
    private ImageButton objectImage;
    private TextView resultText;
    private TextView questionNumber;
    private TextView questionFinalText;

    private Button firstOption;
    private Button secondOption;
    private Button thirdOption;

    @Override
    protected void onStart() {
        super.onStart();
        localQuestionNumber = 0;
        firebaseFirestore = FirebaseFirestore.getInstance();

        //--------------------Get the Question data from Firestore Database------------------------
        firebaseFirestore.collection("Tests").document("WhatIsInPhoto").get().addOnCompleteListener(this::onLoadData);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_what_is_in_photo);

        loadingWidget = findViewById(R.id.loadingProgress);
        objectImage = findViewById(R.id.firstObjectWhatIsImage);
        firstOption = findViewById(R.id.firstOption);
        secondOption = findViewById(R.id.secondOption);
        thirdOption = findViewById(R.id.thirdOption);

        resultText = findViewById(R.id.imageResultText);
        questionNumber = findViewById(R.id.questionNo);
        questionFinalText = findViewById(R.id.questionFinalTextView);

        resultText.setVisibility(View.INVISIBLE);
        setVisibilityForAll(View.INVISIBLE);

        //-------------------The buttons Listeners----------------
        firstOption.setOnClickListener(button -> buttonListener((Button) button));
        secondOption.setOnClickListener(button -> buttonListener((Button) button));
        thirdOption.setOnClickListener(button -> buttonListener((Button) button));
    }



    private void onLoadData(Task<DocumentSnapshot> task) {
        if (task.isSuccessful()) {

            Toast.makeText(WhatIsInPhoto.this, "Datele au fost obtinute cu succes", Toast.LENGTH_SHORT).show();

            questions = new ArrayList<>();
            for (Map.Entry<String, Object> entry : task.getResult().getData().entrySet()) {
                questions.add(new WhatIsInPhotoModel(entry.getKey(), (ArrayList<Objects>) entry.getValue()));
            }

            Collections.shuffle(questions, new Random(System.currentTimeMillis() * System.currentTimeMillis()));
            setQuestionAndAnswers();

        } else {
            Toast.makeText(WhatIsInPhoto.this, getString(R.string.error_get_quiz), Toast.LENGTH_SHORT).show();
            startActivity(new Intent(WhatIsInPhoto.this, WhereIsUsed.class));
            finish();
        }

    }

    private void buttonListener(Button button) {

        //------------MAKING THE BUTTONS nonCLICKABLE----------------
        GlobalUtilities.setThreeClickableFalse(firstOption,secondOption,thirdOption);

        String answer = (String) button.getText();
        Boolean isCorrect = questions.get(localQuestionNumber).isCorrect(answer);

        resultText.setVisibility(View.VISIBLE);
        resultText.setBackgroundColor(isCorrect ? Color.GREEN : Color.RED);
        resultText.setText(isCorrect ? "Corect!" : "Gresit!");

        localQuestionNumber += 1;
        if (localQuestionNumber >= Math.min(NUMBER_OF_QUESTIONS, questions.size())) {
            Toast.makeText(WhatIsInPhoto.this, "Să trecem la urmatoarele întrebări!", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(WhatIsInPhoto.this, WhereIsUsed.class));
            finish();
        } else {
            button.postDelayed(this::setQuestionAndAnswers, 1000);
        }
    }

    private void setQuestionAndAnswers() {
        WhatIsInPhotoModel model = questions.get(localQuestionNumber);
        setImage(model.getPhotoUri());
        firstOption.setText(model.getFirstOption());
        secondOption.setText(model.getSecondOption());
        thirdOption.setText(model.getThirdOption());
        questionNumber.setText(String.valueOf(GlobalUtilities.setGLOBAL_INDEX()));
        resultText.setVisibility(View.INVISIBLE);


        //------------MAKING THE BUTTONS CLICKABLE AGAIN-----------------
        GlobalUtilities.setThreeClickableTrue(firstOption,secondOption,thirdOption);

    }

    @SuppressLint("CheckResult")
    private void setImage(Object firstPhotoUri) {

        //---------First image-----------------
        Glide.with(WhatIsInPhoto.this)
                .load(firstPhotoUri)
                .into(objectImage);

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
        objectImage.setVisibility(visibility);
        questionNumber.setVisibility(visibility);
        questionFinalText.setVisibility(visibility);
        firstOption.setVisibility(visibility);
        secondOption.setVisibility(visibility);
        thirdOption.setVisibility(visibility);
    }

}