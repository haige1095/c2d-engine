package info.u250.c2d.tests.box2d;

import info.u250.c2d.engine.Engine;
import info.u250.c2d.engine.EngineDrive;
import info.u250.c2d.engine.Scene;
import info.u250.c2d.engine.resources.AliasResourceManager;
import info.u250.c2d.graphic.AdvanceSprite;
import info.u250.c2d.physical.box2d.Cb2Object;
import info.u250.c2d.physical.box2d.Cb2World;

import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;


public class PhysicTrackLineTest extends Engine {
	@Override
	protected EngineDrive onSetupEngineDrive() {
		return new EngineX();
	}
	@Override
	public void dispose () {
		Cb2World.getInstance().dispose();
		super.dispose();
	}
	
	private class EngineX implements EngineDrive{

		@Override
		public EngineOptions onSetupEngine() {
			return new EngineOptions(new String[]{
					"data/c2d.png",
					"data/box2d/branch.png"
					},800,480);
		}
		@Override
		public void onResourcesRegister(AliasResourceManager<String> reg) {
			reg.texture("Ball", "data/c2d.png");
			reg.texture("Branch", "data/box2d/branch.png");
		}
		
		@Override
		public void onLoadedResourcesCompleted() {
			Cb2World.getInstance().installDefaultWorld().createScreenBox();
			
			Engine.setMainScene(new Scene() {

				AdvanceSprite ball ;
				AdvanceSprite branch ;
				Array<Vector2> tracks = new Array<Vector2>();
				@Override
				public void render(float delta) {
					Engine.getSpriteBatch().begin();
					branch.render(delta);
					if(object==null){
						ball.render(delta);
					}else{
						object.render(delta);
					}
					Engine.getSpriteBatch().end();
					
					Engine.getShapeRenderer().begin(ShapeType.FilledCircle);
					Engine.getShapeRenderer().setColor(Color.WHITE);
					for(Vector2 v:tracks){
						Engine.getShapeRenderer().filledCircle(v.x, v.y, 3);
					}
					Engine.getShapeRenderer().end();
					if(object!=null)object.data.debug(Engine.getShapeRenderer());
					Engine.debugInfo(" Physic Track .  Drag to fire~");
					
				}
				boolean bTouch = false;
				Cb2Object object  = null;
				@Override
				public InputProcessor getInputProcessor() {
					
					return new InputAdapter(){
						Vector2 offset = new Vector2();
						@Override
						public boolean touchDown(int x, int y, int pointer,
								int button) {
							
							if(null!=object){
								Cb2World.getInstance().world().destroyBody(object.data.body);
								object=null;
								ball.setRotation(0);
								ball.setPosition(200, 100);
							}else{
								Vector2 v = Engine.screenToWorld(x, y);
								if(ball.getBoundingRectangle().contains(v.x,v.y)){
									offset.set(ball.getX(),ball.getY()).sub(v);
									bTouch = true;
								}
							}
							
							return super.touchDown(x, y, pointer, button);
						}

						@Override
						public boolean touchUp(int x, int y, int pointer,
								int button) {
							if(bTouch){
								Vector2 touchPosition = Engine.screenToWorld(x, y);
								makeTrack(touchPosition.cpy());
								
								object =  new Cb2Object(ball,true).setPosition(touchPosition);
								Vector2 basePostion = new Vector2(branch.getWidth()/2+branch.getX(),branch.getY()+branch.getHeight());
								float distance = basePostion.dst(touchPosition);
								float angle = touchPosition.sub(basePostion).angle();
								object.data.body.setLinearVelocity(
										-distance*MathUtils.cos(angle*MathUtils.degreesToRadians)/Cb2World.RADIO*4, 
										-distance*MathUtils.sin(angle*MathUtils.degreesToRadians)/Cb2World.RADIO*4);
								bTouch = false;
							}
							
							return super.touchUp(x, y, pointer, button);
						}

						
						@Override
						public boolean touchDragged(int x, int y, int pointer) {
							if(bTouch){
								makeTrack(Engine.screenToWorld(x, y));
							}
							return super.touchDragged(x, y, pointer);
						}
						
						private void makeTrack(Vector2 touchPosition){
							tracks.clear();
							ball.setPosition(touchPosition.x,touchPosition.y);
							
							Cb2Object tmpPhysicalObject = new Cb2Object(ball,true).setPosition(touchPosition);
							Vector2 basePostion = new Vector2(branch.getWidth()/2+branch.getX(),branch.getY()+branch.getHeight());
							float distance = basePostion.dst(touchPosition);
							float angle = touchPosition.sub(basePostion).angle();
							tmpPhysicalObject.data.body.setLinearVelocity(
									-distance*MathUtils.cos(angle*MathUtils.degreesToRadians)/Cb2World.RADIO*4, 
									-distance*MathUtils.sin(angle*MathUtils.degreesToRadians)/Cb2World.RADIO*4);
							
							for(int i=0;i<150;i++){
								Cb2World.getInstance().world().step(1/30f, 10, 10);
								tracks.add(tmpPhysicalObject.data.body.getPosition().cpy().mul(Cb2World.RADIO));
							}
							
							Cb2World.getInstance().world().destroyBody(tmpPhysicalObject.data.body);
						}
						
					};
				}
				
				@Override
				public void hide() {
				}

				@Override
				public void show() {
					ball = new AdvanceSprite(Engine.resource("Ball", Texture.class));
					ball.setPosition(200, 150);
					branch =  new AdvanceSprite(Engine.resource("Branch",Texture.class));
					branch.setPosition(200, -50);
				}
				@Override
				public void update(float delta) {
					Cb2World.getInstance().update(delta);
				}
			});
		}
		@Override
		public void dispose() {
			
		}
	}
}
