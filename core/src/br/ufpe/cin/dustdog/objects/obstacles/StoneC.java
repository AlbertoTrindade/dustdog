package br.ufpe.cin.dustdog.objects.obstacles;

import br.ufpe.cin.dustdog.world.World;

public class StoneC extends Obstacle {
	
	public static final float STONE_WIDTH = 1.25f;
	public static final float STONE_HEIGHT = 1.2f;
	
	public static final float LEFT_LANE_POSITION_X = 2f;
	public static final float CENTRAL_LANE_POSITION_X = 4.2f;
	public static final float RIGHT_LANE_POSITION_X = 6.4f;

	public StoneC(float x, float y, float width, float height) {
		super(x, y, width, height);
		velocity.y = -World.velocity.y;
	}
}
