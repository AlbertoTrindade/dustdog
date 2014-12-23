package br.ufpe.cin.dustdog.world;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class WorldRenderer {

	static final float FRUSTUM_WIDTH = 10;
	static final float FRUSTUM_HEIGHT = 15;

	World world;
	OrthographicCamera camera;
	SpriteBatch batch;

	public WorldRenderer(SpriteBatch batch, World world) {
		this.world = world;
		this.camera = new OrthographicCamera(FRUSTUM_WIDTH, FRUSTUM_HEIGHT);
		this.camera.position.set(FRUSTUM_WIDTH/2, FRUSTUM_HEIGHT/2, 0);
		this.batch = batch;
	}

	public void render() {
		camera.update();
		batch.setProjectionMatrix(camera.combined);
		
		renderBackground();
		renderObjects();
	}
	
	public void renderBackground() {
		batch.disableBlending();
		batch.begin();
		
		world.background.render(camera, batch);
		
		batch.end();
	}
	
	public void renderObjects() {
		batch.enableBlending();
		batch.begin();
		
		// TODO: create and call here the methods: renderSpot(), renderObstacles(), renderGarbages(), and renderSpecialItems()
		
		batch.end();
	}
}
