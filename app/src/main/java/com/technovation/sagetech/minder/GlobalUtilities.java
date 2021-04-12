package com.technovation.sagetech.minder;

public class GlobalUtilities {

    public Integer GLOBAL_INDEX = 0;

//    public GlobalUtilities(Integer GLOBAL_INDEX){
//        this.GLOBAL_INDEX += GLOBAL_INDEX;
//    }

    public GlobalUtilities() {

    }

    public Integer getGLOBAL_INDEX(Integer localIndex){ return GLOBAL_INDEX + localIndex;}

    public Integer setGLOBAL_INDEX(){return  GLOBAL_INDEX = GLOBAL_INDEX + 1;}
}
