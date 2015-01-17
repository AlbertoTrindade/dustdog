package br.ufpe.cin.dustdog.world;

import br.ufpe.cin.dustdog.Assets;
import br.ufpe.cin.dustdog.objects.obstacles.Obstacle;
import br.ufpe.cin.dustdog.objects.obstacles.Stone;
import br.ufpe.cin.dustdog.objects.obstacles.Tree;
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

	private void renderBackground() {
		batch.disableBlending();
		batch.begin();

		world.background.render(camera, batch);

		batch.end();
	}

	private void renderObjects() {
		batch.enableBlending();
		batch.begin();

		// TODO: create and call here the methods: renderGarbages() and renderSpecialItems()

		renderObstacles();
		renderSpot();

		batch.end();
	}

	private void renderSpot() {
		TextureRegion keyFrame = null;

		switch (world.spot.spotState) {
		case GOING_FORWARD:
			keyFrame = Assets.spotGoingForwardAnimation.getKeyFrame(world.spot.stateTime, true);
			break;

		case GOING_RIGHT:
			keyFrame = Assets.spotGoingRightAnimation.getKeyFrame(world.spot.stateTime, true);			
			break;

		case GOING_LEFT:
			keyFrame = Assets.spotGoingLeftAnimation.getKeyFrame(world.spot.stateTime, true);
			break;

		case JUMPING:
			keyFrame = Assets.spotGoingRightAnimation.getKeyFrame(0, true);
			break;

		case CROUCHING:
			keyFrame = Assets.spotGoingRightAnimation.getKeyFrame(0, true);
			break;
		}

		if (world.spot.visible) batch.draw(keyFrame, world.spot.position.x, world.spot.position.y, Spot.SPOT_WIDTH, Spot.SPOT_HEIGHT);
	}
	
	private void renderObstacles() {
		for (Obstacle obstacle : world.obstacles) {
			
			if (obstacle instanceof Stone) {
				batch.draw(Assets.obstacleStone, obstacle.position.x, obstacle.position.y, Stone.STONE_WIDTH, Stone.STONE_HEIGHT);
			}
			
			if (obstacle instanceof Tree) {
				batch.draw(Assets.obstacleTree, obstacle.position.x, obstacle.position.y, Tree.TREE_WIDTH, Tree.TREE_HEIGHT);
			}
		}
	}
}
