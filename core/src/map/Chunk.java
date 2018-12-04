package map;

import java.util.ArrayList;

import com.badlogic.gdx.math.Vector2;

import map.Tile;

	public class Chunk {

		int id; 
		Tile tile;
		final static int SIZE = 200;
		static Tile[][] chunk;
		
		public Chunk(int id) {
			chunk = new Tile[SIZE][SIZE];
		}
		
		public static Tile getTile(int x, int y) {
			
				
				Tile tile = (Tile)chunk[x][y];
				return tile;

		}
		
		public void setTile(int x, int y, Tile tile) {
			chunk[x][y] = tile;
			System.out.println(tile.x + " " + tile.y);
		}

		public static int getSize() {
			return SIZE;
		}
		
	}

