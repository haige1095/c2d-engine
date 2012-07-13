package info.u250.c2d.tools.staff.box2d;

import info.u250.c2d.engine.Engine;
import info.u250.c2d.engine.events.Event;
import info.u250.c2d.engine.events.EventListener;
import info.u250.c2d.physical.box2d.loader.cbt.data.BodyData;
import info.u250.c2d.physical.box2d.loader.cbt.data.BoxData;
import info.u250.c2d.physical.box2d.loader.cbt.data.CircleData;
import info.u250.c2d.physical.box2d.loader.cbt.data.DistanceJointData;
import info.u250.c2d.physical.box2d.loader.cbt.data.FrictionJointData;
import info.u250.c2d.physical.box2d.loader.cbt.data.JointData;
import info.u250.c2d.physical.box2d.loader.cbt.data.PrismaticJointData;
import info.u250.c2d.physical.box2d.loader.cbt.data.PulleyJointData;
import info.u250.c2d.physical.box2d.loader.cbt.data.RevoluteJointData;
import info.u250.c2d.physical.box2d.loader.cbt.data.RopeJointData;
import info.u250.c2d.physical.box2d.loader.cbt.data.WeldJointData;
import info.u250.c2d.physical.box2d.loader.cbt.data.WheelJointData;
import info.u250.c2d.tools.DesktopUtil;
import info.u250.c2d.tools.Events;
import info.u250.c2d.tools.staff.SceneLayerInputStaff;
import info.u250.c2d.tools.staff.box2d.properties.BoxBodyProperties;
import info.u250.c2d.tools.staff.box2d.properties.CircleBodyProperties;
import info.u250.c2d.tools.staff.box2d.properties.DistanceJointProperties;
import info.u250.c2d.tools.staff.box2d.properties.FrictionJointProperties;
import info.u250.c2d.tools.staff.box2d.properties.PrismaticJointProperties;
import info.u250.c2d.tools.staff.box2d.properties.PulleyJointProperties;
import info.u250.c2d.tools.staff.box2d.properties.RevoluteJointProperties;
import info.u250.c2d.tools.staff.box2d.properties.RopeJointProperties;
import info.u250.c2d.tools.staff.box2d.properties.WeldJointProperties;
import info.u250.c2d.tools.staff.box2d.properties.WheelJointProperties;

