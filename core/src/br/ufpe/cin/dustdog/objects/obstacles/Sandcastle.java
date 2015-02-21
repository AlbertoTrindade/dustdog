package br.ufpe.cin.dustdog.objects.obstacles;

import br.ufpe.cin.dustdog.world.World;

public class Sandcastle extends Obstacle {
	
	public static final float SANDCASTLE_WIDTH = 1.76f;
	public static final float SANDCASTLE_HEIGHT = 1.26f;
	
	public static final float LEFT_LANE_POSITION_X = 1.8f;
	public static final float CENTRAL_LANE_POSITION_X = 4f;
	public static final float RIGHT_LANE_POSITION_X = 6.2f;

	public Sandcastle(float x, float y, float width, float height) {
		super(x, y, width, height);
		velocity.y = -World.velocity.y;
	}
}
