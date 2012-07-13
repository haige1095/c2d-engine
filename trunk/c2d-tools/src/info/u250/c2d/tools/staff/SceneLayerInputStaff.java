package info.u250.c2d.tools.staff;

import com.badlogic.gdx.scenes.scene2d.ui.Table;

public abstract class SceneLayerInputStaff extends InputStaff {
	public SceneLayer layer;
	public abstract Table layoutInfo();
	public abstract void buildStaff();
}
