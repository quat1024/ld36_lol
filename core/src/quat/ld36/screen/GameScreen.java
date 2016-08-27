package quat.ld36.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import quat.ld36.LudumDare36VideoGame;
import quat.ld36.player.PlayerCharacter;

import static com.badlogic.gdx.math.MathUtils.clamp;
import static quat.ld36.LudumDare36VideoGame.testMap;

public class GameScreen implements Screen {
	
	LudumDare36VideoGame game;
	
	OrthographicCamera camera;
	
	
	PlayerCharacter player;
	
	public GameScreen(LudumDare36VideoGame game_) {
		game = game_;
		
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 32,18);
		
		player = new PlayerCharacter(8,7,testMap);
	}
	
	@Override
	public void show() {
		
	}
	
	public void render(float dt) {
		Gdx.gl.glClearColor(0.1f,0.1f,0.1f,1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		camera.position.x += (player.renderedPos.x - camera.position.x) * 2 * dt;
		camera.position.y += (player.renderedPos.y - camera.position.y) * 2 * dt;
		
		camera.position.x = clamp(camera.position.x,16,1000);
		camera.position.y = clamp(camera.position.y,9,1000);
		
		camera.update();
		
		player.handleInput(dt);
		player.update(dt);
		
		
		game.renderer.setView(camera);
		game.shapes.setProjectionMatrix(camera.combined);
		
		game.batch.begin();
		game.renderer.render();
		game.batch.end();
		
		game.shapes.begin(ShapeRenderer.ShapeType.Filled);
		player.render(game.shapes);
		game.shapes.end();
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
