package br.ufpe.cin.dustdog.objects.garbages;

import br.ufpe.cin.dustdog.objects.DynamicGameObject;
import br.ufpe.cin.dustdog.objects.LaneState;
import br.ufpe.cin.dustdog.world.World;

import com.badlogic.gdx.utils.Pool.Poolable;

public abstract class Garbage extends DynamicGameObject implements Poolable {

	public LaneState laneState;
	public int score;
	
	public Garbage(float x, float y, float width, float height) {
		super(x, y, width, height);
	}
	
	public void update(float deltaTime) {
		position.x += velocity.x * deltaTime;
		position.y += velocity.y * deltaTime;
		bounds.x = position.x;
		bounds.y = position.y;
	}
	
	@Override
	public void reset() {
		velocity.x = 0;
		velocity.y = -World.velocity.y;
	}
}
