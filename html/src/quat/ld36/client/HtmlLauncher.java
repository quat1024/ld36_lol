package quat.ld36.client;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.gwt.GwtApplication;
import com.badlogic.gdx.backends.gwt.GwtApplicationConfiguration;
import quat.ld36.LudumDare36VideoGame;

public class HtmlLauncher extends GwtApplication {

        @Override
        public GwtApplicationConfiguration getConfig () {
                GwtApplicationConfiguration config = new GwtApplicationConfiguration(LudumDare36VideoGame.WIDTH, LudumDare36VideoGame.HEIGHT);
	        
	        config.antialiasing = false;
	        
	        return config;
        }

        @Override
        public ApplicationListener createApplicationListener () {
                return new LudumDare36VideoGame();
        }
}