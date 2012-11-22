package info.u250.c2d.physical.box2d;

import java.util.Iterator;

import info.u250.c2d.engine.Engine;
import info.u250.c2d.engine.service.Disposable;
import info.u250.c2d.engine.service.Updatable;
import info.u250.c2d.physical.box2d.builder.BodyBuilder;
import info.u250.c2d.physical.box2d.builder.FixtureDefBuilder;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.ChainShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.Joint;
import com.badlogic.gdx.physics.box2d.World;

/**
 * The whole world that hold all the physical objects
 * (1) create it :
 *  PhysicalManager.PM().installDefaultWorld();
 *  if you want to create a default screen just call 
 *  {@link #createScreenBox()} and larger or small {@link #createScreenBox(Rectangle)}
 * 	if you want to create a custom world call 
 *  {@link #installWorld(World)}
 * @author lycying@gmail.com
 */
public class Cb2World implements Updatable,Disposable{
	/**
	 * This is a very important synchronous variables.
	 * Used to box2d and animation will sync up. At the same time, 
	 * can draw box2d debugging interface.
	 */
	public static final float RADIO = 32.00000000f;
	

	private Cb2World(){
		//do nothing
	}
	private BodyBuilder bodyBuilder;
	private FixtureDefBuilder fixtureDefBuilder;
	public BodyBuilder getBodyBuilder() {
		return bodyBuilder;
	}
	public FixtureDefBuilder getFixtureDefBuilder() {
		return fixtureDefBuilder;
	}
	
	private static World world ;
	private static Cb2World instance = null;
	
	
	public static Cb2World getInstance(){
		if(null == instance){
			instance = new Cb2World();
		}
		return instance;
	}
	public World world(){
		return world;
	}
	
	/**
	 * install a supplied world
	 */
	public Cb2World installWorld(World world){
		Cb2World.world = world;
		this.bodyBuilder = new BodyBuilder(world);
		this.fixtureDefBuilder = new FixtureDefBuilder();
		return this;
	}
	/**
	 * install a default world with gravity:10
	 */
	public Cb2World installDefaultWorld(){
		World world = new World(new Vector2(0, -10), true);
		this.installWorld(world);
//		this.world().setWarmStarting(true);
		return this;
	}
	@Override
	public void update(float delta) {
		if(null != world){
			//step , step , step 
			world.step(delta, 8, 3);
		}
	}
	/**
	 * create the box via the screen's width and height 
	 */
	public Body createScreenBox(){
		return this.createScreenBox(new Rectangle(0, 0, Engine.getWidth(), Engine.getHeight()));
	}
	public Body createScreenBox(final Rectangle rect){
		if(world==null) {
			this.installDefaultWorld();
		}
		ChainShape shape  = new ChainShape();
		shape.createLoop(new Vector2[]{
				new Vector2(rect.x,rect.y).mul(1/RADIO),
				new Vector2(rect.x+rect.width,rect.y).mul(1/RADIO),
				new Vector2(rect.x+rect.width,rect.y+rect.height).mul(1/RADIO),
				new Vector2(rect.x,rect.y+rect.height).mul(1/RADIO),
		});
		
		final FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.density = Cb2Object.DEFAULT_density;
		fixtureDef.restitution = Cb2Object.DEFAULT_friction;
		fixtureDef.friction = Cb2Object.DEFAULT_friction;
		fixtureDef.isSensor = false;
		fixtureDef.shape = shape;
		
		BodyDef bodyDef = new BodyDef();
		bodyDef.bullet = false;
		bodyDef.type = BodyType.StaticBody;
		bodyDef.linearDamping = 0f;

		Body body = world.createBody(bodyDef);
		body.createFixture(fixtureDef);
		shape.dispose();
		return body;
	}
	/**
	 * dispose the whole {@link Cb2World} and the whole world
	 */
	public void dispose(){
		//Destruction of the physical world
		if(null!=world){
			world.dispose();
			world = null;
		}
		//Make it to be null , then a new PhysicalManager will be made next calling
		instance = null;
	}
	public void clear(){
		if(null!=world){
			Iterator<Joint> itj = world.getJoints();
			while(itj.hasNext())world.destroyJoint(itj.next());
			Iterator<Body> it = world.getBodies();
			while(it.hasNext()){
				try{
					world.destroyBody(it.next());
				}catch(Exception ex){}
			}
		}
	}
	
}