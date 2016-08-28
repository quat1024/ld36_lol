package quat.ld36;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.utils.Array;
import quat.ld36.screen.MainMenuScreen;
import quat.ld36.screen.Screens;
import quat.ld36.util.ItemID;

public class LudumDare36VideoGame extends Game {
	
	public static final int WIDTH = 960;
	public static final int HEIGHT = 540;
	
	public static SpriteBatch batch;
	public static ShapeRenderer shapes;
	
	public static BitmapFont font;
	
	public static Pixmap cursorImage;
	
	public static TiledMap tiledMap;
	public static OrthogonalTiledMapRenderer mapRenderer;
	
	public static final int FPS = 60;
	public static final boolean DEBUG = false;
	
	public static Array<Pixmap> itemSprites;
	public static Array<Texture> itemTex;
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
		
		//Load image sprites (this will be fun lol)
		itemSprites = new Array<Pixmap>();
		itemTex = new Array<Texture>();
		for(int i=0; i < ItemID.COUNT; i++) {
			ItemID j = ItemID.fromID(i);
			itemSprites.add(new Pixmap(j.path()));
			itemTex.add(new Texture(itemSprites.get(i)));
		}
		
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
		
		for(int i=0; i < itemSprites.size; i++) {
			itemSprites.get(i).dispose();
			itemTex.get(i).dispose();
		}
	}
}
