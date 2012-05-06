package info.u250.c2d.physical.box2d.builder;

import info.u250.c2d.physical.box2d.Cb2World;
import info.u250.c2d.physical.box2d.Cb2Object;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;

public class FixtureDefBuilder {

	FixtureDef fixtureDef;

	public FixtureDefBuilder() {
		reset();
	}

	public FixtureDefBuilder sensor(boolean isSensor) {
		fixtureDef.isSensor = isSensor;
		return this;
	}

	public FixtureDefBuilder boxShape(float hx, float hy) {
		PolygonShape shape = new PolygonShape();
		shape.setAsBox(hx, hy);
		fixtureDef.shape = shape;
		return this;
	}
	
	public FixtureDefBuilder boxShape(float hx, float hy, Vector2 center, float angleInRadians) {
		PolygonShape shape = new PolygonShape();
		shape.setAsBox(hx, hy,center,angleInRadians);
		fixtureDef.shape = shape;
		return this;
	}

	public FixtureDefBuilder circleShape(float radius) {
		Shape shape = new CircleShape();
		shape.setRadius(radius);
		fixtureDef.shape = shape;
		return this;
	}
	
	public FixtureDefBuilder circleShape(Vector2 center, float radius) {
		CircleShape shape = new CircleShape();
		shape.setRadius(radius);
		shape.setPosition(center);
		fixtureDef.shape = shape;
		return this;
	}

	public FixtureDefBuilder polygonShape(Vector2[] vertices) {
		PolygonShape shape = new PolygonShape();
		shape.set(vertices);
		fixtureDef.shape = shape;
		return this;
	}

	public FixtureDefBuilder density(float density) {
		fixtureDef.density = density;
		return this;
	}

	public FixtureDefBuilder friction(float friction) {
		fixtureDef.friction = friction;
		return this;
	}

	public FixtureDefBuilder restitution(float restitution) {
		fixtureDef.restitution = restitution;
		return this;
	}

	public FixtureDefBuilder categoryBits(short categoryBits) {
		fixtureDef.filter.categoryBits = categoryBits;
		return this;
	}
	
	public FixtureDefBuilder groupIndex(short groupIndex){
		fixtureDef.filter.groupIndex = groupIndex;
		return this;
	}

	public FixtureDefBuilder maskBits(short maskBits) {
		fixtureDef.filter.maskBits = maskBits;
		return this;
	}

	private void reset() {
		fixtureDef = new FixtureDef();
	}
	
	public FixtureDefBuilder defaultValues(){
		return this.density(Cb2Object.DEFAULT_density)
				.restitution(Cb2World.RADIO)
				.friction(Cb2Object.DEFAULT_friction);
	}

	public FixtureDef build() {
		FixtureDef fixtureDef = this.fixtureDef;
		reset();
		return fixtureDef;
	}

}