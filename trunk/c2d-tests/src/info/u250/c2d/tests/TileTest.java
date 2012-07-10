package info.u250.c2d.tests;

import info.u250.c2d.engine.Engine;
import info.u250.c2d.engine.EngineDrive;
import info.u250.c2d.engine.Scene;
import info.u250.c2d.engine.resources.AliasResourceManager;
import info.u250.c2d.graphic.C2dStage;
import info.u250.c2d.tiled.TileMapRenderer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.tiled.TileAtlas;
import com.badlogic.gdx.graphics.g2d.tiled.TiledLoader;
import com.badlogic.gdx.graphics.g2d.tiled.TiledMap;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;


public class TileTest extends Engine {
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

		@Override
		public void onLoadedResourcesCompleted() {
			Engine.setMainScene(new SceneMain());	
		}
		private class SceneMain extends C2dStage implements Scene{
			public SceneMain(){
				final String path = "data/tiledmap/";
				final String mapname = "map1.tmx";
			
				FileHandle mapHandle = Gdx.files.internal(path + mapname);
				FileHandle baseDir = Gdx.files.internal(path);
			
				final TiledMap map = TiledLoader.createMap(mapHandle);
				final TileAtlas atlas = new TileAtlas(map, baseDir);
				
				final TileMapRenderer render = new TileMapRenderer(map,atlas);
				
				final ScrollPane pane = new ScrollPane(render);
				pane.setSize(Engine.getEngineConfig().width, Engine.getEngineConfig().height);
				this.addActor(pane);
			}
			@Override
			public void update(float delta) {
				this.act(delta);
			}

			@Override
			public void render(float delta) {
				this.draw();
			}

			@Override
			public void show() {}

			@Override
			public void hide() {}

			@Override
			public InputProcessor getInputProcessor() {
				return this;
			}
			
		}
	}
}
