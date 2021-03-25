package com.technovation.sagetech.minder.quizzez;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.QwertyKeyListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.technovation.sagetech.minder.MainActivity;
import com.technovation.sagetech.minder.R;
import com.technovation.sagetech.minder.authentication.StoreUserData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;


public class Test1_TrueFalse extends AppCompatActivity {

    private FirebaseFirestore firebaseFirestore;
    private HashMap<String,Object> questionDataFromDB;
    private ArrayList<String> questionKey;
    private int qKeyLength;

    public int globalQuestionIndex, randomNo;

    private TextView questionTextView, questionNumberTextView;
    private Button trueBtn, falseBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.adevarat_sau_fals);

       // init();
        randomNo = 0;
        globalQuestionIndex = 0;
        qKeyLength = 0;
        questionDataFromDB = new HashMap<>();
        questionKey = new ArrayList<>();
        firebaseFirestore = FirebaseFirestore.getInstance();

        questionTextView = findViewById(R.id.intrebareTextView_AsauF);
        questionNumberTextView = findViewById(R.id.numarIntrebare_AsauF);
        trueBtn = findViewById(R.id.trueBtn);
        falseBtn = findViewById(R.id.falseBtn);

        //--------------------Get the Question data from Firestore Database------------------------
        firebaseFirestore.collection("Tests").document("TrueFalse").get().
                addOnCompleteListener(task -> {

                    if(task.isSuccessful()){

                        questionDataFromDB =(HashMap<String, Object>) task.getResult().getData();

                        Toast.makeText(Test1_TrueFalse.this,"Datele au fost obtinute.",Toast.LENGTH_SHORT).show();

                        questionKey.addAll(questionDataFromDB.keySet());
                        qKeyLength = questionKey.size();

                    }
                });


        setQuestion();

        //------------Click Listener for the TRUE button
        trueBtn.setOnClickListener(v -> {
            if (globalQuestionIndex<5) {
                if (questionDataFromDB.get(questionKey.get(randomNo)).toString().equals("true")) {
                    Toast.makeText(Test1_TrueFalse.this, "Corect!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(Test1_TrueFalse.this, "Gresit!", Toast.LENGTH_SHORT).show();
                }
                setQuestion();
            } else{
                Toast.makeText(Test1_TrueFalse.this, "Să trecem la urmatoarele întrebări!", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(Test1_TrueFalse.this, Test2_WhoIsInPhoto.class));
            }
        });

        falseBtn.setOnClickListener(v -> {
            if (globalQuestionIndex<5) {
                if (questionDataFromDB.get(questionKey.get(randomNo)).toString().equals("false")) {
                    Toast.makeText(Test1_TrueFalse.this, "Corect!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(Test1_TrueFalse.this, "Gresit!", Toast.LENGTH_SHORT).show();
                }
                setQuestion();
            } else{
                Toast.makeText(Test1_TrueFalse.this, "Să trecem la urmatoarele întrebări!", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(Test1_TrueFalse.this, Test2_WhoIsInPhoto.class));
            }
        });

    }

    private void init(){

        randomNo = 0;
        globalQuestionIndex = 0;
        qKeyLength = 0;
        questionDataFromDB = new HashMap<>();
        questionKey = new ArrayList<>();
        firebaseFirestore = FirebaseFirestore.getInstance();

        questionTextView = findViewById(R.id.intrebareTextView_AsauF);
        questionNumberTextView = findViewById(R.id.numarIntrebare_AsauF);
        trueBtn = findViewById(R.id.trueBtn);
        falseBtn = findViewById(R.id.falseBtn);
    }

    private void setQuestion(){
        Random rand = new Random();
        randomNo = rand.nextInt(qKeyLength-1);
        questionTextView.setText(String.valueOf(questionKey.get(randomNo)));
        questionNumberTextView.setText(String.valueOf(++globalQuestionIndex));

        questionKey.remove(randomNo);
        qKeyLength--;
    }
}







