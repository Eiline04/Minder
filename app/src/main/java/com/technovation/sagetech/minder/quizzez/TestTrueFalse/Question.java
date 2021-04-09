package com.technovation.sagetech.minder.quizzez.TestTrueFalse;

public interface Question {

    String getQuestion();
    Boolean isCorrect(Object givenAnswer);
}
