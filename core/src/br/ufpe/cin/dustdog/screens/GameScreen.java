package br.ufpe.cin.dustdog.screens;

import br.ufpe.cin.dustdog.Assets;
import br.ufpe.cin.dustdog.Dustdog;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector3;

public class GameScreen extends ScreenAdapter {
	
	static final int GAME_READY = 0;
	static final int GAME_RUNNING = 1;
	static final int GAME_PAUSED = 2;
	static final int GAME_OVER = 3;
	
	int gameState;
	
	Dustdog game;
	OrthographicCamera camera;
	
	Vector3 touchPoint;
	boolean backPressed;

	public GameScreen(Dustdog game) {
		this.game = game;
		
		gameState = GAME_READY;

		camera = new OrthographicCamera(Assets.SCREEN_WIDTH, Assets.SCREEN_HEIGHT);
		camera.position.set(Assets.SCREEN_WIDTH/2, Assets.SCREEN_HEIGHT/2, 0);

		touchPoint = new Vector3();
		backPressed = false;
	}

	public void update(float deltaTime) {
		if (deltaTime > 0.1f) {
			deltaTime = 0.1f;
		}
		
		switch (gameState) {
		case GAME_READY:
			updateReady();
			break;
			
		case GAME_RUNNING:
			updateRunning(deltaTime);
			break;
		
		case GAME_PAUSED:
			// TODO
			break;
		
		case GAME_OVER:
			// TODO
			break;
		}
	}

	private void updateReady() {
		gameState = GAME_RUNNING;
		// maybe change this later to check if the screen was just touched to first ask the user if he is ready
	}
	
	private void updateRunning(float deltaTime) {
		
		// TODO: handle pause pressing
		
		if (Gdx.input.isKeyPressed(Keys.BACK) || Gdx.input.isKeyPressed(Keys.ESCAPE)) {
			backPressed = true;
		}
		else if (backPressed){
			game.setScreen(new MainScreen(game));
			// change this later to pause the game instead of returning to main screen, then we won't use backPressed variable anymore
		}
		
		// TODO: handle touch commands/keys and other stuff
	}
	
	public void draw() {
		GL20 gl = Gdx.gl;
		gl.glClearColor(1, 0, 0, 1);
		gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		camera.update();
		game.batcher.setProjectionMatrix(camera.combined);

		game.batcher.disableBlending();
		game.batcher.begin();
		game.batcher.draw(Assets.backgroundRegionGameScreen, 0, 0, Assets.SCREEN_WIDTH, Assets.SCREEN_HEIGHT);
		game.batcher.end();

		game.batcher.enableBlending();
		game.batcher.begin();
		game.batcher.end();
	}

	@Override
	public void render(float delta) {
		update(delta);
		draw();
	}

	@Override
	public void pause() {
		// TODO
	}
}
