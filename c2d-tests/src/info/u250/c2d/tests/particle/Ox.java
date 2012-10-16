package info.u250.c2d.tests.particle;

import info.u250.c2d.engine.Engine;
import info.u250.c2d.engine.EngineDrive;
import info.u250.c2d.engine.Scene;
import info.u250.c2d.engine.resources.AliasResourceManager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;


public class Ox extends Engine {
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
			final EngineOptions opt = new EngineOptions(new String[]{},800,480);
			return opt;
		}

		private ShaderProgram shader;
		
		@Override
		public void onLoadedResourcesCompleted() {
			shader = new ShaderProgram(Gdx.files.internal("shaders/simple.vert"), Gdx.files.internal("shaders/toner.frag"));
			
			Engine.setMainScene(new Scene() {
				@Override
				public void render(float delta) {
					Engine.getSpriteBatch().setShader(shader);
				}
				@Override
				public InputProcessor getInputProcessor() {
					return null;
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
