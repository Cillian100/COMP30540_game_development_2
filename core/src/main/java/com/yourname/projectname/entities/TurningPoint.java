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
import com.badlogic.gdx.physics.bullet.collision.btBoxShape;

public class TurningPoint extends Box{
    public TurningPoint(float x, float y, float z, float width, float height, float depth){
        super(x, y, z, width, height, depth);
        myShape = new btBoxShape(new Vector3(width/2, height/2, depth/2));
        myObject.setCollisionShape(myShape);
        ModelBuilder mb = new ModelBuilder();
        mb.begin();
        mb.node().id = "coin2";
        mb.part("box", GL20.GL_TRIANGLES, Usage.Position |
            Usage.Normal, new Material(ColorAttribute.createDiffuse(Color.BLUE))).box(width, height, depth);
        model = mb.end();
        myModel = new ModelInstance(model, "coin2");
        myModel.transform.setToTranslation(x, y, z);
    }
    
}
