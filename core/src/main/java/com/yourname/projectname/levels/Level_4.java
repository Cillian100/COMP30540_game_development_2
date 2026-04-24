package com.yourname.projectname.levels;

import com.yourname.projectname.entities.Box;
import com.yourname.projectname.entities.TurningPoint;
import com.yourname.projectname.entities.Player;
import com.yourname.projectname.logic.CollisionDetection;


public class Level_4 extends Level_Master{
    Box[] groundBox;
    TurningPoint[] turningBox;
    int direction;

    @Override
    public void create(){
        super.create();
        groundBox = new Box[10];
        turningBox = new TurningPoint[2];
        groundBox[0] = new Box(0f, 0f, 15f, 5f, 0.5f, 30f);
        groundBox[1] = new Box(22.5f, 0f, 30f, 50f, 0.5f, 5f);
        groundBox[2] = new Box(45f, 0f, 55f, 5f, 0.5f, 50f);
        turningBox[0] = new TurningPoint(0f, 1f, 32.5f, 5f, 0.5f, 5f);
        turningBox[1] = new TurningPoint(47.5f, 1f, 30f, 5f, 0.5f, 5f);


        for(int a=0;a<2;a++){
            instance.add(turningBox[a].getModel());  
        }
        
        for(int a=0;a<3;a++){
            groundArray.add(groundBox[a]);
            instance.add(groundBox[a].getModel());
        }

        hasCoins=false;
        hasEndOfLevel=false;
        hasPowerUp=false;
        hasEnemy=false;
        hasGround=true;
        hasSkull=false;

        direction=1;
    }

    public void childRender(float delta, int frames){
    }

    public void childTextRender(){
    }

    public int changeDirection(Player player, CollisionDetection collision){
        if(turningBox[0].booleanDetectPlayer(player, collision)){
            direction=2;
            player.rotateModel(direction, player.getX(), player.getY(), player.getZ());
        }

        if(turningBox[1].booleanDetectPlayer(player, collision)){
            direction=1;
            player.rotateModel(direction, player.getX(), player.getY(), player.getZ());
        }

        return direction;
    }
}
