package br.ufpe.cin.dustdog.world;

import java.util.ArrayList;
import java.util.List;

import br.ufpe.cin.dustdog.Assets;
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
import br.ufpe.cin.dustdog.objects.obstacles.BeachUmbrellaBlue;
import br.ufpe.cin.dustdog.objects.obstacles.BeachUmbrellaGreen;
import br.ufpe.cin.dustdog.objects.obstacles.BeachUmbrellaRed;
import br.ufpe.cin.dustdog.objects.obstacles.BeachUmbrellaYellow;
import br.ufpe.cin.dustdog.objects.obstacles.Obstacle;
import br.ufpe.cin.dustdog.objects.obstacles.Sandcastle;
import br.ufpe.cin.dustdog.objects.obstacles.StoneA;
import br.ufpe.cin.dustdog.objects.obstacles.StoneB;
import br.ufpe.cin.dustdog.objects.obstacles.StoneC;
import br.ufpe.cin.dustdog.objects.obstacles.StoneD;
import br.ufpe.cin.dustdog.objects.obstacles.Tree;
import br.ufpe.cin.dustdog.objects.specialItems.CarBattery;
import br.ufpe.cin.dustdog.objects.specialItems.CookieBox;
import br.ufpe.cin.dustdog.objects.specialItems.SpecialItem;
import br.ufpe.cin.dustdog.objects.specialItems.Starfish;
import br.ufpe.cin.dustdog.objects.specialItems.Tornado;
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
	
	List<Tree> remainingTrees;
	List<BeachUmbrellaBlue> remainingBeachUmbrellasBlue;
	List<BeachUmbrellaGreen> remainingBeachUmbrellasGreen;
	List<BeachUmbrellaRed> remainingBeachUmbrellasRed;
	List<BeachUmbrellaYellow> remainingBeachUmbrellasYellow;

	public WorldRenderer(SpriteBatch batch, World world) {
		this.world = world;
		this.camera = new OrthographicCamera(FRUSTUM_WIDTH, FRUSTUM_HEIGHT);
		this.camera.position.set(FRUSTUM_WIDTH/2, FRUSTUM_HEIGHT/2, 0);
		this.batch = batch;
		
		remainingTrees = new ArrayList<Tree>();
		remainingBeachUmbrellasBlue = new ArrayList<BeachUmbrellaBlue>();
		remainingBeachUmbrellasGreen = new ArrayList<BeachUmbrellaGreen>();
		remainingBeachUmbrellasRed = new ArrayList<BeachUmbrellaRed>();
		remainingBeachUmbrellasYellow = new ArrayList<BeachUmbrellaYellow>();
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

		renderObstacles();
		renderGarbages();
		renderSpecialItems();
		renderSpot();
		
		renderRemainingTrees();
		renderRemainingBeachUmbrellasBlue();
		renderRemainingBeachUmbrellasGreen();
		renderRemainingBeachUmbrellasRed();
		renderRemainingBeachUmbrellasYellow();

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
			
			if (obstacle instanceof StoneA) {
				batch.draw(Assets.obstacleStoneA, obstacle.position.x, obstacle.position.y, StoneA.STONE_WIDTH, StoneA.STONE_HEIGHT);
			}
			
			if (obstacle instanceof StoneB) {
				batch.draw(Assets.obstacleStoneB, obstacle.position.x, obstacle.position.y, StoneB.STONE_WIDTH, StoneB.STONE_HEIGHT);
			}
			
			if (obstacle instanceof StoneC) {
				batch.draw(Assets.obstacleStoneC, obstacle.position.x, obstacle.position.y, StoneC.STONE_WIDTH, StoneC.STONE_HEIGHT);
			}
			
			if (obstacle instanceof StoneD) {
				batch.draw(Assets.obstacleStoneD, obstacle.position.x, obstacle.position.y, StoneD.STONE_WIDTH, StoneD.STONE_HEIGHT);
			}
			
			if (obstacle instanceof Tree) {
				if ((world.spot.position.y <= obstacle.position.y + Tree.TREE_COLLISION_HEIGHT/2) &&
					(world.spot.laneState == obstacle.laneState)){
					batch.draw(Assets.obstacleTree, obstacle.position.x, obstacle.position.y, Tree.TREE_WIDTH, Tree.TREE_HEIGHT);
				}
				else { // tree is behind spot, so it will be rendered after spot
					remainingTrees.add((Tree) obstacle);
				}
			}
			
			if (obstacle instanceof BeachUmbrellaBlue) {
				if ((world.spot.position.y <= obstacle.position.y + BeachUmbrellaBlue.BEACH_UMBRELLA_BLUE_COLLISION_HEIGHT/10) &&
					(world.spot.laneState == obstacle.laneState)){
					batch.draw(Assets.obstacleBeachUmbrellaBlue, obstacle.position.x, obstacle.position.y, BeachUmbrellaBlue.BEACH_UMBRELLA_BLUE_WIDTH, BeachUmbrellaBlue.BEACH_UMBRELLA_BLUE_HEIGHT);
				}
				else { // beach umbrella is behind spot, so it will be rendered after spot
					remainingBeachUmbrellasBlue.add((BeachUmbrellaBlue) obstacle);
				}
			}
			
			if (obstacle instanceof BeachUmbrellaGreen) {
				if ((world.spot.position.y <= obstacle.position.y + BeachUmbrellaGreen.BEACH_UMBRELLA_GREEN_COLLISION_HEIGHT/10) &&
					(world.spot.laneState == obstacle.laneState)){
					batch.draw(Assets.obstacleBeachUmbrellaGreen, obstacle.position.x, obstacle.position.y, BeachUmbrellaGreen.BEACH_UMBRELLA_GREEN_WIDTH, BeachUmbrellaGreen.BEACH_UMBRELLA_GREEN_HEIGHT);
				}
				else { // beach umbrella is behind spot, so it will be rendered after spot
					remainingBeachUmbrellasGreen.add((BeachUmbrellaGreen) obstacle);
				}
			}
			
			if (obstacle instanceof BeachUmbrellaRed) {
				if ((world.spot.position.y <= obstacle.position.y + BeachUmbrellaRed.BEACH_UMBRELLA_RED_COLLISION_HEIGHT/10) &&
					(world.spot.laneState == obstacle.laneState)){
					batch.draw(Assets.obstacleBeachUmbrellaRed, obstacle.position.x, obstacle.position.y, BeachUmbrellaRed.BEACH_UMBRELLA_RED_WIDTH, BeachUmbrellaRed.BEACH_UMBRELLA_RED_HEIGHT);
				}
				else { // beach umbrella is behind spot, so it will be rendered after spot
					remainingBeachUmbrellasRed.add((BeachUmbrellaRed) obstacle);
				}
			}
			
			if (obstacle instanceof BeachUmbrellaYellow) {
				if ((world.spot.position.y <= obstacle.position.y + BeachUmbrellaYellow.BEACH_UMBRELLA_YELLOW_COLLISION_HEIGHT/10) &&
					(world.spot.laneState == obstacle.laneState)){
					batch.draw(Assets.obstacleBeachUmbrellaYellow, obstacle.position.x, obstacle.position.y, BeachUmbrellaYellow.BEACH_UMBRELLA_YELLOW_WIDTH, BeachUmbrellaYellow.BEACH_UMBRELLA_YELLOW_HEIGHT);
				}
				else { // beach umbrella is behind spot, so it will be rendered after spot
					remainingBeachUmbrellasYellow.add((BeachUmbrellaYellow) obstacle);
				}
			}
			
			if (obstacle instanceof Sandcastle) {
				batch.draw(Assets.obstacleSandcastle, obstacle.position.x, obstacle.position.y, Sandcastle.SANDCASTLE_WIDTH, Sandcastle.SANDCASTLE_HEIGHT);
			}
		}
	}
	
	private void renderGarbages() {
		for (Garbage garbage : world.garbages) {
			
			if (garbage instanceof PaperBallA) {
				batch.draw(Assets.garbagePaperBallA, garbage.position.x, garbage.position.y, PaperBallA.PAPER_BALL_A_WIDTH, PaperBallA.PAPER_BALL_A_HEIGHT);
			}
			
			if (garbage instanceof PaperBallB) {
				batch.draw(Assets.garbagePaperBallB, garbage.position.x, garbage.position.y, PaperBallB.PAPER_BALL_B_WIDTH, PaperBallB.PAPER_BALL_B_HEIGHT);
			}
			
			if (garbage instanceof PaperBallC) {
				batch.draw(Assets.garbagePaperBallC, garbage.position.x, garbage.position.y, PaperBallC.PAPER_BALL_C_WIDTH, PaperBallC.PAPER_BALL_C_HEIGHT);
			}
			
			if (garbage instanceof CoconutStraw) {
				batch.draw(Assets.garbageCoconutStraw, garbage.position.x, garbage.position.y, CoconutStraw.COCONUT_STRAW_WIDTH, CoconutStraw.COCONUT_STRAW_HEIGHT);
			}
			
			if (garbage instanceof CoconutNoStraw) {
				batch.draw(Assets.garbageCoconutNoStraw, garbage.position.x, garbage.position.y, CoconutNoStraw.COCONUT_NO_STRAW_WIDTH, CoconutNoStraw.COCONUT_NO_STRAW_HEIGHT);
			}
			
			if (garbage instanceof BottleBrown) {
				batch.draw(Assets.garbageBottleBrown, garbage.position.x, garbage.position.y, BottleBrown.BOTTLE_BROWN_WIDTH, BottleBrown.BOTTLE_BROWN_HEIGHT);
			}
			
			if (garbage instanceof BottleGreen) {
				batch.draw(Assets.garbageBottleGreen, garbage.position.x, garbage.position.y, BottleGreen.BOTTLE_GREEN_WIDTH, BottleGreen.BOTTLE_GREEN_HEIGHT);
			}
			
			if (garbage instanceof BottlePurple) {
				batch.draw(Assets.garbageBottlePurple, garbage.position.x, garbage.position.y, BottlePurple.BOTTLE_PURPLE_WIDTH, BottlePurple.BOTTLE_PURPLE_HEIGHT);
			}
			
			if (garbage instanceof CanGreen) {
				batch.draw(Assets.garbageCanGreen, garbage.position.x, garbage.position.y, CanGreen.CAN_GREEN_WIDTH, CanGreen.CAN_GREEN_HEIGHT);
			}
			
			if (garbage instanceof CanRed) {
				batch.draw(Assets.garbageCanRed, garbage.position.x, garbage.position.y, CanRed.CAN_RED_WIDTH, CanRed.CAN_RED_HEIGHT);
			}
			
			if (garbage instanceof CanPurple) {
				batch.draw(Assets.garbageCanPurple, garbage.position.x, garbage.position.y, CanPurple.CAN_PURPLE_WIDTH, CanPurple.CAN_PURPLE_HEIGHT);
			}
			
			if (garbage instanceof Fishbone) {
				batch.draw(Assets.garbageFishbone, garbage.position.x, garbage.position.y, Fishbone.FISHBONE_WIDTH, Fishbone.FISHBONE_HEIGHT);
			}
		}
	}
	
	private void renderSpecialItems() {
		for (SpecialItem specialItem : world.specialItems) {
			if (specialItem instanceof CookieBox) {
				batch.draw(Assets.specialItemCookieBox, specialItem.position.x, specialItem.position.y, CookieBox.COOKIE_BOX_WIDTH, CookieBox.COOKIE_BOX_HEIGHT);
			}
			
			if (specialItem instanceof CarBattery) {
				batch.draw(Assets.specialItemCarBattery, specialItem.position.x, specialItem.position.y, CarBattery.CAR_BATTERY_WIDTH, CarBattery.CAR_BATTERY_HEIGHT);
			}
			
			if (specialItem instanceof Starfish) {
				batch.draw(Assets.specialItemStarfish, specialItem.position.x, specialItem.position.y, Starfish.STARFISH_WIDTH, Starfish.STARFISH_HEIGHT);
			}
		}
		
		for (SpecialItem specialItem : world.specialItems) {
			if ((specialItem instanceof Tornado) && (world.spot.visible)) {
				TextureRegion keyFrame = Assets.specialItemTornado.getKeyFrame(world.spot.tornado.stateTime, true);
				batch.draw(keyFrame, specialItem.position.x, specialItem.position.y, Tornado.TORNADO_WIDTH, Tornado.TORNADO_HEIGHT);
			}
		}
	}
	
	private void renderRemainingTrees() {
		for (Tree tree : remainingTrees) {
			batch.draw(Assets.obstacleTree, tree.position.x, tree.position.y, Tree.TREE_WIDTH, Tree.TREE_HEIGHT);
		}
		
		remainingTrees.clear();
	}
	
	private void renderRemainingBeachUmbrellasBlue() {
		for (BeachUmbrellaBlue beachUmbrella : remainingBeachUmbrellasBlue) {
			batch.draw(Assets.obstacleBeachUmbrellaBlue, beachUmbrella.position.x, beachUmbrella.position.y, BeachUmbrellaBlue.BEACH_UMBRELLA_BLUE_WIDTH, BeachUmbrellaBlue.BEACH_UMBRELLA_BLUE_HEIGHT);
		}
		
		remainingBeachUmbrellasBlue.clear();
	}
	
	private void renderRemainingBeachUmbrellasGreen() {
		for (BeachUmbrellaGreen beachUmbrella : remainingBeachUmbrellasGreen) {
			batch.draw(Assets.obstacleBeachUmbrellaGreen, beachUmbrella.position.x, beachUmbrella.position.y, BeachUmbrellaGreen.BEACH_UMBRELLA_GREEN_WIDTH, BeachUmbrellaGreen.BEACH_UMBRELLA_GREEN_HEIGHT);
		}
		
		remainingBeachUmbrellasGreen.clear();
	}
	
	private void renderRemainingBeachUmbrellasRed() {
		for (BeachUmbrellaRed beachUmbrella : remainingBeachUmbrellasRed) {
			batch.draw(Assets.obstacleBeachUmbrellaRed, beachUmbrella.position.x, beachUmbrella.position.y, BeachUmbrellaRed.BEACH_UMBRELLA_RED_WIDTH, BeachUmbrellaRed.BEACH_UMBRELLA_RED_HEIGHT);
		}
		
		remainingBeachUmbrellasRed.clear();
	}
	
	private void renderRemainingBeachUmbrellasYellow() {
		for (BeachUmbrellaYellow beachUmbrella : remainingBeachUmbrellasYellow) {
			batch.draw(Assets.obstacleBeachUmbrellaYellow, beachUmbrella.position.x, beachUmbrella.position.y, BeachUmbrellaYellow.BEACH_UMBRELLA_YELLOW_WIDTH, BeachUmbrellaYellow.BEACH_UMBRELLA_YELLOW_HEIGHT);
		}
		
		remainingBeachUmbrellasYellow.clear();
	}
}
