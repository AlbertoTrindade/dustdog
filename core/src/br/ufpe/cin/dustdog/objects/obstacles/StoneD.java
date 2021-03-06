package br.ufpe.cin.dustdog.objects.obstacles;

import br.ufpe.cin.dustdog.world.World;

public class StoneD extends Obstacle {
	
	public static final float STONE_WIDTH = 1.07f;
	public static final float STONE_HEIGHT = 0.865f;
	
	public static final float LEFT_LANE_POSITION_X = 2.1f;
	public static final float CENTRAL_LANE_POSITION_X = 4.3f;
	public static final float RIGHT_LANE_POSITION_X = 6.5f;

	public StoneD(float x, float y, float width, float height) {
		super(x, y, width, height);
		velocity.y = -World.velocity.y;
	}
}