/** I will comment this working code to try out something new - to read the questions from DB-**/
/*
//--------------THIS CLASS SHOULD START AFTER START BUTTON FROM MainActivity IS PRESSED------------------
public class Test1_TrueFalse extends AppCompatActivity {

    public int score = 0;
    private int questionNumber = 0;
    private final int[] no_questions_ToAsk ={-1,-1,-1,-1,-1};
    private String[] questionDataArray;
    private boolean isQuestionLeft;
    private TextView questionTextView, questionNumberTextView;
    private Button trueBtn, falseBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.adevarat_sau_fals);

        questionTextView = findViewById(R.id.intrebareTextView_AsauF);
        questionNumberTextView = findViewById(R.id.numarIntrebare_AsauF);
        trueBtn = findViewById(R.id.trueBtn);
        falseBtn = findViewById(R.id.falseBtn);

        questionDataArray = new String[2];

        setQuestion();
        questionTextView.setText(questionDataArray[0]);
        questionNumberTextView.setText(String.valueOf(questionNumber+1));

        //------------Click Listener for the TRUE button
        trueBtn.setOnClickListener(v -> {
            if (isQuestionLeft) {
                if (questionDataArray[1].equals("true")) {
                    score += 10;
                    Toast.makeText(Test1_TrueFalse.this, "Corect!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(Test1_TrueFalse.this, "Gresit!", Toast.LENGTH_SHORT).show();
                }
                questionNumber++;
                if (isQuestionLeft) {
                    setQuestion();
                    questionTextView.setText(questionDataArray[0]);
                    questionNumberTextView.setText(String.valueOf(questionNumber+1));
                }
            } else{
                Toast.makeText(Test1_TrueFalse.this, "Să trecem la urmatoarele întrebări!", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(Test1_TrueFalse.this, Test2_WhoIsInPhoto.class));
            }
        });


        //------------Click Listener for the FALSE button
        falseBtn.setOnClickListener(v -> {
            if (isQuestionLeft) {
                if (questionDataArray[1].equals("false") && isQuestionLeft) {
                    score += 10;
                    Toast.makeText(Test1_TrueFalse.this, "Corect!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(Test1_TrueFalse.this, "Gresit!", Toast.LENGTH_SHORT).show();
                }
                questionNumber++;
                if (isQuestionLeft) {
                    setQuestion();
                    questionTextView.setText(questionDataArray[0]);
                    questionNumberTextView.setText(String.valueOf(questionNumber+1));
                }
            } else{
                Toast.makeText(Test1_TrueFalse.this, "Să trecem la urmatoarele întrebări!", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(Test1_TrueFalse.this, Test2_WhoIsInPhoto.class));
            }
        });


    }

    //--------------------CREATING A ARRAY WITH THE QUESTION AND ITS ANSWER-----------------
    String[] getQuestionData(String question, String answer){
        String[] questionAndAnswer = new String[2];
        questionAndAnswer[0] = question;
        questionAndAnswer[1] = answer;
        return questionAndAnswer;
    }

    //--------------------------THE FUNCTION THAT GETS THE QUESTION--------------------------
    public void setQuestion(){
        if(questionNumber<=5) {
            isQuestionLeft = false;

            //Verifying if there is a question remained to ask
            for (int i = 0; i < 5 && !isQuestionLeft; i++) {
                if (no_questions_ToAsk[i] == -1) isQuestionLeft = true;
            }


            if (isQuestionLeft) {
                Random rand = new Random();
                int randomNo = rand.nextInt(9);

                for (int i = 0; i < 5; i++) {
                    if (no_questions_ToAsk[i] == randomNo) setQuestion();
                }

                if (no_questions_ToAsk[questionNumber ] == -1 && questionNumber< 5) {
                    no_questions_ToAsk[questionNumber ] = randomNo;
                    switch (randomNo) {
                        case 0:
                            questionDataArray = getQuestionData("Se traverseaza pe culoarea verde a semaforului.", "true");
                            break;
                        case 1:
                            questionDataArray = getQuestionData("Mărul este o legumă.", "false");
                            break;
                        case 2:
                            questionDataArray = getQuestionData("Dacă plouă, este bine să folosim umbrela.", "true");
                            break;
                        case 3:
                            questionDataArray = getQuestionData("Cățelul are patru picioare.", "true");
                            break;
                        case 4:
                            questionDataArray = getQuestionData("Soarele este vizibil noaptea.", "false");
                            break;
                        case 5:
                            questionDataArray = getQuestionData("Apa are gust.", "false");
                            break;
                        case 6:
                            questionDataArray = getQuestionData("Apa de mare este sărată.", "true");
                            break;
                        case 7:
                            questionDataArray = getQuestionData("Lămâia este acră.", "true");
                            break;
                        case 8:
                            questionDataArray = getQuestionData("Cireșii înfloresc vara.", "false");
                            break;
                        default:
                            questionDataArray = getQuestionData("Pinguinii zboară.", "false");
                    }
                } else setQuestion();
            }
        }else Toast.makeText(Test1_TrueFalse.this,"Sa trecem la urmatoarele intrebari",Toast.LENGTH_SHORT).show();
    }
}*/

