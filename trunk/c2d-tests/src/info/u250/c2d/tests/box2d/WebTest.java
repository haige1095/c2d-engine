package info.u250.c2d.tests.box2d;

import info.u250.c2d.engine.Engine;
import info.u250.c2d.engine.EngineDrive;
import info.u250.c2d.engine.Scene;
import info.u250.c2d.engine.resources.AliasResourceManager;
import info.u250.c2d.graphic.AdvanceSprite;
import info.u250.c2d.input.PhysicalFingerInput;
import info.u250.c2d.physical.box2d.Cb2Object;
import info.u250.c2d.physical.box2d.Cb2ObjectGroup;
import info.u250.c2d.physical.box2d.Cb2World;
import info.u250.c2d.physical.box2d.loader.cbt.data.CircleData;
import info.u250.c2d.physical.box2d.loader.cbt.data.DistanceJointData;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Array;


public class WebTest  extends Engine {

	@Override
	protected EngineDrive onSetupEngineDrive() {
		return new EngineX();
	}
	
	
	private class EngineX implements EngineDrive{
		@Override
		public void onResourcesRegister(AliasResourceManager<String> reg) {
			reg.texture("logo", "data/c2d.png");
		}
		@Override
		public void dispose() {}
		@Override
		public EngineOptions onSetupEngine() {
			return new EngineOptions(new String[]{"data/c2d.png"},800,480);
		}
		ShapeRenderer render;
		PhysicalFingerInput input;
		
		Array<DistanceJointData> joints = new Array<DistanceJointData>(); 
		Cb2ObjectGroup group = new Cb2ObjectGroup();
		@Override
		public void onLoadedResourcesCompleted() {
			render = new ShapeRenderer();
			input =new PhysicalFingerInput( Cb2World.getInstance().installDefaultWorld().createScreenBox());
			
			
			//some ball
			final AdvanceSprite sprite  = new AdvanceSprite(Engine.resource("logo",Texture.class));
			Cb2Object objA =  new Cb2Object(sprite,true);
			Cb2Object objB =  new Cb2Object(sprite,true);
			Cb2Object objC =  new Cb2Object(sprite,true);
			Cb2Object objD =  new Cb2Object(sprite,true);
			final float hf = objA.object.getWidth()/2;
			objA.setPosition(100-hf,100-hf);
			objB.setPosition(300-hf,100-hf);
			objC.setPosition(300-hf,300-hf);
			objD.setPosition(100-hf,300-hf);
			group.add(objA);
			group.add(objB);
			group.add(objC);
			group.add(objD);
			
			CircleData data1 = new CircleData();
			data1.center.set(0,0);
			data1.radius = 10;
			data1.isDynamic = false;
			CircleData data2 = new CircleData();
			data2.center.set(400,0);
			data2.radius = 10;
			data2.isDynamic = false;
			CircleData data3 = new CircleData();
			data3.center.set(400,400);
			data3.radius = 10;
			data3.isDynamic = false;
			CircleData data4 = new CircleData();
			data4.center.set(0,400);
			data4.radius = 10;
			data4.isDynamic = false;
			data1.build();
			data2.build();
			data3.build();
			data4.build();
		
			DistanceJointData joint1 = new DistanceJointData();
			joint1.bodyA = objA.data;
			joint1.bodyB = objB.data;
			joint1.frequencyHz = 500;
			joint1.build();
			joints.add(joint1);
			DistanceJointData joint2 = new DistanceJointData();
			joint2.bodyA = objB.data;
			joint2.bodyB = objC.data;
			joint2.frequencyHz = 500;
			joint2.build();
			joints.add(joint2);
			DistanceJointData joint3 = new DistanceJointData();
			joint3.bodyA = objC.data;
			joint3.bodyB = objD.data;
			joint3.frequencyHz = 500;
			joint3.build();
			joints.add(joint3);
			DistanceJointData joint4 = new DistanceJointData();
			joint4.bodyA = objD.data;
			joint4.bodyB = objA.data;
			joint4.frequencyHz = 500;
			joint4.build();
			joints.add(joint4);
			
			
			DistanceJointData joint5 = new DistanceJointData();
			joint5.bodyA = data1;
			joint5.bodyB = objA.data;
			joint5.build();
			joints.add(joint5);
			DistanceJointData joint6 = new DistanceJointData();
			joint6.bodyA = data2;
			joint6.bodyB = objB.data;
			joint6.build();
			joints.add(joint6);
			DistanceJointData joint7 = new DistanceJointData();
			joint7.bodyA = data3;
			joint7.bodyB = objC.data;
			joint7.build();
			joints.add(joint7);
			DistanceJointData joint8 = new DistanceJointData();
			joint8.bodyA = data4;
			joint8.bodyB = objD.data;
			joint8.build();
			joints.add(joint8);
			
			Engine.setMainScene(new Scene() {
				@Override
				public void update(float delta) {
					Cb2World.getInstance().update(delta);
				}
				@Override
				public void hide() {}

				@Override
				public void show() {}
				@Override
				public void render(float delta) {
					Engine.getSpriteBatch().begin();
					group.render(delta);
					Engine.getSpriteBatch().end();
//					group.debug(render);	
					for(DistanceJointData j:joints){
						j.debug(render);
					}
				}
				
				@Override
				public InputProcessor getInputProcessor() {
					return input;
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
