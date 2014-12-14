package br.ufpe.cin.dustdog;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector3;

public class MainScreen extends ScreenAdapter {
	
	Dustdog game;
	OrthographicCamera camera;
	Vector3 touchPoint;
	
	public MainScreen(Dustdog game) {
		this.game = game;
		
		camera = new OrthographicCamera(Assets.BACKGROUND_MAIN_SCREEN_WIDTH, Assets.BACKGROUND_MAIN_SCREEN_HEIGHT);
		camera.position.set(Assets.BACKGROUND_MAIN_SCREEN_WIDTH/2, Assets.BACKGROUND_MAIN_SCREEN_HEIGHT/2, 0);
		touchPoint = new Vector3();
	}
	
	public void draw() {
		GL20 gl = Gdx.gl;
		gl.glClearColor(1, 0, 0, 1);
		gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		camera.update();
		game.batcher.setProjectionMatrix(camera.combined);
		game.batcher.disableBlending();
		
		game.batcher.begin();
		game.batcher.draw(Assets.backgroundRegionMainScreen, 0, 0, Assets.BACKGROUND_MAIN_SCREEN_WIDTH, Assets.BACKGROUND_MAIN_SCREEN_HEIGHT);
		game.batcher.end();
	}
	
	@Override
	public void render(float delta) {
		//update();
		draw();
	}
	
	@Override
	public void pause() {
		// TODO
	}
}
