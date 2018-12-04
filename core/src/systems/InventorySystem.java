package systems;

import java.util.ArrayList;

import objects.Item;

public class InventorySystem {

	ArrayList<Item> items;
	
	public InventorySystem() {
		this.items = new ArrayList<Item>();
	}

	public ArrayList<Item> getItems() {
		return items;
	}
	
	public Item getItem(int index) {
		return this.items.get(index);
	}

	public void setItems(ArrayList<Item> items) {
		this.items = items;
	}
	
	public void addItem(Item item) {
		this.items.add(item);
	}
	
	public void listItems() {
		for (int i = 0; i < items.size(); i++) {
			System.out.println(items.get(i));
		}
	}
	
	public void removeItem(Item item) {
		this.items.remove(item);
	}
	
	public void removeItemIndex(int index) {
		this.items.remove(index);
	}
}
