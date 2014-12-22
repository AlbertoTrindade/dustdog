package br.ufpe.cin.dustdog;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Assets {
	
	public final static int SCREEN_WIDTH = 682;
	public final static int SCREEN_HEIGHT = 1024;
	
	public static Texture backgroundMainScreen;
	public static TextureRegion backgroundRegionMainScreen;
	
	public static Texture backgroundSettingsScreen;
	public static TextureRegion backgroundRegionSettingsScreen;
	
	public static Texture backgroundGameScreen;
	public static TextureRegion backgroundRegionGameScreen;
	
	public static TextureAtlas screenItemsAtlas;
	public static Texture screenItems;
	
	public static TextureRegion mainScreenLogo;
	public static TextureRegion mainScreenBestScore;
	public static TextureRegion mainScreenTapPlay;
	public static TextureRegion mainScreenSettingsButton;
	
	public static TextureRegion settingsScreenSettingsBox;
	public static TextureRegion settingsScreenMarkedBox;
	public static TextureRegion settingsScreenUnmarkedBox;
	public static TextureRegion settingsScreenCancelButton;
	public static TextureRegion settingsScreenOkButton;
	
	public static Texture loadTexture(String fileName) {
		return new Texture(Gdx.files.internal(fileName));
	}
	
	public static TextureAtlas loadTextureAtlas(String fileName) {
		return new TextureAtlas(Gdx.files.internal(fileName));
	}
	
	public static void load() {
		backgroundMainScreen = loadTexture("backgroundMainScreen.png");
		backgroundRegionMainScreen = new TextureRegion(backgroundMainScreen, 0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
		
		backgroundSettingsScreen = loadTexture("backgroundSettingsScreen.png");
		backgroundRegionSettingsScreen = new TextureRegion(backgroundSettingsScreen, 0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
		
		backgroundGameScreen = loadTexture("backgroundGameScreen.png");
		backgroundRegionGameScreen = new TextureRegion(backgroundGameScreen, 0, 0, SCREEN_WIDTH, SCREEN_HEIGHT*2);
		
		screenItemsAtlas = loadTextureAtlas("screenItems.atlas");
		
		mainScreenLogo = screenItemsAtlas.findRegion("logo_dustdog");
		mainScreenBestScore = screenItemsAtlas.findRegion("box_best_score");
		mainScreenTapPlay = screenItemsAtlas.findRegion("btn_tap_to_play");
		mainScreenSettingsButton = screenItemsAtlas.findRegion("btn_config");
		
		settingsScreenSettingsBox = screenItemsAtlas.findRegion("box_config");
		settingsScreenMarkedBox = screenItemsAtlas.findRegion("chk_marked");
		settingsScreenUnmarkedBox = screenItemsAtlas.findRegion("chk_unmarked");
		settingsScreenCancelButton = screenItemsAtlas.findRegion("btn_cancel");
		settingsScreenOkButton = screenItemsAtlas.findRegion("btn_ok");
	}
}
