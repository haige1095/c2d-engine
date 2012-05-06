package info.u250.c2d.tools.staff.box2d.properties;

import info.u250.c2d.physical.box2d.loader.cbt.data.PulleyJointData;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;

public class PulleyJointProperties extends JointProperties{
	final TextField localAnchorAx,localAnchorAy,localAnchorBx,localAnchorBy;
	final TextField groundAnchorAx,groundAnchorAy,groundAnchorBx,groundAnchorBy;
	final TextField ratio;
	public PulleyJointProperties(){
		super();
		localAnchorAx = new TextField(skin);
		localAnchorAy = new TextField(skin);
		localAnchorBx = new TextField(skin);
		localAnchorBy = new TextField(skin);
		
		groundAnchorAx = new TextField(skin);
		groundAnchorAy = new TextField(skin);
		groundAnchorBx = new TextField(skin);
		groundAnchorBy = new TextField(skin);
		ratio = new TextField(skin);
		
		
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
		this.add(new Label("ratio", skin)).colspan(2);
		this.add(this.ratio).colspan(2).fillX();
		this.row();
		this.add(new Label("groundAnchorAx", skin)).colspan(2);
		this.add(this.groundAnchorAx).colspan(2).fillX();
		this.row();
		this.add(new Label("groundAnchorAy", skin)).colspan(2);
		this.add(this.groundAnchorAy).colspan(2).fillX();
		this.row();
		this.add(new Label("groundAnchorBx", skin)).colspan(2);
		this.add(this.groundAnchorBx).colspan(2).fillX();
		this.row();
		this.add(new Label("groundAnchorBx", skin)).colspan(2);
		this.add(this.groundAnchorBy).colspan(2).fillX();
		this.row();
		
		this.pack();
	}
	@Override
	public void update(Object data) {
		bind(PulleyJointData.class.cast(data).localAnchorA, "x", localAnchorAx);
		bind(PulleyJointData.class.cast(data).localAnchorA, "y", localAnchorAy);
		bind(PulleyJointData.class.cast(data).localAnchorB, "x", localAnchorBx);
		bind(PulleyJointData.class.cast(data).localAnchorB, "y", localAnchorBy);
		bind(data,"ratio",ratio);
		bind(PulleyJointData.class.cast(data).groundAnchorA, "x", groundAnchorAx);
		bind(PulleyJointData.class.cast(data).groundAnchorA, "y", groundAnchorAy);
		bind(PulleyJointData.class.cast(data).groundAnchorB, "x", groundAnchorBx);
		bind(PulleyJointData.class.cast(data).groundAnchorB, "y", groundAnchorBy);
		super.update(data);
	}
}
