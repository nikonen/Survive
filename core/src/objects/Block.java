package objects;

import java.util.ArrayList;
import java.util.Locale;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;

import map.Chunk;
import map.Enums;
import map.Enums.BlockType;
import utils.BodyBuilder;

public class Block extends Entity{

	String name;
	public float health;
	public String description;
	public String image;
	public float x, y, size;
	public Color color;
	public boolean isDestructable;
	public boolean isPassable;
	public Body body;
	World world;
	String blockType;
	Chunk chunk;
	BodyBuilder builder;
	public static ArrayList<Body> toBeDestroyed;
	
	public Block() {
		
	}
	
	public Block(String name, float health, String description, String blockType, String image) {
		this.name = name;
		this.health = health;
		this.description = description;
		this.blockType = blockType;
		this.image = image;
	}
	
	public float getHealth() {
		return health;
	}

	public void setHealth(float health) {
		this.health = health;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

	public float getSize() {
		return size;
	}

	public void setSize(float size) {
		this.size = size;
	}

	public Block (float x, float y, float size, Color color, String type, World world, String name) {
		
		this.x = x;
		this.y = y;
		this.color = color;
		this.size = size;
		this.blockType = type;
		this.toBeDestroyed = new ArrayList<Body>();
		this.name = name;
	}
	
	public Block(float x, float y) {
		this.x = x;
		this.y = y;
		this.size = 1f;
	}
	public static ArrayList<Body> getToBeDestroyed() {
		return toBeDestroyed;
	}

	public void setToBeDestroyed(ArrayList<Body> toBeDestroyed) {
		this.toBeDestroyed = toBeDestroyed;
	}

	public void setNull(int row, int col) {
		this.color = Color.DARK_GRAY;
		System.out.println(row + " " +col);
		
		
		if (this.blockType == "stone") {
			this.toBeDestroyed.add(this.body);

			
		}
		
		this.isPassable = true;
		this.blockType = null;
		
		

		
	}
	
	public String getBlockType() {
		return this.blockType;
	}
	
	public void setPassable(boolean passable) {
		this.isPassable = passable;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isPassable() {
		return isPassable;
	}
	
	

}
