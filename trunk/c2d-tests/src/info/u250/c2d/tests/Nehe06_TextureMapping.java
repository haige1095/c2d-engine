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


public class Nehe06_TextureMapping extends Engine {
	@Override
	protected EngineDrive onSetupEngineDrive() {
		return new EngineX();
	}
	@Override
	public void dispose () {
		super.dispose();
	}
	
	
	
	final static class SceneState {
		float cubeRotX = 0.0f;
		float cubeRotY = 0.0f;
		float cubeRotZ = 0.0f;
	}

	private static float[][] cubeVertexCoords = new float[][] {
		new float[] { // top
			 1, 1,-1,
			-1, 1,-1,
			-1, 1, 1,
			 1, 1, 1
		},
		new float[] { // bottom
			 1,-1, 1,
			-1,-1, 1,
			-1,-1,-1,
			 1,-1,-1
		},
		new float[] { // front
			 1, 1, 1,
			-1, 1, 1,
			-1,-1, 1,
			 1,-1, 1
		},
		new float[] { // back
			 1,-1,-1,
			-1,-1,-1,
			-1, 1,-1,
			 1, 1,-1
		},
		new float[] { // left
			-1, 1, 1,
			-1, 1,-1,
			-1,-1,-1,
			-1,-1, 1
		},
		new float[] { // right
			 1, 1,-1,
			 1, 1, 1,
			 1,-1, 1,
			 1,-1,-1
		},
	};

	private static float[][] cubeTextureCoords = new float[][] {
		new float[] { // top
			1, 0,
			1, 1,
			0, 1,
			0, 0
		},
		new float[] { // bottom
			0, 0,
			1, 0,
			1, 1,
			0, 1
		},
		new float[] { // front
			1, 1,
			0, 1,
			0, 0,
			1, 0
		},
		new float[] { // back
			0, 1,
			0, 0,
			1, 0,
			1, 1
		},
		new float[] { // left
			1, 1,
			0, 1,
			0, 0,
			1, 0
		},
		new float[] { // right
			0, 1,
			0, 0,
			1, 0,
			1, 1
		},
	};
	
	private static FloatBuffer[] cubeVertexBfr;
	private static FloatBuffer[] cubeTextureBfr;
	
//	private IntBuffer texturesBuffer;

	private static final SceneState sceneState;
	
	static
	{
		cubeVertexBfr = new FloatBuffer[6];
		cubeTextureBfr = new FloatBuffer[6];
		for (int i = 0; i < 6; i++)
		{
			cubeVertexBfr[i] = BufferUtils.newFloatBuffer(cubeVertexCoords[i].length);
			cubeVertexBfr[i].put(cubeVertexCoords[i]);
			cubeVertexBfr[i].rewind();
//			cubeVertexBfr[i] = FloatBuffer.wrap(cubeVertexCoords[i]);
			cubeTextureBfr[i] = BufferUtils.newFloatBuffer(cubeTextureCoords[i].length);
			cubeTextureBfr[i].put(cubeTextureCoords[i]);
			cubeTextureBfr[i].rewind();
//			cubeTextureBfr[i] = FloatBuffer.wrap(cubeTextureCoords[i]);
		}
		
		sceneState = new SceneState();
	}
	
	private class EngineX implements EngineDrive{
		@Override
		public void onResourcesRegister(AliasResourceManager<String> reg) {
			reg.texture("Tex", "data/nehe/nehe_texture_logo.bmp");
		}
		@Override
		public void dispose() {}
		@Override
		public EngineOptions onSetupEngine() {
			final EngineOptions opt = new EngineOptions(new String[]{"data/nehe/nehe_texture_logo.bmp"},800,480);
			return opt;
		}

		@Override
		public void onLoadedResourcesCompleted() {
			GL10 gl = Gdx.gl10;
			gl.glShadeModel(GL10.GL_SMOOTH);
			gl.glClearColor(0, 0, 0, 0);

			gl.glClearDepthf(1.0f);
			gl.glEnable(GL10.GL_DEPTH_TEST);
			gl.glDepthFunc(GL10.GL_LEQUAL);
			
			gl.glEnable(GL10.GL_CULL_FACE);
			gl.glCullFace(GL10.GL_BACK);
			
			gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_NICEST);
			
			// create texture
			gl.glEnable(GL10.GL_TEXTURE_2D);
			
			Engine.getDefaultCamera().fieldOfView = 45f;
			Engine.setMainScene(new Scene() {
				@Override
				public void render(float delta) {
					GL10 gl = Gdx.gl10;
					gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
					gl.glMatrixMode(GL10.GL_MODELVIEW);
					gl.glLoadIdentity();
					
					// draw cube
					
					gl.glTranslatef(0, 0, -6);
					gl.glRotatef(sceneState.cubeRotX, 1, 0, 0);
					gl.glRotatef(sceneState.cubeRotY, 0, 1, 0);
					gl.glRotatef(sceneState.cubeRotZ, 0, 0, 1);
					
//					gl.glBindTexture(GL10.GL_TEXTURE_2D, Engine.resource("Tex",Texture.class).getTextureObjectHandle());
					// create texture
					gl.glEnable(GL10.GL_TEXTURE_2D);
					Engine.resource("Tex",Texture.class).bind();  
					gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
					gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
					for (int i = 0; i < 6; i++) // draw each face
					{
						gl.glVertexPointer(3, GL10.GL_FLOAT, 0, cubeVertexBfr[i]);
						gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, cubeTextureBfr[i]);
						gl.glDrawArrays(GL10.GL_TRIANGLE_FAN, 0, 4);
					}
					gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
					gl.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
					
					// update rotations
					sceneState.cubeRotX += 1.2f;
					sceneState.cubeRotY += 0.8f;
					sceneState.cubeRotZ += 0.6f;
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
