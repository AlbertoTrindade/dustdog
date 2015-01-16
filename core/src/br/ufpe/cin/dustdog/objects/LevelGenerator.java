package br.ufpe.cin.dustdog.objects;

import java.util.Random;

public class LevelGenerator {

	public final Random random;

	public final LevelGeneratorObject[][] map = new LevelGeneratorObject[][] 
			{{LevelGeneratorObject.NONE, LevelGeneratorObject.OBSTACLE_STONE, LevelGeneratorObject.NONE},
			{LevelGeneratorObject.NONE, LevelGeneratorObject.NONE, LevelGeneratorObject.NONE},
			{LevelGeneratorObject.OBSTACLE_STONE, LevelGeneratorObject.NONE, LevelGeneratorObject.OBSTACLE_STONE},
			{LevelGeneratorObject.NONE, LevelGeneratorObject.NONE, LevelGeneratorObject.NONE},
			{LevelGeneratorObject.NONE, LevelGeneratorObject.NONE, LevelGeneratorObject.NONE},
			{LevelGeneratorObject.OBSTACLE_STONE, LevelGeneratorObject.NONE, LevelGeneratorObject.NONE},
			{LevelGeneratorObject.NONE, LevelGeneratorObject.NONE, LevelGeneratorObject.NONE},
			{LevelGeneratorObject.NONE, LevelGeneratorObject.OBSTACLE_STONE, LevelGeneratorObject.NONE},
			{LevelGeneratorObject.NONE, LevelGeneratorObject.NONE, LevelGeneratorObject.NONE},
			{LevelGeneratorObject.OBSTACLE_TREE, LevelGeneratorObject.NONE, LevelGeneratorObject.NONE},
			{LevelGeneratorObject.NONE, LevelGeneratorObject.NONE, LevelGeneratorObject.OBSTACLE_STONE},
			{LevelGeneratorObject.NONE, LevelGeneratorObject.NONE, LevelGeneratorObject.NONE},
			{LevelGeneratorObject.NONE, LevelGeneratorObject.NONE, LevelGeneratorObject.NONE},
			{LevelGeneratorObject.NONE, LevelGeneratorObject.OBSTACLE_TREE, LevelGeneratorObject.NONE},
			{LevelGeneratorObject.NONE, LevelGeneratorObject.NONE, LevelGeneratorObject.NONE},
			{LevelGeneratorObject.NONE, LevelGeneratorObject.NONE, LevelGeneratorObject.NONE},
			{LevelGeneratorObject.NONE, LevelGeneratorObject.OBSTACLE_STONE, LevelGeneratorObject.OBSTACLE_TREE},
			{LevelGeneratorObject.NONE, LevelGeneratorObject.NONE, LevelGeneratorObject.NONE},
			{LevelGeneratorObject.OBSTACLE_STONE, LevelGeneratorObject.NONE, LevelGeneratorObject.NONE},
			{LevelGeneratorObject.NONE, LevelGeneratorObject.NONE, LevelGeneratorObject.NONE},
			{LevelGeneratorObject.NONE, LevelGeneratorObject.NONE, LevelGeneratorObject.OBSTACLE_STONE},
			{LevelGeneratorObject.NONE, LevelGeneratorObject.NONE, LevelGeneratorObject.NONE},
			{LevelGeneratorObject.OBSTACLE_STONE, LevelGeneratorObject.NONE, LevelGeneratorObject.NONE},
			{LevelGeneratorObject.NONE, LevelGeneratorObject.NONE, LevelGeneratorObject.NONE},
			{LevelGeneratorObject.NONE, LevelGeneratorObject.OBSTACLE_STONE, LevelGeneratorObject.NONE},
			{LevelGeneratorObject.NONE, LevelGeneratorObject.NONE, LevelGeneratorObject.NONE},
			{LevelGeneratorObject.OBSTACLE_STONE, LevelGeneratorObject.NONE, LevelGeneratorObject.NONE},
			{LevelGeneratorObject.NONE, LevelGeneratorObject.NONE, LevelGeneratorObject.NONE},
			{LevelGeneratorObject.NONE, LevelGeneratorObject.NONE, LevelGeneratorObject.OBSTACLE_STONE},
			{LevelGeneratorObject.NONE, LevelGeneratorObject.NONE, LevelGeneratorObject.NONE},
			{LevelGeneratorObject.NONE, LevelGeneratorObject.NONE, LevelGeneratorObject.NONE},
			{LevelGeneratorObject.OBSTACLE_STONE, LevelGeneratorObject.NONE, LevelGeneratorObject.NONE},
			{LevelGeneratorObject.NONE, LevelGeneratorObject.NONE, LevelGeneratorObject.NONE},
			{LevelGeneratorObject.NONE, LevelGeneratorObject.NONE, LevelGeneratorObject.NONE},
			{LevelGeneratorObject.NONE, LevelGeneratorObject.OBSTACLE_STONE, LevelGeneratorObject.NONE},
			{LevelGeneratorObject.NONE, LevelGeneratorObject.NONE, LevelGeneratorObject.NONE},
			{LevelGeneratorObject.NONE, LevelGeneratorObject.NONE, LevelGeneratorObject.OBSTACLE_TREE},
			{LevelGeneratorObject.NONE, LevelGeneratorObject.OBSTACLE_STONE, LevelGeneratorObject.NONE},
			{LevelGeneratorObject.NONE, LevelGeneratorObject.NONE, LevelGeneratorObject.NONE},
			{LevelGeneratorObject.NONE, LevelGeneratorObject.NONE, LevelGeneratorObject.NONE},
			{LevelGeneratorObject.NONE, LevelGeneratorObject.NONE, LevelGeneratorObject.OBSTACLE_STONE},
			{LevelGeneratorObject.OBSTACLE_STONE, LevelGeneratorObject.NONE, LevelGeneratorObject.NONE},
			{LevelGeneratorObject.NONE, LevelGeneratorObject.NONE, LevelGeneratorObject.NONE},
			{LevelGeneratorObject.OBSTACLE_TREE, LevelGeneratorObject.NONE, LevelGeneratorObject.NONE},
			{LevelGeneratorObject.NONE, LevelGeneratorObject.NONE, LevelGeneratorObject.NONE},
			{LevelGeneratorObject.OBSTACLE_STONE, LevelGeneratorObject.NONE, LevelGeneratorObject.NONE},
			{LevelGeneratorObject.NONE, LevelGeneratorObject.NONE, LevelGeneratorObject.NONE},
			{LevelGeneratorObject.NONE, LevelGeneratorObject.OBSTACLE_TREE, LevelGeneratorObject.NONE},
			{LevelGeneratorObject.OBSTACLE_STONE, LevelGeneratorObject.NONE, LevelGeneratorObject.NONE},
			{LevelGeneratorObject.NONE, LevelGeneratorObject.NONE, LevelGeneratorObject.NONE},
			{LevelGeneratorObject.NONE, LevelGeneratorObject.NONE, LevelGeneratorObject.NONE}};

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
		LevelGeneratorObject nextObject = map[mapIndex[laneIndex]][laneIndex];
		
		if (nextObject != LevelGeneratorObject.NONE || noneCount[laneIndex] == NONE_MAX){
			incrementMapIndex(laneIndex);
		}

		if (nextObject == LevelGeneratorObject.NONE) {
			if (noneCount[laneIndex] < NONE_MAX) {
				noneCount[laneIndex]++;
			}
			else {
				noneCount[laneIndex] = 0;
				nextObject = map[mapIndex[laneIndex]][laneIndex];
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
}
