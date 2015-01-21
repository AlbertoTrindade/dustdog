package br.ufpe.cin.dustdog.objects;

import java.util.Random;

import br.ufpe.cin.dustdog.objects.spot.Spot;
import br.ufpe.cin.dustdog.world.World;

public class LevelGenerator {

	public final Random random;

	public final int NONE = 0;
	public final int NONE_GARBAGE = 1;
	public final int OBSTACLE_STONE = 2;
	public final int OBSTACLE_TREE = 3;
	public final int GARBAGE = 4;

	public final int[][] map = new int[][] 
			{{GARBAGE, OBSTACLE_STONE, NONE},
			{NONE_GARBAGE, NONE, NONE},
			{GARBAGE, NONE, OBSTACLE_STONE},
			{NONE_GARBAGE, NONE, NONE},
			{GARBAGE, NONE, NONE},
			{NONE_GARBAGE, NONE, NONE},
			{GARBAGE, NONE, NONE},
			{NONE_GARBAGE, NONE, NONE},
			{GARBAGE, NONE, NONE},
			{NONE_GARBAGE, NONE, NONE},
			{OBSTACLE_TREE, NONE_GARBAGE, NONE},
			{NONE, GARBAGE, OBSTACLE_STONE},
			{NONE, NONE_GARBAGE, NONE},
			{NONE, GARBAGE, NONE},
			{NONE, NONE_GARBAGE, NONE},
			{NONE, GARBAGE, NONE},
			{NONE, NONE_GARBAGE, NONE},
			{NONE, GARBAGE, NONE},
			{NONE, NONE, NONE_GARBAGE},
			{NONE, NONE, NONE},
			{NONE, OBSTACLE_TREE, GARBAGE},
			{NONE, NONE, NONE_GARBAGE},
			{NONE, NONE, GARBAGE},
			{NONE, NONE, NONE_GARBAGE},
			{NONE, NONE, GARBAGE},
			{NONE, NONE, NONE},
			{OBSTACLE_STONE, GARBAGE, NONE},
			{NONE, NONE_GARBAGE, NONE},
			{NONE, GARBAGE, NONE},
			{NONE, NONE_GARBAGE, NONE},
			{NONE, GARBAGE, NONE},
			{NONE, NONE_GARBAGE, NONE},
			{NONE, GARBAGE, NONE},
			{NONE, NONE_GARBAGE, NONE},
			{NONE, GARBAGE, NONE},
			{NONE_GARBAGE, NONE, NONE},
			{GARBAGE, NONE, NONE},
			{NONE_GARBAGE, NONE, NONE},
			{GARBAGE, NONE, NONE},
			{NONE_GARBAGE, NONE, NONE},
			{GARBAGE, NONE, NONE},
			{NONE_GARBAGE, NONE, NONE},
			{GARBAGE, NONE, NONE},
			{NONE, NONE, NONE},
			{OBSTACLE_STONE, NONE, NONE},
			{NONE, NONE, NONE},
			{NONE, NONE, NONE},
			{OBSTACLE_STONE, NONE, OBSTACLE_TREE},
			{NONE, NONE, NONE},
			{NONE, NONE_GARBAGE, NONE},
			{NONE, GARBAGE, NONE},
			{NONE, NONE_GARBAGE, NONE},
			{OBSTACLE_TREE, GARBAGE, NONE},
			{NONE, NONE_GARBAGE, NONE},
			{NONE, GARBAGE, NONE},
			{NONE, NONE_GARBAGE, NONE},
			{NONE, GARBAGE, NONE},
			{NONE, NONE, NONE_GARBAGE},
			{NONE, NONE, GARBAGE},
			{NONE, NONE, NONE_GARBAGE},
			{OBSTACLE_STONE, NONE, GARBAGE},
			{NONE, NONE, NONE_GARBAGE},
			{NONE, NONE, GARBAGE},
			{NONE, OBSTACLE_TREE, NONE_GARBAGE},
			{NONE, NONE, GARBAGE},
			{NONE, NONE, NONE},
			{NONE, NONE, OBSTACLE_STONE},
			{NONE, NONE, NONE},
			{NONE, NONE, GARBAGE},
			{NONE, OBSTACLE_STONE, NONE},
			{NONE, NONE_GARBAGE, NONE},
			{NONE, GARBAGE, NONE},
			{OBSTACLE_TREE, NONE_GARBAGE, NONE},
			{NONE, GARBAGE, NONE},
			{NONE, NONE_GARBAGE, NONE},
			{NONE, GARBAGE, NONE},
			{NONE, NONE, NONE},
			{NONE_GARBAGE, NONE, NONE},
			{GARBAGE, NONE, NONE},
			{NONE_GARBAGE, NONE, NONE},
			{GARBAGE, NONE, NONE},
			{NONE_GARBAGE, NONE, NONE},
			{GARBAGE, NONE, NONE},
			{NONE, NONE_GARBAGE, NONE},
			{NONE, GARBAGE, NONE},
			{OBSTACLE_TREE, NONE_GARBAGE, NONE},
			{NONE, GARBAGE, NONE},
			{NONE, NONE_GARBAGE, NONE},
			{NONE, GARBAGE, NONE},
			{NONE, NONE, NONE},
			{NONE, NONE, OBSTACLE_STONE},
			{NONE, NONE, NONE_GARBAGE},
			{NONE, NONE, GARBAGE},
			{NONE, NONE, NONE_GARBAGE},
			{NONE, OBSTACLE_TREE, GARBAGE},
			{NONE, NONE, NONE_GARBAGE},
			{NONE, NONE, GARBAGE},
			{OBSTACLE_STONE, NONE, NONE}};

