package info.u250.c2d.tools.staff.box2d.properties;

import info.u250.c2d.physical.box2d.loader.cbt.data.PrismaticJointData;

import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;

public class PrismaticJointProperties extends JointProperties{
	final TextField localAnchorX,localAnchorY,localAxisAx,localAxisAy,
					lowerTranslation, upperTranslation,
					motorSpeed,maxMotorForce;
					
	
	final CheckBox enableLimit,enableMotor;
	public PrismaticJointProperties(){
		super();
		localAnchorX = new TextField(skin);
		localAnchorY = new TextField(skin);
		localAxisAx = new TextField(skin);
		localAxisAy = new TextField(skin);
		lowerTranslation = new TextField(skin);
		upperTranslation = new TextField(skin);
		motorSpeed = new TextField(skin);
		maxMotorForce = new TextField(skin);
		
		enableLimit = new CheckBox(skin);
		enableMotor = new CheckBox(skin);
		
		
		this.add(new Label("localAnchorX", skin)).colspan(2);
		this.add(this.localAnchorX).colspan(2).fillX();
		this.row();
		this.add(new Label("localAnchorY", skin)).colspan(2);
		this.add(this.localAnchorY).colspan(2).fillX();
		this.row();
		
		this.add(new Label("enableLimit", skin)).colspan(3);
		this.add(enableLimit).colspan(1).fillX();
		this.row();
		
		this.add(new Label("lowerTranslation", skin)).colspan(2);
		this.add(this.lowerTranslation).colspan(2).fillX();
		this.row();
		this.add(new Label("upperTranslation", skin)).colspan(2);
		this.add(this.upperTranslation).colspan(2).fillX();
		this.row();
		
		
		this.add(new Label("enableMotor", skin)).colspan(3);
		this.add(enableMotor).colspan(1).fillX();
		this.row();
		
		this.add(new Label("motorSpeed", skin)).colspan(2);
		this.add(this.motorSpeed).colspan(2).fillX();
		this.row();
		this.add(new Label("maxMotorForce", skin)).colspan(2);
		this.add(this.maxMotorForce).colspan(2).fillX();
		this.row();
		
		this.add(new Label("localAxisA_x", skin)).colspan(2);
		this.add(this.localAxisAx).colspan(2).fillX();
		this.row();
		this.add(new Label("localAxisA_y", skin)).colspan(2);
		this.add(this.localAxisAy).colspan(2).fillX();
		this.row();
		this.pack();
	}
	@Override
	public void update(Object data) {
		bind(PrismaticJointData.class.cast(data).localAnchorA, "x", localAnchorX);
		bind(PrismaticJointData.class.cast(data).localAnchorA, "y", localAnchorY);
		bind(PrismaticJointData.class.cast(data).localAxisA, "x", localAxisAx);
		bind(PrismaticJointData.class.cast(data).localAxisA, "y", localAxisAy);
		bind(data,"lowerTranslation",lowerTranslation);
		bind(data,"upperTranslation",upperTranslation);
		bind(data,"motorSpeed",motorSpeed);
		bind(data,"maxMotorForce",maxMotorForce);
		bind(data,"enableLimit",enableLimit);
		bind(data,"enableMotor",enableMotor);
		super.update(data);
	}
}
