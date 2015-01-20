package br.ufpe.cin.dustdog.objects.garbages;

import br.ufpe.cin.dustdog.world.World;

public class CoconutNoStraw extends Garbage {
	
	public static final int COCONUT_NO_STRAW_SCORE = 1;
	
	public static final float COCONUT_NO_STRAW_WIDTH = 0.63f;
	public static final float COCONUT_NO_STRAW_HEIGHT = 0.645f;
	
	public static final float LEFT_LANE_POSITION_X = 2.3f;
	public static final float CENTRAL_LANE_POSITION_X = 4.5f;
	public static final float RIGHT_LANE_POSITION_X = 6.7f;

	public CoconutNoStraw(float x, float y, float width, float height) {
		super(x, y, width, height);
		velocity.y = -World.WORLD_VELOCITY;
		
		score = COCONUT_NO_STRAW_SCORE;
	}

}
