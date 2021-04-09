package com.technovation.sagetech.minder.quizzez.TestWordsFittingPhotos;

public class WordFittingPhotoModel {

    private String photoUri;
    private String fittingWord;

    public WordFittingPhotoModel(String photoUri, String fittingWord){
        this.photoUri = photoUri;
        this.fittingWord = fittingWord;
    }

    public String getPhotoUri(){
        return photoUri;
    }

    public String getFittingWord(){
        return fittingWord;
    }

    public Boolean isCorrect(Object givenAnswer) {
        return photoUri.equals(givenAnswer.toString());
    }
}
