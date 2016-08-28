package quat.ld36;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import quat.ld36.screen.MainMenuScreen;
import quat.ld36.screen.Screens;

public class LudumDare36VideoGame extends Game {
	
	public static final int WIDTH = 960;
	public static final int HEIGHT = 540;
	
	public static SpriteBatch batch;
	public static ShapeRenderer shapes;
	
	public static BitmapFont font;
	
	public static Pixmap cursorImage;
	
	public static TiledMap tiledMap;
	public static OrthogonalTiledMapRenderer mapRenderer;
	
	public static Pixmap backgroundImage;
	
	public static final int FPS = 60;
	public static final boolean DEBUG = false;
	
	@Override
	public void create() {
		//Make our batch renderers
		//Daily reminder not to nest these...
		batch = new SpriteBatch();
		shapes = new ShapeRenderer();
		
		//Font!
		font = new BitmapFont(Gdx.files.internal("CordiaUPC.fnt"));
		
		//Cursor
		cursorImage = new Pixmap(Gdx.files.internal("cursor.png"));
		Gdx.graphics.setCursor(Gdx.graphics.newCursor(cursorImage,8,8));
		
		//Load the map.
		//We do this here, because it's better to have the massive
		//lag spike right now, instead of right when you click play.
		tiledMap = new TmxMapLoader().load("TestMap.tmx");
		mapRenderer = new OrthogonalTiledMapRenderer(tiledMap,1/16f);
		
		//Load screens
		new Screens(this);
		
		//Default to the main menu screen.
		this.setScreen(Screens.MAIN_MENU_SCREEN);
	}

	@Override
	public void render() {
		//The Game class will render the current screen.
		//But only if I do this:
		super.render();
	}
	
	@Override
	public void dispose() {
		//Remember to unload stuff here ya dummy!
		batch.dispose();
		font.dispose();
		cursorImage.dispose();
		tiledMap.dispose();
		mapRenderer.dispose();
	}
}
