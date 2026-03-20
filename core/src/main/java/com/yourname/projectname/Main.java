package com.yourname.projectname;

import com.badlogic.gdx.ApplicationListener;
import com.yourname.projectname.levels.Level_1;
import com.yourname.projectname.levels.Level_2;
import com.yourname.projectname.levels.Level_3;
import com.yourname.projectname.levels.Level_4;
import com.yourname.projectname.levels.StartingScreen;

public class Main implements ApplicationListener{
    int currentLevel=0;
    StartingScreen startingScreen;
    Level_1 level_1;
    Level_2 level_2;
    Level_3 level_3;
    Level_4 level_4;

    Test test;
    
    @Override
    public void create() {
        startingScreen = new StartingScreen();
    }

    @Override
    public void resize(int width, int height) {
    }
    
    public void level1(){
        if(level_1==null){
            level_1 = new Level_1();
        }
        
        if(level_1.getCreated()==false){
            level_1.create();
        }
        level_1.render();

        if(level_1.getNextLevel()==true){
            currentLevel++;
            level_1=null;
        }
    }

    public void level2(){
        if(level_2==null){
            level_2 = new Level_2();
        }

        if(level_2.getCreated()==false){
            level_2.create();
        }
        level_2.render();

        if(level_2.getNextLevel()==true){
            currentLevel++;
            level_2=null;
        }
    }

    public void level3(){
        if(level_3==null){
            level_3 = new Level_3();
        }

        if(level_3.getCreated()==false){

        }
    }

    public void level4(){

    }


    public void startingScreenFunction(){
        if(startingScreen==null){
            startingScreen = new StartingScreen();
        }
        if(startingScreen.getCreated()==false){
            startingScreen.create();
        }
        startingScreen.resize(1000,1000);
        startingScreen.render();

        if(startingScreen.getCurrentLevel()!=0){
            currentLevel=startingScreen.getCurrentLevel();
            startingScreen = null;
        }
    }


    @Override
    public void render(){
        if(currentLevel==0){
            startingScreenFunction();
        }
        if(currentLevel==1){
            level1();
        }
        if(currentLevel==2){
            level2();
        }
    }

    //@Override
    //public void render() {
    //    if(currentLevel==0){
    //        startingScreen.render();
    //        currentLevel=startingScreen.getCurrentLevel();
    //    }
    //    if(currentLevel==1){
    //        level_1.render();
    //    }
    //    if(currentLevel==2){
    //        level_2.render();
    //    }
    //    if(currentLevel==3){
    //        level_3.render();
    //    }
    //    if(currentLevel==4){
    //        level_4.render();
    //    }

    //    if(currentLevel==10){
    //        test.render();
    //    }
    //}

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void dispose(){

    }

    //@Override
    //public void dispose() {
    //    if(currentLevel==0){
    //        startingScreen.dispose();
    //    }
    //    if(currentLevel==1){
    //        level_1.dispose();
    //    }
    //    if(currentLevel==2){
    //        level_2.dispose();
    //    }
    //    if(currentLevel==3){
    //        level_3.dispose();
    //    }
    //    if(currentLevel==4){
    //        level_4.dispose();
    //    }
    //}
       
}
