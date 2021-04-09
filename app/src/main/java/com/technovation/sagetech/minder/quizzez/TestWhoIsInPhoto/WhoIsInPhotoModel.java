package com.technovation.sagetech.minder.quizzez.TestWhoIsInPhoto;

public class WhoIsInPhotoModel {

    private String photoUri;
    private String name;

    public WhoIsInPhotoModel(String name, String photoUri){
        this.photoUri = photoUri;
        this.name = name;
    }

    public String getPhotoUri(){
        return photoUri;
    }
    public String getName(){return name;}

    public Boolean isCorrect(Object givenAnswer) {
        return photoUri.equals(givenAnswer.toString());
    }

}
