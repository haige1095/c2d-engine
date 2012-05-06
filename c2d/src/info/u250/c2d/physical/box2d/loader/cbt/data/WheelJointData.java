package info.u250.c2d.physical.box2d.loader.cbt.data;

import info.u250.c2d.physical.box2d.Cb2World;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.joints.WheelJointDef;
/**
 * @author lycying@gmail.com
 */
public class WheelJointData extends JointData {
	/** The local anchor point relative to body1's origin. **/
	public final Vector2 localAnchorA = new Vector2();

	/** The local translation axis in body1. **/
	public final Vector2 localAxisA = new Vector2(1, 0);

	/** Enable/disable the joint motor. **/
	public boolean enableMotor = false;

	/** The maximum motor torque, usually in N-m. */
	public float maxMotorTorque = 0;

	/** The desired motor speed in radians per second. */
	public float motorSpeed = 0;

	/** Suspension frequency, zero indicates no suspension */
	public float frequencyHz = 2;

	/** Suspension damping ratio, one indicates critical damping */
	public float dampingRatio = 0.7f;
	
	public void build(){
		WheelJointDef def = new WheelJointDef();
		def.initialize(bodyA.body, bodyB.body, 
				bodyA.body.getWorldPoint(this.localAnchorA.cpy().mul(1/Cb2World.RADIO)),
				this.localAxisA);
		def.collideConnected = this.collideConnected;
		def.dampingRatio = this.dampingRatio;
		def.frequencyHz = this.frequencyHz;
		def.enableMotor = this.enableMotor;
		def.maxMotorTorque = this.maxMotorTorque;
		def.motorSpeed = this.motorSpeed;
		
		this.joint =  Cb2World.getInstance().world().createJoint(def);
	}

	@Override
	public void debug(ShapeRenderer render) {
		render.setColor(DebugColor.COLOR_WheelJoint);
		render.begin(ShapeType.Line);
		if(null==this.joint){
			Vector2 p1 =  bodyA.center.cpy().add(localAnchorA);
			Vector2 p2 =  bodyA.center.cpy().add(localAnchorA);
			render.line(bodyA.center.x, bodyA.center.y, p1.x, p1.y);
			render.line(bodyB.center.x, bodyB.center.y, p2.x, p2.y);
		}else {
			Vector2 a1 = bodyA.body.getPosition().mul(Cb2World.RADIO);
			Vector2 a2 = bodyB.body.getPosition().mul(Cb2World.RADIO);
			Vector2 p1  = joint.getAnchorA().mul(Cb2World.RADIO);
			Vector2 p2  = joint.getAnchorB().mul(Cb2World.RADIO);
			render.line(p1.x, p1.y, a1.x, a1.y);
			render.line(p2.x, p2.y, a2.x, a2.y);
			render.line(p1.x, p1.y, p2.x, p2.y);
		}
		render.end();
	}

	@Override
	public boolean isFocus(Vector2 point) {
		return point.dst(this.localAnchorA.cpy().add(this.bodyA.center))<=5 ;
	}
	
}
