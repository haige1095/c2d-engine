package info.u250.c2d.tests;


import info.u250.c2d.engine.CoreProvider.StartupLoadingScreens;
import info.u250.c2d.engine.Engine;
import info.u250.c2d.engine.EngineDrive;
import info.u250.c2d.engine.Scene;
import info.u250.c2d.engine.resources.AliasResourceManager;
import info.u250.c2d.graphic.parallax.ParallaxGroup;
import info.u250.c2d.updatable.Day2NightEvent;
import info.u250.c2d.updatable.ShakeCameraEvent;
import info.u250.c2d.updatable.SpeedCameraEvent;
import info.u250.c2d.updatable.ZoomCameraEvent;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;


public class ParallaxGroupEventsTest extends Engine{
	@Override
	protected EngineDrive onSetupEngineDrive() {
		return new EngineX();
	}
	
	
	private class EngineX implements EngineDrive{
		@Override
		public void onResourcesRegister(AliasResourceManager<String> reg) {
			reg.textureAtlas("bgAtlas", "data/parallax/bg.atlas");
		}
		@Override
		public void dispose() {}
		@Override
		public EngineOptions onSetupEngine() {
			final EngineOptions opt =  new EngineOptions(new String[]{"data/"},800,480);
			opt.loading = StartupLoadingScreens.LineLoading;
			return opt;
		}
		
		ParallaxGroup rbg ;
		

		private  ShakeCameraEvent ShakeCameraEvent ;
		private  Day2NightEvent   Day2NightEvent   ;
		private  ZoomCameraEvent  ZoomCameraEvent  ;
		private  SpeedCameraEvent SpeedCameraEvent ;
		
		
		@Override
		public void onLoadedResourcesCompleted() {
			rbg = ParallaxGroup.load(
					Engine.resource("bgAtlas",TextureAtlas.class),
					Gdx.files.internal("data/parallax/parallax.txt"));
			
			ShakeCameraEvent = new ShakeCameraEvent(rbg.getCamera(),8, 4, 1f);
			Day2NightEvent   = new Day2NightEvent(2, 0.3f, true);
			ZoomCameraEvent  = new ZoomCameraEvent(rbg.getCamera(),5, 2f){
				@Override
				public void end() {
					this.camera.setZoom(1);
					super.end();
				}
			};
			SpeedCameraEvent = new SpeedCameraEvent(rbg,3f, new Vector2(1000, 0)) {
				@Override
				public void begin() {	}
				@Override
				public void end() {
					this.setSpeed(50, 0);
				}
				@Override
				public void render(float delta) { }
			};
			//must enable the light
			Engine.addUpdatable("Day2NightEvent", Day2NightEvent);
			
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
				private float deltaAppend = 0.0f;
				@Override
				public void render(float delta) {
					if(!Gdx.graphics.isGL20Available()){
						Gdx.graphics.getGL10().glEnable(GL10.GL_LIGHTING);
					}
					
					rbg.render(delta);
					
					deltaAppend+= Gdx.graphics.getDeltaTime();
					if((int)deltaAppend%9==2){
						Engine.addUpdatable("ShakeCameraEvent", ShakeCameraEvent);
					}
					
					if((int)deltaAppend%9==4){
						Engine.addUpdatable("SpeedCameraEvent", SpeedCameraEvent);
					}
					
					if((int)deltaAppend%9==8){
						Engine.addUpdatable("ZoomCameraEvent", ZoomCameraEvent);
					}
					if(!Gdx.graphics.isGL20Available()){
						Gdx.graphics.getGL10().glDisable(GL10.GL_LIGHTING);
					}
					
					Engine.debugInfo("The parallax background with layer number:"+rbg.getLayers().size+"\n" +
							"*With event: \n" +
							ShakeCameraEvent+"\n" +
							SpeedCameraEvent+"\n" +
							ZoomCameraEvent+"\n" +
							Day2NightEvent+"\n" +
							"The position of the backgroud="+rbg.getCamera().position+"\n" +
							"The zoom of the backgroud="+1/rbg.getCamera().getZoom()+"\n" +
							"The speed of the backgroud="+rbg.getSpeed());
					
				}
				
				@Override
				public InputProcessor getInputProcessor() {
					return null;
				}
			});
		}
	}

}
