package com.yourname.projectname.levels;

import com.yourname.projectname.entities.Box;

public class Level_2 extends Level_Master{
    Box groundBox, groundBox_2;

    @Override
    public void create() {
        super.create();
        groundBox = new Box(0f, 0f, 250f,5f, 0.5f, 500f);
        instance.add(groundBox.getModel());
        groundArray.add(groundBox);

        hasGround=true;
        hasCoins=false;
        hasEndOfLevel=false;
        hasPowerUp=false;
        hasEnemy=false;
    }

    public void childRender(float delta){
    }
}
