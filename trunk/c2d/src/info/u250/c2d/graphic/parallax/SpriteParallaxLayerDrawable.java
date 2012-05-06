package info.u250.c2d.graphic.parallax;

import info.u250.c2d.engine.C2dCamera;
import info.u250.c2d.engine.Engine;
import info.u250.c2d.graphic.AnimationSprite;
import info.u250.c2d.graphic.parallax.ParallaxLayer.ParallaxLayerDrawable;
import info.u250.c2d.graphic.parallax.ParallaxLayer.ParallaxLayerResult;

import com.badlogic.gdx.graphics.g2d.Sprite;

/** the simple {@link ParallaxLayerDrawable} use a sprite */
public  class SpriteParallaxLayerDrawable implements ParallaxLayerDrawable{
	public Sprite object;
	public SpriteParallaxLayerDrawable(Sprite sprite){
		this.object = sprite;
	}
	@Override
	public float obtainWidth() {
		return this.object.getWidth();
	}
	@Override
	public float obtainHeight() {
		return this.object.getHeight();
	}
	@Override
	public void renderLayer(float delta,C2dCamera camera,ParallaxLayerResult parallaxLayerResult,ParallaxLayer parallaxLayer) {
		Engine.getSpriteBatch().setProjectionMatrix(camera.combined);
		Engine.getSpriteBatch().begin();
		this.object.setPosition(parallaxLayerResult.resultX, parallaxLayerResult.resultY);

		if(this.object instanceof AnimationSprite){
			AnimationSprite.class.cast(this.object).render(delta);
		}else{
			this.object.draw(Engine.getSpriteBatch());
		}
		Engine.getSpriteBatch().end();
		Engine.getSpriteBatch().setProjectionMatrix(Engine.getDefaultCamera().combined);
	}
}
