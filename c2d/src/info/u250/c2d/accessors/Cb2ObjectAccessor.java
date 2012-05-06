package info.u250.c2d.accessors;

import info.u250.c2d.physical.box2d.Cb2Object;
import aurelienribon.tweenengine.TweenAccessor;

public class Cb2ObjectAccessor implements TweenAccessor<Cb2Object> {

	public static final int POSITION_XY = 1;
	public static final int ROTATION = 2;
	
	@Override
	public int getValues(Cb2Object target, int tweenType, float[] returnValues) {

		switch (tweenType) {
			case POSITION_XY:
				returnValues[0] = target.object.getX();
				returnValues[1] = target.object.getY();
				return 2;

			case ROTATION: returnValues[0] = target.object.getRotation(); return 1;
			default: assert false; return -1;
		}
	
	}

	@Override
	public void setValues(Cb2Object target, int tweenType, float[] newValues) {

		switch (tweenType) {
			case POSITION_XY: target.setPosition(newValues[0], newValues[1]); break;
			case ROTATION: target.setRotation(newValues[0]); break;
			default: assert false;
		}
	
	}

}
