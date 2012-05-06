package info.u250.c2d.tools.staff.box2d;

import info.u250.c2d.engine.Engine;
import info.u250.c2d.engine.SceneGroup;
import info.u250.c2d.graphic.AdvanceSprite;
import info.u250.c2d.physical.box2d.Cb2Object;
import info.u250.c2d.physical.box2d.Cb2Object.Cb2ObjectSetupCallback;
import info.u250.c2d.physical.box2d.Cb2ObjectGroup;
import info.u250.c2d.physical.box2d.loader.cbt.CbtWorldReader;
import info.u250.c2d.physical.box2d.loader.cbt.data.BodyData;
import info.u250.c2d.physical.box2d.loader.cbt.data.BoxData;
import info.u250.c2d.physical.box2d.loader.cbt.data.CircleData;
import info.u250.c2d.physical.box2d.loader.cbt.data.JointData;
import info.u250.c2d.tools.DesktopUtil;
import info.u250.c2d.tools.scenes.SceneWorkTable;
import info.u250.c2d.tools.staff.SceneAdapter;
import info.u250.c2d.tools.staff.box2d.helper.BoxHelper;
import info.u250.c2d.tools.staff.box2d.helper.CircleHelper;
import info.u250.c2d.tools.staff.box2d.helper.DistanceJointHelper;
import info.u250.c2d.tools.staff.box2d.helper.FrictionJointHelper;
import info.u250.c2d.tools.staff.box2d.helper.JointSelectHelper;
import info.u250.c2d.tools.staff.box2d.helper.PrismaticJointHelper;
import info.u250.c2d.tools.staff.box2d.helper.PulleyJointHelper;
import info.u250.c2d.tools.staff.box2d.helper.RevoluteJointHelper;
import info.u250.c2d.tools.staff.box2d.helper.RopeJointHelper;
import info.u250.c2d.tools.staff.box2d.helper.RotateHelper;
import info.u250.c2d.tools.staff.box2d.helper.ScaleHelper;
import info.u250.c2d.tools.staff.box2d.helper.SelectHelper;
import info.u250.c2d.tools.staff.box2d.helper.WeldJointHelper;
import info.u250.c2d.tools.staff.box2d.helper.WheelJointHelper;

