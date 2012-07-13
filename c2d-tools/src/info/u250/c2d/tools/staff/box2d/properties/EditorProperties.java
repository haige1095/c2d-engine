package info.u250.c2d.tools.staff.box2d.properties;

import info.u250.c2d.engine.Engine;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

public abstract class EditorProperties extends Table{
	
	protected Skin skin;
	
	public EditorProperties(){
		skin = Engine.resource("Skin");
	}
	Map<Actor,BindSupport> bindMap = new HashMap<Actor,BindSupport>();
	void bind(final Object obj,final String name,final Actor textField){
		final BindSupport support = bindMap.get(textField);
		if(null!=support){
			support.update(obj);
		}else{
			bindMap.put(textField, new BindSupport(obj, name, textField));
		}	
	}
	public abstract void update(Object object);
}
