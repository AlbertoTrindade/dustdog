package br.ufpe.cin.dustdog.world;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Pool;

import br.ufpe.cin.dustdog.Assets;
import br.ufpe.cin.dustdog.Settings;
import br.ufpe.cin.dustdog.objects.LaneState;
import br.ufpe.cin.dustdog.objects.LevelGenerator;
import br.ufpe.cin.dustdog.objects.LevelGeneratorObject;
import br.ufpe.cin.dustdog.objects.garbages.BottleBrown;
import br.ufpe.cin.dustdog.objects.garbages.BottleGreen;
import br.ufpe.cin.dustdog.objects.garbages.BottlePurple;
import br.ufpe.cin.dustdog.objects.garbages.CanGreen;
import br.ufpe.cin.dustdog.objects.garbages.CanPurple;
import br.ufpe.cin.dustdog.objects.garbages.CanRed;
import br.ufpe.cin.dustdog.objects.garbages.CoconutNoStraw;
import br.ufpe.cin.dustdog.objects.garbages.CoconutStraw;
import br.ufpe.cin.dustdog.objects.garbages.Fishbone;
import br.ufpe.cin.dustdog.objects.garbages.Garbage;
import br.ufpe.cin.dustdog.objects.garbages.PaperBallA;
import br.ufpe.cin.dustdog.objects.garbages.PaperBallB;
import br.ufpe.cin.dustdog.objects.garbages.PaperBallC;
import br.ufpe.cin.dustdog.objects.obstacles.Obstacle;
import br.ufpe.cin.dustdog.objects.obstacles.StoneA;
import br.ufpe.cin.dustdog.objects.obstacles.StoneB;
import br.ufpe.cin.dustdog.objects.obstacles.StoneC;
import br.ufpe.cin.dustdog.objects.obstacles.StoneD;
import br.ufpe.cin.dustdog.objects.obstacles.Tree;
import br.ufpe.cin.dustdog.objects.specialItems.CarBattery;
import br.ufpe.cin.dustdog.objects.specialItems.CookieBox;
import br.ufpe.cin.dustdog.objects.specialItems.SpecialItem;
import br.ufpe.cin.dustdog.objects.specialItems.Tornado;
import br.ufpe.cin.dustdog.objects.spot.Spot;
import br.ufpe.cin.dustdog.objects.spot.SwipeDirection;
import br.ufpe.cin.dustdog.parallax.ParallaxBackground;
import br.ufpe.cin.dustdog.parallax.ParallaxLayer;

public class World {

	public static final float WORLD_WIDTH = 10f;
	public static final float WORLD_VELOCITY = 8f;
	public static final float WORLD_VELOCITY_INCREMENT = 0.2f;
	public static final float SPOT_COLLISION_DURATION = 3f;
	public static final float SPOT_BARK_MIN_INTERVAL = 45f;
	public static final float TORNADO_DURATION = 20f;

	public static Vector2 velocity;

	public final ParallaxBackground background;
	public final Spot spot;

	public int score;
	public boolean newHighscore;
	public WorldState state;

	public final LevelGenerator levelGenerator;

	public final List<Obstacle> obstacles;

	public final Pool<StoneA> stonesA;
	public final Pool<StoneB> stonesB;
	public final Pool<StoneC> stonesC;
	public final Pool<StoneD> stonesD;
	public final Pool<Tree> trees;

	public final List<Garbage> garbages;

	public final Pool<PaperBallA> paperBallsA;
	public final Pool<PaperBallB> paperBallsB;
	public final Pool<PaperBallC> paperBallsC;
	public final Pool<CoconutStraw> coconutsStraw;
	public final Pool<CoconutNoStraw> coconutsNoStraw;
	public final Pool<BottleBrown> bottlesBrown;
	public final Pool<BottleGreen> bottlesGreen;
	public final Pool<BottlePurple> bottlesPurple;
	public final Pool<CanGreen> cansGreen;
	public final Pool<CanRed> cansRed;
	public final Pool<CanPurple> cansPurple;
	public final Pool<Fishbone> fishbones;

	public final List<SpecialItem> specialItems;

	public final Pool<CookieBox> cookieBoxes;
	public final Pool<CarBattery> carBatteries;
	public final Pool<Tornado> tornadoes;

	public boolean leftLaneIsFree;
	public boolean centralLaneIsFree;
	public boolean rightLaneIsFree;

	public boolean spotCollision;
	public float spotCollisionTimeSpent;
	public float spotCollisionStateTimeSpent;

	public boolean tornadoRunning;
	public float tornadoRunningTimeSpent;

	public Random random;
	public float timeSpentSinceLastBark;

