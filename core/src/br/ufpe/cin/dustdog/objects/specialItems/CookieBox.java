package br.ufpe.cin.dustdog.objects.specialItems;

import br.ufpe.cin.dustdog.world.World;

public class CookieBox extends SpecialItem {
	public static final float COOKIE_BOX_WIDTH = 1.114f;
	public static final float COOKIE_BOX_HEIGHT = 1.3f;
	
	public static final float LEFT_LANE_POSITION_X = 2f;
	public static final float CENTRAL_LANE_POSITION_X = 4.2f;
	public static final float RIGHT_LANE_POSITION_X = 6.4f;

	public CookieBox(float x, float y, float width, float height) {
		super(x, y, width, height);
		velocity.y = -World.WORLD_VELOCITY;
	}
}
