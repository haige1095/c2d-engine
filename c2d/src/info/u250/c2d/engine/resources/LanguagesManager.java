package info.u250.c2d.engine.resources;

import java.io.IOException;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.ObjectMap;
import com.badlogic.gdx.utils.XmlReader;
import com.badlogic.gdx.utils.XmlReader.Element;

public class LanguagesManager {
	ObjectMap<String, String> map = new ObjectMap<String, String>();
	private String lang = "en_US";
	public void setLang(String lang){
		this.lang = lang;
	}
	public void load(String name){
		try {
			parse(new XmlReader().parse(Gdx.files.internal("lang/"+name+"-"+lang+".xml")));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public String getString(String key){
		return map.get(key);
	}
	private void parse(Element ele){
		map.clear();
		for(int i=0;i<ele.getChildCount();i++){
			Element e = ele.getChild(i);
			map.put(e.getAttribute("name"), e.getText());
		}
	}
}
