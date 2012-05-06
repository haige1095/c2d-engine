package info.u250.c2d.utils;

import info.u250.c2d.physical.box2d.Cb2World;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
/**
 * @author lycying@gmail.com
 */
public class Box2dlUtils {
	/** get the the box shape due to the Sprite object */
	public static Vector2 getBoxShapeVector(Sprite object){
		return new Vector2(object.getWidth()*object.getScaleX(),object.getHeight()*object.getScaleY()).mul(1/Cb2World.RADIO/2);
	}
	/** get the radius of the circle shape due to the Sprite object */
	public static float getCircleShapeRadius(Sprite object){
		return object.getWidth() * object.getScaleX()/Cb2World.RADIO/2;
	}
}
