package com.yourname.projectname.entities;

import java.util.Vector;
import java.util.ArrayList;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.bullet.collision.btCollisionObject;
import com.badlogic.gdx.utils.Array;
import com.yourname.projectname.entities.BulletEntityPlayer;
import com.badlogic.gdx.graphics.g3d.ModelInstance;

public class Player extends Box{
    private Array<ModelInstance> instances_poop = new Array<ModelInstance>();
    public ArrayList<BulletEntityPlayer> bulletVector = new ArrayList<BulletEntityPlayer>();
    public BulletEntityPlayer bullet;
    private AssetManager assets;
    private boolean powerUp;
    private float verticalSpeed, verticalAcceleration, movementSpeed, powerUpValue, waitValue, groundLevel;
    public int powerUpSpeed=1, health=10, immune, currentFrames;
    private Model ship;
    private ModelInstance shipInstance;

    public Player(float x, float y, float z, float width, float height, float depth) {
        super(x, y, z, width, height, depth);
        assets = new AssetManager();
        assets.load("data/ship.obj", Model.class);
        assets.finishLoading();
        doneLoading();

        myModel = new ModelInstance(ship);
        myModel.transform.setToTranslation(x, y, z).rotate(Vector3.Y, 270);
        myObject = new btCollisionObject();
        myObject.setCollisionShape(myShape);
        myObject.setWorldTransform(myModel.transform);
        verticalSpeed=0;
        verticalAcceleration=0;
        movementSpeed=0;
        powerUpValue=0;
        waitValue=0;
        immune=0;
        groundLevel=0;
        currentFrames=0;
    }

    public void shoot(Vector<ModelInstance> instances, int frames){
        if(currentFrames+50<frames){
            bullet = new BulletEntityPlayer(getX(), getY(), getZ(), 1f, 1f, 1f);
            bulletVector.add(bullet);
            instances.add(bulletVector.get(bulletVector.size()-1).getModel());
            currentFrames=frames;
        }
    }

    public ArrayList<BulletEntityPlayer> getBullets(){
        return bulletVector;
    }

    public void moveBullets(float delta){
        for(int a=0;a<bulletVector.size();a++){
            bulletVector.get(a).movementFunction(delta, (10+movementSpeed*powerUpSpeed));
        }
    }

    public void setGroundLevel(float setter){
        groundLevel=setter;
    }

    public float getGroundLevel(){
        return groundLevel;
    }

    public void setHealth(int variable){
        health=health-variable;
    }

    public void reduceHealth(){
        health=health-1;
    }

    public int getImmunityValue(){
        return immune;
    }

    public void reduceImmunity(){
        if(immune>0){
            immune=immune-1;
        }
    }

    public int getHealth(){
        return health;
    }

    public void setImmunity(int number){
        immune=number;
    }

    public boolean getImmunity(){
        return immune==0;
    }

    public float getPowerUpValue(){
        return powerUpValue;
    }

    public void powerUpDecrease(float delta){
        powerUpValue=powerUpValue-(float)2*delta;
    }

    public void wait(float delta){
        waitValue=waitValue+(float)delta;
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

    public void bounceBack(){
        movementSpeed=-5;
    }

    public float horizontalMovement(boolean forward, boolean backwards, boolean rightSide, boolean leftSide, float delta){
        float movement=0;

        if(rightSide==true){
            move(0f, 0f, delta*10);
        }

        if(leftSide==true){
            move(0f, 0f, -delta*10);
        }

        if(forward==true){
            if(movementSpeed<10){
                movementSpeed=movementSpeed+0.1f;
            }
            if(powerUpValue>0){
                powerUpSpeed=2;
            }else{
                powerUpSpeed=1;
            }

            movement=delta*movementSpeed*powerUpSpeed;

            move(movement, 0f, 0f);
        }

        return movement;
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
        shipInstance.transform.setToRotation(Vector3.Y, 180).trn(0f, 0f, 6f);
        instances_poop.add(shipInstance);
    }
}
