package map;

import java.util.ArrayList;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;

import map.Enums.BlockType;
import map.Enums.BlockType;
import screens.GameScreen;
import systems.ItemSystem;
import utils.BodyBuilder;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;

import objects.Block;
import objects.Item;

public class Mapper {
	Random random;
	static NoiseMaker noiseMaker;
	public static Chunk map;
	double x, y;
	Block tileB;
	GameScreen game;
	public World world;
	public Chunk chunk;
	static Item[][] objectLayer;
	ItemSystem itemSystem;

	public Mapper(GameScreen gamescreen, World world) {

		itemSystem = new ItemSystem();
		random = new Random();
		noiseMaker = new NoiseMaker();
		this.game = gamescreen;
		setupTiles();
		this.world = world;
		objectLayer = new Item[chunk.SIZE][chunk.SIZE];
		createObjectLayer();
		generateHitBoxes(this.world);
		
		randomHouse(3,3,4);
		randomHouse(5,5, 10);
		randomHouse(1,10, 5);
		
	}

	public void setupTiles() {


		chunk = new Chunk(1); //
		double[][] noise = noiseMaker.generate();
		Block tile = null;
		for (int row = 0; row < Chunk.SIZE; row++) {
			for (int col = 0; col < Chunk.SIZE; col++) {

				if (noise[row][col] < 0.1) {

					tile = new Block(row, col, 1f, Color.BLUE, "water", world, "water");
					tile.setPassable(true);
					chunk.setBlock(row, col, tile);

				}

				else if (noise[row][col] < 0.15) {
					tile = new Block(row, col, 1f, Color.YELLOW, "sand", world, "sand");
					tile.setPassable(true);
					chunk.setBlock(row, col, tile);

				}

				else if (noise[row][col] < 0.25) {
					tile = new Block(row, col, 1f, Color.GREEN, "grass", world, "grass");
					tile.setPassable(true);
					chunk.setBlock(row, col, tile);

				}

				else if (noise[row][col] < 0.30) {
					tile = new Block(row, col, 1f, Color.GRAY, "stone", world, "stone");

					chunk.setBlock(row, col, tile);

				} else {
					tile = new Block(row, col, 1f, Color.BROWN, "neutral", world, "");
					tile.setPassable(true);
					chunk.setBlock(row, col, tile);
				}

			}

		}
	}

	public void createObjectLayer() {
		for (int row = 0; row < Chunk.SIZE; row++) {
			for (int col = 0; col < Chunk.SIZE; col++) {

				Item item = new Item();
				Random random = new Random();
				int rnd2 = random.nextInt(30) + 1;

				if (rnd2 == 5 && map.getBlock(row, col).getBlockType() == "stone") {
					item = itemSystem.getItemByName("stoneAxe");
					item.setX(row);
					item.setY(col);
				} else {
					item = null;
				}

				objectLayer[row][col] = item;
			}

		}


		}
	
	public void randomHouse(int startX, int startY, int size) {

		for (int i = startY; i <= startY + size; i++) {
			for (int j = startX; j <= startX + size; j++) {
				if (i == startY || i == size + startY || j == startX|| j == size + startX) {
					Item item2 = itemSystem.getItemByName("stoneAxe");
					item2.x = j;
					item2.y = i;
					objectLayer[j][i] = item2;
					
					}
				}

			}
		
	}
	

	/** public void randomHouse() {

		for (int i = 1; i <= 5; i++) {
			for (int j = 1; j <= 5; j++) {
				if (i == 1 || i == 5 || j == 1 || j == 5) {
					Item item = itemSystem.getItemByName("stoneAxe");
					item.x = j;
					item.y = i;
					objectLayer[j][i] = item;
					
					}
				}

			}
	} **/


	public Item[][] getObjectLayer() {
		return objectLayer;
	}

	public Item getObjectLayer(float x, float y) {
		return this.objectLayer[(int) x][(int) y];
	}

	public void generateHitBoxes(World world) {
		Block tile;
		Body box;
		for (int x = 0; x < chunk.SIZE; x++) {
			for (int y = 0; y < chunk.SIZE; y++) {
				tile = chunk.getBlock(x, y);
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

}
