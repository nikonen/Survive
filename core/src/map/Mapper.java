package map;

import java.util.ArrayList;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;

import map.Enums.TileType;
import map.Tile;
import screens.GameScreen;
import utils.BodyBuilder;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import objects.Tree;

public class Mapper {
	Random random;
	static NoiseMaker noiseMaker;
	public static Chunk map;
	double x, y;
	Tile tileB;
	GameScreen game;
	public World world;
	public Chunk chunk;
	ArrayList<Tree> trees;

	public Mapper(GameScreen gamescreen, World world) {

		random = new Random();
		noiseMaker = new NoiseMaker();
		this.game = gamescreen;
		setupTiles();
		this.world = world;

		generateHitBoxes(this.world);
	}

	public void setupTiles() {

		trees = new ArrayList<Tree>();

		chunk = new Chunk(1); //
		double[][] noise = noiseMaker.generate();
		Tile tile = null;
		Tree tree = null;
		for (int row = 0; row < Chunk.SIZE; row++) {
			for (int col = 0; col < Chunk.SIZE; col++) {
				

				if (noise[row][col] < 0.1) {

					tile = new Tile(row, col, 16, Color.BLUE, TileType.WATER, world);
					tile.setPassable(true);
					chunk.setTile(row, col, tile);

				}

				else if (noise[row][col] < 0.15) {
					tile = new Tile(row, col, 16, Color.YELLOW, TileType.SAND, world);
					tile.setPassable(true);
					chunk.setTile(row, col, tile);

				}

				else if (noise[row][col] < 0.25) {
					tile = new Tile(row, col, 16, Color.GREEN, TileType.GRASS, world);
					tile.setPassable(true);
					chunk.setTile(row, col, tile);
					int rnd2 = random.nextInt(30) + 1;

					if (rnd2 == 5) {
						tree = new Tree((float) row, (float) col);
						trees.add(tree);
						System.out.println("tree added");

					}
				}

				else if (noise[row][col] < 0.30) {
					tile = new Tile(row, col, 16, Color.GRAY, TileType.STONE, world);

					chunk.setTile(row, col, tile);

				} else {
					tile = new Tile(row, col, 16, Color.BROWN, TileType.NEUTRAL, world);
					tile.setPassable(true);
					chunk.setTile(row, col, tile);
				}



			}
			
		}

		}

	public void generateHitBoxes(World world) {
		Tile tile;
		Body box;
		for (int x = 0; x < chunk.SIZE; x++) {
			for (int y = 0; y < chunk.SIZE; y++) {
				tile = chunk.getTile(x, y);
				if (!tile.isPassable()) {
					box = BodyBuilder.createBox(x, y, 1, 1, true, false, 0, world);
					box.setTransform(x + 0.5f, y + 0.5f, 0);
					tile.body = box;

				}
			}
		}

	}

	public static Chunk getMap() {
		return map;
	}

	public ArrayList getTrees() {
		return this.trees;
	}

}
