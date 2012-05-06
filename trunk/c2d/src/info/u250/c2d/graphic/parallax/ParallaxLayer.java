package info.u250.c2d.graphic.parallax;

import info.u250.c2d.engine.C2dCamera;

import com.badlogic.gdx.math.Vector2;

/**
 * This is the layer of the  parallax-background
 * @author lycying@gmail.com
 */
public class ParallaxLayer{
	/**the layer to draw
	 * @author lycying@gmail.com
	 */
	public static abstract interface ParallaxLayerDrawable{
		public float obtainWidth();
		public float obtainHeight();
		public void renderLayer(final float delta,final C2dCamera camera,final ParallaxLayerResult parallaxLayerResult,final ParallaxLayer parallaxLayer);
	}
	/**
	 * the result of the finger
	 * @author lycying@gmail.com
	 */
	public static final class ParallaxLayerResult{
		public float resultX;
		public float resultY;
		@Override
		public String toString() {
			return "ParallaxLayerResult [resultX=" + resultX + ", resultY="
					+ resultY + "]";
		}
	}
	public ParallaxLayerDrawable drawable ;
	public Vector2 parallaxRatio;
	public Vector2 startPosition;
	
	public Vector2 padding ;
	
	public int loopX;
	public int loopY;
	
	public String name ;
	
	public ParallaxLayer(String name,ParallaxLayerDrawable drawable,Vector2 parallaxRatio,Vector2 padding,int loopX,int loopY){
		this(name,drawable, parallaxRatio,padding,loopX,loopY,new Vector2(0,0));
	}
	/**
	 * @param drawable   the TextureRegion to draw , this can be any width/height
	 * @param parallaxRatio	the relative speed of x,y {@link ParallaxGroup#ParallaxBackground(ParallaxLayer[], float, float, Vector2)}
	 * @param startPosition the init position of x,y
	 * @param padding  the padding of the region at x,y
	 */
	public ParallaxLayer(String name,ParallaxLayerDrawable drawable,Vector2 parallaxRatio,Vector2 padding,int loopX,int loopY,Vector2 startPosition){
		this.drawable  = drawable;
		this.parallaxRatio = parallaxRatio;
		this.startPosition = startPosition;
		this.padding = padding;
		this.loopX= loopX;
		this.loopY= loopY;
		this.name = name;
	}
}