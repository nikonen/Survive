package objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Tree {

	float x, y, size;
	Texture tree;
	
	public Tree(float x, float y) {
		this.x = x;
		this.y = y;
		this.size = 32;
		tree = new Texture(Gdx.files.internal("tree_01.png"));
	}
	
	public void update(SpriteBatch batch) {
		batch.draw(tree, this.x, this.y, 2f, 2f);
	}
}
