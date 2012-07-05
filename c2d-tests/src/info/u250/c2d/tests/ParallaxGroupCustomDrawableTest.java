package info.u250.c2d.tests;

import info.u250.c2d.accessors.C2dCameraAccessor;
import info.u250.c2d.engine.CoreProvider.StartupLoadingScreens;
import info.u250.c2d.engine.Engine;
import info.u250.c2d.engine.EngineDrive;
import info.u250.c2d.engine.Scene;
import info.u250.c2d.engine.resources.AliasResourceManager;
import info.u250.c2d.graphic.AnimationSprite;
import info.u250.c2d.graphic.parallax.ParallaxGroup;
import info.u250.c2d.graphic.parallax.ParallaxLayer;
import info.u250.c2d.graphic.parallax.SpriteParallaxLayerDrawable;
import info.u250.c2d.graphic.parallax.TileParallaxLayerDrawable;

import aurelienribon.tweenengine.Timeline;
import aurelienribon.tweenengine.Tween;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.tiled.TileAtlas;
import com.badlogic.gdx.graphics.g2d.tiled.TileMapRenderer;
import com.badlogic.gdx.graphics.g2d.tiled.TiledLoader;
import com.badlogic.gdx.graphics.g2d.tiled.TiledMap;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public class ParallaxGroupCustomDrawableTest extends Engine{
	@Override
	protected EngineDrive onSetupEngineDrive() {
		return new EngineX();
	}
	
	private class EngineX implements EngineDrive{
		@Override
		public void onResourcesRegister(AliasResourceManager<String> reg) {
			reg.textureAtlas("Anim",  "data/animationsprite/turkey.atlas");
			reg.texture("ANDROID", "data/android.png");
			reg.texture("LINUX", "data/linux.png");
		}
		@Override
		public void dispose() {}
		@Override
		public EngineOptions onSetupEngine() {
			final EngineOptions opt =  new EngineOptions(new String[]{"data/animationsprite/","data/android.png","data/linux.png"},800,480);
			opt.loading = StartupLoadingScreens.LineLoading;
			return opt;
		}
		
		ParallaxGroup rbg ;
		
		TileMapRenderer tileMapRenderer;
		TiledMap map;
		TileAtlas atlas;
		
		@Override
		public void onLoadedResourcesCompleted() {
			final String path = "data/tiledmap/";
			final String mapname = "map1.tmx";

			FileHandle mapHandle = Gdx.files.internal(path + mapname);
			FileHandle baseDir = Gdx.files.internal(path);

			map = TiledLoader.createMap(mapHandle);

			atlas = new TileAtlas(map, baseDir);

			int blockWidth = 10;
			int blockHeight = 12;

			tileMapRenderer = new TileMapRenderer(map, atlas, blockWidth, blockHeight);
			
			//rbg
			rbg = new ParallaxGroup(Engine.getEngineConfig().width, Engine.getEngineConfig().height, new Vector2(100,100), false);
			
			rbg.add(new ParallaxLayer("tile", new TileParallaxLayerDrawable(tileMapRenderer), 
					new Vector2(0.9f,1), new Vector2(), -1, -1));
			Sprite linux = new Sprite(Engine.resource("LINUX",Texture.class));
			linux.setScale(3f);
			rbg.add(new ParallaxLayer("android-1", new SpriteParallaxLayerDrawable(linux),
					new Vector2(0f,3f), new Vector2(1000,3000), -1, -1,new Vector2(300,1600)));
			rbg.add(new ParallaxLayer("parrot", new SpriteParallaxLayerDrawable(new AnimationSprite(0.05f, Engine.resource("Anim",TextureAtlas.class),"fly")),
					new Vector2(2f,0f), new Vector2(1000,1000), -1, -1));
			Sprite android = new Sprite(Engine.resource("ANDROID",Texture.class));
			android.setScale(0.5f);
			rbg.add(new ParallaxLayer("android-2", new SpriteParallaxLayerDrawable(android),
					new Vector2(0.3f,0f), new Vector2(200,1000), -1, -1));
		
			//stop  it 
			rbg.setSpeed(0, 0);
			//tween it 
			final Vector3 positon = rbg.getCamera().position ;
			Timeline.createSequence()
			.push(Tween.to(rbg.getCamera(), C2dCameraAccessor.XY, 5000).target(positon.x + 32*60-Engine.getEngineConfig().width,positon.y ))
			.push(Tween.to(rbg.getCamera(), C2dCameraAccessor.XY, 5000).target(positon.x + 32*60-Engine.getEngineConfig().width,positon.y + 32*40-Engine.getEngineConfig().height))
			.push(Tween.to(rbg.getCamera(), C2dCameraAccessor.XY, 5000).target(positon.x ,positon.y + 32*40-Engine.getEngineConfig().height))
			.push(Tween.to(rbg.getCamera(), C2dCameraAccessor.XY, 5000).target(positon.x ,positon.y ))
			.repeat(-1, 0)
			.start(Engine.getTweenManager());
			
			Engine.setMainScene(new Scene() {
				@Override
				public void update(float delta) {
				}
				@Override
				public void hide() {}

				@Override
				public void show() {	}
				@Override
				public void render(float delta) {
					rbg.render(delta);

					Engine.debugInfo("The parallax background with layer number:"+rbg.getLayers().size+"\n" +
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