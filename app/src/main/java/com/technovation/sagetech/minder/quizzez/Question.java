package com.technovation.sagetech.minder.quizzez;

public interface Question {

    String getQuestion();
    Boolean isCorrect(Object givenAnswer);
}
