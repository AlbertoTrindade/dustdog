package br.ufpe.cin.dustdog.objects;

import java.util.Random;

public class LevelGenerator {
	
	public final Random random;
	
	public LevelGenerator() {
		random = new Random();
	}
	
	public LevelGeneratorObject getNextLeftLaneObject() {
		if (random.nextFloat() > 0.997f) {
			return LevelGeneratorObject.OBSTACLE_STONE;
		}
		else {
			return LevelGeneratorObject.NONE;
		}
	}
	
	public LevelGeneratorObject getNextCentralLaneObject() {
		if (random.nextFloat() > 0.997f) {
			return LevelGeneratorObject.OBSTACLE_STONE;
		}
		else {
			return LevelGeneratorObject.NONE;
		}
	}
	
	public LevelGeneratorObject getNextRightLaneObject() {
		if (random.nextFloat() > 0.997f) {
			return LevelGeneratorObject.OBSTACLE_STONE;
		}
		else {
			return LevelGeneratorObject.NONE;
		}
	}
}
