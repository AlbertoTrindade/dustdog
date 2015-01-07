package br.ufpe.cin.dustdog;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;

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
	public static TextureAtlas gameItemsAtlas;
	
	public static TextureRegion mainScreenLogo;
	public static TextureRegion mainScreenBestScore;
	public static TextureRegion mainScreenBestScoreActive;
	public static TextureRegion mainScreenTapPlay;
	public static TextureRegion mainScreenTapPlayActive;
	public static TextureRegion mainScreenSettingsButton;
	public static TextureRegion mainScreenSettingsButtonActive;
	
	public static TextureRegion settingsScreenSettingsBox;
	public static TextureRegion settingsScreenMarkedBox;
	public static TextureRegion settingsScreenUnmarkedBox;
	public static TextureRegion settingsScreenCancelButton;
	public static TextureRegion settingsScreenCancelButtonActive;
	public static TextureRegion settingsScreenOkButton;
	public static TextureRegion settingsScreenOkButtonActive;
	
	public static TextureRegion highscoresScreenHighscoresBox;
	public static TextureRegion highscoresScreenBackButton;
	public static TextureRegion highscoresScreenBackButtonActive;
	
	public static TextureRegion gameScreenReady;
	public static TextureRegion gameScreenPauseButton;
	public static TextureRegion gameScreenPauseButtonActive;
	public static TextureRegion gameScreenPausedBox;
	public static TextureRegion gameScreenResumeButton;
	public static TextureRegion gameScreenResumeButtonActive;
	public static TextureRegion gameScreenHomeButton;
	public static TextureRegion gameScreenHomeButtonActive;
	public static TextureRegion gameScreenSettingsButton;
	public static TextureRegion gameScreenSettingsButtonActive;
	
	public static TextureRegion spotForward;
	public static TextureRegion spotRight1;
	public static TextureRegion spotRight2;
	public static TextureRegion spotLeft1;
	public static TextureRegion spotLeft2;
	
	public static Animation spotGoingRightAnimation;
	public static Animation spotGoingLeftAnimation;
	
	public static FreeTypeFontGenerator fontGenerator;
	public static FreeTypeFontParameter fontParameter;
	
	public static BitmapFont font48;
	
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
		gameItemsAtlas = loadTextureAtlas("gameItems.atlas");
		
		mainScreenLogo = screenItemsAtlas.findRegion("logo_dustdog");
		mainScreenBestScore = screenItemsAtlas.findRegion("box_best_score");
		mainScreenBestScoreActive = screenItemsAtlas.findRegion("box_best_score_active");
		mainScreenTapPlay = screenItemsAtlas.findRegion("btn_tap_to_play");
		mainScreenTapPlayActive = screenItemsAtlas.findRegion("btn_tap_to_play_active");
		mainScreenSettingsButton = screenItemsAtlas.findRegion("btn_config");
		mainScreenSettingsButtonActive = screenItemsAtlas.findRegion("btn_config_active");
		
		settingsScreenSettingsBox = screenItemsAtlas.findRegion("box_config");
		settingsScreenMarkedBox = screenItemsAtlas.findRegion("chk_marked");
		settingsScreenUnmarkedBox = screenItemsAtlas.findRegion("chk_unmarked");
		settingsScreenCancelButton = screenItemsAtlas.findRegion("btn_cancel");
		settingsScreenCancelButtonActive = screenItemsAtlas.findRegion("btn_cancel_active");
		settingsScreenOkButton = screenItemsAtlas.findRegion("btn_ok");
		settingsScreenOkButtonActive = screenItemsAtlas.findRegion("btn_ok_active");
		
		highscoresScreenHighscoresBox = screenItemsAtlas.findRegion("box_highscores");
		highscoresScreenBackButton = screenItemsAtlas.findRegion("btn_back");
		highscoresScreenBackButtonActive = screenItemsAtlas.findRegion("btn_back_active");
		
		gameScreenReady = screenItemsAtlas.findRegion("txt_ready");
		gameScreenPauseButton = screenItemsAtlas.findRegion("btn_pause");
		gameScreenPauseButtonActive = screenItemsAtlas.findRegion("btn_pause_active");
		gameScreenPausedBox = screenItemsAtlas.findRegion("box_paused");
		gameScreenResumeButton = screenItemsAtlas.findRegion("btn_resume");
		gameScreenResumeButtonActive = screenItemsAtlas.findRegion("btn_resume_active");
		gameScreenHomeButton = screenItemsAtlas.findRegion("btn_home");
		gameScreenHomeButtonActive = screenItemsAtlas.findRegion("btn_home_active");
		gameScreenSettingsButton = screenItemsAtlas.findRegion("btn_settings");
		gameScreenSettingsButtonActive = screenItemsAtlas.findRegion("btn_settings_active");
		
		spotForward = gameItemsAtlas.findRegion("car_forward");
		spotRight1 = gameItemsAtlas.findRegion("car_right_1");
		spotRight2 = gameItemsAtlas.findRegion("car_right_2");
		
		spotLeft1 = new TextureRegion(spotRight1);
		spotLeft1.flip(true, false);
		
		spotLeft2 = new TextureRegion(spotRight2);
		spotLeft2.flip(true, false);
		
		spotGoingRightAnimation = new Animation(0.1f, spotForward, spotRight1, spotRight2, spotRight1, spotForward);
		spotGoingLeftAnimation = new Animation(0.1f, spotForward, spotLeft1, spotLeft2, spotLeft1, spotForward);
		
		fontGenerator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/berlin-sans-fb.ttf"));
		fontParameter = new FreeTypeFontParameter();
		fontParameter.size = 48;
		font48 = fontGenerator.generateFont(fontParameter);
		font48.setColor(Color.BLACK);
		fontGenerator.dispose();
	}
}
