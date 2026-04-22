package com.yourname.projectname.entities;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.bullet.collision.btCollisionObject;
import com.badlogic.gdx.utils.Array;

public class Skull extends Box{
    private AssetManager assets;
    private Array<ModelInstance> instances = new Array<>();
    private Model skull;
    private ModelInstance skullInstance;
    private Matrix4 collisionTransform;
    int direction1, health;

    public Skull(float x, float y, float z, float width, float height, float depth){
        super(x, y, z, width, height, depth);
        assets = new AssetManager();
        assets.load("data/skull.obj", Model.class);
        assets.finishLoading();
        doneLoading();

        myModel = new ModelInstance(skull);
        myModel.transform.setToTranslation(x, y, z).rotate(Vector3.Y, 0).rotate(Vector3.X, 270).rotate(Vector3.Z, 180).scale(0.2f, 0.2f, 0.2f);

        myShape.setLocalScaling(new Vector3(0.2f, 0.2f, 0.2f));
        collisionTransform = new Matrix4().setToTranslation(x, y, z).rotate(Vector3.X, 270).rotate(Vector3.Z, 180);
        myObject = new btCollisionObject();
        myObject.setCollisionShape(myShape);
        myObject.setWorldTransform(collisionTransform);
        direction1=-1;
        health=5;
    }

    public void movementFunction(float minX, float maxX, float delta, float speed){
        if(getX()>maxX){
            direction1=1;
        }

        if(getX()<minX){
            direction1=-1;
        }
        move(direction1*delta*speed, 0, 0);
    }

    public void reduceHealth(){
        health=health-1;
    }

    public int getHealth(){
        return health;
    }

    private void doneLoading(){
        skull = assets.get("data/skull.obj", Model.class);
        skullInstance = new ModelInstance(skull);
        skullInstance.transform.setToRotation(Vector3.Y, 180).trn(0f, 0f, 6f);
        instances.add(skullInstance);
    }

    @Override
    public btCollisionObject getObject(){
        Vector3 pos = myModel.transform.getTranslation(new Vector3());
        collisionTransform.setToTranslation(pos).rotate(Vector3.X, 270).rotate(Vector3.Z, 180);
        myObject.setWorldTransform(collisionTransform);
        return myObject;
    }

    public boolean detectPlayer(Player player){
        if(player.getZ()+20>getZ()){
            return true;
        }

        return false;
    }
}
