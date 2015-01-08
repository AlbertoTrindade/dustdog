package br.ufpe.cin.dustdog.objects.spot;

import br.ufpe.cin.dustdog.objects.DynamicGameObject;
import br.ufpe.cin.dustdog.objects.LaneState;

public class Spot extends DynamicGameObject {

	public SpotState spotState;
	public LaneState laneState;

	public float stateTime;

	public static final float SPOT_POSITION_Y = 0.5f;
	public static final float SPOT_VELOCITY = 5f;

	public static final float SPOT_WIDTH = 3.54f;
	public static final float SPOT_HEIGHT = 2.4f;

	public static final float LEFT_LANE_POSITION_X = 0.55f;
	public static final float CENTRAL_LANE_POSITION_X = 3.05f;
	public static final float RIGHT_LANE_POSITION_X = 5.55f;

	public Spot(float x, float y, float width, float height) {
		super(x, y, width, height);
		goForward();

		laneState = LaneState.CENTRAL;
		stateTime = 0;
	}

	public void update(float deltaTime, SwipeDirection swipeDirection) {
		position.x += velocity.x * deltaTime;

		// Only apply spot movement (given the swipe) if it is going forward, otherwise let it finish that movement (ignoring the swipe)
		if (spotState == SpotState.GOING_FORWARD) { 
			
			switch (swipeDirection) {
			case LEFT:
				velocity.x = -SPOT_VELOCITY;
				spotState = SpotState.GOING_LEFT;
				stateTime = 0;

				break;

			case RIGHT:
				velocity.x = SPOT_VELOCITY;
				spotState = SpotState.GOING_RIGHT;
				stateTime = 0;

				break;

			case UP:
				spotState = SpotState.JUMPING;

				break;

			case DOWN:
				spotState = SpotState.CROUCHING;

				break;

			case NONE:
				break;
			}
		}

		// Update spot position based on its state (where it is going)
		switch (spotState) {
		case GOING_RIGHT:

			switch (laneState) {
			case CENTRAL:
				if (position.x >= RIGHT_LANE_POSITION_X) {
					position.x = RIGHT_LANE_POSITION_X;
					laneState = LaneState.RIGHT;
					goForward();
				}

				break;
			case LEFT:
				if (position.x >= CENTRAL_LANE_POSITION_X) {
					position.x = CENTRAL_LANE_POSITION_X;
					laneState = LaneState.CENTRAL;
					goForward();
				}

				break;

			case RIGHT:
				position.x = RIGHT_LANE_POSITION_X;
				goForward();

				break;
			}

			break;

		case GOING_LEFT:

			switch (laneState) {
			case CENTRAL:
				if (position.x <= LEFT_LANE_POSITION_X) {
					position.x = LEFT_LANE_POSITION_X;
					laneState = LaneState.LEFT;
					goForward();
				}

				break;

			case LEFT:
				position.x = LEFT_LANE_POSITION_X;
				goForward();

				break;

			case RIGHT:
				if (position.x <= CENTRAL_LANE_POSITION_X) {
					position.x = CENTRAL_LANE_POSITION_X;
					laneState = LaneState.CENTRAL;
					goForward();
				}

				break;
			}

			break;

		case GOING_FORWARD:
			break;

		case JUMPING:
			goForward(); // TODO: call this within an if-condition to check whether movement is over
			break;	

		case CROUCHING:
			goForward(); // TODO: call this within an if-condition to check whether movement is over
			break;
		}

		stateTime += deltaTime;
	}

	private void goForward() {
		velocity.x = 0;
		spotState = SpotState.GOING_FORWARD;
	}
}
