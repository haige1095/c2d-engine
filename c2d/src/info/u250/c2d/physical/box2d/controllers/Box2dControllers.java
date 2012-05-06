package info.u250.c2d.physical.box2d.controllers;

import info.u250.c2d.engine.service.Updatable;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.utils.Array;
/**
 * box2d controllers is a group of bodys that has the same action  besides the common 
 * physical actions . 
 * @author lycying@gmail.com
 */
public abstract class Box2dControllers implements Updatable{
	/** the controller bodys */
	protected Array<Body> controllersBody;
	/** put the body to the controller list */
	public void control(Body body) {
		if (controllersBody == null) {
			controllersBody = new Array<Body>();
		}
		// if the list does not already contain this body...
		if (controllersBody.contains(body, true) == false) {
			controllersBody.add(body);
		}
	}
	/** remove the body from the controller list */
	public void outControl(Body body) {
		if (controllersBody != null) {
			controllersBody.removeValue(body, true);
		}
	}
}
