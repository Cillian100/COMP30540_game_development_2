package com.yourname.projectname;

import com.badlogic.gdx.ApplicationListener;

public class Level_2 extends Level_Master implements ApplicationListener{
    Box groundBox, groundBox_2;

    @Override
    public void create() {
        groundBox = new Box(0f, 0f, 0f,10f, 0.5f, 5f);
        groundBox_2 = new Box(10f, 3f, 0f, 10f, 0.5f, 5f);
        instances.add(groundBox.getModel());
        instances.add(groundBox_2.getModel());
        groundArray.add(groundBox);
        groundArray.add(groundBox_2);
    }

    @Override
    public void resize(int width, int height) {
    }

    public void input(){
        masterInput();
    }

    @Override
    public void render() {
        input();
        masterRender();
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void dispose() {
    }
}
