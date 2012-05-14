package info.u250.c2d.utils;

import com.badlogic.gdx.graphics.Color;
/**
  @author lycying@gmail.com
 */
public class ColorUtils {

	public static  Color colorFromHex(long hex){
		float a = (hex & 0xFF000000L) >> 24;
		float r = (hex & 0xFF0000L) >> 16;
		float g = (hex & 0xFF00L) >> 8;
		float b = (hex & 0xFFL);

		return new Color(r / 255f, g / 255f, b / 255f, a / 255f);
	}

}
