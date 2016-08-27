package quat.ld36.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector3;
import quat.ld36.LudumDare36VideoGame;
import quat.ld36.player.PlayerCharacter;

import static com.badlogic.gdx.math.MathUtils.clamp;
import static com.badlogic.gdx.math.MathUtils.floor;
import static quat.ld36.LudumDare36VideoGame.tiledMap;

public class GameScreen implements Screen {
	
	public static LudumDare36VideoGame game;
	
	public static OrthographicCamera cam;
	
	
	public static PlayerCharacter player;
	
	//Variable to account for full-screen rotation and scaling.
	public static float rotation = 0;
	public static float rotationTarget = 0;
	
	public GameScreen(LudumDare36VideoGame game_) {
		game = game_;
		
		cam = new OrthographicCamera();
		cam.setToOrtho(false, 32,18);
		
		player = new PlayerCharacter(8,7, tiledMap);
	}
	
	@Override
	public void show() {
		
	}
	
	public void render(float dt) {
		//We can render gameScreen from the inventory
		//so I need to make sure we're actually on the right screen here.
		boolean mainScreen = game.getScreen() instanceof GameScreen;
		
		
		//Clear screen
		Gdx.gl.glClearColor(0.1f,0.1f,0.1f,1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		//Update player
		if(mainScreen) player.handleInput(dt);
		player.update(dt);
		
		//Update cam and make sure it's not out of bounds
		cam.position.x += ((player.renderedPos.x + 0.5f) - cam.position.x) * 2 * dt;
		cam.position.y += ((player.renderedPos.y + 0.5f)- cam.position.y) * 2 * dt;
		cam.position.x = clamp(cam.position.x,16,1000);
		cam.position.y = clamp(cam.position.y,9,1000);
		
		//Rotation effect
		rotation += (rotationTarget - rotation) * 15f * dt;
		cam.up.set(0f,1f,0f);
		cam.direction.set(0f,0f,-1f);
		cam.rotate(rotation);
		cam.zoom = 1 - rotation / 20f;
		cam.update();
		
		//Calculate screenspace coordinates of stuff
		Vector3 mouseVec3 = cam.unproject(new Vector3(Gdx.input.getX(),Gdx.input.getY(),0f));
		int mouseXTile = floor(mouseVec3.x);
		int mouseYTile = floor(mouseVec3.y);
		
		Vector3 playerVec3 = new Vector3(player.renderedPos.x + 0.5f, player.renderedPos.y + 0.5f,0);
		
		//Make sure everything's aligned to the cam
		game.mapRenderer.setView(cam);
		game.shapes.setProjectionMatrix(cam.combined);
		
		//Image Sprites Rendering
		game.batch.begin();
		game.mapRenderer.render();
		game.batch.end();
		
		//Shapes Rendering
		game.shapes.begin(ShapeRenderer.ShapeType.Filled);
		player.render(game.shapes); //TODO make this a sprite
		game.shapes.end();
		
		game.shapes.begin(ShapeRenderer.ShapeType.Line);
		game.shapes.setColor(0f,0f,0f,0.3f);
		game.shapes.rect(mouseXTile,mouseYTile,1,1);
		game.shapes.line(mouseXTile+0.5f,mouseYTile+0.5f,playerVec3.x,playerVec3.y);
		
		
		game.shapes.end();
		
		if(mainScreen && Gdx.input.isKeyJustPressed(Input.Keys.E)) {
			game.setScreen(Screens.INVENTORY_SCREEN);
		}
	}
	
	@Override
	public void resize(int width, int height) {
		
	}
	
	@Override
	public void pause() {
		
	}
	
	@Override
	public void resume() {
		
	}
	
	@Override
	public void hide() {
		
	}
	
	@Override
	public void dispose() {
		
	}
}
