package quat.ld36.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import quat.ld36.LudumDare36VideoGame;

public enum ItemID {
	
	LOG(0,"Wood Log","log"),
	PLANKS(1,"Wood Planks","planks");
	
	public static final int COUNT = 2;
	
	public final int id;
	public final String name;
	public final String filename;
	
	ItemID(int id_, String name_, String filename_) {
		id = id_;
		name = name_;
		filename = filename_;
	}
	
	public FileHandle path() {
		return Gdx.files.internal("icons/" + filename + ".png");
	}
	
	public Pixmap mySprite() {
		return LudumDare36VideoGame.itemSprites.get(id);
	}
	
	public Texture myTex() {
		return LudumDare36VideoGame.itemTex.get(id);
	}
	
	public static ItemID fromID(int id) {
		for(ItemID i : values()) {
			if(i.id == id) return i;
		}
		return null;
	}
	
}
