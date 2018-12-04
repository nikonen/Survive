package screens;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.swing.Box;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.ai.pfa.GraphPath;
import com.badlogic.gdx.ai.pfa.indexed.IndexedAStarPathFinder;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.JointEdge;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.random.survive.Survive;

import box2dLight.BlendFunc;
import box2dLight.ConeLight;
import box2dLight.PointLight;
import box2dLight.RayHandler;
import map.Chunk;
import map.Mapper;
import map.Tile;

import map.PathFinder;
import objects.Item;
import objects.Player;
import objects.Tree;
import systems.Control;
import systems.Gui;
import systems.InventorySlot;
import systems.InventorySystem2;
import systems.ItemSystem;
import utils.Destroy;
import utils.FrameRate;
import utils.ItemCreator;

public class GameScreen extends ApplicationAdapter implements Screen, InputProcessor {

	public final static float SCREEN_WIDTH = 800;
	public final static float SCREEN_HEIGHT = 600;
	Chunk map;
	Mapper mapper;
	SpriteBatch batch;
	TextureAtlas atlas;
	Texture texture = new Texture(Gdx.files.internal("background.jpg"));
	BitmapFont font;
	Player player;
	final HashMap<String, Sprite> sprites = new HashMap<String, Sprite>();
	ArrayList<Tree> trees;
	private int displayW, displayH;
	public static OrthographicCamera camera;
	ExtendViewport viewport;
	Box2DDebugRenderer debugRenderer;
	float elapsedTime = 0;
	static World world;
	static final float STEP_TIME = 1f / 60f;
	static final int VELOCITY_ITERATIONS = 6;
	static final int POSITION_ITERATIONS = 2;
	float accumulator = 0;
	ShapeRenderer shape;
	Control control;
	ItemSystem itemSystem = new ItemSystem();
	Gui gui;
	InputMultiplexer multi = new InputMultiplexer();
	ArrayList<Item> droppedItems;
	double[][] random;
	
	PathFinder pathFinder;
	InventoryScreen inventoryScreen;
	Survive game;
	
	InventorySystem2 inv2 = new InventorySystem2();
	
	Item item2 = new Item();
	


	int startX = 1;
	int startY = 1;
	int endX = 120;
	int endY = 120;

	public void create() {
		font = new BitmapFont();
		map = Mapper.getMap();
		camera = new OrthographicCamera();
		viewport = new ExtendViewport(50,50, camera);
		viewport.apply();
		debugRenderer = new Box2DDebugRenderer();

		batch = new SpriteBatch();
		Box2D.init();
		world = new World(new Vector2(0, 0), false);
		player = new Player(world, batch);
		mapper = new Mapper(null, world);
		shape = new ShapeRenderer();
		control = new Control(25, 25, camera, player, world, mapper, gui);
		// Gdx.input.setInputProcessor(control);

		Item item = new Item();
		item = itemSystem.getItemByName("stoneAxe");
		player.getInventory().addItem(item);
		
		item2 = itemSystem.getItemByName("stoneAxe");
		inv2.addItem(item2);
		inv2.addItem(item2);
		
		item2 = itemSystem.getItemByName("club");
		inv2.addItem(item2);
		inv2.addItem(item2);
		
		item2 = itemSystem.getItemByName("knife");
		inv2.addItem(item2);
		inv2.addItem(item2);
		
		item2 = itemSystem.getItemByName("stoneAxe");
		inv2.addItem(item2);
		inv2.addItem(item2);
		
		item2 = itemSystem.getItemByName("stoneAxe");
		inv2.addItem(item2);
		inv2.addItem(item2);
		
		item2 = itemSystem.getItemByName("knife");
		inv2.addItem(item2);
		inv2.addItem(item2);
		
		item2 = itemSystem.getItemByName("stone");
		inv2.addItem(item2);
		inv2.addItem(item2);
		
		item2 = itemSystem.getItemByName("stone");
		inv2.addItem(item2);
		inv2.addItem(item2);
		
		inv2.getItems();
		ArrayList<InventorySlot> inventory = new ArrayList<InventorySlot>();
		inventory = inv2.getItems();
		for(int i = 0; i < inventory.size() - 1; i++) {
			
				if (!inventory.get(i).isEmpty()) {
					System.out.println("stack nro. " + i + " stack count: "+inventory.get(i).getCount() +" " + inventory.get(i).getItem(i));
				}
				
			
		}

		player.getInventory().listItems();
		gui = new Gui(player, this);
		multi.addProcessor(gui.getStage());
		
		// Gdx.input.setInputProcessor(control);
		multi.addProcessor(control);
		Gdx.input.setInputProcessor(multi);
		droppedItems = new ArrayList<Item>();
		
		inventoryScreen = new InventoryScreen(player, game, this);
		multi.addProcessor(inventoryScreen.getStage());
		
		
		trees = new ArrayList();
		trees = mapper.getTrees();

	}

