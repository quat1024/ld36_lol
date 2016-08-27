package quat.ld36.screen;

import quat.ld36.LudumDare36VideoGame;

public class Screens {
	
	public static MainMenuScreen MAIN_MENU_SCREEN;
	public static GameScreen GAME_SCREEN;
	public static InventoryScreen INVENTORY_SCREEN;
	
	public Screens(LudumDare36VideoGame game) {
		MAIN_MENU_SCREEN = new MainMenuScreen(game);
		GAME_SCREEN = new GameScreen(game);
		INVENTORY_SCREEN = new InventoryScreen(game);
	}
	
}
