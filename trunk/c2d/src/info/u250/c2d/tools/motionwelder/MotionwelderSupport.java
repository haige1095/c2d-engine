package info.u250.c2d.tools.motionwelder;

import info.u250.c2d.tools.motionwelder.support.MSimpleAnimationPlayer;
import info.u250.c2d.tools.motionwelder.support.MSpriteData;
import info.u250.c2d.tools.motionwelder.support.MSpriteImageLoader;
import info.u250.c2d.tools.motionwelder.support.MSpriteLoader;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
/**
 * Motion Welder meets a definite need of J2ME Developers / Graphic artist
 *  to automate mobile game development process and answer 
 *  to today’s unwieldy process of developing animation for mobile.
 * This is the c2d support of [ http://www.motionwelder.com/ ]
 * @author lycying@gmail.com
 */
public class MotionwelderSupport {
	/** 
	 * make a new motionwelder instance
	 * @param fileName the filename of the .anu
	 * @param regions the regions that used by the .anu file
	 * @param initX the initialize position  of x
	 * @param initY the initialize position of y
	 */
	public static MSimpleAnimationPlayer makeMotionwelderAnimationPlayer(
			String fileName, TextureRegion[] regions,float initX,float initY) {
		try {
			MSpriteData mongoAnimationData = MSpriteLoader.loadMSprite(fileName, true, new C2dMSpriteImageLoader(regions));
			final MSimpleAnimationPlayer player = new MSimpleAnimationPlayer(mongoAnimationData, (int)initX, (int)initY);
			player.setAnimation(0);
			
			return player;
		} catch (Exception e) {
			Gdx.app.error("C2d", "Load Motionwelder file error :" + fileName);
		}
		return null;
	}
	/**
	 * copy a new instance from a MSimpleAnimationPlayer
	 * @param cpy the orgi animation player instance
	 */
	public static MSimpleAnimationPlayer cpy(MSimpleAnimationPlayer cpy){
		final MSimpleAnimationPlayer player = new MSimpleAnimationPlayer(cpy.getData(), cpy.getSpriteDrawX(), cpy.getSpriteDrawY());
		player.setAnimation(cpy.getAnimation());
		return player;
	}
	private static class C2dMSpriteImageLoader implements MSpriteImageLoader {
		private TextureRegion[] regions;

		public C2dMSpriteImageLoader(TextureRegion[] regions) {
			this.regions = regions;
		}

		@Override
		public TextureRegion[] loadImageClip(String spriteName, int imageId,
				int x, int y, int w, int h, int orientationUsedInStudio) {
			
			TextureRegion[] rets = new TextureRegion[3];
			TextureRegion region = new TextureRegion(regions[imageId], x, y, w,h);

			rets[0] = rets[1] = rets[2] = region;
			return rets;
		}

		@Override
		public TextureRegion[] loadImage(String spriteName, int imageId,int orientationUsedInStudio) {
			return null;
		}

	}
}
