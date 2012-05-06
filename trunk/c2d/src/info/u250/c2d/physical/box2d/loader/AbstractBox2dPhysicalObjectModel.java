package info.u250.c2d.physical.box2d.loader;

import info.u250.c2d.physical.box2d.Cb2Object;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.Shape.Type;
import com.badlogic.gdx.utils.Array;
/**the default physical model by physical loader , 
 * include just every thing of the {@link BodyDef}  and {@link FixtureDef}  , but you can change it after you build the body 
 * 
 * @author lycying@gmail.com*/
public abstract class AbstractBox2dPhysicalObjectModel {
	public Vector2 anchorpoint = new Vector2();
	
	public float density = Cb2Object.DEFAULT_density;
	public float friction = Cb2Object.DEFAULT_density;
	public float restitution = Cb2Object.DEFAULT_restitution;
	
	public int filter_categoryBits = 0x0001 ;
	public int filter_groupIndex = 0;
	public int filter_maskBits = -1;
	public boolean isSensor = false;
	public Type fixture_type;
	
	public Array<Vector2[]> polygons = new Array<Vector2[]>();
	
	/**some resources need build params  but do not know how many params ,so ... is needed
	 * */
	public abstract Body build(float width,float height);
	
}
