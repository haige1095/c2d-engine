package info.u250.c2d.physical.box2d.controllers;

import java.util.ArrayList;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.ChainShape;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.Transform;
/**
 * buoyancy that use the {@link Box2dControllers}
 * @author lycying@gmail.com
 */
public class BuoyancyController extends Box2dControllers{
   /// The outer surface normal
   public Vector2 normal;
   /// The height of the fluid surface along the normal
   public float offset;
   /// The fluid density
   public float density = 2.0f;
   /// Fluid velocity, for drag calculations
   public Vector2 velocity;
   /// Linear drag co-efficient
   public float linearDrag = 2;
   /// Linear drag co-efficient
   public float angularDrag = 2;
   /// If false, bodies are assumed to be uniformly dense, otherwise use the shapes densities
   public boolean useDensity = false; //False by default to prevent a gotcha
   /// Gravity vector, if the world's gravity is not used
   public Vector2 gravity;
   
   private Vector2 tmp;
   private Vector2 sc;
   private Vector2 areac;
   private Vector2 massc;

   public BuoyancyController(){
      normal = new Vector2(0,1);
      velocity = new Vector2();
      gravity = new Vector2(0,-9.8f);
      
      tmp = new Vector2();
      sc = new Vector2();
      areac = new Vector2();
      massc = new Vector2();
   }
   
   public boolean ApplyToFixture(Fixture f){
      Body body = f.getBody();
      areac.set(0,0);
      massc.set(0,0);
      float area = 0;
      float mass = 0;
      
      Shape shape = f.getShape();
      
      sc.set(0,0);
      float sarea;
      switch (shape.getType()) {
         case Circle:
            sarea  = B2ShapeExtensions.ComputeSubmergedArea((CircleShape)shape,normal, offset, body.getTransform(), sc);
            break;
            
         case Chain:
            sarea  = B2ShapeExtensions.ComputeSubmergedArea((ChainShape)shape,normal, offset, body.getTransform(), sc);
            break;
            
         case Edge:
            sarea  = B2ShapeExtensions.ComputeSubmergedArea((EdgeShape)shape,normal, offset, body.getTransform(), sc);
            break;
            
         case Polygon:
            sarea  = B2ShapeExtensions.ComputeSubmergedArea((PolygonShape)shape,normal, offset, body.getTransform(), sc);
            break;
            
         default:
            sarea = 0;
            break;
      }
      
      area += sarea;
      areac.x += sarea * sc.x;
      areac.y += sarea * sc.y;
      float shapeDensity = useDensity ? f.getDensity() : density;
      mass += sarea * shapeDensity;
      massc.x += sarea * sc.x * shapeDensity;
      massc.y += sarea * sc.y * shapeDensity;         

      areac.x /= area;
      areac.y /= area;
      massc.x /= mass;
      massc.y /= mass;
      if(area < Float.MIN_VALUE) {
         return false;
      }

      // buoyancy force.
      body.applyForce(tmp.set(gravity).mul(-density * area), massc); // multiply by -density to invert gravity  (combining a couple of operations)
      // linear drag.
      body.applyForce(
         body.getLinearVelocityFromWorldPoint(areac)
         .sub(velocity)
         .mul(-linearDrag * area),
         areac);
      /// angular drag.
      float torque = -body.getInertia() / body.getMass() * area * body.getAngularVelocity() * angularDrag;
      body.applyTorque(torque);
      return true;
   }
   

	@Override
	public void update(float delta) {
		if (controllersBody != null) {
			for (int i = 0; i < controllersBody.size; i++) {
				ArrayList<Fixture> fixtureList = controllersBody.get(i).getFixtureList();
				for (int j = 0; j < fixtureList.size(); j++) {
					ApplyToFixture(fixtureList.get(j));
				}
			}
		}
		
	}

	
	
}
/**
 * codes from https://hg.assembla.com/box2dcontrollers/
 * @author Tim Scott
 */
class B2ShapeExtensions {

