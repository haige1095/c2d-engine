package info.u250.c2d.tests;

import info.u250.c2d.engine.Engine;
import info.u250.c2d.engine.EngineDrive;
import info.u250.c2d.engine.Scene;
import info.u250.c2d.engine.resources.AliasResourceManager;
import info.u250.c2d.graphic.AnimationSprite;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;


public class AnimationSpriteLoopWithTimesTest extends Engine {
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
			reg.textureAtlas("ParrotAtlas",  "data/animationsprite/pack/pack");
		}
		
		@Override
		public void onLoadedResourcesCompleted() {
			TextureAtlas atlas = Engine.resource("ParrotAtlas");
			sprite = new AnimationSprite(new float[]{
					0.1f,
					0.1f,
					0.02f,
					0.05f,
					0.04f,
					0.05f,
					0.09f,
					0.01f,
					0.02f,
			}, new TextureRegion[]{
					atlas.findRegion("parrot0001"),
					atlas.findRegion("parrot0002"),
					atlas.findRegion("parrot0003"),
					atlas.findRegion("parrot0004"),
					atlas.findRegion("parrot0005"),
					atlas.findRegion("parrot0006"),
					atlas.findRegion("parrot0007"),
					atlas.findRegion("parrot0008"),
					atlas.findRegion("parrot0009"),
					atlas.findRegion("parrot0010"),
					
			});
			sprite.setPosition((Engine.getEngineConfig().width-sprite.getWidth())/2, 
					(Engine.getEngineConfig().height-sprite.getHeight())/2);
			sprite.setLoopTimes(2);
			sprite.setWaitingIndex(4);
			
			
			
			Engine.setMainScene(new Scene() {
				@Override
				public void render(float delta) {
					Engine.getSpriteBatch().begin();
					sprite.render( delta);
					Engine.getSpriteBatch().end();
					
					Engine.debugInfo(  "animation with different keyframes duration .\n touch the screen to replay the animation .\n touch the sprite to handle the onClick event ");
					
					if(Gdx.input.isTouched()){
						toggle = !toggle;
						if(toggle){
							
						}else{
							sprite.replay();
						}
					}
					
				}
				@Override
				public void update(float delta) {
				
					
				}
				@Override
				public InputProcessor getInputProcessor() {
					return null;
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

