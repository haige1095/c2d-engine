package info.u250.c2d.tests;

import info.u250.c2d.engine.Engine;
import info.u250.c2d.engine.EngineDrive;
import info.u250.c2d.engine.Scene;
import info.u250.c2d.engine.resources.AliasResourceManager;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;


public class OX extends Engine {
	
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
			reg.texture("blue", "data/blue.png");
		}
		@Override
		public void dispose() {}
		@Override
		public EngineOptions onSetupEngine() {
			final EngineOptions opt = new EngineOptions(new String[]{"data/blue.png"},800,480);
			return opt;
		}

		Matrix4 transform = new Matrix4();
		Texture texture ;
		@Override
		public void onLoadedResourcesCompleted() {
			texture = Engine.resource("blue");
			transform.setToTranslation(new Vector3(100,100,0));
			transform.rotate(new Vector3(0,1,1),20);
			Engine.setMainScene(new Scene() {
				@Override
				public void render(float delta) {
//					Engine.getShapeRenderer().setProjectionMatrix(Engine.getDefaultCamera().combined);
//					Engine.getShapeRenderer().setColor(Color.YELLOW);
//					Engine.getShapeRenderer().begin(ShapeType.Curve);
//					Engine.getShapeRenderer().curve(0, 0, 150, 150, 200, 400, 200, 100);
//					Engine.getShapeRenderer().curve(200, 100, 250, 250, 200, 400, 200, 100);
//					Engine.getShapeRenderer().end();
					Engine.getSpriteBatch().setTransformMatrix(transform);
					Engine.getSpriteBatch().begin();
					Engine.getSpriteBatch().draw(texture, 100, 100);
					Engine.getSpriteBatch().end();
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
