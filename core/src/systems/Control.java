package systems;
import java.awt.Color;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.World;


import map.Chunk;
import map.Mapper;
import map.Tile;
import objects.Item;
import objects.Player;
import screens.GameScreen;

public class Control extends InputAdapter implements InputProcessor {

	public boolean up, down, left, right;
	public boolean leftMouseBtn, rightMouseBtn;
	public boolean processedClick;
	public Vector2 mouseClickPos = new Vector2();
	public Vector2 mapClickPos = new Vector2();
	int screenWidth, screenHeight;
	public OrthographicCamera camera;
	public Player player;
	public World world;
	public Mapper testMap;
	public Gui gui;
	ItemSystem itemSystem;
	
	public Control(int screenWidth, int screenHeight, OrthographicCamera camera, Player player, World world, Mapper testMap, Gui gui) {
		this.camera = camera;
		this.screenWidth = screenWidth;
		this.screenHeight = screenHeight;
		this.player = player;
		this.world = world;
		this.testMap = testMap;
		this.gui = gui;
		this.itemSystem = new ItemSystem();
	}
	
	private void setMouseClickedPos(int screenX, int screenY) {
		mouseClickPos.set(screenX, screenHeight - screenY);
		mapClickPos.set(getMapCoords(mouseClickPos));
		System.out.println("Mouse: "+ mouseClickPos+ ", Map: " +getMapCoords(mouseClickPos));
		
		Vector2 v2 = getMapCoords(mouseClickPos);
		
		int x = (int) Math.floor(v2.x);
		int y = (int) Math.floor(v2.y);

		float maxDist = 1.5f;
		float dist = getCursorDistance();

		System.out.println("Distance: " +dist);
		Tile remove = testMap.chunk.getTile(x, y);
		
		if (dist <= maxDist) {
			player.swing(Gdx.graphics.getDeltaTime());
			if (remove.getTileType() != null) {
				player.getInventory2().addItem(itemSystem.getItemByName(remove.getName()));
				System.out.println("Got one " + remove.getTileType());
				remove.setNull((int)remove.x,(int)remove.y);
			}

			
			
		}
		
	}
	
	public Vector2 getMapCoords(Vector2 mouseCoords) {
		Vector3 v3 = new Vector3(mouseCoords.x, screenHeight - mouseCoords.y, 0);
		this.camera.unproject(v3);
		return new Vector2 (v3.x, v3.y);
	}
	
	public float getCursorDistance() {
		Vector2 v1 = new Vector2(player.body.getPosition().x, player.body.getPosition().y);
		Vector2 v3 = getMapCoords(mouseClickPos);

		float distSqr = v1.dst2(v3);
		return distSqr;
	}
	
    @Override
    public boolean keyDown(int keyCode) {
        switch (keyCode) {
            case Keys.DOWN:
                down = true;
                break;
            case Keys.UP:
                up = true;
                break;
            case Keys.LEFT:
                left = true;
                break;
            case Keys.RIGHT:
                right = true;
                break;
            case Keys.W:
                up = true;
                break;
            case Keys.A:
                left = true;
                break;
            case Keys.S:
                down = true;
                break;
            case Keys.D:
                right = true;
                break;
  
        }

        return false;
}

	
    @Override
    public boolean keyUp(int keycode) {
        switch (keycode) {
            case Keys.DOWN:
                down = false;
                break;
            case Keys.UP:
                up = false;
                break;
            case Keys.LEFT:
                left = false;
                break;
            case Keys.RIGHT:
                right = false;
                break;
            case Keys.W:
                up = false;
                break;
            case Keys.A:
                left = false;
                break;
            case Keys.S:
                down = false;
                break;
            case Keys.D:
                right = false;
                break;
            case Keys.ESCAPE:
                Gdx.app.exit();
                break;
            case Keys.BACKSPACE:
                break;
        }
        return false;
}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {

		if (pointer == 0 && button == 0) {

			
		}

		
		return false;
	}
    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        if(pointer == 0 && button == 0){
            leftMouseBtn = false; 
            processedClick = false;
        } else if (pointer == 0 && button == 0){
            rightMouseBtn = false; 
        }
    
        setMouseClickedPos(screenX, screenY);
        return false;
}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		 setMouseClickedPos(screenX, screenY);
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
			camera.zoom += 0.1f;
		} else {
			camera.zoom -= 0.1f;
		}
		
		return false;
	}

}
