package info.u250.c2d.tools.staff.box2d.properties;

import info.u250.c2d.physical.box2d.loader.cbt.data.WeldJointData;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;

public class WeldJointProperties extends JointProperties{
	final TextField localAnchorX,localAnchorY;
	
	public WeldJointProperties(){
		super();
		localAnchorX = new TextField("localAnchorX",skin);
		localAnchorY = new TextField("localAnchorY",skin);
		
		this.add(new Label("localAnchorX", skin)).colspan(2);
		this.add(this.localAnchorX).colspan(2).fillX();
		this.row();
		this.add(new Label("localAnchorY", skin)).colspan(2);
		this.add(this.localAnchorY).colspan(2).fillX();
		this.row();
		
		this.pack();
	}
	@Override
	public void update(Object data) {
		bind(WeldJointData.class.cast(data).localAnchorA, "x", localAnchorX);
		bind(WeldJointData.class.cast(data).localAnchorA, "y", localAnchorY);
		super.update(data);
	}
}
