package com.technovation.sagetech.minder.quizzez;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.technovation.sagetech.minder.R;
//--------------THIS CLASS SHOULD START AFTER START BUTTON FROM MainActivity IS PRESSED------------------
public class Test1 extends AppCompatActivity {

    public int score = 0;
    public int questionNumber = 1;
    protected boolean finish = false;
    String[] questionDataArray;
    boolean isQuestionLeft, buttonPressed;
    Set1AdevaratFals afVarr;
    TextView questionTextView;
    Button trueBtn, falseBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.adevarat_sau_fals);

        questionTextView = findViewById(R.id.intrebareTextView_AsauF);
        trueBtn = findViewById(R.id.trueBtn);
        falseBtn = findViewById(R.id.falseBtn);

        questionDataArray = new String[2];
        isQuestionLeft = true;
        buttonPressed = false;

        while(isQuestionLeft){
            buttonPressed = false;
            afVarr.setQuestion();
            questionTextView.setText(afVarr.questionDataArray[0]);

            while(!buttonPressed){
                trueBtn.setOnClickListener(v -> {
                    if(afVarr.questionDataArray[1].equals("true")) {
                        score += 10;
                        Toast.makeText(Test1.this,"Corect!",Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(Test1.this,"Gresit!",Toast.LENGTH_SHORT).show();
                    }
                    buttonPressed = true;
                });

                falseBtn.setOnClickListener(v -> {
                    if(afVarr.questionDataArray[1].equals("false")) {
                        score += 10;
                        Toast.makeText(Test1.this,"Corect!",Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(Test1.this,"Gresit!",Toast.LENGTH_SHORT).show();
                    }
                    buttonPressed = true;
                });

            }
            questionNumber++;
        }
        Toast.makeText(Test1.this,"Testul s-a terminat!",Toast.LENGTH_SHORT).show();
    }

    String[] getQuestionData(String question, String answer){
            String[] questionAndAnswer = new String[2];
            questionAndAnswer[0] = question;
            questionAndAnswer[1] = answer;
            return questionAndAnswer;
    }

}