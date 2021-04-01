package com.technovation.sagetech.minder.quizzez;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class ImageNameQuestionModel {

    private String photoUri;
    private Object answer;
    private String firstOption;
    private String secondOption;
    private String thirdOption;
    private List<Objects> optionsArray;
    private List<String> options;

    public ImageNameQuestionModel(String photoUri, Objects optionsArray) {
        this.photoUri = photoUri;
        this.optionsArray.addAll(Collections.singleton(optionsArray));
        answer = this.optionsArray.get(Integer.parseInt(String.valueOf(this.optionsArray.get(0))));
        firstOption = (this.optionsArray.get(1)).toString();
        secondOption = (this.optionsArray.get(2)).toString();
        thirdOption = (this.optionsArray.get(3)).toString();
    }


    public String getPhotoUri() {
        return photoUri;
    }

    public List<String> getOptions(){
        options.add(firstOption);
        options.add(secondOption);
        options.add(thirdOption);
        return options;
    }


    public Boolean isCorrect(Object givenAnswer) {
        return  answer == givenAnswer;
    }
}
