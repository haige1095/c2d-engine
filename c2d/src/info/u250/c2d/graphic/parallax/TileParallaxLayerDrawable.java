package info.u250.c2d.graphic.parallax;

import info.u250.c2d.engine.C2dCamera;
import info.u250.c2d.engine.Engine;
import info.u250.c2d.graphic.parallax.ParallaxLayer.ParallaxLayerDrawable;
import info.u250.c2d.graphic.parallax.ParallaxLayer.ParallaxLayerResult;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.tiled.TileMapRenderer;

public class TileParallaxLayerDrawable implements ParallaxLayerDrawable{
	TileMapRenderer object;
	OrthographicCamera cam ;
	public TileParallaxLayerDrawable(TileMapRenderer tileMapRenderer){
		cam = new OrthographicCamera(Engine.getEngineConfig().width,Engine.getEngineConfig().height);
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
		cam.position.set(camera.position.cpy());
		cam.position.z = 0;
		cam.position.add(-parallaxLayerResult.resultX, -parallaxLayerResult.resultY, 0);
		cam.update();
		object.render(cam);
	}
}
