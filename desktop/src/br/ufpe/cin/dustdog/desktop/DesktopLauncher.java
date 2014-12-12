package br.ufpe.cin.dustdog.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import br.ufpe.cin.dustdog.Dustdog;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Dustdog";
		config.width = 480;
		config.height = 800;
		new LwjglApplication(new Dustdog(), config);
	}
}