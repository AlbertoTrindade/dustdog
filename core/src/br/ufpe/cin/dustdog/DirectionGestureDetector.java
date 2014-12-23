package br.ufpe.cin.dustdog;

import com.badlogic.gdx.input.GestureDetector;

public class DirectionGestureDetector extends GestureDetector {
	public interface DirectionListener {
		void onLeft();

		void onRight();

		void onUp();

		void onDown();
	}

	public DirectionGestureDetector(DirectionListener directionListener) {
		super(new DirectionGestureListener(directionListener));
	}

	private static class DirectionGestureListener extends GestureAdapter {
		DirectionListener directionListener;

		public DirectionGestureListener(DirectionListener directionListener) {
			this.directionListener = directionListener;
		}

		@Override
		public boolean fling(float velocityX, float velocityY, int button) {
			if(Math.abs(velocityX) > Math.abs(velocityY)){
				if(velocityX > 100){
					directionListener.onRight();
				}
				else if(velocityX < -100){
					directionListener.onLeft();
				}
			}
			else{
				if(velocityY > 100){
					directionListener.onDown();
				}
				else if(velocityY < -100){                               
					directionListener.onUp();
				}
			}
			
			return super.fling(velocityX, velocityY, button);
		}
	}
}
