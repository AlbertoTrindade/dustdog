package br.ufpe.cin.dustdog.objects;

import java.util.Random;

import br.ufpe.cin.dustdog.objects.spot.Spot;
import br.ufpe.cin.dustdog.world.World;

public class LevelGenerator {

	public final Random random;

	public int[] mapIndex;
	public int[] noneCount;
	public int[] noneObstacle;
	public int[] lastGarbage;
	public int[] lastObject;

	public World world;
	public float level = 0.0f; // 0.00 for easy, until 0.1 for hard
	public int pointsToChangeLevel = 10;
	public int lastPoints = 0;
	
	public final int NONE_MAX = 20;
	public final int NONE_OBSTACLE = 20;
	public final int NONE_GARBAGE_MAX = 3;
	
	public int scenario;
	public final int SCENARIO_A = 0;
	public final int SCENARIO_B = 1;
	
	public int scoreLastScenarioChange;
	public final int SCENARIO_DURATION_IN_SCORES = 1000;

	public LevelGenerator(World world) {
		this.world = world;
		random = new Random();

		mapIndex = new int[3];
		noneCount = new int[3];
		lastObject = new int[3]; //0 = nada, 1 = lixo, 2 = arvore;
		noneObstacle = new int[3];
		lastGarbage = new int[3];
		lastGarbage[0] = lastGarbage[1] = lastGarbage[2] = 3;
		
		scenario = SCENARIO_A;
		scoreLastScenarioChange = 0;
	}
	
	public LevelGeneratorObject getNextLeftLaneObject() {
		return getObject(0);
	}

	public LevelGeneratorObject getNextCentralLaneObject() {
		return getObject(1);
	}

	public LevelGeneratorObject getNextRightLaneObject() {
		return getObject(2);
	}
	
	private float levelRegulator(){
		float current = level;
		int score = world.score;
		if(score - pointsToChangeLevel >= lastPoints){
			lastPoints += pointsToChangeLevel;
			float rand = random.nextFloat();
			if( rand < 0.75f){
				current += 0.001;
			}else{
				rand = random.nextFloat();
				if( rand < 0.25f){
					// check if it is time to change scenario:
					if (world.score - scoreLastScenarioChange > SCENARIO_DURATION_IN_SCORES) {
						current -= 0.03;
						current = Math.max(current, 0.02f); // to avoid to make it so easy
						
						scenario = ((scenario == SCENARIO_A) ? SCENARIO_B : SCENARIO_A);
						scoreLastScenarioChange = world.score;
					}
					else {
						current -= 0.003;
					}
				}
				current -= 0.001;
			}
			current = Math.max(current, 0.00f);
			current = Math.min(current, 0.10f);
		}
		return current;
	}
	
	private LevelGeneratorObject getObject(int laneIndex) {
		LevelGeneratorObject object = LevelGeneratorObject.NONE;
		noneObstacle[laneIndex]++;
		lastGarbage[laneIndex]++;
		
		level = levelRegulator();
		
		float randomNumber = random.nextFloat();
		
		if (randomNumber < (0.01f-level/100) || lastGarbage[laneIndex] < 3){
			int parallel = 0;
			parallel += (lastGarbage[0] < 5?1:0);
			parallel += (lastGarbage[1] < 5?1:0);
			parallel += (lastGarbage[2] < 5?1:0);
			parallel -= (lastGarbage[laneIndex] < 5?1:0);
			
			if (noneCount[laneIndex] < NONE_GARBAGE_MAX || parallel > 0){
				lastGarbage[laneIndex]--;
				noneCount[laneIndex]++;
				return LevelGeneratorObject.NONE;
			}
				
			if (lastGarbage[laneIndex] > 3){
				//(3 to 3+lengthSequence) garbage
				int lengthSequence = (int)(random.nextFloat()*7);
				lastGarbage[laneIndex] = -lengthSequence;
			}
			object = getGarbageTipe();
		}
		else if (randomNumber < 0.9f){
			object = LevelGeneratorObject.NONE;
		}
		else if (randomNumber < (0.901f+level) ){
			int parallel = 0;
			parallel += (noneObstacle[0] < 15?1:0);
			parallel += (noneObstacle[1] < 15?1:0);
			parallel += (noneObstacle[2] < 15?1:0);
			
			if (noneObstacle[laneIndex] < NONE_MAX || parallel > 1){
				noneCount[laneIndex]++;
				return object;
			}
			
			float randomNumberObstacle = random.nextFloat();
			
			switch (scenario) {
			case SCENARIO_A: // trees and stones
				if (randomNumberObstacle < 0.6f) { 
					object = LevelGeneratorObject.OBSTACLE_TREE;
				}
				else if (randomNumberObstacle < 0.7f) {
					object = LevelGeneratorObject.OBSTACLE_STONE_A;
				}
				else if (randomNumberObstacle < 0.8f) {
					object = LevelGeneratorObject.OBSTACLE_STONE_B;
				}
				else if (randomNumberObstacle < 0.9f) {
					object = LevelGeneratorObject.OBSTACLE_STONE_C;				
				}
				else {
					object = LevelGeneratorObject.OBSTACLE_STONE_D;				
				}
				
				break;
				
			case SCENARIO_B: // beach umbrellas, stones and suncastles
				if (randomNumberObstacle < 0.125f) { 
					object = LevelGeneratorObject.OBSTACLE_BEACH_UMBRELLA_BLUE;
				}
				else if (randomNumberObstacle < 0.25f) { 
					object = LevelGeneratorObject.OBSTACLE_BEACH_UMBRELLA_GREEN;
				}
				else if (randomNumberObstacle < 0.375f) { 
					object = LevelGeneratorObject.OBSTACLE_BEACH_UMBRELLA_RED;
				}
				else if (randomNumberObstacle < 0.5f) { 
					object = LevelGeneratorObject.OBSTACLE_BEACH_UMBRELLA_YELLOW;
				}
				else if (randomNumberObstacle < 0.6125f) {
					object = LevelGeneratorObject.OBSTACLE_STONE_A;
				}
				else if (randomNumberObstacle < 0.725f) {
					object = LevelGeneratorObject.OBSTACLE_STONE_B;
				}
				else if (randomNumberObstacle < 0.8375f) {
					object = LevelGeneratorObject.OBSTACLE_STONE_C;				
				}
				else if (randomNumberObstacle < 0.95f) {
					object = LevelGeneratorObject.OBSTACLE_STONE_D;				
				}
				else {
					object = LevelGeneratorObject.OBSTACLE_SANDCASTLE;	
				}
				
				break;
			}
				
			noneObstacle[laneIndex] = 0;
		}
		
		if (object != LevelGeneratorObject.NONE)
			noneCount[laneIndex] = 0;
		else
			noneCount[laneIndex]++;
		return object;
	}
	
