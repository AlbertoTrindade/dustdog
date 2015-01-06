package br.ufpe.cin.dustdog.screens;

import br.ufpe.cin.dustdog.Assets;
import br.ufpe.cin.dustdog.Dustdog;
import br.ufpe.cin.dustdog.Settings;
import br.ufpe.cin.dustdog.world.World;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

public class SettingsScreen extends ScreenAdapter {

	Dustdog game;
	World world;
	OrthographicCamera camera;

	Rectangle soundEnabledBoxBounds;
	Rectangle musicEnabledBoxBounds;
	Rectangle cancelButtonBounds;
	Rectangle okButtonBounds;

	Vector3 touchPoint;

	boolean initialSoundEnabled;
	boolean initialMusicEnabled;

	boolean cancelButtonActive;
	boolean okButtonActive;
	boolean backPressed;

	public SettingsScreen(Dustdog game) {
		this.game = game;

		camera = new OrthographicCamera(Assets.SCREEN_WIDTH, Assets.SCREEN_HEIGHT);
		camera.position.set(Assets.SCREEN_WIDTH/2, Assets.SCREEN_HEIGHT/2, 0);

		soundEnabledBoxBounds = new Rectangle(225, 580, 68, 64);
		musicEnabledBoxBounds = new Rectangle(225, 495, 68, 64);
		cancelButtonBounds = new Rectangle(165, 400, 169, 66);
		okButtonBounds = new Rectangle(360, 400, 169, 66);

		touchPoint = new Vector3();

		initialSoundEnabled = Settings.soundEnabled;
		initialMusicEnabled = Settings.musicEnabled;

		cancelButtonActive = false;
		okButtonActive = false;
		backPressed = false;
	}

	public SettingsScreen(Dustdog game, World world) {
		this(game);
		this.world = world;
	}
	
	public void update() {
		if (Gdx.input.justTouched()) {
			camera.unproject(touchPoint.set(Gdx.input.getX(), Gdx.input.getY(), 0));

			if (soundEnabledBoxBounds.contains(touchPoint.x, touchPoint.y)) {
				Settings.soundEnabled = !Settings.soundEnabled;
				return;
			}

			if (musicEnabledBoxBounds.contains(touchPoint.x, touchPoint.y)) {
				Settings.musicEnabled = !Settings.musicEnabled;
				return;
			}
		}
		
		if (Gdx.input.isTouched()) {
			camera.unproject(touchPoint.set(Gdx.input.getX(), Gdx.input.getY(), 0));

			if (cancelButtonBounds.contains(touchPoint.x, touchPoint.y)) {
				cancelButtonActive = true;
				return;
			}

			if (okButtonBounds.contains(touchPoint.x, touchPoint.y)) {
				okButtonActive = true;
				return;
			}
		}

		if (Gdx.input.isKeyPressed(Keys.BACK) || Gdx.input.isKeyPressed(Keys.ESCAPE)) { 
			backPressed = true;
		}
		else if(backPressed) { // same effect as pressing cancel button
			Settings.soundEnabled = initialSoundEnabled;
			Settings.musicEnabled = initialMusicEnabled;
			
			goBack();
		}

		if (cancelButtonActive) {
			cancelButtonActive = false;
			Settings.soundEnabled = initialSoundEnabled;
			Settings.musicEnabled = initialMusicEnabled;
			
			goBack();			
			return;
		}

		if (okButtonActive) {
			okButtonActive = false;
			Settings.save();
			
			goBack();
		}
	}
	
	private void goBack() {
		if (world == null) { // previous screen was home
			game.setScreen(new MainScreen(game));
		}
		else { // previous screen was game
			game.setScreen(new GameScreen(game, world));
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

		game.batcher.draw(Assets.settingsScreenSettingsBox, 73, 350);
		game.batcher.draw((Settings.soundEnabled ? Assets.settingsScreenMarkedBox : Assets.settingsScreenUnmarkedBox), 225, 580);
		game.batcher.draw((Settings.musicEnabled ? Assets.settingsScreenMarkedBox : Assets.settingsScreenUnmarkedBox), 225, 495);
		game.batcher.draw((cancelButtonActive ? Assets.settingsScreenCancelButtonActive : Assets.settingsScreenCancelButton), 165, 400);
		game.batcher.draw((okButtonActive ? Assets.settingsScreenOkButtonActive : Assets.settingsScreenOkButton), 360, 400);

		game.batcher.end();
	}

	@Override
	public void render(float delta) {
		update();
		draw();
	}
}
