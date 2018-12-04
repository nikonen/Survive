package map;

import java.util.ArrayList;
import java.util.Locale;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;

import map.Enums.TileType;
import utils.BodyBuilder;

public class Tile {

	String name;
	public float x, y, size;
	public Color color;
	public boolean isDestructable;
	public boolean isPassable;
	Body body;
	World world;
	TileType tiletype;
	Chunk chunk;
	BodyBuilder builder;
	public static ArrayList<Body> toBeDestroyed;
	public Tile (float x, float y, float size, Color color, Enums.TileType type, World world) {
		this.name = type.name().toString().toLowerCase();
		this.x = x;
		this.y = y;
		this.color = color;
		this.size = size;
		this.tiletype = type;
		this.toBeDestroyed = new ArrayList<Body>();
	}
	
	public Tile(float x, float y) {
		this.x = x;
		this.y = y;
		this.size = 16;
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
		
		
		if (this.tiletype == TileType.STONE) {
			this.toBeDestroyed.add(this.body);

			
		}
		
		this.isPassable = true;
		this.tiletype = null;
		
		

		
	}
	
	public TileType getTileType() {
		return this.tiletype;
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
