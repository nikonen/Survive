package systems;

import java.util.ArrayList;
import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Json;

import objects.Item;

public class ItemSystem {

	Item item;
	Json json;
	FileHandle itemJson;
	ArrayList<Item> items;
	HashMap<String, Item> itemmap;
	
	public ItemSystem() {
		itemmap = new HashMap<String, Item>();
		json = new Json();
		itemJson = Gdx.files.internal("items/testItems.json");
		items = json.fromJson(ArrayList.class, Item.class, itemJson);
		
		for (int i = 0; i < items.size(); i++) {
			System.out.println( i + " : " +items.get(i));
			itemmap.put(items.get(i).getName(), items.get(i));
		}
	}
	
	public HashMap<String, Item> getItems() {
		
		return this.itemmap;
		
	}
	
	public Item getItemByName(String name) {
		Item item = new Item(itemmap.get(name).getName(),
				itemmap.get(name).getDamage(), 
				itemmap.get(name).getDescription(),
				itemmap.get(name).getSprite());
		return item;

	}

	
}
