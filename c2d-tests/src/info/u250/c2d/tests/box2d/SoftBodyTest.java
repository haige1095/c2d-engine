package info.u250.c2d.tests.box2d;

import info.u250.c2d.engine.Engine;
import info.u250.c2d.engine.EngineDrive;
import info.u250.c2d.engine.Scene;
import info.u250.c2d.engine.resources.AliasResourceManager;
import info.u250.c2d.input.PhysicalFingerInput;
import info.u250.c2d.physical.box2d.Cb2World;
import info.u250.c2d.physical.box2d.loader.cbt.data.CircleData;
import info.u250.c2d.physical.box2d.loader.cbt.data.DistanceJointData;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;


public class SoftBodyTest extends Engine {
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
			reg.texture("Ball", "data/box2d/Ball.png");
		}
		@Override
		public void dispose() {}
		@Override
		public EngineOptions onSetupEngine() {
			final EngineOptions opt = new EngineOptions(new String[]{
					"data/box2d/Ball.png"
			},800,480);
			return opt;
		}
		final int NUM_SEGMENTS = 16;
		final float SPRING = 10.0f;
		@Override
		public void onLoadedResourcesCompleted() {
			Cb2World.getInstance().installDefaultWorld();
			final PhysicalFingerInput input = new PhysicalFingerInput(Cb2World.getInstance().createScreenBox());
			
			final CircleData center = new CircleData();
			final Array<CircleData> circles = new Array<CircleData>();
			final Array<DistanceJointData> joints = new Array<DistanceJointData>();
			center.radius = 10;
			center.density = 0.1f;
			center.restitution = 0.05f;
			center.friction = 1;
			center.center.set(300,200);
			center.isDynamic = true;
			center.build();
			for(int i=0;i<NUM_SEGMENTS;i++){
				float x = MathUtils.cosDeg(360/NUM_SEGMENTS*i)*50;
				float y = MathUtils.sinDeg(360/NUM_SEGMENTS*i)*50;
				CircleData subCircleData = new CircleData();
				subCircleData.radius = 10;
				subCircleData.density = 0.1f;
				subCircleData.restitution = 0.05f;
				subCircleData.friction = 1;				
				subCircleData.center.set(center.center).add(x,y);
				subCircleData.isDynamic = true;
				subCircleData.build();
				circles.add(subCircleData);
			}
			for (int i = 0; i < NUM_SEGMENTS; i++) {
		        int neighborIndex = (i + 1) % NUM_SEGMENTS;
		        DistanceJointData joint1 = new DistanceJointData();
				joint1.bodyA = center	;
				joint1.bodyB = circles.get(i);
				joint1.collideConnected = false;
				joint1.frequencyHz = SPRING;
				joint1.dampingRatio = 0.1f;
				joint1.build();
				joints.add(joint1);
				
				DistanceJointData joint2 = new DistanceJointData();
				joint2.bodyA = circles.get(neighborIndex);
				joint2.bodyB = circles.get(i);
				joint2.collideConnected = false;
				joint2.frequencyHz = SPRING;
				joint2.dampingRatio = 0.1f;
				joint2.build();
				joints.add(joint2);
			}
			
			final Texture texture = Engine.resource("Ball");
			final Mesh	 mesh = new Mesh(false, NUM_SEGMENTS+1+1, NUM_SEGMENTS+1+1, 
		                new VertexAttribute(Usage.Position, 3, "a_position"),
		                new VertexAttribute(Usage.TextureCoordinates, 2, "a_texCoords"),
		                new VertexAttribute(Usage.Color, 4, "a_color"));
                         
		      final float[] vertices = new float[(NUM_SEGMENTS+1+1)*9];
		      final short[] indices = new short[NUM_SEGMENTS+1+1];
		      for (int i = 0; i < NUM_SEGMENTS+1+1; i++) {
					indices[i] = (short) i;
		      }
		      mesh.setIndices(indices);

			Engine.setMainScene(new Scene() {
				private void bounce(){
					Vector2 impulse = new Vector2( center.body.getMass()*0,center.body.getMass()*150);
					Vector2 impulsePoint = center.body.getPosition();
					center.body.applyLinearImpulse(impulse,impulsePoint);
				}
				private void debug(){
					center.debug(Engine.getShapeRenderer());
					for(CircleData c:circles){
						c.debug(Engine.getShapeRenderer());
					}
					for(DistanceJointData j:joints){
						j.debug(Engine.getShapeRenderer());
					}
				}
				@Override
				public void render(float delta) {
					/* ohoh do not forgot set the matrix */
					Engine.getShapeRenderer().setProjectionMatrix(Engine.getDefaultCamera().combined);
					
					vertices[0] = center.body.getPosition().x*Cb2World.RADIO;
					vertices[1] = center.body.getPosition().y*Cb2World.RADIO;
					vertices[2] = 0;
					vertices[3] = 0.5f;
					vertices[4] = 0.5f;
					vertices[5] = 1;
					vertices[6] = 1f;
					vertices[7] = 1f;
					vertices[8] = 1f;
					for(int i=0;i<NUM_SEGMENTS;i++){
						vertices[(i+1)*9+0] = circles.get(i).body.getPosition().x*Cb2World.RADIO ;
						vertices[(i+1)*9+1] = circles.get(i).body.getPosition().y*Cb2World.RADIO;
						vertices[(i+1)*9+2] = 0 ;
						vertices[(i+1)*9+3] = MathUtils.cosDeg(360/NUM_SEGMENTS*i)*0.5f+0.5f ;
						vertices[(i+1)*9+4] = MathUtils.sinDeg(360/NUM_SEGMENTS*i)*0.5f+0.5f ;
						
						vertices[(i+1)*9+5] = 1 ;
						vertices[(i+1)*9+6] = 1 ;
						vertices[(i+1)*9+7] = 1 ;
						vertices[(i+1)*9+8] = 1 ;
					}
					vertices[(NUM_SEGMENTS+1)*9+0] = vertices[9+0];
					vertices[(NUM_SEGMENTS+1)*9+1] = vertices[9+1];
					vertices[(NUM_SEGMENTS+1)*9+2] = vertices[9+2];
					vertices[(NUM_SEGMENTS+1)*9+3] = vertices[9+3];
					vertices[(NUM_SEGMENTS+1)*9+4] = vertices[9+4];
					vertices[(NUM_SEGMENTS+1)*9+5] = vertices[9+5];
					vertices[(NUM_SEGMENTS+1)*9+6] = vertices[9+5];
					vertices[(NUM_SEGMENTS+1)*9+7] = vertices[9+7];
					vertices[(NUM_SEGMENTS+1)*9+8] = vertices[9+8];
					mesh.setVertices(vertices);
					Gdx.graphics.getGL10().glEnable(GL10.GL_BLEND);
					Gdx.graphics.getGL10().glEnable(GL10.GL_TEXTURE_2D);
					texture.bind();
					mesh.render(GL10.GL_TRIANGLE_FAN);
					Gdx.graphics.getGL10().glDisable(GL10.GL_TEXTURE_2D);
					Gdx.graphics.getGL10().glDisable(GL10.GL_BLEND);
				   	this.debug();
					Engine.debugInfo(
							"This example shows how simple to achive the soft body use box2d .\n" +
							"Touch the screen to make the body bounce.\n" +
							"Do not touch quickly its not so strong as you think");
				}
				@Override
				public InputProcessor getInputProcessor() {
					InputMultiplexer mul = new InputMultiplexer();
					mul.addProcessor(input);
					mul.addProcessor(new InputAdapter(){
						@Override
						public boolean touchDown(int x, int y, int pointer,
								int button) {
							bounce();
							return super.touchDown(x, y, pointer, button);
						}
					});
					return mul;
				}
				@Override
				public void update(float delta) {
					Cb2World.getInstance().update(delta);
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
