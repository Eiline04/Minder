package com.technovation.sagetech.minder.recycler_photo;

 class RecyclerModel {
    private String imageUrl;
    private String imageName;
    public RecyclerModel() {
    }


    public RecyclerModel(String imageName, String imageUrl){
        this.imageName = imageName;
        this.imageUrl = imageUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }
}
