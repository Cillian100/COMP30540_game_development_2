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

public class Lose_Screen implements ApplicationListener{
    OrthographicCamera camera;
    SpriteBatch batch;
    BitmapFont font;
    FitViewport viewport;
    ShapeDrawer drawer;
    ShapeRenderer shape;
    Texture backgroundTexture;
    boolean created=false;
    int width, height, score, currentLevel;
    UserInput userInput;

    @Override
    public void create(){
        camera = new OrthographicCamera();
        batch = new SpriteBatch();
        font = new BitmapFont();
        font.setUseIntegerPositions(false);
        backgroundTexture = new Texture("background.png");
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
        score=0;
        userInput = new UserInput();
        currentLevel=11;

        created=true;
    }

    public boolean getCreated(){
        return created;
    }

    public void setScore(int thisScore){
        score=thisScore;
    }

    @Override
    public void resize(int thisWidth, int thisHeight){
        viewport.update(thisWidth, thisHeight, true);
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

    public int getCurrentLevel(){
        return currentLevel;
    }

    public void input(){
        if( (Gdx.input.isKeyJustPressed(Keys.SPACE) || Gdx.input.justTouched()) ){
            currentLevel=0;
        }
    }

    @Override
    public void render() {
        input();
        ScreenUtils.clear(Color.BLACK);
        viewport.apply();
        batch.setProjectionMatrix(viewport.getCamera().combined);
        batch.begin();
        batch.draw(backgroundTexture, 0, 0, 9, 14);
        font.getData().setScale(viewport.getWorldHeight() / 150);
        font.setColor(Color.WHITE);
        font.draw(batch, "You Lose!", 1.1f, 13.5f);
        font.draw(batch, "Score: " + score, 1.1f, 11f);
        font.getData().setScale(viewport.getWorldHeight() / 350);
        font.draw(batch, "Press Space or Screen\nto Return", 1.1f, 7f);

        batch.end();
    }

}
