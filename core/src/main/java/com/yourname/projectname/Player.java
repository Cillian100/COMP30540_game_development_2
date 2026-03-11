package com.yourname.projectname;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.bullet.collision.btCollisionObject;
import com.badlogic.gdx.physics.bullet.softbody.btSoftBody.Material;
import com.badlogic.gdx.utils.Array;

public class Player extends Sphere{
    AssetManager assets;
    boolean loading;
    Model ship;
    Array<ModelInstance> instances_poop = new Array<ModelInstance>();

    public Player(float x, float y, float z, float width, float height, float depth) {
        super(x, y, z, width, height, depth);
        assets = new AssetManager();
        assets.load("data/ship.obj", Model.class);
        assets.finishLoading();
        loading=false;
        doneLoading();

        myModel = new ModelInstance(ship);
        myModel.transform.setToTranslation(x, y, z);
        myObject = new btCollisionObject();
        myObject.setCollisionShape(myShape);
        myObject.setWorldTransform(myModel.transform);
    }

    private void doneLoading(){
        ship = assets.get("data/ship.obj", Model.class);
        ModelInstance shipInstance = new ModelInstance(ship);
        shipInstance.transform.setToRotation(Vector3.Y, 90).trn(0, 0, 6f);
        instances_poop.add(shipInstance);
        loading = false;
    }
}
