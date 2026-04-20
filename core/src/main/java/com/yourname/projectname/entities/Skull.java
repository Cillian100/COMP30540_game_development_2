package com.yourname.projectname.entities;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g3d.Model;

public class Skull extends Box{
    private AssetManager assets;
    public Skull(float x, float y, float z, float width, float height, float depth){
        super(x, y, z, width, height, depth);
        assets = new AssetManager();
        assets.load("data/skullgoon.obj", Model.class);
        assets.finishLoading();
        doneLoading();
    }
}
