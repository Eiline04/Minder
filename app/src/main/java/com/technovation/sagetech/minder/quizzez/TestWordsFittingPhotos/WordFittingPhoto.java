package com.technovation.sagetech.minder.quizzez.TestWordsFittingPhotos;

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

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.technovation.sagetech.minder.R;
import com.technovation.sagetech.minder.quizzez.TestWhoIsInPhoto.WhoIsInPhoto;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class WordFittingPhoto extends AppCompatActivity {

    private FirebaseFirestore firebaseFirestore;

    private Integer localQuestionNumber;
    private static final Integer NUMBER_OF_QUESTIONS = 5;
    private List<WordFittingPhotoModel> questions;

    private ProgressDialog progressDialog;

    private ImageButton firstImage;
    private ImageButton secondImage;

    private TextView questionNumber;
    private TextView resultText;
    private TextView fittingWord;

    private ArrayList<ArrayList<String>> combinationOptions;
    private HashMap<String,Object> dbData;

    @Override
    protected void onStart() {
        super.onStart();
        localQuestionNumber = 0;
        combinationOptions = new ArrayList<ArrayList<String>>();

    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test2_who_photo);

        progressDialog = new ProgressDialog(this);

        firstImage = findViewById(R.id.firstImageBtn);
        secondImage = findViewById(R.id.secondImageBtn);

        firebaseFirestore = FirebaseFirestore.getInstance();

        questionNumber = findViewById(R.id.currentExerciceIndex);
        resultText = findViewById(R.id.imageResultText);
        fittingWord = findViewById(R.id.questionTextView);
        resultText.setVisibility(View.INVISIBLE);



        //--------------------Get the Question data from Firestore Database------------------------
          firebaseFirestore.collection("Tests").document("WordFittingPhoto").get().addOnCompleteListener(this::onLoadData);

        //-------------------The buttons Listeners----------------
        firstImage.setOnClickListener(view -> buttonListener((View) view));
        secondImage.setOnClickListener(view -> buttonListener((View) view));

    }

    private void onLoadData(Task<DocumentSnapshot> task) {
        if (task.isSuccessful()) {

            Toast.makeText(WordFittingPhoto.this, "Datele au fost obtinute cu succes", Toast.LENGTH_SHORT).show();

             dbData = (HashMap<String, Object>) task.getResult().getData();

            questions = task.getResult().getData().entrySet().stream()
                    .map(entry -> new WordFittingPhotoModel(entry.getKey(), String.valueOf(entry.getValue())))
                    .collect(Collectors.toList());

            Collections.shuffle(questions);
             setQuestionAndAnswers();

        } else {
            Toast.makeText(WordFittingPhoto.this, getString(R.string.error_get_quiz), Toast.LENGTH_SHORT).show();
        }

    }


    private void setQuestionAndAnswers() {
        combinationOptions = getCombinations(questions);
        ArrayList<String> combinationData = new ArrayList<>();
        combinationData.addAll(combinationOptions.get(localQuestionNumber));

        setImage(combinationData.get(0), combinationData.get(1));
        fittingWord.setText(String.valueOf(combinationData.get(2)));
        questionNumber.setText(String.valueOf(localQuestionNumber + 15));
        resultText.setVisibility(View.INVISIBLE);
    }

    private ArrayList<ArrayList<String>> getCombinations(List<WordFittingPhotoModel> questions) {
        ArrayList<ArrayList<String>> combinare = new ArrayList<>();
        WordFittingPhotoModel model1, model2;
        ArrayList<String> optionsArray = new ArrayList<>();

        for (int i = 0; i < questions.size() - 1; i++) {
            model1 = questions.get(i);
            for(int j = i + 1; j < questions.size();j++){
                model2 = questions.get(j);
                optionsArray.add(0,model1.getPhotoUri());
                optionsArray.add(1,model2.getPhotoUri());
                optionsArray.add(2,model1.getFittingWord());
                combinare.add(optionsArray);
               // combinare.size();
                optionsArray = new ArrayList<>();
                combinare = new ArrayList<ArrayList<String>>(combinare);
               // combinare.size();

                optionsArray.add(0,model1.getPhotoUri());
                optionsArray.add(1,model2.getPhotoUri());
                optionsArray.add(2,model2.getFittingWord());
                combinare.add(optionsArray);
                optionsArray = new ArrayList<>();
                combinare = new ArrayList<ArrayList<String>>(combinare);
            }
        }

//        for(WordFittingPhotoModel option: questions){
//            int factorialLength = 1;
//            for(int i = 1; i<= questions.size();i++) factorialLength *= i;
//            int length = factorialLength / (2 * questions.size() - 4);
//            while(combinare.size() <= length) combinare.add(new ArrayList<String>());
//        }
//        for (int i = 0; i < questions.size() - 1; i++) {
//            model1 = questions.get(i);
//            for(int j = i + 1; j < questions.size();j++){
//                model2 = questions.get(j);
//                optionsArray.add(0,model1.getFittingWord());
//                optionsArray.add(1,model1.getPhotoUri());
//                optionsArray.add(2,model2.getPhotoUri());
//                combinare.add(optionsArray);
//                combinare.size();
//                optionsArray.clear();
//                combinare.size();
//            }
//        }
        combinare.size();
        Collections.shuffle(combinare);
        Collections.shuffle(combinare);
        return combinare;
    }

    @SuppressLint("CheckResult")
    private void setImage(Object firstPhotoUri, Object secondPhotoUri) {

        //---------First image-----------------
        Glide.with(WordFittingPhoto.this)
                .load(firstPhotoUri)
                .into(firstImage);
        firstImage.setContentDescription(String.valueOf(firstPhotoUri));

        //---------Second image-----------------
        Glide.with(WordFittingPhoto.this)
                .load(secondPhotoUri)
                .into(secondImage);
        secondImage.setContentDescription(String.valueOf(secondPhotoUri));
    }


    private void buttonListener(View view) {
        String answer = String.valueOf(dbData.get(String.valueOf(view.getContentDescription())));
        String givenName = String.valueOf(fittingWord.getText());
       // Boolean isCorrect = questions.get(localQuestionNumber).isCorrect(answer);
        Boolean isCorrect = answer.equals(givenName);

        resultText.setVisibility(View.VISIBLE);
        resultText.setBackgroundColor(isCorrect ? Color.GREEN : Color.RED);
        resultText.setText(isCorrect ? "Corect!" : "Gresit!");

        localQuestionNumber += 1;
        if (localQuestionNumber >= Math.min(NUMBER_OF_QUESTIONS, combinationOptions.size())) {
            Toast.makeText(WordFittingPhoto.this, "Să trecem la urmatoarele întrebări!", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(WordFittingPhoto.this, WhoIsInPhoto.class));
            finish();
        } else {
            view.postDelayed(this::setQuestionAndAnswers, 1000);
        }
    }

}
