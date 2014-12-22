package br.ufpe.cin.dustdog.world;

public class World {
	
	public interface WorldListener {
		// TODO: add method signature for each action in World that generates sound (hit a obstacle, for example)
	}
	
	public static final float WORLD_WIDTH = 10;
	
	public final WorldListener worldListener;
	
	public int score;
	public WorldState state;
	
	public World(WorldListener worldListener) {
		this.worldListener = worldListener;
		
		this.score = 0;
		this.state = WorldState.RUNNING;
	}
}
