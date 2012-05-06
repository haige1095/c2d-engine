package info.u250.c2d.physical.box2d.loader.cbt.data;

import java.util.UUID;

import info.u250.c2d.engine.service.Disposable;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Joint;

/**
 * @author lycying@gmail.com
 */
public abstract class JointData implements Disposable{
	public String name;
	public JointData(){
		name = UUID.randomUUID().toString();
	}
	public BodyData bodyA = null;
	public BodyData bodyB = null;
	public boolean collideConnected = false;
	
	public abstract void build();
	
	public Joint joint;
	
	public abstract void debug(ShapeRenderer render);
	public abstract boolean isFocus(Vector2 point);
	
	
	public void dispose(){
		this.joint = null;
	}
	protected Vector2 getLocalPointWithAngle(final Vector2 localAnchor,final BodyData bodyData){
		final float len = localAnchor.len();
		final float angle = localAnchor.angle() - bodyData.angle;
		return new Vector2(MathUtils.cosDeg(angle),MathUtils.sinDeg(angle)).mul(len);
	}
}
