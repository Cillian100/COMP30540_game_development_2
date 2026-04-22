package com.yourname.projectname.logic;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class UserInput {
    int previousFrames;
    float startX, startY;
    boolean wasTouched;
    public UserInput(){
        previousFrames=0;
        startX=0;
        startY=0;
        wasTouched=false;
    }

    public void androidInput(){
    }

    public int keyBoardInput1(int currentFrames, int menuPosition, int modulo){
        if(Gdx.input.isKeyPressed(Input.Keys.DOWN) || Gdx.input.isKeyPressed(Input.Keys.S)){
            if(currentFrames > previousFrames+10){
                menuPosition++;
                menuPosition=menuPosition%modulo;
            }

            previousFrames=currentFrames;
        }

        if(Gdx.input.isKeyPressed(Input.Keys.UP) || Gdx.input.isKeyPressed(Input.Keys.W)){
            if(currentFrames > previousFrames+10){
                menuPosition--;
                if(menuPosition<0){
                    menuPosition=modulo-1;
                }
            }
        }

        return menuPosition;
    }

    public int loadOpenLevel(boolean justOpenedMenu, int currentLevel, int menuPosition2){
        if((Gdx.input.isKeyJustPressed(Input.Keys.ENTER) || Gdx.input.justTouched()) && menuPosition2==0 && !justOpenedMenu){
            currentLevel=1;
        }

        if((Gdx.input.isKeyJustPressed(Input.Keys.ENTER) || Gdx.input.justTouched()) && menuPosition2==1){
            currentLevel=2;
        }

        if((Gdx.input.isKeyJustPressed(Input.Keys.ENTER) || Gdx.input.justTouched()) && menuPosition2==2){
            currentLevel=3;
        }

        if((Gdx.input.isKeyJustPressed(Input.Keys.ENTER) || Gdx.input.justTouched()) && menuPosition2==3){
            currentLevel=4;
        }

        return currentLevel;
    }

    public int androidInput(int currentFrames, int menuPosition, int modulo, FitViewport viewport){
        Vector2 touch = viewport.unproject(new Vector2(Gdx.input.getX(), Gdx.input.getY()));
        float tx = touch.x;
        float ty = touch.y;

        if(ty<13 && ty>11){
            menuPosition=0;
        }

        if(ty<11 && ty>9){
            menuPosition=1;
        }

        if(ty<9 && ty>7){
            menuPosition=2;
        }

        if(ty<7 && ty>5){
            menuPosition=3;
        }

        return menuPosition;
    }

    public int androidInput2(int currentFrames, int menuPosition, int modulo, FitViewport viewport, float[][] boxes){
        Vector2 touch = viewport.unproject(new Vector2(Gdx.input.getX(), Gdx.input.getY()));
        float touchX = touch.x;
        float touchY = touch.y;

        for(int a=0;a<boxes.length;a++){
            float boxX = boxes[a][0];
            float boxY = boxes[a][1];
            float boxW = boxes[a][2];
            float boxH = boxes[a][3];

            if (touchX > boxX && touchX < boxX + boxW && touchY > boxY && touchY < boxY + boxH) {
                System.out.println(a);
                return a;
            }
        }

        return 0;
    }

    public int levelAndroidInput(FitViewport viewport){
        Vector2 touch = viewport.unproject(new Vector2(Gdx.input.getX(), Gdx.input.getY()));
        float tx = touch.x;
        System.out.println("test"  + tx);
        return 0;
    }
}
