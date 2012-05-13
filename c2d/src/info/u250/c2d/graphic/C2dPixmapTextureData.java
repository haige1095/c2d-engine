package info.u250.c2d.graphic;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.glutils.PixmapTextureData;

public class C2dPixmapTextureData extends PixmapTextureData {
	public C2dPixmapTextureData(Pixmap pixmap) {
		super(pixmap, null, false, false);
	}

	@Override 
	public boolean isManaged(){
		return true;
	}
}
