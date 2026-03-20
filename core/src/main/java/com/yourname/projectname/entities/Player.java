package com.yourname.projectname.entities;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.bullet.collision.btCollisionObject;
import com.badlogic.gdx.utils.Array;

public class Player extends Box{
    private Array<ModelInstance> instances_poop = new Array<ModelInstance>();
    private AssetManager assets;
    private boolean powerUp;
    private float verticalSpeed, verticalAcceleration, movementSpeed, powerUpValue;
    private int powerUpSpeed=1;
    private Model ship;
    private ModelInstance shipInstance;

    public Player(float x, float y, float z, float width, float height, float depth) {
        super(x, y, z, width, height, depth);
        assets = new AssetManager();
        assets.load("data/ship.obj", Model.class);
        assets.finishLoading();
        doneLoading();

        myModel = new ModelInstance(ship);
        myModel.transform.setToTranslation(x, y, z);
        myObject = new btCollisionObject();
        myObject.setCollisionShape(myShape);
        myObject.setWorldTransform(myModel.transform);
        verticalSpeed=0;
        verticalAcceleration=0;
        movementSpeed=10;
        powerUpValue=0;
    }

    public float getPowerUpValue(){
        return powerUpValue;
    }

    public void powerUpDecrease(float delta){
        powerUpValue=powerUpValue-(float)2*delta;
    }
    
    public void setPowerUp(boolean trueOrFalse){
        powerUpValue=5;
    }

    public boolean getPowerUp(){
        return powerUp;
    }

    public void jump(float delta){
        verticalSpeed=10;
    }

    public void fall(float delta){
        if(verticalAcceleration<1){
            verticalAcceleration=verticalAcceleration-0.01f;
        }
    }

    public void horizontalMovement(boolean forward, boolean backwards, boolean rightSide, boolean leftSide, float delta){
        if(powerUpValue>0){
            powerUpSpeed=2;
        }else{
            powerUpSpeed=1;
        }
        
        if(forward==true){
            move(delta*movementSpeed*powerUpSpeed, 0f, 0f);
        }
        if(backwards==true){
            move(-delta*movementSpeed*powerUpSpeed, 0f, 0f);
        }
        if(rightSide==true){
            move(0f, 0f, delta*movementSpeed);
        }
        if(leftSide==true){
            move(0f, 0f, delta*-movementSpeed);
        }
    }

    public void verticalMovement(float delta){
        verticalSpeed=verticalSpeed+verticalAcceleration;
        move(0, verticalSpeed*delta, 0);
    }

    public void hitTheGround(float delta){
        verticalSpeed=0;
        verticalAcceleration=0;
    }

    private void doneLoading(){
        ship = assets.get("data/ship.obj", Model.class);
        shipInstance = new ModelInstance(ship);
        shipInstance.transform.setToRotation(Vector3.Y, 90).trn(0, 0, 6f);
        instances_poop.add(shipInstance);
    }
}
