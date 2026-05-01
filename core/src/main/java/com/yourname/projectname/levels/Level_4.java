package com.yourname.projectname.levels;

import com.yourname.projectname.entities.Box;
import com.yourname.projectname.entities.Coin;
import com.yourname.projectname.entities.EndOfLevel;
import com.yourname.projectname.entities.TurningPoint;
import com.yourname.projectname.entities.Player;
import com.yourname.projectname.logic.CollisionDetection;


public class Level_4 extends Level_Master{
    Box[] groundBox;
    Coin[] coin;
    TurningPoint[] turningBox;
    int direction;

    @Override
    public void create(){
        super.create();
        groundBox = new Box[10];
        coin = new Coin[10];
        turningBox = new TurningPoint[2];
        groundBox[0] = new Box(0f, 0f, 15f, 5f, 0.5f, 30f);
        groundBox[1] = new Box(22.5f, 0f, 30f, 50f, 0.5f, 5f);
        groundBox[2] = new Box(45f, 0f, 55f, 5f, 0.5f, 50f);
        endOfLevel = new EndOfLevel(45f, 0f, 75f, 5f, 5f, 2f);

        // groundBox[0]: player runs +z, box spans z=0..30 at x=0
        coin[0] = new Coin(1f, 0.5f, 5f, 1f, 1f, 1f);
        coin[1] = new Coin(2f, 0.5f, 10f, 1f, 1f, 1f);
        coin[2] = new Coin(-1f, 0.5f, 20f, 1f, 1f, 1f);
        coin[3] = new Coin(1f, 0.5f, 25f, 1f, 1f, 1f);
        // groundBox[1]: player runs +x, box spans x=-2.5..47.5 at z=30
        coin[4] = new Coin(10f, 0.5f, 31f, 1f, 1f, 1f);
        coin[5] = new Coin(25f, 0.5f, 29f, 1f, 1f, 1f);
        // groundBox[2]: player runs +z, box spans z=30..80 at x=45
        coin[6] = new Coin(44f, 0.5f, 40f, 1f, 1f, 1f);
        coin[7] = new Coin(46f, 0.5f, 50f, 1f, 1f, 1f);
        coin[8] = new Coin(45f, 0.5f, 60f, 1f, 1f, 1f);
        coin[9] = new Coin(47f, 0.5f, 70f, 1f, 1f, 1f);
        turningBox[0] = new TurningPoint(0f, 5f, 32.5f, 5f, 10f, 5f);
        turningBox[1] = new TurningPoint(47.5f, 5f, 30f, 5f, 10f, 5f);


        for(int a=0;a<2;a++){
            //instance.add(turningBox[a].getModel());  
        }
        
        for(int a=0;a<3;a++){
            groundArray.add(groundBox[a]);
            instance.add(groundBox[a].getModel());
        }
        instance.add(endOfLevel.getModel());
        for(int a=0;a<10;a++){
            instance.add(coin[a].getModel());
            coinArray.add(coin[a]);
        }

        hasCoins=true;
        hasEndOfLevel=true;
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
