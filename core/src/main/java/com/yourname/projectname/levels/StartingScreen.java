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
import com.badlogic.gdx.physics.bullet.collision.btCollisionObjectArray.less;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;

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

    @Override
    public void create() {
        camera = new OrthographicCamera();
        batch = new SpriteBatch();
        font = new BitmapFont();
        viewport = new FitViewport(20, 20, camera);
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
    }

    public boolean getCreated(){
        return created;
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    public void input(){
        boolean justOpenedLoadMenu=false;

        int currentFrames=frames;
        if(Gdx.input.isKeyPressed(Keys.DOWN) || Gdx.input.isKeyPressed(Keys.S)){
            if(currentFrames > previousFrames+10){
                if(loadLevel){
                    menuPosition2++;
                    menuPosition2=menuPosition2%4;
                }else{
                    menuPosition++;
                    menuPosition=menuPosition%4;
                }
                previousFrames=currentFrames;
            }
        }

        if(Gdx.input.isKeyPressed(Keys.UP) || Gdx.input.isKeyPressed(Keys.W)){
            if(currentFrames > previousFrames+10){
                if(loadLevel){
                    menuPosition2--;
                    if(menuPosition2<0){
                        menuPosition2=3;
                    }
                }else{
                    menuPosition--;
                    if(menuPosition<0){
                        menuPosition=2;
                    }
                }
                previousFrames=currentFrames;
            }
        }

        if(Gdx.input.isKeyJustPressed(Keys.ENTER) && menuPosition==0){
            currentLevel=1;
        }
        if(Gdx.input.isKeyJustPressed(Keys.ENTER) && menuPosition==1){
            loadLevel=true;
            justOpenedLoadMenu=true;
            menuPosition=1;
        }
        if(Gdx.input.isKeyJustPressed(Keys.ENTER) && menuPosition==2){

        }
        if(Gdx.input.isKeyJustPressed(Keys.ENTER) && menuPosition==3){
            Gdx.app.exit();
        }
        System.out.println(justOpenedLoadMenu);

        if(loadLevel && Gdx.input.isKeyJustPressed(Keys.ENTER) && menuPosition2==0 && !justOpenedLoadMenu){
            currentLevel=1;
        }
        if(loadLevel && Gdx.input.isKeyJustPressed(Keys.ENTER) && menuPosition2==1){
            currentLevel=2;
        }
        if(loadLevel && Gdx.input.isKeyJustPressed(Keys.ENTER) && menuPosition2==2){
            currentLevel=3;
        }
        if(loadLevel && Gdx.input.isKeyJustPressed(Keys.ENTER) && menuPosition2==3){
            currentLevel=4;
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
        batch.draw(backgroundTexture, 0, 0, 20, 20);
        font.getData().setScale(viewport.getWorldHeight() / 100);
        font.setColor(Color.WHITE);
        font.draw(batch, "Space Runner", 0, 19f);
        font.getData().setScale(viewport.getWorldHeight() / 150);
        
        if(menuPosition==0){
            font.setColor(Color.RED);
        }else{
            font.setColor(Color.WHITE);
        }
        
        font.draw(batch, "Start Game", 1, 16.5f);
        
        if(menuPosition==1){
            font.setColor(Color.RED);
        }else{
            font.setColor(Color.WHITE);
        }
        
        font.draw(batch, "Load Level", 1, 15f);
        
        if(menuPosition==2){
            font.setColor(Color.RED);
        }else{
            font.setColor(Color.WHITE);
        }
        
        font.draw(batch, "Settings", 1, 13.5f);
        
        if(menuPosition==3){
            font.setColor(Color.RED);
        }else{
            font.setColor(Color.WHITE);
        }
        
        font.draw(batch, "Exit", 1, 12f);

        if(loadLevel==true){
            drawer.setColor(Color.BLACK);
            drawer.filledRectangle(2,2,15,15);

            drawer.setColor(Color.RED);
            if(menuPosition2==0){
                drawer.filledRectangle(3, 10, 6, 6);
            }
            if(menuPosition2==1){
                drawer.filledRectangle(10, 10, 6, 6);
            }
            if(menuPosition2==2){
                drawer.filledRectangle(3f, 3f, 6f, 6f);
            }
            if(menuPosition2==3){
                drawer.filledRectangle(10, 3, 6, 6);
            }
            batch.draw(level_1, 3.5f, 10.5f, 5f, 5f);
            batch.draw(level_2, 10.5f, 10.5f, 5f, 5f);
            batch.draw(level_3, 3.5f, 3.5f, 5f, 5f);
            batch.draw(level_4, 10.5f, 3.5f, 5f, 5f);
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