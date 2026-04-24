package com.yourname.projectname.levels;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.bullet.collision.btCollisionObjectArray.less;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.yourname.projectname.logic.UserInput;

import space.earlygrey.shapedrawer.ShapeDrawer;

public class StartingScreen implements ApplicationListener{
    OrthographicCamera camera;
    public SpriteBatch batch;
    public BitmapFont font;
    public FitViewport viewport;
    ShapeDrawer drawer;
    ShapeRenderer shape;
    Texture backgroundTexture, level_1, level_2, level_3, level_4;
    int menuPosition=0, menuPosition2=0;
    int frames=0;
    int previousFrames=0;
    int currentLevel=0;
    boolean loadLevel=false;
    boolean created=false;
    int width, height;
    UserInput userInput;
    float[][] boxes;

    @Override
    public void create() {
        camera = new OrthographicCamera();
        batch = new SpriteBatch();
        font = new BitmapFont();
        font.setUseIntegerPositions(false);
        backgroundTexture = new Texture("background.png");
        level_1 = new Texture("level_1.png");
        level_2 = new Texture("level_2.png");
        level_3 = new Texture("level_3.png");
        level_4 = new Texture("level_4.png");
        shape = new ShapeRenderer();

        Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.WHITE);
        pixmap.fill();
        Texture pixelTexture = new Texture(pixmap);
        pixmap.dispose();

        drawer = new ShapeDrawer(batch, new TextureRegion(pixelTexture, 0, 0, 1, 1));
        created=true;
        float aspectRatio = 9f / 16f;
        viewport = new FitViewport(9, 16, camera);
        width=9;
        height=16;
        userInput = new UserInput();
        boxes = new float[4][4];
        boxes[0] = new float[]{2, 5, 2, 2};
        boxes[1] = new float[]{5, 5, 2, 2};
        boxes[2] = new float[]{2, 2, 2, 2};
        boxes[3] = new float[]{5, 2, 2, 2};
    }

    public boolean getCreated(){
        return created;
    }

    @Override
    public void resize(int thisWidth, int thisHeight) {
        //System.out.println(thisWidth + " " + thisHeight);
        viewport.update(thisWidth, thisHeight, true);
    }

    public void input(){
        userInput.androidInput();
        boolean justOpenedLoadMenu=false;

        int currentFrames=frames;
        if(!loadLevel){
            menuPosition=userInput.keyBoardInput1(currentFrames, menuPosition, 4);
            menuPosition=userInput.androidInput(currentFrames, menuPosition, 4, viewport);
        }else{
            menuPosition2=userInput.keyBoardInput1(currentFrames, menuPosition2, 4);
            menuPosition2=userInput.androidInput2(currentFrames, menuPosition2, 4, viewport, boxes);
        }

        if( (Gdx.input.isKeyJustPressed(Keys.ENTER) || Gdx.input.justTouched()) && menuPosition==0){
            currentLevel=1;
        }

        if((Gdx.input.isKeyJustPressed(Keys.ENTER) || Gdx.input.justTouched()) && menuPosition==1){
            loadLevel=true;
            justOpenedLoadMenu=true;
            menuPosition=1;
        }

        if((Gdx.input.isKeyJustPressed(Keys.ENTER) || Gdx.input.justTouched()) && menuPosition==2){

        }
        if((Gdx.input.isKeyJustPressed(Keys.ENTER) || Gdx.input.justTouched()) && menuPosition==3){
            Gdx.app.exit();
        }

        if(loadLevel){
            currentLevel=userInput.loadOpenLevel(justOpenedLoadMenu, currentLevel, menuPosition2);
            System.out.println("current level " + currentLevel);
        }

        justOpenedLoadMenu=false;
    }

    public int getCurrentLevel(){
        return currentLevel;
    }

    @Override
    public void render() {
        input();
        ScreenUtils.clear(Color.BLACK);
        viewport.apply();
        batch.setProjectionMatrix(viewport.getCamera().combined);
        batch.begin();
        batch.draw(backgroundTexture, 0, 0, 9, 14);
        font.getData().setScale(viewport.getWorldHeight() / 200);
        font.setColor(Color.WHITE);
        font.draw(batch, "Space Runner", 0, 15.5f);
        font.getData().setScale(viewport.getWorldHeight() / 200);

        if(menuPosition==0){
            font.setColor(Color.RED);
        }else{
            font.setColor(Color.WHITE);
        }

        font.draw(batch, "Start Game", 1, 13f);

        if(menuPosition==1){
            font.setColor(Color.RED);
        }else{
            font.setColor(Color.WHITE);
        }

        font.draw(batch, "Load Level", 1, 11f);

        if(menuPosition==2){
            font.setColor(Color.RED);
        }else{
            font.setColor(Color.WHITE);
        }

        font.draw(batch, "Settings", 1, 9f);

        if(menuPosition==3){
            font.setColor(Color.RED);
        }else{
            font.setColor(Color.WHITE);
        }

        font.draw(batch, "Exit", 1, 7f);

        if(loadLevel==true){
            drawer.setColor(Color.BLACK);
            drawer.filledRectangle(1,1,7,15);

            drawer.setColor(Color.RED);
            if(menuPosition2==0){
                drawer.filledRectangle(boxes[0][0]-0.1f, boxes[0][1]-0.1f, boxes[0][2]+0.2f, boxes[0][3]+0.2f);
            }
            if(menuPosition2==1){
                drawer.filledRectangle(boxes[1][0]-0.1f, boxes[1][1]-0.1f, boxes[1][2]+0.2f, boxes[1][3]+0.2f);
            }
            if(menuPosition2==2){
                drawer.filledRectangle(boxes[2][0]-0.1f, boxes[2][1]-0.1f, boxes[2][2]+0.2f, boxes[2][3]+0.2f);
            }
            if(menuPosition2==3){
                drawer.filledRectangle(boxes[3][0]-0.1f, boxes[3][1]-0.1f, boxes[3][2]+0.2f, boxes[3][3]+0.2f);
            }
            batch.draw(level_1, boxes[0][0], boxes[0][1], boxes[0][2], boxes[0][3]);
            batch.draw(level_2, boxes[1][0], boxes[1][1], boxes[1][2], boxes[1][3]);
            batch.draw(level_3, boxes[2][0], boxes[2][1], boxes[2][2], boxes[2][3]);
            batch.draw(level_4, boxes[3][0], boxes[3][1], boxes[3][2], boxes[3][3]);
        }
        batch.end();
        frames++;

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