import java.util.Iterator;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class Box2dAdapter extends SceneGroup implements SceneAdapter{

	
	final SceneWorkTable editor ;
	final InputMultiplexer mul = new InputMultiplexer();
	final CircleHelper circleHelper ;
	final BoxHelper boxHelper ;
	final SelectHelper selectHelper;
	final JointSelectHelper jointSelectHelper;
	final RotateHelper rotateHelper;
	final ScaleHelper scaleHelper;
	final DistanceJointHelper distanceJointHelper;
	final RevoluteJointHelper revoluteJointHelper;
	final PrismaticJointHelper prismaticJointHelper;
	final WeldJointHelper weldJointHelper;
	final RopeJointHelper ropeJointHelper; 
	final WheelJointHelper wheelJointHelper; 
	final PulleyJointHelper pulleyJointHelper; 
	final FrictionJointHelper frictionJointHelper; 
	public final CbtWorldReader data ;
	FileHandle file ;
	final Cb2ObjectGroup group ;
	boolean runMode = false;
	public Box2dAdapter(){
		editor = Engine.resource("Editor");

		
		data = new CbtWorldReader();
		
		group = new Cb2ObjectGroup();

		
		circleHelper = new CircleHelper(this);
		boxHelper = new BoxHelper(this);
		selectHelper = new SelectHelper(this);
		rotateHelper = new RotateHelper(this);
		scaleHelper = new ScaleHelper(this);
		distanceJointHelper = new DistanceJointHelper(this);
		revoluteJointHelper = new RevoluteJointHelper(this);
		prismaticJointHelper = new PrismaticJointHelper(this);
		weldJointHelper = new WeldJointHelper(this);
		ropeJointHelper = new RopeJointHelper(this);
		wheelJointHelper = new WheelJointHelper(this);
		pulleyJointHelper = new PulleyJointHelper(this);
		frictionJointHelper = new FrictionJointHelper(this);
		jointSelectHelper = new JointSelectHelper(this);
		
		this.add(circleHelper);
		this.add(boxHelper);
		this.add(selectHelper);
		this.add(rotateHelper);
		this.add(scaleHelper);
		this.add(distanceJointHelper);
		this.add(revoluteJointHelper);
		this.add(prismaticJointHelper);
		this.add(weldJointHelper);
		this.add(ropeJointHelper);
		this.add(wheelJointHelper);
		this.add(pulleyJointHelper);
		this.add(frictionJointHelper);
		this.add(jointSelectHelper);
	}
	public void read(){
		this.data.jointDatas.clear();
		this.data.bodyDatas.clear();
		this.data.read(this.file);
	}
	public void save(){
		try{
			if(file == null)
				file = Gdx.files.absolute(DesktopUtil.save());
			data.write(file);
		}catch(Exception ex){
			ex.printStackTrace();
			file = null;
		}
	}
	public void build(){
		for(BodyData body:this.data.bodyDatas){
			body.build();
		}
		for(JointData joint:this.data.jointDatas){
			joint.build();
		}
	}
	
	void reset(){
		this.data.bodyDatas.clear();
		this.data.jointDatas.clear();
	}
	void play(){
		this.build();
		this.mul.clear();
		
		group.clear();
		for(BodyData bd :this.data.bodyDatas){
			if(bd.res == null || bd.res.trim().equals("") || Engine.resource("AAA",TextureAtlas.class).findRegion(bd.res)==null){
				Random random = new Random();
				if(bd instanceof CircleData){
					AdvanceSprite sp = new AdvanceSprite(Engine.resource("CircleTexture",Texture.class));
					sp.setColor(random.nextFloat(), random.nextFloat(), random.nextFloat(), 1);
					group.add(new Cb2Object(bd, sp));
				}else if(bd instanceof BoxData){
					AdvanceSprite sp = new AdvanceSprite(Engine.resource("BoxTexture",Texture.class));
					sp.setColor(random.nextFloat(), random.nextFloat(), random.nextFloat(), 1);
					group.add(new Cb2Object(bd, sp));
				}
			}else{
				group.add(new Cb2Object(bd, new AdvanceSprite(Engine.resource("AAA",TextureAtlas.class).findRegion(bd.res)),new Cb2ObjectSetupCallback() {
					
					@Override
					public void before(Cb2Object obj) {						
					}
					
					@Override
					public void after(Cb2Object obj) {
						obj.data.body.setSleepingAllowed(true);;
						obj.data.body.setAwake(false);
					}
				}));
			}
		}
		runMode = true;
	}
	void stop(){
		group.clear();
		//FIX BUG, the body should be null here
		data.spawn();
		this.mul.clear();
//		rbg.getCamera().position.set(Engine.getDefaultCamera().position);
		runMode = false;
	}
	void activeJointSelectHelper(){
		this.mul.clear();
		this.mul.addProcessor(jointSelectHelper.getInputProcessor());
	}
	void activeFrictionJointHelper(){
		this.mul.clear();
		this.mul.addProcessor(frictionJointHelper.getInputProcessor());
	}
	void activePulleyJointHelper(){
		this.mul.clear();
		this.mul.addProcessor(pulleyJointHelper.getInputProcessor());
	}
	void activeWheelJointHelper(){
		this.mul.clear();
		this.mul.addProcessor(wheelJointHelper.getInputProcessor());
	}
	void activeRopeJointHelper(){
		this.mul.clear();
		this.mul.addProcessor(ropeJointHelper.getInputProcessor());
	}
	void activePrismaticJointHelper(){
		this.mul.clear();
		this.mul.addProcessor(prismaticJointHelper.getInputProcessor());
	}
	void activeWeldJointHelper(){
		this.mul.clear();
		this.mul.addProcessor(weldJointHelper.getInputProcessor());
	}
	void activeRevoluteJointHelper(){
		this.mul.clear();
		this.mul.addProcessor(revoluteJointHelper.getInputProcessor());
	}
	void activeSelectHelper(){
		this.mul.clear();
		this.mul.addProcessor(selectHelper.getInputProcessor());
	}
	void activeDistanceJointHelper(){
		this.mul.clear();
		this.mul.addProcessor(distanceJointHelper.getInputProcessor());
	}
	
	void activeScaleHelper(){
		this.mul.clear();
		this.mul.addProcessor(scaleHelper.getInputProcessor());
	}
	void activeRotateHelper(){
		this.mul.clear();
		this.mul.addProcessor(rotateHelper.getInputProcessor());
	}
	
	void activeCircleHelper(){
		this.mul.clear();
		this.mul.addProcessor(circleHelper.getInputProcessor());
	}
	void activeBoxHelper(){
		this.mul.clear();
		this.mul.addProcessor(boxHelper.getInputProcessor());
	}
	@Override
	public void render(float delta) {
		if(runMode){
			Engine.getSpriteBatch().begin();
			group.render(delta);
			Engine.getSpriteBatch().end();
//			group.debug(editor.render);
			final Iterator<JointData> it2 = data.jointDatas.iterator();
			while(it2.hasNext()){
				it2.next().debug(editor.render);
			}
		}else{
			final Iterator<BodyData> it = data.bodyDatas.iterator();
			while(it.hasNext()){
				it.next().debug(editor.render);
			}
			final Iterator<JointData> it2 = data.jointDatas.iterator();
			while(it2.hasNext()){
				it2.next().debug(editor.render);
			}
		}
		super.render(delta);
//		PhysicalManager.PM().debug();
	}

	@Override
	public InputProcessor getInputProcessor() {
		mul.addProcessor(selectHelper.getInputProcessor());
		return mul;
	}

}
