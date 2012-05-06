package info.u250.c2d.physical.box2d;

import info.u250.c2d.graphic.surfaces.TriangleSurfaces;
import info.u250.c2d.graphic.surfaces.data.SurfaceData;

import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;

/**
 * default drawable of the mesh and physical object
 * 
 * @author lycying@gmail.com
 */
public class Cb2TriangleSurfaces extends TriangleSurfaces {
	Body body;
	
	public Cb2TriangleSurfaces(SurfaceData data) {
		super(data);
	}
	
	@Override
	protected void doBuild() {
		super.doBuild();

		BodyDef bodyDef = new BodyDef();
		bodyDef.position.set(0, 0);
		bodyDef.type = BodyType.StaticBody;
		body = Cb2World.getInstance().world().createBody(bodyDef);

		final FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.friction = 1;
		fixtureDef.restitution = 0;
		fixtureDef.density = 0.5f;
		fixtureDef.isSensor = false;

		PolygonShape shape = new PolygonShape();
		final Vector2[] v = new Vector2[3];

		if (data.primitiveType == GL10.GL_TRIANGLE_FAN) {
			for (int i = 1; i < data.points.size - 1; i++) {
				v[0] = data.points.get(0).cpy().mul(1 / Cb2World.RADIO);
				v[1] = data.points.get(i).cpy().mul(1 / Cb2World.RADIO);
				v[2] = data.points.get(i + 1).cpy().mul(1 / Cb2World.RADIO);

				if (isClockwise(v[0], v[1], v[2])) {
					Vector2 vv = v[0];
					v[0] = v[2];
					v[2] = vv;
				}
				shape.set(v);
				fixtureDef.shape = shape;
				body.createFixture(fixtureDef);
			}
		} else if (data.primitiveType == GL10.GL_TRIANGLE_STRIP) {
			for (int i = 0; i < data.points.size - 2; i++) {
				v[0] = data.points.get(i + 2).cpy().mul(1 / Cb2World.RADIO);
				v[1] = data.points.get(i + 1).cpy().mul(1 / Cb2World.RADIO);
				v[2] = data.points.get(i).cpy().mul(1 / Cb2World.RADIO);

				if (isClockwise(v[0], v[1], v[2])) {
					Vector2 vv = v[0];
					v[0] = v[2];
					v[2] = vv;
				}
				shape.set(v);
				fixtureDef.shape = shape;
				body.createFixture(fixtureDef);
			}
		}

		shape.dispose();

	}

	@Override
	public void dispose() {
		super.dispose();
		if (null != body) {
			try {
				Cb2World.getInstance().world().destroyBody(body);
				body = null;
			} catch (Exception ex) {
				// do nothing
			}
		}
	}
}
