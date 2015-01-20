package br.ufpe.cin.dustdog.objects.garbages;

import br.ufpe.cin.dustdog.world.World;

public class PaperBallA extends Garbage {
	
	public static final int PAPER_BALL_A_SCORE = 1;
	
	public static final float PAPER_BALL_A_WIDTH = 0.527f;
	public static final float PAPER_BALL_A_HEIGHT = 0.41f;
	
	public static final float LEFT_LANE_POSITION_X = 2.4f;
	public static final float CENTRAL_LANE_POSITION_X = 4.6f;
	public static final float RIGHT_LANE_POSITION_X = 6.8f;

	public PaperBallA(float x, float y, float width, float height) {
		super(x, y, width, height);
		velocity.y = -World.WORLD_VELOCITY;
		
		score = PAPER_BALL_A_SCORE;
	}

}
