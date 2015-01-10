package br.ufpe.cin.dustdog.objects.obstacles;

import br.ufpe.cin.dustdog.world.World;

public class Stone extends Obstacle {
	
	public static final float STONE_WIDTH = 1.5f;
	public static final float STONE_HEIGHT = 1.5f;
	
	public static final float LEFT_LANE_POSITION_X = 1.5f;
	public static final float CENTRAL_LANE_POSITION_X = 4f;
	public static final float RIGHT_LANE_POSITION_X = 6.5f;

	public Stone(float x, float y, float width, float height) {
		super(x, y, width, height);
		velocity.y = -World.WORLD_VELOCITY;
	}
}
