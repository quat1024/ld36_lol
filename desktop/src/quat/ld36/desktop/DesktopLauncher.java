package quat.ld36.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import quat.ld36.LudumDare36VideoGame;

public class DesktopLauncher {
	
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		
		config.title="Meme";
		config.width=LudumDare36VideoGame.WIDTH;
		config.height=LudumDare36VideoGame.HEIGHT;
		config.samples=0; //Just double check. It looks like shit with antialiasing. (Maybe fix that)
		config.foregroundFPS = LudumDare36VideoGame.FPS;
		
		new LwjglApplication(new LudumDare36VideoGame(), config);
	}
}
