package info.u250.c2d.tests.misc;

import info.u250.c2d.engine.Engine;
import info.u250.c2d.engine.EngineDrive;
import info.u250.c2d.engine.Scene;
import info.u250.c2d.engine.resources.AliasResourceManager;
import info.u250.c2d.input.GestureRecognizerInput;
import info.u250.c2d.input.GestureRecognizerInput.GestureRecognizerListener;
import info.u250.c2d.input.UnistrokeGestureRecognizerInput;

import com.badlogic.gdx.InputProcessor;


public class GestureRecognizerTest extends Engine {
	@Override
	protected EngineDrive onSetupEngineDrive() {
		return new EngineX();
	}
	@Override
	public void dispose () {
		super.dispose();
	}
	
	private class EngineX implements EngineDrive{
		@Override
		public void onResourcesRegister(AliasResourceManager<String> reg) {
		}
		@Override
		public void dispose() {}
		@Override
		public EngineOptions onSetupEngine() {
			return new EngineOptions(new String[]{},800,480);
		}

		String result ;
		@Override
		public void onLoadedResourcesCompleted() {
			final GestureRecognizerInput input = new UnistrokeGestureRecognizerInput(
					new String[]{
							"data/gestures/rectangle.json",
							"data/gestures/triangle.json",
							"data/gestures/x.json",
					},new GestureRecognizerListener() {
						@Override
						public void onMatch(String name, float score) {
							result = "Name:"+name+",Score:"+score;
						}
					}); 
			
			Engine.setMainScene(new Scene() {
				@Override
				public void render(float delta) {
					
					Engine.debugInfo("Draw a rect , a triangle , a x , to see the result. \n\n result:"+ result);
				}
				
				@Override
				public InputProcessor getInputProcessor() {
					return input;
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
