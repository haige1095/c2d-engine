package info.u250.c2d.physical.box2d.loader.cbt.data;

import info.u250.c2d.physical.box2d.Cb2World;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
/**
 * @author lycying@gmail.com
 */
public class CircleData extends BodyData{
	public float radius =-1;
	
	public void build(){
		this.body =  Cb2World.getInstance().getBodyBuilder()
		.fixture(
				Cb2World.getInstance().getFixtureDefBuilder()
				.density(this.density)
				.friction(this.friction)
				.restitution(this.restitution)
				.maskBits((short)this.filter_maskBits)
				.categoryBits((short)this.filter_categoryBits)
				.groupIndex((short)this.filter_groupIndex)
				.circleShape(this.radius/Cb2World.RADIO)
				.sensor(this.isSensor)
				.build())
		.type(this.isKinematic?BodyType.KinematicBody:(this.isDynamic?BodyType.DynamicBody:BodyType.StaticBody))
		.userData(this.data)
		.angle(MathUtils.degreesToRadians*this.angle)
		.position(this.center.cpy().mul(1/Cb2World.RADIO))
		.build();
	}

	@Override
	public boolean isFocus(Vector2 point) {
		return point.dst(center)<=this.radius;
	}

	private final Color CIRCLE = new Color(0.6f, 0.6f, 0.6f, 0.5f);
	private final Color CIRCLE_BORDER = new Color(0.6f, 0.6f, 0.6f, 0.8f);
	private final Color LINE = new Color(1f, 1f, 1f, 0.3f);
	@Override
	public void debug(ShapeRenderer render) {
		if(null==this.body)this.debugX(render, this.center, this.angle);
		else this.debugX(render, this.body.getPosition().mul(Cb2World.RADIO), this.body.getAngle()*MathUtils.radiansToDegrees);
	}
	private void debugX(ShapeRenderer render,Vector2 center,float angle) {
		Gdx.gl.glEnable(GL10.GL_BLEND);
		render.setColor(CIRCLE);
		render.begin(ShapeType.FilledCircle);
		render.filledCircle(center.x, center.y, radius);
		render.end();
		render.setColor(CIRCLE_BORDER);
		render.begin(ShapeType.Circle);
		render.circle(center.x, center.y, radius);
		render.end();
		render.setColor(LINE);
		render.begin(ShapeType.Line);
		render.line(
				center.x, center.y, 
				center.x+radius*MathUtils.cosDeg(angle),
				center.y+radius*MathUtils.sinDeg(angle));
		render.end();
		Gdx.gl.glDisable(GL10.GL_BLEND);
	}

}