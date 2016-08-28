package quat.ld36.item;

public class ItemStack {
	public Item i;
	
	int count;
	
	public ItemStack(Item i_, int count_) {
		i = i_;
		count = count_;
	}
	
	public String stringify() {
		return count + "x " + i.itemID.name;
	}
	
	public boolean isOfType(Item in) {
		return in.itemID.id == i.itemID.id;
	}
}
