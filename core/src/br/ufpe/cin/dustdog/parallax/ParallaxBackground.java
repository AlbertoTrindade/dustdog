package br.ufpe.cin.dustdog.parallax;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class ParallaxBackground {
	
	ParallaxLayer layer;
	Camera camera;
	SpriteBatch batch;

	public ParallaxBackground(ParallaxLayer layer, Camera camera, SpriteBatch batch) {
		this.layer = layer;
		this.camera = camera;
		this.batch = batch;
	}

	public void render() {
		float regionHeight = camera.viewportHeight * layer.regionScreenRatio;
		
		if (layer.positionY >= regionHeight) {
			layer.positionY = 0;
		}
			
		batch.draw(layer.region, 0, regionHeight - layer.positionY, camera.viewportWidth, regionHeight);
		batch.draw(layer.region, 0, -layer.positionY, camera.viewportWidth, regionHeight);
	}

	public void moveX(float delta) {
		layer.moveX(delta);
	}

	public void moveY(float delta) {
		layer.moveY(delta);
	}
}
