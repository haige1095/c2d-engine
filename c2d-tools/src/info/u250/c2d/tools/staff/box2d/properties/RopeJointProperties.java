package info.u250.c2d.tools.staff.box2d.properties;

import info.u250.c2d.physical.box2d.loader.cbt.data.RopeJointData;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;

public class RopeJointProperties extends JointProperties{
	final TextField localAnchorAx,localAnchorAy,localAnchorBx,localAnchorBy,
	maxLength;
	public RopeJointProperties(){
		super();
		localAnchorAx = new TextField("localAnchorAx",skin);
		localAnchorAy = new TextField("localAnchorAy",skin);
		localAnchorBx = new TextField("localAnchorBx",skin);
		localAnchorBy = new TextField("localAnchorBy",skin);
		maxLength = new TextField("maxLength",skin);
		
		this.add(new Label("anchorA_x", skin)).colspan(2);
		this.add(this.localAnchorAx).colspan(2).fillX();
		this.row();
		this.add(new Label("anchorA_y", skin)).colspan(2);
		this.add(this.localAnchorAy).colspan(2).fillX();
		this.row();
		this.add(new Label("anchorB_x", skin)).colspan(2);
		this.add(this.localAnchorBx).colspan(2).fillX();
		this.row();
		this.add(new Label("anchorB_y", skin)).colspan(2);
		this.add(this.localAnchorBy).colspan(2).fillX();
		this.row();
		this.add(new Label("maxLength", skin)).colspan(2);
		this.add(this.maxLength).colspan(2).fillX();
		this.row();
		this.pack();
	}
	@Override
	public void update(Object data) {
		bind(RopeJointData.class.cast(data).localAnchorA, "x", localAnchorAx);
		bind(RopeJointData.class.cast(data).localAnchorA, "y", localAnchorAy);
		bind(RopeJointData.class.cast(data).localAnchorB, "x", localAnchorBx);
		bind(RopeJointData.class.cast(data).localAnchorB, "y", localAnchorBy);
		bind(data,"maxLength",maxLength);
		super.update(data);
	}
}
