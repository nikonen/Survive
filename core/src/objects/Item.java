package objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

public class Item extends Entity{

	public String name; 
	public float damage;
	public String description;
	public String image;
	public float x, y;
	
	public String getSprite() {
		return image;
	}

	public void setSprite(String image) {
		this.image = image;
	}

	public Item() {
		
	}
	
	public Item(String name, float damage, String description, String image) {
		
		this.name = name;
		this.damage = damage;
		this.description = description;
		this.image = image;
		
	}
	
	public String getName() {
		return this.name;
	}
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setName(String name) {
		this.name = name;
	}
	public float getDamage() {
		return damage;
	}
	public void setDamage(float damage) {
		this.damage = damage;
	}
	
	
	public String toString() {
		
		return name; 
	} 
 }
