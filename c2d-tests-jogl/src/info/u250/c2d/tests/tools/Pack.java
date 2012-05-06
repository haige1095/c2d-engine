package info.u250.c2d.tests.tools;

import com.badlogic.gdx.tools.imagepacker.TexturePacker;
import com.badlogic.gdx.tools.imagepacker.TexturePacker.Settings;

public class Pack {
	public static void main(String[] args) {
		Settings settings = new Settings();
		settings.maxHeight = 1024;
		settings.maxHeight = 1024;
		settings.stripWhitespace = false;
		settings.rotate = false;
		

		TexturePacker.process(settings, 
				"/Users/lycy/trunk/public/c2d/c2d-tests-jogl/assets-raw/animations", 
				"/Users/lycy/trunk/public/c2d/c2d-tests-android/assets/data/animationsprite/pack");
	}
}
