package com.yourname.projectname.levels;

import com.yourname.projectname.entities.Box;
import com.yourname.projectname.entities.EndOfLevel;
import com.yourname.projectname.entities.Coin;
import com.yourname.projectname.entities.Player;
import com.yourname.projectname.logic.CollisionDetection;
import java.util.Vector;

public class Level_3 extends Level_Master{
    Box[] groundBox;
    Coin[] coin;

    @Override
    public void create() {
        super.create();
        groundBox = new Box[10];
        coin = new Coin[10];

        groundBox[0] = new Box(0f, 0f, 0f, 5f, 0.5f, 25f);
        groundBox[1] = new Box(5f, 3f, 30f, 5f, 0.5f, 25f);
        groundBox[2] = new Box(-5f, -3f, 30f, 5f, 0.5f, 25f);
        groundBox[3] = new Box(10f, 6f, 60f, 5f, 0.5f, 25f);
        groundBox[4] = new Box(-10f, -6f, 60f, 5f, 0.5f, 25f);
        groundBox[5] = new Box(-5f, -3f, 90f, 5f, 0.5f, 25);
        groundBox[6] = new Box(5f, 3f, 90f, 5f, 0.5f, 25);
        groundBox[7] = new Box(0f, 0f, 120f, 5f, 0.5f, 25f);
        endOfLevel = new EndOfLevel(0f, 0f, 130f, 5f, 5f, 2f);

        coin[0] = new Coin(1f, 0.5f, 5f, 1f, 1f, 1f);
        coin[1] = new Coin(-1f, 0.5f, 8f, 1f, 1f, 1f);
        coin[2] = new Coin(0f, 0.5f, 125f, 1f, 1f, 1f);
        coin[3] = new Coin(6f, 3.5f, 37f, 1f, 1f, 1f);
        coin[4] = new Coin(5f, 3.5f, 25f, 1f, 1f, 1f);
        coin[5] = new Coin(-6f, -2.5f, 25f, 1f, 1f, 1f);
        coin[6] = new Coin(11f, 6.5f, 70f, 1f, 1f, 1f);
        coin[7] = new Coin(-11f, -5.5f, 65f, 1f, 1f, 1f);
        coin[8] = new Coin(0f, 6.5f, 0f, 1f, 1f, 1f);
        coin[9] = new Coin(0f, 6.5f, 0f, 1f, 1f, 1f);

        for(int a=0;a<8;a++){
            instance.add(groundBox[a].getModel());
            groundArray.add(groundBox[a]);
        }
        for(int a=0;a<10;a++){
            instance.add(coin[a].getModel());
            coinArray.add(coin[a]);
        }
        instance.add(endOfLevel.getModel());


        hasGround=true;
        hasCoins=true;
        hasEndOfLevel=true;
        hasPowerUp=false;
        hasEnemy=false;
        hasSkull=false;
    }

    public void childRender(float delta, int frames){
    }

    public void childTextRender(){
    }

    public int changeDirection(Player player, CollisionDetection collision){
        return 1;
    }
}
