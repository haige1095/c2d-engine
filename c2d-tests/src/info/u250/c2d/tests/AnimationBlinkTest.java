package info.u250.c2d.tests;

import info.u250.c2d.accessors.SpriteAccessor;
import info.u250.c2d.engine.Engine;
import info.u250.c2d.engine.EngineDrive;
import info.u250.c2d.engine.Scene;
import info.u250.c2d.engine.resources.AliasResourceManager;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.equations.Bounce;
import aurelienribon.tweenengine.equations.Sine;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;


public class AnimationBlinkTest extends Engine {
	@Override
	protected EngineDrive onSetupEngineDrive() {
		return new EngineX();
	}
	private class EngineX implements EngineDrive{
		@Override
		public void dispose() {}
		
		@Override
		public EngineOptions onSetupEngine() {
			return new EngineOptions(new String[]{"data/android.png","data/linux.png"},800,480);
		}
		@Override
		public void onResourcesRegister(AliasResourceManager<String> reg) {
			reg.texture("LINUX", "data/linux.png");
			reg.texture("ANDROID", "data/android.png");
		}
		
		private Sprite sprite1;
		private Sprite sprite2;
		
		@Override
		public void onLoadedResourcesCompleted() {
			sprite1 = new Sprite(Engine.resource("LINUX",Texture.class));
			sprite1.setPosition(300, 50);
			sprite2 = new Sprite(Engine.resource("ANDROID",Texture.class));
			sprite2.setPosition(sprite1.getX()+sprite2.getWidth(), sprite1.getY());
			Tween.to(sprite1, SpriteAccessor.OPACITY, 500)
			.ease(Bounce.INOUT)
			.target(0)
			.repeat(-1, 200)
			.delay(0)
			.start(Engine.getTweenManager());

			Tween.to(sprite2, SpriteAccessor.OPACITY, 100)
			.ease(Sine.OUT)
			.target(0)
			.repeatYoyo(-1, 0)
			.start(Engine.getTweenManager());
		
			Engine.setMainScene(new Scene() {
				
				@Override
				public void render(float delta) {
					Engine.getSpriteBatch().begin();
					sprite1.draw(Engine.getSpriteBatch());
					sprite2.draw(Engine.getSpriteBatch());
					Engine.getSpriteBatch().end();
					
					Engine.debugInfo("Blink via to change the alpha value of the sprite");
				}
				
				@Override
				public InputProcessor getInputProcessor() {
					return null;
				}

				@Override
				public void update(float delta) {}

				@Override
				public void hide() {}

				@Override
				public void show() {}
			});
		}
	}
}

