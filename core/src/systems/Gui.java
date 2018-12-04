package systems;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextArea;
import com.badlogic.gdx.scenes.scene2d.ui.TextTooltip;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import objects.Item;
import objects.Player;
import screens.GameScreen;

public class Gui {

	Stage stage;
	StringBuilder stringBuilder;
	BitmapFont font;
	Table table;
	Skin skin = new Skin(Gdx.files.internal("skins/plain-james/skin/plain-james-ui.json"));

	Player player;
	float fpss;
	ProgressBar hungerBar = new ProgressBar(0, 100, 1, false, skin);
	ProgressBar thirstBar = new ProgressBar(0, 100, 1, false, skin);
	Label fps;
	Texture tex;
	ImageButton button;
	InventorySystem inventory;
	Item a;
	Table table2, table3;
	boolean hidden;
	Window window;
	Item b;
	int i = 0;
	GameScreen gameScreen;
	ScrollPane scroll, infoScroll;
	Label label;
	TextArea infoArea;

	public Gui(final Player player, GameScreen gameScreen) {
		hidden = true;
		font = new BitmapFont();
		this.player = player;
		label = new Label("", skin);
		label.setFontScale(0.6f);
		stage = new Stage();
		table = new Table();
		infoArea = new TextArea(null, skin);
		infoScroll = new ScrollPane(label, skin);
		infoScroll.pack();
		infoScroll.setSize(400, 150);
		
		// table.debug();
		table.setFillParent(true);

		table3 = new Table();
		// table3.debug();
		window = new Window("Inventory", skin);
		window.setVisible(false);
		// window.setSize(1000,1000);
		
		scroll = new ScrollPane(table3, skin);
		scroll.layout();
		stringBuilder = new StringBuilder();
		
		Label hunger = new Label("Hunger: ", new Label.LabelStyle(font, Color.RED));
		Label thirst = new Label("Thirst: ", new Label.LabelStyle(font, Color.RED));
		fps = new Label("FPS: ", new Label.LabelStyle(font, Color.RED));
		hungerBar.setColor(Color.WHITE);
		stringBuilder.append(Gdx.graphics.getFramesPerSecond());
		fps.setText(" " + fpss);
		table.top().left();
		table.add(hunger);
		table.add(hungerBar);
		table.row();
		table.add(thirst);
		table.add(thirstBar);
		table.row();
		table.add(fps);
		
		
		stage.addActor(table);
		stage.addActor(window);
		stage.addActor(infoScroll);
		window.add(scroll);
		window.setFillParent(true);
		window.pack();
		a = new Item();
		this.gameScreen = gameScreen;
		infoArea.setDisabled(true);

	}

	public Stage getStage() {
		return this.stage;
	}

	public void listInventory() {
		System.out.println("Listing");
		if (hidden) {
			this.hidden = false;
			window.setVisible(true);

			for (i = 0; i < player.getInventory().getItems().size(); i++) {
				if (i % 10 == 0) {
					table3.row();
				}
				a = new Item();
				a = player.getInventory().getItem(i);
				tex = new Texture(Gdx.files.internal(a.getSprite()));
				button = new ImageButton(new TextureRegionDrawable(new TextureRegion(tex)));
				button.setUserObject(a);

				button.addListener(new ClickListener() {
					public void clicked(InputEvent event, float x, float y) {
						System.out.println(event.getListenerActor().getUserObject().toString());
						b = (Item) event.getListenerActor().getUserObject();
						Dialog dialog = new Dialog("What to do for " + b.name + "?", skin, "dialog") {
							public void result(Object obj) {
								System.out.println(obj.toString() + b.getDescription());

								if (obj.equals("drop")) {
									System.out.println(i);

									player.getInventory().removeItem(b);
									b.x = player.x;
									b.y = player.y;
									gameScreen.addDroppedItems(b);
									// table3.removeActor(button);
									window.setVisible(false);
								}

								if (obj.equals("examine")) {
									//label.appendText(b.toString() + "\n");
									label.setText(label.getText() + "\n" + b.toString());
									window.setVisible(false);

								}

							}
						};

						dialog.button("Drop", "drop");
						dialog.button("Examine", "examine");
						dialog.button("Use", "use");
						dialog.setPosition(stage.getWidth() / 2, stage.getHeight() / 2);
						window.add(dialog);

					}

				});

				table3.add(button);

			}
		} else {
			table3.clear();
			window.setVisible(false);
			this.hidden = true;
		}

	}

	public void update() {
		// hungerBar.setValue(player.getHunger().getStarvation());
		infoScroll.scrollTo(0, 0, 0, 0);
		fpss = Gdx.graphics.getFramesPerSecond();
		fps.setText(" " + fpss);

	}

}
