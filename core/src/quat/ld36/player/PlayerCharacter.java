package quat.ld36.player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileSet;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;

import static com.badlogic.gdx.math.MathUtils.clamp;
import static com.badlogic.gdx.math.MathUtils.floor;
import static java.lang.Math.min;

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
	
	//This is used often enough that it warrants its own variable.
	private TiledMapTileLayer objects;
	
	//Key repeat stuff (for holding down arrow keys, duh!)
	private float keyRepeatCooldown;
	//Lower number = faster key repeat.
	private final float KEY_REPEAT_COOLDOWN_AMOUNT = 0.125f;
	
	public int mouseTileX, mouseTileY;
	public float mineProgress;
	public boolean miningAvailable;
	public TiledMapTile pointedCell;
	public MapProperties pointedCellProps;
	
	public PlayerCharacter(int x, int y, TiledMap tiledMap_) {
		pos = new Vector2(x,y);
		renderedPos = new Vector2(x,y); //For easing!
		
		rect = new Rectangle(x,y,1,1);
		
		tiledMap = tiledMap_;
		
		objects = (TiledMapTileLayer)tiledMap.getLayers().get("Objects");
		
		for(int i=0; i < tiledMap.getLayers().getCount(); i++) {
			TiledMapTileLayer theLayer = (TiledMapTileLayer) tiledMap.getLayers().get(i);
			
			if(!theLayer.getProperties().get("Decorative", Boolean.class)) {
				layers.add((TiledMapTileLayer) tiledMap.getLayers().get(i));
			}
		}
	}
	
	
	/** Draw the character to the screen. */
	public void render(ShapeRenderer shapes) {
		//todo: make this a sprite
		shapes.setColor(1f,1f,1f,1f);
		shapes.rect(rect.x+0.1f,rect.y+0.1f,rect.width-0.2f,rect.height-0.2f);
	}
	
	/** Update position and other every-frame things. */
	public void update(float dt, OrthographicCamera cam) {
		//Update block the cursor is pointing at
		Vector3 mouseVec3 = cam.unproject(new Vector3(Gdx.input.getX(),Gdx.input.getY(),0f));
		mouseTileX = floor(mouseVec3.x);
		mouseTileY = floor(mouseVec3.y);
		
		miningAvailable = false; //will be updated later :)
		TiledMapTileLayer.Cell cell = objects.getCell(mouseTileX,mouseTileY);
		if(cell == null) {
			pointedCell = null;
			pointedCellProps = null;
		} else {
			pointedCell = cell.getTile();
			pointedCellProps = pointedCell.getProperties();
			
			if(pointedCellProps.containsKey("HarvestLevel") && pointedCellProps.get("HarvestLevel",Integer.class) < 1) {
				miningAvailable = true;
			} else {
				miningAvailable = false;
			}
		}
		
		//Stop mining if you walk too far away.
		if(!withinRange(mouseTileX,mouseTileY)) {
			miningAvailable = false;
			mineProgress = 0f;
		}
		
		//Maybe we're done mining! Then remove the block
		if(mineProgress > 0.999f && miningAvailable) {
			System.out.println("asdsadsdsdsadsadsdsad");
			objects.setCell(mouseTileX,mouseTileY,null);
			
			mineProgress = 0; //Sanity check
		}
		
		//Update the rendered rectangle
		renderedPos.x += (pos.x - renderedPos.x) * 30 * dt;
		renderedPos.y += (pos.y - renderedPos.y) * 30 * dt;
		
		rect.x = renderedPos.x;
		rect.y = renderedPos.y;
	}
	
	
	/** Read keyboard and mouse input, and attempt to move the player. */
	public void handleInput(float dt) {
		//Mouse
		if(miningAvailable && Gdx.input.isTouched()) {
			mineProgress += dt;
			mineProgress = min(mineProgress,1);
		} else {
			mineProgress = 0;
		}
		
		//Keyboard
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
	
	/** Whether the tile passed in is close enough to the player to be interacted with. */
	public boolean withinRange(int x, int y) {
		return Vector2.dst2(pos.x,pos.y,x,y) < 25;
	}
	
	/** A floating-point number from 0 to 1. Closer to 0 if the player is farther from the object. */
	public float rangeAlpha(int x, int y) {
		if(!withinRange(x,y)) return 0;
		return 1-clamp((Vector2.dst2(pos.x,pos.y,x,y)*0.9f)/25f,0,1);
	}
}
