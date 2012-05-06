package info.u250.c2d.tests;

import info.u250.c2d.accessors.SpriteAccessor;
import info.u250.c2d.engine.Engine;
import info.u250.c2d.engine.EngineDrive;
import info.u250.c2d.engine.Scene;
import info.u250.c2d.engine.resources.AliasResourceManager;
import aurelienribon.tweenengine.Timeline;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.equations.Back;
import aurelienribon.tweenengine.equations.Quad;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;


public class AnimationSimpleTimelineTest extends Engine {
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
			
			Timeline.createSequence()
			.push(Tween.to(sprite1, SpriteAccessor.POSITION_XY, 700).target(2, -1).ease(Quad.IN).repeatYoyo(1, 200))
			.beginParallel()
				.push(Tween.to(sprite1, SpriteAccessor.ROTATION, 1000).target(360).ease(Quad.INOUT))
				.push(Tween.to(sprite1, SpriteAccessor.OPACITY, 500).target(0).ease(Quad.INOUT).repeatYoyo(1, 0))
				.repeat(1, 200)
			.end()
			.push(Tween.set(sprite1, SpriteAccessor.ROTATION).target(10))
			.push(Tween.to(sprite1, SpriteAccessor.POSITION_XY, 700).target(-1, -1).ease(Quad.IN))
			.push(Tween.to(sprite1, SpriteAccessor.ROTATION, 500).target(360).ease(Quad.INOUT))
			.repeatYoyo(1, 200)
			.repeat(-1, 0)
			.start(Engine.getTweenManager());
			
			Tween.to(sprite2, SpriteAccessor.POSITION_XY, 200)
			.ease(Back.INOUT)
			.target(sprite2.getX(),sprite2.getY()+100 )
			.repeatYoyo(-1, 100)
			.start(Engine.getTweenManager());
		
			Engine.setMainScene(new Scene() {
				
				@Override
				public void render(float delta) {
					Engine.getSpriteBatch().begin();
					sprite1.draw(Engine.getSpriteBatch());
					sprite2.draw(Engine.getSpriteBatch());
					Engine.getSpriteBatch().end();
					
					Engine.debugInfo("Let's make our sprite move !");
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

