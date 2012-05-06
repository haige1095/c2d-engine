package info.u250.c2d.physical.box2d.loader.cbt.data;

import info.u250.c2d.physical.box2d.Cb2World;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.joints.DistanceJointDef;
/**
 * @author lycying@gmail.com
 */
public class DistanceJointData extends JointData {
	/** The local anchor point relative to body1's origin. */
	public final Vector2 localAnchorA = new Vector2();

	/** The local anchor point relative to body2's origin. */
	public final Vector2 localAnchorB = new Vector2();

	/** The mass-spring-damper frequency in Hertz. */
	public float frequencyHz = 0;

	/** The damping ratio. 0 = no damping, 1 = critical damping. */
	public float dampingRatio = 0;
	
	public void build(){
		DistanceJointDef def = new DistanceJointDef();
		def.initialize(bodyA.body, bodyB.body, 
				bodyA.body.getWorldPoint(getLocalPointWithAngle(this.localAnchorA,bodyA).mul(1/Cb2World.RADIO)),
				bodyB.body.getWorldPoint(getLocalPointWithAngle(this.localAnchorB,bodyB).mul(1/Cb2World.RADIO)));
		def.collideConnected = this.collideConnected;
		def.dampingRatio = this.dampingRatio;
		def.frequencyHz = this.frequencyHz;
		
		this.joint =  Cb2World.getInstance().world().createJoint(def);
	}

	@Override
	public void debug(ShapeRenderer render) {
		if(null==this.joint)this.debugX(render, bodyA.center.cpy().add(localAnchorA), bodyB.center.cpy().add(localAnchorB));
		else this.debugX(render, joint.getAnchorA().mul(Cb2World.RADIO), joint.getAnchorB().mul(Cb2World.RADIO));
	}
	private void debugX(ShapeRenderer render,Vector2 pointA,Vector2 pointB){
		render.setColor(DebugColor.COLOR_DistanceJoint);
		render.begin(ShapeType.Line);
		render.line(pointA.x, pointA.y, pointB.x, pointB.y);
		render.end();
	}

	@Override
	public boolean isFocus(Vector2 point) {
		return point.dst(this.localAnchorA.cpy().add(this.bodyA.center))<=5 
				|| point.dst(this.localAnchorB.cpy().add(this.bodyB.center)) <= 5;
	}
}
