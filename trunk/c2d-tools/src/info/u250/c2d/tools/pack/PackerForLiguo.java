package info.u250.c2d.tools.pack;

import com.badlogic.gdx.tools.imagepacker.TexturePacker2;
import com.badlogic.gdx.tools.imagepacker.TexturePacker2.Settings;

public class PackerForLiguo {


	public static void main(String args[]) throws Exception {
		Settings settings = new Settings();
		settings.alias = true;
		settings.edgePadding=false;
		settings.maxWidth = 1024;
		settings.maxHeight = 1024;

		TexturePacker2
		.process(settings,
				"assets-raw/cb2",
				"assets/data/cb2",
				"cb2");
	}
}