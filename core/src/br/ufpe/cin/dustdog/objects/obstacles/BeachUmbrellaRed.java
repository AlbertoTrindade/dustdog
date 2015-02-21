package br.ufpe.cin.dustdog.objects.obstacles;

import br.ufpe.cin.dustdog.objects.spot.Spot;
import br.ufpe.cin.dustdog.world.World;

public class BeachUmbrellaRed extends Obstacle {
	
	public static final float BEACH_UMBRELLA_RED_WIDTH = 2.466f;
	public static final float BEACH_UMBRELLA_RED_HEIGHT = 1.971f;
	
	public static final float BEACH_UMBRELLA_RED_COLLISION_WIDTH = 0.162f;
	public static final float BEACH_UMBRELLA_RED_COLLISION_HEIGHT = Spot.SPOT_COLLISION_HEIGHT/10;
	public static final float BEACH_UMBRELLA_RED_COLLISION_POSITION_X = 1.089f;
	
	public static final float LEFT_LANE_POSITION_X = 1.2f;
	public static final float CENTRAL_LANE_POSITION_X = 3.6f;
	public static final float RIGHT_LANE_POSITION_X = 6f;

	public BeachUmbrellaRed(float x, float y, float width, float height) {
		super(x, y, width, height);
		bounds.width = BEACH_UMBRELLA_RED_COLLISION_WIDTH;
		bounds.height = BEACH_UMBRELLA_RED_COLLISION_HEIGHT;
		
		velocity.y = -World.velocity.y;
	}
}
