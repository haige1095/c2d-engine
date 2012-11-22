package info.u250.c2d.tests.box2d;

import info.u250.c2d.engine.Engine;
import info.u250.c2d.engine.EngineDrive;
import info.u250.c2d.engine.Scene;
import info.u250.c2d.engine.resources.AliasResourceManager;
import info.u250.c2d.engine.service.Disposable;
import info.u250.c2d.engine.service.Renderable;
import info.u250.c2d.graphic.AdvanceSprite;
import info.u250.c2d.input.PhysicalFingerInput;
import info.u250.c2d.physical.box2d.Cb2Object;
import info.u250.c2d.physical.box2d.Cb2Object.Cb2ObjectSetupCallback;
import info.u250.c2d.physical.box2d.Cb2World;
import info.u250.c2d.physical.box2d.loader.cbt.data.CircleData;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;


public class WaterEffectTest  extends Engine {

	@Override
	protected EngineDrive onSetupEngineDrive() {
		return new EngineX();
	}
	
	
	private class EngineX implements EngineDrive{
		@Override
		public void onResourcesRegister(AliasResourceManager<String> reg) {
			reg.texture("A", "data/box2d/water.png");
		}
		@Override
		public void dispose() {}
		@Override
		public EngineOptions onSetupEngine() {
			EngineOptions opt = new EngineOptions(new String[]{"data/box2d/water.png"},800,480);
			opt.useGL20  = true;
			return opt;
		}
		PhysicalFingerInput input;
		
		@Override
		public void onLoadedResourcesCompleted() {
			input =new PhysicalFingerInput( Cb2World.getInstance().installDefaultWorld().createScreenBox());
			
			final Box2dWater water = new Box2dWater(new AdvanceSprite(Engine.resource("A",Texture.class)));
			Engine.setMainScene(new Scene() {
				@Override
				public void update(float delta) {
					Cb2World.getInstance().update(delta);
				}
				@Override
				public void hide() {}

				@Override
				public void show() {}
				@Override
				public void render(float delta) {
					water.render(delta);
				}
				
				@Override
				public InputProcessor getInputProcessor() {
					InputMultiplexer mul = new InputMultiplexer();
					mul.addProcessor(input);
					mul.addProcessor(new InputAdapter(){
						@Override
						public boolean touchUp(int x, int y, int pointer,
								int button) {
							water.createWaterDrop().setPosition(Engine.screenToWorld(x, y));
							return super.touchUp(x, y, pointer, button);
						}
					});
					return mul;
				}
			});
		}
	}
	
	
	@Override
	public void dispose () {
		Cb2World.getInstance().dispose();
		super.dispose();
	}
}

class Box2dWater implements Renderable, Disposable {
	static final String Eraser_vertexShader = "attribute vec4 "
			+ ShaderProgram.POSITION_ATTRIBUTE
			+ ";\n" //
		+ "attribute vec4 " + ShaderProgram.COLOR_ATTRIBUTE
			+ ";\n" //
		+ "attribute vec2 " + ShaderProgram.TEXCOORD_ATTRIBUTE
			+ "0;\n" //
		+ "uniform mat4 u_projTrans;\n" //
		+ "varying vec4 v_color;\n" //
		+ "varying vec2 v_texCoords;\n" //
		+ "\n" //
		+ "void main()\n" //
		+ "{\n" //
		+ "   v_color = " + ShaderProgram.COLOR_ATTRIBUTE
			+ ";\n" //
		+ "   v_texCoords = " + ShaderProgram.TEXCOORD_ATTRIBUTE
			+ "0;\n" //
		+ "   gl_Position =  u_projTrans * "
			+ ShaderProgram.POSITION_ATTRIBUTE + ";\n" //
		+ "}\n";
	static final String Eraser_fragmentShader = "#ifdef GL_ES\n" //
		+ "#define LOWP lowp\n"
			+ "precision mediump float;\n" //
		+ "#else\n"
			+ "#define LOWP \n"
			+ "#endif\n" //
		+ "varying LOWP vec4 v_color;\n" //
		+ "varying vec2 v_texCoords;\n" //
		+ "uniform sampler2D u_texture;\n" //
		+ "void main()\n"//
		+ "{\n" //
		+ "vec4 v_c = texture2D(u_texture, v_texCoords);\n" //
		+ "if (v_c.a < 0.15)\n"
			+ "   discard;\n"
			+ "v_c.a = v_c.a*0.75;\n"
			+ "gl_FragColor = v_c;\n"
			+ "}\n";
	
