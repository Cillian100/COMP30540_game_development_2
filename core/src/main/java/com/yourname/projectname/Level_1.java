package com.yourname.projectname;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.physics.bullet.collision.btCollisionObject;
import com.badlogic.gdx.physics.bullet.collision.btCollisionShape;
import com.badlogic.gdx.physics.bullet.collision.btSphereShape;
import com.badlogic.gdx.utils.Array;

public class Level_1 extends Level_Master implements ApplicationListener{
    ModelInstance ground;
    ModelInstance ground_2;
    ModelInstance ball;
    btCollisionShape ballShape;
    btCollisionObject ballObject;
    Coin coin;
    Box groundBox_3, groundBox_2, groundBox_1, end_of_level;

    @Override
    public void create() {
        coin = new Coin(1f, 2f, 2.5f, 1f, 1f, 1f);
        groundBox_3 = new Box(20f, 0f, 0f, 10f, 0.5f, 5f);
        groundBox_2 = new Box(10f, 0f, 0f, 10f, 0.5f, 5f);
        groundBox_1 = new Box(0f, 0f, 0f,10f, 0.5f, 5f);
        groundArray.add(groundBox_1);
        groundArray.add(groundBox_2);
        groundArray.add(groundBox_3);
        end_of_level = new Box(25f, 0f, 0f, 2f, 5f, 5f);
        upwardsMomentum=0;

        ballShape = new btSphereShape(0.5f);
        
        instances.add(coin.getModel());
        instances.add(player.getModel());
        instances.add(groundBox_1.getModel());
        instances.add(groundBox_3.getModel());
        instances.add(groundBox_2.getModel());
        instances.add(end_of_level.getModel());
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void render() {
        input();
        masterRender();
    
        endOfLevelCollision=checkCollision(player.getObject(), end_of_level.getObject());
    }

    public void input(){
        masterInput();
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void dispose() {
    }
      
}
