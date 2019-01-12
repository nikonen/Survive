package systems;

import java.util.ArrayList;

import com.badlogic.gdx.utils.Array;

import objects.Entity;
import objects.Item;

public class InventorySlot {

	static final int MAX_STACK = 16;
	public ArrayList<Entity> stack;
	String name;

	public InventorySlot() {
		stack = new ArrayList<Entity>();
	}

	public ArrayList<Entity> getItemStack() {
		return this.stack;
	}

	public void addItem(Entity e) {
		this.stack.add(e);
	}

	public String getType() {

		System.out.println("GET TYPE: " + this.stack.get(0).toString());
		return this.stack.get(0).toString();

	}
	
	public boolean isEmpty() {
		return this.stack.size() == 0;
	}
	
	public int getCount() {
		return this.stack.size();
	}
	
	public InventorySlot getItems() {
		return this;
	}
	
	public Entity getItem(int index) {

			return (Entity) this.stack.get(0);


	}

}
