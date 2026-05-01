package com.yourname.projectname;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.yourname.projectname.levels.Level_1;
import com.yourname.projectname.levels.Level_2;
import com.yourname.projectname.levels.Level_3;
import com.yourname.projectname.levels.Level_4;
import com.yourname.projectname.levels.StartingScreen;
import com.yourname.projectname.levels.End_Screen;
import com.yourname.projectname.levels.Lose_Screen;

public class Main implements ApplicationListener{
    int currentLevel=0;
    StartingScreen startingScreen;
    End_Screen endScreen;
    Lose_Screen loseScreen;
    Level_1 level_1;
    Level_2 level_2;
    Level_3 level_3;
    Level_4 level_4;
    int width=1090, height=1920;
    int score;

    Test test;

    @Override
    public void create() {
        startingScreen = new StartingScreen();
        endScreen = new End_Screen();
        loseScreen = new Lose_Screen();
        score=0;
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
        level_1.resize(1090, 1920);

        if(level_1.getNextLevel()==true){
            score=score+level_1.getScore();
            currentLevel++;
            level_1=null;
            return;
        }

        if(level_1.getDead()==true){
            score=score+level_1.getScore();
            currentLevel=11;
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
            score=score+level_2.getScore();
            currentLevel++;
            level_2=null;
            return;
        }

        if(level_2.getDead()==true){
            score=score+level_2.getScore();
            currentLevel=11;
            level_2=null;
        }
    }

    public void level3(){
        if(level_3==null){
            level_3 = new Level_3();
        }

        if(level_3.getCreated()==false){
            level_3.create();
        }
        level_3.render();

        if(level_3.getNextLevel()==true){
            score=score+level_3.getScore();
            currentLevel++;
            level_3=null;
            return;
        }

        if(level_3.getDead()==true){
            score=score+level_3.getScore();
            currentLevel=11;
            level_3=null;
        }
    }

    public void level4(){
        if(level_4==null){
            level_4 = new Level_4();
        }

        if(level_4.getCreated()==false){
            level_4.create();
        }
        level_4.render();

        if(level_4.getNextLevel()==true){
            score=score+level_4.getScore();
            currentLevel=10;
            level_4=null;
            return;
        }

        if(level_4.getDead()==true){
            score=score+level_4.getScore();
            currentLevel=11;
            level_4=null;
        }
    }


    public void startingScreenFunction(int width, int height){
        if(startingScreen==null){
            startingScreen = new StartingScreen();
        }
        if(startingScreen.getCreated()==false){
            startingScreen.create();
        }
        startingScreen.resize(width, height);
        startingScreen.render();

        if(startingScreen.getCurrentLevel()!=0){
            currentLevel=startingScreen.getCurrentLevel();
            startingScreen = null;
        }
    }

    public void endScreenFunction(int width, int height){
        if(endScreen==null){
            endScreen = new End_Screen();
        }
        if(endScreen.getCreated()==false){
            endScreen.create();
        }
        endScreen.setScore(score);
        
        endScreen.resize(width, height);
        endScreen.render();

        if(endScreen.getCurrentLevel()!=10){
            currentLevel=endScreen.getCurrentLevel();
            endScreen=null;
        }
    }

    public void loseScreenFunction(int width, int height){
        if(loseScreen==null){
            loseScreen = new Lose_Screen();
        }
        if(loseScreen.getCreated()==false){
            loseScreen.create();
        }
        loseScreen.setScore(score);

        loseScreen.resize(width, height);
        loseScreen.render();

        if(loseScreen.getCurrentLevel()!=11){
            currentLevel=endScreen.getCurrentLevel();
            endScreen=null;
        }
    }

    @Override
    public void render(){
        if(currentLevel==0){
            startingScreenFunction(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        }
        
        if(currentLevel==1){
            level1();
        }
        
        if(currentLevel==2){
            level2();
        }

        if(currentLevel==3){
            level3();
        }

        if(currentLevel==4){
            level4();
        }

        if(currentLevel==10){
            endScreenFunction(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        }

        if(currentLevel==11){
            loseScreenFunction(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
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
