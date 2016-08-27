package quat.ld36.player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileSet;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class PlayerCharacter {
	
	private Rectangle rect;
	
	private Vector2 pos;
	public Vector2 renderedPos;
	private Vector2 wantedPos;
	
	private TiledMap tiledMap;
	private TiledMapTileLayer layer;
	private TiledMapTileSet tileset;
	
	private float keyRepeatCooldown;
	private final float KEY_REPEAT_COOLDOWN_AMOUNT = 0.125f;
	
	public PlayerCharacter(int x, int y, TiledMap tiledMap_) {
		pos = new Vector2(x,y);
		renderedPos = new Vector2(x,y);
		
		rect = new Rectangle(x,y,1,1);
		
		tiledMap = tiledMap_;
		layer = (TiledMapTileLayer) tiledMap.getLayers().get("Main");
    tileset = tiledMap.getTileSets().getTileSet(0);
		
	}
	
	public void render(ShapeRenderer shapes) {
		shapes.setColor(1f,1f,1f,1f);
		shapes.rect(rect.x,rect.y,rect.width,rect.height);
	}
	
	public void update(float dt) {
		
		renderedPos.x += (pos.x - renderedPos.x) * 30 * dt;
		renderedPos.y += (pos.y - renderedPos.y) * 30 * dt;
		
		rect.x = renderedPos.x;
		rect.y = renderedPos.y;
	}
	
	public void handleInput(float dt) {
		
		boolean allowHeldKeys = true;
		
		if(keyRepeatCooldown > 0) {
			keyRepeatCooldown -= dt;
			allowHeldKeys = false;
		}
		
		wantedPos = new Vector2(pos);
		
		if(Gdx.input.isKeyJustPressed(Input.Keys.A) || (allowHeldKeys && Gdx.input.isKeyPressed(Input.Keys.A))) {
			wantedPos.x--;
			keyRepeatCooldown = KEY_REPEAT_COOLDOWN_AMOUNT;
		}
		
		if(Gdx.input.isKeyJustPressed(Input.Keys.D) || (allowHeldKeys && Gdx.input.isKeyPressed(Input.Keys.D))) {
			wantedPos.x++;
			keyRepeatCooldown = KEY_REPEAT_COOLDOWN_AMOUNT;
		}
		
		if(Gdx.input.isKeyJustPressed(Input.Keys.S) || (allowHeldKeys && Gdx.input.isKeyPressed(Input.Keys.S))) {
			wantedPos.y--;
			keyRepeatCooldown = KEY_REPEAT_COOLDOWN_AMOUNT;
		}
		
		if(Gdx.input.isKeyJustPressed(Input.Keys.W) || (allowHeldKeys && Gdx.input.isKeyPressed(Input.Keys.W))) {
			wantedPos.y++;
			keyRepeatCooldown = KEY_REPEAT_COOLDOWN_AMOUNT;
		}
		
		//Find out what block we're trying to move in to.
		TiledMapTileLayer.Cell meme = layer.getCell((int) wantedPos.x, (int) wantedPos.y);
		if(meme == null) return; //off map
		
		MapProperties tileProp = meme.getTile().getProperties();
		
		boolean movementBlocked = false;
		
		if(tileProp.get("Water",Boolean.class)) {
			movementBlocked = true;
		}
		
		if(tileProp.get("AlwaysSolid", Boolean.class)) {
			movementBlocked = true;
		}
		
		if(!movementBlocked) {
			pos = wantedPos;
		}
	}
}
