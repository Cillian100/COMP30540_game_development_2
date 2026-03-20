package com.yourname.projectname;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController;
import com.badlogic.gdx.physics.bullet.Bullet;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.yourname.projectname.entities.Box;
import com.yourname.projectname.entities.Coin;
import com.yourname.projectname.entities.Player;

public class Test implements ApplicationListener {
	public PerspectiveCamera cam;
	public ModelBatch modelBatch;
	public Model model;
	public Array<ModelInstance> instance;
	public CameraInputController camController;
	Array<Coin> coinArray;
	Array<Box> groundArray;
	int upwardsMomentum;
	Player player;
	Environment enviroment;
	FitViewport viewport;
	SpriteBatch spriteBatch;
	boolean created=false;

	@Override
	public void create() {
		Bullet.init();
		player = new Player(0f, 4f, 0f, 1f, 1f, 1f);
		modelBatch = new ModelBatch();
		
		cam = new PerspectiveCamera(67, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		cam.position.set(10f, 10f, 10f);
		cam.lookAt(0,0,0);
		cam.near = 1f;
		cam.far = 300f;
		cam.update();

		modelBatch = new ModelBatch();
		instance = new Array<ModelInstance>();
		instance.add(player.getModel());
		viewport = new FitViewport(20, 20, cam);
		spriteBatch = new SpriteBatch();
		groundArray = new Array<Box>();
		coinArray = new Array<Coin>();

		camController = new CameraInputController(cam);
		Gdx.input.setInputProcessor(camController);

		enviroment = new Environment();
		enviroment.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.4f, 0.4f, 0.4f, 1f));
		enviroment.add();
		created=true;
	}

	public boolean getCreated(){
		return created;
	}

	@Override
	public void render() {
        Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
		cam.update();
		camController.update();
        modelBatch.begin(cam);
        modelBatch.render(instance, enviroment);
        modelBatch.end();
	}
	
	@Override
	public void dispose() {
		modelBatch.dispose();
		model.dispose();
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}
}