package com.yourname.projectname.levels;

import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.physics.bullet.collision.btCollisionObject;
import com.badlogic.gdx.physics.bullet.collision.btCollisionShape;
import com.badlogic.gdx.physics.bullet.collision.btSphereShape;
import com.yourname.projectname.entities.Box;
import com.yourname.projectname.entities.Coin;
import com.yourname.projectname.entities.EndOfLevel;
import com.yourname.projectname.entities.Enemy;
import com.yourname.projectname.entities.PowerUp;

public class Level_1 extends Level_Master{
    ModelInstance ground;
    ModelInstance ground_2;
    ModelInstance ball;
    btCollisionShape ballShape;
    btCollisionObject ballObject;
    Coin coin;
    PowerUp powerUp;
    Enemy enemy, enemy2;
    Box groundBox_3, groundBox_2, groundBox_1, end_of_level;
    //EndOfLevel endOfLevel;

    @Override
    public void create() {
        super.create();
        coin = new Coin(0f, 0.5f, 10f, 1f, 1f, 1f);
        enemy = new Enemy(0f, 0.5f, 35f, 1f, 1f, 1f);
        enemy2 = new Enemy(0f, 0.5f, 40f, 1f, 1f, 1f);
        powerUp = new PowerUp(0f, 0.5f, 25f, 1f, 1f, 1f);
        groundBox_3 = new Box(0f, 0f, 0f, 10f, 0.5f, 25f);
        groundBox_2 = new Box(0f, 0f, 30f, 10f, 0.5f, 25f);
        groundBox_1 = new Box(0f, 0f, 60f,10f, 0.5f, 25f);
        endOfLevel = new EndOfLevel(0f, 0f, 70f, 5f, 5f, 2f);
        coinArray.add(coin);
        powerUpArray.add(powerUp);
        enemyVector.add(enemy);
        enemyVector.add(enemy2);
        groundArray.add(groundBox_1);
        groundArray.add(groundBox_2);
        groundArray.add(groundBox_3);
        
        upwardsMomentum=0;

        ballShape = new btSphereShape(0.5f);
        
        instance.add(coin.getModel());
        instance.add(powerUp.getModel());
        instance.add(player.getModel());
        instance.add(groundBox_1.getModel());
        instance.add(groundBox_3.getModel());
        instance.add(groundBox_2.getModel());
        instance.add(endOfLevel.getModel());
        instance.add(enemy.getModel());
        instance.add(enemy2.getModel());

        hasCoins=true;
        hasEndOfLevel=true;
        hasPowerUp=true;
        hasEnemy=true;
        hasGround=true;
    }
    
    public void childRender(float delta, int frames){
        enemy.movementFunction1(-5f, 5f, delta, 3f);
        enemy2.movementFunction1(-5f, 5f, delta, 5f);
    }
}
