package info.u250.c2d.accessors;

import info.u250.c2d.graphic.Mask;
import aurelienribon.tweenengine.TweenAccessor;
/**The mask accessor aim to make change of the mask transparency .
 * @author lycying@gmail.com
 */
public class MeshMaskAccessor implements TweenAccessor<Mask>{

	public final static int Transparency = 1;
	@Override
	public int getValues(Mask target, int tweenType, float[] returnValues) {
		switch(tweenType){
		case Transparency:
			returnValues[0] = target.getTransparency();
			return 1;
		default: assert false; return -1;
		}
	}

	@Override
	public void setValues(Mask target, int tweenType, float[] newValues) {
		switch(tweenType){
		case Transparency:
			target.setTransparency(newValues[0]);
		default: assert false;
		}
	}

}
