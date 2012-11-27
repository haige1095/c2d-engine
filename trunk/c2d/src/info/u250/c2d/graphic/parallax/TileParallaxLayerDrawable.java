package info.u250.c2d.graphic.parallax;

import info.u250.c2d.engine.C2dCamera;
import info.u250.c2d.graphic.parallax.ParallaxLayer.ParallaxLayerDrawable;
import info.u250.c2d.graphic.parallax.ParallaxLayer.ParallaxLayerResult;

import com.badlogic.gdx.graphics.g2d.tiled.TileMapRenderer;

public class TileParallaxLayerDrawable implements ParallaxLayerDrawable{
	TileMapRenderer object;
	public TileParallaxLayerDrawable(TileMapRenderer tileMapRenderer){
		object = tileMapRenderer;
	}
	

	@Override
	public float obtainWidth() {
		return object.getMapWidthUnits();
	}

	@Override
	public float obtainHeight() {
		return object.getMapHeightUnits();
	}

	@Override
	public void renderLayer(float delta,C2dCamera camera,
			ParallaxLayerResult parallaxLayerResult, ParallaxLayer parallaxLayer) {
		
		object.render(camera);
	}
}
