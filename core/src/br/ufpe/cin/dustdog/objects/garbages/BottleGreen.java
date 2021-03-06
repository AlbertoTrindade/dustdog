package br.ufpe.cin.dustdog.objects.garbages;

import br.ufpe.cin.dustdog.world.World;

public class BottleGreen extends Garbage {
	
	public static final int BOTTLE_GREEN_SCORE = 5;
	
	public static final float BOTTLE_GREEN_WIDTH = 0.279f;
	public static final float BOTTLE_GREEN_HEIGHT = 0.63f;
	
	public static final float LEFT_LANE_POSITION_X = 2.5f;
	public static final float CENTRAL_LANE_POSITION_X = 4.7f;
	public static final float RIGHT_LANE_POSITION_X = 6.9f;

	public BottleGreen(float x, float y, float width, float height) {
		super(x, y, width, height);
		velocity.y = -World.velocity.y;
		
		score = BOTTLE_GREEN_SCORE;
	}

}
