package info.u250.c2d.tools.staff.box2d.properties;

import info.u250.c2d.physical.box2d.loader.cbt.data.BodyData;

import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;

public abstract class BodyProperties extends EditorProperties{
	final TextField density;
	final TextField friction;
	final TextField restitution;
	final TextField filter_categoryBits;
	final TextField filter_groupIndex;
	final TextField filter_maskBits;
	final CheckBox sensor;
	final CheckBox dynamic;
	final TextField angle;
	final TextField centerX;
	final TextField centerY;
	final TextField name;
	final TextField res;
	final TextField userData;
	
	
	public BodyProperties(){
		name = new TextField("", skin);
		res = new TextField("", skin);
		userData = new TextField("", skin);
		density = new TextField("", skin);
		friction = new TextField("", skin);
		restitution = new TextField("",  skin);
		filter_categoryBits = new TextField("", skin) ;
		filter_groupIndex = new TextField("", skin) ;
		filter_maskBits = new TextField("",  skin) ;
		sensor = new CheckBox("sensor",skin) ;
		dynamic = new CheckBox("dynamic",skin) ;
		angle = new TextField("angle", skin) ;
		centerX = new TextField("centerX", skin) ;
		centerY = new TextField("centerY", skin) ;
		this.left();
		
		this.add(new Label("name:", skin));
		this.add(this.name).colspan(3).fillX();
		this.row();
		this.add(new Label("centerX", skin));
		this.add(this.centerX).colspan(3).fillX();
		this.row();
		this.add(new Label("centerY", skin));
		this.add(this.centerY).colspan(3).fillX();
		this.row();
		this.add(new Label("angle", skin));
		this.add(this.angle).colspan(3).fillX();
		this.row();
		this.add(new Label("res:", skin));
		this.add(this.res).colspan(3).fillX();
		this.row();
		this.add(new Label("userData:", skin));
		this.add(this.userData).colspan(3).fillX();
		this.row();
		
		
		this.add(new Label("density", skin));
		this.add(this.density).width(40);
		this.add(new Label("friction", skin));
		this.add(this.friction).width(40);
		this.row();
		this.add(new Label("restitution", skin));
		this.add(this.restitution).width(40);
		this.row();
		
		this.add(new Label("sensor", skin));
		this.add(this.sensor);
		this.add(new Label("dynamic", skin));
		this.add(this.dynamic);
		this.row();
	
		
		this.add(new Label("groupIndex", skin));
		this.add(this.filter_groupIndex).width(40);
		this.add(new Label("maskBits", skin));
		this.add(this.filter_maskBits).width(40);
		this.row();
		this.add(new Label("categoryBits", skin));
		this.add(this.filter_categoryBits).width(40);
		this.row();
		
	}
	
	
	public void update(Object data){
		bind(data, "name", name);
		bind(data, "data", userData);
		bind(data, "res", res);
		bind(data, "isSensor", sensor);
		bind(data, "isDynamic", dynamic);
		bind(data, "density", density);
		bind(data, "friction", friction);
		bind(data, "restitution", restitution);
		bind(data, "angle", angle);
		bind(BodyData.class.cast(data).center, "x", centerX);
		bind(BodyData.class.cast(data).center, "y", centerY);
		bind(data, "filter_categoryBits",filter_categoryBits );
		bind(data, "filter_groupIndex", filter_groupIndex);
		bind(data, "filter_maskBits", filter_maskBits);
	}
}