	public static float ComputeSubmergedArea(CircleShape shape, Vector2 normal,
			float offset, Transform xf, Vector2 c) {
		Vector2 p = xf.mul(shape.getPosition());
		float l = -(normal.dot(p) - offset);
		float r = shape.getRadius();

		if (l < -r) { // Completely dry
			return 0;
		}

		if (l > r) { // Completely wet
			c.set(p);
			return MathUtils.PI * r * r;
		}

		float r2 = r * r;
		float l2 = l * l;
		float area = r2 * ((float) Math.asin(l / r) + MathUtils.PI / 2) + l
				* (float) Math.sqrt(r2 - l2);
		float com = -2.0f / 3.0f * (float) Math.pow(r2 - l2, 1.5f) / area;
		c.x = p.x + normal.x * com;
		c.y = p.y + normal.y * com;
		return area;
	}

	public static float ComputeSubmergedArea(ChainShape shape, Vector2 normal,
			float offset, Transform xf, Vector2 c) {
		return 0;
	}

	/*
	 * Minimize run-time memory allocation overhead here.
	 */
	private static Vector2 c1 = new Vector2();
	private static Vector2 c2 = new Vector2();
	private static Vector2 normalL = new Vector2();
	// need as many floats as there are vertices in a fixture's shape. Check out
	// the
	// jni/Common/b2Settings.h/b2_maxPolygonVertices for the currently defined
	// value.
	// This value is currently 8! If you jack around with that, you'll need to
	// update this!
	private static float[] depths = new float[8];
	private static Vector2 vertex = new Vector2();

	private static Vector2 intoVertex = new Vector2();
	private static Vector2 intoVertex2 = new Vector2();
	private static Vector2 outoVertex = new Vector2();
	private static Vector2 outoVertex2 = new Vector2();

	private static Vector2 intoVec = new Vector2();
	private static Vector2 outoVec = new Vector2();

	private static Vector2 p2 = new Vector2();

	public static float ComputeSubmergedArea(PolygonShape shape,
			Vector2 normal, float offset, Transform xf, Vector2 c) {
		// Transform plane into shape co-ordinates
		c1.set(xf.vals[Transform.COS], xf.vals[Transform.SIN]);
		c2.set(-xf.vals[Transform.SIN], xf.vals[Transform.COS]);

		normalL.set(normal.dot(c1), normal.dot(c2));
		float offsetL = offset - normal.dot(xf.getPosition());

		int diveCount = 0;
		int intoIndex = -1;
		int outoIndex = -1;

		boolean lastSubmerged = false;
		int i;

		int vertexCount = shape.getVertexCount();

		if (vertexCount > 8)
			throw new IndexOutOfBoundsException("Bad vertex count.");

		// loop on all vertices of the polygon
		for (i = 0; i < vertexCount; i++) {
			shape.getVertex(i, vertex);
			// determine depth of this object versus the waterline. negative
			// represents submerged
			depths[i] = normalL.dot(vertex) - offsetL;
			boolean isSubmerged = depths[i] < 0;
			if (i > 0) {
				// if the current vertex is wet...
				if (isSubmerged) {
					// if the previous vertex is dry...
					if (!lastSubmerged) {
						// we've transitioned from dry to wet! Record the dry
						// vertex index. Used below for submerged area
						// calculations
						intoIndex = i - 1;
						diveCount++;
					}
				} else {
					// else the current vertex is dry....

					// if the previous vertex was wet
					if (lastSubmerged) {
						// we've transitioned from wet to dry! Record the wet
						// vertex index. Used below for submerged area
						// calculations
						outoIndex = i - 1;
						diveCount++;
					}
				}
			}
			lastSubmerged = isSubmerged;
		}
		switch (diveCount) {
		case 0:
			if (lastSubmerged) {
				float area = ComputeCentroid(shape, c);
				xf.mul(c);
				return area;
			} else { // Completely dry
				return 0;
			}
			// break;
		case 1:

			if (intoIndex == -1) {
				intoIndex = vertexCount - 1;
			} else {
				outoIndex = vertexCount - 1;
			}
			break;
		}
		int intoIndex2 = (intoIndex + 1) % vertexCount;
		int outoIndex2 = (outoIndex + 1) % vertexCount;

		shape.getVertex(intoIndex, intoVertex);
		shape.getVertex(intoIndex2, intoVertex2);
		shape.getVertex(outoIndex, outoVertex);
		shape.getVertex(outoIndex2, outoVertex2);

		float intoLambda = (0 - depths[intoIndex])
				/ (depths[intoIndex2] - depths[intoIndex]);
		float outoLambda = (0 - depths[outoIndex])
				/ (depths[outoIndex2] - depths[outoIndex]);

		intoVec.set(intoVertex.x * (1 - intoLambda) + intoVertex2.x
				* intoLambda, intoVertex.y * (1 - intoLambda) + intoVertex2.y
				* intoLambda);
		outoVec.set(outoVertex.x * (1 - outoLambda) + outoVertex2.x
				* outoLambda, outoVertex.y * (1 - outoLambda) + outoVertex2.y
				* outoLambda);

		// Initialize accumulator
		float area = 0;
		c.set(0, 0);
		p2.set(intoVertex2);
		Vector2 p3;

		float k_inv3 = 1.0f / 3.0f;

		// An awkward loop from intoIndex2+1 to outIndex2
		i = intoIndex2;
		while (i != outoIndex2) {
			i = (i + 1) % vertexCount;
			if (i == outoIndex2) {
				p3 = outoVec;
			} else {
				p3 = vertex;
				shape.getVertex(i, p3);
			}
			// Add the triangle formed by intoVec,p2,p3
			// b2Vec2 e1 = p2 - p1;
			float e1X = p2.x - intoVec.x;
			float e1Y = p2.y - intoVec.y;
			// b2Vec2 e2 = p3 - p1;
			float e2X = p3.x - intoVec.x;
			float e2Y = p3.y - intoVec.y;

			// float32 D = b2Cross(e1, e2);
			float D = e1X * e2Y - e1Y * e2X;

			// float32 triangleArea = 0.5f * D;
			float triangleArea = 0.5f * D;
			area += triangleArea;

			// Area weighted centroid
			// center += triangleArea * k_inv3 * (p1 + p2 + p3);
			c.x += triangleArea * k_inv3 * (intoVec.x + p2.x + p3.x);
			c.y += triangleArea * k_inv3 * (intoVec.y + p2.y + p3.y);
			p2.set(p3);
		}

		// Normalize and transform centroid
		c.x /= area;
		c.y /= area;

		c.set(xf.mul(c));

		return area;
	}

