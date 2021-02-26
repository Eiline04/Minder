package com.technovation.sagetech.minder.quizzez;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.technovation.sagetech.minder.R;

import java.util.Random;

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
            } else Toast.makeText(Test1_TrueFalse.this, "Testul s-a terminat!", Toast.LENGTH_SHORT).show();
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
            } else Toast.makeText(Test1_TrueFalse.this, "Testul s-a terminat!", Toast.LENGTH_SHORT).show();
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
                            questionDataArray = getQuestionData("Marul este o leguma.", "false");
                            break;
                        case 2:
                            questionDataArray = getQuestionData("Daca ploua, este bine sa folosim umbrela.", "true");
                            break;
                        case 3:
                            questionDataArray = getQuestionData("Catelul are patru picioare.", "true");
                            break;
                        case 4:
                            questionDataArray = getQuestionData("Soarele este vizibil noaptea.", "false");
                            break;
                        case 5:
                            questionDataArray = getQuestionData("Apa are gust.", "false");
                            break;
                        case 6:
                            questionDataArray = getQuestionData("Apa de mare este sarata.", "true");
                            break;
                        case 7:
                            questionDataArray = getQuestionData("Lamaia este acra.", "true");
                            break;
                        case 8:
                            questionDataArray = getQuestionData("Ciresii infloresc vara.", "false");
                            break;
                        default:
                            questionDataArray = getQuestionData("Pinguinii zboara.", "false");
                    }
                } else setQuestion();
            }
        }else Toast.makeText(Test1_TrueFalse.this,"Sa trecem la urmatoarele intrebari",Toast.LENGTH_SHORT).show();
    }



}