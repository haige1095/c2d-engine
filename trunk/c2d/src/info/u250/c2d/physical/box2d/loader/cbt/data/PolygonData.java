package info.u250.c2d.physical.box2d.loader.cbt.data;

import info.u250.c2d.engine.Engine;
import info.u250.c2d.physical.box2d.Cb2World;
import info.u250.c2d.physical.box2d.builder.BodyBuilder;

import java.util.List;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Transform;
import com.badlogic.gdx.utils.Array;
/**
 * @author lycying@gmail.com
 */
public class PolygonData extends BodyData{
	public float width =-1;
	public float height = -1;
	public Array<Vector2[]> polygons = new Array<Vector2[]>();
	
	public void build(){
		BodyBuilder bodyBuilder = Cb2World.getInstance().getBodyBuilder();
		for(Vector2[] polygon:polygons){
			final Vector2[] vv = new Vector2[polygon.length];
			for(int i=0;i<polygon.length;i++){
				vv[i] = polygon[i].cpy().mul(1/Cb2World.RADIO);
			}
			bodyBuilder.fixture(
					Cb2World.getInstance().getFixtureDefBuilder()
					.density(this.density)
					.friction(this.friction)
					.restitution(this.restitution)
					.maskBits((short)this.filter_maskBits)
					.categoryBits((short)this.filter_categoryBits)
					.groupIndex((short)this.filter_groupIndex)
					.polygonShape(vv)
					.sensor(this.isSensor)
					.build());
		}
		this.body = bodyBuilder
		.type(this.isKinematic?BodyType.KinematicBody:(this.isDynamic?BodyType.DynamicBody:BodyType.StaticBody))
		.userData(this.data)
		.angle(MathUtils.degreesToRadians*this.angle)
		.position(this.center.cpy().mul(1/Cb2World.RADIO))
		.build();
	}

	@Override
	public boolean isFocus(Vector2 point) {
		return false;
	}

	
	@Override
	public void debug(ShapeRenderer render) {
		if(null==body){
			//TODO
			float scale = 1.5f;
			render.setProjectionMatrix(Engine.getDefaultCamera().combined);
			for(Vector2[] vv:polygons){
				for(int i=1;i<vv.length;i++){
					render.setColor(Color.YELLOW);
					render.begin(ShapeType.Line);
					float len1 = vv[i-1].len();
					float len2 = vv[i].len();
					float angle1 = vv[i-1].angle();
					float angle2 = vv[i].angle();
					render.line(
							(width/2+len1*MathUtils.cosDeg(angle+angle1))*scale,
							(height/2+len1*MathUtils.sinDeg(angle+angle1))*scale , 
							(width/2+len2*MathUtils.cosDeg(angle+angle2))*scale,
							(height/2+len2*MathUtils.sinDeg(angle+angle2))*scale);
					render.end();
				}
			}
		}else{
			Transform transform = body.getTransform();
			int len = body.getFixtureList().size();
			List<Fixture> fixtures = body.getFixtureList();
			for (int i = 0; i < len; i++) {
				Fixture fixture = fixtures.get(i);
				drawShape(fixture, transform, Color.GREEN,render);
			}
		}
	}
	private void drawShape (Fixture fixture, Transform transform, Color color,ShapeRenderer render) {
		PolygonShape chain = (PolygonShape)fixture.getShape();
		int vertexCount = chain.getVertexCount();
		Vector2[] vertices = new Vector2[vertexCount];
		for(int p=0;p<vertices.length;p++)vertices[p] = new Vector2();
		for (int i = 0; i < vertexCount; i++) {
			chain.getVertex(i, vertices[i]);
			transform.mul(vertices[i]);
		}
		drawSolidPolygon(vertices, vertexCount, color,render);
	}
	Vector2 lv = new Vector2();
	Vector2 f = new Vector2();
	private void drawSolidPolygon (Vector2[] vertices, int vertexCount, Color color,ShapeRenderer render) {
		render.begin(ShapeType.Line);
		render.setColor(color);
		for (int i = 0; i < vertexCount; i++) {
			Vector2 v = vertices[i];
			if (i == 0) {
				lv.set(v);
				f.set(v);
				continue;
			}
			render.line(lv.x*Cb2World.RADIO, lv.y*Cb2World.RADIO, v.x*Cb2World.RADIO, v.y*Cb2World.RADIO);
			lv.set(v);
		}
		render.line(f.x*Cb2World.RADIO, f.y*Cb2World.RADIO, lv.x*Cb2World.RADIO, lv.y*Cb2World.RADIO);
		render.end();
	}
}