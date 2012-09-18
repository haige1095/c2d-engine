package info.u250.c2d.tools.staff.box2d.properties;

import info.u250.c2d.physical.box2d.loader.cbt.data.DistanceJointData;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;

public class DistanceJointProperties extends JointProperties{
	final TextField localAnchorAx,localAnchorAy,localAnchorBx,localAnchorBy,
					frequencyHz,dampingRatio;
	public DistanceJointProperties(){
		super();
		localAnchorAx = new TextField("localAnchorAx",skin);
		localAnchorAy = new TextField("localAnchorAy",skin);
		localAnchorBx = new TextField("localAnchorBx",skin);
		localAnchorBy = new TextField("localAnchorBy",skin);
		frequencyHz = new TextField("frequencyHz",skin);
		dampingRatio = new TextField("dampingRatio",skin);
		
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
		this.add(new Label("frequencyHz", skin)).colspan(2);
		this.add(this.frequencyHz).colspan(2).fillX();
		this.row();
		this.add(new Label("dampingRatio", skin)).colspan(2);
		this.add(this.dampingRatio).colspan(2).fillX();
		this.row();
		this.pack();
	}
	@Override
	public void update(Object data) {
		bind(DistanceJointData.class.cast(data).localAnchorA, "x", localAnchorAx);
		bind(DistanceJointData.class.cast(data).localAnchorA, "y", localAnchorAy);
		bind(DistanceJointData.class.cast(data).localAnchorB, "x", localAnchorBx);
		bind(DistanceJointData.class.cast(data).localAnchorB, "y", localAnchorBy);
		bind(data,"frequencyHz",frequencyHz);
		bind(data,"dampingRatio",dampingRatio);
		super.update(data);
	}
}
