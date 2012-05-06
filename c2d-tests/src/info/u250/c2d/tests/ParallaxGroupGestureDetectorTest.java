package info.u250.c2d.tests;


import info.u250.c2d.engine.CoreProvider.StartupLoadingScreens;
import info.u250.c2d.engine.Engine;
import info.u250.c2d.engine.EngineDrive;
import info.u250.c2d.engine.Scene;
import info.u250.c2d.engine.resources.AliasResourceManager;
import info.u250.c2d.graphic.parallax.ParallaxGroup;
import info.u250.c2d.input.CameraControllerInRange;
import info.u250.c2d.input.CameraControllerInRange.CameraControllerInRangeAdapter;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;


public class ParallaxGroupGestureDetectorTest  extends Engine{
	@Override
	protected EngineDrive onSetupEngineDrive() {
		return new EngineX();
	}
	
	
	private class EngineX implements EngineDrive{
		@Override
		public void onResourcesRegister(AliasResourceManager<String> reg) {
			reg.textureAtlas("bgAtlas", "data/parallax/pack/pack");
		}
		@Override
		public void dispose() {}
		ParallaxGroup rbg ;
		@Override
		public EngineOptions onSetupEngine() {
			final EngineOptions opt =  new EngineOptions(new String[]{"data/"},480,320);
			opt.loading = StartupLoadingScreens.WindmillLoading;
			return opt;
		}
		
		@Override
		public void onLoadedResourcesCompleted() {
			rbg = ParallaxGroup.load(
					Engine.resource("bgAtlas",TextureAtlas.class),
					Gdx.files.internal("data/parallax/parallax.txt"));
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
							"The zoom of the backgroud="+1/rbg.getCamera().getZoom()+"\n" +
							"The speed of the backgroud="+rbg.getSpeed());
				}
				
				@Override
				public InputProcessor getInputProcessor() {
//					return rbg.enableGestureDetector().getGestureDetector();
					CameraControllerInRangeAdapter ca=new CameraControllerInRangeAdapter(rbg.getCamera());
					return rbg.enableGeBackground(new CameraControllerInRange(ca),ca).getGestureDetector();
				}
			});

			
		}
	}
}
