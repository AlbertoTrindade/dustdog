package br.ufpe.cin.dustdog.world;

import br.ufpe.cin.dustdog.Assets;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

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
		
		renderSpot();
		
		batch.end();
	}
	
	public void renderSpot() {
		TextureRegion keyFrame = null;
		float positionX = 0;
		float positionY = 0;
		
		switch (world.spot.spotState) {
		case SPOT_FORWARD:
			keyFrame = Assets.gameObjectSpotRedAnimation.getKeyFrame(world.spot.stateTime, true);
			break;
			
		case SPOT_RIGTH:
			keyFrame = Assets.gameObjectSpotBlueAnimation.getKeyFrame(world.spot.stateTime, true);
			break;
			
		case SPOT_LEFT:
			keyFrame = Assets.gameObjectSpotGreenAnimation.getKeyFrame(world.spot.stateTime, true);
			break;
			
		case SPOT_JUMP:
			keyFrame = Assets.gameObjectSpotRedAnimation.getKeyFrame(world.spot.stateTime, true);
			break;
			
		case SPOT_DOWN:
			keyFrame = Assets.gameObjectSpotRedAnimation.getKeyFrame(world.spot.stateTime, true);
			break;
		}
		
		positionX = world.spot.position.x;
		positionY = world.spot.position.y;
		
		batch.draw(keyFrame, positionX, positionY, world.spot.velocity.x, 1);
	}
}
