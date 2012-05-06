package info.u250.c2d.tools.staff.box2d;

import info.u250.c2d.tools.staff.SceneLayer;

public class Box2dSceneLayer extends SceneLayer {

	public Box2dSceneLayer(String name) {
		super(name, new Box2dInputStaff(), new Box2dAdapter());
	}

	@Override
	public void start() {
		Box2dAdapter.class.cast(this.adapter).play();
	}

	@Override
	public void stop() {
		Box2dAdapter.class.cast(this.adapter).stop();
	}

}
