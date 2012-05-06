package info.u250.c2d.tests;

import info.u250.c2d.engine.Engine;
import info.u250.c2d.engine.EngineDrive;
import info.u250.c2d.engine.Scene;
import info.u250.c2d.engine.resources.AliasResourceManager;
import info.u250.c2d.graphic.Thumbpad;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;


public class ThumbpadTest  extends Engine {
	@Override
	protected EngineDrive onSetupEngineDrive() {
		return new EngineX();
	}
	
	
	private class EngineX implements EngineDrive{
		@Override
		public void onResourcesRegister(AliasResourceManager<String> reg) {
			reg.texture("logo",  "data/c2d.png");
			reg.object("logoSprite",new Sprite(Engine.resource("logo",Texture.class)));
		}
		@Override
		public void dispose() {}
		@Override
		public EngineOptions onSetupEngine() {
			EngineOptions opt = new EngineOptions(new String[]{"data/c2d.png"},480,320);
			opt.resizeSync = false;
			return opt ;
		}
		Thumbpad  thumbpad;
		
		Sprite sprite ;

		@Override
		public void onLoadedResourcesCompleted() {
			sprite  = Engine.resource("logoSprite");
			sprite.setPosition(Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2);
			
			
			float radius = Math.min(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()) / 7;
			float offset = radius / 4;
			thumbpad = new Thumbpad((int) radius, new Vector2(radius + offset, radius+ offset), 0.0f);
			
			
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
				@Override
				public void render(float delta) {
					Engine.getSpriteBatch().begin();
					sprite.setPosition(
							Engine.getEngineConfig().width/2+thumbpad.getAmount()*Engine.getEngineConfig().width/2*(float)Math.cos(thumbpad.getAngle()),
							Engine.getEngineConfig().height/2+thumbpad.getAmount()*Engine.getEngineConfig().height/2*(float)Math.sin(thumbpad.getAngle()));
					sprite.draw(Engine.getSpriteBatch());
					Engine.getSpriteBatch().end();
					
					thumbpad.render(delta);
					
					Engine.debugInfo("by touch the Thumbpad .You can see the change of this. \n" +
							"the amount is :"+thumbpad.getAmount()+",\n" +
							"the angle is :"+thumbpad.getAngle()*MathUtils.radiansToDegrees);
				}
				
				@Override
				public InputProcessor getInputProcessor() {
					return new InputAdapter() {
						final Vector2 tmp = new Vector2();
						
						@Override
						public boolean touchUp(int x, int y, int pointer, int button) {
							this.tmp.set( Engine.screenToWorld(x, y) );
							thumbpad.touchUp((int)tmp.x , (int)tmp.y , pointer);
							return false;
						}
					
						
						@Override
						public boolean touchDragged(int x, int y, int pointer) {
							this.tmp.set( Engine.screenToWorld(x, y) );
							thumbpad.touchDragged((int)tmp.x , (int)tmp.y , pointer);
							return false;
						}
						
						@Override
						public boolean touchDown(int x, int y, int pointer, int button) {
							this.tmp.set( Engine.screenToWorld(x, y) );
							thumbpad.touchDown((int)tmp.x , (int)tmp.y , pointer);
							return false;
						}
						
					};
				}
			});
		}
	}

}
