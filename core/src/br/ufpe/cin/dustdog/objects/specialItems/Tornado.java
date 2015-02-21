package br.ufpe.cin.dustdog.objects.specialItems;

public class Tornado extends SpecialItem {
	public static final float TORNADO_WIDTH = 1.73f;
	public static final float TORNADO_HEIGHT = 1.5f;
	
	public static final float LEFT_LANE_POSITION_X = 2f;
	public static final float CENTRAL_LANE_POSITION_X = 4.2f;
	public static final float RIGHT_LANE_POSITION_X = 6.4f;
	
	public float stateTime;

	public Tornado(float x, float y, float width, float height) {
		super(x, y, width, height);
	}
	
	public void update(float deltaTime) {
		bounds.x = position.x;
		bounds.y = position.y;
		stateTime += deltaTime;		
	}
}
