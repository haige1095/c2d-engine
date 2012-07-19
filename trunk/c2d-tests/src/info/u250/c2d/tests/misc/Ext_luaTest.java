package info.u250.c2d.tests.misc;

import info.u250.c2d.engine.Engine;
import info.u250.c2d.engine.EngineDrive;
import info.u250.c2d.engine.Scene;
import info.u250.c2d.engine.resources.AliasResourceManager;
import info.u250.c2d.scripts.lua.LoadScriptLua;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;


public class Ext_luaTest extends Engine {
	@Override
	protected EngineDrive onSetupEngineDrive() {
		return new EngineX();
	}
	@Override
	public void dispose () {
		super.dispose();
	}
	
	private class EngineX implements EngineDrive{
		LoadScriptLua script;
		String text = "ERROR:Not Loading!";
		
		@Override
		public void onResourcesRegister(AliasResourceManager<String> reg) {
		}
		@Override
		public void dispose() {}
		@Override
		public EngineOptions onSetupEngine() {
			return new EngineOptions(new String[]{},800,480);
		}

		@Override
		public void onLoadedResourcesCompleted() {
			
			script = new LoadScriptLua("helloworld.lua");
			
			Engine.setMainScene(new Scene() {
				@Override
				public void render(float delta) {
					//This is loading a Global with my class
		            text = script.getGlobalString("int");
		            //Run a Lua Function with my class
		            script.runScriptFunction("render", Gdx.gl);
		            //Update doesnt work "properly" you can edit the script and the app will change
		            //But it will crash every once in a while :/ 
		            script.update();
		            Engine.getSpriteBatch().begin();
		            Engine.getDefaultFont().draw(Engine.getSpriteBatch(), text, 100, 100);
		            //Run a Lua Function with two objects with my class
		            //You can edit LoadScript.java and add more objects
		            script.runScriptFunction("fontBatch", Engine.getDefaultFont(), Engine.getSpriteBatch());
		            Engine.getSpriteBatch().end();
				}
				@Override
				public InputProcessor getInputProcessor() {
					return null;
				}
				@Override
				public void update(float delta) {	
				}
				@Override
				public void hide() {	
				}
				@Override
				public void show() {
				}
			});	
		}
	}
}
