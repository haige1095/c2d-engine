package info.u250.c2d.tests.box2d;

import info.u250.c2d.engine.Engine;
import info.u250.c2d.engine.EngineDrive;
import info.u250.c2d.engine.Scene;
import info.u250.c2d.engine.resources.AliasResourceManager;
import info.u250.c2d.graphic.AdvanceSprite;
import info.u250.c2d.physical.box2d.Cb2Object;
import info.u250.c2d.physical.box2d.Cb2Object.Cb2ObjectSetupCallback;
import info.u250.c2d.physical.box2d.Cb2ObjectGroup;
import info.u250.c2d.physical.box2d.Cb2World;
import info.u250.c2d.physical.box2d.loader.cbt.data.CircleData;

import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;


public class KinematicBodiesTest  extends Engine {

	@Override
	protected EngineDrive onSetupEngineDrive() {
		return new EngineX();
	}
	
	Cb2ObjectGroup group = new Cb2ObjectGroup();
	private class EngineX implements EngineDrive{
		@Override
		public void onResourcesRegister(AliasResourceManager<String> reg) {
			reg.texture("A1", "data/yellow.png");
			reg.texture("A2", "data/green.png");
		}
		@Override
		public void dispose() {}
		@Override
		public EngineOptions onSetupEngine() {
			return new EngineOptions(new String[]{"data/yellow.png","data/green.png"},800,480);
		}
		@Override
		public void onLoadedResourcesCompleted() {
			Cb2World.getInstance().installDefaultWorld().createScreenBox();
			//some ball
			final AdvanceSprite sprite  = new AdvanceSprite(Engine.resource("A1",Texture.class));
			sprite.setSize(32, 32);
			final AdvanceSprite sprite2  = new AdvanceSprite(Engine.resource("A2",Texture.class));
			sprite2.setColor(Color.CYAN);
			sprite2.setSize(32, 32);
			for (int i=0; i<10; i++) {
				// building 10 kinematic spheres
				CircleData data = new CircleData();
				data.radius = sprite.getWidth()/2;
				data.isKinematic = true;
				final int index = i;
				group.add(new Cb2Object(data, sprite,new Cb2ObjectSetupCallback() {
					@Override
					public void before(Cb2Object obj) {}
					@Override
					public void after(Cb2Object obj) {
						obj.data.body.setLinearVelocity((index%2==0?-1:1)*10, 0);
					}
				}).setPosition(100+60*i, 80+30*i));
			}
			
			
			Engine.setMainScene(new Scene() {
				@Override
				public void update(float delta) {
					Cb2World.getInstance().update(delta);
					for(Cb2Object obj : group){
						if(obj.getPosition().x < 0 || obj.getPosition().x > Engine.getWidth() - obj.object.getWidth() ){
							obj.data.body.setLinearVelocity(-obj.data.body.getLinearVelocity().x, 0);
						}
					}
				}
				@Override
				public void hide() {
					
					
				}

				@Override
				public void show() {
					
					
				}
				@Override
				public void render(float delta) {
					Engine.getSpriteBatch().begin();
					group.render(delta);
					Engine.getSpriteBatch().end();
//					group.debug(render);
					
					Engine.getDefaultFont().setColor(Color.WHITE);
					Engine.debugInfo("A kinematic body is an hybrid body which is not affected \n" +
							"by forces and collisions like a static body \n" +
							"but can moved with a linear velocity like a dynamic body. ");
					
					Engine.getDefaultFont().setColor(Color.YELLOW);
					Engine.debugInfo("\n\n\n\nA dynamic body is a body which is affected by world forces and react to collisions.");
					
					Engine.getDefaultFont().setColor(Color.ORANGE);
					Engine.debugInfo("\n\n\n\n\n\n\nA static body is a body which isn’t affected by world forces it does \n" +
							"not react to collisions. It can’t be moved. Fullstop.");
				}
				
				@Override
				public InputProcessor getInputProcessor() {
					return new InputAdapter(){
						@Override
						public boolean touchUp(int x, int y, int pointer,int button) {
							group.add(new Cb2Object(sprite2,true).setPosition(Engine.screenToWorld(x, y)));
							return super.touchUp(x, y, pointer, button);
						}
					};
				}
			});
		}
	}
	
	
	@Override
	public void dispose () {
		Cb2World.getInstance().dispose();
		super.dispose();
	}


}
