package info.u250.c2d.tests.tiled;

import info.u250.c2d.engine.Engine;
import info.u250.c2d.engine.EngineDrive;
import info.u250.c2d.engine.Scene;
import info.u250.c2d.engine.resources.AliasResourceManager;
import info.u250.c2d.graphic.AdvanceSprite;
import info.u250.c2d.graphic.AnimationSprite;
import info.u250.c2d.physical.box2d.Cb2Object;
import info.u250.c2d.physical.box2d.Cb2World;
import info.u250.c2d.physical.box2d.loader.cbt.data.BoxData;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.tiled.TileAtlas;
import com.badlogic.gdx.graphics.g2d.tiled.TileMapRenderer;
import com.badlogic.gdx.graphics.g2d.tiled.TiledLoader;
import com.badlogic.gdx.graphics.g2d.tiled.TiledMap;
import com.badlogic.gdx.graphics.g2d.tiled.TiledObject;
import com.badlogic.gdx.graphics.g2d.tiled.TiledObjectGroup;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;


public class Box2dTileTest extends Engine {
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
		private class SceneMain implements Scene{
			final TileMapRenderer render;
			Cb2Object player;
			Array<BoxData> blocks = new Array<BoxData>();
			OrthographicCamera camera;
			public SceneMain(){
				camera = new OrthographicCamera();
				camera.viewportWidth = Engine.getEngineConfig().width;
				camera.viewportHeight = Engine.getEngineConfig().height;
				camera.position.set(camera.viewportWidth/2, camera.viewportHeight/2, 0);
				
				
				final String path = "data/tiledmap/";
				final String mapname = "events.tmx";
			
				FileHandle mapHandle = Gdx.files.internal(path + mapname);
				FileHandle baseDir = Gdx.files.internal(path);
			
				final TiledMap map = TiledLoader.createMap(mapHandle);
				final TileAtlas atlas = new TileAtlas(map, baseDir);
				
				Cb2World.getInstance().installDefaultWorld();
				render = new TileMapRenderer(map, atlas, 5, 5);
				for (TiledObjectGroup group : map.objectGroups) { 
		            for (TiledObject object : group.objects) { 
		                if ("player".equals(object.name)) {
		                	AdvanceSprite sprite = new AnimationSprite(0.05f, new TextureRegion[]{
		    						new TextureRegion(Engine.resource("A1",Texture.class)),
		    						new TextureRegion(Engine.resource("A2",Texture.class)),
		    						new TextureRegion(Engine.resource("A3",Texture.class))});
		                	BoxData data = new BoxData();
		                	data.restitution = 0;
		                	data.width = sprite.getWidth();
		                	data.height =sprite.getHeight();
		                	data.isDynamic=true;
		                	player = new Cb2Object(data, sprite);
		                	player.setPosition(object.x,render.getMapHeightUnits()-object.y);
		                	player.data.body.setFixedRotation(true);
		                } else{
		                	BoxData boxData = new BoxData();
		                	boxData.width = object.width;
		                	boxData.height = object.height;
		                	boxData.isDynamic = false;
		                	boxData.center.set(object.x, render.getMapHeightUnits()-object.y).add(object.width/2,-object.height/2);
		                	boxData.build();
		                	blocks.add(boxData);
		                }
		            } 
		        }
			}
			
			
			Vector2 moveVector = new Vector2();
			private void changeDirect(int typeId) {
		        switch (typeId) {
		        case 1:
		            moveVector.set(0, 50);//up
		            player.data.body.applyLinearImpulse(new Vector2(0, 5),player.data.body.getWorldCenter());
		            break;
		        case 2:
		            moveVector.set(0, -50);//down
		            break;
		        case 3:
		            moveVector.set(-50, 0);//left
		            player.data.body.applyLinearImpulse(new Vector2(-5,0),player.data.body.getWorldCenter());
		            break;
		        case 4:
		            moveVector.set(50, 0);//right
		            player.data.body.applyLinearImpulse(new Vector2(5, 0),player.data.body.getWorldCenter());
		            break;
		        }
		    }
			
			@Override
			public void update(float delta) {
				Cb2World.getInstance().update(delta);
				boolean mapMove = true;
				
				if(this.camera.position.x-this.camera.viewportWidth/2  <=-(this.render.getMapWidthUnits()-Engine.getEngineConfig().width )&& moveVector.x>0)mapMove = false ;
				if(this.camera.position.y-this.camera.viewportHeight/2 <=-(this.render.getMapWidthUnits()-Engine.getEngineConfig().height)&& moveVector.y>0)mapMove = false ;
				if(this.camera.position.x-this.camera.viewportWidth/2  >=0  && moveVector.x<0)mapMove = false ;
				if(this.camera.position.y-this.camera.viewportHeight/2 >=0  && moveVector.y<0)mapMove = false ;
				if(mapMove){
//					this.camera.position.x -= moveVector.x*delta;
//					this.camera.position.y -= moveVector.y*delta;
				}else{
					if(	( this.player.object.getX()<=0 && moveVector.x<0 )
						||( this.player.object.getX()>=Engine.getEngineConfig().width-this.player.object.getWidth()  && moveVector.x>0 )
						||( this.player.object.getY()<=0 && moveVector.y<0 )
						||( this.player.object.getY()>=Engine.getEngineConfig().height-this.player.object.getHeight()  && moveVector.y>0 ))return;
//					this.player.setPosition(this.player.object.getX()+moveVector.x*delta, this.player.object.getY()+moveVector.y*delta);
				}
			}

			@Override
			public void render(float delta) {
				camera.update();
				render.render(camera);
				Engine.getSpriteBatch().begin();
				player.render(delta);
				Engine.getSpriteBatch().end();
				Engine.getShapeRenderer().setProjectionMatrix(camera.combined);
				for(BoxData box : blocks){
					box.debug(Engine.getShapeRenderer());
				}
			}

			@Override
			public void show() {}

			@Override
			public void hide() {}

			@Override
			public InputProcessor getInputProcessor() {
				return new InputAdapter(){
					@Override
					public boolean keyDown(int keycode) {
						switch (keycode) {
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
						return super.keyDown(keycode);
					}
				};
			}
			
		}
	}
}
