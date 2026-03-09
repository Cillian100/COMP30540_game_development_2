package com.yourname.projectname;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;
import com.badlogic.gdx.graphics.g3d.*;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.bullet.Bullet;
import com.badlogic.gdx.physics.bullet.collision.*;
import com.badlogic.gdx.utils.Array;

public class Main implements ApplicationListener{
    PerspectiveCamera cam;
    CameraInputController camController;
    ModelBatch modelBatch;
    Array<ModelInstance> instances;
    Environment enviroment;
    Model model, model_2;
    ModelInstance ground;
    ModelInstance ground_2;
    ModelInstance ball;
    boolean collision;
    btCollisionShape groundShape;
    btCollisionShape ballShape;
    btCollisionShape groundShape_2;
    btCollisionObject groundObject_2;
    btCollisionObject groundObject;
    btCollisionObject ballObject;
    btCollisionConfiguration collisionConfig;
    btDispatcher dispatcher;
    boolean forward, backwards, leftSide, rightSide, jump;
    float upwardsMomentum;
    Sphere coin;
    Box groundBox_3, groundBox_2, groundBox_1;

    @Override
    public void create() {
        coin = new Sphere(4f, 0.5f, 2.5f, 1f, 1f, 1f);
        groundBox_3 = new Box(20f, 0f, 0f, 10f, 0.5f, 5f);
        groundBox_2 = new Box(10f, 0f, 0f, 10f, 0.5f, 5f);
        groundBox_1 = new Box(0f, 0f, 0f,10f, 0.5f, 5f);
        forward=false;
        backwards=false;
        leftSide=false;
        rightSide=false;
        jump=false;
        upwardsMomentum=0;

        Bullet.init();
        ballShape = new btSphereShape(0.5f);
        modelBatch = new ModelBatch();

        enviroment = new Environment();
        enviroment.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.4f, 0.4f, 0.4f, 1f));
        enviroment.add(new DirectionalLight().set(0.8f, 0.8f, 0.8f, -1f, -0.8f, -0.2f));
        
        cam = new PerspectiveCamera(67, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        cam.position.set(-10f, 7f, 0f);
        cam.lookAt(0, 0f, 0);
        cam.update();

        camController = new CameraInputController(cam);

        ModelBuilder mb = new ModelBuilder();
        ModelBuilder mb_2 = new ModelBuilder();
        mb.begin();
        mb.node().id="ball";
        mb.part("sphere", GL20.GL_TRIANGLES, Usage.Position | 
            Usage.Normal, new Material(ColorAttribute.createDiffuse(Color.GREEN))).sphere(1f, 1f, 1f, 10, 10);
        model = mb.end();

        ball = new ModelInstance(model, "ball");
        ball.transform.setToTranslation(0, 9f, 0);
        
        instances = new Array<ModelInstance>();
        instances.add(ball);
        instances.add(coin.getModel());
        instances.add(groundBox_1.getModel());
        instances.add(groundBox_3.getModel());
        instances.add(groundBox_2.getModel());

        ballObject = new btCollisionObject();
        ballObject.setCollisionShape(ballShape);
        ballObject.setWorldTransform(ball.transform);

        collisionConfig = new btDefaultCollisionConfiguration();
        dispatcher = new btCollisionDispatcher(collisionConfig);
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void render() {
        input();
        final float delta = Math.min(1f/30f, Gdx.graphics.getDeltaTime());
        
        collision=checkCollision(ballObject, groundBox_1.getObject())
            || checkCollision(ballObject, groundBox_2.getObject())  
            || checkCollision(ballObject, groundBox_3.getObject()); 
        

        if(!collision){
            upwardsMomentum=upwardsMomentum-0.3f;
            ballObject.setWorldTransform(ball.transform);
        }
        if(forward==true){
            ball.transform.translate(delta*5, 0f, 0f);
            ballObject.setWorldTransform(ball.transform);
        }
        if(backwards==true){
            ball.transform.translate(-delta*5, 0f, 0f);
            ballObject.setWorldTransform(ball.transform);
        }
        if(rightSide==true){
            ball.transform.translate(0f, 0f, delta*5);
            ballObject.setWorldTransform(ball.transform);
        }
        if(leftSide==true){
            ball.transform.translate(0f, 0f, -delta*5);
            ballObject.setWorldTransform(ball.transform);
        }
        if(collision==true && jump==true){
            upwardsMomentum=300;
        }

        ball.transform.translate(0f, delta*upwardsMomentum, 0f);
        ballObject.setWorldTransform(ball.transform);

        if(collision){
            upwardsMomentum=0;
        }

        Vector3 position = new Vector3();
        ball.transform.getTranslation(position);

        cam.position.set(position.x-10, 10f, position.z);
        cam.lookAt(position.x, 0f, position.z);
        cam.update();

        camController.update();
        Gdx.gl.glClearColor(0.3f, 0.3f, 0.3f, 1.f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

        modelBatch.begin(cam);
        modelBatch.render(instances, enviroment);
        modelBatch.end();
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

    public void input(){
        
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

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void dispose() {
        groundObject.dispose();
        groundShape.dispose();

        ballObject.dispose();
        ballShape.dispose();

        dispatcher.dispose();
        collisionConfig.dispose();

        modelBatch.dispose();
        model.dispose();
    }
    
}