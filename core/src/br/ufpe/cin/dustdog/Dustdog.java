package br.ufpe.cin.dustdog;

import br.ufpe.cin.dustdog.screens.MainScreen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Dustdog extends Game {
	
	public SpriteBatch batcher;
	
	@Override
	public void create() {
		batcher = new SpriteBatch();
		
		Gdx.input.setCatchBackKey(true);
		Settings.load();
		Assets.load();
		
		setScreen(new MainScreen(this));
	}

	@Override
	public void render() {
		super.render();
	}
}