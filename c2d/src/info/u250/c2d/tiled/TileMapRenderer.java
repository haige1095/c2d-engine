package info.u250.c2d.tiled;

import info.u250.c2d.graphic.C2dStage;

import com.badlogic.gdx.graphics.g2d.tiled.TileAtlas;
import com.badlogic.gdx.graphics.g2d.tiled.TiledLayer;
import com.badlogic.gdx.graphics.g2d.tiled.TiledMap;
import com.badlogic.gdx.scenes.scene2d.Group;

public abstract class TileMapRenderer extends Group{
	protected TiledMap map;
	public TiledMap getMap() {
		return map;
	}

	public TileMapRenderer(C2dStage stage,TiledMap map,TileAtlas atlas){
		this.map = map ;
		this.setSize(map.width*map.tileWidth, map.height*map.tileHeight);
		atlas.flipRegions(false, false);
		for (int i = 0; i < map.layers.size(); i++) {
			TiledLayer tiledLayer = map.layers.get(i);
			TiledLayerDrawable tiledLayerDrawable = new TiledLayerDrawable(map,atlas,tiledLayer);			
			this.addActor(tiledLayerDrawable);
		}
		stage.addActor(this);
		this.processObjectGroups();
	}
	public abstract void processObjectGroups();
}
