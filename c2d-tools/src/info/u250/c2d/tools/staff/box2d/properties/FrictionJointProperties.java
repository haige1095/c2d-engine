package info.u250.c2d.tools.staff.box2d.properties;

import info.u250.c2d.physical.box2d.loader.cbt.data.FrictionJointData;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;

public class FrictionJointProperties extends JointProperties{
	final TextField localAnchorX,localAnchorY,
	maxForce, maxTorque;
	
	public FrictionJointProperties(){
		super();
		localAnchorX = new TextField(skin);
		localAnchorY = new TextField(skin);
		maxForce = new TextField(skin);
		maxTorque = new TextField(skin);
		
		
		this.add(new Label("localAnchorX", skin)).colspan(2);
		this.add(this.localAnchorX).colspan(2).fillX();
		this.row();
		this.add(new Label("localAnchorY", skin)).colspan(2);
		this.add(this.localAnchorY).colspan(2).fillX();
		this.row();
		
		this.add(new Label("maxForce", skin)).colspan(2);
		this.add(this.maxForce).colspan(2).fillX();
		this.row();
		this.add(new Label("maxTorque", skin)).colspan(2);
		this.add(this.maxTorque).colspan(2).fillX();
		this.row();
		this.pack();
	}
	@Override
	public void update(Object data) {
		bind(FrictionJointData.class.cast(data).localAnchorA, "x", localAnchorX);
		bind(FrictionJointData.class.cast(data).localAnchorA, "y", localAnchorY);
		bind(data,"maxForce",maxForce);
		bind(data,"maxTorque",maxTorque);
		super.update(data);
	}
}
