package br.ufpe.cin.dustdog.objects.garbages;

import br.ufpe.cin.dustdog.world.World;

public class CoconutStraw extends Garbage {
	
	public static final int COCONUT_STRAW_SCORE = 1;
	
	public static final float COCONUT_STRAW_WIDTH = 0.645f;
	public static final float COCONUT_STRAW_HEIGHT = 0.748f;
	
	public static final float LEFT_LANE_POSITION_X = 2.4f;
	public static final float CENTRAL_LANE_POSITION_X = 4.6f;
	public static final float RIGHT_LANE_POSITION_X = 6.8f;

	public CoconutStraw(float x, float y, float width, float height) {
		super(x, y, width, height);
		velocity.y = -World.velocity.y;
		
		score = COCONUT_STRAW_SCORE;
	}

}
