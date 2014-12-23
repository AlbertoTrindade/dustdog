package br.ufpe.cin.dustdog.world;

import br.ufpe.cin.dustdog.Assets;
import br.ufpe.cin.dustdog.SwipeDirection;
import br.ufpe.cin.dustdog.parallax.ParallaxBackground;
import br.ufpe.cin.dustdog.parallax.ParallaxLayer;

public class World {
	
	public interface WorldListener {
		// TODO: add method signature for each action in World that generates sound (hit a obstacle, for example)
	}
	
	public static final float WORLD_WIDTH = 10;
	
	public final WorldListener worldListener;
	
	public final ParallaxBackground background;
	
	public int score;
	public WorldState state;
	
	public World(WorldListener worldListener) {
		this.worldListener = worldListener;
		
		this.background = new ParallaxBackground(new ParallaxLayer(Assets.backgroundRegionGameScreen, 0, 1, ((float) Assets.backgroundGameScreen.getHeight()/Assets.SCREEN_HEIGHT)));

		this.score = 0;
		this.state = WorldState.RUNNING;
	}
	
	public void update (float deltaTime, SwipeDirection swipe) {
		updateBackground(deltaTime);
		// TODO: create and call here update methods for other game elements
	}
	
	public void updateBackground(float deltaTime) {
		background.moveY(5 * deltaTime);
	}
}
