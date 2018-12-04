package utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextArea;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Json;
import com.google.gson.Gson;
import com.random.survive.Survive;

import objects.Item;
import screens.GameScreen;

public class ItemCreator implements Screen {

	public Screen parent;
	public Stage stage;
	public Item item;
	public Table createTable;
	public Label name, damage, description, image;
	public TextField nameTxt, damageTxt, descriptionTxt, imageTxt;
	public TextButton createButton, backButton;
	public TextArea preview;
	public Skin skin;
	static Survive game;
	public Json json;
	public String filePath;
	public Gson gson;
	
	public ItemCreator(final Survive game, final GameScreen parent) {
		
		this.game = game;
		this.parent = parent;
		this.stage = new Stage();
		this.createTable = new Table();
		this.skin = new Skin(Gdx.files.internal("skins/plain-james/skin/plain-james-ui.json"));
		
		this.name = new Label("name", skin);
		this.damage = new Label( "damage", skin);
		this.description = new Label("description", skin);
		this.image = new Label("Image url", skin);
		this.nameTxt = new TextField("name", skin);
		this.damageTxt = new TextField("damage", skin);
		this.descriptionTxt = new TextField("description", skin);
		this.imageTxt = new TextField("image url", skin);
		this.createButton = new TextButton("Create", skin);
		this.backButton = new TextButton("Back to game", skin);
		this.preview = new TextArea("preview \n", skin);
		this.json = new Json();
		gson = new Gson();
		this.filePath = "items/testItems.json";
		
		
		
		this.createTable.setFillParent(true);
		this.createTable.row();
		
		// Size, position, etc
		
		preview.setPrefRows(8);
		
		// Adding labels and txtfields
		
		this.createTable.add(this.name);
		this.createTable.add(this.nameTxt);
		createTable.row();
		this.createTable.add(this.damage);
		this.createTable.add(this.damageTxt);
		createTable.row();
		this.createTable.add(this.description);
		this.createTable.add(this.descriptionTxt);
		createTable.row();
		this.createTable.add(this.image);
		this.createTable.add(this.imageTxt);
		createTable.row();
		this.createTable.add(this.preview).expand().fill();
		this.createTable.row();
		this.createTable.add(this.createButton);
		this.createTable.add(this.backButton);
		this.stage.addActor(createTable);
		
		// add listeners
		this.backButton.addListener(new ClickListener() {
			public void clicked(InputEvent event, float x, float y) {
				game.setScreen(parent);
				
			};
		});
		
		this.createButton.addListener(new ClickListener() {
			public void clicked(InputEvent event, float x, float y) {
				createItem();
			}
		});
	
		Gdx.input.setInputProcessor(this.stage);
		
	}
	
	public void createItem() {
		
		item = new Item(this.nameTxt.getText(), Float.parseFloat(this.damageTxt.getText()), this.descriptionTxt.getText(), this.imageTxt.getText());
		preview.setText(gson.toJson(item));
		writeFile(filePath, item);
		
		
	}
	
	public void writeFile(String filePath, Item item) {
		
		FileHandle handle = Gdx.files.local(filePath);
		handle.writeString(gson.toJson(item) +"\r\n", true);
		System.out.println(gson.toJson(item));
		
	}
	
	
	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 0);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		this.stage.act();
		this.stage.draw();
		
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