	private LevelGeneratorObject getGarbageTipe(){
		LevelGeneratorObject object = LevelGeneratorObject.NONE;
		
		float randomNumber = random.nextFloat();

		if (randomNumber < 0.07f) {
			object = LevelGeneratorObject.GARBAGE_PAPER_BALL_A;
		}
		else if (randomNumber < 0.14f) {
			object = LevelGeneratorObject.GARBAGE_PAPER_BALL_B;
		}
		else if (randomNumber < 0.2f) {
			object = LevelGeneratorObject.GARBAGE_PAPER_BALL_C;
		}
		else if (randomNumber < 0.3f) {
			object = LevelGeneratorObject.GARBAGE_COCONUT_STRAW;
		}
		else if (randomNumber < 0.395f) {
			object = LevelGeneratorObject.GARBAGE_COCONUT_NO_STRAW;
		}
		else if (randomNumber < 0.4f) {
			if (world.spot.numberLives < Spot.SPOT_NUMBER_LIVES) {
				object = LevelGeneratorObject.SPECIAL_ITEMS_COOKIE_BOX;
			}
			else {
				object = LevelGeneratorObject.GARBAGE_COCONUT_NO_STRAW;
			}
		}
		else if (randomNumber < 0.597f) {
			object = LevelGeneratorObject.GARBAGE_FISHBONE;
		}
		else if (randomNumber < 0.6f) {
			if (!world.tornadoRunning && !world.starfishRunning) {
				object = LevelGeneratorObject.SPECIAL_ITEMS_CAR_BATTERY;
			}
			else {
				object = LevelGeneratorObject.GARBAGE_FISHBONE;
			}
		}
		else if (randomNumber < 0.67f) {
			object = LevelGeneratorObject.GARBAGE_CAN_GREEN;
		}
		else if (randomNumber < 0.74f) {
			object = LevelGeneratorObject.GARBAGE_CAN_RED;
		}
		else if (randomNumber < 0.8f) {
			object = LevelGeneratorObject.GARBAGE_CAN_PURPLE;
		}
		else if (randomNumber < 0.87f) {
			object = LevelGeneratorObject.GARBAGE_BOTTLE_BROWN;
		}
		else if (randomNumber < 0.94f) {
			object = LevelGeneratorObject.GARBAGE_BOTTLE_GREEN;
		}
		else if (randomNumber < 0.998f) {
			object = LevelGeneratorObject.GARBAGE_BOTTLE_PURPLE;
		}
		else {
			if (!world.tornadoRunning && !world.starfishRunning) {
				object = LevelGeneratorObject.SPECIAL_ITEMS_STARFISH;
			}
			else {
				object = LevelGeneratorObject.GARBAGE_FISHBONE;
			}
		}
		
		return object;
	}
	
}
