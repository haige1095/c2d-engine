package info.u250.c2d.tests;

import info.u250.c2d.engine.Engine;
import info.u250.c2d.engine.EngineDrive;
import info.u250.c2d.engine.Scene;
import info.u250.c2d.engine.resources.AliasResourceManager;

import java.nio.FloatBuffer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.BufferUtils;


public class Nehe09_BitmapAnimation extends Engine {
	@Override
	protected EngineDrive onSetupEngineDrive() {
		return new EngineX();
	}
	@Override
	public void dispose () {
		super.dispose();
	}
	
	private static float[] quadVertexCoords = new float[] {
		-1, 1, 0,
		-1,-1, 0,
		 1, 1, 0,
		 1,-1, 0
	};

	private static float[] quadTextureCoords = new float[] {
		0, 1,
		0, 0,
		1, 1,
		1, 0
	};
	
	private static FloatBuffer quadVertexBuffer;
	private static FloatBuffer quadTextureBuffer;
		
	static final SceneState sceneState;
	
	final static class Star {

		float r, g, b;
		float dist;
		float angle;
		
	}
	
	final static class SceneState {
		
		final int nrStars = 30;
		Star[] stars;
		
		float zoom = -20.0f;
		float tilt = 90.0f;
		float spin;
		boolean twinkle = false;
		
		public SceneState() {
			// setup stars
			stars = new Star[nrStars];
			for (int i = 0; i < nrStars; i++) {
				stars[i] = new Star();
				stars[i].angle = 0;
				stars[i].dist = (((float)i)/nrStars)*5.0f;
				stars[i].r = (float)Math.random();
				stars[i].g = (float)Math.random();
				stars[i].b = (float)Math.random();
			}
		}
		
		public void toggleTwinkle() {
			twinkle = !twinkle;
		}

		public void updateNextFrame() {
			for (int i = 0; i < nrStars; i++) {
				spin += 0.01f;									// Used To Spin The Stars
				stars[i].angle += ((float)i)/nrStars;			// Changes The Angle Of A Star
				stars[i].dist -= 0.01f;							// Changes The Distance Of A Star

				if (stars[i].dist < 0.0f)						// Is The Star In The Middle Yet
				{
					stars[i].dist += 5.0f;						// Move The Star 5 Units From The Center
					stars[i].r = (float)Math.random();			// Give It A New Red Value
					stars[i].g = (float)Math.random();			// Give It A New Green Value
					stars[i].b = (float)Math.random();			// Give It A New Blue Value
				}
			}
		}
	}


	static
	{
		quadVertexBuffer = BufferUtils.newFloatBuffer(quadVertexCoords.length);
		quadVertexBuffer.put(quadVertexCoords);
		quadVertexBuffer.rewind();
//		quadVertexBuffer = FloatBuffer.wrap(quadVertexCoords);
		quadTextureBuffer = BufferUtils.newFloatBuffer(quadTextureCoords.length);
		quadTextureBuffer.put(quadTextureCoords);
		quadTextureBuffer.rewind();
//		quadTextureBuffer = FloatBuffer.wrap(quadTextureCoords);
		
		sceneState = new SceneState();
	}
	private class EngineX implements EngineDrive{
		@Override
		public void onResourcesRegister(AliasResourceManager<String> reg) {
			reg.texture("Tex", "data/nehe/nehe_texture_star.bmp");
		}
		@Override
		public void dispose() {}
		@Override
		public EngineOptions onSetupEngine() {
			final EngineOptions opt = new EngineOptions(new String[]{"data/nehe/nehe_texture_star.bmp"},800,480);
			return opt;
		}

		
		
		@Override
		public void onLoadedResourcesCompleted() {
			GL10 gl = Gdx.gl10;
			gl.glShadeModel(GL10.GL_SMOOTH);
			gl.glClearColor(0, 0, 0, 0);
			gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_NICEST);
			gl.glEnable(GL10.GL_BLEND);
			gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE);
			
			Engine.getDefaultCamera().fieldOfView = 45;
			Engine.setMainScene(new Scene() {
				
				@Override
				public void render(float delta) {
					GL10 gl = Gdx.gl10;
					gl.glEnable(GL10.GL_BLEND);
					gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE);
					
					gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
//					gl.glBindTexture(GL10.GL_TEXTURE_2D, texturesBuffer.get(0));
					gl.glEnable(GL10.GL_TEXTURE_2D);
					Engine.resource("Tex",Texture.class).bind();
					gl.glMatrixMode(GL10.GL_MODELVIEW);

					for (int i = 0; i < sceneState.nrStars; i++)
					{
						gl.glLoadIdentity();
						gl.glTranslatef(0, 0, sceneState.zoom);					// Zoom Into The Screen (Using The Value In 'zoom')
						gl.glRotatef(sceneState.tilt, 1, 0, 0);					// Tilt The View (Using The Value In 'tilt')
						
						gl.glRotatef(sceneState.stars[i].angle, 0, 1, 0);		// Rotate To The Current Stars Angle
						gl.glTranslatef(sceneState.stars[i].dist, 0, 0);		// Move Forward On The X Plane
						
						gl.glRotatef(-sceneState.stars[i].angle, 0, 1, 0);		// Cancel The Current Stars Angle
						gl.glRotatef(-sceneState.tilt, 1, 0, 0);				// Cancel The Screen Tilt

						gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
						gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
						gl.glVertexPointer(3, GL10.GL_FLOAT, 0, quadVertexBuffer);
						gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, quadTextureBuffer);

						if (sceneState.twinkle)									// Twinkling Stars Enabled
						{
							Star twinkleStar = sceneState.stars[(sceneState.nrStars - i) - 1];
							gl.glColor4f(twinkleStar.r, twinkleStar.g, twinkleStar.b, 1.0f);
							gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, 4);
						}
						gl.glRotatef(sceneState.spin, 0, 0, 1);					// Rotate The Star On The Z Axis
						gl.glColor4f(sceneState.stars[i].r, sceneState.stars[i].g, sceneState.stars[i].b, 1.0f);
						gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, 4);
						
						gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
						gl.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
					}
					
					sceneState.updateNextFrame();
					
					Engine.getSpriteBatch().begin();
					Engine.getDefaultFont().drawMultiLine(Engine.getSpriteBatch(), "Like Sprite\nGL_ONE\nGL_BLEND\n",
							250, 250);
					Engine.getSpriteBatch().end();
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
