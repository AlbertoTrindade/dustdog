package br.ufpe.cin.dustdog.objects.obstacles;

import br.ufpe.cin.dustdog.objects.spot.Spot;
import br.ufpe.cin.dustdog.world.World;

public class Tree extends Obstacle {
	
	public static final float TREE_WIDTH = 2.08f;
	public static final float TREE_HEIGHT = 2.4f;
	
	public static final float TREE_COLLISION_WIDTH = 0.59f;
	public static final float TREE_COLLISION_HEIGHT = Spot.SPOT_COLLISION_HEIGHT/10;
	public static final float TREE_COLLISION_POSITION_X = 0.92f;
	
	public static final float LEFT_LANE_POSITION_X = 1.7f;
	public static final float CENTRAL_LANE_POSITION_X = 3.9f;
	public static final float RIGHT_LANE_POSITION_X = 6.1f;

	public Tree(float x, float y, float width, float height) {
		super(x, y, width, height);
		bounds.width = TREE_COLLISION_WIDTH;
		bounds.height = TREE_COLLISION_HEIGHT;
		
		velocity.y = -World.velocity.y;
	}
}
