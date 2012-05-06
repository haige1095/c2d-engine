package info.u250.c2d.tests;

import info.u250.c2d.engine.Engine;
import info.u250.c2d.engine.EngineDrive;
import info.u250.c2d.engine.Scene;
import info.u250.c2d.engine.resources.AliasResourceManager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeBitmapFontData;


public class FreeTypeJapanTest extends Engine {
	private FreeTypeFontGenerator freetypeGenerator;
    private SpriteBatch spriteBatch;
    
    private BitmapFont freetypeBitmapFont;

	@Override
	protected EngineDrive onSetupEngineDrive() {
		return new EngineX();
	}
	@Override
	public void dispose () {
		super.dispose();
	}
	
	private class EngineX implements EngineDrive{
		@Override
		public void onResourcesRegister(AliasResourceManager<String> reg) {
		}
		@Override
		public void dispose() {}
		@Override
		public EngineOptions onSetupEngine() {
			final EngineOptions opt = new EngineOptions(new String[]{},800,480);
			return opt;
		}

		@Override
		public void onLoadedResourcesCompleted() {
			freetypeGenerator = new FreeTypeFontGenerator(Gdx.files.internal("aquafont.ttf"));   
	        FreeTypeBitmapFontData fontData = freetypeGenerator.generateData(48, FreeTypeFontGenerator.DEFAULT_CHARS + "パズドラでは", false);
	        freetypeGenerator.dispose();    
	        freetypeBitmapFont = new BitmapFont(fontData, fontData.getTextureRegion(), false);  
	        
	        spriteBatch = new SpriteBatch();
	        
	        freetypeBitmapFont.setColor(Color.RED);
	      
			Engine.setMainScene(new Scene() {
				@Override
				public void render(float delta) {
					 Gdx.gl10.glClear(GL10.GL_COLOR_BUFFER_BIT);  

				       
				        
				        spriteBatch.begin();
				  
				        
				        freetypeBitmapFont.draw(spriteBatch, "MLGB:パズドラでは", 100, 100);
				        spriteBatch.end();
				}
				@Override
				public InputProcessor getInputProcessor() {
					return null;
				}
				@Override
				public void update(float delta) {	
				}
				@Override
				public void hide() {	
				}
				@Override
				public void show() {
				}
			});	
		}
	}
}
