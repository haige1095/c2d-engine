package info.u250.c2d.physical.box2d;

import info.u250.c2d.engine.Engine;
import info.u250.c2d.engine.service.Disposable;
import info.u250.c2d.engine.service.Renderable;
import info.u250.c2d.graphic.AdvanceSprite;
import info.u250.c2d.physical.box2d.loader.cbt.data.BodyData;
import info.u250.c2d.physical.box2d.loader.cbt.data.BoxData;
import info.u250.c2d.physical.box2d.loader.cbt.data.CircleData;
import info.u250.c2d.physical.box2d.loader.cbt.data.PolygonData;
import aurelienribon.tweenengine.Tween;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
/**
 * This is A physical model. Contains a sprite and a box2d rigid body.
 * This class is mainly doing synchronous between sprite  and rigid body .
 * Including rotation Angle, displacement and other major synchronous information. 
 * After you make a new PhysicalObject, the contact of rigid body and sprite   has been established in fact, 
 * the same sprite can be used in more than one PhysicalObject. 
 * PhysicalObject is managered {@link Cb2World} , This only explicit call add method, 
 * the linked sprite can be drawn.
 * Some important case  :
 * 
 * (1) i just want make a object such as circle or a box via the sprite's size .In this case you can call the 
 * {@link #PhysicalObject(AdvanceSprite, Object, boolean)} , it use the default values such as 
 * {@value #DEFAULT_density}
 * {@link #DEFAULT_friction}
 * {@link #DEFAULT_restitution}
 * {@link #DEFAULT_linearDamping} etc.. mostly it's enough to archive some physical test 
 * <br/>
 * (2) if you want to make the physical body outside and the sprite outside . just want give a establish of them . call
 * {@link #PhysicalObject(AdvanceSprite, Body)}
 * in this case , you can modify every attributes of the body , so it's full access the origin of the box2d's api.
 * <br/>
 * (3) just want extends this class and give custom api organization . Nothing can be call ,
 * you must extends this class . and write your initialize code at 
 * {@link #PhysicalObject()}
 * remember you must supply the {@link #body} and the {@link #object} , they can not be null.
 * and mostly important is that remember call {@link #init()} method after you fill the two attributes
 * <br/>
 * 
 * More questions :
 * (1) how to modify the position of the physicalObject
 * the {@link AdvanceSprite#setPosition(float, float)} has no effect to the physical object 
 * you must call {@link #setPosition(Vector2)} or {@link #setPosition(float, float)} instead.
 * 
 * (2) how to get the position of the physicalObject .
 * You call directly use the {@link #getPosition()} method to get what you want .it return the x,y at the (0,0) position
 * in many scenes , it's the same as  {@link AdvanceSprite#getX()} . 
 * 
 * (3) Call i use the {@link Tween} engine ?
 * yes you can , but remember , if you change the transform of the body .
 * This breaks any contacts and wakes the other bodies. Manipulating a
 * body's transform may cause <B>non-physical behavior</b>. {@link Body#setTransform(float, float, float)}
 * 
 * 
 * @author lycying@gmail.com
 */
public  class Cb2Object implements Renderable,Disposable{
	/**
	 * the world body , call it directly . its not null 
	 */
	public BodyData data ;
	/**
	 * the object such as a sprite, or its a AnimationSprite 
	 */
	public AdvanceSprite object;
	
	/**
	 * The default restitution of the rigid body
	 * @see com.badlogic.gdx.physics.box2d.FixtureDef#restitution
	 */
	public static final float DEFAULT_restitution = 0.3f;
	/**
	 * The default angularDamping of the rigid body
	 * @see com.badlogic.gdx.physics.box2d.BodyDef#angularDamping
	 */
	public static final float DEFAULT_angularDamping = (float) (Math.PI / 2);
	/**
	 * The default linerDamping of the rigid body
	 * @see com.badlogic.gdx.physics.box2d.BodyDef#linearDamping
	 */
	public static final float DEFAULT_linearDamping = 0.3f;
	/**
	 * The default Friction of the rigid body
	 * @see com.badlogic.gdx.physics.box2d.FixtureDef#friction
	 */
	public static final float DEFAULT_friction = 0.4f;
	/**
	 * The default density of the rigid body 
	 * @see com.badlogic.gdx.physics.box2d.FixtureDef#density
	 */
	public static final float DEFAULT_density = 1f;
	/**
	 * if the physical object is recycled
	 */
	private boolean visiable = true;
	
	private boolean resizeObject = true;
	
