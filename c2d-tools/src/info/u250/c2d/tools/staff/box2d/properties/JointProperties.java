package info.u250.c2d.tools.staff.box2d.properties;

import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;

public abstract class JointProperties extends EditorProperties{
	final CheckBox collideConnected;
	final TextField name;
//	final TextField type;
	public JointProperties(){
		collideConnected = new CheckBox(skin) ;
		name = new TextField("", "", skin);
//		type = new TextField("", "", skin);
		
		this.add(new Label("name:", skin));
		this.add(this.name).colspan(3).fillX();
		this.row();
		this.add(new Label("collideConnected", skin)).colspan(3);
		this.add(this.collideConnected).colspan(1).fillX();
		this.row();
	}
	
	
	public void update(Object data){
		bind(data, "collideConnected",collideConnected );
		bind(data, "name", name);
	}
}
