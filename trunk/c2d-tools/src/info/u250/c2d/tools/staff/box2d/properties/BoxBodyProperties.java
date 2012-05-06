package info.u250.c2d.tools.staff.box2d.properties;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;

public class BoxBodyProperties extends BodyProperties{
	final TextField width,height;
	public BoxBodyProperties(){
		super();
		width = new TextField(skin);
		height = new TextField(skin);
		this.add(new Label("width", skin));
		this.add(this.width).colspan(3).fillX();
		this.row();
		this.add(new Label("height", skin));
		this.add(this.height).colspan(3).fillX();
		this.row();
		this.pack();
	}
	@Override
	public void update(Object data) {
		bind(data, "width", width);
		bind(data, "height", height);
		super.update(data);
		
	}
}
