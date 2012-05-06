package info.u250.c2d.physical.box2d.controllers;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
/**
 * Custom gravity use {@link Box2dControllers}
 * @author lycying@gmail.com
 */
public class GravityController extends Box2dControllers {
	
	private Vector2 mControllerGravity;
	private static final float GRAVITY_CONSTANT = 9.8f;

	public GravityController() {
		mControllerGravity = new Vector2();
		mControllerGravity.x = 0;
		mControllerGravity.y = GRAVITY_CONSTANT;
	}

	/**
	 * This sets the controller gravity. Invert the signs if you want to make it
	 * different from the world gravity
	 * @param gravityX
	 * @param gravityY
	 */
	public void setGravity(float gravityX, float gravityY) {
		mControllerGravity.x = gravityX;
		mControllerGravity.y = gravityY;
	}

	@Override
	public void update(float delta) {
		if (controllersBody != null) {
			for (int i = 0; i < controllersBody.size; i++) {
				Body body = controllersBody.get(i);
				float mass = body.getMass();
				float forceX = mass * mControllerGravity.x;
				float forceY = mass * mControllerGravity.y;
				Vector2 pos = body.getPosition();
				float pointX = pos.x;
				float pointY = pos.y;
				body.applyForce(forceX, forceY, pointX, pointY);
			}
		}
	}

	
}
