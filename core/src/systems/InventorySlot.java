package systems;

import com.badlogic.gdx.utils.Array;

import objects.Entity;
import objects.Item;

public class InventorySlot {

	static final int MAX_STACK = 16;
	public Array<Entity> stack;
	String name;

	public InventorySlot() {
		stack = new Array<Entity>();
	}

	public Array<Entity> getItemStack() {
		return this.stack;
	}

	public void addItem(Entity e) {
		this.stack.add(e);
	}

	public String getType() {

		return this.stack.first().toString();

	}
	
	public boolean isEmpty() {
		return this.stack.size == 0;
	}
	
	public int getCount() {
		return this.stack.size;
	}
	
	public InventorySlot getItems() {
		return this;
	}
	
	public Item getItem(int index) {
		return (Item) this.stack.get(index);
	}

}
