package com.yourname.projectname;

import com.badlogic.gdx.ApplicationListener;

public class Main implements ApplicationListener{
    int currentLevel=0;
    StartingScreen startingScreen = new StartingScreen();
    Level_1 level_1;
    Level_2 level_2;
    Level_3 level_3;
    Level_4 level_4;
    
    @Override
    public void create() {
        level_1 = new Level_1();
        level_2 = new Level_2();
        level_3 = new Level_3();
        level_4 = new Level_4();
        level_1.create();
        level_2.create();
        level_3.create();
        level_4.create();
        startingScreen.create();
    }

    @Override
    public void resize(int width, int height) {
        if(currentLevel==0){
            startingScreen.resize(width, height);
        }
        if(currentLevel==1){
            level_1.resize(width, height);
        }
        if(currentLevel==2){
            level_2.resize(width, height);
        }
        if(currentLevel==3){
            level_3.resize(width, height);
        }
        if(currentLevel==4){
            level_4.resize(width, height);
        }
    }

    @Override
    public void render() {
        System.out.println(startingScreen.getCurrentLevel());
        if(currentLevel==0){
            startingScreen.render();
            currentLevel=startingScreen.getCurrentLevel();
        }
        if(currentLevel==1){
            level_1.render();
        }
        if(currentLevel==2){
            level_2.render();
        }
        if(currentLevel==3){
            level_3.render();
        }
        if(currentLevel==4){
            level_4.render();
        }
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void dispose() {
        if(currentLevel==0){
            startingScreen.dispose();
        }
        if(currentLevel==1){
            level_1.dispose();
        }
        if(currentLevel==2){
            level_2.dispose();
        }
        if(currentLevel==3){
            level_3.dispose();
        }
        if(currentLevel==4){
            level_4.dispose();
        }
    }
       
}