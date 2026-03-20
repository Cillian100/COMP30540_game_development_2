package com.yourname.projectname.entities;


import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.bullet.Bullet;
import com.badlogic.gdx.physics.bullet.collision.*;

public class Sphere{
    btCollisionShape myShape;
    btCollisionObject myObject;
    ModelBuilder modelBuilder;
    Model model;
    ModelInstance myModel;
    ModelBuilder mb;
    float x, y, z, width, height, depth;
    Vector3 zeroVector;

    public Sphere(float x, float y, float z, float width, float height, float depth){
        this.x=x;
        this.y=y;
        this.z=z;
        this.width=width;
        this.height=height;
        this.depth=depth;
        Bullet.init();
        myShape = new btSphereShape(0.5f);
        zeroVector = new Vector3(0f, 0f, 0f);

    }


    public float getX(){
        return myModel.transform.getTranslation(zeroVector).x;
    }
    public float getXAndRadius(){
        return getX() + 0.5f;
    }

    public ModelInstance getModel(){
        return myModel;
    }

    public btCollisionObject getObject(){
        return myObject;
    }

    public void move(float x, float y, float z){
        myModel.transform.translate(x, y, z);
        myObject.setWorldTransform(myModel.transform);
    }

    public Vector3 getVector(){
        Vector3 position = new Vector3();
        myModel.transform.getTranslation(position);
        return position;
    }
}