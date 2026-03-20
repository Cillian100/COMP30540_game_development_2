package com.yourname.projectname.entities;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.physics.bullet.collision.btCollisionObject;

public class EndOfLevel extends Box{
    AssetManager assets;
    boolean loading;
    Model endOfLevel;
    ModelInstance endOfLevelInstance;

    public EndOfLevel(float x, float y, float z, float width, float height, float depth) {
        super(x, y, z, width, height, depth);
        assets = new AssetManager();
        assets.load("data/endOfLevel.obj", Model.class);
        assets.finishLoading();
        loading=false;
        doneLoading();

        myModel = new ModelInstance(endOfLevelInstance);
        myModel.transform.setToTranslation(x, y, z);
        myObject = new btCollisionObject();
        myObject.setCollisionShape(myShape);
        myObject.setWorldTransform(myModel.transform);
    }

    private void doneLoading(){
        endOfLevel = assets.get("data/endOfLevel.obj", Model.class);
        endOfLevelInstance = new ModelInstance(endOfLevel);
        loading=false;
    }
    
}
