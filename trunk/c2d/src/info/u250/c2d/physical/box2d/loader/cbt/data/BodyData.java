package info.u250.c2d.physical.box2d.loader.cbt.data;

import info.u250.c2d.engine.service.Disposable;
import info.u250.c2d.physical.box2d.Cb2Object;

import java.util.UUID;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
/**
 * @author lycying@gmail.com
 */
public abstract class BodyData implements Disposable{
	public String name;
	public BodyData(){
		name = UUID.randomUUID().toString();
	}
	public String res = "";
	public String data = "";
	
	public float density = Cb2Object.DEFAULT_density;
	public float friction = Cb2Object.DEFAULT_density;
	public float restitution = Cb2Object.DEFAULT_restitution;
	
	
	public int filter_categoryBits = 0x0001 ;
	public int filter_groupIndex = 0;
	public int filter_maskBits = -1;
	public boolean isSensor = false;
	public boolean isDynamic = true;
	/** if its true , the {@link #isDynamic} is no use at all*/
	public boolean isKinematic = false;
	
	public float angle = 0;
	public final Vector2 center = new Vector2();
	
	public abstract boolean isFocus(Vector2 point);
	public abstract void debug(ShapeRenderer render);
	
	public void translate(float x, float y) {
		this.center.add(x,y);
	}
	
	public Body body;
	public abstract void build();
	
	@Override
	public void dispose() {
		this.body = null;
	}

}
