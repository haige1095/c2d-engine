package info.u250.c2d.tests.tools;

import info.u250.c2d.engine.Engine;
import info.u250.c2d.engine.EngineDrive;
import info.u250.c2d.engine.Scene;
import info.u250.c2d.engine.resources.AliasResourceManager;
import info.u250.c2d.tools.motionwelder.MotionwelderSupport;
import info.u250.c2d.tools.motionwelder.support.MSimpleAnimationPlayer;

import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class MotionWelderTest extends Engine {
	@Override
	protected EngineDrive onSetupEngineDrive() {
		return new EngineX();
	}

	@Override
	public void dispose() {
		super.dispose();
	}

	private class EngineX implements EngineDrive {
		@Override
		public void onResourcesRegister(AliasResourceManager<String> reg) {
			reg.texture("Texture1", "data/tools/motionwelder/mongo.png");
			reg.texture("Texture2", "data/tools/motionwelder/banana.png");
			
			reg.texture("Texture3", "data/tools/motionwelder/get.png");
			reg.texture("Texture4", "data/tools/motionwelder/mainmenu.png");
			reg.texture("Texture5", "data/tools/motionwelder/gameover.png");
			
			reg.texture("Texture6", "data/tools/motionwelder/interface.png");
			reg.texture("Texture7", "data/tools/motionwelder/bg.png");
		}

		@Override
		public void dispose() {
		}

		@Override
		public EngineOptions onSetupEngine() {
			final EngineOptions opt = new EngineOptions(new String[] {
					"data/tools/motionwelder/"
			}, 800,480);
			opt.useGL20 = true;
			return opt;
		}

		@Override
		public void onLoadedResourcesCompleted() {
			final MSimpleAnimationPlayer mongo = MotionwelderSupport.makeMotionwelderAnimationPlayer("data/tools/motionwelder/mongo.anu", 
					new TextureRegion[]{
					new TextureRegion(Engine.resource("Texture1",Texture.class)),
					new TextureRegion(Engine.resource("Texture2",Texture.class))
			},100,100);
			mongo.setAnimation(0);
			
			final MSimpleAnimationPlayer animationsuite = MotionwelderSupport.makeMotionwelderAnimationPlayer("data/tools/motionwelder/animationsuite.anu", 
					new TextureRegion[]{
					new TextureRegion(Engine.resource("Texture3",Texture.class)),
					new TextureRegion(Engine.resource("Texture4",Texture.class)),
					new TextureRegion(Engine.resource("Texture5",Texture.class))
			},300,100);
			
			final MSimpleAnimationPlayer staticbg = MotionwelderSupport.makeMotionwelderAnimationPlayer("data/tools/motionwelder/interface.anu", 
					new TextureRegion[]{
					new TextureRegion(Engine.resource("Texture6",Texture.class)),
					new TextureRegion(Engine.resource("Texture7",Texture.class))
			},500,250);
			mongo.setAnimation(0);
			
			mongo.setAnimation(0);
			Engine.setMainScene(new Scene() {
				@Override
				public void render(float delta) {
					Engine.getSpriteBatch().begin();
					mongo.update(delta);
					mongo.render(delta);
					
					animationsuite.update(delta);
					animationsuite.render(delta);
					
					staticbg.update(delta);
					staticbg.render(delta);
					
					Engine.getSpriteBatch().draw(Engine.resource("Texture1",Texture.class),0,300);
					Engine.getSpriteBatch().draw(Engine.resource("Texture2",Texture.class),100,300);
					Engine.getSpriteBatch().draw(Engine.resource("Texture3",Texture.class),150,300);
					Engine.getSpriteBatch().draw(Engine.resource("Texture4",Texture.class),250,300);
					Engine.getSpriteBatch().draw(Engine.resource("Texture5",Texture.class),300,300);
					Engine.getSpriteBatch().draw(Engine.resource("Texture6",Texture.class),500,300);
					Engine.getSpriteBatch().draw(Engine.resource("Texture7",Texture.class),650,300);
					
					Engine.getSpriteBatch().end();
					
					Engine.debugInfo("Touch the screen to see different animations\nThe images in the top is the origi images");
				}

				int mongoAnimationId = 0;
				int animationSiuteAnimationId = 0;
				@Override
				public InputProcessor getInputProcessor() {
					return new InputAdapter() {
						@Override
						public boolean touchUp(int x, int y, int pointer,
								int button) {
							mongoAnimationId++;
							if(mongoAnimationId>3){
								mongoAnimationId=0;
							}
							mongo.setAnimation(mongoAnimationId);
							mongo.setSpriteX(100);
							mongo.setSpriteY(100);
							
							animationSiuteAnimationId++;
							if(animationSiuteAnimationId>4){
								animationSiuteAnimationId=0;
							}
							animationsuite.setAnimation(animationSiuteAnimationId);

							return super.touchUp(x, y, pointer, button);
						}
					};
				}

				@Override
				public void update(float delta) {
				}

				@Override
				public void hide() {
				}

				@Override
				public void show() {
				}
			});
		}
	}
}
