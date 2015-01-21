package br.ufpe.cin.dustdog.objects.garbages;

import br.ufpe.cin.dustdog.world.World;

public class CanRed extends Garbage {
	
	public static final int CAN_RED_SCORE = 3;
	
	public static final float CAN_RED_WIDTH = 0.352f;
	public static final float CAN_RED_HEIGHT = 0.66f;
	
	public static final float LEFT_LANE_POSITION_X = 2.5f;
	public static final float CENTRAL_LANE_POSITION_X = 4.7f;
	public static final float RIGHT_LANE_POSITION_X = 6.9f;

	public CanRed(float x, float y, float width, float height) {
		super(x, y, width, height);
		velocity.y = -World.velocity.y;
		
		score = CAN_RED_SCORE;
	}

}