	public static float ComputeSubmergedArea(EdgeShape shape, Vector2 normal,
			float offset, Transform xf, Vector2 c) {
		return 0;
	}

	private static Vector2 e1 = new Vector2();
	private static Vector2 e2 = new Vector2();
	private static Vector2 tmp = new Vector2();
	private static Vector2 tmp2 = new Vector2();
	private static Vector2 pRef = new Vector2();

	static float ComputeCentroid(PolygonShape shape, Vector2 c) {
		float area = 0.0f;
		c.set(0, 0);

		// pRef is the reference point for forming triangles.
		// It's location doesn't change the result (except for rounding error).
		pRef.set(0, 0);

		float inv3 = 1.0f / 3.0f;

		int count = shape.getVertexCount();

		if (count < 3)
			throw new IndexOutOfBoundsException("Bad vertex count!");

		for (int i = 0; i < count; ++i) {
			// Triangle vertices.
			Vector2 p1 = pRef;
			Vector2 p2 = tmp;
			shape.getVertex(i, p2); // vs[i];
			Vector2 p3 = tmp2;
			shape.getVertex(i + 1 < count ? i + 1 : 0, p3); // vs[i+1] : vs[0];

			e1.set(p2).sub(p1); // e1 = p2 - p1;
			e2.set(p3).sub(p1); // e2 = p3 - p1;

			float D = e1.crs(e2);

			float triangleArea = 0.5f * D;
			area += triangleArea;

			// Area weighted centroid
			tmp.set(p1).add(p2).add(p3).mul(triangleArea * inv3);
			c.add(tmp);

		}

		if (area < Float.MIN_VALUE)
			throw new ArithmeticException("Bad area computation!");
		// Centroid
		c.mul(1.0f / area);

		return area;
	}
}