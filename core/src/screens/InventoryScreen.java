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
import com.random.survive.Survive;

import objects.Player;
import systems.InventorySlot;
import systems.InventorySystem2;

public class InventoryScreen implements Screen {

	public Stage stage;
	public Table inventory;
	public InventorySystem2 invSystem;
	public ArrayList<InventorySlot> inv3;
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
		this.invSystem = player.getInventory2();
		this.inv3 = new ArrayList<InventorySlot>();
		
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
		this.inventory.setFillParent(true);
		this.scroll = new ScrollPane(inventory, skin);
		scroll.setFillParent(true);

		scroll.layout();
		inventory.pack();
		this.inventory.left().top();
		this.stage.addActor(scroll);
		System.out.println("inventory screen");
		this.inv3 = player.inventory2.getItems();
		this.showInventory2();
		Gdx.input.setInputProcessor(this.stage);

	}

	public Stage getStage() {
		return this.stage;
	}

	public void show() {

	}


	public void showInventory2() {

		
		for(int i = 0; i < inv3.size(); i++) {
			
			if (!inv3.get(i).isEmpty()) {
				System.out.println("stack nro. " + i + " stack count: "+inv3.get(i).getCount() +" " + inv3.get(i).getItem(i));
			}
			
		
	}
		
		ArrayList<InventorySlot> invSlots = new ArrayList<InventorySlot>();
		invSlots = invSystem.getItems();
		System.out.println(invSlots.size());
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
				Label label = new Label("item: "+invSlots.get(i).getItem(i).name +" x " + invSlots.get(i).getCount(), labels);
				System.out.println("stack nro. " + i + " stack count: "+invSlots.get(i).getCount());
				
				
				inventory.add(label);
				inventory.add(button);
				inventory.add(button2);
				inventory.add(button3);
				
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
