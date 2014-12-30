package br.ufpe.cin.dustdog.objects.spot;

import br.ufpe.cin.dustdog.objects.DynamicGameObject;
import br.ufpe.cin.dustdog.world.World;

public class Spot extends DynamicGameObject {
	
	public SpotState spotState;

	public float stateTime;
	
	public static final float POSITION_Y = 0.5f;
	public static final float SPOT_VELOCITY = 1f;

	public Spot(float x, float y, float width, float height) {
		super(x, y, width, height);
		spotState = SpotState.SPOT_FORWARD;
		stateTime = 0;
	}

	public void update(float deltaTime, SwipeDirection swipeDirection) {
		
		switch (swipeDirection) {
		
		case LEFT:
		
			position.x = World.WORLD_WIDTH - 4f;
			velocity.x = SPOT_VELOCITY;
			//stateTime = 0;
			spotState = SpotState.SPOT_LEFT;

			break;

		case RIGHT:
			
			position.x = World.WORLD_WIDTH + 4f;
			velocity.x = SPOT_VELOCITY;
			//stateTime = 0;
			spotState = SpotState.SPOT_RIGTH;

			break;

		case UP:
			
			position.y = POSITION_Y + 1.5f;
			velocity.x = SPOT_VELOCITY;
			//stateTime = 0;
			spotState = SpotState.SPOT_JUMP;

			break;

		case DOWN:
			
			position.y = POSITION_Y - 0.2f;
			velocity.x = SPOT_VELOCITY;
			//stateTime = 0;
			spotState = SpotState.SPOT_DOWN;

			break;
			
		case NONE:
			
			position.y = POSITION_Y;
			position.x = World.WORLD_WIDTH - 5.6f;
			velocity.x = SPOT_VELOCITY;
			//stateTime = 0;
			spotState = SpotState.SPOT_FORWARD;
			
			break;

		}
		stateTime += deltaTime;
	}

}
