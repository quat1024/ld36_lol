package quat.ld36.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import quat.ld36.LudumDare36VideoGame;

import static com.badlogic.gdx.math.MathUtils.random;
import static quat.ld36.LudumDare36VideoGame.shapes;

public class InventoryScreen implements Screen {
	
	LudumDare36VideoGame game;
	
	OrthographicCamera cam;
	
	public InventoryScreen(LudumDare36VideoGame game_) {
		game = game_;
		cam = new OrthographicCamera(1,1);
	}
	
	@Override
	public void show() {
		Screens.GAME_SCREEN.rotationTarget = 10;
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
		game.shapes.rect(-1,-1,2,2);
		game.shapes.end();
		Gdx.gl20.glDisable(GL20.GL_BLEND);
		
		game.batch.begin();
		game.font.draw(game.batch,"Inventory",10,LudumDare36VideoGame.HEIGHT-10);
		game.batch.end();
		
		if(Gdx.input.isKeyJustPressed(Input.Keys.E)) {
			Screens.GAME_SCREEN.rotationTarget = 0;
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
