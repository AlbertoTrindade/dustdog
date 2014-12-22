package br.ufpe.cin.dustdog.screens;

import br.ufpe.cin.dustdog.Assets;
import br.ufpe.cin.dustdog.DirectionGestureDetector;
import br.ufpe.cin.dustdog.Dustdog;
import br.ufpe.cin.dustdog.GameState;
import br.ufpe.cin.dustdog.world.World;
import br.ufpe.cin.dustdog.world.World.WorldListener;
import br.ufpe.cin.dustdog.world.WorldRenderer;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector3;

public class GameScreen extends ScreenAdapter {

	GameState gameState;

	Dustdog game;
	OrthographicCamera camera;

	World world;
	WorldRenderer worldRenderer;
	WorldListener worldListener;

	Vector3 touchPoint;
	boolean backPressed, upPressed, downPressed, leftPressed, rightPressed;

	ApplicationType applicationType;

	public GameScreen(Dustdog game) {
		this.game = game;

		gameState = GameState.READY;

		camera = new OrthographicCamera(Assets.SCREEN_WIDTH, Assets.SCREEN_HEIGHT);
		camera.position.set(Assets.SCREEN_WIDTH/2, Assets.SCREEN_HEIGHT/2, 0);

		// TODO: create a WorldListener implementing the actions which generate sound (see SuperJumper code as reference)
		worldListener = new WorldListener() {
		};

		world = new World(worldListener);
		worldRenderer = new WorldRenderer(game.batcher, world);

		touchPoint = new Vector3();
		backPressed = false;

		applicationType = Gdx.app.getType();

		if (applicationType == ApplicationType.Android || applicationType == ApplicationType.iOS) {
			Gdx.input.setInputProcessor(new DirectionGestureDetector(new DirectionGestureDetector.DirectionListener() {

				@Override
				public void onUp() {
					upPressed = true;
				}

				@Override
				public void onRight() {
					rightPressed = true;
				}

				@Override
				public void onLeft() {
					leftPressed = true;
				}

				@Override
				public void onDown() {
					downPressed = true;
				}
			}));
		}
	}

	public void update(float deltaTime) {
		if (deltaTime > 0.1f) {
			deltaTime = 0.1f;
		}

		switch (gameState) {
		case READY:
			updateReady();
			break;

		case RUNNING:
			updateRunning(deltaTime);
			break;

		case PAUSED:
			// TODO
			break;

		case GAME_OVER:
			// TODO
			break;
		}
	}

	private void updateReady() {
		gameState = GameState.RUNNING;
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

		if (applicationType == ApplicationType.Android || applicationType == ApplicationType.iOS) {
			if (leftPressed){
				// TODO: call world method to move left
				leftPressed = false;
			}

			if (rightPressed){
				// TODO: call world method to move right
				rightPressed = false;
			}

			if (upPressed){
				// TODO: call world method to handle jump
				upPressed = false;
			}

			if (downPressed){
				// TODO: call world method to handle crouch
				downPressed = false;
			}
		}
		else { // Desktop
			if (Gdx.input.isKeyJustPressed(Keys.DPAD_LEFT)) {
				// TODO: call world method to move left
			}

			if (Gdx.input.isKeyJustPressed(Keys.DPAD_RIGHT)) {
				// TODO: call world method to move right
			}

			if (Gdx.input.isKeyJustPressed(Keys.DPAD_UP)) {
				// TODO: call world method to jump
			}

			if (Gdx.input.isKeyJustPressed(Keys.DPAD_DOWN)) {
				// TODO: call world method to crouch
			}
		}
	}

	public void draw() {
		GL20 gl = Gdx.gl;
		gl.glClearColor(1, 0, 0, 1);
		gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		worldRenderer.render();

		camera.update();
		game.batcher.setProjectionMatrix(camera.combined);
		game.batcher.enableBlending();

		game.batcher.begin();

		// TODO: switch-case to draw screen elements according to the game state (paused -> pause menu; running -> pause button, score; etc)

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
