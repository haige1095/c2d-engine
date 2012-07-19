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
			final MSimpleAnimationPlayer player = MotionwelderSupport.makeMotionwelderAnimationPlayer("data/tools/motionwelder/mongo.anu", 
					new TextureRegion[]{
					new TextureRegion(Engine.resource("Texture1",Texture.class)),
					new TextureRegion(Engine.resource("Texture2",Texture.class))
			},100,100);
			player.setAnimation(0);
			Engine.setMainScene(new Scene() {
				@Override
				public void render(float delta) {
					Engine.getSpriteBatch().begin();
					player.update(delta);
					player.render(delta);
					
					Engine.getSpriteBatch().draw(Engine.resource("Texture1",Texture.class),100,300);
					Engine.getSpriteBatch().draw(Engine.resource("Texture2",Texture.class),300,300);
					Engine.getSpriteBatch().end();
					
					Engine.debugInfo("Touch the screen to see different animations\nThe images in the top is the origi images");
				}

				int animationId = 0;
				@Override
				public InputProcessor getInputProcessor() {
					return new InputAdapter() {
						@Override
						public boolean touchUp(int x, int y, int pointer,
								int button) {
							animationId++;
							if(animationId>3){
								animationId=0;
							}
							player.setAnimation(animationId);
							player.setSpriteX(100);
							player.setSpriteY(100);

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
