package br.ufpe.cin.dustdog;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Assets {
	
	public static Texture backgroundMainScreen;
	public static TextureRegion backgroundRegionMainScreen;
	
	public final static int BACKGROUND_MAIN_SCREEN_WIDTH = 682;
	public final static int BACKGROUND_MAIN_SCREEN_HEIGHT = 1024;
	
	public static Texture loadTexture(String fileName) {
		return new Texture(Gdx.files.internal(fileName));
	}
	
	public static void load() {
		backgroundMainScreen = loadTexture("backgroundMainScreen.png");
		backgroundRegionMainScreen = new TextureRegion(backgroundMainScreen, 0, 0, BACKGROUND_MAIN_SCREEN_WIDTH, BACKGROUND_MAIN_SCREEN_HEIGHT);
	}
}
