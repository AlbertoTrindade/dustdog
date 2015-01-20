package br.ufpe.cin.dustdog;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

public class Settings {
	
	public static boolean soundEnabled = true;
	public static boolean musicEnabled = true;
	
	public static int[] highscores = new int[] {0, 0, 0, 0, 0};
	
	public static String preferencesFileName = "MyPreferences";
	
	public static void save() {
		Preferences preferences = Gdx.app.getPreferences(preferencesFileName);
		
		preferences.putBoolean("soundEnabled", soundEnabled);
		preferences.putBoolean("musicEnabled", musicEnabled);
		
		preferences.putInteger("firstScore", highscores[0]);
		preferences.putInteger("secondScore", highscores[1]);
		preferences.putInteger("thirdScore", highscores[2]);
		preferences.putInteger("fourthScore", highscores[3]);
		preferences.putInteger("fifthScore", highscores[4]);
		
		preferences.flush();
	}
	
	public static void load() {
		Preferences preferences = Gdx.app.getPreferences(preferencesFileName);
		
		if (preferences.contains("soundEnabled")) {
			soundEnabled = preferences.getBoolean("soundEnabled");
		}
		
		if (preferences.contains("musicEnabled")) {
			musicEnabled = preferences.getBoolean("musicEnabled");
		}
		
		if (preferences.contains("firstScore")) {
			highscores[0] = preferences.getInteger("firstScore");
		}
		
		if (preferences.contains("secondScore")) {
			highscores[1] = preferences.getInteger("secondScore");
		}
		
		if (preferences.contains("thirdScore")) {
			highscores[2] = preferences.getInteger("thirdScore");
		}
		
		if (preferences.contains("fourthScore")) {
			highscores[3] = preferences.getInteger("fourthScore");
		}
		
		if (preferences.contains("fifthScore")) {
			highscores[4] = preferences.getInteger("fifthScore");
		}
	}
}
