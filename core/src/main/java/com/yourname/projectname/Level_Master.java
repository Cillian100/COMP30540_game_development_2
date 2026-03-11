package com.yourname.projectname;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.BitmapFontCache;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.bullet.Bullet;
import com.badlogic.gdx.physics.bullet.collision.CollisionObjectWrapper;
import com.badlogic.gdx.physics.bullet.collision.btCollisionAlgorithm;
import com.badlogic.gdx.physics.bullet.collision.btCollisionConfiguration;
import com.badlogic.gdx.physics.bullet.collision.btCollisionDispatcher;
import com.badlogic.gdx.physics.bullet.collision.btCollisionObject;
import com.badlogic.gdx.physics.bullet.collision.btDefaultCollisionConfiguration;
import com.badlogic.gdx.physics.bullet.collision.btDispatcher;
import com.badlogic.gdx.physics.bullet.collision.btDispatcherInfo;
import com.badlogic.gdx.physics.bullet.collision.btManifoldResult;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class Level_Master {
    btDispatcher dispatcher;
    PerspectiveCamera cam;
    CameraInputController camController;
    ModelBatch modelBatch;
    btCollisionConfiguration collisionConfig;
    boolean forward, backwards, leftSide, rightSide, jump;
    boolean groundCollision, endOfLevelCollision, currentCollision;
    float upwardsMomentum;
    Player player;
    Environment enviroment;
    Array<ModelInstance> instances;
    Array<Box> groundArray;
    float groundLevel;
    BitmapFont font;
    FitViewport viewport;
    SpriteBatch spriteBatch;
    int health=0, score=0;


    public Level_Master(){
        Bullet.init();
        player = new Player(0f, 4f, 0f, 1f, 1f, 1f);
        cam = new PerspectiveCamera(67, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        cam.position.set(-10f, 7f, 0f);
        cam.lookAt(0, 0f, 0);
        cam.update();
        instances = new Array<ModelInstance>();
        instances.add(player.getModel());
        font = new BitmapFont();
        viewport = new FitViewport(20, 20, cam);
        spriteBatch = new SpriteBatch();
        groundArray = new Array<Box>();

        camController = new CameraInputController(cam);
        modelBatch = new ModelBatch();

        collisionConfig = new btDefaultCollisionConfiguration();
        dispatcher = new btCollisionDispatcher(collisionConfig);

        enviroment = new Environment();
        enviroment.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.4f, 0.4f, 0.4f, 1f));
        enviroment.add(new DirectionalLight().set(0.8f, 0.8f, 0.8f, -1f, -0.8f, -0.2f));
        font.getData().setScale(viewport.getWorldHeight() / 8);
        groundLevel=0;
    }

    public void masterRender(){
        groundCollision=false;
        for(int a=0;a<groundArray.size;a++){
            currentCollision = checkCollision(player.getObject(), groundArray.get(a).getObject());
            if(currentCollision==true){
                groundLevel = groundArray.get(a).getTop();
            }
            groundCollision = groundCollision || currentCollision;
        }

        final float delta = Math.min(1f/30f, Gdx.graphics.getDeltaTime());
        if(!groundCollision){
            upwardsMomentum=upwardsMomentum-0.3f;
        }
        if(forward==true){
            player.move(delta*5, 0f, 0f);
        }
        if(backwards==true){
            player.move(-delta*5, 0f, 0f);
        }
        if(rightSide==true){
            player.move(0f, 0f, delta*5);
        }
        if(leftSide==true){
            player.move(0f, 0f, delta*-5);
        }
        if(groundCollision==true && jump==true){
            upwardsMomentum=300;
        }

        player.move(0f, delta*upwardsMomentum, 0f);
        if(groundCollision){
            upwardsMomentum=0;
        }

        cam.position.set(player.getVector().x-10, 10f, player.getVector().z);
        cam.lookAt(player.getVector().x, groundLevel, player.getVector().z);
        cam.update();

        camController.update();
        Gdx.gl.glClearColor(0.3f, 0.3f, 0.3f, 1.f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

        modelBatch.begin(cam);
        modelBatch.render(instances, enviroment);
        modelBatch.end();

        spriteBatch.begin();
        font.draw(spriteBatch, "Score: " + score, 10, Gdx.graphics.getHeight() - 10);
        font.draw(spriteBatch, "Health: " + health, 10, Gdx.graphics.getHeight() - 50);
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

    boolean checkCollision(btCollisionObject obj0, btCollisionObject obj1){
        CollisionObjectWrapper co0 = new CollisionObjectWrapper(obj0);
        CollisionObjectWrapper co1 = new CollisionObjectWrapper(obj1);

        btCollisionAlgorithm algorithm = dispatcher.findAlgorithm(co0.wrapper, co1.wrapper, null, 0);

        btDispatcherInfo info = new btDispatcherInfo();
        btManifoldResult result = new btManifoldResult(co0.wrapper, co1.wrapper);
        
        algorithm.processCollision(co0.wrapper, co1.wrapper, info, result);

        boolean r = result.getPersistentManifold().getNumContacts()>0;

        dispatcher.freeCollisionAlgorithm(algorithm.getCPointer());
        result.dispose();
        info.dispose();
        co0.dispose();
        co1.dispose();

        return r;
    }

}