import java.util.Iterator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane.ScrollPaneStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class Box2dInputStaff extends SceneLayerInputStaff {
	Table right ;
	Table propertiesTable ;
	
	final Table tableCommon = new Table();
	final Table tools_table = new Table();
	
	final CircleBodyProperties circleBodyProperties;
	final BoxBodyProperties boxBodyProperties;
	final DistanceJointProperties distanceJointProperties;
	final RevoluteJointProperties revoluteJointProperties;
	final PrismaticJointProperties prismaticJointProperties; 
	final RopeJointProperties ropeJointProperties;
	final WeldJointProperties weldJointProperties;
	final FrictionJointProperties frictionJointProperties;
	final WheelJointProperties wheelJointProperties ;
	final PulleyJointProperties pulleyJointProperties;
	
	Object obejct_temp_for_delete_and_current = null;
	public Box2dInputStaff(){
		propertiesTable = new Table();
		
		boxBodyProperties = new BoxBodyProperties();
		circleBodyProperties = new CircleBodyProperties();
		distanceJointProperties = new DistanceJointProperties();
		revoluteJointProperties = new RevoluteJointProperties();
		prismaticJointProperties = new PrismaticJointProperties();
		ropeJointProperties = new RopeJointProperties();
		weldJointProperties = new WeldJointProperties();
		frictionJointProperties = new FrictionJointProperties();
		wheelJointProperties = new WheelJointProperties();
		pulleyJointProperties = new PulleyJointProperties();
		
		propertiesTable.add(circleBodyProperties);
		propertiesTable.pack();
		
		Engine.getEventManager().register(Events.UPDATE_BOXED_PANEL, new EventListener() {
			@Override
			public void onEvent(Event event) {
				Box2dAdapter.class.cast(layer.adapter).save();
				
				Object data = event.getSource();
				obejct_temp_for_delete_and_current = data;
				if(data instanceof BoxData){
					propertiesTable.clear();
					propertiesTable.add(boxBodyProperties);
					propertiesTable.pack();
					boxBodyProperties.update(data);
				}else if(data instanceof CircleData){
					propertiesTable.clear();
					propertiesTable.add(circleBodyProperties);
					propertiesTable.pack();
					circleBodyProperties.update(data);
				}else if(data instanceof DistanceJointData){
					propertiesTable.clear();
					propertiesTable.add(distanceJointProperties);
					propertiesTable.pack();
					distanceJointProperties.update(data);
				}else if(data instanceof RevoluteJointData){
					propertiesTable.clear();
					propertiesTable.add(revoluteJointProperties);
					propertiesTable.pack();
					revoluteJointProperties.update(data);
				}else if(data instanceof PrismaticJointData){
					propertiesTable.clear();
					propertiesTable.add(prismaticJointProperties);
					propertiesTable.pack();
					prismaticJointProperties.update(data);					
				}else if(data instanceof RopeJointData){
					propertiesTable.clear();
					propertiesTable.add(ropeJointProperties);
					propertiesTable.pack();
					ropeJointProperties.update(data);
				}else if(data instanceof WeldJointData){
					propertiesTable.clear();
					propertiesTable.add(weldJointProperties);
					propertiesTable.pack();
					weldJointProperties.update(data);
				}else if(data instanceof FrictionJointData){
					propertiesTable.clear();
					propertiesTable.add(frictionJointProperties);
					propertiesTable.pack();
					frictionJointProperties.update(data);
				}else if(data instanceof WheelJointData){
					propertiesTable.clear();
					propertiesTable.add(wheelJointProperties);
					propertiesTable.pack();
					wheelJointProperties.update(data);
				}else if(data instanceof PulleyJointData){
					propertiesTable.clear();
					propertiesTable.add(pulleyJointProperties);
					propertiesTable.pack();
					pulleyJointProperties.update(data);
				}
			}
		});
		
		
		
	}
	
	@Override
	public Table layoutInfo() {
		return new Table();
	}

	private void uncheckAllButton(){
		for(Actor item:this.tableCommon.getActors()){
			if(item instanceof Button){
				((Button) item).setChecked(false);
			}
		}
		for(Actor item:this.tools_table.getActors()){
			if(item instanceof Button){
				((Button) item).setChecked(false);
			}
		}
	}
	@Override
	public void buildStaff() {
		tableCommon.clear();
		tools_table.clear();
		
		this.ui.removeActor(right);
		this.right = new Table(){
			@Override
			public boolean touchDown(float x, float y, int pointer) {
				super.touchDown(x, y, pointer);
				return true;
			}
		};
		
		
		final Box2dAdapter sAdapter = Box2dAdapter.class.cast(this.layer.adapter);
		
		final Button tools_delete = new Button(new Image(skin.getRegion("tools-delete")),skin.getStyle("toggle", TextButtonStyle.class));
		tools_delete.setClickListener(new ClickListener() {
			@Override
			public void click(Actor actor, float x, float y) {
				tools_delete.setChecked(false);
				if(obejct_temp_for_delete_and_current instanceof JointData){
					sAdapter.data.jointDatas.removeValue(JointData.class.cast(obejct_temp_for_delete_and_current), false);
				}else if(obejct_temp_for_delete_and_current instanceof BodyData){
					BodyData bodyData = BodyData.class.cast(obejct_temp_for_delete_and_current);
					Iterator<JointData> it = sAdapter.data.jointDatas.iterator();
					while(it.hasNext()){
						final JointData tmp = it.next();
						if(
								   tmp.bodyA.name.equals(bodyData.name) 
								|| tmp.bodyB.name.equals(bodyData.name)){
							sAdapter.data.jointDatas.removeValue(tmp, false);
						}
					}
					sAdapter.data.bodyDatas.removeValue(bodyData, false);
				}
			}
		});
		
		
		
		
		final Button tools_select = new Button(new Image(skin.getRegion("tools-select")),skin.getStyle("toggle", TextButtonStyle.class));
		tools_select.setClickListener(new ClickListener() {
			@Override
			public void click(Actor actor, float x, float y) {
				sAdapter.activeSelectHelper();
				uncheckAllButton();
				tools_select.setChecked(true);
			}
		});
		
		final Button tools_clear = new Button(new Image(skin.getRegion("tools-clear")),skin.getStyle("toggle", TextButtonStyle.class));
		tools_clear.setClickListener(new ClickListener() {
			@Override
			public void click(Actor actor, float x, float y) {
				sAdapter.reset();
				tools_clear.setChecked(false);
			}
		});
		
		final Button tools_select_joint = new Button(new Image(skin.getRegion("tools-select-joint")),skin.getStyle("toggle", TextButtonStyle.class));
		tools_select_joint.setClickListener(new ClickListener() {
			@Override
			public void click(Actor actor, float x, float y) {
				sAdapter.activeJointSelectHelper();
				uncheckAllButton();
				tools_select_joint.setChecked(true);
			}
		});
		
		final Button tools_rotate = new Button(new Image(skin.getRegion("tools-rotate")),skin.getStyle("toggle", TextButtonStyle.class));
		tools_rotate.setClickListener(new ClickListener() {
			@Override
			public void click(Actor actor, float x, float y) {
				sAdapter.activeRotateHelper();
				uncheckAllButton();
				tools_rotate.setChecked(true);
			}
		});
		final Button tools_scale = new Button(new Image(skin.getRegion("tools-scale")),skin.getStyle("toggle", TextButtonStyle.class));
		tools_scale.setClickListener(new ClickListener() {
			@Override
			public void click(Actor actor, float x, float y) {
				sAdapter.activeScaleHelper();
				uncheckAllButton();
				tools_scale.setChecked(true);
			}
		});
		final Button tools_circle = new Button(new Image(skin.getRegion("tools-circle")),skin.getStyle("toggle", TextButtonStyle.class));;
		tools_circle.setClickListener(new ClickListener() {
			@Override
			public void click(Actor actor, float x, float y) {
				sAdapter.activeCircleHelper();
				uncheckAllButton();
				tools_circle.setChecked(true);
			}
		});
		final Button tools_rect = new Button(new Image(skin.getRegion("tools-rect")),skin.getStyle("toggle", TextButtonStyle.class));
		tools_rect.setClickListener(new ClickListener() {
			@Override
			public void click(Actor actor, float x, float y) {
				sAdapter.activeBoxHelper();
				uncheckAllButton();
				tools_rect.setChecked(true);
			}
		});
		final Button tools_line = new Button(new Image(skin.getRegion("tools-line")),skin.getStyle("toggle", TextButtonStyle.class));
		tools_line.setClickListener(new ClickListener() {
			@Override
			public void click(Actor actor, float x, float y) {
				uncheckAllButton();
				tools_line.setChecked(true);
			}
		});
		
		tools_table.add(tools_select);
		tools_table.add(tools_select_joint);
		tools_table.add(tools_rotate);
		tools_table.add(tools_scale);
		tools_table.add(tools_delete);
		tools_table.add(tools_clear);
		tools_table.row();
		tools_table.add(tools_circle);
		tools_table.add(tools_rect);
		tools_table.add(tools_line);
		tools_table.row();
		tools_table.pack();
		
		final Button distanceJointBtn = new TextButton("Distance Joint", skin.getStyle("toggle", TextButtonStyle.class), "button-sl");
		final Button revoluteJointBtn = new TextButton("Revolute Joint", skin.getStyle("toggle", TextButtonStyle.class), "button-sl");
		final Button gearJointBtn = new TextButton("GearJoint Joint", skin.getStyle("toggle", TextButtonStyle.class), "button-sl");
		final Button prismaticJointBtn = new TextButton("Prismatic Joint", skin.getStyle("toggle", TextButtonStyle.class), "button-sl");
		final Button pulleyJointBtn = new TextButton("Pulley Joint", skin.getStyle("toggle", TextButtonStyle.class), "button-sl");
		final Button ropeJointBtn = new TextButton("Rope Joint", skin.getStyle("toggle", TextButtonStyle.class), "button-sl");
		final Button weldJointBtn = new TextButton("Weld Joint", skin.getStyle("toggle", TextButtonStyle.class), "button-sl");
		final Button wheelJointBtn = new TextButton("Wheel Joint", skin.getStyle("toggle", TextButtonStyle.class), "button-sl");
		final Button frictionJointBtn = new TextButton("Friction Joint", skin.getStyle("toggle", TextButtonStyle.class), "button-sl");
		distanceJointBtn.setClickListener(new ClickListener() {
			public void click(Actor actor, float x, float y) {
				uncheckAllButton();
				distanceJointBtn.setChecked(true);
				sAdapter.activeDistanceJointHelper();
			}
		});
		revoluteJointBtn.setClickListener(new ClickListener() {
			public void click(Actor actor, float x, float y) {
				uncheckAllButton();
				revoluteJointBtn.setChecked(true);
				sAdapter.activeRevoluteJointHelper();
			}
		});
		gearJointBtn.setClickListener(new ClickListener() {
			public void click(Actor actor, float x, float y) {
				uncheckAllButton();
				gearJointBtn.setChecked(true);
			}
		});
		prismaticJointBtn.setClickListener(new ClickListener() {
			public void click(Actor actor, float x, float y) {
				uncheckAllButton();
				prismaticJointBtn.setChecked(true);
				sAdapter.activePrismaticJointHelper();
			}
		});
		pulleyJointBtn.setClickListener(new ClickListener() {
			public void click(Actor actor, float x, float y) {
				uncheckAllButton();
				pulleyJointBtn.setChecked(true);
				sAdapter.activePulleyJointHelper();
			}
		});
		ropeJointBtn.setClickListener(new ClickListener() {
			public void click(Actor actor, float x, float y) {
				uncheckAllButton();
				ropeJointBtn.setChecked(true);
				sAdapter.activeRopeJointHelper();
			}
		});
		weldJointBtn.setClickListener(new ClickListener() {
			public void click(Actor actor, float x, float y) {
				uncheckAllButton();
				weldJointBtn.setChecked(true);
				sAdapter.activeWeldJointHelper();
			}
		});
		wheelJointBtn.setClickListener(new ClickListener() {
			public void click(Actor actor, float x, float y) {
				uncheckAllButton();
				wheelJointBtn.setChecked(true);
				sAdapter.activeWheelJointHelper();
			}
		});
		frictionJointBtn.setClickListener(new ClickListener() {
			public void click(Actor actor, float x, float y) {
				uncheckAllButton();
				frictionJointBtn.setChecked(true);
				sAdapter.activeFrictionJointHelper();
			}
		});
		
		tableCommon.defaults().spaceBottom(0).spaceRight(10).left().top();
		final Label title_opt = new Label("operation", skin);
		title_opt.setColor(Color.PINK);
		tableCommon.add(title_opt).colspan(2);
		tableCommon.row();
		tableCommon.add(tools_table).colspan(2);
		tableCommon.row();
		
		final Label title_joints = new Label("joints", skin);
		title_joints.setColor(Color.PINK);
		tableCommon.add(title_joints).colspan(2);
		tableCommon.row();
		tableCommon.add(distanceJointBtn);
		tableCommon.add(revoluteJointBtn);
		tableCommon.row();
		tableCommon.add(gearJointBtn);
		tableCommon.add(prismaticJointBtn);
		tableCommon.row();
		tableCommon.add(pulleyJointBtn);
		tableCommon.add(ropeJointBtn);
		tableCommon.row();
		tableCommon.add(weldJointBtn);
		tableCommon.add(wheelJointBtn);
		tableCommon.row();
		tableCommon.add(frictionJointBtn);
		tableCommon.row();
		
		final Label path =  new Label("Path:",skin);
		final Image tools_open = new Image(skin.getRegion("tools-open"));
		final Image tools_save = new Image(skin.getRegion("tools-save"));
		tools_open.setClickListener(new ClickListener() {
			public void click(Actor actor, float x, float y) {
				try{
					String file = DesktopUtil.getFile();
					sAdapter.file = Gdx.files.absolute(file);
					sAdapter.read();
					path.setText("Path:"+file);
				}catch(Exception ex){
					ex.printStackTrace();
					sAdapter.file = null;
					sAdapter.data.jointDatas.clear();
					sAdapter.data.bodyDatas.clear();
					path.setText("Path:Invalid");
				}
			}
		});
		tools_save.setClickListener(new ClickListener() {
			public void click(Actor actor, float x, float y) {
				sAdapter.save();
				path.setText(null==sAdapter.file?"Path:Invalid":"Path:"+sAdapter.file.file().getAbsolutePath());
			}
		});
		Table openSave = new Table();
		openSave.add(tools_open);
		openSave.add(tools_save);
		openSave.row();
		openSave.left().add(path).colspan(2);
		openSave.pack();
		tableCommon.add(openSave).colspan(2);
		tableCommon.row();
		
		final Label title_properties = new Label("properties", skin);
		title_properties.setColor(Color.PINK);
		tableCommon.add(title_properties).colspan(2);
		tableCommon.row();
		

		ScrollPane pane = new ScrollPane(propertiesTable, skin.getStyle(ScrollPaneStyle.class));
		pane.setFillParent(true);
		pane.setScrollingDisabled(true, false);
		
		Table tablePro = new Table();
		tablePro.defaults().spaceBottom(0).spaceRight(10).left().top();
		tablePro.add(new Label("",skin));
		tablePro.row();
		tablePro.add(pane);
		tablePro.row();
//		tablePro.pack();
		

		
		this.right.add(tableCommon);
		this.right.row();
		this.right.add(tablePro).fillY();
//		this.right.pack();
		this.right.left().top();
		this.right.width = 270;
		this.right.height = Engine.getEngineConfig().height ;
		this.right.x = Engine.getEngineConfig().width - this.right.width;
		this.right.y = 0;
		this.right.setBackground(skin.getPatch("default-pane"));
		this.ui.addActor(right);
	}

	@Override
	protected void resize(float width, float height) {
		this.buildStaff();
	}
}
