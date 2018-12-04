package systems;

import java.util.ArrayList;

import objects.Entity;
import objects.Item;

public class InventorySystem2 {

	static final int INV_SIZE = 16;
	ArrayList<InventorySlot> slots;

	public InventorySystem2() {
		this.slots = new ArrayList<InventorySlot>();
		for (int i = 0; i < INV_SIZE; i++) {
			InventorySlot e = new InventorySlot();
			slots.add(e);

		}

	}

	public void addItem(Entity e) {

		for (int i = 0; i < this.slots.size(); i++) {
			if (slots.get(i).isEmpty()) {
				slots.get(i).addItem(e);
				System.out.println("Added item " + e + " to stack " + i);

				break;
				
			}
			
			if (slots.get(i).getType().equals(e.toString()) && slots.get(i).stack.size() < slots.get(i).MAX_STACK) {
				slots.get(i).addItem(e);
				System.out.println("Added item " + e + " to existing stack " + i);
				break;
				
			}

		}

	}
	

	public ArrayList<InventorySlot> getItems() {
		ArrayList<InventorySlot> stackedItems = new ArrayList<InventorySlot>();
		for (int i = 0; i < this.slots.size(); i++) {
			InventorySlot slot = slots.get(i).getItems();
			stackedItems.add(slot);
		}
		
		return stackedItems;
		
		
	}	
	
}
	