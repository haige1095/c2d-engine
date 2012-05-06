package info.u250.c2d.graphic.surfaces.data;

import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

public class SurfaceData {
	public String name ="";
	/**the points of the mesh*/
	public Array<Vector2> points = new Array<Vector2>();
	/**the texture which used to fill the mesh */
	public String texture = "";
	/**the drawing method 
	 * @see {@link GL10#GL_TRIANGLE_FAN}*/
	public int primitiveType  = GL10.GL_TRIANGLE_FAN;
}
