package br.ufpe.cin.dustdog.world;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.utils.Pool;

import br.ufpe.cin.dustdog.Assets;
import br.ufpe.cin.dustdog.objects.LaneState;
import br.ufpe.cin.dustdog.objects.LevelGenerator;
import br.ufpe.cin.dustdog.objects.LevelGeneratorObject;
import br.ufpe.cin.dustdog.objects.obstacles.Obstacle;
import br.ufpe.cin.dustdog.objects.obstacles.Stone;
import br.ufpe.cin.dustdog.objects.spot.Spot;
import br.ufpe.cin.dustdog.objects.spot.SwipeDirection;
import br.ufpe.cin.dustdog.parallax.ParallaxBackground;
import br.ufpe.cin.dustdog.parallax.ParallaxLayer;

public class World {

	public interface WorldListener {
		// TODO: add method signature for each action in World that generates sound (hit a obstacle, for example)
	}

	public static final float WORLD_WIDTH = 10;
	public static final int WORLD_VELOCITY = 5;
	public static final float SPOT_COLLISION_TIME = 3;

	public final WorldListener worldListener;

	public final ParallaxBackground background;
	public final Spot spot;

	public int score;
	public WorldState state;

	public final LevelGenerator levelGenerator;

	public final List<Obstacle> obstacles;

	public final Pool<Stone> stones;

	public boolean leftLaneIsFree;
	public boolean centralLaneIsFree;
	public boolean rightLaneIsFree;

	public boolean spotCollision;
	public float spotCollisionTimeSpent;
	public float spotCollisionStateTimeSpent;

	public World(WorldListener worldListener) {
		this.worldListener = worldListener;

		this.background = new ParallaxBackground(new ParallaxLayer(Assets.backgroundRegionGameScreen, 0, 1, ((float) Assets.backgroundGameScreen.getHeight()/Assets.SCREEN_HEIGHT)));
		this.spot = new Spot(Spot.CENTRAL_LANE_POSITION_X, Spot.SPOT_POSITION_Y, Spot.SPOT_WIDTH_FRAME_1, Spot.SPOT_HEIGHT_FRAME_1);

		this.score = 0;
		this.state = WorldState.RUNNING;

		levelGenerator = new LevelGenerator();

		obstacles = new ArrayList<Obstacle>();

		stones = new Pool<Stone>() {
			@Override
			protected Stone newObject() {
				return new Stone(0, 0, Stone.STONE_WIDTH, Stone.STONE_HEIGHT);
			}
		};

		leftLaneIsFree = true;
		centralLaneIsFree = true;
		rightLaneIsFree = true;
	}

	public void update(float deltaTime, SwipeDirection swipeDirection) {
		updateBackground(deltaTime);
		updateSpot(deltaTime, swipeDirection);
		updateObstacles(deltaTime);

		createObjects();
		checkCollisions();
	}

	public void updateBackground(float deltaTime) {
		background.moveY(WORLD_VELOCITY * deltaTime);
	}

	public void updateSpot(float deltaTime, SwipeDirection swipeDirection) {
		spot.update(deltaTime, swipeDirection);

		if (spotCollision) {
			spotCollisionTimeSpent += deltaTime;
			spotCollisionStateTimeSpent += deltaTime;

			if (spotCollisionStateTimeSpent >= SPOT_COLLISION_TIME/20) {
				spot.visible = !spot.visible;
				spotCollisionStateTimeSpent = 0;
			}

			if (spotCollisionTimeSpent >= SPOT_COLLISION_TIME) {
				spot.visible = true;
				spotCollision = false;
			}
		}
	}

	public void updateObstacles(float deltaTime) {
		centralLaneIsFree = true;
		leftLaneIsFree = true;
		rightLaneIsFree = true;

		Obstacle obstacle;

		for (int i = 0; i < obstacles.size(); i++) {
			obstacle = obstacles.get(i);			
			obstacle.update(deltaTime);

			// Check if some obstacle is not being completely showing up			
			if (obstacle.position.y >= WorldRenderer.FRUSTUM_HEIGHT - obstacle.bounds.height) {
				switch (obstacle.laneState) {
				case CENTRAL:
					centralLaneIsFree = false;
					break;

				case LEFT:
					leftLaneIsFree = false;
					break;

				case RIGHT:
					rightLaneIsFree = false;
					break;
				}
			}

			// Check if obstacle is out of the world		
			if (obstacle.position.y < -obstacle.bounds.height) {
				obstacles.remove(obstacle);

				if (obstacle instanceof Stone) {
					stones.free((Stone) obstacle);
				}				
			}
		}
	}