	//shader 
	static final String vertexShader = "attribute vec4 "
			+ ShaderProgram.POSITION_ATTRIBUTE
			+ ";\n" //
			+ "attribute vec4 " + ShaderProgram.COLOR_ATTRIBUTE
			+ ";\n" //
			+ "attribute vec2 " + ShaderProgram.TEXCOORD_ATTRIBUTE
			+ "0;\n" //
			+ "uniform mat4 u_projTrans;\n" //
			+ "varying vec4 v_color;\n" //
			+ "varying vec2 v_texCoords;\n" //
			+ "\n" //
			+ "void main()\n" //
			+ "{\n" //
			+ "   v_color = " + ShaderProgram.COLOR_ATTRIBUTE
			+ ";\n" //
			+ "   v_texCoords = " + ShaderProgram.TEXCOORD_ATTRIBUTE
			+ "0;\n" //
			+ "   gl_Position =  u_projTrans * "
			+ ShaderProgram.POSITION_ATTRIBUTE + ";\n" //
			+ "}\n";
	static final String fragmentShader = "#ifdef GL_ES\n" //
			+ "#define LOWP lowp\n"
			+ "precision mediump float;\n" //
			+ "#else\n"
			+ "#define LOWP \n"
			+ "#endif\n" //
			+ "varying LOWP vec4 v_color;\n" //
			+ "varying vec2 v_texCoords;\n" //
			+ "uniform sampler2D u_texture;\n" //
			+ "void main()\n"//
			+ "{\n" //
			+ "vec4 v_c = texture2D(u_texture, v_texCoords);\n" //
			+ "if (v_c.a < 0.25)\n"
			+ "   discard;\n"
			+ "v_c.a = 0.35;\n"
			+ "gl_FragColor = v_c;\n"
			+ "}\n";

	public Box2dWater(AdvanceSprite sprite) {
		if (!Gdx.graphics.isGL20Available())
			throw new IllegalStateException("can't use this tool with OpenGL ES 1.x");

		this.sprite = sprite;
		ShaderProgram.pedantic = false;
		eraser = new ShaderProgram(Eraser_vertexShader,Eraser_fragmentShader);
		shader = new ShaderProgram(vertexShader,fragmentShader);
		
		frameBuffer = new FrameBuffer(Pixmap.Format.RGBA8888,
				(int) Engine.getWidth(),
				(int) Engine.getHeight(), false);
		frameSprite = new AdvanceSprite(frameBuffer.getColorBufferTexture());
		frameSprite.flip(false, true);
	}

	public int getDropNumber() {
		return this.getAll().size;
	}


	private ShaderProgram eraser;
	private ShaderProgram shader;

	private FrameBuffer frameBuffer;
	private AdvanceSprite frameSprite;
	private AdvanceSprite sprite;
	private Pool<Cb2Object> objects = new Pool<Cb2Object>() {
		@Override
		protected Cb2Object newObject() {
			CircleData data = new CircleData();
			data.radius = sprite.getWidth()/2/2;
			data.restitution = 0.1f;
			data.friction = 0.01f;
			data.density = 3;
			
			Cb2Object object = new Cb2Object(data,sprite,new Cb2ObjectSetupCallback() {
				@Override
				public void before(Cb2Object obj) {
					obj.setResizeObject(false);
				}
				@Override
				public void after(Cb2Object obj) {}
			}).setPosition(100,100);
			drops.add(object);
			return object;
		}
	};
	
	private Array<Cb2Object> drops = new Array<Cb2Object>();
	
	public void removeDrop(Cb2Object drop){
		this.objects.free(drop);
		this.drops.removeValue(drop, false);
	}

	public Array<Cb2Object> getAll(){
		return this.drops;
	}
	public void freeAll(){
		for(Cb2Object o:drops){
			this.objects.free(o);
		}
		this.drops.clear();
	}

	public Cb2Object createWaterDrop() {
		return this.objects.obtain();
	}

	@Override
	public void dispose() {
		this.freeAll();
		this.frameBuffer.dispose();
		this.frameSprite.getTexture().dispose();
	}

	@Override
	public void render(float delta) {
		Engine.getSpriteBatch().setShader(null);
		frameBuffer.begin();
		Engine.getSpriteBatch().setShader(eraser);
		Engine.getSpriteBatch().begin();
		frameSprite.render(delta);
		Engine.getSpriteBatch().setBlendFunction(GL20.GL_ONE,GL20.GL_ONE_MINUS_SRC_ALPHA);
		for(Cb2Object object:drops){
			object.render(delta);
		}
		Engine.getSpriteBatch().end();	
		frameBuffer.end();
		
		Engine.getSpriteBatch().setBlendFunction(GL20.GL_SRC_ALPHA,GL20.GL_ONE_MINUS_SRC_ALPHA);
		Engine.getSpriteBatch().setShader(shader);
		Engine.getSpriteBatch().begin();
		frameSprite.draw(Engine.getSpriteBatch());
		Engine.getSpriteBatch().end();
		
		Engine.debugInfo("Touch the screen to add a new water drop");
	}
}
