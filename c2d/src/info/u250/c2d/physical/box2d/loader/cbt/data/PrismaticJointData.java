package info.u250.c2d.physical.box2d.loader.cbt.data;

import info.u250.c2d.physical.box2d.Cb2World;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.joints.PrismaticJointDef;
/**
 * @author lycying@gmail.com
 */
public class PrismaticJointData extends JointData {
	
	/** The local anchor point relative to body1's origin. */
	public final Vector2 localAnchorA = new Vector2();

	/** The local translation axis in body1. */
	public final Vector2 localAxisA = new Vector2(1, 0);

	/** Enable/disable the joint limit. */
	public boolean enableLimit = false;

	/** The lower translation limit, usually in meters. */
	public float lowerTranslation = 0;

	/** The upper translation limit, usually in meters. */
	public float upperTranslation = 0;

	/** Enable/disable the joint motor. */
	public boolean enableMotor = false;

	/** The maximum motor torque, usually in N-m. */
	public float maxMotorForce = 0;

	/** The desired motor speed in radians per second. */
	public float motorSpeed = 0;
	
	
	public void build(){
		PrismaticJointDef def = new PrismaticJointDef();
		def.initialize(bodyA.body, bodyB.body, 
				bodyA.body.getWorldPoint(getLocalPointWithAngle(this.localAnchorA,bodyA).mul(1/Cb2World.RADIO)),
				localAxisA//ignore it , it's no use
				);
		def.collideConnected = this.collideConnected;
		def.enableMotor = this.enableMotor;
		def.motorSpeed = this.motorSpeed;
		def.maxMotorForce = this.maxMotorForce;
		def.enableLimit = this.enableLimit;
		def.upperTranslation = this.upperTranslation/Cb2World.RADIO;
		def.lowerTranslation = this.lowerTranslation/Cb2World.RADIO;
		def.localAxisA.set(this.localAxisA);//make this in/use~
		
		this.joint =  Cb2World.getInstance().world().createJoint(def);
	}

	@Override
	public void debug(ShapeRenderer render) {
		render.setColor(DebugColor.COLOR_PrismaticJoint);
		render.begin(ShapeType.Line);
		if(null==this.joint){
			Vector2 p =  bodyA.center.cpy().add(localAnchorA);
			render.line(bodyA.center.x, bodyA.center.y, p.x, p.y);
			render.line(bodyB.center.x, bodyB.center.y, p.x, p.y);
		}else {
			Vector2 p1 = bodyA.body.getPosition().mul(Cb2World.RADIO);
			Vector2 p2 = bodyB.body.getPosition().mul(Cb2World.RADIO);
			Vector2 p  = joint.getAnchorA().mul(Cb2World.RADIO);
			render.line(p1.x, p1.y, p.x, p.y);
			render.line(p2.x, p2.y, p.x, p.y);
		}
		render.end();
	}

	@Override
	public boolean isFocus(Vector2 point) {
		return point.dst(this.localAnchorA.cpy().add(this.bodyA.center))<=5 ;
	}
}
