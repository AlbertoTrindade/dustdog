package br.ufpe.cin.dustdog.objects.garbages;

import br.ufpe.cin.dustdog.world.World;

public class BottleBrown extends Garbage {
	
	public static final int BOTTLE_BROWN_SCORE = 5;
	
	public static final float BOTTLE_BROWN_WIDTH = 0.293f;
	public static final float BOTTLE_BROWN_HEIGHT = 0.924f;
	
	public static final float LEFT_LANE_POSITION_X = 2.5f;
	public static final float CENTRAL_LANE_POSITION_X = 4.7f;
	public static final float RIGHT_LANE_POSITION_X = 6.9f;

	public BottleBrown(float x, float y, float width, float height) {
		super(x, y, width, height);
		velocity.y = -World.velocity.y;
		
		score = BOTTLE_BROWN_SCORE;
	}

}
