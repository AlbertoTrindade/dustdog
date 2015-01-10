package br.ufpe.cin.dustdog.world;

import br.ufpe.cin.dustdog.Assets;
import br.ufpe.cin.dustdog.objects.obstacles.Obstacle;
import br.ufpe.cin.dustdog.objects.obstacles.Stone;
import br.ufpe.cin.dustdog.objects.spot.Spot;

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
		renderObstacles();

		batch.end();
	}

	public void renderSpot() {
		TextureRegion keyFrame = null;
		float positionX = 0;
		float positionY = 0;

		switch (world.spot.spotState) {
		case GOING_FORWARD:
			keyFrame = Assets.spotGoingRightAnimation.getKeyFrame(0, true);
			world.spot.bounds = world.spot.frameBounds[0];
			break;

		case GOING_RIGHT:
			keyFrame = Assets.spotGoingRightAnimation.getKeyFrame(world.spot.stateTime, true);			
			world.spot.bounds = world.spot.frameBounds[Assets.spotGoingRightAnimation.getKeyFrameIndex(world.spot.stateTime)];
			break;

		case GOING_LEFT:
			keyFrame = Assets.spotGoingLeftAnimation.getKeyFrame(world.spot.stateTime, true);
			world.spot.bounds = world.spot.frameBounds[Assets.spotGoingLeftAnimation.getKeyFrameIndex(world.spot.stateTime)];
			break;

		case JUMPING:
			keyFrame = Assets.spotGoingRightAnimation.getKeyFrame(0, true);
			break;

		case CROUCHING:
			keyFrame = Assets.spotGoingRightAnimation.getKeyFrame(0, true);
			break;
		}

		positionX = world.spot.position.x;
		positionY = world.spot.position.y;

		if (world.spot.visible) batch.draw(keyFrame, positionX, positionY, Spot.SPOT_WIDTH, Spot.SPOT_HEIGHT);
	}
	
	public void renderObstacles() {
		for (Obstacle obstacle : world.obstacles) {
			TextureRegion keyFrame = null;
			
			if (obstacle instanceof Stone) {
				keyFrame = Assets.obstacleStone;
			}
			
			batch.draw(keyFrame, obstacle.position.x, obstacle.position.y, obstacle.bounds.width, obstacle.bounds.height);
		}
	}
}
