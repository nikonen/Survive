package screens;

import java.awt.Color;
import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.random.survive.Survive;

import objects.Player;
import systems.InventorySlot;
import systems.InventorySystem;
import systems.InventorySystem2;

public class InventoryScreen implements Screen {

	public Stage stage;
	public Table inventory;
	public InventorySystem inventorySystem;
	public InventorySystem2 inv2;
	public Player player;
	public Survive game;
	public Screen parent;
	public OrthographicCamera camera;
	public Label label;
	public BitmapFont font;
	public ScrollPane scroll;
	public FreeTypeFontGenerator generator;
	public FreeTypeFontParameter parameter;
	public TextButtonStyle buttonStyle;
	BitmapFont font2;
	Label inv;

	Skin skin;

	public InventoryScreen(Player player, Survive game, Screen parent) {

		this.player = player;
		this.inventorySystem = player.getInventory();
		this.inv2 = player.getInventory2();
		this.game = game;
		this.parent = parent;
		this.stage = new Stage();
		this.camera = GameScreen.camera;
		this.skin = new Skin(Gdx.files.internal("skins/plain-james/skin/plain-james-ui.json"));
		this.font = new BitmapFont();
		this.inv = new Label(" ", skin);

		generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/OpenSans-Regular.ttf"));
		parameter = new FreeTypeFontParameter();
		parameter.size = 12;
		font2 = generator.generateFont(parameter);
		buttonStyle = new TextButtonStyle();
		buttonStyle.font = font2;

		this.inventory = new Table();
		this.scroll = new ScrollPane(inventory, skin);
		scroll.setFillParent(true);
		scroll.setDebug(true);
		inventory.setDebug(true);

		scroll.layout();
		inventory.pack();
		this.inventory.left().top();
		this.stage.addActor(scroll);
		System.out.println("inventory screen");

		showInventory2();
		Gdx.input.setInputProcessor(this.stage);

	}

	public Stage getStage() {
		return this.stage;
	}

	public void show() {

	}

	/** public void showInventory() {

		for (int i = 0; i < inventorySystem.getItems().size(); i++) {

			Button button = new TextButton("Use", skin);
			Button button2 = new TextButton("Drop", skin);
			Button button3 = new TextButton("Examine", skin);
			
			button.setTransform(true);
			button2.setTransform(true);
			button3.setTransform(true);

			button.setScale(0.3f);


			LabelStyle labels = new LabelStyle();
			labels.font = font2;
			labels.fontColor = com.badlogic.gdx.graphics.Color.BLACK;
			Label label = new Label(inventorySystem.getItem(i).name, labels);

			inventory.add(label);
		
			inventory.add(button);
			inventory.add(button2);
			inventory.add(button3);
			
			inventory.row();

			
		}
	} **/
	
	public void showInventory2() {

		ArrayList<InventorySlot> invSlots = new ArrayList<InventorySlot>();
		invSlots = inv2.getItems();
		for (int i = 0; i < invSlots.size(); i++) {

			Button button = new TextButton("Use", skin);
			Button button2 = new TextButton("Drop", skin);
			Button button3 = new TextButton("Examine", skin);
			
			button.setTransform(true);
			button2.setTransform(true);
			button3.setTransform(true);

			LabelStyle labels = new LabelStyle();
			labels.font = font2;
			labels.fontColor = com.badlogic.gdx.graphics.Color.BLACK;
			
			if (!invSlots.get(i).isEmpty()) {
				Label label = new Label("item: "+invSlots.get(i).getItem(i).name, labels);
				System.out.println("stack nro. " + i + " stack count: "+invSlots.get(i).getCount());
				

				inventory.add(label);
			
				inventory.add(button).expand().top().right();
				//inventory.add(button2);
				//inventory.add(button3);
				
				inventory.row();
			}
			


			
		}
	}

	@Override
	public void render(float delta) {

		Gdx.gl.glClearColor(0.3f, 0.3f, 0.3f, 0.3f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		stage.act();
		stage.draw();

		if (Gdx.input.isKeyJustPressed(Keys.Q)) {
			game.setScreen(parent);
		}

	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub

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
		// TODO Auto-generated method stub

	}
}
