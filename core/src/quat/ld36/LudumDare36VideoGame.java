package quat.ld36;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import quat.ld36.screen.MainMenuScreen;

public class LudumDare36VideoGame extends Game {
	
	public static final int WIDTH = 960;
	public static final int HEIGHT = 540;
	
	public SpriteBatch batch;
	public BitmapFont font;
	
	public Pixmap cursorImage;
	
	@Override
	public void create() {
		batch = new SpriteBatch();
		font = new BitmapFont(Gdx.files.internal("CordiaUPC.fnt"));
		
		this.setScreen(new MainMenuScreen(this));
		
		cursorImage = new Pixmap(Gdx.files.internal("cursor.png"));
		Gdx.graphics.setCursor(Gdx.graphics.newCursor(cursorImage,8,8));
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
	}
}
