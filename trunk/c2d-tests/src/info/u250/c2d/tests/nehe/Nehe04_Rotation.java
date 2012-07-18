package info.u250.c2d.tests.nehe;

import info.u250.c2d.engine.Engine;
import info.u250.c2d.engine.EngineDrive;
import info.u250.c2d.engine.Scene;
import info.u250.c2d.engine.resources.AliasResourceManager;

import java.nio.FloatBuffer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.utils.BufferUtils;


public class Nehe04_Rotation extends Engine {
	@Override
	protected EngineDrive onSetupEngineDrive() {
		return new EngineX();
	}
	@Override
	public void dispose () {
		super.dispose();
	}
	
	private static float[] triangleCoords = new float[] {
		 0,  1, 0,
		-1, -1, 0,
		 1, -1, 0
	};

	private static float[] triangleColors = new float[] {
		1, 0, 0, 1,
		0, 1, 0, 1,
		0, 0, 1, 1
	};
	
	private static float[] quadCoords = new float[] {
		-1, 1, 0,
		-1,-1, 0,
		 1, 1, 0,
		 1,-1, 0
	};

	private static FloatBuffer triangleVertexBfr;
	private static FloatBuffer triangleColorBfr;
	private static FloatBuffer quadVertexBfr;
	
	private static final SceneState sceneState;
	
	static
	{
		triangleVertexBfr = BufferUtils.newFloatBuffer(triangleCoords.length);
		triangleVertexBfr.put(triangleCoords);
		triangleVertexBfr.rewind();
		triangleColorBfr = BufferUtils.newFloatBuffer(triangleColors.length);
		triangleColorBfr.put(triangleColors);
		triangleColorBfr.rewind();
		quadVertexBfr = BufferUtils.newFloatBuffer(quadCoords.length);
		quadVertexBfr.put(quadCoords);
		quadVertexBfr.rewind();
		
		sceneState = new SceneState();
	}
	
	
	final static class SceneState {
		float triangleRot = 0.0f;
		float quadRot = 0.0f;
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
			
			gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_NICEST);
			
			Engine.getDefaultCamera().fieldOfView = 45f;
			
			Engine.setMainScene(new Scene() {
				@Override
				public void render(float delta) {
					GL10 gl = Gdx.gl10;
					
					gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
					
					gl.glMatrixMode(GL10.GL_MODELVIEW);
					gl.glLoadIdentity();
					
					// draw triangle
					gl.glTranslatef(-1.5f, 0, -6);
					gl.glRotatef(sceneState.triangleRot, 0, 1, 0);
					gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
					gl.glEnableClientState(GL10.GL_COLOR_ARRAY);
					gl.glVertexPointer(3, GL10.GL_FLOAT, 0, triangleVertexBfr);
					gl.glColorPointer(4, GL10.GL_FLOAT, 0, triangleColorBfr);
					gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, 3);
					gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
					gl.glDisableClientState(GL10.GL_COLOR_ARRAY);
					
					// draw quad
					gl.glLoadIdentity();
					gl.glTranslatef(1.5f, 0, -6);
					gl.glRotatef(sceneState.quadRot, 1, 0, 0);
					gl.glColor4f(0.5f, 0.5f, 1.0f, 1.0f);
					gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
					gl.glVertexPointer(3, GL10.GL_FLOAT, 0, quadVertexBfr);
					gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, 4);
					gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
					
					// update rotations
					sceneState.triangleRot += 0.8f;
					sceneState.quadRot -= 0.5f;
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
