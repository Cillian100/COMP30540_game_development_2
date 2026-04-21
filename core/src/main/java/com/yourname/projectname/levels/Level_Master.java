package com.yourname.projectname.levels;

import java.util.ArrayList;
import java.util.Vector;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.Model;import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.bullet.Bullet;
import com.badlogic.gdx.physics.bullet.collision.btCollisionConfiguration;
import com.badlogic.gdx.physics.bullet.collision.btDispatcher;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.yourname.projectname.logic.CollisionDetection;
import com.yourname.projectname.entities.Box;
import com.yourname.projectname.entities.Coin;
import com.yourname.projectname.entities.EndOfLevel;
import com.yourname.projectname.entities.Enemy;
import com.yourname.projectname.entities.Player;
import com.yourname.projectname.entities.Skull;
import com.yourname.projectname.entities.PowerUp;import com.yourname.projectname.logic.UserInput;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g3d.attributes.TextureAttribute;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.graphics.g3d.Material;

public abstract class Level_Master  implements ApplicationListener{
    Array<Box> groundArray;
    BitmapFont font;
    boolean forward, backwards, leftSide, rightSide, jump, groundCollision, endOfLevelCollision, currentCollision, nextLevel;
    boolean hasCoins, hasEndOfLevel, hasPowerUp, hasEnemy, hasGround, movementIllusion;
    boolean created=false;
    btCollisionConfiguration collisionConfig;
    btDispatcher dispatcher;
    CameraInputController camController;
    CollisionDetection collisionDetection;
    EndOfLevel endOfLevel;
    Environment enviroment;
    FitViewport viewport;
    float groundLevel, upwardsMomentum, powerUp=0, backgroundPosition=0;
    int health=0, score=0, frames=0;
    PerspectiveCamera cam;
    ModelBatch modelBatch;
    Player player;
    SpriteBatch spriteBatch;
    Vector<ModelInstance> instance;
    Vector<Coin> coinArray;
    Vector<Skull> skullArray;
    Vector<PowerUp> powerUpArray;
    ArrayList<Enemy> enemyVector;
    UserInput userInput;
    OrthographicCamera hudCam;
    Texture backgroundTexture;
    Model backgroundModel;
    ModelInstance backgroundInstance;
    float playerMovement;


    abstract void childRender(float delta, int frames);

    @Override
    public void create() {
        Bullet.init();
        player = new Player(0f, 4f, 0f, 1f, 1f, 1f);
		cam = new PerspectiveCamera(67, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		cam.update();
        instance = new Vector<ModelInstance>();
        instance.add(player.getModel());
        font = new BitmapFont();
        //viewport = new FitViewport(20, 20, cam);
        hudCam = new OrthographicCamera();
        viewport = new FitViewport(9, 16, hudCam);
        spriteBatch = new SpriteBatch();
        groundArray = new Array<Box>();
        coinArray = new Vector<Coin>();
        skullArray = new Vector<Skull>();
        powerUpArray = new Vector<PowerUp>();
        enemyVector = new ArrayList<Enemy>();
        modelBatch = new ModelBatch();
        enviroment = new Environment();
        enviroment.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.4f, 0.4f, 0.4f, 1f));
        enviroment.add(new DirectionalLight().set(0.8f, 0.8f, 0.8f, -1f, -0.8f, -0.2f));
        font.getData().setScale(viewport.getWorldHeight() / 8);
        groundLevel=0;
        created=true;
        collisionDetection = new CollisionDetection();
        nextLevel=false;
        userInput = new UserInput();
        backgroundTexture = new Texture("background.png");
        ModelBuilder mb = new ModelBuilder();

        backgroundModel = mb.createBox(100f, 0.1f, 200f,
            new Material(TextureAttribute.createDiffuse(backgroundTexture)),
            Usage.Position | Usage.Normal | Usage.TextureCoordinates);

        backgroundInstance = new ModelInstance(backgroundModel);
        instance.add(backgroundInstance);
        movementIllusion=false;
        forward=true;
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

