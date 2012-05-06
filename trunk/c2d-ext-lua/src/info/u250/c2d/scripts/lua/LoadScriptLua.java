package info.u250.c2d.scripts.lua;

import org.keplerproject.luajava.LuaState;
import org.keplerproject.luajava.LuaStateFactory;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

public class LoadScriptLua {
	public LuaState luaState;
	private String fileName;

	/**
	 * Constructor
	 * 
	 * @param fileName
	 *            File name with Lua script.
	 */
	public LoadScriptLua(final String fileName) {
		this.luaState = LuaStateFactory.newLuaState();
		this.luaState.openLibs();
		// This next part we do because Android cant use LdoFile instead
		// we load the lua file using gdx and convert it into a string and load
		// it
		FileHandle handle = Gdx.files.internal(fileName);
		String file = handle.readString();
		this.luaState.LdoString(file);
		this.fileName = fileName;
	}

	public void update() {
		FileHandle handle = Gdx.files.internal(this.fileName);
		String file = handle.readString();
		this.luaState.LdoString(file);
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
