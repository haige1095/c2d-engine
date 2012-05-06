package info.u250.c2d.tools.staff;

import info.u250.c2d.engine.Engine;
import info.u250.c2d.engine.SceneGroup;

import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public abstract class SceneLayer extends SceneGroup {
	public abstract void start();
	public abstract void stop();
	public SceneLayerInputStaff inputStaff ;
	public SceneAdapter adapter ; 
	protected Skin skin ;
	
	public SceneLayer(String name,SceneLayerInputStaff inputStaff,SceneAdapter adapter){
		this.name = name;
		this.inputStaff = inputStaff;
		this.adapter = adapter;
		this.inputStaff.layer = this;
		this.add(this.adapter);
		this.add(this.inputStaff);
		this.skin = Engine.resource("Skin");
		this.inputStaff.buildStaff();
	}
	
	@Override
	public void render(float delta) {
		if(this.active){
			super.render(delta);
		}else{
			this.adapter.render(delta);
		}
	}
	boolean active = true;
	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	String name ;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
