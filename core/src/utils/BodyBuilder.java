package utils;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class BodyBuilder {
	
public static Body createBox(float x,float y,float width,float height,boolean isStatic, boolean isSensor, float angle, World world) {
		
		Body pBody;
		BodyDef def = new BodyDef();
		if (isStatic) {
			def.type = BodyDef.BodyType.StaticBody;
		} else {
			def.type= BodyDef.BodyType.DynamicBody;
		}
		def.position.set(new Vector2(x, y));
		
		def.fixedRotation = true;
		pBody = world.createBody(def);
		
		PolygonShape shape = new PolygonShape();
		shape.setAsBox(width / 2,  height / 2 );
		//System.out.println("box: " + def.position);
		
		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = shape;
		//fixtureDef.friction = friction;
		
		if (isSensor == true) {
			fixtureDef.isSensor = true;
		}
		
		Fixture fixture = pBody.createFixture(fixtureDef);
		pBody.setTransform(new Vector2(x,y), angle);
		
		shape.dispose();
		
		System.out.println(pBody);
		return pBody;
		
	}

}
