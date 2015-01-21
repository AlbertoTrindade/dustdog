package br.ufpe.cin.dustdog.screens;

import br.ufpe.cin.dustdog.Assets;
import br.ufpe.cin.dustdog.Dustdog;
import br.ufpe.cin.dustdog.GameState;
import br.ufpe.cin.dustdog.objects.spot.DirectionGestureDetector;
import br.ufpe.cin.dustdog.objects.spot.SwipeDirection;
import br.ufpe.cin.dustdog.world.World;
import br.ufpe.cin.dustdog.world.WorldRenderer;
import br.ufpe.cin.dustdog.world.WorldState;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

public class GameScreen extends ScreenAdapter {

	GameState gameState;

	Dustdog game;
	OrthographicCamera camera;

	World world;
	WorldRenderer worldRenderer;

	Rectangle pauseButtonBounds;
	Rectangle resumeButtonBounds;
	Rectangle homeButtonBounds;
	Rectangle settingsButtonBounds;
	Rectangle gameOverScoreBounds;

	boolean pauseButtonActive;
	boolean resumeButtonActive;
	boolean homeButtonActive;
	boolean settingsButtonActive;
	boolean gameOverScoreActive;

	Vector3 touchPoint;

	boolean backPressedRunning;
	boolean backPressedPaused;
	boolean upPressed;
	boolean downPressed;
	boolean leftPressed;
	boolean rightPressed;
	boolean screenReleased;
	boolean swipeProcessed;

	int currentGameOverScore;
	boolean totalGameOverScoreShown;

	ApplicationType applicationType;

	public GameScreen(Dustdog game) {
		this.game = game;

		gameState = GameState.READY;

		camera = new OrthographicCamera(Assets.SCREEN_WIDTH, Assets.SCREEN_HEIGHT);
		camera.position.set(Assets.SCREEN_WIDTH/2, Assets.SCREEN_HEIGHT/2, 0);

		world = new World();
		worldRenderer = new WorldRenderer(game.batcher, world);

		pauseButtonBounds = new Rectangle(5, 952, 63, 64);
		resumeButtonBounds = new Rectangle(202, 585, 283, 73);
		homeButtonBounds = new Rectangle(202, 490, 277, 71);
		settingsButtonBounds = new Rectangle(202, 390, 277, 71);
		gameOverScoreBounds = new Rectangle(93.5f, 290, 495, 163);

		pauseButtonActive = false;
		resumeButtonActive = false;
		homeButtonActive = false;
		settingsButtonActive = false;
		gameOverScoreActive = false;

		touchPoint = new Vector3();

		backPressedRunning = false;
		backPressedPaused = false;
		upPressed = false;
		downPressed = false;
		leftPressed = false;
		rightPressed = false;
		screenReleased = false;
		swipeProcessed = false;

		currentGameOverScore = 0;
		totalGameOverScoreShown = false;

		applicationType = Gdx.app.getType();

		if (applicationType == ApplicationType.Android || applicationType == ApplicationType.iOS) {
			Gdx.input.setInputProcessor(new DirectionGestureDetector(new DirectionGestureDetector.DirectionListener() {

				@Override
				public void onUp() {
					upPressed = true;
					screenReleased = false;
				}

				@Override
				public void onRight() {
					rightPressed = true;
					screenReleased = false;
				}

				@Override
				public void onLeft() {
					leftPressed = true;
					screenReleased = false;
				}

				@Override
				public void onDown() {
					downPressed = true;
					screenReleased = false;
				}

				@Override
				public void onRelease() {
					screenReleased = true;
				}
			}));
		}
	}

