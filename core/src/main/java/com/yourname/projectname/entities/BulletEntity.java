package com.yourname.projectname.entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.physics.bullet.collision.btCollisionObject;

public class BulletEntity extends Box{
    public BulletEntity(float x, float y, float z, float width, float height, float depth){
        super(x, y, z, width, height, depth);
        ModelBuilder mb = new ModelBuilder();
        model = mb.createSphere(
            1, 1, 1,
            10, 10,
            new com.badlogic.gdx.graphics.g3d.Material(ColorAttribute.createDiffuse(Color.YELLOW)),
            Usage.Position | Usage.Normal
        );
        myModel = new ModelInstance(model);
        myModel.transform.setToTranslation(x, y, z);
        myObject = new btCollisionObject();
        myObject.setCollisionShape(myShape);
        myObject.setWorldTransform(myModel.transform);
    }

    public void movementFunction(float delta, float speed){
        move(0, 0, -speed);
    }

}