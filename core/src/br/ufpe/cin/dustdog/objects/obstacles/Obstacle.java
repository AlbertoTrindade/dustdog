package br.ufpe.cin.dustdog.objects.obstacles;

import com.badlogic.gdx.utils.Pool.Poolable;

import br.ufpe.cin.dustdog.objects.DynamicGameObject;
import br.ufpe.cin.dustdog.objects.LaneState;

public abstract class Obstacle extends DynamicGameObject implements Poolable {

	public LaneState laneState;
	
	public Obstacle(float x, float y, float width, float height) {
		super(x, y, width, height);
	}
	
	public void update(float deltaTime) {
		position.y += velocity.y * deltaTime;
		bounds.y = position.y;
	}
	
	@Override
	public void reset() {
		
	}
}