	public GameScreen(Dustdog game, World world) {
		// Constructor called when coming from settings screen instead the home one
		this(game);
		this.world = world;
		this.worldRenderer = new WorldRenderer(game.batcher, world);

		gameState = GameState.PAUSED;
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
			updatePaused();
			break;

		case GAME_OVER:
			updateGameOver();
			break;
		}
	}

	private void updateReady() {
		if (Gdx.input.justTouched()) {
			Assets.playSound(Assets.barkSound);
			gameState = GameState.RUNNING;
		}

		if (Gdx.input.isKeyPressed(Keys.BACK) || Gdx.input.isKeyPressed(Keys.ESCAPE)) {
			backPressedRunning = true;
		}
		else if (backPressedRunning){
			backPressedRunning = false;

			game.setScreen(new MainScreen(game));
			return;
		}
	}

	private void updateRunning(float deltaTime) {
		if (Gdx.input.isTouched()) {
			camera.unproject(touchPoint.set(Gdx.input.getX(), Gdx.input.getY(), 0));

			if (pauseButtonBounds.contains(touchPoint.x, touchPoint.y)) {
				pauseButtonActive = true;
				return;
			}
		}

		if (pauseButtonActive) {
			pauseButtonActive = false;
			Assets.playSound(Assets.clickSound);
			gameState = GameState.PAUSED;
			return;
		}

		if (Gdx.input.isKeyPressed(Keys.BACK) || Gdx.input.isKeyPressed(Keys.ESCAPE)) {
			backPressedRunning = true;
		}
		else if (backPressedRunning){
			backPressedRunning = false;
			Assets.playSound(Assets.clickSound);

			gameState = GameState.PAUSED;
			return;
		}

		SwipeDirection swipeDirection = SwipeDirection.NONE;

		if (applicationType == ApplicationType.Android || applicationType == ApplicationType.iOS) {

			if (!swipeProcessed) {
				if (leftPressed){
					swipeDirection = SwipeDirection.LEFT;
					leftPressed = false;
					swipeProcessed = true;
				}

				if (rightPressed){
					swipeDirection = SwipeDirection.RIGHT;
					rightPressed = false;
					swipeProcessed = true;
				}

				if (upPressed){
					swipeDirection = SwipeDirection.UP;
					upPressed = false;
					swipeProcessed = true;
				}

				if (downPressed){
					swipeDirection = SwipeDirection.DOWN;
					downPressed = false;
					swipeProcessed = true;
				}
			}
			else {
				if (screenReleased) {
					swipeProcessed = false;
				}
			}
		}
		else { // Desktop
			if (Gdx.input.isKeyJustPressed(Keys.DPAD_LEFT)) {
				swipeDirection = SwipeDirection.LEFT;
			}

			if (Gdx.input.isKeyJustPressed(Keys.DPAD_RIGHT)) {
				swipeDirection = SwipeDirection.RIGHT;
			}

			if (Gdx.input.isKeyJustPressed(Keys.DPAD_UP)) {
				swipeDirection = SwipeDirection.UP;
			}

			if (Gdx.input.isKeyJustPressed(Keys.DPAD_DOWN)) {
				swipeDirection = SwipeDirection.DOWN;
			}
		}

		world.update(deltaTime, swipeDirection);

		if (world.state == WorldState.GAME_OVER) {
			gameState = GameState.GAME_OVER;
		}
	}

	private void updatePaused() {
		if (Gdx.input.isTouched()) {
			camera.unproject(touchPoint.set(Gdx.input.getX(), Gdx.input.getY(), 0));

			if (resumeButtonBounds.contains(touchPoint.x, touchPoint.y)) {
				resumeButtonActive = true;
				return;
			}

			if (homeButtonBounds.contains(touchPoint.x, touchPoint.y)) {
				homeButtonActive = true;
				return;
			}

			if (settingsButtonBounds.contains(touchPoint.x, touchPoint.y)) {
				settingsButtonActive = true;
				return;
			}
		}

		if (Gdx.input.isKeyPressed(Keys.BACK) || Gdx.input.isKeyPressed(Keys.ESCAPE)) {
			backPressedPaused = true;
		}
		else if (backPressedPaused){
			backPressedPaused = false;

			gameState = GameState.RUNNING;
			return;
		}

		if (resumeButtonActive) {
			resumeButtonActive = false;
			Assets.playSound(Assets.clickSound);
			gameState = GameState.RUNNING;
			return;
		}

		if (homeButtonActive) {
			homeButtonActive = false;
			Assets.playSound(Assets.clickSound);
			game.setScreen(new MainScreen(game));
			return;
		}

		if (settingsButtonActive) {
			settingsButtonActive = false;
			Assets.playSound(Assets.clickSound);
			game.setScreen(new SettingsScreen(game, world));
			return;
		}
	}

	private void updateGameOver() {
		if (totalGameOverScoreShown) {			
			if (Gdx.input.isTouched()) {
				camera.unproject(touchPoint.set(Gdx.input.getX(), Gdx.input.getY(), 0));

				if (gameOverScoreBounds.contains(touchPoint.x, touchPoint.y)) {
					gameOverScoreActive = true;
					return;
				}
			}

			if (gameOverScoreActive) {
				gameOverScoreActive = false;
				Assets.playSound(Assets.clickSound);
				game.setScreen(new HighscoresScreen(game));
				return;
			}

			if (Gdx.input.justTouched()) {
				game.setScreen(new MainScreen(game));
				return;
			}

			if (Gdx.input.isKeyPressed(Keys.BACK) || Gdx.input.isKeyPressed(Keys.ESCAPE)) {
				backPressedRunning = true;
			}
			else if (backPressedRunning){
				backPressedRunning = false;

				game.setScreen(new MainScreen(game));
				return;
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

		switch (gameState) {
		case READY:
			presentReady();
			break;

		case RUNNING:
			presentRunning();
			break;

		case PAUSED:
			presentPaused();
			break;

		case GAME_OVER:
			presentGameOver();
			break;
		}

		game.batcher.end();
	}

	private void presentReady() {
		game.batcher.draw(Assets.gameScreenReady, 112, 442);
	}

	private void presentRunning() {
		game.batcher.draw((pauseButtonActive ? Assets.gameScreenPauseButtonActive : Assets.gameScreenPauseButton), 5, 952);
		game.batcher.draw(Assets.gameScreenScoreBox, 501, 945);

		String score = Integer.toString(world.score);
		float scoreX = (Assets.gameScreenScoreBox.getRegionWidth() - Assets.font48.getBounds(score).width) / 2;
		Assets.font48.draw(game.batcher, Integer.toString(world.score), 501 + scoreX, 1000);

		game.batcher.draw(Assets.gameScreenBonesBox, 547, 855);
		Assets.font48.draw(game.batcher, Integer.toString(world.spot.numberBones), 585, 910);
	}

	private void presentPaused() {
		game.batcher.draw(Assets.gameScreenPausedBox, 73, 350);
		game.batcher.draw((resumeButtonActive ? Assets.gameScreenResumeButtonActive : Assets.gameScreenResumeButton), 202, 585);
		game.batcher.draw((homeButtonActive ? Assets.gameScreenHomeButtonActive : Assets.gameScreenHomeButton), 202, 490);
		game.batcher.draw((settingsButtonActive ? Assets.gameScreenSettingsButtonActive : Assets.gameScreenSettingsButton), 202, 390);
	}

	private void presentGameOver() {
		game.batcher.draw(Assets.gameScreenGameOver, 28, 500);

		if (world.newHighscore) {
			game.batcher.draw((gameOverScoreActive ? Assets.gameScreenHighscoreBoxActive : Assets.gameScreenHighscoreBox), 93.5f, 290);
		}
		else {
			game.batcher.draw((gameOverScoreActive ? Assets.gameScreenGameOverScoreBoxActive : Assets.gameScreenGameOverScoreBox), 93.5f, 290);	
		}

		String currentScore = Integer.toString(currentGameOverScore);
		float scoreX = (Assets.gameScreenGameOverScoreBox.getRegionWidth() - Assets.font48.getBounds(currentScore).width) / 2;
		Assets.font48.draw(game.batcher, currentScore, 93.5f + scoreX, 350);

		int incrementGameOverScore;

		if (world.score - currentGameOverScore < 50) {
			incrementGameOverScore = 1;
		}
		else if (world.score - currentGameOverScore < 500) { 
			incrementGameOverScore = 10;
		}
		else if (world.score - currentGameOverScore < 5000) { 
			incrementGameOverScore = 200;
		}
		else {
			incrementGameOverScore = 1000;
		}

		if (currentGameOverScore == world.score) {
			totalGameOverScoreShown = true;
		}
		else {
			currentGameOverScore += incrementGameOverScore;
		}
	}

	@Override
	public void render(float delta) {
		update(delta);
		draw();
	}

	@Override
	public void pause() {
		if (gameState == GameState.RUNNING) {
			gameState = GameState.PAUSED;
		}
	}
}
