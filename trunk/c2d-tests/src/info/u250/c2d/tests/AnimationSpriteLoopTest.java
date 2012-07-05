package info.u250.c2d.tests;

import info.u250.c2d.accessors.SpriteAccessor;
import info.u250.c2d.engine.Engine;
import info.u250.c2d.engine.EngineDrive;
import info.u250.c2d.engine.Scene;
import info.u250.c2d.engine.resources.AliasResourceManager;
import info.u250.c2d.graphic.AnimationSprite;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.equations.Back;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;


public class AnimationSpriteLoopTest extends Engine {
	@Override
	protected EngineDrive onSetupEngineDrive() {
		return new EngineX();
	}
	private class EngineX implements EngineDrive{
		@Override
		public void dispose() {}
		AnimationSprite sprite ;
		boolean toggle = false;
		@Override
		public EngineOptions onSetupEngine() {
			return new EngineOptions(new String[]{"data/animationsprite/"},480,320);
		}
		@Override
		public void onResourcesRegister(AliasResourceManager<String> reg) {
			reg.textureAtlas("Anim",  "data/animationsprite/turkey.atlas");
		}
		
		@Override
		public void onLoadedResourcesCompleted() {
			sprite = new AnimationSprite(0.05f, Engine.resource("Anim",TextureAtlas.class),"fly");
			sprite.setPosition((Engine.getEngineConfig().width-sprite.getWidth())/2, 
					(Engine.getEngineConfig().height-sprite.getHeight())/2);
			
			Tween.to(sprite, SpriteAccessor.POSITION_XY, 5000)
			.ease(Back.INOUT)
			.target(sprite.getX(),sprite.getY()+100 )
			.repeatYoyo(-1, 100)
			.start(Engine.getTweenManager());
			
			Engine.setMainScene(new Scene() {
				
				@Override
				public void render(float delta) {
					Engine.getSpriteBatch().begin();
					sprite.render( delta);
					Engine.getSpriteBatch().end();
					
					Engine.debugInfo( "animation with loop . touch the screen to stop \n and touch again to begin ");
					
					if(Gdx.input.isTouched()){
						toggle = !toggle;
						if(toggle){
							sprite.setAlphaModulation(0.2f);
							sprite.stop();
						}else{
							sprite.setAlphaModulation(1f);
							sprite.play();
						}
					}
					
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

