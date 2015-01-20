package br.ufpe.cin.dustdog.objects.specialItems;

import br.ufpe.cin.dustdog.objects.DynamicGameObject;
import br.ufpe.cin.dustdog.objects.LaneState;

import com.badlogic.gdx.utils.Pool.Poolable;

public class SpecialItem extends DynamicGameObject implements Poolable {
	public LaneState laneState;

	public SpecialItem(float x, float y, float width, float height) {
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
