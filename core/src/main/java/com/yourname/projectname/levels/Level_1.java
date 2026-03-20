package com.yourname.projectname.levels;

import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.physics.bullet.collision.btCollisionObject;
import com.badlogic.gdx.physics.bullet.collision.btCollisionShape;
import com.badlogic.gdx.physics.bullet.collision.btSphereShape;
import com.yourname.projectname.entities.Box;
import com.yourname.projectname.entities.Coin;
import com.yourname.projectname.entities.EndOfLevel;
import com.yourname.projectname.entities.PowerUp;

public class Level_1 extends Level_Master{
    ModelInstance ground;
    ModelInstance ground_2;
    ModelInstance ball;
    btCollisionShape ballShape;
    btCollisionObject ballObject;
    Coin coin;
    PowerUp powerUp;
    Box groundBox_3, groundBox_2, groundBox_1, end_of_level;
    //EndOfLevel endOfLevel;

    @Override
    public void create() {
        super.create();
        coin = new Coin(15f, 0.5f, 1f, 1f, 1f, 1f);
        powerUp = new PowerUp(17f, 0.5f, -1f, 1f, 1f, 1f);
        groundBox_3 = new Box(30f, 0f, 0f, 10f, 0.5f, 5f);
        groundBox_2 = new Box(15f, 0f, 0f, 10f, 0.5f, 5f);
        groundBox_1 = new Box(0f, 0f, 0f,10f, 0.5f, 5f);
        coinArray.add(coin);
        powerUpArray.add(powerUp);
        groundArray.add(groundBox_1);
        groundArray.add(groundBox_2);
        groundArray.add(groundBox_3);
        //end_of_level = new Box(35f, 0f, 0f, 2f, 5f, 5f);
        endOfLevel = new EndOfLevel(35f, 0f, 0f, 2f, 5f, 5f);
        upwardsMomentum=0;

        ballShape = new btSphereShape(0.5f);
        
        instance.add(coin.getModel());
        instance.add(powerUp.getModel());
        instance.add(player.getModel());
        instance.add(groundBox_1.getModel());
        instance.add(groundBox_3.getModel());
        instance.add(groundBox_2.getModel());
        instance.add(endOfLevel.getModel());

        hasCoins=true;
        hasEndOfLevel=true;
        hasPowerUp=true;
        hasEnemy=false;
        hasGround=true;
    }      
}
