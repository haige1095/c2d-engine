package info.u250.c2d.tiled;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public abstract class TileBaseObject extends Image{
	public TileBaseObject(TextureRegion region){
		super(region);
	}
	
	private Rectangle rect = new Rectangle();

	public Rectangle getRectangle(){
		rect.x = this.getX();
		rect.y = this.getY();
		rect.width = this.getWidth();
		rect.height = this.getHeight();
		return rect;
	}
	
	public boolean overlaps(TileBaseObject object){
		return object.getRectangle().overlaps(this.getRectangle());
	}
}