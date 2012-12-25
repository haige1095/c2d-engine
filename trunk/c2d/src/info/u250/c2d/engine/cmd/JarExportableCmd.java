package info.u250.c2d.engine.cmd;

import com.badlogic.gdx.Graphics.DisplayMode;

import info.u250.c2d.engine.Engine;
import info.u250.c2d.engine.EngineCallback;

public class JarExportableCmd {
	/**
	 * This command only run on desktop . Add this , you can export the project 
	 * into a jar to run it
	 */
	public static final void process(){
		final EngineCallback callback = Engine.getEngineCallback();
		Engine.setEngineCallback(new EngineCallback() {
			@Override
			public void preLoad(DisplayMode mode, String[] assets) {
				//the sort is important , do not change it
				Engine.getAliasResourceManager().setLoopLoader(new LoopLoaderJar());
				callback.preLoad(mode, assets);
			}
			
			@Override
			public void postLoad() {
				callback.postLoad();
			}
		});
	} 
}
