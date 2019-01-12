package map;

import java.util.ArrayList;

import com.badlogic.gdx.math.Vector2;

import objects.Block;

public class Chunk {

	int id;
	Block tile;
	final static int SIZE = 200;
	static Block[][] chunk;

	public Chunk(int id) {
		chunk = new Block[SIZE][SIZE];
	}

	public static Block getBlock(int x, int y) {

		Block tile = (Block) chunk[x][y];
		return tile;

	}

	public void setBlock(int x, int y, Block tile) {
		chunk[x][y] = tile;
		System.out.println(tile.x + " " + tile.y);
	}

	public static int getSize() {
		return SIZE;
	}

}
