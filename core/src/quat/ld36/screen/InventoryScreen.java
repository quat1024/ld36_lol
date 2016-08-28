package quat.ld36.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector3;
import quat.ld36.LudumDare36VideoGame;
import quat.ld36.item.Item;
import quat.ld36.item.ItemStack;

import static com.badlogic.gdx.math.MathUtils.random;
import static quat.ld36.LudumDare36VideoGame.shapes;
import static quat.ld36.screen.GameScreen.player;

public class InventoryScreen implements Screen {
	
	LudumDare36VideoGame game;
	
	OrthographicCamera cam;
	
	public InventoryScreen(LudumDare36VideoGame game_) {
		game = game_;
		cam = new OrthographicCamera(LudumDare36VideoGame.WIDTH,LudumDare36VideoGame.HEIGHT);
		cam.position.set(LudumDare36VideoGame.WIDTH/2,LudumDare36VideoGame.HEIGHT/2,0);
		cam.update();
	}
	
	@Override
	public void show() {
		Screens.GAME_SCREEN.camR.setTarget(10f);
	}
	
	@Override
	public void render(float dt) {
		Screens.GAME_SCREEN.render(dt);
		//Darkened overlay effect
		Gdx.gl20.glEnable(GL20.GL_BLEND);
		Gdx.gl20.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
		game.shapes.begin(ShapeRenderer.ShapeType.Filled);
		game.shapes.setProjectionMatrix(cam.combined);
		game.shapes.setColor(0f,0f,0f,0.7f);
		game.shapes.rect(0,0,LudumDare36VideoGame.WIDTH,LudumDare36VideoGame.HEIGHT);
		game.shapes.end();
		Gdx.gl20.glDisable(GL20.GL_BLEND);
		
		game.batch.setProjectionMatrix(cam.combined);
		game.batch.begin();
		
		game.font.setColor(1,1,1,1f);
		game.font.draw(game.batch,"Inventory",10,LudumDare36VideoGame.HEIGHT-10);
		
		for(int i=0; i < player.inventory.size; i++) {
			ItemStack s = player.inventory.get(i);
			Item item = s.i;
			
			game.batch.draw(item.itemID.myTex(),20,LudumDare36VideoGame.HEIGHT-(40*(i+2)),32,32);
			game.font.draw(game.batch,s.stringify(),70,LudumDare36VideoGame.HEIGHT-(40*(i+1)));
		}
		
		game.batch.end();
		
		if(Gdx.input.isKeyJustPressed(Input.Keys.E)) {
			Screens.GAME_SCREEN.camR.setTarget(0f);
			game.setScreen(Screens.GAME_SCREEN);
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
