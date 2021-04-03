package com.technovation.sagetech.minder.quizzez;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.Task;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import com.technovation.sagetech.minder.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Test1_TrueFalse extends AppCompatActivity {

    private static final Integer NUMBER_OF_QUESTIONS = 5;

    private FirebaseFirestore firebaseFirestore;

    private List<Question> questions;
    private int globalQuestionIndex;

    private TextView questionTextView;
    private TextView questionNumberTextView;
    private TextView resultText;
    private Button trueBtn;
    private Button falseBtn;
    private View loadingWidget;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        firebaseFirestore = FirebaseFirestore.getInstance();
        setContentView(R.layout.adevarat_sau_fals);

        questionTextView = findViewById(R.id.intrebareTextView_AsauF);
        questionNumberTextView = findViewById(R.id.numarIntrebare_AsauF);
        trueBtn = findViewById(R.id.trueBtn);
        falseBtn = findViewById(R.id.falseBtn);
        resultText = findViewById(R.id.resultText);
        loadingWidget = findViewById(R.id.loadingProgress);


        setVisibilityForAll(View.INVISIBLE);

        firebaseFirestore.collection("Tests").document("TrueFalse").get().addOnCompleteListener(this::onLoadData);

        //------------Click Listener for the TRUE button
        trueBtn.setOnClickListener(this::buttonListener);
        falseBtn.setOnClickListener(this::buttonListener);
    }

    @Override
    protected void onStart() {
        super.onStart();
        globalQuestionIndex = 0;
        resultText.setVisibility(View.INVISIBLE);
    }

    /**
     * Sets the visibility for all widgets
     * Loading widget has the visibility flipped
     *
     * @param visibility View.VISIBLE or View.INVISIBLE
     */
    private void setVisibilityForAll(int visibility) {
        loadingWidget.setVisibility(visibility == View.VISIBLE ? View.INVISIBLE : View.VISIBLE);
        questionTextView.setVisibility(visibility);
        questionNumberTextView.setVisibility(visibility);
        trueBtn.setVisibility(visibility);
        falseBtn.setVisibility(visibility);
    }

    private void onLoadData(Task<DocumentSnapshot> task) {
        setVisibilityForAll(View.VISIBLE);
        if (task.isSuccessful()) {

            Toast.makeText(Test1_TrueFalse.this, "Datele au fost obtinute cu succes", Toast.LENGTH_SHORT).show();

            questions = task.getResult().getData().entrySet().stream()
                    .map(entry -> new TrueFalseQuestion(entry.getKey(), String.valueOf(entry.getValue()).equals("true")))
                    .collect(Collectors.toList());

//            questions = new ArrayList<>();
//            for (Map.Entry<String, Object> entry : task.getResult().getData().entrySet()) {
//                questions.add(new TrueFalseQuestion(entry.getKey(), String.valueOf(entry.getValue()).equals("true")));
//            }


            Collections.shuffle(questions);

            setQuestion();
        }else{
            Toast.makeText(Test1_TrueFalse.this, getString(R.string.error_get_quiz), Toast.LENGTH_SHORT).show();
        }
    }

    private void buttonListener(View view) {

        Boolean answer = view.getId() == R.id.trueBtn;
        Boolean isCorrect = questions.get(globalQuestionIndex).isCorrect(answer);

        resultText.setVisibility(View.VISIBLE);
        resultText.setBackgroundColor(isCorrect ? Color.GREEN : Color.RED);
        resultText.setText(isCorrect ? "Corect!" : "Gresit!");

        globalQuestionIndex += 1;
        if (globalQuestionIndex >= Math.min(NUMBER_OF_QUESTIONS, questions.size())) {
            Toast.makeText(Test1_TrueFalse.this, "Să trecem la urmatoarele întrebări!", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(Test1_TrueFalse.this, WhatIsInPhoto.class));
            finish();
        } else {
            view.postDelayed(this::setQuestion, 1000);
        }
    }

    private void setQuestion() {
        questionTextView.setText(String.valueOf(questions.get(globalQuestionIndex).getQuestion()));
        questionNumberTextView.setText(String.valueOf(globalQuestionIndex + 1));
        resultText.setVisibility(View.INVISIBLE);
    }
}