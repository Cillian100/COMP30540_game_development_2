package com.yourname.projectname;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController;
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

public class Level_Master {
    btDispatcher dispatcher;
    PerspectiveCamera cam;
    CameraInputController camController;
    ModelBatch modelBatch;
    btCollisionConfiguration collisionConfig;
    boolean forward, backwards, leftSide, rightSide, jump;
    boolean groundCollision, endOfLevelCollision;
    float upwardsMomentum;
    Sphere ballPlayer;
    Environment enviroment;
    Array<ModelInstance> instances;

    public Level_Master(){
        Bullet.init();
        ballPlayer = new Sphere(0f, 4f, 0f, 1f, 1f, 1f);
        cam = new PerspectiveCamera(67, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        cam.position.set(-10f, 7f, 0f);
        cam.lookAt(0, 0f, 0);
        cam.update();
        instances = new Array<ModelInstance>();
        instances.add(ballPlayer.getModel());

        camController = new CameraInputController(cam);
        modelBatch = new ModelBatch();

        collisionConfig = new btDefaultCollisionConfiguration();
        dispatcher = new btCollisionDispatcher(collisionConfig);

        enviroment = new Environment();
        enviroment.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.4f, 0.4f, 0.4f, 1f));
        enviroment.add(new DirectionalLight().set(0.8f, 0.8f, 0.8f, -1f, -0.8f, -0.2f));
    }

    public void masterRender(){
        final float delta = Math.min(1f/30f, Gdx.graphics.getDeltaTime());
        if(!groundCollision){
            upwardsMomentum=upwardsMomentum-0.3f;
        }
        if(forward==true){
            ballPlayer.move(delta*5, 0f, 0f);
        }
        if(backwards==true){
            ballPlayer.move(-delta*5, 0f, 0f);
        }
        if(rightSide==true){
            ballPlayer.move(0f, 0f, delta*5);
        }
        if(leftSide==true){
            ballPlayer.move(0f, 0f, delta*-5);
        }
        if(groundCollision==true && jump==true){
            upwardsMomentum=300;
        }

        ballPlayer.move(0f, delta*upwardsMomentum, 0f);

        if(groundCollision){
            upwardsMomentum=0;
        }

        cam.position.set(ballPlayer.getVector().x-10, 10f, ballPlayer.getVector().z);
        cam.lookAt(ballPlayer.getVector().x, 0f, ballPlayer.getVector().z);
        cam.update();

        camController.update();
        Gdx.gl.glClearColor(0.3f, 0.3f, 0.3f, 1.f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

        modelBatch.begin(cam);
        modelBatch.render(instances, enviroment);
        modelBatch.end();
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
