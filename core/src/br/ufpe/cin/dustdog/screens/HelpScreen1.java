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

public class HelpScreen1 extends ScreenAdapter {
	
	Dustdog game;
	OrthographicCamera camera;

	Rectangle nextArrowBounds;

	Vector3 touchPoint;
	
	boolean nextArrowActive;
	boolean backPressed;
	
	public HelpScreen1(Dustdog game) {
		this.game = game;

		camera = new OrthographicCamera(Assets.SCREEN_WIDTH, Assets.SCREEN_HEIGHT);
		camera.position.set(Assets.SCREEN_WIDTH/2, Assets.SCREEN_HEIGHT/2, 0);

		nextArrowBounds = new Rectangle(577, 40, 85, 69);

		touchPoint = new Vector3();

		nextArrowActive = false;
		backPressed = false;
	}
	
	public void update() {
		if (Gdx.input.isTouched()) {
			camera.unproject(touchPoint.set(Gdx.input.getX(), Gdx.input.getY(), 0));

			if (nextArrowBounds.contains(touchPoint.x, touchPoint.y)) {
				nextArrowActive = true;
				return;
			}
			else {
				nextArrowActive = false;
			}
		}

		if (Gdx.input.isKeyPressed(Keys.BACK) || Gdx.input.isKeyPressed(Keys.ESCAPE)) { 
			backPressed = true;
		}
		else if(backPressed) {
			game.setScreen(new MainScreen(game));
		}

		if (nextArrowActive) {
			nextArrowActive = false;
			Assets.playSound(Assets.clickSound);
			game.setScreen(new HelpScreen2(game));
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
		game.batcher.draw(Assets.backgroundRegionHelpScreen1, 0, 0);
		game.batcher.end();

		game.batcher.enableBlending();
		game.batcher.begin();

		game.batcher.draw((nextArrowActive ? Assets.helpScreenNextArrowActive : Assets.helpScreenNextArrow), 577, 40);

		game.batcher.end();
	}

	@Override
	public void render(float delta) {
		update();
		draw();
	}
}
