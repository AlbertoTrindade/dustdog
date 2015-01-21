package br.ufpe.cin.dustdog.objects.garbages;

import br.ufpe.cin.dustdog.world.World;

public class PaperBallC extends Garbage {
	
	public static final int PAPER_BALL_C_SCORE = 1;
	
	public static final float PAPER_BALL_C_WIDTH = 0.47f;
	public static final float PAPER_BALL_C_HEIGHT = 0.43f;
	
	public static final float LEFT_LANE_POSITION_X = 2.4f;
	public static final float CENTRAL_LANE_POSITION_X = 4.6f;
	public static final float RIGHT_LANE_POSITION_X = 6.8f;

	public PaperBallC(float x, float y, float width, float height) {
		super(x, y, width, height);
		velocity.y = -World.velocity.y;
		
		score = PAPER_BALL_C_SCORE;
	}

}
