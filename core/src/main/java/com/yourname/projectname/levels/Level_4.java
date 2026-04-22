package com.yourname.projectname.levels;

import com.yourname.projectname.entities.Box;

public class Level_4 extends Level_Master{
    Box[] groundBox;

    @Override
    public void create(){
        super.create();
        groundBox = new Box[10];
        groundBox[0] = new Box(0f, 0f, 15f, 5f, 0.5f, 30f);
        groundBox[1] = new Box(12.5f, 0f, 30f, 30f, 0.5f, 5f);

        for(int a=0;a<2;a++){
            instance.add(groundBox[a].getModel());
            groundArray.add(groundBox[a]);
        }
    }

    public void childRender(float delta, int frames){
    }

    public void childTextRender(){

    }
}
