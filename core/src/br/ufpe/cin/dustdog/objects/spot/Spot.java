package br.ufpe.cin.dustdog.objects.spot;

import com.badlogic.gdx.math.Rectangle;

import br.ufpe.cin.dustdog.objects.DynamicGameObject;
import br.ufpe.cin.dustdog.objects.LaneState;

public class Spot extends DynamicGameObject {

	public static final float SPOT_POSITION_Y = 0.5f;
	public static final float SPOT_VELOCITY_X = 10f;

	public static final float SPOT_WIDTH = 3.54f;
	public static final float SPOT_HEIGHT = 2.4f;

	public static final float SPOT_WIDTH_FRAME_1 = 1.74f;
	public static final float SPOT_HEIGHT_FRAME_1 = 2.26f;

	public static final float SPOT_WIDTH_FRAME_2 = 2.74f;
	public static final float SPOT_HEIGHT_FRAME_2 = 2.31f;

	public static final float SPOT_WIDTH_FRAME_3 = 3.4f;
	public static final float SPOT_HEIGHT_FRAME_3 = 2.23f;	

	public static final float LEFT_LANE_POSITION_X = 0.55f;
	public static final float CENTRAL_LANE_POSITION_X = 3.05f;
	public static final float RIGHT_LANE_POSITION_X = 5.55f;

	public Rectangle[] frameBounds;

	public SpotState spotState;
	public LaneState laneState;

	public float stateTime;

	public boolean visible;

	public Spot(float x, float y, float width, float height) {
		super(x, y, width, height);
		goForward();

		bounds = new Rectangle(x + (SPOT_WIDTH - SPOT_WIDTH_FRAME_1)/2, y + (SPOT_HEIGHT - SPOT_HEIGHT_FRAME_1)/2, SPOT_WIDTH_FRAME_1, SPOT_HEIGHT_FRAME_1);

		frameBounds = new Rectangle[5];
		frameBounds[0] = bounds;
		frameBounds[1] = new Rectangle(x + (SPOT_WIDTH - SPOT_WIDTH_FRAME_2)/2, y + (SPOT_HEIGHT - SPOT_HEIGHT_FRAME_2)/2, SPOT_WIDTH_FRAME_2, SPOT_HEIGHT_FRAME_2);
		frameBounds[2] = new Rectangle(x + (SPOT_WIDTH - SPOT_WIDTH_FRAME_3)/2, y + (SPOT_HEIGHT - SPOT_HEIGHT_FRAME_3)/2, SPOT_WIDTH_FRAME_3, SPOT_HEIGHT_FRAME_3);
		frameBounds[3] = frameBounds[1];
		frameBounds[4] = bounds;

		laneState = LaneState.CENTRAL;
		stateTime = 0;

		visible = true;
	}

	public void update(float deltaTime, SwipeDirection swipeDirection) {
		position.x += velocity.x * deltaTime;

		for (int i = 0; i < 3; i++) {
			frameBounds[i].x += velocity.x * deltaTime;
		}

		// Only apply spot movement (given the swipe) if it is going forward, otherwise let it finish that movement (ignoring the swipe)
		if (spotState == SpotState.GOING_FORWARD) { 

			switch (swipeDirection) {
			case LEFT:
				velocity.x = -SPOT_VELOCITY_X;
				spotState = SpotState.GOING_LEFT;
				stateTime = 0;

				break;

			case RIGHT:
				velocity.x = SPOT_VELOCITY_X;
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
