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

public class LudumDare36VideoGame extends Game {
	
	public static final int WIDTH = 960;
	public static final int HEIGHT = 540;
	
	public static SpriteBatch batch;
	public static ShapeRenderer shapes;
	
	public static BitmapFont font;
	
	public static Pixmap cursorImage;
	
	//Let's put these here to preload them (?)
	public static TiledMap testMap;
	public static OrthogonalTiledMapRenderer renderer;
	
	public static Pixmap backgroundImage;
	
	@Override
	public void create() {
		batch = new SpriteBatch();
		shapes = new ShapeRenderer();
		
		font = new BitmapFont(Gdx.files.internal("CordiaUPC.fnt"));
		
		this.setScreen(new MainMenuScreen(this));
		
		cursorImage = new Pixmap(Gdx.files.internal("cursor.png"));
		Gdx.graphics.setCursor(Gdx.graphics.newCursor(cursorImage,8,8));
		
		testMap = new TmxMapLoader().load("TestMap.tmx");
		renderer = new OrthogonalTiledMapRenderer(testMap,1/16f);
	}

	@Override
	public void render() {
		super.render();
	}
	
	@Override
	public void dispose() {
		batch.dispose();
		font.dispose();
		cursorImage.dispose();
		
		testMap.dispose();
	}
}
