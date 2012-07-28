package info.u250.c2d.physical.box2d.loader.cbt.data;

import info.u250.c2d.physical.box2d.Cb2World;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
/**
 * @author lycying@gmail.com
 */
public class BoxData extends BodyData{
	public float width =-1;
	public float height = -1;
	
	public void build(){
		this.body = Cb2World.getInstance().getBodyBuilder()
		.fixture(
				Cb2World.getInstance().getFixtureDefBuilder()
				.density(this.density)
				.friction(this.friction)
				.restitution(this.restitution)
				.maskBits((short)this.filter_maskBits)
				.categoryBits((short)this.filter_categoryBits)
				.groupIndex((short)this.filter_groupIndex)
				.boxShape(this.width/Cb2World.RADIO/2, this.height/Cb2World.RADIO/2)
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
		float rect_angle = new Vector2(this.width,this.height).angle();
		float half_diagonal = (float)Math.sqrt(width*width+height*height)/2;
		Vector3 p = new Vector3(point.x,point.y,0);
		//left bottom
		final Vector3 point1 = new Vector3(center.x+MathUtils.cosDeg(180+rect_angle+angle)*half_diagonal, 
				center.y+MathUtils.sinDeg(180+rect_angle+angle)*half_diagonal,0);
		//right bottom
		final Vector3 point2 = new Vector3(center.x+MathUtils.cosDeg(-rect_angle+angle)*half_diagonal, 
				center.y+MathUtils.sinDeg(-rect_angle+angle)*half_diagonal,0);
		//right top
		final Vector3 point3 = new Vector3(center.x+MathUtils.cosDeg(rect_angle+angle)*half_diagonal, 
				center.y+MathUtils.sinDeg(rect_angle+angle)*half_diagonal,0);
		//left top
		final Vector3 point4 = new Vector3(center.x+MathUtils.cosDeg(180-rect_angle+angle)*half_diagonal, 
				center.y+MathUtils.sinDeg(180-rect_angle+angle)*half_diagonal,0);
		
		return Intersector.isPointInTriangle(p, point1, point2, point3)||
				Intersector.isPointInTriangle(p, point1, point4, point3);
				
	}

	private final Color BOX = new Color(0.6f, 0.6f, 0.6f, 0.5f);
	private final Color BOX_BORDER = new Color(0.6f, 0.6f, 0.6f, 0.8f);
	
	@Override
	public void debug(ShapeRenderer render) {
		if(null==this.body)this.debugX(render, this.center, this.angle);
		else this.debugX(render, this.body.getPosition().mul(Cb2World.RADIO), this.body.getAngle()*MathUtils.radiansToDegrees);
	}
	
	private void debugX(ShapeRenderer render,Vector2 center,float angle) {
		float rect_angle = new Vector2(this.width,this.height).angle();
		float half_diagonal = (float)Math.sqrt(width*width+height*height)/2;
		
		Gdx.gl.glEnable(GL10.GL_BLEND);
		render.setColor(BOX);
		render.begin(ShapeType.FilledTriangle);
		render.filledTriangle(
				center.x+MathUtils.cosDeg(180+rect_angle+angle)*half_diagonal, 
				center.y+MathUtils.sinDeg(180+rect_angle+angle)*half_diagonal, 
				center.x+MathUtils.cosDeg(-rect_angle+angle)*half_diagonal, 
				center.y+MathUtils.sinDeg(-rect_angle+angle)*half_diagonal, 
				center.x+MathUtils.cosDeg(rect_angle+angle)*half_diagonal, 
				center.y+MathUtils.sinDeg(rect_angle+angle)*half_diagonal);
		render.filledTriangle(
				center.x+MathUtils.cosDeg(180-rect_angle+angle)*half_diagonal, 
				center.y+MathUtils.sinDeg(180-rect_angle+angle)*half_diagonal, 
				center.x+MathUtils.cosDeg(180+rect_angle+angle)*half_diagonal, 
				center.y+MathUtils.sinDeg(180+rect_angle+angle)*half_diagonal, 
				center.x+MathUtils.cosDeg(rect_angle+angle)*half_diagonal, 
				center.y+MathUtils.sinDeg(rect_angle+angle)*half_diagonal);
		render.end();
		render.setColor(BOX_BORDER);
		render.begin(ShapeType.Triangle);
		render.triangle(
				center.x+MathUtils.cosDeg(180+rect_angle+angle)*half_diagonal, 
				center.y+MathUtils.sinDeg(180+rect_angle+angle)*half_diagonal, 
				center.x+MathUtils.cosDeg(-rect_angle+angle)*half_diagonal, 
				center.y+MathUtils.sinDeg(-rect_angle+angle)*half_diagonal, 
				center.x+MathUtils.cosDeg(rect_angle+angle)*half_diagonal, 
				center.y+MathUtils.sinDeg(rect_angle+angle)*half_diagonal);
		render.triangle(
				center.x+MathUtils.cosDeg(180-rect_angle+angle)*half_diagonal, 
				center.y+MathUtils.sinDeg(180-rect_angle+angle)*half_diagonal, 
				center.x+MathUtils.cosDeg(180+rect_angle+angle)*half_diagonal, 
				center.y+MathUtils.sinDeg(180+rect_angle+angle)*half_diagonal, 
				center.x+MathUtils.cosDeg(rect_angle+angle)*half_diagonal, 
				center.y+MathUtils.sinDeg(rect_angle+angle)*half_diagonal);
		render.end();
		
		Gdx.gl.glDisable(GL10.GL_BLEND);
	}

}