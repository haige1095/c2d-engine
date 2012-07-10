package info.u250.c2d.tiled;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.tiled.TiledMap;

public class TiledCellDrawable extends TileBaseObject{
	private int row,col;
	public int getRow() {
		return row;
	}
	public int getCol() {
		return col;
	}
	public TiledCellDrawable(TiledMap map,TextureRegion region,int row,int col){
		super(region);
		this.row = row;
		this.col = col;
		this.setSize(map.tileWidth, map.tileHeight);
		this.setPosition(this.getWidth()*col, this.getHeight()*row);//flip it
	}
	
}
