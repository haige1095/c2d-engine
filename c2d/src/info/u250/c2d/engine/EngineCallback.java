package info.u250.c2d.engine;

import com.badlogic.gdx.Graphics.DisplayMode;

public interface EngineCallback {
	void preLoad(DisplayMode mode , String[] assets);
	void postLoad();
}
