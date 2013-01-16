package info.u250.c2d.box2deditor.gdx.support;

import info.u250.c2d.box2d.Box2dObject;
import info.u250.c2d.box2d.model.b2BodyDefModel;
import info.u250.c2d.box2d.model.b2JointDefModel;
import info.u250.c2d.box2d.model.fixture.b2CircleFixtureDefModel;
import info.u250.c2d.box2d.model.fixture.b2RectangleFixtureDefModel;
import info.u250.c2d.box2d.model.joint.b2DistanceJointDefModel;
import info.u250.c2d.box2d.model.joint.b2GearJointDefModel;
import info.u250.c2d.box2d.model.joint.b2PulleyJointDefModel;
import info.u250.c2d.box2d.model.joint.b2RopeJointDefModel;
import info.u250.c2d.box2deditor.adapter.DistanceJointDefModel;
import info.u250.c2d.box2deditor.adapter.FrictionJointDefModel;
import info.u250.c2d.box2deditor.adapter.PolygonFixtureDefModel;
import info.u250.c2d.box2deditor.adapter.PrismaticJointDefModel;
import info.u250.c2d.box2deditor.adapter.PulleyJointDefModel;
import info.u250.c2d.box2deditor.adapter.RevoluteJointDefModel;
import info.u250.c2d.box2deditor.adapter.RopeJointDefModel;
import info.u250.c2d.box2deditor.adapter.WeldJointDefModel;
import info.u250.c2d.box2deditor.adapter.WheelJointDefModel;
import info.u250.c2d.engine.Engine;
import info.u250.c2d.utils.Mathutils;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.EarClippingTriangulator;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public class Geometry {
	public static final Color COLOR_DistanceJoint = new Color(Color.RED);
	public static final Color COLOR_PrismaticJoint = new Color(Color.GREEN);
	public static final Color COLOR_RevoluteJoint = new Color(Color.MAGENTA);
	public static final Color COLOR_RopeJoint = new Color(Color.ORANGE);
	public static final Color COLOR_WeldJoint = new Color(Color.YELLOW);
	public static final Color COLOR_WheelJoint = new Color(Color.WHITE);
	public static final Color COLOR_PulleyJoint = new Color(Color.CYAN);
	public static final Color COLOR_FrictionJoint = new Color(Color.GRAY);
	
	private static final Color CIRCLE = new Color(1, 0.5f, 1, 0.4f);
	private static final Color CIRCLE_BORDER = new Color(1, 0.5f, 1, 0.8f);
	private static final Color LINE = new Color(1f, 0.5f, 1f, 1f);
	private static final Color BOX = new Color(0.5f, 1, 1f, 0.4f);
	private static final Color BOX_BORDER = new Color(0.1f, 0.8f, 1f, 0.9f);
	private static final Color POLYGON = new Color(0f, 1, 0.1f, 0.2f);
	private static final Color POLYGON_BORDER = new Color(0f, 0f, 0f, 1f);
	
	private static final Color FocusColor = new Color(0,0,1f,0.3f);
	private static final Color FocusColor_BORDER = new Color(0,0,1,0.8f);
	private static final EarClippingTriangulator earClippingTriangulator = new EarClippingTriangulator();
	public static void renderPolygon(PolygonFixtureDefModel polygon,Vector2 position,float angle,boolean focus){
		ShapeRenderer render = Engine.getShapeRenderer();
		//rotate
		final float cos = MathUtils.cosDeg(angle);
		final float sin = MathUtils.sinDeg(angle);
		
		final List<Vector2> vertices = new ArrayList<Vector2>();
		// rotate if needed
		for(Vector2 v:polygon.polygon){
			if (angle != 0) {
				vertices.add(new Vector2(cos * v.x - sin * v.y,sin * v.x + cos * v.y).add(position));
			}else{
				vertices.add(v.cpy().add(position));
			}
		}
		
		List<Vector2> temp = earClippingTriangulator.computeTriangles(vertices);
		for(int i=0;i<temp.size();i=i+3){
			render.setColor(focus?FocusColor:POLYGON);
			render.begin(ShapeType.FilledTriangle);
			render.filledTriangle(temp.get(i).x, temp.get(i).y, temp.get(i+1).x, temp.get(i+1).y, temp.get(i+2).x, temp.get(i+2).y);
			render.end();
			render.setColor(Color.CYAN);
			render.begin(ShapeType.Triangle);
			render.triangle(temp.get(i).x, temp.get(i).y, temp.get(i+1).x, temp.get(i+1).y, temp.get(i+2).x, temp.get(i+2).y);
			render.end();
		}
		
		render.setColor(focus?FocusColor_BORDER:POLYGON_BORDER);
		render.begin(ShapeType.Line);
		Vector2 lv = new Vector2();
		Vector2 f  = new Vector2();
		for (int i = 0; i < vertices.size(); i++) {
			Vector2 v = vertices.get(i);
			if (i == 0) {
				lv.set(v);
				f.set(v);
				continue;
			}
			render.line(lv.x, lv.y, v.x, v.y);
			lv.set(v);
		}
		render.line(f.x, f.y, lv.x, lv.y);
		render.end();
		
	}
	public static void renderBox(b2RectangleFixtureDefModel box,Vector2 position,float angle,boolean focus){
		ShapeRenderer render = Engine.getShapeRenderer();
		
		float rect_angle = new Vector2(box.width,box.height).angle();
		float half_diagonal = (float)Math.sqrt(box.width*box.width+box.height*box.height)/2;
		
		render.setColor(focus?FocusColor:BOX);
		render.begin(ShapeType.FilledTriangle);
		render.filledTriangle(
				position.x+MathUtils.cosDeg(180+rect_angle+angle)*half_diagonal, 
				position.y+MathUtils.sinDeg(180+rect_angle+angle)*half_diagonal, 
				position.x+MathUtils.cosDeg(-rect_angle+angle)*half_diagonal, 
				position.y+MathUtils.sinDeg(-rect_angle+angle)*half_diagonal, 
				position.x+MathUtils.cosDeg(rect_angle+angle)*half_diagonal, 
				position.y+MathUtils.sinDeg(rect_angle+angle)*half_diagonal);
		render.filledTriangle(
				position.x+MathUtils.cosDeg(180-rect_angle+angle)*half_diagonal, 
				position.y+MathUtils.sinDeg(180-rect_angle+angle)*half_diagonal, 
				position.x+MathUtils.cosDeg(180+rect_angle+angle)*half_diagonal, 
				position.y+MathUtils.sinDeg(180+rect_angle+angle)*half_diagonal, 
				position.x+MathUtils.cosDeg(rect_angle+angle)*half_diagonal, 
				position.y+MathUtils.sinDeg(rect_angle+angle)*half_diagonal);
		render.end();
		render.setColor(focus?FocusColor_BORDER:BOX_BORDER);
		render.begin(ShapeType.Triangle);
		render.triangle(
				position.x+MathUtils.cosDeg(180+rect_angle+angle)*half_diagonal, 
				position.y+MathUtils.sinDeg(180+rect_angle+angle)*half_diagonal, 
				position.x+MathUtils.cosDeg(-rect_angle+angle)*half_diagonal, 
				position.y+MathUtils.sinDeg(-rect_angle+angle)*half_diagonal, 
				position.x+MathUtils.cosDeg(rect_angle+angle)*half_diagonal, 
				position.y+MathUtils.sinDeg(rect_angle+angle)*half_diagonal);
		render.triangle(
				position.x+MathUtils.cosDeg(180-rect_angle+angle)*half_diagonal, 
				position.y+MathUtils.sinDeg(180-rect_angle+angle)*half_diagonal, 
				position.x+MathUtils.cosDeg(180+rect_angle+angle)*half_diagonal, 
				position.y+MathUtils.sinDeg(180+rect_angle+angle)*half_diagonal, 
				position.x+MathUtils.cosDeg(rect_angle+angle)*half_diagonal, 
				position.y+MathUtils.sinDeg(rect_angle+angle)*half_diagonal);
		render.end();
	}
	
	public static void renderCircle(b2CircleFixtureDefModel circle,Vector2 position,float angle,boolean focus){
		ShapeRenderer render = Engine.getShapeRenderer();
		render.setColor(focus?FocusColor:CIRCLE);
		render.begin(ShapeType.FilledCircle);
		render.filledCircle(position.x, position.y, circle.radius);
		render.end();
		render.setColor(focus?FocusColor_BORDER:CIRCLE_BORDER);
		render.begin(ShapeType.Circle);
		render.circle(position.x, position.y, circle.radius);
		render.end();
		render.setColor(LINE);
		render.begin(ShapeType.Line);
		render.line(
				position.x, position.y, 
				position.x+circle.radius*MathUtils.cosDeg(angle),
				position.y+circle.radius*MathUtils.sinDeg(angle));
		render.end();
	}
	public static void ajustJoint(b2JointDefModel object){
		try{
			if(object instanceof WeldJointDefModel){
				WeldJointDefModel model = WeldJointDefModel.class.cast(object);
				model.localAnchorA.set(model.bodyA.body.getLocalPoint(model.anchor.cpy().div(Box2dObject.RADIO)).mul(Box2dObject.RADIO));
				model.localAnchorB.set(model.bodyB.body.getLocalPoint(model.anchor.cpy().div(Box2dObject.RADIO)).mul(Box2dObject.RADIO));
				model.referenceDegrees = model.bodyB.degrees - model.bodyA.degrees;
			}else if(object instanceof RevoluteJointDefModel){
				RevoluteJointDefModel model = RevoluteJointDefModel.class.cast(object);
				if(model.useBodyACenter){
					model.anchor.set(model.bodyA.position);
				}else{
					if(model.useBodyBCenter){
						model.anchor.set(model.bodyB.position);
					}
				}
				model.localAnchorA.set(model.bodyA.body.getLocalPoint(model.anchor.cpy().div(Box2dObject.RADIO)).mul(Box2dObject.RADIO));
				model.localAnchorB.set(model.bodyB.body.getLocalPoint(model.anchor.cpy().div(Box2dObject.RADIO)).mul(Box2dObject.RADIO));
				model.referenceDegrees = model.bodyB.degrees - model.bodyA.degrees;
			}else if(object instanceof PrismaticJointDefModel){
				PrismaticJointDefModel model = PrismaticJointDefModel.class.cast(object);
				if(model.useBodyACenter){
					model.anchor.set(model.bodyA.position);
				}else{
					if(model.useBodyBCenter){
						model.anchor.set(model.bodyB.position);
					}
				}
				if(model.useABCenterLine){
					model.localAxisA.set(model.bodyB.position).sub(model.bodyA.position);
					model.localAxisA.div(model.localAxisA.len());
				}
				
				model.localAnchorA.set(model.bodyA.body.getLocalPoint(model.anchor.cpy().div(Box2dObject.RADIO)).mul(Box2dObject.RADIO));
				model.localAnchorB.set(model.bodyB.body.getLocalPoint(model.anchor.cpy().div(Box2dObject.RADIO)).mul(Box2dObject.RADIO));
				model.referenceDegrees = model.bodyB.degrees - model.bodyA.degrees;
			}else if(object instanceof FrictionJointDefModel){
				FrictionJointDefModel model = FrictionJointDefModel.class.cast(object);
				if(model.useBodyACenter){
					model.anchor.set(model.bodyA.position);
				}else{
					if(model.useBodyBCenter){
						model.anchor.set(model.bodyB.position);
					}
				}
				model.localAnchorA.set(model.bodyA.body.getLocalPoint(model.anchor.cpy().div(Box2dObject.RADIO)).mul(Box2dObject.RADIO));
				model.localAnchorB.set(model.bodyB.body.getLocalPoint(model.anchor.cpy().div(Box2dObject.RADIO)).mul(Box2dObject.RADIO));
			}else if(object instanceof WheelJointDefModel){
				WheelJointDefModel model = WheelJointDefModel.class.cast(object);
				if(model.useBodyACenter){
					model.anchor.set(model.bodyA.position);
				}else{
					if(model.useBodyBCenter){
						model.anchor.set(model.bodyB.position);
					}
				}
				if(model.useABCenterLine){
					model.localAxisA.set(model.bodyB.position).sub(model.bodyA.position);
				}
				model.localAxisA.div(model.localAxisA.len());
				model.localAnchorA.set(model.bodyA.body.getLocalPoint(model.anchor.cpy().div(Box2dObject.RADIO)).mul(Box2dObject.RADIO));
				model.localAnchorB.set(model.bodyB.body.getLocalPoint(model.anchor.cpy().div(Box2dObject.RADIO)).mul(Box2dObject.RADIO));
			}else if(object instanceof PulleyJointDefModel){
				PulleyJointDefModel model = PulleyJointDefModel.class.cast(object);
				if(model.setBodyAZero){
					model.localAnchorA.set(0, 0);
				}
				if(model.setBodyBZero){
					model.localAnchorB.set(0, 0);
				}
				final Vector2 worldA = model.bodyA.body.getWorldPoint(model.localAnchorA.cpy().div(Box2dObject.RADIO)).mul(Box2dObject.RADIO);
				final Vector2 worldB = model.bodyB.body.getWorldPoint(model.localAnchorB.cpy().div(Box2dObject.RADIO)).mul(Box2dObject.RADIO);
				if(model.groundAAlignAnchorA){
					model.groundAnchorA.x = worldA.x ;
				}
				if(model.groundBAlignAnchorB){
					model.groundAnchorB.x = worldB.x ;
				}
				model.lengthA = worldA.dst(model.groundAnchorA);
				model.lengthB = worldB.dst(model.groundAnchorB);
			}else if(object instanceof RopeJointDefModel){
				RopeJointDefModel model = RopeJointDefModel.class.cast(object);
				if(model.setBodyAZero){
					model.localAnchorA.set(0, 0);
				}
				if(model.setBodyBZero){
					model.localAnchorB.set(0, 0);
				}
				if(model.autoCalculateLength){
					model.maxLength = 
							model.bodyA.body.getWorldPoint(model.localAnchorA.cpy().div(Box2dObject.RADIO)).sub(
									model.bodyB.body.getWorldPoint(model.localAnchorB.cpy().div(Box2dObject.RADIO))
									).mul(Box2dObject.RADIO).len();
				}
			}else if(object instanceof DistanceJointDefModel){
				DistanceJointDefModel model = DistanceJointDefModel.class.cast(object);
				if(model.setBodyAZero){
					model.localAnchorA.set(0, 0);
				}
				if(model.setBodyBZero){
					model.localAnchorB.set(0, 0);
				}
				if(model.autoCalculateLength){
					model.length = 
							model.bodyA.body.getWorldPoint(model.localAnchorA.cpy().div(Box2dObject.RADIO)).sub(
									model.bodyB.body.getWorldPoint(model.localAnchorB.cpy().div(Box2dObject.RADIO))
									).mul(Box2dObject.RADIO).len();
				}
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
		
	}
	public static void splitPolygon(PolygonFixtureDefModel object){
		object.vertices.clear();
		List<Vector2> temp = earClippingTriangulator.computeTriangles(object.polygon);
		for(int i=0;i<temp.size();i=i+3){
			Vector2[] vv = new Vector2[3];
			vv[0] = temp.get(i);
			vv[1] = temp.get(i+1);
			vv[2] = temp.get(i+2);
			if (Mathutils.isClockwise(vv[0], vv[1], vv[2])) {
				Vector2 vt = vv[0];
				vv[0] = vv[2];
				vv[2] = vt;
			}
			object.vertices.add(vv);
		}
	}
	
	public static void renderJoint(b2JointDefModel b2Joint){
		if(b2Joint.bodyA==null || b2Joint.bodyB==null)return;
		if(b2Joint.bodyA.body==null || b2Joint.bodyB.body==null)return;
		
		ShapeRenderer render = Engine.getShapeRenderer();
		if(b2Joint instanceof b2DistanceJointDefModel){
			final b2DistanceJointDefModel obj = b2DistanceJointDefModel.class.cast(b2Joint);
			final Vector2 p1 = worldPoint(obj.bodyA,obj.localAnchorA);
			final Vector2 p2 = worldPoint(obj.bodyB,obj.localAnchorB);
			render.begin(ShapeType.Line);
			render.line(p1.x, p1.y, p2.x, p2.y);
			render.end();
		}else if(b2Joint instanceof b2PulleyJointDefModel){
			final b2PulleyJointDefModel obj = b2PulleyJointDefModel.class.cast(b2Joint);
			Vector2 s1 = obj.groundAnchorA;
			Vector2 s2 = obj.groundAnchorB;
			final Vector2 p1 = worldPoint(obj.bodyA,obj.localAnchorA);
			final Vector2 p2 = worldPoint(obj.bodyB,obj.localAnchorB);
			render.begin(ShapeType.Line);
			render.line(s1.x, s1.y, p1.x, p1.y);
			render.line(s2.x, s2.y, p2.x, p2.y);
			render.line(s1.x, s1.y, s2.x, s2.y);
			render.end();
		}else if(b2Joint instanceof PrismaticJointDefModel){
			final PrismaticJointDefModel obj = PrismaticJointDefModel.class.cast(b2Joint);
			Vector2 x1 = obj.bodyA.position;
			Vector2 x2 = obj.bodyB.position;
			Vector2 anchor = obj.anchor;
			render.begin(ShapeType.Line);
			render.line(x1.x, x1.y, anchor.x, anchor.y);
			render.line(x2.x, x2.y, anchor.x, anchor.y);
			render.end();
		}else if(b2Joint instanceof RevoluteJointDefModel){
			final RevoluteJointDefModel obj = RevoluteJointDefModel.class.cast(b2Joint);
			Vector2 x1 = obj.bodyA.position;
			Vector2 x2 = obj.bodyB.position;
			Vector2 anchor = obj.anchor;
			render.begin(ShapeType.Line);
			render.line(x1.x, x1.y, anchor.x, anchor.y);
			render.line(x2.x, x2.y, anchor.x, anchor.y);
			render.end();
		}else if(b2Joint instanceof b2RopeJointDefModel){
			final b2RopeJointDefModel obj = b2RopeJointDefModel.class.cast(b2Joint);
			Vector2 x1 = obj.bodyA.position;
			Vector2 x2 = obj.bodyB.position;
			final Vector2 p1 = worldPoint(obj.bodyA,obj.localAnchorA);
			final Vector2 p2 = worldPoint(obj.bodyB,obj.localAnchorB);
			render.begin(ShapeType.Line);
			render.line(x1.x, x1.y, p1.x, p1.y);
			render.line(p1.x, p1.y, p2.x, p2.y);
			render.line(x2.x, x2.y, p2.x, p2.y);
			render.end();
		}else if(b2Joint instanceof WeldJointDefModel){
			final WeldJointDefModel obj = WeldJointDefModel.class.cast(b2Joint);
			Vector2 x1 = obj.bodyA.position;
			Vector2 x2 = obj.bodyB.position;
			Vector2 anchor = obj.anchor;
			render.begin(ShapeType.Line);
			render.line(x1.x, x1.y, anchor.x, anchor.y);
			render.line(x2.x, x2.y, anchor.x, anchor.y);
			render.end();
		}else if(b2Joint instanceof WheelJointDefModel){
			final WheelJointDefModel obj = WheelJointDefModel.class.cast(b2Joint);
			Vector2 x1 = obj.bodyA.position;
			Vector2 x2 = obj.bodyB.position;
			Vector2 anchor = obj.anchor;
			render.begin(ShapeType.Line);
			render.line(x1.x, x1.y, anchor.x, anchor.y);
			render.line(x2.x, x2.y, anchor.x, anchor.y);
			render.end();
		}else if(b2Joint instanceof FrictionJointDefModel){
			final FrictionJointDefModel obj = FrictionJointDefModel.class.cast(b2Joint);
			Vector2 x1 = obj.bodyA.position;
			Vector2 x2 = obj.bodyB.position;
			Vector2 anchor = obj.anchor;
			render.begin(ShapeType.Line);
			render.line(x1.x, x1.y, anchor.x, anchor.y);
			render.line(x2.x, x2.y, anchor.x, anchor.y);
			render.end();
		}else if(b2Joint instanceof b2GearJointDefModel){
			//TODO
		}
	}
	static Vector2 worldPoint(b2BodyDefModel b2,Vector2 localVector){
		Vector2 tmp = new Vector2();
		try{
			tmp.set(b2.body.getWorldPoint(localVector.cpy().div(Box2dObject.RADIO))).mul(Box2dObject.RADIO);
		}catch(Exception ex){
			//
		}
		return tmp;
	}
}