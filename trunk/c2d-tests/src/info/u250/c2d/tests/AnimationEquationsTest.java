package info.u250.c2d.tests;

import info.u250.c2d.accessors.SpriteAccessor;
import info.u250.c2d.engine.Engine;
import info.u250.c2d.engine.EngineDrive;
import info.u250.c2d.engine.Scene;
import info.u250.c2d.engine.resources.AliasResourceManager;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenEquation;
import aurelienribon.tweenengine.equations.Back;
import aurelienribon.tweenengine.equations.Bounce;
import aurelienribon.tweenengine.equations.Circ;
import aurelienribon.tweenengine.equations.Cubic;
import aurelienribon.tweenengine.equations.Elastic;
import aurelienribon.tweenengine.equations.Expo;
import aurelienribon.tweenengine.equations.Linear;
import aurelienribon.tweenengine.equations.Quad;
import aurelienribon.tweenengine.equations.Quart;
import aurelienribon.tweenengine.equations.Quint;
import aurelienribon.tweenengine.equations.Sine;

import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;


public class AnimationEquationsTest extends Engine {
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
		
		int current = 0;
		private TweenEquation[] equations = new TweenEquation[]{
				Back.IN,
				Bounce.IN,
				Circ.IN,
				Cubic.IN,
				Elastic.IN,
				Expo.IN,
				Linear.INOUT,
				Quad.IN,
				Quart.IN,
				Quint.IN,
				Sine.IN
		};
		TweenEquation t;
		
		@Override
		public void onLoadedResourcesCompleted() {
			sprite1 = new Sprite(Engine.resource("LINUX",Texture.class));
			sprite1.setPosition(300, 50);
			sprite2 = new Sprite(Engine.resource("ANDROID",Texture.class));
			sprite2.setPosition(sprite1.getX()+sprite2.getWidth(), sprite1.getY());
			
		
			Engine.setMainScene(new Scene() {
				
				@Override
				public void render(float delta) {
					Engine.getSpriteBatch().begin();
					sprite1.draw(Engine.getSpriteBatch());
					sprite2.draw(Engine.getSpriteBatch());
					Engine.getSpriteBatch().end();
					
					Engine.debugInfo("Let's make our sprite move , click the screen to switch next equation! \n" +
							" current:"+t);
				}
				
				@Override
				public InputProcessor getInputProcessor() {
					return new InputAdapter(){
						@Override
						public boolean touchDown(int x, int y, int pointer,
								int button) {
							Engine.getTweenManager().killAll();
							
							t = equations[current];
							sprite1.setPosition(0, 0);
							Tween.to(sprite1, SpriteAccessor.POSITION_XY, 2500)
							.ease(t)
							.target(Engine.getEngineConfig().width-sprite1.getWidth(), 0)
							.repeat(-1, 1000)
							.start(Engine.getTweenManager());

							sprite2.setPosition(0, Engine.getEngineConfig().height-sprite2.getHeight());
							Tween.to(sprite2, SpriteAccessor.POSITION_XY, 2500)
							.ease(t)
							.target(Engine.getEngineConfig().width-sprite2.getWidth(),Engine.getEngineConfig().height-sprite2.getHeight() )
							.repeatYoyo(-1, 1000)
							.delay(100)
							.start(Engine.getTweenManager());
							
							current++;
							if(current>equations.length-1){
								current = 0;
							}
							return super.touchDown(x, y, pointer, button);
						}
					};
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

