package br.ufpe.cin.dustdog.objects.garbages;

import br.ufpe.cin.dustdog.world.World;

public class CanGreen extends Garbage {
	
	public static final int CAN_GREEN_SCORE = 3;
	
	public static final float CAN_GREEN_WIDTH = 0.337f;
	public static final float CAN_GREEN_HEIGHT = 0.645f;
	
	public static final float LEFT_LANE_POSITION_X = 2.5f;
	public static final float CENTRAL_LANE_POSITION_X = 4.7f;
	public static final float RIGHT_LANE_POSITION_X = 6.9f;

	public CanGreen(float x, float y, float width, float height) {
		super(x, y, width, height);
		velocity.y = -World.WORLD_VELOCITY;
		
		score = CAN_GREEN_SCORE;
	}

}
