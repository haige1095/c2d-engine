package info.u250.c2d.tests;

import info.u250.c2d.engine.Engine;
import info.u250.c2d.engine.EngineDrive;
import info.u250.c2d.engine.Scene;
import info.u250.c2d.engine.resources.AliasResourceManager;
import info.u250.svg.SVGParse;
import info.u250.svg.elements.SVGRootElement;
import info.u250.svg.glutils.SVGTextureData;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Texture;


public class SvgTest extends Engine {
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
			opt.gl20Enable = true;
			return opt;
		}

		@Override
		public void onLoadedResourcesCompleted() {
			SVGRootElement svgFile  = new SVGRootElement();
			svgFile.format = 4;
			svgFile.width = 512;
			svgFile.height = 512;

			svgFile.min_x = 0;
			svgFile.min_y = 0;
			svgFile.max_x = 0;
			svgFile.max_y = 0;

			svgFile.scale = 1f;

			SVGParse parse = new SVGParse (Gdx.files.internal ("data/tiger.svg"));

			parse.parse (svgFile);

			final Texture texture = new Texture (new SVGTextureData (svgFile));
			
			Engine.setMainScene(new Scene() {
				@Override
				public void render(float delta) {
					Engine.getSpriteBatch().begin();
					Engine.getSpriteBatch().draw(texture,0,0);
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
