package info.u250.c2d.tiled;

import com.badlogic.gdx.graphics.g2d.tiled.TileAtlas;
import com.badlogic.gdx.graphics.g2d.tiled.TiledLayer;
import com.badlogic.gdx.graphics.g2d.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Group;

public class TiledLayerDrawable extends Group{
	public TiledLayerDrawable(TiledMap map,TileAtlas atlas,TiledLayer tiledLayer){	
		this.setSize(tiledLayer.getWidth()*map.tileWidth,tiledLayer.getHeight()*map.tileHeight);
		for(int i = 0;i< tiledLayer.tiles.length ;i++){
			for(int j = 0;j< tiledLayer.tiles[i].length ;j++){
				int cell = tiledLayer.tiles[i][j];
				if(cell == 0)continue;
				TiledCellDrawable cellObject = new TiledCellDrawable(map,atlas.getRegion(cell),tiledLayer.tiles.length-i-1,j);
				this.addActor(cellObject);
			}
		}
	}
	public void processProperties(TiledLayer tiledLayer){
	}
	
	Rectangle cullingArea = new Rectangle();
	@Override
	public void act(float delta) {
		super.act(delta);
		cullingArea.x = -this.getParent().getX();
		cullingArea.y = -this.getParent().getY();
		cullingArea.width = this.getWidth();
		cullingArea.height = this.getHeight();
		this.setCullingArea(cullingArea);
	}
}
