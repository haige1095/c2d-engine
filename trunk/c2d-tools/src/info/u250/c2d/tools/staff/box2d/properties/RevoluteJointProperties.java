package info.u250.c2d.tools.staff.box2d.properties;

import info.u250.c2d.physical.box2d.loader.cbt.data.RevoluteJointData;

import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;

public class RevoluteJointProperties extends JointProperties{
	final TextField localAnchorX,localAnchorY,
					lowerAngle, upperAngle,
					motorSpeed,maxMotorTorque;
					
	final CheckBox enableLimit,enableMotor;
	public RevoluteJointProperties(){
		super();
		localAnchorX = new TextField("localAnchorX",skin);
		localAnchorY = new TextField("localAnchorY",skin);
		lowerAngle = new TextField("lowerAngle",skin);
		upperAngle = new TextField("upperAngle",skin);
		motorSpeed = new TextField("motorSpeed",skin);
		maxMotorTorque = new TextField("maxMotorTorque",skin);
		
		enableLimit = new CheckBox("enableLimit",skin);
		enableMotor = new CheckBox("enableMotor",skin);
		
		
		this.add(new Label("localAnchorX", skin)).colspan(2);
		this.add(this.localAnchorX).colspan(2).fillX();
		this.row();
		this.add(new Label("localAnchorY", skin)).colspan(2);
		this.add(this.localAnchorY).colspan(2).fillX();
		this.row();
		
		this.add(new Label("enableLimit", skin)).colspan(3);
		this.add(enableLimit).colspan(1).fillX();
		this.row();
		
		this.add(new Label("lowerAngle", skin)).colspan(2);
		this.add(this.lowerAngle).colspan(2).fillX();
		this.row();
		this.add(new Label("upperAngle", skin)).colspan(2);
		this.add(this.upperAngle).colspan(2).fillX();
		this.row();
		
		
		this.add(new Label("enableMotor", skin)).colspan(3);
		this.add(enableMotor).colspan(1).fillX();
		this.row();
		
		this.add(new Label("motorSpeed", skin)).colspan(2);
		this.add(this.motorSpeed).colspan(2).fillX();
		this.row();
		this.add(new Label("maxMotorTorque", skin)).colspan(2);
		this.add(this.maxMotorTorque).colspan(2).fillX();
		this.row();
		this.pack();
	}
	@Override
	public void update(Object data) {
		bind(RevoluteJointData.class.cast(data).localAnchorA, "x", localAnchorX);
		bind(RevoluteJointData.class.cast(data).localAnchorA, "y", localAnchorY);
		bind(data,"lowerAngle",lowerAngle);
		bind(data,"upperAngle",upperAngle);
		bind(data,"motorSpeed",motorSpeed);
		bind(data,"maxMotorTorque",maxMotorTorque);
		bind(data,"enableLimit",enableLimit);
		bind(data,"enableMotor",enableMotor);
		super.update(data);
	}
}
