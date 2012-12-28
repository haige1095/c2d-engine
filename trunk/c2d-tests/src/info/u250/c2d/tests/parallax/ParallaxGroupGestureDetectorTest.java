package info.u250.c2d.tests.parallax;

import info.u250.c2d.engine.Engine;
import info.u250.c2d.engine.EngineDrive;
import info.u250.c2d.engine.Scene;
import info.u250.c2d.engine.load.startup.StartupLoading;
import info.u250.c2d.engine.load.startup.WindmillLoading;
import info.u250.c2d.engine.resources.AliasResourceManager;
import info.u250.c2d.graphic.AdvanceSprite;
import info.u250.c2d.graphic.parallax.ParallaxGroup;
import info.u250.c2d.graphic.parallax.ParallaxGroup.DefaultParallaxGroupGestureListener;
import info.u250.c2d.graphic.parallax.ParallaxLayer;
import info.u250.c2d.graphic.parallax.SpriteParallaxLayerDrawable;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector2;


public class ParallaxGroupGestureDetectorTest  extends Engine{
	@Override
	protected EngineDrive onSetupEngineDrive() {
		return new EngineX();
	}
	
	@Override
	protected StartupLoading getStartupLoading() {
		return new WindmillLoading();
	}
	
	private class EngineX implements EngineDrive{
		@Override
		public void onResourcesRegister(AliasResourceManager<String> reg) {
			reg.textureAtlas("bgAtlas", "data/parallax/bg.atlas");
		}
		@Override
		public void dispose() {}
		ParallaxGroup rbg ;
		@Override
		public EngineOptions onSetupEngine() {
			final EngineOptions opt =  new EngineOptions(new String[]{"data/parallax/bg.atlas"},480,320);
			return opt;
		}
		
		@Override
		public void onLoadedResourcesCompleted() {
			final TextureAtlas bgAtlas = Engine.resource("bgAtlas",TextureAtlas.class);
			rbg = new ParallaxGroup(480, 320, new Vector2(50,100), false);
			rbg.add(new ParallaxLayer("bg",new SpriteParallaxLayerDrawable(new AdvanceSprite(bgAtlas.findRegion("bg"))),new Vector2(),new Vector2(0,1000),-1,-1));
			rbg.add(new ParallaxLayer("cloud",new SpriteParallaxLayerDrawable(new AdvanceSprite(bgAtlas.findRegion("cloud"))),new Vector2(0.5f,0),new Vector2(0,100),-1,-1,new Vector2(0,70)));
			rbg.add(new ParallaxLayer("front",new SpriteParallaxLayerDrawable(new AdvanceSprite(bgAtlas.findRegion("front"))),new Vector2(1f,0),new Vector2(0,1000),-1,-1));
			rbg.add(new ParallaxLayer("tree",new SpriteParallaxLayerDrawable(new AdvanceSprite(bgAtlas.findRegion("dock-tree"))),new Vector2(1f,0),new Vector2(1000,500),-1,-1));

			rbg.setSpeed(0, 0);
			
			Engine.setMainScene(new Scene() {
				@Override
				public void update(float delta) {
				
					
				}
				@Override
				public void hide() {
					
					
				}

				@Override
				public void show() {
					
					
				}
				@Override
				public void render(float delta) {
					rbg.render(delta);
					
					Engine.debugInfo("The parallax background with layer number:"+rbg.getLayers().size+"\n" +
							"GestureDetector by make a gesture use your mouse or your finger\n"+
							"The position of the backgroud="+rbg.getCamera().position+"\n" +
							"The zoom of the backgroud="+1/rbg.getCamera().zoom+"\n" +
							"The speed of the backgroud="+rbg.getSpeed());
				}
				
				@Override
				public InputProcessor getInputProcessor() {
					DefaultParallaxGroupGestureListener ca=new DefaultParallaxGroupGestureListener(rbg.getCamera());
					return rbg.enableGeBackground(new GestureDetector(ca),ca).getGestureDetector();
				}
			});

			
		}
	}
}
