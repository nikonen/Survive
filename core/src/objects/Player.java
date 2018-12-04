package objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;

import screens.GameScreen;

import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

import systems.Control;
import systems.InventorySystem;
import systems.InventorySystem2;

public class Player extends Sprite {

	public float x, y, size, angle, dirX, dirY;
	public Body body;
	public float elapsedTime;
	public SpriteBatch batch;
	public Texture tex;
	public Sprite sprite;
	public TextureRegion currentFrame;
	public TextureAtlas anim;
	public Animation<TextureRegion> swingingAnim;
	public boolean swinging;
	public float stateTime = 0; // animation state time
	public InventorySystem inventory;
	public InventorySystem2 inventory2;
	public Item onHand;

	public Player(World world, SpriteBatch batch) {
		currentFrame = new TextureRegion();
		swinging = false;
		anim = new TextureAtlas(Gdx.files.internal("swinging.txt"));
		swingingAnim = new Animation<TextureRegion>(0.15f, anim.findRegions("swinging"), PlayMode.NORMAL);

		this.batch = batch;
		this.angle = 0;
		this.tex = new Texture(Gdx.files.internal("player.png"));

		this.sprite = new Sprite(this.tex, 0,0, 32, 32);
		System.out.println(sprite.getWidth() + " - - - -" + sprite.getHeight());
		// sprite.setSize(sprite.getWidth() * 0.01f, sprite.getHeight() * 0.01f);

		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyType.DynamicBody;
		bodyDef.position.x = 20;
		bodyDef.position.y = 2;
		bodyDef.linearDamping = 0.1f;
		bodyDef.angularDamping = 0.5f;

		this.body = world.createBody(bodyDef);

		PolygonShape shape = new PolygonShape();

		shape.setAsBox(0.3f, 0.5f); // half meters???

		FixtureDef def = new FixtureDef();
		def.shape = shape;

		Fixture fixture = body.createFixture(def);
		body.setTransform(new Vector2(x, y), angle);
		shape.dispose();

		inventory = new InventorySystem();
		inventory2 = new InventorySystem2();
		this.onHand = null;

	}

	public void swing(float delta) {

		stateTime = 0;
		swinging = true;

	}

	public InventorySystem getInventory() {
		return inventory;
	}
	
	public InventorySystem2 getInventory2() {
		System.out.println("player get inv: " + this.inventory2.getItems().size());
		return this.inventory2;
	}

	public void update(float delta, Camera camera, Control control) {

		dirX = 0;
		dirY = 0;

		if (control.down)
			dirY = -1;
		if (control.up)
			dirY = 1;
		if (control.left)
			dirX = -1;
		if (control.right)
			dirX = 1;

		x += dirX * 5;
		y += dirY * 5;

		body.setLinearVelocity(dirX * 5, dirY * 5);
		x = body.getPosition().x - 0.5f;
		y = body.getPosition().y - 0.5f;

		Vector3 worldCoords = GameScreen.camera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));
		Vector2 vec2 = new Vector2(this.body.getWorldCenter().x - worldCoords.x,
				this.body.getWorldCenter().y - worldCoords.y);
		vec2.set(vec2.x, vec2.y);

		float angle = (float) Math.atan2(this.body.getWorldCenter().y - worldCoords.y,
				this.body.getWorldCenter().x - worldCoords.x);
		this.angle = angle;

		this.body.setTransform(new Vector2(this.body.getPosition().x, this.body.getPosition().y), angle);

		setPosition(body.getPosition().x, body.getPosition().y);

		sprite.setRotation(body.getAngle() * MathUtils.radiansToDegrees);
		sprite.setPosition(body.getPosition().x - sprite.getWidth() / 2, body.getPosition().y - sprite.getHeight() / 2);
		sprite.setSize(1f, 1f);
		sprite.setOriginCenter();

		stateTime += Gdx.graphics.getDeltaTime();
		currentFrame = swingingAnim.getKeyFrame(stateTime, false);
		batch.draw(currentFrame, body.getPosition().x - 0.5f, body.getPosition().y - 0.5f, sprite.getWidth() / 2,
				sprite.getHeight() / 2, 1f, 1f, 1f, 1f, body.getAngle() * MathUtils.radiansToDegrees);

	}

}
