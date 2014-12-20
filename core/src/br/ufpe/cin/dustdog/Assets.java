package br.ufpe.cin.dustdog;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
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
	
	public static void load() {
		backgroundMainScreen = loadTexture("backgroundMainScreen.png");
		backgroundRegionMainScreen = new TextureRegion(backgroundMainScreen, 0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
		
		backgroundSettingsScreen = loadTexture("backgroundSettingsScreen.png");
		backgroundRegionSettingsScreen = new TextureRegion(backgroundSettingsScreen, 0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
		
		backgroundGameScreen = loadTexture("backgroundGameScreen.png");
		backgroundRegionGameScreen = new TextureRegion(backgroundGameScreen, 0, 0, SCREEN_WIDTH, SCREEN_HEIGHT*2);
		
		screenItems = loadTexture("screenItems.png");
		
		mainScreenLogo = new TextureRegion(screenItems, 539, 312, 351, 160);
		mainScreenBestScore = new TextureRegion(screenItems, 539, 170, 255, 140);
		mainScreenTapPlay = new TextureRegion(screenItems, 1, 8, 311, 59);
		mainScreenSettingsButton = new TextureRegion(screenItems, 892, 380, 95, 98);
		
		settingsScreenSettingsBox = new TextureRegion(screenItems, 1, 69, 536, 409);
		settingsScreenMarkedBox = new TextureRegion(screenItems, 796, 172, 68, 64);
		settingsScreenUnmarkedBox = new TextureRegion(screenItems, 892, 314, 68, 64);
		settingsScreenCancelButton = new TextureRegion(screenItems, 314, 1, 169, 66);
		settingsScreenOkButton = new TextureRegion(screenItems, 539, 28, 169, 66);
	}
}
