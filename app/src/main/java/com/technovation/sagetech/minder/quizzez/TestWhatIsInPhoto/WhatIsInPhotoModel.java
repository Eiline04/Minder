package com.technovation.sagetech.minder.quizzez.TestWhatIsInPhoto;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class WhatIsInPhotoModel {

    private String photoUri;
    private Object answer;
    private String firstOption;
    private String secondOption;
    private String thirdOption;

    public WhatIsInPhotoModel(String photoUri, ArrayList<Objects> optionsArray) {
        this.photoUri = photoUri;
        answer = optionsArray.get(Integer.parseInt(String.valueOf(optionsArray.get(0))));
        firstOption = String.valueOf(optionsArray.get(1));
        secondOption = String.valueOf(optionsArray.get(2));
        thirdOption = String.valueOf(optionsArray.get(3));
    }


    public String getPhotoUri() {
        return photoUri;
    }

    public String getFirstOption() {
        return firstOption;
    }

    public String getSecondOption() {
        return secondOption;
    }

    public String getThirdOption() {
        return thirdOption;
    }

    public Boolean isCorrect(Object givenAnswer) {
        return answer.toString().equals(givenAnswer.toString());
    }
}