    public boolean collisionWithSkull(){
        boolean bool=false;
        for(int a=0;a<skullArray.size();a++){
            bool=bool||skullArray.get(a).detectPlayer(player);
        }
        return bool;
    }

    public void collisionWithEndOfLevel(){
        if(endOfLevel.booleanDetectPlayer(player, collisionDetection)){
            nextLevel=true;
        }
    }

    public void cameraPosition(){
        Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        cam.position.set(player.getX(), 10f, player.getZ()-10);
        cam.lookAt(player.getX(), groundLevel, player.getZ());
        cam.update();
        Gdx.gl.glClearColor(0.3f, 0.3f, 0.3f, 1.f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
    }

    public void backgroundMovement(boolean illusionMovement, float delta, float playerMovement){
        //System.out.println(playerMovement);
        backgroundPosition = backgroundPosition+(-delta*3f)+playerMovement;
        backgroundInstance.transform.setToTranslation(player.getX(), -10f, backgroundPosition);
    }

    public float getPlayerMovement(){
        return playerMovement;
    }

    public void masterRender(float delta){
        if(hasCoins==true){
            collisionWithCoin();
        }
        if(hasGround==true){
            groundCollision=collisionDetection.collisionWithGround(player, groundArray, delta);
        }
        if(hasPowerUp==true){
            collisionWithPowerUp();
        }
        if(hasEndOfLevel==true){
            collisionWithEndOfLevel();
        }
        if(hasEnemy==true){
            collisionDetection.collisionWithEnemy(player, enemyVector);
        }

        if(groundCollision==true && jump==true){
            player.jump(delta);
        }

        forward=!collisionWithSkull();
        //System.out.println(forward);
        playerMovement=player.horizontalMovement(forward, backwards, rightSide, leftSide, delta);
        
        player.verticalMovement(delta);
        backgroundMovement(false, delta, playerMovement);
        cameraPosition();

        modelBatch.begin(cam);
        modelBatch.render(instance, enviroment);
        modelBatch.end();

        viewport.apply();
        spriteBatch.setProjectionMatrix(hudCam.combined);
        spriteBatch.begin();

        if(player.getPowerUpValue()>0){
            player.powerUpDecrease(delta);
        }

        font.draw(spriteBatch, "Score: " + score, 10, Gdx.graphics.getHeight() - 10);
        font.draw(spriteBatch, "Health: " + (int)player.getHealth(), 10, Gdx.graphics.getHeight() - 50);
        font.draw(spriteBatch, "Power Up: " + (int)player.getPowerUpValue(), 10, Gdx.graphics.getHeight() - 90);
        spriteBatch.end();
    }

    public void masterInput(){
        int movement=0, movement2=0;
        if(Gdx.input.isTouched()){
            float screenWidth = Gdx.graphics.getWidth();
            float screenHeight = Gdx.graphics.getHeight();
            float touchX = Gdx.input.getX();
            float touchY = Gdx.input.getY();

            //System.out.println(screenHeight/3 + " " + touchY);
            if(touchY < screenHeight/3){
                 movement2=1;
            }else if(touchX > screenWidth/2) {
                movement = 1;
            }else if(touchX < screenWidth/2){
                movement=2;
            }


         }
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

        if(Gdx.input.isKeyPressed(Keys.D) || Gdx.input.isKeyPressed(Keys.LEFT) || movement==1){
            rightSide=true;
        }else{
            rightSide=false;
        }

        if(Gdx.input.isKeyPressed(Keys.A) || Gdx.input.isKeyPressed(Keys.RIGHT) || movement==2){
            leftSide=true;
        }else{
            leftSide=false;
        }

        if(Gdx.input.isKeyPressed(Keys.SPACE) || movement2==1){
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
        viewport.update(width, height, true);
    }

    @Override
    public void render() {
        frames++;
        final float delta = Math.min(1f/30f, Gdx.graphics.getDeltaTime());
        masterInput();
        masterRender(delta);
        childRender(delta, frames);
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
