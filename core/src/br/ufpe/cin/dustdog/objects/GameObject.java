package br.ufpe.cin.dustdog.objects;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class GameObject {
	
	public Vector2 position;
	public Rectangle bounds;

	public GameObject (float x, float y, float width, float height) {
		this.position = new Vector2(x, y);
		this.bounds = new Rectangle(x, y, width, height);
	}
	
}