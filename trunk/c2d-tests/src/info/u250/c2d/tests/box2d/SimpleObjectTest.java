package info.u250.c2d.tests.box2d;

import info.u250.c2d.engine.Engine;
import info.u250.c2d.engine.EngineDrive;
import info.u250.c2d.engine.Scene;
import info.u250.c2d.engine.resources.AliasResourceManager;
import info.u250.c2d.graphic.AdvanceSprite;
import info.u250.c2d.graphic.AnimationSprite;
import info.u250.c2d.input.PhysicalFingerInput;
import info.u250.c2d.physical.box2d.Cb2Object;
import info.u250.c2d.physical.box2d.Cb2Object.Cb2ObjectSetupCallback;
import info.u250.c2d.physical.box2d.Cb2ObjectGroup;
import info.u250.c2d.physical.box2d.Cb2World;

import java.util.Random;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;


public class SimpleObjectTest  extends Engine {

	@Override
	protected EngineDrive onSetupEngineDrive() {
		return new EngineX();
	}
	
	Cb2ObjectGroup group = new Cb2ObjectGroup();
	private class EngineX implements EngineDrive{
		@Override
		public void onResourcesRegister(AliasResourceManager<String> reg) {
			reg.texture("logo", "data/c2d.png");
			reg.textureAtlas("Anim",  "data/animationsprite/turkey.atlas");
		}
		@Override
		public void dispose() {}
		@Override
		public EngineOptions onSetupEngine() {
			return new EngineOptions(new String[]{"data/c2d.png","data/animationsprite/"},800,480);
		}
		PhysicalFingerInput input;
		@Override
		public void onLoadedResourcesCompleted() {			
			input =new PhysicalFingerInput(
					Cb2World.getInstance().installDefaultWorld().createScreenBox());
			
			final AnimationSprite sprite =  new AnimationSprite(0.05f, Engine.resource("Anim",TextureAtlas.class),"fly");
			sprite.enableShadow();
			
			group.add(new Cb2Object(sprite, false)
			.setPosition(new Vector2((Engine.getEngineConfig().width-sprite.getWidth())/2, 
					(Engine.getEngineConfig().height-sprite.getHeight())/2)));
			
			//some ball
			final AdvanceSprite sprite2  = new AdvanceSprite(Engine.resource("logo",Texture.class));
			sprite2.setScale(0.5f);
			for(int i = 0;i<20;i++){
				group.add(
				new Cb2Object(sprite2, true,new Cb2ObjectSetupCallback() {
					@Override
					public void before(Cb2Object obj) {}
					
					@Override
					public void after(Cb2Object obj) {
						obj.setPosition(new Vector2((Engine.getEngineConfig().width-sprite2.getWidth())/2, 200));
						obj.data.body.setLinearVelocity(new Random().nextFloat()*10, new Random().nextFloat()*10);
					}
				})
			);
		}
			
			Engine.setMainScene(new Scene() {
				@Override
				public void update(float delta) {
					Cb2World.getInstance().update(delta);
				}
				@Override
				public void hide() {
					
					
				}

				@Override
				public void show() {
					
					
				}
				@Override
				public void render(float delta) {
					Engine.getSpriteBatch().begin();
					group.render(delta);
					Engine.getSpriteBatch().end();
					group.debug(Engine.getShapeRenderer());
					Engine.debugInfo( "Make a physical object is simple \njust new a PhysicalObject");
					
				}
				
				@Override
				public InputProcessor getInputProcessor() {
					return input;
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
