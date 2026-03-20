package com.yourname.projectname.levels;

import com.yourname.projectname.entities.Box;

public class Level_2 extends Level_Master{
    Box groundBox, groundBox_2;

    @Override
    public void create() {
        super.create();
        groundBox = new Box(0f, 0f, 0f,10f, 0.5f, 5f);
        groundBox_2 = new Box(10f, 3f, 0f, 10f, 0.5f, 5f);
        instance.add(groundBox.getModel());
        instance.add(groundBox_2.getModel());
        groundArray.add(groundBox);
        groundArray.add(groundBox_2);

        hasGround=true;
        hasCoins=false;
        hasEndOfLevel=false;
        hasPowerUp=false;
        hasEnemy=false;
    }
}