	public int[] mapIndex;
	public int[] noneCount;

	public World world;
	
	public final int NONE_MAX = 20;
	public final int NONE_GARBAGE_MAX = 3;

	public LevelGenerator(World world) {
		this.world = world;
		random = new Random();

		mapIndex = new int[3];
		noneCount = new int[3];
	}

	public LevelGeneratorObject getNextLeftLaneObject() {
		return getNextObjectFromMap(0);
	}

	public LevelGeneratorObject getNextCentralLaneObject() {
		return getNextObjectFromMap(1);
	}

	public LevelGeneratorObject getNextRightLaneObject() {
		return getNextObjectFromMap(2);
	}

	private LevelGeneratorObject getNextObjectFromMap(int laneIndex) {
		LevelGeneratorObject nextObject = getObject(map[mapIndex[laneIndex]][laneIndex]);

		if (nextObject != LevelGeneratorObject.NONE){
			incrementMapIndex(laneIndex);
		}
		else {
			if ((map[mapIndex[laneIndex]][laneIndex] == NONE && noneCount[laneIndex] == NONE_MAX) || 
				(map[mapIndex[laneIndex]][laneIndex] == NONE_GARBAGE && noneCount[laneIndex] == NONE_GARBAGE_MAX)) {
				incrementMapIndex(laneIndex);
			}
		}

		if (nextObject == LevelGeneratorObject.NONE) {
			if ((map[mapIndex[laneIndex]][laneIndex] == NONE && noneCount[laneIndex] < NONE_MAX) ||
				(map[mapIndex[laneIndex]][laneIndex] == NONE_GARBAGE && noneCount[laneIndex] < NONE_GARBAGE_MAX)) {
				noneCount[laneIndex]++;
			}
			else {
				noneCount[laneIndex] = 0;
				nextObject = getObject(map[mapIndex[laneIndex]][laneIndex]);
				incrementMapIndex(laneIndex);
			}
		}

		return nextObject;
	}

	private void incrementMapIndex(int laneIndex) {
		mapIndex[laneIndex]++;

		if (mapIndex[laneIndex] == map.length) {
			mapIndex[laneIndex] = 0;
			for (int i = 0; i < 3; i++) {
				noneCount[i] = 0;
			}
		}
	}

	private LevelGeneratorObject getObject(int objectCode) {
		LevelGeneratorObject object = LevelGeneratorObject.NONE;

		switch (objectCode) {
		case OBSTACLE_STONE:
			object = LevelGeneratorObject.OBSTACLE_STONE;

			break;

		case OBSTACLE_TREE:
			object = LevelGeneratorObject.OBSTACLE_TREE;

			break;

		case GARBAGE:

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
				if (world.spot.numberBones < Spot.SPOT_NUMBER_BONES) {
					object = LevelGeneratorObject.SPECIAL_ITEMS_COOKIE_BOX;
				}
				else {
					object = LevelGeneratorObject.GARBAGE_COCONUT_NO_STRAW;
				}
			}
			else if (randomNumber < 0.6f) {
				object = LevelGeneratorObject.GARBAGE_FISHBONE;
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
			else if (randomNumber < 1f) {
				object = LevelGeneratorObject.GARBAGE_BOTTLE_PURPLE;
			}

			break;
		}

		return object;
	}
}
