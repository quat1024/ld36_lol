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
import com.badlogic.gdx.utils.Array;

public class PlayerCharacter {
	
	private Rectangle rect;
	
	//My position.
	private Vector2 pos;
	
	//My smoothed-out position.
	public Vector2 renderedPos;
	
	//The map as a whole.
	private TiledMap tiledMap;
	
	//Convenience array so I don't have to loop through and enumerate them every time.
	//Does NOT contain layers with the "Decorative" property.
	private Array<TiledMapTileLayer> layers = new Array<TiledMapTileLayer>();
	
	//Key repeat stuff (for holding down arrow keys, duh!)
	private float keyRepeatCooldown;
	//Lower number = faster key repeat.
	private final float KEY_REPEAT_COOLDOWN_AMOUNT = 0.125f;
	
	public PlayerCharacter(int x, int y, TiledMap tiledMap_) {
		pos = new Vector2(x,y);
		renderedPos = new Vector2(x,y); //For easing.
		
		rect = new Rectangle(x,y,1,1);
		
		tiledMap = tiledMap_;
		
		for(int i=0; i < tiledMap.getLayers().getCount(); i++) {
			TiledMapTileLayer theLayer = (TiledMapTileLayer) tiledMap.getLayers().get(i);
			
			if(!theLayer.getProperties().get("Decorative", Boolean.class)) {
				layers.add((TiledMapTileLayer) tiledMap.getLayers().get(i));
			}
		}
	}
	
	public void render(ShapeRenderer shapes) {
		//todo: make this a sprite
		shapes.setColor(1f,1f,1f,1f);
		shapes.rect(rect.x+0.1f,rect.y+0.1f,rect.width-0.2f,rect.height-0.2f);
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
		
		Vector2 posDiff = new Vector2(0,0);
		
		//Left
		if(Gdx.input.isKeyJustPressed(Input.Keys.A) || (allowHeldKeys && Gdx.input.isKeyPressed(Input.Keys.A))) {
			posDiff.x = -1;
			keyRepeatCooldown = KEY_REPEAT_COOLDOWN_AMOUNT;
		}
		
		//Right
		if(Gdx.input.isKeyJustPressed(Input.Keys.D) || (allowHeldKeys && Gdx.input.isKeyPressed(Input.Keys.D))) {
			posDiff.x = 1;
			keyRepeatCooldown = KEY_REPEAT_COOLDOWN_AMOUNT;
		}
		
		//Down
		if(Gdx.input.isKeyJustPressed(Input.Keys.S) || (allowHeldKeys && Gdx.input.isKeyPressed(Input.Keys.S))) {
			posDiff.y = -1;
			keyRepeatCooldown = KEY_REPEAT_COOLDOWN_AMOUNT;
		}
		
		//Up
		if(Gdx.input.isKeyJustPressed(Input.Keys.W) || (allowHeldKeys && Gdx.input.isKeyPressed(Input.Keys.W))) {
			posDiff.y = 1;
			keyRepeatCooldown = KEY_REPEAT_COOLDOWN_AMOUNT;
		}
		
		//Figure out the actual position you want to go to.
		//Make sure to copy pos so you don't accidentally change it.
		Vector2 wantedPos = new Vector2(pos.cpy().add(posDiff));
		
		//Now we're going to check if we can move into this space.
		boolean movementBlocked = false;
		
		for(int layerID = 0; layerID < layers.size; layerID++) {
			TiledMapTileLayer layer = layers.get(layerID);
			
			//Find out what block we're trying to move in to.
			TiledMapTileLayer.Cell meme = layer.getCell((int) wantedPos.x, (int) wantedPos.y);
			if(meme == null) {
				continue;
			}
			
			//Now we're going to look through the properties and see if anything fancy has to happen.
			MapProperties tileProp = meme.getTile().getProperties();
			
			if(tileProp.get("Water",Boolean.class)) { //TODO: and also not having the walking on water trait
				movementBlocked = true;
				break;
			}
			
			if(tileProp.get("AlwaysSolid", Boolean.class)) {
				movementBlocked = true;
				break;
			}
		}
		
		if(movementBlocked) {
			renderedPos.add(posDiff.scl(0.2f));
		} else {
			pos = wantedPos;
		}
	}
}
