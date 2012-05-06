//package info.u250.c2d.physical.box2d.water;
//
//import info.u250.c2d.engine.Engine;
//import info.u250.c2d.engine.service.Disposable;
//import info.u250.c2d.engine.service.Renderable;
//import info.u250.c2d.graphic.AdvanceSprite;
//import info.u250.c2d.physical.box2d.Cb2Manager;
//import info.u250.c2d.physical.box2d.Cb2Object;
//import info.u250.c2d.utils.Box2dlUtils;
//
//import com.badlogic.gdx.Application.ApplicationType;
//import com.badlogic.gdx.Gdx;
//import com.badlogic.gdx.graphics.Color;
//import com.badlogic.gdx.graphics.GL20;
//import com.badlogic.gdx.graphics.Pixmap;
//import com.badlogic.gdx.graphics.g2d.Sprite;
//import com.badlogic.gdx.graphics.glutils.FrameBuffer;
//import com.badlogic.gdx.graphics.glutils.ShaderProgram;
//import com.badlogic.gdx.math.Vector2;
//import com.badlogic.gdx.physics.box2d.Body;
//import com.badlogic.gdx.physics.box2d.BodyDef;
//import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
//import com.badlogic.gdx.physics.box2d.CircleShape;
//import com.badlogic.gdx.physics.box2d.FixtureDef;
//import com.badlogic.gdx.utils.Array;
//import com.badlogic.gdx.utils.Pool;
//
//public class Box2dWater implements Renderable, Disposable {
//	public Box2dWater(AdvanceSprite sprite) {
//		if (!Gdx.graphics.isGL20Available())
//			throw new IllegalStateException("can't use this tool with OpenGL ES 1.x");
//
//		this.sprite = sprite;
//		
//		eraser = Eraser.createShader();
//		shader = Shaders.createShader();
//
//		if (Gdx.app.getType() == ApplicationType.Android) {
//			frameBuffer = new FrameBuffer(Pixmap.Format.RGBA4444,
//					(int) Engine.getEngineConfig().width,
//					(int) Engine.getEngineConfig().height, false);
//		} else {
//			frameBuffer = new FrameBuffer(Pixmap.Format.RGBA8888,
//					(int) Engine.getEngineConfig().width,
//					(int) Engine.getEngineConfig().height, false);
//		}
//		frameSprite = new Sprite(frameBuffer.getColorBufferTexture());
//		frameSprite.flip(false, true);
//	}
//
//	public int getDropNumber() {
//		return this.getAll().size;
//	}
//
//
//	private ShaderProgram eraser;
//	private ShaderProgram shader;
//
//	private FrameBuffer frameBuffer;
//	private Sprite frameSprite;
//	private AdvanceSprite sprite;
//	private Pool<Cb2Object> objects = new Pool<Cb2Object>() {
//		@Override
//		protected Cb2Object newObject() {
//			BodyDef boxBodyDef;
//			CircleShape waterDrop;
//			FixtureDef fixDef;
//			
//			boxBodyDef = new BodyDef();
//			boxBodyDef.type = BodyType.DynamicBody;
//			boxBodyDef.angularDamping = 0.6f;
//
//			waterDrop = new CircleShape();
//			waterDrop.setRadius(Box2dlUtils.getCircleShapeRadius(sprite)/2);
//			fixDef = new FixtureDef();
//			fixDef.restitution = 0.1f;
//			fixDef.friction = 0.01f;
//			fixDef.density = 3f;
//			fixDef.shape = waterDrop;
//
//			Body boxBody = Cb2Manager.getInstance().world().createBody(boxBodyDef);
//			boxBody.createFixture(fixDef);
//			
//			Cb2Object object = new Cb2Object(sprite,boxBody){
//				public void render(float delta) {
//					final Vector2 velocity = body.getLinearVelocity();
//					final float vDt = velocity.len();
//					final float c = vDt <= 1f ? vDt : 1f;
//					Engine.getSpriteBatch().setColor(LUT[(int) ((LUT_SIZE - 1) * c)]);
//					super.render(delta);
//				};
//			}.setPosition(100,100);
//			drops.add(object);
//			waterDrop.dispose();
//			return object;
//		}
//	};
//	
//	private Array<Cb2Object> drops = new Array<Cb2Object>();
//
//	static final float WHITE = Color.toFloatBits(1f, 1f, 1f, 1f);
//	private static final int LUT_SIZE = 128;
//	private static final float[] LUT = new float[LUT_SIZE];
//	static {
//		float static_alpha = 0.8f;
//		float minC = 0.7f;
//		float tmp = 1f - minC;
//		for (int i = 0; i < LUT_SIZE; i++) {
//			float c = minC + tmp * (i / (float) LUT_SIZE);
//			LUT[i] = Color.toFloatBits(c, c, c, static_alpha);
//		}
//	}
//	
//	public void removeDrop(Cb2Object drop){
//		this.objects.free(drop);
//		this.drops.removeValue(drop, false);
//	}
//
//	public Array<Cb2Object> getAll(){
//		return this.drops;
//	}
//	public void freeAll(){
//		this.objects.free(this.drops);
//		this.drops.clear();
//	}
//
//	public Cb2Object createWaterDrop() {
//		return this.objects.obtain();
//	}
//
//	@Override
//	public void dispose() {
//		this.freeAll();
//		this.frameBuffer.dispose();
//		this.frameSprite.getTexture().dispose();
//	}
//
//	@Override
//	public void render(float delta) {
//		Engine.getSpriteBatch().setShader(null);
//		frameBuffer.begin();
//		{
//			if (Gdx.app.getType() == ApplicationType.Android) {
//				Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
//			}
//			Engine.getSpriteBatch().setShader(eraser);
//			Engine.getSpriteBatch().begin();
//			frameSprite.draw(Engine.getSpriteBatch());
//			Engine.getSpriteBatch().end();
//			Engine.getSpriteBatch().setShader(null);
//		
//			Engine.getSpriteBatch().setBlendFunction(GL20.GL_ONE,GL20.GL_ONE_MINUS_SRC_ALPHA);
//			Engine.getSpriteBatch().begin();
//			for(Cb2Object object:drops){
//				object.render(delta);
//			}
//			Engine.getSpriteBatch().end();	
//		}
//		frameBuffer.end();
//		Engine.getSpriteBatch().setBlendFunction(GL20.GL_SRC_ALPHA,GL20.GL_ONE_MINUS_SRC_ALPHA);
//		Engine.getSpriteBatch().setColor(WHITE);
//		Engine.getSpriteBatch().setShader(shader);
//		Engine.getSpriteBatch().begin();
//		frameSprite.draw(Engine.getSpriteBatch());
//		Engine.getSpriteBatch().end();
//		Engine.getSpriteBatch().setShader(null);
//		Engine.getSpriteBatch().setBlendFunction(GL20.GL_SRC_ALPHA,GL20.GL_ONE_MINUS_SRC_ALPHA);
//		Engine.getSpriteBatch().setColor(WHITE);
//	}
//}
