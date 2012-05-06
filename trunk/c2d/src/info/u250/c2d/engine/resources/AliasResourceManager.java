package info.u250.c2d.engine.resources;

import info.u250.c2d.engine.Engine;
import info.u250.c2d.engine.resources.rules.RuleFont;
import info.u250.c2d.engine.resources.rules.RuleMusic;
import info.u250.c2d.engine.resources.rules.RuleSkin;
import info.u250.c2d.engine.resources.rules.RuleSound;
import info.u250.c2d.engine.resources.rules.RuleTexture;
import info.u250.c2d.engine.resources.rules.RuleTextureAtlas;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Array;

/**
 * @author lycying@gmail.com
 */
public class AliasResourceManager<K>  {
	/**
	 * @author lycying@gmail.com
	 * this is a rule to load resources , if the file's suffix or contains dictionary match the load rule , then load it .
	 * such as the texture has the suffix ".png"
	 */
	public interface LoadResourceRule{
		public boolean match(FileHandle file);
	}
	/**the map to hold all resources*/
	private Map<K, Object>  resources = new HashMap<K, Object>();
	/**the map to hold all resources's alias*/
	private Map<K, String>  resources_alias = new HashMap<K, String>();
	/**remove the resources use the key */
	public synchronized void unload(String key){
		String key_alias = resources_alias.get(key);
		if(null!=key_alias){
			Engine.getAssetManager().unload(key_alias);
			resources_alias.remove(key);
		}
		resources.remove(key);
	}
	
	/** a simple method to get the real resources */
	@SuppressWarnings("unchecked")
	public <T> T get(K id) {
		return (T)resources.get(id);
	}	
	/** quick access texture*/
	public void texture(K key,String res){
		this.resources.put(key, Engine.getAssetManager().get(res, Texture.class));
		this.resources_alias.put(key, res);
	}
	/** quick access sound resource */
	public void sound(K key,String res){
		this.resources.put(key, Engine.getAssetManager().get(res, Sound.class));
		this.resources_alias.put(key, res);
	}
	/** quick access music resource */
	public void music(K key,String res){
		this.resources.put(key, Engine.getAssetManager().get(res, Music.class));
		this.resources_alias.put(key, res);
	}
	/** quick access texture atlas */
	public void textureAtlas(K key,String res){
		this.resources.put(key, Engine.getAssetManager().get(res, TextureAtlas.class));
		this.resources_alias.put(key, res);
	}
	/** quick access bitmap font */
	public void font(K key,String res){
		this.resources.put(key, Engine.getAssetManager().get(res, BitmapFont.class));
		this.resources_alias.put(key, res);
	}
	/** quick access skin */
	public void skin(K key,String res){
		this.resources.put(key, Engine.getAssetManager().get(res, Skin.class));
		this.resources_alias.put(key, res);
	}
	/** quickly put any type resource object */
	public <T> void object(K key,T res){
		this.resources.put(key, res);
	}
	
	/**load resources*/
	public void load(String dataDir){
		if(Gdx.app.getType() == ApplicationType.Desktop){
			loadDesktop(dataDir);
		}else{
			FileHandle file = Gdx.files.internal(dataDir);
			if(null!=file){
				if(file.isDirectory()){
					loadDirectory(file);
				}else{
					loadFile(file);
				}
			}
		}
	}
	private void loadDesktop(String dataDir){
		File file = new File( AliasResourceManager.class.getResource("/"+dataDir).getFile() );
		if(file.isDirectory()){
			for(String f:file.list()){
				loadDesktop((dataDir.endsWith("/")?dataDir:(dataDir+"/"))+f);
			}
		}else{
			loadFile(Gdx.files.internal(dataDir));
		}
	}
	private void loadDirectory(FileHandle dir){
		for(FileHandle handle :dir.list()){
			if(handle.isDirectory()){
				this.loadDirectory(handle);
			}else{
				this.loadFile(handle);
			}
		}
	}
	private void loadFile(FileHandle file){
		for(LoadResourceRule rule:rules){
			if(rule.match(file)){
				return;
			}
		}
		
	}
	static Array<LoadResourceRule>  rules = new Array<LoadResourceRule>();
	static{
		rules.add(new RuleTexture());
		rules.add(new RuleSound());
		rules.add(new RuleMusic());
		rules.add(new RuleTextureAtlas());
		rules.add(new RuleFont());
		rules.add(new RuleSkin());
	}
	public void addRule(LoadResourceRule rule){
		rules.add(rule);
	}

}