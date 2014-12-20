package br.ufpe.cin.dustdog.world;

import br.ufpe.cin.dustdog.Assets;
import br.ufpe.cin.dustdog.parallax.ParallaxBackground;
import br.ufpe.cin.dustdog.parallax.ParallaxLayer;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class WorldRenderer {

	static final float FRUSTUM_WIDTH = 10;
	static final float FRUSTUM_HEIGHT = 15;

	World world;
	OrthographicCamera camera;
	SpriteBatch batch;
	
	ParallaxLayer backgroundLayer;
	ParallaxBackground background;

	public WorldRenderer(SpriteBatch batch, World world) {
		this.world = world;
		this.camera = new OrthographicCamera(FRUSTUM_WIDTH, FRUSTUM_HEIGHT);
		this.camera.position.set(FRUSTUM_WIDTH/2, FRUSTUM_HEIGHT/2, 0);
		this.batch = batch;
		
		this.backgroundLayer = new ParallaxLayer(Assets.backgroundRegionGameScreen, 0, 1, ((float) Assets.backgroundGameScreen.getHeight()/Assets.SCREEN_HEIGHT));
		this.background = new ParallaxBackground(backgroundLayer, camera, batch);
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
		
		background.moveY(0.07f);
		background.render();
		
		batch.end();
	}
	
	public void renderObjects() {
		batch.enableBlending();
		batch.begin();
		
		// TODO: create and call here the methods: renderSpot(), renderObstacles(), renderGarbages(), and renderSpecialItems()
		
		batch.end();
	}
}
