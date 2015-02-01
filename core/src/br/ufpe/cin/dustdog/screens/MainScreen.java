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

public class MainScreen extends ScreenAdapter {
	
	Dustdog game;
	OrthographicCamera camera;
	
	Rectangle settingsButtonBounds;
	Rectangle bestScoreBounds;
	
	boolean tapPlayActive;
	boolean settingsButtonActive;
	boolean bestScoreActive;
	
	Vector3 touchPoint;
	
	public MainScreen(Dustdog game) {
		this.game = game;
		
		camera = new OrthographicCamera(Assets.SCREEN_WIDTH, Assets.SCREEN_HEIGHT);
		camera.position.set(Assets.SCREEN_WIDTH/2, Assets.SCREEN_HEIGHT/2, 0);
		
		settingsButtonBounds = new Rectangle(575, 10, 95, 98);
		bestScoreBounds = new Rectangle(10, 225, 255, 140);
		
		tapPlayActive = false;
		settingsButtonActive = false;
		bestScoreActive = false;
		
		touchPoint = new Vector3();
	}
	
	public void update() {
		if (Gdx.input.isTouched()) {
			camera.unproject(touchPoint.set(Gdx.input.getX(), Gdx.input.getY(), 0));
			
			if (settingsButtonBounds.contains(touchPoint.x, touchPoint.y)) {
				settingsButtonActive = true;
				return;
			}
			else if (bestScoreBounds.contains(touchPoint.x, touchPoint.y)) {
				bestScoreActive = true;
				return;
			}
			else {
				settingsButtonActive = false;
				bestScoreActive = false;
			}
		}
		
		if (Gdx.input.justTouched()) {
			tapPlayActive = true;
			return;
		}
		
		if (Gdx.input.isKeyPressed(Keys.BACK) || Gdx.input.isKeyPressed(Keys.ESCAPE)) {
			Gdx.app.exit();
		}
		
		if (settingsButtonActive) {
			settingsButtonActive = false;
			Assets.playSound(Assets.clickSound);
			game.setScreen(new SettingsScreen(game));
			return;
		}
		
		if (bestScoreActive) {
			bestScoreActive = false;
			Assets.playSound(Assets.clickSound);
			game.setScreen(new HighscoresScreen(game));
			return;
		}
		
		if (tapPlayActive) {
			tapPlayActive = false;
			game.setScreen(new GameScreen(game));
			return;
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
		game.batcher.draw(Assets.backgroundRegionMainScreen, 0, 0);
		game.batcher.end();
		
		game.batcher.enableBlending();
		game.batcher.begin();
		
		game.batcher.draw(Assets.mainScreenLogo, 325, 695);
		game.batcher.draw((bestScoreActive ? Assets.mainScreenBestScoreActive : Assets.mainScreenBestScore), 10, 225);
		
		String bestScore = Integer.toString(Settings.highscores[0]);
		float scoreX = (Assets.mainScreenBestScoreActive.getRegionWidth() - Assets.font48.getBounds(bestScore).width) / 2;
		Assets.font48.draw(game.batcher, bestScore, 10 + scoreX, 290);
		
		game.batcher.draw((tapPlayActive ? Assets.mainScreenTapPlayActive : Assets.mainScreenTapPlay), 10, 10);
		game.batcher.draw((settingsButtonActive ? Assets.mainScreenSettingsButtonActive : Assets.mainScreenSettingsButton), 575, 10);
		
		game.batcher.end();
	}
	
	@Override
	public void render(float delta) {
		update();
		draw();
	}
}