	protected Vector2 offset = new Vector2();

	
	public boolean isResizeObject() {
		return resizeObject;
	}
	public void setResizeObject(boolean resizeObject) {
		this.resizeObject = resizeObject;
	}
	public boolean isVisiable() {
		return visiable;
	}
	public void setVisiable(boolean visiable){
		this.visiable = visiable;
	}
	
	
	public static interface Cb2ObjectSetupCallback{
		void before(Cb2Object obj);
		void after(Cb2Object obj);
	}
	/**
	 * you may what make a new body outside to full control . 
	 */
	public Cb2Object(BodyData data,AdvanceSprite sprite,Cb2ObjectSetupCallback callback){
		this.data = data;
		this.object = sprite;
		if(null!=callback)callback.before(this);
		this.setup();
		if(null!=callback)callback.after(this);
	}
	public Cb2Object(BodyData data,AdvanceSprite sprite){
		this(data, sprite, null);
	}
	public Cb2Object(AdvanceSprite sprite,boolean circle,Cb2ObjectSetupCallback callback){
		if(circle){
			CircleData c = new CircleData();
			c.radius = sprite.getWidth()*sprite.getScaleX()/2;
			c.center.set(sprite.getX(), sprite.getY()).add(c.radius,c.radius);
			this.data = c;
		}else{
			BoxData b = new BoxData();
			b.width = sprite.getWidth()*sprite.getScaleX();
			b.height = sprite.getHeight()*sprite.getScaleY();
			b.center.set(sprite.getX(), sprite.getY()).add(b.width/2,b.height/2);
			this.data = b;
		}
		
		this.object = sprite;
		
		if(null!=callback)callback.before(this);
		this.setup();
		if(null!=callback)callback.after(this);
	}
	public Cb2Object(AdvanceSprite sprite,boolean circle){
		this(sprite, circle, null);
	}
	protected void setup(){
		if(null == this.data || null==this.object) {
			Gdx.app.error("C2d", "the body and the sprite must be not null");
			System.exit(0);
		}
		final Vector2 pos = new Vector2();
		if(data instanceof CircleData){
			final CircleData tmp = (CircleData)data;
			if(resizeObject){
				object.setSize(tmp.radius*2, tmp.radius*2);
			}
			pos.set(tmp.center).sub(tmp.radius, tmp.radius);
		}else if(data instanceof BoxData){
			final BoxData tmp = (BoxData)data;
			if(resizeObject){
				object.setSize(tmp.width, tmp.height);
			}
			pos.set(tmp.center).sub(tmp.width/2,tmp.height/2);
		}else if(data instanceof PolygonData){
			final PolygonData tmp = (PolygonData)data;
			if(resizeObject){
				object.setSize(tmp.width, tmp.height);
			}
			pos.set(tmp.center).sub(tmp.width/2,tmp.height/2);
		}
		this.object.setScale(1);
		
		if(null == this.data.body){
			this.data.build();
		}
		
		
		float shapeHalfWidth = this.object.getWidth()/2;
		float shapeHalfHeight = this.object.getHeight()/2;
		
		this.object.setOrigin(shapeHalfWidth,shapeHalfHeight);
		offset.set(shapeHalfWidth,shapeHalfHeight);
		
		this.setPosition(pos).setRotation(data.angle);
	}
	/**
	 * Note this method is not for direct use but for user who 
	 * want to supply the body and the sprite dynamic .
	 * After you supply it ,Remember call {@link #init()} to archive some base operations
	 */
	protected Cb2Object(){
		
	}
	
	/**
	 * this method is integrate with the {@link Engine} . used to draw the sprite also the graphic
	 *  Yes , this method can be directly called . And mostly it will be called by 
	 *  {@link Cb2World#render(float)}
	 *  if the object is a AnimationSprite , then we'll draw the animation instead only draw a simple sprite.
	 */
	public void render(float delta){
		final Vector2  position = data.body.getPosition();
		this.object.setPosition(position.x*Cb2World.RADIO -offset.x,position.y*Cb2World.RADIO -offset.y);
		this.object.setRotation(MathUtils.radiansToDegrees * data.body.getAngle());
		if(visiable)this.object.render(delta);
	}
	
	
	/**
	 * Yes , the position of the sprite . You must supply the sprite's position instead of the body's position .
	 * it more like the exact position of the sprite on the screen .
	 * It's not the center of the sprite object ,it's the zero position of the object  .
	 * Manipulating a body's transform may cause non-physical behavior. So you'd better 
	 * call this In the rigid body initialization and render method . Otherwise, most cases will have a JNI error .
	 */
	public Cb2Object setPosition(Vector2 position){
		return this.setPosition(position.x, position.y);
	}
	/**
	 * we use the sprite's x,y but not the physical body . Because you may dynamic change the object of the 
	 * PhysicalObject , so  we return the position use the body's position also with the offset.
	 */
	public final Vector2 getPosition(){
		return data.body.getPosition().mul(Cb2World.RADIO).sub(offset);
	}
	/**
	 * set the position use the camera's viewPort . the zero-zero is on the left bottom 
	 */
	public Cb2Object setPosition(final float x,final float y){
		this.object.setPosition(x, y);
		data.body.setTransform(
				new Vector2(x,y).add(offset).mul(1/Cb2World.RADIO), data.body.getAngle());
		return this;
	}
	
	public Cb2Object setRotation(float degrees){
		this.object.setRotation(degrees);
		data.body.setTransform(data.body.getPosition(), degrees*MathUtils.degreesToRadians);
		return this;
	}
	
	/**
	 * really delete it 
	 * make sure the game is running . if not . call {@link Engine#doResume() }
	 */
	public void dispose(){
		while(data.body.getJointList().iterator().hasNext()){
			Cb2World.getInstance().world().destroyJoint(data.body.getJointList().iterator().next().joint);
		}
		Cb2World.getInstance().world().destroyBody(data.body);
	}
}