	public GameScreen(Survive game) {
		this.game = game;
		create();
	}

	@Override
	public void show() {
		System.out.println("Game Screen");

	}

	@Override
	public void render(float delta) {
		super.render();
		Gdx.input.setInputProcessor(multi);

		stepWorld();
		removeBodies();
		camera.position.set(player.body.getPosition().x, player.body.getPosition().y, 0);
		camera.update();
		Gdx.gl.glClearColor(0, 0, 0, 0);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.setProjectionMatrix(camera.combined);
		drawMap(batch);
		batch.begin();

		drawDroppedItems(batch);

		// batch.draw(texture, 0, 0);
		player.update(delta, camera, control);
		Tree tree;
		for (int i = 0; i < trees.size(); i++) {
			tree = trees.get(i);
			tree.update(batch);
		}

		batch.end();

		debugRenderer.render(world, camera.combined);

		if (Gdx.input.isKeyJustPressed(Keys.T)) {
			gui.listInventory();
		}
		
		if (Gdx.input.isKeyJustPressed(Keys.Q)) {
			game.setScreen(new InventoryScreen(player, game, this));
		}
		
		if (Gdx.input.isKeyJustPressed(Keys.C)) {
			game.setScreen(new ItemCreator(game, this));
		}

		gui.update();
		gui.getStage().act();
		gui.getStage().draw();
	}

	public void drawMap(SpriteBatch batch) {

		Tile tile;
		shape = new ShapeRenderer();
		shape.setProjectionMatrix(batch.getProjectionMatrix());
		shape.setTransformMatrix(batch.getTransformMatrix());
		shape.begin(ShapeType.Filled);

		for (int x = 0; x < Chunk.getSize(); x++) {
			for (int y = 0; y < Chunk.getSize(); y++) {

				tile = Chunk.getTile(x, y);
				shape.setColor(tile.color);
				shape.rect(tile.x, tile.y, tile.size, tile.size);

			}

		}

		shape.end();

	}

	static ArrayList<Body> tiles = new ArrayList<Body>();

	public static void removeBodies() {

		tiles = Tile.getToBeDestroyed();

		if (!world.isLocked()) {
			if (tiles != null && tiles.size() > 0) {
				for (int i = 0; i < tiles.size(); i++) {
					if (tiles.get(i) != null) {
						tiles.get(i).setActive(false);
						tiles.get(i).getWorld().destroyBody(tiles.get(i));
						tiles.remove(i);
					}

				}
			}
		}

	}

	public void addDroppedItems(Item item) {
		this.droppedItems.add(item);
	}

	public void drawDroppedItems(SpriteBatch batch) {
		Texture tex;
		for (int i = 0; i < droppedItems.size(); i++) {
			Item a = (Item) droppedItems.get(i);
			tex = new Texture(Gdx.files.internal(a.getSprite()));
			batch.draw(tex, a.x, a.y, 1, 1);

		}
	}

	@Override
	public void resize(int width, int height) {

		viewport.update(width, height);

		batch.setProjectionMatrix(camera.combined);

	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		atlas.dispose();
		world.dispose();
		debugRenderer.dispose();
	}

	// own methods

	private void stepWorld() {
		float delta = Gdx.graphics.getDeltaTime();
		accumulator += Math.min(delta, 0.25f);

		if (accumulator >= STEP_TIME) {
			accumulator -= STEP_TIME;
			world.step(STEP_TIME, VELOCITY_ITERATIONS, POSITION_ITERATIONS);
		}

	}

	@Override
	public boolean keyDown(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {

		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {

		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int amount) {

		if (amount < 0) {
			camera.zoom -= 0.5f;
		} else {
			camera.zoom += 0.5f;
		}

		System.out.println(camera.zoom);

		return false;
	}

}
