package info.u250.c2d.tests;

import java.io.File;
import java.io.IOException;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.jogl.JoglApplication;
import com.badlogic.gdx.tiledmappacker.TiledMapPacker;
import com.badlogic.gdx.tools.imagepacker.TexturePacker.Settings;

public class C2dTiledPaker {
	public static void main (String[] args) {
		File  inputDir, outputDir;
		inputDir = new File("/home/lycy/trunk/public/c2d/c2d-tests-jogl/assets-raw/tiledmap");
		outputDir = new File("/home/lycy/trunk/public/c2d/c2d-tests-android/assets/data/tiledmap");
		Settings settings = new Settings();

		// Note: the settings below are now default...
		settings.padding = 2;
		settings.duplicatePadding = true;

		// Create a new JoglApplication so that Gdx stuff works properly
		new JoglApplication(new ApplicationListener() {
			@Override
			public void create () {
			}

			@Override
			public void dispose () {
			}

			@Override
			public void pause () {
			}

			@Override
			public void render () {
			}

			@Override
			public void resize (int width, int height) {
			}

			@Override
			public void resume () {
			}
		}, "", 0, 0, false);

		TiledMapPacker packer = new TiledMapPacker();


		if (!inputDir.exists()) {
			throw new RuntimeException("Input directory does not exist");
		}

		try {
			packer.processMap(inputDir, outputDir, settings);
		} catch (IOException e) {
			throw new RuntimeException("Error processing map: " + e.getMessage());
		}

	}
}
