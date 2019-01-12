package systems;

import java.util.ArrayList;
import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Json;

import objects.Block;
import objects.Item;

public class ItemSystem {

	Item item;
	Json json;
	FileHandle itemJson, edibleJson, blocksJson;
	ArrayList<Item> items;
	ArrayList<Block> blocks;
	HashMap<String, Item> itemmap;
	HashMap<String, Block> blockMap;
	
	public ItemSystem() {
		itemmap = new HashMap<String, Item>();
		blockMap = new HashMap<String, Block>();
		
		json = new Json();
		
		// Let's read items
		
		itemJson = Gdx.files.internal("items/itemJson.json");
		items = json.fromJson(ArrayList.class, Item.class, itemJson);
		
		for (int i = 0; i < items.size(); i++) {
			System.out.println( i + " : " +items.get(i));
			itemmap.put(items.get(i).getName(), items.get(i));
		} 
		
		// Time for blocks
		
		blocksJson = Gdx.files.internal("items/blocks.json");
		blocks = json.fromJson(ArrayList.class, Block.class, blocksJson);
		
		
		
		for (int i = 0; i < blocks.size(); i++) {

			blockMap.put(blocks.get(i).getName(), blocks.get(i));
			System.out.println(blockMap.toString());

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
	
	public Block getBlockByName(String name) {
		
		Block block = new Block(blockMap.get(name).getName(),
				blockMap.get(name).getHealth(),
				blockMap.get(name).getDescription(),
				blockMap.get(name).getBlockType(),
				blockMap.get(name).getImage());
		System.out.println(block.getBlockType());
		return block;
	}

	
}
