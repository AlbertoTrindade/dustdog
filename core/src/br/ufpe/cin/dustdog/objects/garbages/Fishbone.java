package br.ufpe.cin.dustdog.objects.garbages;

import br.ufpe.cin.dustdog.world.World;

public class Fishbone extends Garbage {
	
	public static final int FISHBONE_SCORE = 2;
	
	public static final float FISHBONE_WIDTH = 0.674f;
	public static final float FISHBONE_HEIGHT = 0.308f;
	
	public static final float LEFT_LANE_POSITION_X = 2.3f;
	public static final float CENTRAL_LANE_POSITION_X = 4.5f;
	public static final float RIGHT_LANE_POSITION_X = 6.7f;

	public Fishbone(float x, float y, float width, float height) {
		super(x, y, width, height);
		velocity.y = -World.velocity.y;
		
		score = FISHBONE_SCORE;
	}

}
