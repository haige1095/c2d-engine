package info.u250.c2d.tests;

import info.u250.c2d.engine.Engine;
import info.u250.c2d.engine.EngineDrive;
import info.u250.c2d.engine.Scene;
import info.u250.c2d.engine.resources.AliasResourceManager;
import info.u250.c2d.graphic.AnimationSprite;
import info.u250.c2d.graphic.AnimationSpriteImage;
import info.u250.c2d.graphic.C2dStage;
import info.u250.c2d.tiled.TileMapRenderer;
import info.u250.c2d.tiled.TiledObjectDrawable;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.tiled.TileAtlas;
import com.badlogic.gdx.graphics.g2d.tiled.TiledLoader;
import com.badlogic.gdx.graphics.g2d.tiled.TiledMap;
import com.badlogic.gdx.graphics.g2d.tiled.TiledObject;
import com.badlogic.gdx.graphics.g2d.tiled.TiledObjectGroup;
import com.badlogic.gdx.math.Vector2;


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
			reg.texture("A1", "data/tiledmap/lesma0.gif");
			reg.texture("A2", "data/tiledmap/lesma1.gif");
			reg.texture("A3", "data/tiledmap/lesma2.gif");
		}
		@Override
		public void dispose() {}
		@Override
		public EngineOptions onSetupEngine() {
			final EngineOptions opt = new EngineOptions(new String[]{
					"data/tiledmap/lesma0.gif",
					"data/tiledmap/lesma1.gif",
					"data/tiledmap/lesma2.gif",
			},800,480);
			opt.useGL20 = true;
			return opt;
		}

		@Override
		public void onLoadedResourcesCompleted() {
			Engine.setMainScene(new SceneMain());	
		}
		private class SceneMain extends C2dStage implements Scene{
			final TileMapRenderer render;
			TiledObjectDrawable player;
			public SceneMain(){
				final String path = "data/tiledmap/";
				final String mapname = "events.tmx";
			
				FileHandle mapHandle = Gdx.files.internal(path + mapname);
				FileHandle baseDir = Gdx.files.internal(path);
			
				final TiledMap map = TiledLoader.createMap(mapHandle);
				final TileAtlas atlas = new TileAtlas(map, baseDir);
				
				render = new TileMapRenderer(this,map,atlas){
					@Override
					public void processObjectGroups() {
						for (TiledObjectGroup group : map.objectGroups) { 
				            for (TiledObject object : group.objects) { 
				                if ("player".equals(object.name)) { 
				                	player = new TiledObjectDrawable(this,object,new AnimationSpriteImage( 
				                			new AnimationSprite(0.05f, new TextureRegion[]{
				    						new TextureRegion(Engine.resource("A1",Texture.class)),
				    						new TextureRegion(Engine.resource("A2",Texture.class)),
				    						new TextureRegion(Engine.resource("A3",Texture.class)),
				    				})));
				                	SceneMain.this.addActor(player);
				                } 
				            } 
				        }
					}
				};			
			}
			@Override
			public boolean keyUp(int keyCode) {
				switch (keyCode) {
				case Keys.LEFT:
					changeDirect(3);
					break;
				case Keys.RIGHT:
					changeDirect(4);
					break;
				case Keys.UP:
					changeDirect(1);
					break;
				case Keys.DOWN:
					changeDirect(2);
					break;
				default:
					break;
				}
				return super.keyUp(keyCode);
			}
			
			Vector2 moveVector = new Vector2();
			private void changeDirect(int typeId) {
		        switch (typeId) {
		        case 1:
		            moveVector.set(0, 50);
		            break;
		        case 2:
		            moveVector.set(0, -50);
		            break;
		        case 3:
		            moveVector.set(-50, 0);
		            break;
		        case 4:
		            moveVector.set(50, 0);
		            break;
		        }
		    }
			
			@Override
			public void update(float delta) {
				this.act(delta);
				boolean mapMove = true;
				if(this.render.getX()<=-(this.render.getWidth()-Engine.getEngineConfig().width )&& moveVector.x>0)mapMove = false ;
				if(this.render.getY()<=-(this.render.getHeight()-Engine.getEngineConfig().height)&& moveVector.y>0)mapMove = false ;
				if(this.render.getX()>=0  && moveVector.x<0)mapMove = false ;
				if(this.render.getY()>=0  && moveVector.y<0)mapMove = false ;
				if(mapMove){
					this.render.setX(this.render.getX()-moveVector.x*delta);
					this.render.setY(this.render.getY()-moveVector.y*delta);
				}else{
					if(	( this.player.getX()<=0 && moveVector.x<0 )
						||( this.player.getX()>=Engine.getEngineConfig().width-this.player.getWidth()  && moveVector.x>0 )
						||( this.player.getY()<=0 && moveVector.y<0 )
						||( this.player.getY()>=Engine.getEngineConfig().height-this.player.getHeight()  && moveVector.y>0 ))return;
					this.player.setX(this.player.getX()+moveVector.x*delta);
					this.player.setY(this.player.getY()+moveVector.y*delta);
				}
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
