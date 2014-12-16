package br.ufpe.cin.dustdog;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

public class Settings {
	
	public static boolean soundEnabled = true;
	public static boolean musicEnabled = true;
	
	public static String preferencesFileName = "MyPreferences";
	
	public static void save() {
		Preferences preferences = Gdx.app.getPreferences(preferencesFileName);
		
		preferences.putBoolean("soundEnabled", soundEnabled);
		preferences.putBoolean("musicEnabled", musicEnabled);
		
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
	}
}
