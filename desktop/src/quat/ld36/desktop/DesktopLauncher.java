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
		
		new LwjglApplication(new LudumDare36VideoGame(), config);
	}
}
