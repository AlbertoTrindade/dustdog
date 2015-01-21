package br.ufpe.cin.dustdog.objects.spot;

import com.badlogic.gdx.input.GestureDetector;

public class DirectionGestureDetector extends GestureDetector {
	public interface DirectionListener {
		void onLeft();

		void onRight();

		void onUp();

		void onDown();
		
		void onRelease();
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
		public boolean pan(float velocityX, float velocityY, float deltaX, float deltaY) {
			if(Math.abs(deltaX) > Math.abs(deltaY)){
				if(deltaX > 0){
					directionListener.onRight();
				}
				else if(deltaX < 0){
					directionListener.onLeft();
				}
			}
			else{
				if(deltaY > 0){
					directionListener.onDown();
				}
				else if(deltaY < 0){                               
					directionListener.onUp();
				}
			}
			
			return super.pan(velocityX, velocityY, deltaX, deltaY);
		}
		
		@Override
		public boolean panStop(float velocityX, float velocityY, int pointer, int button) {
			directionListener.onRelease();			
			
			return super.panStop(velocityX, velocityY, pointer, button);
		}
	}
}
