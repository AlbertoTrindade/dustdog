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

public class HelpScreen5 extends ScreenAdapter {
	
	Dustdog game;
	OrthographicCamera camera;

	Rectangle backArrowBounds;
	Rectangle closeButtonBounds;

	Vector3 touchPoint;
	
	boolean backArrowActive;
	boolean closeButtonActive;
	boolean backPressed;
	
	public HelpScreen5(Dustdog game) {
		this.game = game;

		camera = new OrthographicCamera(Assets.SCREEN_WIDTH, Assets.SCREEN_HEIGHT);
		camera.position.set(Assets.SCREEN_WIDTH/2, Assets.SCREEN_HEIGHT/2, 0);

		backArrowBounds = new Rectangle(17, 40, 85, 69);
		closeButtonBounds = new Rectangle(585, 40, 69, 72);

		touchPoint = new Vector3();

		closeButtonActive = false;
		backPressed = false;
	}
	
	public void update() {
		if (Gdx.input.isTouched()) {
			camera.unproject(touchPoint.set(Gdx.input.getX(), Gdx.input.getY(), 0));

			if (backArrowBounds.contains(touchPoint.x, touchPoint.y)) {
				backArrowActive = true;
				return;
			}
			else if (closeButtonBounds.contains(touchPoint.x, touchPoint.y)) {
				closeButtonActive = true;
				return;
			}
			else {
				backArrowActive = false;
				closeButtonActive = false;
			}
		}

		if (Gdx.input.isKeyPressed(Keys.BACK) || Gdx.input.isKeyPressed(Keys.ESCAPE)) { 
			backPressed = true;
		}
		else if(backPressed) {
			game.setScreen(new HelpScreen4(game));
		}

		if (backArrowActive) {
			backArrowActive = false;
			Assets.playSound(Assets.clickSound);
			game.setScreen(new HelpScreen4(game));
		}
		
		if (closeButtonActive) {
			closeButtonActive = false;
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
		game.batcher.draw(Assets.backgroundRegionHelpScreen5, 0, 0);
		game.batcher.end();

		game.batcher.enableBlending();
		game.batcher.begin();

		game.batcher.draw((backArrowActive ? Assets.helpScreenBackArrowActive : Assets.helpScreenBackArrow), 17, 40);
		game.batcher.draw((closeButtonActive ? Assets.helpScreenCloseButtonActive : Assets.helpScreenCloseButton), 585, 40);

		game.batcher.end();
	}

	@Override
	public void render(float delta) {
		update();
		draw();
	}
}
