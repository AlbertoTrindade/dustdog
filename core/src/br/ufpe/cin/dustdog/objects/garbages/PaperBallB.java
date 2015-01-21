package br.ufpe.cin.dustdog.objects.garbages;

import br.ufpe.cin.dustdog.world.World;

public class PaperBallB extends Garbage {
	
	public static final int PAPER_BALL_B_SCORE = 1;
	
	public static final float PAPER_BALL_B_WIDTH = 0.43f;
	public static final float PAPER_BALL_B_HEIGHT = 0.37f;
	
	public static final float LEFT_LANE_POSITION_X = 2.4f;
	public static final float CENTRAL_LANE_POSITION_X = 4.6f;
	public static final float RIGHT_LANE_POSITION_X = 6.8f;

	public PaperBallB(float x, float y, float width, float height) {
		super(x, y, width, height);
		velocity.y = -World.velocity.y;
		
		score = PAPER_BALL_B_SCORE;
	}

}
