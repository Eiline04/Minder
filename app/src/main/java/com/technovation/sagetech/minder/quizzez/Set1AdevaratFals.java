package com.technovation.sagetech.minder.quizzez;
import java.util.Random;

public class Set1AdevaratFals extends Test1 {

// private int wrong_answers, correct_answers;
 private int[] no_questions_ToAsk ={0,0,0,0,0};


    //---------------------THE FUNCTION THAT GETS THE QUESTION--------------------------
    public void setQuestion(){
        isQuestionLeft = false;
        for(int i = 0; i<5 && !isQuestionLeft; i++){
            if(no_questions_ToAsk[i] == 0) isQuestionLeft = true;
        }

        Random rand = new Random();
        int randomNo = rand.nextInt(9);
        boolean questionAlreadyAsked = false;

        for(int i = 0; i<5 && !questionAlreadyAsked;i++){
            if(no_questions_ToAsk[i] == randomNo) questionAlreadyAsked = true;
        }

        if(no_questions_ToAsk[questionNumber-1] == 0 && !questionAlreadyAsked && isQuestionLeft){
            no_questions_ToAsk[questionNumber-1] = randomNo;
            switch (randomNo){
                case 0:
                     questionDataArray = getQuestionData("Se traverseaza pe culoarea verde a semaforului.","true");
                    break;
                case 1:
                    questionDataArray = getQuestionData("Marul este o leguma.","false");
                    break;
                case 2:
                    questionDataArray = getQuestionData("Daca ploua, este bine sa folosim umbrela.", "true");
                    break;
                case 3:
                    questionDataArray = getQuestionData("Catelul are patru picioare.", "true");
                    break;
                case 4:
                    questionDataArray = getQuestionData("Soarele este vizibil noaptea.","false");
                    break;
                case 5:
                    questionDataArray = getQuestionData("Apa are gust.","false");
                    break;
                case 6:
                    questionDataArray = getQuestionData("Apa de mare este sarata.","true");
                    break;
                case 7:
                    questionDataArray = getQuestionData("Lamaia este acra.","true");
                    break;
                case 8:
                    questionDataArray = getQuestionData("Ciresii infloresc vara.","false");
                    break;
                default:
                    questionDataArray = getQuestionData("Pinguinii zboara.","false");
            }
        } else if(isQuestionLeft) setQuestion();
    }

}

