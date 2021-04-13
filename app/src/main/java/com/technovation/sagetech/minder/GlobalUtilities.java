package com.technovation.sagetech.minder;

import android.view.View;

public class GlobalUtilities {

    public static Integer GLOBAL_INDEX = 0;
    public static Integer TOTAL_SCORE = 0;
    public static final Integer TOTAL_QUESTIONS = 25;

    public GlobalUtilities() {

    }

    public static Integer getTotalScore(){return TOTAL_SCORE;}
    public static Integer getGLOBAL_INDEX(Integer localIndex){ return GLOBAL_INDEX;}

    public static Integer setGLOBAL_INDEX(){return  GLOBAL_INDEX = GLOBAL_INDEX + 1;}


    /**----------------------------Buttons functions------------------------------
    **Those functions are important because this way wou assure that the button is pressed just once for each question**/
    public static void setTwoClickableTrue(View trueBtn, View falseBtn){
        trueBtn.setClickable(true);
        falseBtn.setClickable(true);
    }

    public static void setTwoClickableFalse(View trueBtn, View falseBtn){
        trueBtn.setClickable(false);
        falseBtn.setClickable(false);
    }

    public static void setThreeClickableTrue(View firstOption, View secondOption, View thirdOption){
        firstOption.setClickable(true);
        secondOption.setClickable(true);
        thirdOption.setClickable(true);
    }

    public static void setThreeClickableFalse(View firstOption, View secondOption, View thirdOption){
        firstOption.setClickable(false);
        secondOption.setClickable(false);
        thirdOption.setClickable(false);
    }

    /** -------------------Score related functions-----------------------**/
    public static String getScorePercentage(){
        float percentageScore = (float) TOTAL_SCORE/TOTAL_QUESTIONS * 100;
        return String.valueOf(percentageScore + "% ");
    }
}
