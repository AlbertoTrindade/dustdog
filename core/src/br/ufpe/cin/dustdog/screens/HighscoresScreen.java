package br.ufpe.cin.dustdog.screens;

import br.ufpe.cin.dustdog.Assets;
import br.ufpe.cin.dustdog.Dustdog;
import br.ufpe.cin.dustdog.Settings;

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
	
	String[] highscores;
	float[] highscoresX;
	
	public HighscoresScreen(Dustdog game) {
		this.game = game;

		camera = new OrthographicCamera(Assets.SCREEN_WIDTH, Assets.SCREEN_HEIGHT);
		camera.position.set(Assets.SCREEN_WIDTH/2, Assets.SCREEN_HEIGHT/2, 0);

		backButtonBounds = new Rectangle(256, 385, 170, 66);

		touchPoint = new Vector3();

		backButtonActive = false;
		backPressed = false;
		
		highscores = new String[5];
		highscoresX = new float[5];
		
		for (int i = 0; i < highscores.length; i++) {
			highscores[i] = Integer.toString(Settings.highscores[i]);
			highscoresX[i] = (Assets.highscoresScreenHighscoresBox.getRegionWidth() - Assets.font48.getBounds(highscores[i]).width) / 2;
		}
	}
	
	public void update() {
		if (Gdx.input.isTouched()) {
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
			backButtonActive = false;
			Assets.playSound(Assets.clickSound);
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
		
		float highscoreY = 730;
		
		for (int i = 0; i < highscores.length; i++) {
			Assets.font48.draw(game.batcher, (i+1) + ".", 208, highscoreY);
			Assets.font48.draw(game.batcher, highscores[i], 73 + highscoresX[i], highscoreY);
			highscoreY -= Assets.font48.getLineHeight();
		}
		
		game.batcher.draw((backButtonActive ? Assets.highscoresScreenBackButtonActive : Assets.highscoresScreenBackButton), 256, 385);

		game.batcher.end();
	}

	@Override
	public void render(float delta) {
		update();
		draw();
	}
}
