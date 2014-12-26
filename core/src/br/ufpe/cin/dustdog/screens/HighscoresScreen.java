package br.ufpe.cin.dustdog.screens;

import br.ufpe.cin.dustdog.Assets;
import br.ufpe.cin.dustdog.Dustdog;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

public class HighscoresScreen extends ScreenAdapter {
	
	Dustdog game;
	OrthographicCamera camera;

	Rectangle backButtonBounds;

	Vector3 touchPoint;
	
	boolean backButtonActive;
	boolean backPressed;
	
	public HighscoresScreen(Dustdog game) {
		this.game = game;

		camera = new OrthographicCamera(Assets.SCREEN_WIDTH, Assets.SCREEN_HEIGHT);
		camera.position.set(Assets.SCREEN_WIDTH/2, Assets.SCREEN_HEIGHT/2, 0);

		backButtonBounds = new Rectangle(256, 142, 170, 66);

		touchPoint = new Vector3();

		backButtonActive = false;
		backPressed = false;
	}
	
	public void update() {
		if (Gdx.input.justTouched()) {
			camera.unproject(touchPoint.set(Gdx.input.getX(), Gdx.input.getY(), 0));

			if (backButtonBounds.contains(touchPoint.x, touchPoint.y)) {
				backButtonActive = true;
				return;
			}
		}

		if (Gdx.input.isKeyPressed(Keys.BACK) || Gdx.input.isKeyPressed(Keys.ESCAPE)) { 
			backPressed = true;
		}
		else if(backPressed) { // same effect as pressing cancel button
			game.setScreen(new MainScreen(game));
		}

		if (backButtonActive) {
			game.buttonDelay();

			backButtonActive = false;
			game.setScreen(new MainScreen(game));
		}
	}

	public void draw() {
		GL20 gl = Gdx.gl;
		gl.glClearColor(1, 0, 0, 1);
		gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		camera.update();
		game.batcher.setProjectionMatrix(camera.combined);

		game.batcher.disableBlending();
		game.batcher.begin();
		game.batcher.draw(Assets.backgroundRegionSettingsScreen, 0, 0);
		game.batcher.end();

		game.batcher.enableBlending();
		game.batcher.begin();

		game.batcher.draw(Assets.highscoresScreenHighscoresBox, 73, 350);
		game.batcher.draw((backButtonActive ? Assets.highscoresScreenBackButtonActive : Assets.highscoresScreenBackButton), 256, 142);

		game.batcher.end();
	}

	@Override
	public void render(float delta) {
		update();
		draw();
	}
}
