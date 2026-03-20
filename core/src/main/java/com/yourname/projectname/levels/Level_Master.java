package com.yourname.projectname.levels;

import java.util.Vector;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController;
import com.badlogic.gdx.physics.bullet.Bullet;
import com.badlogic.gdx.physics.bullet.collision.btCollisionConfiguration;
import com.badlogic.gdx.physics.bullet.collision.btDispatcher;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.yourname.projectname.CollisionDetection;
import com.yourname.projectname.entities.Box;
import com.yourname.projectname.entities.Coin;
import com.yourname.projectname.entities.EndOfLevel;
import com.yourname.projectname.entities.Player;
import com.yourname.projectname.entities.PowerUp;

public class Level_Master  implements ApplicationListener{
    Array<Box> groundArray;
    BitmapFont font;
    boolean forward, backwards, leftSide, rightSide, jump, groundCollision, endOfLevelCollision, currentCollision, nextLevel;
    boolean hasCoins, hasEndOfLevel, hasPowerUp, hasEnemy, hasGround;
    boolean created=false;
    btCollisionConfiguration collisionConfig;
    btDispatcher dispatcher;
    CameraInputController camController;
    CollisionDetection collisionDetection;
    EndOfLevel endOfLevel;
    Environment enviroment;
    FitViewport viewport;
    float groundLevel, upwardsMomentum, powerUp=0;
    int health=0, score=0;
    PerspectiveCamera cam;
    ModelBatch modelBatch;
    Player player;
    SpriteBatch spriteBatch;
    Vector<ModelInstance> instance;
    Vector<Coin> coinArray;
    Vector<PowerUp> powerUpArray;

    @Override
    public void create() {
        Bullet.init();
        player = new Player(0f, 4f, 0f, 1f, 1f, 1f);
		cam = new PerspectiveCamera(67, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		cam.update();
        instance = new Vector<ModelInstance>();
        instance.add(player.getModel());
        font = new BitmapFont();
        viewport = new FitViewport(20, 20, cam);
        spriteBatch = new SpriteBatch();
        groundArray = new Array<Box>();
        coinArray = new Vector<Coin>();
        powerUpArray = new Vector<PowerUp>();
        modelBatch = new ModelBatch();
        enviroment = new Environment();
        enviroment.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.4f, 0.4f, 0.4f, 1f));
        enviroment.add(new DirectionalLight().set(0.8f, 0.8f, 0.8f, -1f, -0.8f, -0.2f));
        font.getData().setScale(viewport.getWorldHeight() / 8);
        groundLevel=0;
        created=true;
        collisionDetection = new CollisionDetection();
        nextLevel=false;
    }

    public boolean getNextLevel(){
        return nextLevel;
    }

    public void collisionWithCoin(){
        for(int a=0;a<coinArray.size();a++){
            if(coinArray.get(a).booleanDetectPlayer(player, collisionDetection)){
                score=score+10;
                instance.remove(coinArray.get(a).getModel());
                coinArray.remove(a);
            }
        }
    }

    public void collisionWithPowerUp(){
        for(int a=0;a<powerUpArray.size();a++){
            if(powerUpArray.get(a).booleanDetectPlayer(player, collisionDetection)){
                player.setPowerUp(true);
                instance.remove(powerUpArray.get(a).getModel());
                powerUpArray.remove(a);
            }
        }
    }

    public void collisionWithGround(float delta){
        groundCollision=false;
        for(int a=0;a<groundArray.size;a++){
            currentCollision = collisionDetection.checkCollision(player.getObject(), groundArray.get(a).getObject());
            if(currentCollision==true){
                groundLevel = groundArray.get(a).getTop();
            }
            groundCollision = groundCollision || currentCollision;
        }
        if(!groundCollision){
            player.fall(delta);
        }else{
            player.hitTheGround(delta);
        }

        if(groundCollision==true && jump==true){
            player.jump(delta);
        }
    }

    public void collisionWithEndOfLevel(){
        if(endOfLevel.booleanDetectPlayer(player, collisionDetection)){
            nextLevel=true;
        }
    }

    public void collisionWithEnemy(){

    }

    public void cameraPosition(){
        cam.position.set(player.getX()-10, groundLevel+10f, player.getZ());
        cam.lookAt(player.getX(), groundLevel, player.getZ());
        cam.update();
        Gdx.gl.glClearColor(0.3f, 0.3f, 0.3f, 1.f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
    }
    
    public void masterRender(){
        final float delta = Math.min(1f/30f, Gdx.graphics.getDeltaTime());
        if(hasCoins==true){
            collisionWithCoin();
        }
        if(hasGround==true){
            collisionWithGround(delta);
        }
        if(hasPowerUp==true){
            collisionWithPowerUp();
        }
        if(hasEndOfLevel==true){
            collisionWithEndOfLevel();
        }
        if(hasEnemy==true){
            collisionWithEnemy();
        }

        player.horizontalMovement(forward, backwards, rightSide, leftSide, delta);
        player.verticalMovement(delta);

        cameraPosition();

        modelBatch.begin(cam);
        modelBatch.render(instance, enviroment);
        modelBatch.end();

        spriteBatch.begin();

        if(player.getPowerUpValue()>0){
            player.powerUpDecrease(delta);
        }

        font.draw(spriteBatch, "Score: " + score, 10, Gdx.graphics.getHeight() - 10);
        font.draw(spriteBatch, "Health: " + health, 10, Gdx.graphics.getHeight() - 50);
        font.draw(spriteBatch, "Power Up: " + (int)player.getPowerUpValue(), 10, Gdx.graphics.getHeight() - 90);
        spriteBatch.end();
    }

    public void masterInput(){
        if(Gdx.input.isKeyPressed(Keys.W)){
            forward=true;
        }else{
            forward=false;
        }
        
        if(Gdx.input.isKeyPressed(Keys.S)){
            backwards=true;
        }else{
            backwards=false;
        }
        
        if(Gdx.input.isKeyPressed(Keys.D)){
            rightSide=true;
        }else{
            rightSide=false;
        }
        
        if(Gdx.input.isKeyPressed(Keys.A)){
            leftSide=true;
        }else{
            leftSide=false;
        }
        
        if(Gdx.input.isKeyPressed(Keys.SPACE)){
            jump=true;
        }else{
            jump=false;
        }
    }

    public boolean getCreated(){
        return created;
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void render() {
        masterInput();
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
