package info.u250.c2d.tools.motionwelder;

import info.u250.c2d.tools.motionwelder.support.MSimpleAnimationPlayer;
import info.u250.c2d.tools.motionwelder.support.MSprite;
import info.u250.c2d.tools.motionwelder.support.MSpriteData;
import info.u250.c2d.tools.motionwelder.support.MSpriteImageLoader;
import info.u250.c2d.tools.motionwelder.support.MSpriteLoader;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class MotionwelderSupport {
	public static MSimpleAnimationPlayer makeMotionwelderAnimationPlayer(
			String fileName, TextureRegion[] regions,float initX,float initY) {
		try {
			MSpriteData mongoAnimationData = MSpriteLoader.loadMSprite(
					fileName, true, new C2dMSpriteImageLoader(regions));
			final MSimpleAnimationPlayer player = new MSimpleAnimationPlayer(
					mongoAnimationData, (int)initX, (int)initY);
			player.setSpriteOrientation(new Integer(MSprite.ORIENTATION_FLIP_V)
					.byteValue());
			player.setAnimation(0);
			
			return player;
		} catch (Exception e) {
			Gdx.app.error("C2d", "Load Motionwelder file error :" + fileName);
		}
		return null;
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
		public TextureRegion[] loadImage(String spriteName, int imageId,
				int orientationUsedInStudio) {
			return null;
		}

	}
}
