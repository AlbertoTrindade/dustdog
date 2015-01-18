package br.ufpe.cin.dustdog.objects;

import java.util.Random;

public class LevelGenerator {

	public final Random random;

	public final int NONE = 0;
	public final int OBSTACLE_STONE = 1;
	public final int OBSTACLE_TREE = 2;
	public final int GARBAGE = 3;

	public final int[][] map = new int[][] 
			{{NONE, OBSTACLE_STONE, NONE},
			{NONE, NONE, NONE},
			{OBSTACLE_STONE, NONE, OBSTACLE_STONE},
			{NONE, NONE, NONE},
			{NONE, NONE, NONE},
			{OBSTACLE_STONE, NONE, NONE},
			{NONE, NONE, NONE},
			{NONE, OBSTACLE_STONE, NONE},
			{NONE, NONE, NONE},
			{OBSTACLE_TREE, NONE, NONE},
			{NONE, NONE, OBSTACLE_STONE},
			{NONE, NONE, NONE},
			{NONE, NONE, NONE},
			{NONE, OBSTACLE_TREE, NONE},
			{NONE, NONE, NONE},
			{NONE, NONE, NONE},
			{NONE, OBSTACLE_STONE, NONE},
			{NONE, NONE, NONE},
			{OBSTACLE_STONE, NONE, OBSTACLE_TREE},
			{NONE, NONE, NONE},
			{NONE, NONE, OBSTACLE_STONE},
			{NONE, NONE, NONE},
			{OBSTACLE_STONE, NONE, NONE},
			{NONE, NONE, NONE},
			{NONE, OBSTACLE_STONE, NONE},
			{NONE, NONE, NONE},
			{OBSTACLE_STONE, NONE, NONE},
			{NONE, NONE, NONE},
			{NONE, NONE, OBSTACLE_STONE},
			{NONE, NONE, NONE},
			{NONE, NONE, NONE},
			{OBSTACLE_STONE, NONE, NONE},
			{NONE, NONE, NONE},
			{NONE, NONE, NONE},
			{NONE, OBSTACLE_STONE, NONE},
			{NONE, NONE, NONE},
			{NONE, NONE, OBSTACLE_TREE},
			{NONE, NONE, NONE},
			{NONE, OBSTACLE_STONE, NONE},
			{NONE, NONE, NONE},
			{NONE, NONE, NONE},
			{NONE, NONE, OBSTACLE_STONE},
			{OBSTACLE_STONE, NONE, NONE},
			{NONE, NONE, NONE},
			{OBSTACLE_TREE, NONE, NONE},
			{NONE, NONE, NONE},
			{OBSTACLE_STONE, NONE, NONE},
			{NONE, NONE, NONE},
			{NONE, OBSTACLE_TREE, NONE},
			{NONE, NONE, NONE},
			{OBSTACLE_STONE, NONE, NONE},
			{NONE, NONE, NONE},
			{NONE, NONE, NONE}};

	public int[] mapIndex;
	public int[] noneCount;

	public final int NONE_MAX = 30;

	public LevelGenerator() {
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

		if (nextObject != LevelGeneratorObject.NONE || noneCount[laneIndex] == NONE_MAX){
			incrementMapIndex(laneIndex);
		}

		if (nextObject == LevelGeneratorObject.NONE) {
			if (noneCount[laneIndex] < NONE_MAX) {
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
			else if (randomNumber < 0.4f) {
				object = LevelGeneratorObject.GARBAGE_COCONUT_NO_STRAW;
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
