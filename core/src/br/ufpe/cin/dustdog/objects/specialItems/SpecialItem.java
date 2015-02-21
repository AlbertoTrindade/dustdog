package br.ufpe.cin.dustdog.objects.specialItems;

import br.ufpe.cin.dustdog.objects.DynamicGameObject;
import br.ufpe.cin.dustdog.objects.LaneState;
import br.ufpe.cin.dustdog.world.World;

import com.badlogic.gdx.utils.Pool.Poolable;

public class SpecialItem extends DynamicGameObject implements Poolable {
	public LaneState laneState;

	public SpecialItem(float x, float y, float width, float height) {
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
