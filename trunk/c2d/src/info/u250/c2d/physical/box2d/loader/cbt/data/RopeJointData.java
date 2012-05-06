package info.u250.c2d.physical.box2d.loader.cbt.data;

import info.u250.c2d.physical.box2d.Cb2World;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.joints.RopeJointDef;
/**
 * @author lycying@gmail.com
 */
public class RopeJointData extends JointData {
	/** The local anchor point relative to bodyA's origin. **/
	public final Vector2 localAnchorA = new Vector2(-1, 0);

	/** The local anchor point relative to bodyB's origin. **/
	public final Vector2 localAnchorB = new Vector2(1, 0);

	/** The maximum length of the rope. Warning: this must be larger than b2_linearSlop or the joint will have no effect. */
	public float maxLength = 0;
	
	public void build(){
		RopeJointDef def = new RopeJointDef();
		def.bodyA = bodyA.body;
		def.bodyB = bodyB.body;
		def.localAnchorA.set(getLocalPointWithAngle(this.localAnchorA,bodyA).mul(1/Cb2World.RADIO));
		def.localAnchorB.set(getLocalPointWithAngle(this.localAnchorB,bodyB).mul(1/Cb2World.RADIO));
		def.collideConnected = this.collideConnected;
		def.maxLength = this.maxLength/Cb2World.RADIO;
		
		this.joint =  Cb2World.getInstance().world().createJoint(def);
	}

	@Override
	public void debug(ShapeRenderer render) {
		render.setColor(DebugColor.COLOR_RopeJoint);
		render.begin(ShapeType.Line);
		if(null==this.joint){
			Vector2 p1 =  bodyA.center.cpy().add(localAnchorA);
			Vector2 p2 =  bodyB.center.cpy().add(localAnchorB);
			render.line(bodyA.center.x, bodyA.center.y, p1.x, p1.y);
			render.line(bodyB.center.x, bodyB.center.y, p2.x, p2.y);
			render.line(p1.x, p1.y, p2.x, p2.y);
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
		return point.dst(this.localAnchorA.cpy().add(this.bodyA.center))<=5 
				|| point.dst(this.localAnchorB.cpy().add(this.bodyB.center)) <= 5;
	}
}