	public void createObjects() {

		if (leftLaneIsFree) {
			LevelGeneratorObject nextObject = levelGenerator.getNextLeftLaneObject();
			Obstacle nextObstacle = null;

			leftLaneIsFree = false;

			switch (nextObject) {

			case OBSTACLE_STONE:
				nextObstacle = stones.obtain();
				nextObstacle.position.x = Stone.LEFT_LANE_POSITION_X;
				nextObstacle.position.y = WorldRenderer.FRUSTUM_HEIGHT;
				nextObstacle.bounds.x = nextObstacle.position.x;
				nextObstacle.bounds.y = nextObstacle.position.y;
				nextObstacle.laneState = LaneState.LEFT;

				break;

			case NONE:
				leftLaneIsFree = true;
				break;
			}

			if (nextObstacle != null) {
				obstacles.add(nextObstacle);
			}
		}

		if (centralLaneIsFree) {
			LevelGeneratorObject nextObject = levelGenerator.getNextLeftLaneObject();
			Obstacle nextObstacle = null;

			centralLaneIsFree = false;

			switch (nextObject) {

			case OBSTACLE_STONE:
				nextObstacle = stones.obtain();
				nextObstacle.position.x = Stone.CENTRAL_LANE_POSITION_X;
				nextObstacle.position.y = WorldRenderer.FRUSTUM_HEIGHT;
				nextObstacle.bounds.x = nextObstacle.position.x;
				nextObstacle.bounds.y = nextObstacle.position.y;
				nextObstacle.laneState = LaneState.CENTRAL;

				break;

			case NONE:
				centralLaneIsFree = true;
				break;
			}

			if (nextObstacle != null) {
				obstacles.add(nextObstacle);
			}
		}

		if (rightLaneIsFree) {
			LevelGeneratorObject nextObject = levelGenerator.getNextLeftLaneObject();
			Obstacle nextObstacle = null;

			rightLaneIsFree = false;

			switch (nextObject) {

			case OBSTACLE_STONE:
				nextObstacle = stones.obtain();
				nextObstacle.position.x = Stone.RIGHT_LANE_POSITION_X;
				nextObstacle.position.y = WorldRenderer.FRUSTUM_HEIGHT;
				nextObstacle.bounds.x = nextObstacle.position.x;
				nextObstacle.bounds.y = nextObstacle.position.y;
				nextObstacle.laneState = LaneState.RIGHT;

				break;

			case NONE:
				rightLaneIsFree = true;
				break;
			}

			if (nextObstacle != null) {
				obstacles.add(nextObstacle);
			}
		}
	}

	public void checkCollisions() {
		if (!spotCollision) checkObstacleCollisions();
		//TODO checkGarbageCollisions, checkSpecialItemCollisions
	}

	public void checkObstacleCollisions() {

		boolean collision = false;

		for (Obstacle obstacle : obstacles) {
			if (obstacle.position.y <= spot.position.y + spot.bounds.height) {
				if (spot.bounds.overlaps(obstacle.bounds)) {

					switch(spot.spotState) {
					case GOING_FORWARD:
						collision = true;
						break;
						
					case GOING_LEFT:
						if (((obstacle.position.y >= spot.position.y + spot.bounds.height/2) && (obstacle.position.x < spot.position.x + spot.bounds.width/2)) ||
							((obstacle.position.y < spot.position.y + spot.bounds.height/2) && (obstacle.position.x >= spot.position.x + spot.bounds.width/2))) {
							collision = true;
						}

						break;
					case GOING_RIGHT:
						if (((obstacle.position.y >= spot.position.y + spot.bounds.height/2) && (obstacle.position.x >= spot.position.x + spot.bounds.width/2)) ||
							((obstacle.position.y < spot.position.y + spot.bounds.height/2) && (obstacle.position.x < spot.position.x + spot.bounds.width/2))) {
							collision = true;
						}
						
						break;
						
					case JUMPING:
						break;
						
					case CROUCHING:
						break;

					}
					
					if(collision) {
						break;
					}
				}
			}
		}

		if (collision) {
			spotCollision = true;
			spotCollisionTimeSpent = 0;
			spotCollisionStateTimeSpent = 0;
		}
	}
}
