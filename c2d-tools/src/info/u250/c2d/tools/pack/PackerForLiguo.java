package info.u250.c2d.tools.pack;

import com.badlogic.gdx.tools.imagepacker.TexturePacker;
import com.badlogic.gdx.tools.imagepacker.TexturePacker.Settings;

public class PackerForLiguo {


	public static void main(String args[]) throws Exception {
		Settings settings = new Settings();
		settings.alias = true;
		settings.stripWhitespace = false;
		settings.rotate = false;
		settings.edgePadding=false;
		settings.maxWidth = 1024;
		settings.maxHeight = 1024;

		TexturePacker
		.process(settings,
				"/Users/lycy/trunk/public/c2d/c2d-tools/assets-raw/cb2",
				"/Users/lycy/trunk/public/c2d/c2d-tools/assets/data/cb2");
	}
}