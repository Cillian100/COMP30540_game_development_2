package com.yourname.projectname;

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

public class Sphere{
    btCollisionShape myShape;
    btCollisionObject myObject;
    Model model;
    ModelInstance myModel;
    float x, y, z, width, height, depth;

    public Sphere(float x, float y, float z, float width, float height, float depth){
        this.x=x;
        this.y=y;
        this.z=z;
        this.width=width;
        this.height=height;
        this.depth=depth;
        Bullet.init();
        myShape = new btSphereShape(0.5f);
        ModelBuilder mb = new ModelBuilder();
        
        mb.begin();
        mb.node().id = "coin";
        mb.part("sphere", GL20.GL_TRIANGLES, Usage.Position | 
            Usage.Normal, new Material(ColorAttribute.createDiffuse(Color.YELLOW))).sphere(1f, 1f, 1f, 10, 10);
        model = mb.end();
        myModel = new ModelInstance(model, "coin");
        myModel.transform.setToTranslation(x, y, z);
        myObject = new btCollisionObject();
        myObject.setCollisionShape(myShape);
        myObject.setWorldTransform(myModel.transform);
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

        //ballObject = new btCollisionObject();
        //ballObject.setCollisionShape(ballShape);
        //ballObject.setWorldTransform(ball.transform);