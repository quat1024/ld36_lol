package quat.ld36.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import quat.ld36.LudumDare36VideoGame;

public class GameScreen implements Screen {
	
	LudumDare36VideoGame game;
	
	OrthographicCamera camera;
	
	public GameScreen(LudumDare36VideoGame game_) {
		game = game_;
		
		camera = new OrthographicCamera();
		camera.setToOrtho(false, LudumDare36VideoGame.WIDTH, LudumDare36VideoGame.HEIGHT);
	}
	
	@Override
	public void show() {
		
	}
	
	public void render(float delta) {
		Gdx.gl.glClearColor(0.1f,0.1f,0.1f,1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		camera.update();
		game.batch.begin();
		
		game.font.draw(game.batch,"WOW IT REALLY IS!!!!!!!!",LudumDare36VideoGame.WIDTH/3, LudumDare36VideoGame.HEIGHT/2);
		
		game.batch.end();
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
