package info.u250.c2d.physical.box2d.loader.cbt.data;

import info.u250.c2d.physical.box2d.Cb2World;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.joints.PulleyJointDef;
/**
 * @author lycying@gmail.com
 */
public class PulleyJointData extends JointData {
	/** The first ground anchor in world coordinates. This point never moves. */
	public final Vector2 groundAnchorA = new Vector2(-1, 1);

	/** The second ground anchor in world coordinates. This point never moves. */
	public final Vector2 groundAnchorB = new Vector2(1, 1);

	/** The local anchor point relative to bodyA's origin. */
	public final Vector2 localAnchorA = new Vector2(-1, 0);

	/** The local anchor point relative to bodyB's origin. */
	public final Vector2 localAnchorB = new Vector2(1, 0);


	/** The pulley ratio, used to simulate a block-and-tackle. */
	public float ratio = 1;
	
	public void build(){
		PulleyJointDef def = new PulleyJointDef();
		
		def.initialize(bodyA.body, bodyB.body, 
				this.groundAnchorA.cpy().mul(1/Cb2World.RADIO),
				this.groundAnchorB.cpy().mul(1/Cb2World.RADIO),
				bodyA.body.getWorldPoint(getLocalPointWithAngle(this.localAnchorA,bodyA).mul(1/Cb2World.RADIO)),
				bodyB.body.getWorldPoint(getLocalPointWithAngle(this.localAnchorB,bodyA).mul(1/Cb2World.RADIO)),
				this.ratio);
		def.collideConnected = this.collideConnected;
		
		this.joint =  Cb2World.getInstance().world().createJoint(def);
	}

	@Override
	public void debug(ShapeRenderer render) {
		render.setColor(DebugColor.COLOR_PulleyJoint);
		render.begin(ShapeType.Line);
		if(null==this.joint){
			final Vector2 p1 =  bodyA.center.cpy().add(localAnchorA);
			final Vector2 p2 =  bodyB.center.cpy().add(localAnchorB);
			render.line(this.groundAnchorA.x, this.groundAnchorA.y, p1.x, p1.y);
			render.line(this.groundAnchorB.x, this.groundAnchorB.y, p2.x, p2.y);
			render.line(this.groundAnchorA.x, this.groundAnchorA.y, this.groundAnchorB.x, this.groundAnchorB.y);
		}else {
			Vector2 p1  = joint.getAnchorA().mul(Cb2World.RADIO);
			Vector2 p2  = joint.getAnchorB().mul(Cb2World.RADIO);
			render.line(this.groundAnchorA.x, this.groundAnchorA.y, p1.x, p1.y);
			render.line(this.groundAnchorB.x, this.groundAnchorB.y, p2.x, p2.y);
			render.line(this.groundAnchorA.x, this.groundAnchorA.y, this.groundAnchorB.x, this.groundAnchorB.y);
		}
		render.end();
	}

	@Override
	public boolean isFocus(Vector2 point) {
		return point.dst(this.localAnchorA.cpy().add(this.bodyA.center))<=5 
				|| point.dst(this.localAnchorB.cpy().add(this.bodyB.center)) <= 5;
	}
	
}