	public World() {
		velocity = new Vector2();
		velocity.y = WORLD_VELOCITY;

		this.background = new ParallaxBackground(new ParallaxLayer(Assets.backgroundRegionGameScreen, 0, 1, ((float) Assets.backgroundGameScreen.getHeight()/Assets.SCREEN_HEIGHT)));
		this.spot = new Spot(Spot.CENTRAL_LANE_POSITION_X, Spot.SPOT_POSITION_Y, Spot.SPOT_COLLISION_WIDTH, Spot.SPOT_COLLISION_HEIGHT);

		this.score = 0;
		this.newHighscore = false;
		this.state = WorldState.RUNNING;

		levelGenerator = new LevelGenerator(this);

		obstacles = new ArrayList<Obstacle>();

		stonesA = new Pool<StoneA>() {
			@Override
			protected StoneA newObject() {
				return new StoneA(0, 0, StoneA.STONE_WIDTH, StoneA.STONE_HEIGHT);
			}
		};

		stonesB = new Pool<StoneB>() {
			@Override
			protected StoneB newObject() {
				return new StoneB(0, 0, StoneB.STONE_WIDTH, StoneB.STONE_HEIGHT);
			}
		};

		stonesC = new Pool<StoneC>() {
			@Override
			protected StoneC newObject() {
				return new StoneC(0, 0, StoneC.STONE_WIDTH, StoneC.STONE_HEIGHT);
			}
		};

		stonesD = new Pool<StoneD>() {
			@Override
			protected StoneD newObject() {
				return new StoneD(0, 0, StoneD.STONE_WIDTH, StoneD.STONE_HEIGHT);
			}
		};

		trees = new Pool<Tree>() {
			@Override
			protected Tree newObject() {
				return new Tree(0, 0, Tree.TREE_WIDTH, Tree.TREE_HEIGHT);
			}
		};

		garbages = new ArrayList<Garbage>();

		paperBallsA = new Pool<PaperBallA>() {
			@Override
			protected PaperBallA newObject() {
				return new PaperBallA(0, 0, PaperBallA.PAPER_BALL_A_WIDTH, PaperBallA.PAPER_BALL_A_HEIGHT);
			}
		};

		paperBallsB = new Pool<PaperBallB>() {
			@Override
			protected PaperBallB newObject() {
				return new PaperBallB(0, 0, PaperBallB.PAPER_BALL_B_WIDTH, PaperBallB.PAPER_BALL_B_HEIGHT);
			}
		};

		paperBallsC = new Pool<PaperBallC>() {
			@Override
			protected PaperBallC newObject() {
				return new PaperBallC(0, 0, PaperBallC.PAPER_BALL_C_WIDTH, PaperBallC.PAPER_BALL_C_HEIGHT);
			}
		};

		coconutsStraw = new Pool<CoconutStraw>() {
			@Override
			protected CoconutStraw newObject() {
				return new CoconutStraw(0, 0, CoconutStraw.COCONUT_STRAW_WIDTH, CoconutStraw.COCONUT_STRAW_HEIGHT);
			}
		};

		coconutsNoStraw = new Pool<CoconutNoStraw>() {
			@Override
			protected CoconutNoStraw newObject() {
				return new CoconutNoStraw(0, 0, CoconutNoStraw.COCONUT_NO_STRAW_WIDTH, CoconutNoStraw.COCONUT_NO_STRAW_HEIGHT);
			}
		};

		bottlesBrown = new Pool<BottleBrown>() {
			@Override
			protected BottleBrown newObject() {
				return new BottleBrown(0, 0, BottleBrown.BOTTLE_BROWN_WIDTH, BottleBrown.BOTTLE_BROWN_HEIGHT);
			}
		};

		bottlesGreen = new Pool<BottleGreen>() {
			@Override
			protected BottleGreen newObject() {
				return new BottleGreen(0, 0, BottleGreen.BOTTLE_GREEN_WIDTH, BottleGreen.BOTTLE_GREEN_HEIGHT);
			}
		};

		bottlesPurple = new Pool<BottlePurple>() {
			@Override
			protected BottlePurple newObject() {
				return new BottlePurple(0, 0, BottlePurple.BOTTLE_PURPLE_WIDTH, BottlePurple.BOTTLE_PURPLE_HEIGHT);
			}
		};

		cansGreen = new Pool<CanGreen>() {
			@Override
			protected CanGreen newObject() {
				return new CanGreen(0, 0, CanGreen.CAN_GREEN_WIDTH, CanGreen.CAN_GREEN_HEIGHT);
			}
		};

		cansRed = new Pool<CanRed>() {
			@Override
			protected CanRed newObject() {
				return new CanRed(0, 0, CanRed.CAN_RED_WIDTH, CanRed.CAN_RED_HEIGHT);
			}
		};

		cansPurple = new Pool<CanPurple>() {
			@Override
			protected CanPurple newObject() {
				return new CanPurple(0, 0, CanPurple.CAN_PURPLE_WIDTH, CanPurple.CAN_PURPLE_HEIGHT);
			}
		};

		fishbones = new Pool<Fishbone>() {
			@Override
			protected Fishbone newObject() {
				return new Fishbone(0, 0, Fishbone.FISHBONE_WIDTH, Fishbone.FISHBONE_HEIGHT);
			}
		};

		specialItems = new ArrayList<SpecialItem>();

		cookieBoxes = new Pool<CookieBox>() {
			@Override
			protected CookieBox newObject() {
				return new CookieBox(0, 0, CookieBox.COOKIE_BOX_WIDTH, CookieBox.COOKIE_BOX_HEIGHT);
			}
		};

		carBatteries = new Pool<CarBattery>() {
			@Override
			protected CarBattery newObject() {
				return new CarBattery(0, 0, CarBattery.CAR_BATTERY_WIDTH, CarBattery.CAR_BATTERY_HEIGHT);
			}
		};

		tornadoes = new Pool<Tornado>() {
			@Override
			protected Tornado newObject() {
				return new Tornado(0, 0, Tornado.TORNADO_WIDTH, Tornado.TORNADO_HEIGHT);
			}
		};

		leftLaneIsFree = true;
		centralLaneIsFree = true;
		rightLaneIsFree = true;

		random = new Random();
		timeSpentSinceLastBark = 0;
	}

	public void update(float deltaTime, SwipeDirection swipeDirection) {

		if (velocity.y < WORLD_VELOCITY) {
			velocity.y += WORLD_VELOCITY_INCREMENT;
		}

		setObjectsVelocity(velocity.y);

		updateBackground(deltaTime);
		updateSpot(deltaTime, swipeDirection);
		updateObstacles(deltaTime);
		updateGarbages(deltaTime);
		updateSpecialItems(deltaTime);

		spawnObjects();
		checkCollisions();

		bark(deltaTime);
	}

