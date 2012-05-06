package info.u250.c2d.tests;

import info.u250.c2d.engine.Engine;
import info.u250.c2d.engine.EngineDrive;
import info.u250.c2d.engine.Scene;
import info.u250.c2d.engine.resources.AliasResourceManager;

import java.nio.FloatBuffer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.utils.BufferUtils;


public class Nehe05_3dShapes extends Engine {
	@Override
	protected EngineDrive onSetupEngineDrive() {
		return new EngineX();
	}
	@Override
	public void dispose () {
		super.dispose();
	}
	
	final static class SceneState {
		float pyramidRot = 0.0f;
		float cubeRot = 0.0f;
	}
	
	private static float[] pyramidCoords = new float[] {
		 0,  1,  0,
		 1, -1,  1,
		 1, -1, -1,
		-1, -1, -1,
		-1, -1,  1,
		 1, -1,  1
	};

	private static float[] pyramidColors = new float[] {
		1, 0, 0, 1,
		0, 1, 0, 1,
		0, 0, 1, 1,
		0, 1, 0, 1,
		0, 0, 1, 1,
		0, 1, 0, 1
	};
	
	private static float[][] cubeCoords = new float[][] {
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

	private static float[] cubeColors = new float[] {
		0,1,0,1,
		1,0.5f,0,1,
		1,0,0,1,
		1,1,0,1,
		0,0,1,1,
		1,0,1,1		
	};
	
	private static FloatBuffer pyramidVertexBfr;
	private static FloatBuffer pyramidColorBfr;
	private static FloatBuffer[] cubeVertexBfr;
	
	private static final SceneState sceneState;
	
	static
	{
		pyramidVertexBfr = BufferUtils.newFloatBuffer(pyramidCoords.length);
		pyramidVertexBfr.put(pyramidCoords);
		pyramidVertexBfr.rewind();
		pyramidColorBfr = BufferUtils.newFloatBuffer(pyramidColors.length);
		pyramidColorBfr.put(pyramidColors);
		pyramidColorBfr.rewind();
		
		cubeVertexBfr = new FloatBuffer[6];
		for (int i = 0; i < 6; i++)
		{
			cubeVertexBfr[i] = BufferUtils.newFloatBuffer(cubeCoords[i].length);
			cubeVertexBfr[i] .put(cubeCoords[i]);
			cubeVertexBfr[i].rewind();
		}
		
		sceneState = new SceneState();
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
			GL10 gl = Gdx.gl10;
			
			gl.glShadeModel(GL10.GL_SMOOTH);
			gl.glClearColor(0, 0, 0, 0);

			gl.glClearDepthf(1.0f);
			gl.glEnable(GL10.GL_DEPTH_TEST);
			gl.glDepthFunc(GL10.GL_LEQUAL);
			
			gl.glEnable(GL10.GL_CULL_FACE);
			gl.glCullFace(GL10.GL_BACK);
			
			gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_NICEST);
			
			
			Engine.getDefaultCamera().fieldOfView = 45f;
			
			Engine.setMainScene(new Scene() {
				@Override
				public void render(float delta) {
					com.badlogic.gdx.graphics.GL10 gl = Gdx.gl10;
					gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
					
					gl.glMatrixMode(GL10.GL_MODELVIEW);
					gl.glLoadIdentity();
					
					// draw pyramid
					gl.glTranslatef(-1.5f, 0, -6);
					gl.glRotatef(sceneState.pyramidRot, 0, 1, 0);
					gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
					gl.glEnableClientState(GL10.GL_COLOR_ARRAY);
					gl.glVertexPointer(3, GL10.GL_FLOAT, 0, pyramidVertexBfr);
					gl.glColorPointer(4, GL10.GL_FLOAT, 0, pyramidColorBfr);
					gl.glDrawArrays(GL10.GL_TRIANGLE_FAN, 0, 6);
					gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
					gl.glDisableClientState(GL10.GL_COLOR_ARRAY);
					
					// draw cube
					gl.glLoadIdentity();
					gl.glTranslatef(1.5f, 0, -6);
					gl.glRotatef(sceneState.cubeRot, 1, 1, 1);
					for (int i = 0; i < 6; i++)
					{
						gl.glColor4f(cubeColors[4*i+0], cubeColors[4*i+1], cubeColors[4*i+2], cubeColors[4*i+3]);
						gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
						gl.glVertexPointer(3, GL10.GL_FLOAT, 0, cubeVertexBfr[i]);
						gl.glDrawArrays(GL10.GL_TRIANGLE_FAN, 0, 4);
						gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
					}
					
					// update rotations
					sceneState.pyramidRot += 0.8f;
					sceneState.cubeRot -= 0.5f;
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
