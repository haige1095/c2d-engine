package info.u250.c2d.tools.pack;

import java.io.File;

import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.tools.imagepacker.TexturePacker.Settings;
import com.badlogic.gdx.tools.skins.SkinPacker;

public class SkinPack {
	static public void main (String[] args) throws Exception {
		File inputDir = new File("/Users/lycy/trunk/public/c2d/c2d-tools/assets-raw/skin");
		File skinFile = new File("/Users/lycy/trunk/public/c2d/c2d-tools/assets/data/skin/uiskin.json");
		File imageFile = new File("/Users/lycy/trunk/public/c2d/c2d-tools/assets/data/skin/uiskin.png");
		Settings settings = new Settings();
		settings.defaultFilterMag = TextureFilter.Linear;
		settings.defaultFilterMin = TextureFilter.Linear;
		settings.duplicatePadding = false;
		SkinPacker.process(settings, inputDir, skinFile, imageFile);
	}
}