	private void updateBackground(float deltaTime) {
		background.moveY(velocity.y * deltaTime);
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

		if (tornadoRunning) {
			tornadoRunningTimeSpent += deltaTime;

			if (tornadoRunningTimeSpent >= TORNADO_DURATION) {
				tornadoRunning = false;

				specialItems.remove(spot.tornado);
				tornadoes.free((Tornado) spot.tornado);

				spot.tornado = null;

				if (Settings.soundEnabled) Assets.tornadoMusic.stop();
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

			if (obstacle instanceof StoneA) {
				obstacleHeight = StoneA.STONE_HEIGHT;
			}

			if (obstacle instanceof StoneB) {
				obstacleHeight = StoneB.STONE_HEIGHT;
			}

			if (obstacle instanceof StoneC) {
				obstacleHeight = StoneC.STONE_HEIGHT;
			}

			if (obstacle instanceof StoneD) {
				obstacleHeight = StoneD.STONE_HEIGHT;
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

				if (obstacle instanceof StoneA) {
					stonesA.free((StoneA) obstacle);
				}

				if (obstacle instanceof StoneB) {
					stonesB.free((StoneB) obstacle);
				}

				if (obstacle instanceof StoneC) {
					stonesC.free((StoneC) obstacle);
				}

				if (obstacle instanceof StoneD) {
					stonesD.free((StoneD) obstacle);
				}

				if (obstacle instanceof Tree) {
					trees.free((Tree) obstacle);
				}
			}
		}
	}

	private void updateGarbages(float deltaTime) {
		Garbage garbage;

		for (int i = 0; i < garbages.size(); i++) {
			garbage = garbages.get(i);			

			// Checking if garbage is in tornado area
			if (tornadoRunning) {

				// garbage is front of spot
				if ((garbage.laneState == spot.laneState) && 
					((garbage.position.y >= spot.tornado.position.y + Tornado.TORNADO_HEIGHT) && 
					 (garbage.position.y <= spot.tornado.position.y + Tornado.TORNADO_HEIGHT + 0.8))) {
					garbage.velocity.y = -2*WORLD_VELOCITY;
				}
				
				// garbage is next to spot
				switch (spot.laneState) {
				case LEFT:
					if ((garbage.laneState == LaneState.CENTRAL) && 
						((garbage.position.y >= spot.tornado.position.y) &&
						 (garbage.position.y <= spot.tornado.position.y + Tornado.TORNADO_HEIGHT))){
						garbage.velocity.x = -2*WORLD_VELOCITY;
					}

					break;

				case CENTRAL:
					if ((garbage.position.y >= spot.tornado.position.y) && (garbage.position.y <= spot.tornado.position.y + Tornado.TORNADO_HEIGHT)){
						if (garbage.laneState == LaneState.LEFT) {
							garbage.velocity.x = 2*WORLD_VELOCITY;
						}
						
						if (garbage.laneState == LaneState.RIGHT) {
							garbage.velocity.x = -2*WORLD_VELOCITY;
						}
					}

					break;

				case RIGHT:
					if ((garbage.laneState == LaneState.CENTRAL) && 
						((garbage.position.y >= spot.tornado.position.y) &&
						 (garbage.position.y <= spot.tornado.position.y + Tornado.TORNADO_HEIGHT))){
						garbage.velocity.x = 2*WORLD_VELOCITY;
					}

					break;
				}
			}

			garbage.update(deltaTime);

			float garbageHeight = 0f;

			if (garbage instanceof PaperBallA) {
				garbageHeight = PaperBallA.PAPER_BALL_A_HEIGHT;
			}

			if (garbage instanceof PaperBallB) {
				garbageHeight = PaperBallB.PAPER_BALL_B_HEIGHT;
			}

			if (garbage instanceof PaperBallC) {
				garbageHeight = PaperBallC.PAPER_BALL_C_HEIGHT;
			}

			if (garbage instanceof CoconutStraw) {
				garbageHeight = CoconutStraw.COCONUT_STRAW_HEIGHT;
			}

			if (garbage instanceof CoconutNoStraw) {
				garbageHeight = CoconutNoStraw.COCONUT_NO_STRAW_HEIGHT;
			}

			if (garbage instanceof BottleBrown) {
				garbageHeight = BottleBrown.BOTTLE_BROWN_HEIGHT;
			}

			if (garbage instanceof BottleGreen) {
				garbageHeight = BottleGreen.BOTTLE_GREEN_HEIGHT;
			}

			if (garbage instanceof BottlePurple) {
				garbageHeight = BottlePurple.BOTTLE_PURPLE_HEIGHT;
			}

			if (garbage instanceof CanGreen) {
				garbageHeight = CanGreen.CAN_GREEN_HEIGHT;
			}

			if (garbage instanceof CanRed) {
				garbageHeight = CanRed.CAN_RED_HEIGHT;
			}

			if (garbage instanceof CanPurple) {
				garbageHeight = CanPurple.CAN_PURPLE_HEIGHT;
			}

			if (garbage instanceof Fishbone) {
				garbageHeight = Fishbone.FISHBONE_HEIGHT;
			}

			// Check if some obstacle is not being completely showing up			
			if (garbage.position.y >= WorldRenderer.FRUSTUM_HEIGHT - garbageHeight) {
				switch (garbage.laneState) {
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
			if (garbage.position.y < - garbageHeight) {
				garbages.remove(garbage);
				i--;

				if (garbage instanceof PaperBallA) {
					paperBallsA.free((PaperBallA) garbage);
				}	

				if (garbage instanceof PaperBallB) {
					paperBallsB.free((PaperBallB) garbage);
				}

				if (garbage instanceof PaperBallC) {
					paperBallsC.free((PaperBallC) garbage);
				}

				if (garbage instanceof CoconutStraw) {
					coconutsStraw.free((CoconutStraw) garbage);
				}

				if (garbage instanceof CoconutNoStraw) {
					coconutsNoStraw.free((CoconutNoStraw) garbage);
				}

				if (garbage instanceof BottleBrown) {
					bottlesBrown.free((BottleBrown) garbage);
				}

				if (garbage instanceof BottleGreen) {
					bottlesGreen.free((BottleGreen) garbage);
				}

				if (garbage instanceof BottlePurple) {
					bottlesPurple.free((BottlePurple) garbage);
				}

				if (garbage instanceof CanGreen) {
					cansGreen.free((CanGreen) garbage);
				}

				if (garbage instanceof CanRed) {
					cansRed.free((CanRed) garbage);
				}

				if (garbage instanceof CanPurple) {
					cansPurple.free((CanPurple) garbage);
				}

				if (garbage instanceof Fishbone) {
					fishbones.free((Fishbone) garbage);
				}
			}
		}
	}

	private void updateSpecialItems(float deltaTime) {
		SpecialItem specialItem;

		for (int i = 0; i < specialItems.size(); i++) {
			specialItem = specialItems.get(i);			

			float specialItemHeight = 0f;

			if (specialItem instanceof CookieBox) {
				specialItemHeight = CookieBox.COOKIE_BOX_HEIGHT;
			}

			if (specialItem instanceof CarBattery) {
				specialItemHeight = CarBattery.CAR_BATTERY_HEIGHT;
			}

			if (specialItem instanceof Tornado) {
				specialItem.position.x = spot.position.x + (Spot.SPOT_WIDTH - Tornado.TORNADO_WIDTH)/2;
				specialItem.position.y = spot.position.y + 2*Spot.SPOT_HEIGHT/3;
			}
			
			specialItem.update(deltaTime);

			// Check if some obstacle is not being completely showing up			
			if (specialItem.position.y >= WorldRenderer.FRUSTUM_HEIGHT - specialItemHeight) {
				switch (specialItem.laneState) {
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
			if (specialItem.position.y < - specialItemHeight) {
				specialItems.remove(specialItem);
				i--;

				if (specialItem instanceof CookieBox) {
					cookieBoxes.free((CookieBox) specialItem);
				}

				if (specialItem instanceof CarBattery) {
					carBatteries.free((CarBattery) specialItem);
				}
			}
		}
	}

	private void spawnObjects() {

		if (leftLaneIsFree) {
			LevelGeneratorObject nextObject = levelGenerator.getNextLeftLaneObject();
			Obstacle nextObstacle = null;
			Garbage nextGarbage = null;
			SpecialItem nextSpecialItem = null;

			leftLaneIsFree = false;

			switch (nextObject) {

			case OBSTACLE_STONE_A:
				nextObstacle = stonesA.obtain();
				setObstacle(nextObstacle, StoneA.LEFT_LANE_POSITION_X, WorldRenderer.FRUSTUM_HEIGHT, StoneA.LEFT_LANE_POSITION_X, WorldRenderer.FRUSTUM_HEIGHT, LaneState.LEFT);

				break;

			case OBSTACLE_STONE_B:
				nextObstacle = stonesB.obtain();
				setObstacle(nextObstacle, StoneB.LEFT_LANE_POSITION_X, WorldRenderer.FRUSTUM_HEIGHT, StoneB.LEFT_LANE_POSITION_X, WorldRenderer.FRUSTUM_HEIGHT, LaneState.LEFT);

				break;

			case OBSTACLE_STONE_C:
				nextObstacle = stonesC.obtain();
				setObstacle(nextObstacle, StoneC.LEFT_LANE_POSITION_X, WorldRenderer.FRUSTUM_HEIGHT, StoneC.LEFT_LANE_POSITION_X, WorldRenderer.FRUSTUM_HEIGHT, LaneState.LEFT);

				break;

			case OBSTACLE_STONE_D:
				nextObstacle = stonesD.obtain();
				setObstacle(nextObstacle, StoneD.LEFT_LANE_POSITION_X, WorldRenderer.FRUSTUM_HEIGHT, StoneD.LEFT_LANE_POSITION_X, WorldRenderer.FRUSTUM_HEIGHT, LaneState.LEFT);

				break;

			case OBSTACLE_TREE:
				nextObstacle = trees.obtain();
				setObstacle(nextObstacle, Tree.LEFT_LANE_POSITION_X, WorldRenderer.FRUSTUM_HEIGHT, Tree.LEFT_LANE_POSITION_X + Tree.TREE_COLLISION_POSITION_X, WorldRenderer.FRUSTUM_HEIGHT, LaneState.LEFT);

				break;

			case GARBAGE_PAPER_BALL_A:
				nextGarbage = paperBallsA.obtain();
				setGarbage(nextGarbage, PaperBallA.LEFT_LANE_POSITION_X, WorldRenderer.FRUSTUM_HEIGHT, PaperBallA.LEFT_LANE_POSITION_X, WorldRenderer.FRUSTUM_HEIGHT, LaneState.LEFT);

				break;

			case GARBAGE_PAPER_BALL_B:
				nextGarbage = paperBallsB.obtain();
				setGarbage(nextGarbage, PaperBallB.LEFT_LANE_POSITION_X, WorldRenderer.FRUSTUM_HEIGHT, PaperBallB.LEFT_LANE_POSITION_X, WorldRenderer.FRUSTUM_HEIGHT, LaneState.LEFT);

				break;

			case GARBAGE_PAPER_BALL_C:
				nextGarbage = paperBallsC.obtain();
				setGarbage(nextGarbage, PaperBallC.LEFT_LANE_POSITION_X, WorldRenderer.FRUSTUM_HEIGHT, PaperBallC.LEFT_LANE_POSITION_X, WorldRenderer.FRUSTUM_HEIGHT, LaneState.LEFT);

				break;

			case GARBAGE_COCONUT_STRAW:
				nextGarbage = coconutsStraw.obtain();
				setGarbage(nextGarbage, CoconutStraw.LEFT_LANE_POSITION_X, WorldRenderer.FRUSTUM_HEIGHT, CoconutStraw.LEFT_LANE_POSITION_X, WorldRenderer.FRUSTUM_HEIGHT, LaneState.LEFT);

				break;

			case GARBAGE_COCONUT_NO_STRAW:
				nextGarbage = coconutsNoStraw.obtain();
				setGarbage(nextGarbage, CoconutNoStraw.LEFT_LANE_POSITION_X, WorldRenderer.FRUSTUM_HEIGHT, CoconutNoStraw.LEFT_LANE_POSITION_X, WorldRenderer.FRUSTUM_HEIGHT, LaneState.LEFT);

				break;

			case GARBAGE_BOTTLE_BROWN:
				nextGarbage = bottlesBrown.obtain();
				setGarbage(nextGarbage, BottleBrown.LEFT_LANE_POSITION_X, WorldRenderer.FRUSTUM_HEIGHT, BottleBrown.LEFT_LANE_POSITION_X, WorldRenderer.FRUSTUM_HEIGHT, LaneState.LEFT);

				break;

			case GARBAGE_BOTTLE_GREEN:
				nextGarbage = bottlesGreen.obtain();
				setGarbage(nextGarbage, BottleGreen.LEFT_LANE_POSITION_X, WorldRenderer.FRUSTUM_HEIGHT, BottleGreen.LEFT_LANE_POSITION_X, WorldRenderer.FRUSTUM_HEIGHT, LaneState.LEFT);

				break;

			case GARBAGE_BOTTLE_PURPLE:
				nextGarbage = bottlesPurple.obtain();
				setGarbage(nextGarbage, BottlePurple.LEFT_LANE_POSITION_X, WorldRenderer.FRUSTUM_HEIGHT, BottlePurple.LEFT_LANE_POSITION_X, WorldRenderer.FRUSTUM_HEIGHT, LaneState.LEFT);

				break;

			case GARBAGE_CAN_GREEN:
				nextGarbage = cansGreen.obtain();
				setGarbage(nextGarbage, CanGreen.LEFT_LANE_POSITION_X, WorldRenderer.FRUSTUM_HEIGHT, CanGreen.LEFT_LANE_POSITION_X, WorldRenderer.FRUSTUM_HEIGHT, LaneState.LEFT);

				break;

			case GARBAGE_CAN_RED:
				nextGarbage = cansRed.obtain();
				setGarbage(nextGarbage, CanRed.LEFT_LANE_POSITION_X, WorldRenderer.FRUSTUM_HEIGHT, CanRed.LEFT_LANE_POSITION_X, WorldRenderer.FRUSTUM_HEIGHT, LaneState.LEFT);

				break;

			case GARBAGE_CAN_PURPLE:
				nextGarbage = cansPurple.obtain();
				setGarbage(nextGarbage, CanPurple.LEFT_LANE_POSITION_X, WorldRenderer.FRUSTUM_HEIGHT, CanPurple.LEFT_LANE_POSITION_X, WorldRenderer.FRUSTUM_HEIGHT, LaneState.LEFT);

				break;

			case GARBAGE_FISHBONE:
				nextGarbage = fishbones.obtain();
				setGarbage(nextGarbage, Fishbone.LEFT_LANE_POSITION_X, WorldRenderer.FRUSTUM_HEIGHT, Fishbone.LEFT_LANE_POSITION_X, WorldRenderer.FRUSTUM_HEIGHT, LaneState.LEFT);

				break;

			case SPECIAL_ITEMS_COOKIE_BOX:
				nextSpecialItem = cookieBoxes.obtain();
				setSpecialItem(nextSpecialItem, CookieBox.LEFT_LANE_POSITION_X, WorldRenderer.FRUSTUM_HEIGHT, CookieBox.LEFT_LANE_POSITION_X, WorldRenderer.FRUSTUM_HEIGHT, LaneState.LEFT);

				break;

			case SPECIAL_ITEMS_CAR_BATTERY:
				nextSpecialItem = carBatteries.obtain();
				setSpecialItem(nextSpecialItem, CarBattery.LEFT_LANE_POSITION_X, WorldRenderer.FRUSTUM_HEIGHT, CarBattery.LEFT_LANE_POSITION_X, WorldRenderer.FRUSTUM_HEIGHT, LaneState.LEFT);

				break;

			case NONE:
				leftLaneIsFree = true;
				break;
			}

			if (nextObstacle != null) {
				obstacles.add(nextObstacle);
			}

			if (nextGarbage != null) {
				garbages.add(nextGarbage);
			}

			if (nextSpecialItem != null) {
				specialItems.add(nextSpecialItem);
			}
		}

		if (centralLaneIsFree) {
			LevelGeneratorObject nextObject = levelGenerator.getNextCentralLaneObject();
			Obstacle nextObstacle = null;
			Garbage nextGarbage = null;
			SpecialItem nextSpecialItem = null;

			centralLaneIsFree = false;

			switch (nextObject) {

			case OBSTACLE_STONE_A:
				nextObstacle = stonesA.obtain();
				setObstacle(nextObstacle, StoneA.CENTRAL_LANE_POSITION_X, WorldRenderer.FRUSTUM_HEIGHT, StoneA.CENTRAL_LANE_POSITION_X, WorldRenderer.FRUSTUM_HEIGHT, LaneState.CENTRAL);

				break;

			case OBSTACLE_STONE_B:
				nextObstacle = stonesB.obtain();
				setObstacle(nextObstacle, StoneB.CENTRAL_LANE_POSITION_X, WorldRenderer.FRUSTUM_HEIGHT, StoneB.CENTRAL_LANE_POSITION_X, WorldRenderer.FRUSTUM_HEIGHT, LaneState.CENTRAL);

				break;

			case OBSTACLE_STONE_C:
				nextObstacle = stonesC.obtain();
				setObstacle(nextObstacle, StoneC.CENTRAL_LANE_POSITION_X, WorldRenderer.FRUSTUM_HEIGHT, StoneC.CENTRAL_LANE_POSITION_X, WorldRenderer.FRUSTUM_HEIGHT, LaneState.CENTRAL);

				break;

			case OBSTACLE_STONE_D:
				nextObstacle = stonesD.obtain();
				setObstacle(nextObstacle, StoneD.CENTRAL_LANE_POSITION_X, WorldRenderer.FRUSTUM_HEIGHT, StoneD.CENTRAL_LANE_POSITION_X, WorldRenderer.FRUSTUM_HEIGHT, LaneState.CENTRAL);

				break;

			case OBSTACLE_TREE:
				nextObstacle = trees.obtain();
				setObstacle(nextObstacle, Tree.CENTRAL_LANE_POSITION_X, WorldRenderer.FRUSTUM_HEIGHT, Tree.CENTRAL_LANE_POSITION_X + Tree.TREE_COLLISION_POSITION_X, WorldRenderer.FRUSTUM_HEIGHT, LaneState.CENTRAL);

				break;

			case GARBAGE_PAPER_BALL_A:
				nextGarbage = paperBallsA.obtain();
				setGarbage(nextGarbage, PaperBallA.CENTRAL_LANE_POSITION_X, WorldRenderer.FRUSTUM_HEIGHT, PaperBallA.CENTRAL_LANE_POSITION_X, WorldRenderer.FRUSTUM_HEIGHT, LaneState.CENTRAL);

				break;

			case GARBAGE_PAPER_BALL_B:
				nextGarbage = paperBallsB.obtain();
				setGarbage(nextGarbage, PaperBallB.CENTRAL_LANE_POSITION_X, WorldRenderer.FRUSTUM_HEIGHT, PaperBallB.CENTRAL_LANE_POSITION_X, WorldRenderer.FRUSTUM_HEIGHT, LaneState.CENTRAL);

				break;

			case GARBAGE_PAPER_BALL_C:
				nextGarbage = paperBallsC.obtain();
				setGarbage(nextGarbage, PaperBallC.CENTRAL_LANE_POSITION_X, WorldRenderer.FRUSTUM_HEIGHT, PaperBallC.CENTRAL_LANE_POSITION_X, WorldRenderer.FRUSTUM_HEIGHT, LaneState.CENTRAL);

				break;

			case GARBAGE_COCONUT_STRAW:
				nextGarbage = coconutsStraw.obtain();
				setGarbage(nextGarbage, CoconutStraw.CENTRAL_LANE_POSITION_X, WorldRenderer.FRUSTUM_HEIGHT, CoconutStraw.CENTRAL_LANE_POSITION_X, WorldRenderer.FRUSTUM_HEIGHT, LaneState.CENTRAL);

				break;

			case GARBAGE_COCONUT_NO_STRAW:
				nextGarbage = coconutsNoStraw.obtain();
				setGarbage(nextGarbage, CoconutNoStraw.CENTRAL_LANE_POSITION_X, WorldRenderer.FRUSTUM_HEIGHT, CoconutNoStraw.CENTRAL_LANE_POSITION_X, WorldRenderer.FRUSTUM_HEIGHT, LaneState.CENTRAL);

				break;

			case GARBAGE_BOTTLE_BROWN:
				nextGarbage = bottlesBrown.obtain();
				setGarbage(nextGarbage, BottleBrown.CENTRAL_LANE_POSITION_X, WorldRenderer.FRUSTUM_HEIGHT, BottleBrown.CENTRAL_LANE_POSITION_X, WorldRenderer.FRUSTUM_HEIGHT, LaneState.CENTRAL);

				break;

			case GARBAGE_BOTTLE_GREEN:
				nextGarbage = bottlesGreen.obtain();
				setGarbage(nextGarbage, BottleGreen.CENTRAL_LANE_POSITION_X, WorldRenderer.FRUSTUM_HEIGHT, BottleGreen.CENTRAL_LANE_POSITION_X, WorldRenderer.FRUSTUM_HEIGHT, LaneState.CENTRAL);

				break;

			case GARBAGE_BOTTLE_PURPLE:
				nextGarbage = bottlesPurple.obtain();
				setGarbage(nextGarbage, BottlePurple.CENTRAL_LANE_POSITION_X, WorldRenderer.FRUSTUM_HEIGHT, BottlePurple.CENTRAL_LANE_POSITION_X, WorldRenderer.FRUSTUM_HEIGHT, LaneState.CENTRAL);

				break;

			case GARBAGE_CAN_GREEN:
				nextGarbage = cansGreen.obtain();
				setGarbage(nextGarbage, CanGreen.CENTRAL_LANE_POSITION_X, WorldRenderer.FRUSTUM_HEIGHT, CanGreen.CENTRAL_LANE_POSITION_X, WorldRenderer.FRUSTUM_HEIGHT, LaneState.CENTRAL);

				break;

			case GARBAGE_CAN_RED:
				nextGarbage = cansRed.obtain();
				setGarbage(nextGarbage, CanRed.CENTRAL_LANE_POSITION_X, WorldRenderer.FRUSTUM_HEIGHT, CanRed.CENTRAL_LANE_POSITION_X, WorldRenderer.FRUSTUM_HEIGHT, LaneState.CENTRAL);

				break;

			case GARBAGE_CAN_PURPLE:
				nextGarbage = cansPurple.obtain();
				setGarbage(nextGarbage, CanPurple.CENTRAL_LANE_POSITION_X, WorldRenderer.FRUSTUM_HEIGHT, CanPurple.CENTRAL_LANE_POSITION_X, WorldRenderer.FRUSTUM_HEIGHT, LaneState.CENTRAL);

				break;

			case GARBAGE_FISHBONE:
				nextGarbage = fishbones.obtain();
				setGarbage(nextGarbage, Fishbone.CENTRAL_LANE_POSITION_X, WorldRenderer.FRUSTUM_HEIGHT, Fishbone.CENTRAL_LANE_POSITION_X, WorldRenderer.FRUSTUM_HEIGHT, LaneState.CENTRAL);

				break;

			case SPECIAL_ITEMS_COOKIE_BOX:
				nextSpecialItem = cookieBoxes.obtain();
				setSpecialItem(nextSpecialItem, CookieBox.CENTRAL_LANE_POSITION_X, WorldRenderer.FRUSTUM_HEIGHT, CookieBox.CENTRAL_LANE_POSITION_X, WorldRenderer.FRUSTUM_HEIGHT, LaneState.CENTRAL);

				break;

			case SPECIAL_ITEMS_CAR_BATTERY:
				nextSpecialItem = carBatteries.obtain();
				setSpecialItem(nextSpecialItem, CarBattery.CENTRAL_LANE_POSITION_X, WorldRenderer.FRUSTUM_HEIGHT, CarBattery.CENTRAL_LANE_POSITION_X, WorldRenderer.FRUSTUM_HEIGHT, LaneState.CENTRAL);

				break;

			case NONE:
				centralLaneIsFree = true;
				break;
			}

			if (nextObstacle != null) {
				obstacles.add(nextObstacle);
			}

			if (nextGarbage != null) {
				garbages.add(nextGarbage);
			}

			if (nextSpecialItem != null) {
				specialItems.add(nextSpecialItem);
			}
		}

		if (rightLaneIsFree) {
			LevelGeneratorObject nextObject = levelGenerator.getNextRightLaneObject();
			Obstacle nextObstacle = null;
			Garbage nextGarbage = null;
			SpecialItem nextSpecialItem = null;

			rightLaneIsFree = false;

			switch (nextObject) {

			case OBSTACLE_STONE_A:
				nextObstacle = stonesA.obtain();
				setObstacle(nextObstacle, StoneA.RIGHT_LANE_POSITION_X, WorldRenderer.FRUSTUM_HEIGHT, StoneA.RIGHT_LANE_POSITION_X, WorldRenderer.FRUSTUM_HEIGHT, LaneState.RIGHT);

				break;

			case OBSTACLE_STONE_B:
				nextObstacle = stonesB.obtain();
				setObstacle(nextObstacle, StoneB.RIGHT_LANE_POSITION_X, WorldRenderer.FRUSTUM_HEIGHT, StoneB.RIGHT_LANE_POSITION_X, WorldRenderer.FRUSTUM_HEIGHT, LaneState.RIGHT);

				break;

			case OBSTACLE_STONE_C:
				nextObstacle = stonesC.obtain();
				setObstacle(nextObstacle, StoneC.RIGHT_LANE_POSITION_X, WorldRenderer.FRUSTUM_HEIGHT, StoneC.RIGHT_LANE_POSITION_X, WorldRenderer.FRUSTUM_HEIGHT, LaneState.RIGHT);

				break;

			case OBSTACLE_STONE_D:
				nextObstacle = stonesA.obtain();
				setObstacle(nextObstacle, StoneD.RIGHT_LANE_POSITION_X, WorldRenderer.FRUSTUM_HEIGHT, StoneD.RIGHT_LANE_POSITION_X, WorldRenderer.FRUSTUM_HEIGHT, LaneState.RIGHT);

				break;

			case OBSTACLE_TREE:
				nextObstacle = trees.obtain();
				setObstacle(nextObstacle, Tree.RIGHT_LANE_POSITION_X, WorldRenderer.FRUSTUM_HEIGHT, Tree.RIGHT_LANE_POSITION_X + Tree.TREE_COLLISION_POSITION_X, WorldRenderer.FRUSTUM_HEIGHT, LaneState.RIGHT);

				break;

			case GARBAGE_PAPER_BALL_A:
				nextGarbage = paperBallsA.obtain();
				setGarbage(nextGarbage, PaperBallA.RIGHT_LANE_POSITION_X, WorldRenderer.FRUSTUM_HEIGHT, PaperBallA.RIGHT_LANE_POSITION_X, WorldRenderer.FRUSTUM_HEIGHT, LaneState.RIGHT);

				break;

			case GARBAGE_PAPER_BALL_B:
				nextGarbage = paperBallsB.obtain();
				setGarbage(nextGarbage, PaperBallB.RIGHT_LANE_POSITION_X, WorldRenderer.FRUSTUM_HEIGHT, PaperBallB.RIGHT_LANE_POSITION_X, WorldRenderer.FRUSTUM_HEIGHT, LaneState.RIGHT);

				break;

			case GARBAGE_PAPER_BALL_C:
				nextGarbage = paperBallsC.obtain();
				setGarbage(nextGarbage, PaperBallC.RIGHT_LANE_POSITION_X, WorldRenderer.FRUSTUM_HEIGHT, PaperBallC.RIGHT_LANE_POSITION_X, WorldRenderer.FRUSTUM_HEIGHT, LaneState.RIGHT);

				break;

			case GARBAGE_COCONUT_STRAW:
				nextGarbage = coconutsStraw.obtain();
				setGarbage(nextGarbage, CoconutStraw.RIGHT_LANE_POSITION_X, WorldRenderer.FRUSTUM_HEIGHT, CoconutStraw.RIGHT_LANE_POSITION_X, WorldRenderer.FRUSTUM_HEIGHT, LaneState.RIGHT);

				break;

			case GARBAGE_COCONUT_NO_STRAW:
				nextGarbage = coconutsNoStraw.obtain();
				setGarbage(nextGarbage, CoconutNoStraw.RIGHT_LANE_POSITION_X, WorldRenderer.FRUSTUM_HEIGHT, CoconutNoStraw.RIGHT_LANE_POSITION_X, WorldRenderer.FRUSTUM_HEIGHT, LaneState.RIGHT);

				break;

			case GARBAGE_BOTTLE_BROWN:
				nextGarbage = bottlesBrown.obtain();
				setGarbage(nextGarbage, BottleBrown.RIGHT_LANE_POSITION_X, WorldRenderer.FRUSTUM_HEIGHT, BottleBrown.RIGHT_LANE_POSITION_X, WorldRenderer.FRUSTUM_HEIGHT, LaneState.RIGHT);

				break;

			case GARBAGE_BOTTLE_GREEN:
				nextGarbage = bottlesGreen.obtain();
				setGarbage(nextGarbage, BottleGreen.RIGHT_LANE_POSITION_X, WorldRenderer.FRUSTUM_HEIGHT, BottleGreen.RIGHT_LANE_POSITION_X, WorldRenderer.FRUSTUM_HEIGHT, LaneState.RIGHT);

				break;

			case GARBAGE_BOTTLE_PURPLE:
				nextGarbage = bottlesPurple.obtain();
				setGarbage(nextGarbage, BottlePurple.RIGHT_LANE_POSITION_X, WorldRenderer.FRUSTUM_HEIGHT, BottlePurple.RIGHT_LANE_POSITION_X, WorldRenderer.FRUSTUM_HEIGHT, LaneState.RIGHT);

				break;

			case GARBAGE_CAN_GREEN:
				nextGarbage = cansGreen.obtain();
				setGarbage(nextGarbage, CanGreen.RIGHT_LANE_POSITION_X, WorldRenderer.FRUSTUM_HEIGHT, CanGreen.RIGHT_LANE_POSITION_X, WorldRenderer.FRUSTUM_HEIGHT, LaneState.RIGHT);

				break;

			case GARBAGE_CAN_RED:
				nextGarbage = cansRed.obtain();
				setGarbage(nextGarbage, CanRed.RIGHT_LANE_POSITION_X, WorldRenderer.FRUSTUM_HEIGHT, CanRed.RIGHT_LANE_POSITION_X, WorldRenderer.FRUSTUM_HEIGHT, LaneState.RIGHT);

				break;

			case GARBAGE_CAN_PURPLE:
				nextGarbage = cansPurple.obtain();
				setGarbage(nextGarbage, CanPurple.RIGHT_LANE_POSITION_X, WorldRenderer.FRUSTUM_HEIGHT, CanPurple.RIGHT_LANE_POSITION_X, WorldRenderer.FRUSTUM_HEIGHT, LaneState.RIGHT);

				break;

			case GARBAGE_FISHBONE:
				nextGarbage = fishbones.obtain();
				setGarbage(nextGarbage, Fishbone.RIGHT_LANE_POSITION_X, WorldRenderer.FRUSTUM_HEIGHT, Fishbone.RIGHT_LANE_POSITION_X, WorldRenderer.FRUSTUM_HEIGHT, LaneState.RIGHT);

				break;

			case SPECIAL_ITEMS_COOKIE_BOX:
				nextSpecialItem = cookieBoxes.obtain();
				setSpecialItem(nextSpecialItem, CookieBox.RIGHT_LANE_POSITION_X, WorldRenderer.FRUSTUM_HEIGHT, CookieBox.RIGHT_LANE_POSITION_X, WorldRenderer.FRUSTUM_HEIGHT, LaneState.RIGHT);

				break;

			case SPECIAL_ITEMS_CAR_BATTERY:
				nextSpecialItem = carBatteries.obtain();
				setSpecialItem(nextSpecialItem, CarBattery.RIGHT_LANE_POSITION_X, WorldRenderer.FRUSTUM_HEIGHT, CarBattery.RIGHT_LANE_POSITION_X, WorldRenderer.FRUSTUM_HEIGHT, LaneState.RIGHT);

				break;

			case NONE:
				rightLaneIsFree = true;
				break;
			}

			if (nextObstacle != null) {
				obstacles.add(nextObstacle);
			}

			if (nextGarbage != null) {
				garbages.add(nextGarbage);
			}

			if (nextSpecialItem != null) {
				specialItems.add(nextSpecialItem);
			}
		}
	}

	private void setObstacle(Obstacle obstacle, float positionX, float positionY, float boundsX, float boundsY, LaneState laneState) {
		obstacle.position.x = positionX;
		obstacle.position.y = positionY;
		obstacle.bounds.x = boundsX;
		obstacle.bounds.y = boundsY;
		obstacle.laneState = laneState;
	}

	private void setGarbage(Garbage garbage, float positionX, float positionY, float boundsX, float boundsY, LaneState laneState) {
		garbage.position.x = positionX;
		garbage.position.y = positionY;
		garbage.bounds.x = boundsX;
		garbage.bounds.y = boundsY;
		garbage.laneState = laneState;
	}

	private void setSpecialItem(SpecialItem specialItem, float positionX, float positionY, float boundsX, float boundsY, LaneState laneState) {
		specialItem.position.x = positionX;
		specialItem.position.y = positionY;
		specialItem.bounds.x = boundsX;
		specialItem.bounds.y = boundsY;
		specialItem.laneState = laneState;
	}

	private void checkCollisions() {
		if (!spotCollision) checkObstacleCollisions();
		checkGarbageCollisions();
		checkSpecialItemCollisions();
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
						if ((obstacle instanceof StoneA) || (obstacle instanceof StoneB) || (obstacle instanceof StoneC) || (obstacle instanceof StoneD)) {
							Assets.playSound(Assets.hitStoneSound);
						}

						if (obstacle instanceof Tree) {
							Assets.playSound(Assets.hitTreeSound);
						}

						break;
					}
				}
			}
		}

		if (collision) {
			spotCollision = true;
			spotCollisionTimeSpent = 0;
			spotCollisionStateTimeSpent = 0;

			velocity.y = 0f;
			setObjectsVelocity(velocity.y);

			spot.numberLives--;

			Gdx.input.vibrate(250);

			if (spot.numberLives < 0) {
				state = WorldState.GAME_OVER;
				updateHighscores();

				Assets.playSound(Assets.gameOverScoreSound);
			}
		}
	}

	private void checkGarbageCollisions() {

		boolean collision = false;
		Garbage garbage;

		for (int i = 0; i < garbages.size(); i++) {
			garbage = garbages.get(i);

			// Checking collision with Spot
			if (garbage.position.y <= spot.position.y + spot.bounds.height) {
				if (spot.bounds.overlaps(garbage.bounds)) {

					switch(spot.spotState) {
					case GOING_FORWARD:
						collision = true;
						break;

					case GOING_LEFT:
						if (((garbage.position.y >= spot.position.y + spot.bounds.height/2) && (garbage.position.x < spot.position.x + spot.bounds.width/2)) ||
								((garbage.position.y < spot.position.y + spot.bounds.height/2) && (garbage.position.x >= spot.position.x + spot.bounds.width/2))) {
							collision = true;
						}

						break;
					case GOING_RIGHT:
						if (((garbage.position.y >= spot.position.y + spot.bounds.height/2) && (garbage.position.x >= spot.position.x + spot.bounds.width/2)) ||
								((garbage.position.y < spot.position.y + spot.bounds.height/2) && (garbage.position.x < spot.position.x + spot.bounds.width/2))) {
							collision = true;
						}

						break;

					case JUMPING:
						break;

					case CROUCHING:
						break;

					}
				}
			}
			
			// Checking collision with tornado
			if (tornadoRunning && (spot.tornado.bounds.overlaps(garbage.bounds))) {
				collision = true;
			}

			if (collision) {
				Assets.playSound(Assets.hitGarbageSound);
				score += garbage.score;

				garbages.remove(garbage);
				i--;

				if (garbage instanceof PaperBallA) {
					paperBallsA.free((PaperBallA) garbage);
				}	

				if (garbage instanceof PaperBallB) {
					paperBallsB.free((PaperBallB) garbage);
				}

				if (garbage instanceof PaperBallC) {
					paperBallsC.free((PaperBallC) garbage);
				}

				if (garbage instanceof CoconutStraw) {
					coconutsStraw.free((CoconutStraw) garbage);
				}

				if (garbage instanceof CoconutNoStraw) {
					coconutsNoStraw.free((CoconutNoStraw) garbage);
				}

				if (garbage instanceof BottleBrown) {
					bottlesBrown.free((BottleBrown) garbage);
				}

				if (garbage instanceof BottleGreen) {
					bottlesGreen.free((BottleGreen) garbage);
				}

				if (garbage instanceof BottlePurple) {
					bottlesPurple.free((BottlePurple) garbage);
				}

				if (garbage instanceof CanGreen) {
					cansGreen.free((CanGreen) garbage);
				}

				if (garbage instanceof CanRed) {
					cansRed.free((CanRed) garbage);
				}

				if (garbage instanceof CanPurple) {
					cansPurple.free((CanPurple) garbage);
				}

				if (garbage instanceof Fishbone) {
					fishbones.free((Fishbone) garbage);
				}

				collision = false;
				break;
			}
		}
	}

	private void checkSpecialItemCollisions() {
		boolean collision = false;
		SpecialItem specialItem;

		for (int i = 0; i < specialItems.size(); i++) {
			specialItem = specialItems.get(i);

			if (specialItem.position.y <= spot.position.y + spot.bounds.height) {
				if (spot.bounds.overlaps(specialItem.bounds)) {

					switch(spot.spotState) {
					case GOING_FORWARD:
						collision = true;
						break;

					case GOING_LEFT:
						if (((specialItem.position.y >= spot.position.y + spot.bounds.height/2) && (specialItem.position.x < spot.position.x + spot.bounds.width/2)) ||
								((specialItem.position.y < spot.position.y + spot.bounds.height/2) && (specialItem.position.x >= spot.position.x + spot.bounds.width/2))) {
							collision = true;
						}

						break;
					case GOING_RIGHT:
						if (((specialItem.position.y >= spot.position.y + spot.bounds.height/2) && (specialItem.position.x >= spot.position.x + spot.bounds.width/2)) ||
								((specialItem.position.y < spot.position.y + spot.bounds.height/2) && (specialItem.position.x < spot.position.x + spot.bounds.width/2))) {
							collision = true;
						}

						break;

					case JUMPING:
						break;

					case CROUCHING:
						break;

					}

					if(collision) {
						specialItems.remove(specialItem);
						i--;

						if (specialItem instanceof CookieBox) {
							Assets.playSound(Assets.hitCookieBoxSound);

							if (spot.numberLives < Spot.SPOT_NUMBER_LIVES) {
								spot.numberLives++;
							}

							cookieBoxes.free((CookieBox) specialItem);
						}

						if (specialItem instanceof CarBattery) {
							Assets.playSound(Assets.hitCarBatterySound);

							carBatteries.free((CarBattery) specialItem);

							// Creating tornado
							if (!tornadoRunning) {
								Tornado tornado = tornadoes.obtain();
								tornado.position.x = spot.position.x + (Spot.SPOT_WIDTH - Tornado.TORNADO_WIDTH)/2;
								tornado.position.y = spot.position.y + 2*Spot.SPOT_HEIGHT/3;
								tornado.velocity.x = spot.velocity.x;
								tornado.velocity.y = spot.velocity.y;

								spot.tornado = tornado;
								specialItems.add(tornado);

								tornadoRunning = true;
							}

							tornadoRunningTimeSpent = 0;
							if (Settings.soundEnabled) Assets.tornadoMusic.play();
						}	

						collision = false;
						break;
					}
				}
			}
		}
	}

	private void updateHighscores() {
		for (int i = 0; i < Settings.highscores.length; i++) {
			if (score > Settings.highscores[i]) {
				for (int j = Settings.highscores.length - 1; j > i; j--) {
					Settings.highscores[j] = Settings.highscores[j-1];
				}

				Settings.highscores[i] = score;

				if (i == 0) {
					newHighscore = true;
				}

				break;
			}
		}

		Settings.save();
	}

	private void bark(float deltaTime) {
		timeSpentSinceLastBark += deltaTime;		

		if ((state == WorldState.RUNNING) && (timeSpentSinceLastBark > SPOT_BARK_MIN_INTERVAL) && (random.nextFloat() > 0.9996f)) {
			timeSpentSinceLastBark= 0;

			Assets.playSound(Assets.barkSound);
		}
	}

	private void setObjectsVelocity(float velocity) {
		for (Obstacle obstacle : obstacles) {
			obstacle.velocity.y = -velocity;
		}

		for (Garbage garbage : garbages) {
			garbage.velocity.x = 0;
			garbage.velocity.y = -velocity;
		}

		for (SpecialItem specialItem : specialItems) {
			if (!(specialItem instanceof Tornado)) {
				specialItem.velocity.y = -velocity;
			}
		}
	}
}