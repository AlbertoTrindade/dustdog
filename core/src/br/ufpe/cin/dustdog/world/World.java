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
import br.ufpe.cin.dustdog.objects.obstacles.Tree;
import br.ufpe.cin.dustdog.objects.spot.Spot;
import br.ufpe.cin.dustdog.objects.spot.SwipeDirection;
import br.ufpe.cin.dustdog.parallax.ParallaxBackground;
import br.ufpe.cin.dustdog.parallax.ParallaxLayer;

public class World {

	public interface WorldListener {
		// TODO: add method signature for each action in World that generates sound (hit an obstacle, for example)
	}

	public static final float WORLD_WIDTH = 10f;
	public static final float WORLD_VELOCITY = 8f;
	public static final float SPOT_COLLISION_DURATION = 3f;

	public final WorldListener worldListener;

	public final ParallaxBackground background;
	public final Spot spot;

	public int score;
	public WorldState state;

	public final LevelGenerator levelGenerator;

	public final List<Obstacle> obstacles;

	public final Pool<Stone> stones;
	public final Pool<Tree> trees;

	public boolean leftLaneIsFree;
	public boolean centralLaneIsFree;
	public boolean rightLaneIsFree;

	public boolean spotCollision;
	public float spotCollisionTimeSpent;
	public float spotCollisionStateTimeSpent;

	public World(WorldListener worldListener) {
		this.worldListener = worldListener;

		this.background = new ParallaxBackground(new ParallaxLayer(Assets.backgroundRegionGameScreen, 0, 1, ((float) Assets.backgroundGameScreen.getHeight()/Assets.SCREEN_HEIGHT)));
		this.spot = new Spot(Spot.CENTRAL_LANE_POSITION_X, Spot.SPOT_POSITION_Y, Spot.SPOT_COLLISION_WIDTH, Spot.SPOT_COLLISION_HEIGHT);

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
		
		trees = new Pool<Tree>() {
			@Override
			protected Tree newObject() {
				return new Tree(0, 0, Tree.TREE_WIDTH, Tree.TREE_HEIGHT);
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

		spawnObjects();
		checkCollisions();
	}

	private void updateBackground(float deltaTime) {
		background.moveY(WORLD_VELOCITY * deltaTime);
	}

	private void updateSpot(float deltaTime, SwipeDirection swipeDirection) {
		spot.update(deltaTime, swipeDirection);

		if (spotCollision) {
			spotCollisionTimeSpent += deltaTime;
			spotCollisionStateTimeSpent += deltaTime;

			if (spotCollisionStateTimeSpent >= SPOT_COLLISION_DURATION/20) {
				spot.visible = !spot.visible;
				spotCollisionStateTimeSpent = 0;
			}

			if (spotCollisionTimeSpent >= SPOT_COLLISION_DURATION) {
				spot.visible = true;
				spotCollision = false;
			}
		}
	}

	private void updateObstacles(float deltaTime) {
		centralLaneIsFree = true;
		leftLaneIsFree = true;
		rightLaneIsFree = true;

		Obstacle obstacle;

		for (int i = 0; i < obstacles.size(); i++) {
			obstacle = obstacles.get(i);			
			obstacle.update(deltaTime);
			
			float obstacleHeight = 0f;
			
			if (obstacle instanceof Stone) {
				obstacleHeight = Stone.STONE_HEIGHT;
			}
			
			if (obstacle instanceof Tree) {
				obstacleHeight = Tree.TREE_HEIGHT;
			}

			// Check if some obstacle is not being completely showing up			
			if (obstacle.position.y >= WorldRenderer.FRUSTUM_HEIGHT - obstacleHeight) {
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
			if (obstacle.position.y < - obstacleHeight) {
				obstacles.remove(obstacle);
				i--;
				score+= 10; // TODO: remove this later

				if (obstacle instanceof Stone) {
					stones.free((Stone) obstacle);
				}	
				
				if (obstacle instanceof Tree) {
					trees.free((Tree) obstacle);
				}
			}
		}
	}

	private void spawnObjects() {

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
				
			case OBSTACLE_TREE:
				nextObstacle = trees.obtain();
				nextObstacle.position.x = Tree.LEFT_LANE_POSITION_X;
				nextObstacle.position.y = WorldRenderer.FRUSTUM_HEIGHT;
				nextObstacle.bounds.x = nextObstacle.position.x + Tree.TREE_COLLISION_POSITION_X;
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
			LevelGeneratorObject nextObject = levelGenerator.getNextCentralLaneObject();
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
				
			case OBSTACLE_TREE:
				nextObstacle = trees.obtain();
				nextObstacle.position.x = Tree.CENTRAL_LANE_POSITION_X;
				nextObstacle.position.y = WorldRenderer.FRUSTUM_HEIGHT;
				nextObstacle.bounds.x = nextObstacle.position.x + Tree.TREE_COLLISION_POSITION_X;
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
			LevelGeneratorObject nextObject = levelGenerator.getNextRightLaneObject();
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
				
			case OBSTACLE_TREE:
				nextObstacle = trees.obtain();
				nextObstacle.position.x = Tree.RIGHT_LANE_POSITION_X;
				nextObstacle.position.y = WorldRenderer.FRUSTUM_HEIGHT;
				nextObstacle.bounds.x = nextObstacle.position.x + Tree.TREE_COLLISION_POSITION_X;
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

	private void checkCollisions() {
		if (!spotCollision) checkObstacleCollisions();
		//TODO checkGarbageCollisions, checkSpecialItemCollisions
	}

	private void checkObstacleCollisions() {

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
			
			spot.numberBones--;
			
			if (spot.numberBones == 0) {
				state = WorldState.GAME_OVER;
			}
		}
	}
}
