package quat.ld36.item;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import quat.ld36.util.ItemID;

public class Item {
	
	Vector2 pos;
	public ItemID itemID;
	
	Pixmap image;
	Texture texture;
	
	public Item(Vector2 pos_, ItemID item_) {
		pos = pos_.cpy();
		itemID = item_;
		
		image = itemID.mySprite();
		texture = new Texture(image);
	}
	
	public void render(SpriteBatch batch) {
		batch.draw(texture,pos.x,pos.y,1f,1f);
	}
	
	public boolean isAt(Vector2 other) {
		return pos.dst2(other) < 1f;
	}
	
}
