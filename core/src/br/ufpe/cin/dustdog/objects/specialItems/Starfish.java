package br.ufpe.cin.dustdog.objects.specialItems;

import br.ufpe.cin.dustdog.world.World;

public class Starfish extends SpecialItem {
	public static final float STARFISH_WIDTH = 1.155f;
	public static final float STARFISH_HEIGHT = 1.1445f;
	
	public static final float LEFT_LANE_POSITION_X = 2f;
	public static final float CENTRAL_LANE_POSITION_X = 4.2f;
	public static final float RIGHT_LANE_POSITION_X = 6.4f;

	public Starfish(float x, float y, float width, float height) {
		super(x, y, width, height);
		velocity.y = -World.velocity.y;
	}
}
