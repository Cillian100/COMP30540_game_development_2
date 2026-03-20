package com.yourname.projectname.entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.bullet.Bullet;
import com.badlogic.gdx.physics.bullet.collision.*;
import com.yourname.projectname.CollisionDetection;

public class Box{
    btCollisionShape myShape;
    btCollisionObject myObject;
    Model model;
    ModelInstance myModel;
    float x, y, z, width, height, depth;
    Vector3 zeroVector;

    public Box(float x, float y, float z, float width, float height, float depth){
        this.x=x;
        this.y=y;
        this.z=z;
        this.width=width;
        this.height=height;
        this.depth=depth;
        Bullet.init();
        myShape = new btBoxShape(new Vector3(width/2, 0.5f, depth/2));
        ModelBuilder mb = new ModelBuilder();
        
        mb.begin();
        mb.node().id = "coin";
        mb.part("box", GL20.GL_TRIANGLES, Usage.Position | 
            Usage.Normal, new Material(ColorAttribute.createDiffuse(Color.RED))).box(width, height, depth);
        model = mb.end();
        myModel = new ModelInstance(model, "coin");
        myModel.transform.setToTranslation(x, y, z);
        myObject = new btCollisionObject();
        myObject.setCollisionShape(myShape);
        myObject.setWorldTransform(myModel.transform);
        zeroVector = new Vector3(0f, 0f, 0f);
    }

    public boolean booleanDetectPlayer(Player player, CollisionDetection collisionDetection){
        return collisionDetection.checkCollision(player.getObject(), getCollisionObject());
    }
    
    public void move(float x, float y, float z){
        myModel.transform.translate(x, y, z);
        myObject.setWorldTransform(myModel.transform);
    }

    public btCollisionObject getCollisionObject(){
        return myObject;
    }

    public ModelInstance getModel(){
        return myModel;
    }

    public btCollisionObject getObject(){
        myObject.setWorldTransform(myModel.transform);
        return myObject;
    }

    public btCollisionShape getShape(){
        return myShape;
    }

    public float getTop(){
        return y+height;
    }

    public float getXAndWidth(){
        return getX() + myModel.transform.getScaleX();
    }

    public float getX(){
        return myModel.transform.getTranslation(zeroVector).x;
    }

    public float getYAndHeight(){
        return getY() + myModel.transform.getScaleY();
    }

    public float getY(){
        return myModel.transform.getTranslation(zeroVector).y;
    }

    public float getZAndDepth(){
        return getZ() + myModel.transform.getScaleZ();
    }

    public float getZ(){
        return myModel.transform.getTranslation(zeroVector).z;
    }
}