package com.technovation.sagetech.minder.quizzez;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class ImageNameQuestionModel {

    public String photoUri;
    public Object answer;
    public String firstOption;
    public String secondOption;
    public String thirdOption;
    //private List<Objects> optionsArray;
    //private ArrayList<Objects> optionsArray;
    public ArrayList<String> options;

    public ImageNameQuestionModel(String photoUri, ArrayList<Objects> optionsArray) {
        this.photoUri = photoUri;
       // this.optionsArray.addAll(optionsArray);
        answer = optionsArray.get(Integer.parseInt(String.valueOf(optionsArray.get(0))));
        firstOption = String.valueOf(optionsArray.get(1));
        secondOption = String.valueOf(optionsArray.get(2));
        thirdOption = String.valueOf(optionsArray.get(3));
    }


    public String getPhotoUri() {
        return photoUri;
    }

//    public List<String> getOptions(){
//        options.add(firstOption);
//        options.add(secondOption);
//        options.add(thirdOption);
//        return options;
//    }


public String getFirstOption(){return firstOption;}
public String getSecondOption(){return secondOption;}
public String getThirdOption(){return thirdOption;}

    public Boolean isCorrect(Object givenAnswer) {
        return answer.toString().equals(givenAnswer.toString());
    }
}
