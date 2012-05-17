package info.u250.c2d.scripts.lua;

import org.keplerproject.luajava.LuaState;
import org.keplerproject.luajava.LuaStateFactory;

import com.badlogic.gdx.files.FileHandle;

public class LoadScriptLua {
	public LuaState luaState;
	private FileHandle fileHandle;
	private String source;

	/**
	 * Constructor
	 * 
	 * @param fileHandle File name with Lua script.
	 */
	public LoadScriptLua(FileHandle fileHandle) {
		this.fileHandle = fileHandle;
		// This next part we do because Android cant use LdoFile instead
		// we load the lua file using gdx and convert it into a string and load
		// it
		String source = this.fileHandle.readString();
		this.loadSource(source);
	}

	public LoadScriptLua(final String source) {
		this.loadSource(source);
		this.source = source;
	}
	private void loadSource(final String source){
		this.luaState = LuaStateFactory.newLuaState();
		this.luaState.openLibs();
		this.luaState.LdoString(source);
	}
	public void update() {
		if(this.fileHandle!=null){
			this.luaState.LdoString(fileHandle.readString());
		}else{
			this.luaState.LdoString(source);
		}
	}

	/**
	 * Ends the use of Lua environment.
	 */
	public void closeScript() {
		this.luaState.close();
	}

	/**
	 * Call a Lua function inside the Lua script to insert data into a Java
	 * object passed as parameter
	 * 
	 * @param functionName
	 *            Name of Lua function.
	 * @param obj
	 *            A Java object.
	 */
	public void runScriptFunction(String functionName, Object obj) {
		this.luaState.getGlobal(functionName);
		this.luaState.pushJavaObject(obj);
		this.luaState.call(1, 0);
	}

	// Same thing as above but with another object
	public void runScriptFunction(String functionName, Object obj, Object obj2) {
		this.luaState.getGlobal(functionName);
		this.luaState.pushJavaObject(obj);
		this.luaState.pushJavaObject(obj2);
		this.luaState.call(2, 0);
	}

	public String getGlobalString(String globalName) {
		this.luaState.getGlobal(globalName);
		return this.luaState.toString(luaState.getTop());
	}
}
