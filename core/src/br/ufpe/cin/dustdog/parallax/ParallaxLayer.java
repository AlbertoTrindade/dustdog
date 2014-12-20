package br.ufpe.cin.dustdog.parallax;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class ParallaxLayer {
	
	TextureRegion region;

	float ratioX, ratioY;
	float positionX, positionY;
	float regionScreenRatio;

	public ParallaxLayer(TextureRegion region, float ratioX, float ratioY, float regionScreenRatio) {
		this.region = region;
		this.ratioX = ratioX;
		this.ratioY = ratioY;
		this.regionScreenRatio = regionScreenRatio;
	}

	protected void moveX(float delta) {
		positionX += delta * ratioX;
	}

	protected void moveY(float delta) {
		positionY += delta * ratioY;
	}
}
