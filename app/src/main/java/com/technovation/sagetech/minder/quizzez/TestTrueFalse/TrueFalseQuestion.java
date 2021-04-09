package com.technovation.sagetech.minder.quizzez.TestTrueFalse;

import com.technovation.sagetech.minder.quizzez.TestTrueFalse.Question;

public class TrueFalseQuestion implements Question {

    private String question;
    private Boolean answer;


    public TrueFalseQuestion(String question, Boolean answer) {
        this.question = question;
        this.answer = answer;
    }

    @Override
    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public Boolean getAnswer() {
        return answer;
    }

    public void setAnswer(Boolean answer) {
        this.answer = answer;
    }

    @Override
    public Boolean isCorrect(Object givenAnswer) {
        return answer == (Boolean) givenAnswer;
    }
}
