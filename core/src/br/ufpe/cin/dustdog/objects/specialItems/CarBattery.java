package br.ufpe.cin.dustdog.objects.specialItems;

import br.ufpe.cin.dustdog.world.World;

public class CarBattery extends SpecialItem {
	public static final float CAR_BATTERY_WIDTH = 1.21f;
	public static final float CAR_BATTERY_HEIGHT = 0.96f;
	
	public static final float LEFT_LANE_POSITION_X = 2f;
	public static final float CENTRAL_LANE_POSITION_X = 4.2f;
	public static final float RIGHT_LANE_POSITION_X = 6.4f;

	public CarBattery(float x, float y, float width, float height) {
		super(x, y, width, height);
		velocity.y = -World.velocity.y;
	}
}
