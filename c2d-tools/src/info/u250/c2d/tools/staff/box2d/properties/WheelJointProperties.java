package info.u250.c2d.tools.staff.box2d.properties;

import info.u250.c2d.physical.box2d.loader.cbt.data.WheelJointData;

import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;

public class WheelJointProperties extends JointProperties{
	final TextField localAnchorX,localAnchorY,localAxisAx,localAxisAy,
	dampingRatio, frequencyHz,motorSpeed,maxMotorTorque;
					
	final CheckBox enableMotor;
	public WheelJointProperties(){
		super();
		localAnchorX = new TextField(skin);
		localAnchorY = new TextField(skin);
		localAxisAx = new TextField(skin);
		localAxisAy = new TextField(skin);
		dampingRatio = new TextField(skin);
		frequencyHz = new TextField(skin);
		motorSpeed = new TextField(skin);
		maxMotorTorque = new TextField(skin);
		
		enableMotor = new CheckBox(skin);
		
		
		this.add(new Label("localAnchorX", skin)).colspan(2);
		this.add(this.localAnchorX).colspan(2).fillX();
		this.row();
		this.add(new Label("localAnchorY", skin)).colspan(2);
		this.add(this.localAnchorY).colspan(2).fillX();
		this.row();
		
		this.add(new Label("dampingRatio", skin)).colspan(2);
		this.add(this.dampingRatio).colspan(2).fillX();
		this.row();
		this.add(new Label("frequencyHz", skin)).colspan(2);
		this.add(this.frequencyHz).colspan(2).fillX();
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
		bind(WheelJointData.class.cast(data).localAnchorA, "x", localAnchorX);
		bind(WheelJointData.class.cast(data).localAnchorA, "y", localAnchorY);
		bind(WheelJointData.class.cast(data).localAxisA, "x", localAxisAx);
		bind(WheelJointData.class.cast(data).localAxisA, "y", localAxisAy);
		bind(data,"dampingRatio",dampingRatio);
		bind(data,"frequencyHz",frequencyHz);
		bind(data,"motorSpeed",motorSpeed);
		bind(data,"maxMotorTorque",maxMotorTorque);
		bind(data,"enableMotor",enableMotor);
		super.update(data);
	}
}
