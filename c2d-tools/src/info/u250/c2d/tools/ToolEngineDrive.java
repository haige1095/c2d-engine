package info.u250.c2d.tools;

import info.u250.c2d.engine.Engine;
import info.u250.c2d.engine.EngineDrive;
import info.u250.c2d.engine.Scene;
import info.u250.c2d.engine.resources.AliasResourceManager;
import info.u250.c2d.tools.scenes.SceneWorkTable;

public class ToolEngineDrive implements EngineDrive{

	Scene sceneWorkTable ;
	
	@Override
	public EngineOptions onSetupEngine() {
		final EngineOptions opt = new EngineOptions(new String[]{
				"data/"
		},800,480);
		opt.gl20Enable = false;
		opt.resizeSync = true;
		opt.fps = false;
		return opt;
	}

	@Override
	public void onLoadedResourcesCompleted() {	
		sceneWorkTable = new SceneWorkTable();
		
		Engine.setMainScene(sceneWorkTable);
		
	}

	@Override
	public void dispose() {
	}

	@Override
	public void onResourcesRegister(AliasResourceManager<String> reg) {
		reg.skin("Skin", "data/skin/uiskin.json");
		reg.texture("CircleTexture", "data/circle.png");
		reg.texture("BoxTexture", "data/box.png");
		reg.textureAtlas("AAA", "data/cb2/pack");
		for(String res:Constants.TextureNames){
			reg.texture(res, "data/textures/"+res);
		}
	}

}
