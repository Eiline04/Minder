package com.technovation.sagetech.minder.quizzez;
import com.technovation.sagetech.minder.quizzez.Test1;

import java.util.Random;

/*public class Set1AdevaratFals  {
  public int score = 0;
    public int questionNumber = 1;
    protected String[] questionDataArray;
    boolean isQuestionLeft;
// private int wrong_answers, correct_answers;
 public int[] no_questions_ToAsk ={-1,-1,-1,-1,-1};
 Test1 test1varr;


    //---------------------THE FUNCTION THAT GETS THE QUESTION--------------------------
    public void setQuestion(){
        isQuestionLeft = false;
        for(int i = 0; i<5 && !isQuestionLeft; i++){
            if(no_questions_ToAsk[i] == -1) isQuestionLeft = true;
        }

        Random rand = new Random();
        int randomNo = rand.nextInt(9);
        boolean questionAlreadyAsked = false;

        for(int i = 0; i<5 && !questionAlreadyAsked;i++){
            if(no_questions_ToAsk[i] == randomNo) questionAlreadyAsked = true;
        }

        if(no_questions_ToAsk[questionNumber-1] == -1 && !questionAlreadyAsked && isQuestionLeft){
            no_questions_ToAsk[questionNumber-1] = randomNo;
            switch (randomNo){
                case 0:
                     questionDataArray = test1varr.getQuestionData("Se traverseaza pe culoarea verde a semaforului.","true");
                    break;
                case 1:
                    questionDataArray = test1varr.getQuestionData("Marul este o leguma.","false");
                    break;
                case 2:
                    questionDataArray = test1varr.getQuestionData("Daca ploua, este bine sa folosim umbrela.", "true");
                    break;
                case 3:
                    questionDataArray = test1varr.getQuestionData("Catelul are patru picioare.", "true");
                    break;
                case 4:
                    questionDataArray = test1varr.getQuestionData("Soarele este vizibil noaptea.","false");
                    break;
                case 5:
                    questionDataArray = test1varr.getQuestionData("Apa are gust.","false");
                    break;
                case 6:
                    questionDataArray = test1varr.getQuestionData("Apa de mare este sarata.","true");
                    break;
                case 7:
                    questionDataArray = test1varr.getQuestionData("Lamaia este acra.","true");
                    break;
                case 8:
                    questionDataArray = test1varr.getQuestionData("Ciresii infloresc vara.","false");
                    break;
                default:
                   questionDataArray = test1varr.getQuestionData("Pinguinii zboara.","false");
            }
        } else if(isQuestionLeft) setQuestion();
    }

}
*/
