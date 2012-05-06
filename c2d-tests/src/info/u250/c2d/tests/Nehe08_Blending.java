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
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.input.GestureDetector.GestureAdapter;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.utils.BufferUtils;


public class Nehe08_Blending extends Engine {
	@Override
	protected EngineDrive onSetupEngineDrive() {
		return new EngineX();
	}
	@Override
	public void dispose () {
		super.dispose();
	}
	final static class SceneState {
		
		static final float angleFactor = 0.35f;
		float dx, dy;
		float dxSpeed, dySpeed;
		Matrix4 baseMatrix = new Matrix4();
		
		

		
		void rotateModel(GL10 gl) {
			float r = (float)Math.sqrt(dx * dx + dy * dy);
			if (r != 0) {
				gl.glRotatef(r * angleFactor, dy / r, dx / r, 0);
			}
			gl.glMultMatrixf(baseMatrix.val,0);
		}

		public void dampenSpeed(float deltaMillis) {
			if (dxSpeed != 0.0f) {
				dxSpeed *= (1.0f - 0.001f * deltaMillis);
				if (Math.abs(dxSpeed) < 0.001f) dxSpeed = 0.0f;
			}
			
			if (dySpeed != 0.0f) {
				dySpeed *= (1.0f - 0.001f * deltaMillis);
				if (Math.abs(dySpeed) < 0.001f) dySpeed = 0.0f;
			}
		}
	}
	
	private final static float[][] cubeVertexCoords = new float[][] {
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

	private final static float[][] cubeNormalCoords = new float[][] {
		new float[] { // top
			 0, 1, 0,
			 0, 1, 0,
			 0, 1, 0,
			 0, 1, 0
		},
		new float[] { // bottom
			 0,-1, 0,
			 0,-1, 0,
			 0,-1, 0,
			 0,-1, 0
		},
		new float[] { // front
			 0, 0, 1,
			 0, 0, 1,
			 0, 0, 1,
			 0, 0, 1
		},
		new float[] { // back
			 0, 0,-1,
			 0, 0,-1,
			 0, 0,-1,
			 0, 0,-1
		},
		new float[] { // left
			-1, 0, 0,
			-1, 0, 0,
			-1, 0, 0,
			-1, 0, 0
		},
		new float[] { // right
			 1, 0, 0,
			 1, 0, 0,
			 1, 0, 0,
			 1, 0, 0
		},
	};
	
	private final static float[][] cubeTextureCoords = new float[][] {
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
	
	private final static float lightAmb[]= { 0.5f, 0.5f, 0.5f, 1.0f };
	private final static float lightDif[]= { 1.0f, 1.0f, 1.0f, 1.0f };
	private final static float lightPos[]= { 0.0f, 0.0f, 2.0f, 1.0f };
	
	private final static FloatBuffer[] cubeVertexBfr;
	private final static FloatBuffer[] cubeNormalBfr;
	private final static FloatBuffer[] cubeTextureBfr;

	private final static FloatBuffer lightAmbBfr;
	private final static FloatBuffer lightDifBfr;
	private final static FloatBuffer lightPosBfr;
	
	
	static final SceneState sceneState;
	private long lastMillis;
	
	static
	{
		cubeVertexBfr = new FloatBuffer[6];
		cubeNormalBfr = new FloatBuffer[6];
		cubeTextureBfr = new FloatBuffer[6];
		for (int i = 0; i < 6; i++)
		{
			cubeVertexBfr[i] = BufferUtils.newFloatBuffer(cubeVertexCoords[i].length);
			cubeVertexBfr[i].put(cubeVertexCoords[i]);
			cubeVertexBfr[i].rewind();
			cubeNormalBfr[i] = BufferUtils.newFloatBuffer(cubeNormalCoords[i].length);
			cubeNormalBfr[i].put(cubeNormalCoords[i]);
			cubeNormalBfr[i].rewind();
			cubeTextureBfr[i] = BufferUtils.newFloatBuffer(cubeTextureCoords[i].length);
			cubeTextureBfr[i].put(cubeTextureCoords[i]);
			cubeTextureBfr[i].rewind();
//			cubeVertexBfr[i] = FloatBuffer.wrap(cubeVertexCoords[i]);
//			cubeNormalBfr[i] = FloatBuffer.wrap(cubeNormalCoords[i]);
//			cubeTextureBfr[i] = FloatBuffer.wrap(cubeTextureCoords[i]);
		}
		lightAmbBfr = BufferUtils.newFloatBuffer(lightAmb.length);
		lightAmbBfr.put(lightAmb);
		lightAmbBfr.rewind();
		lightDifBfr = BufferUtils.newFloatBuffer(lightDif.length);
		lightDifBfr.put(lightDif);
		lightDifBfr.rewind();
		lightPosBfr = BufferUtils.newFloatBuffer(lightPos.length);
		lightPosBfr.put(lightPos);
		lightPosBfr.rewind();
//		lightAmbBfr = FloatBuffer.wrap(lightAmb);
//		lightDifBfr = FloatBuffer.wrap(lightDif);
//		lightPosBfr = FloatBuffer.wrap(lightPos);
		
		sceneState = new SceneState();
	}
	private class EngineX implements EngineDrive{
		@Override
		public void onResourcesRegister(AliasResourceManager<String> reg) {
			reg.texture("Tex", "data/nehe/nehe_texture_glass.bmp");
		}
		@Override
		public void dispose() {}
		@Override
		public EngineOptions onSetupEngine() {
			final EngineOptions opt = new EngineOptions(new String[]{"data/nehe/nehe_texture_glass.bmp"},800,480);
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
			
			
			
		
			
			
			Engine.getDefaultCamera().fieldOfView = 45;
			
			Engine.setMainScene(new Scene() {
				@Override
				public void render(float delta) {
					GL10 gl = Gdx.gl10;
					gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
					
					
					gl.glMatrixMode(GL10.GL_MODELVIEW);
					gl.glLoadIdentity();
					
					// blending
					{
						gl.glColor4f(1.0f, 1.0f, 1.0f, 0.5f);
						gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE);
						gl.glEnable(GL10.GL_BLEND);
						gl.glDisable(GL10.GL_CULL_FACE);
					}
					
					// update lighting
					gl.glEnable(GL10.GL_LIGHTING);
					// lighting
					gl.glEnable(GL10.GL_LIGHT0);
					gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_AMBIENT, lightAmbBfr);
					gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_DIFFUSE, lightDifBfr);
					gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_POSITION, lightPosBfr);
					// draw cube
					
					gl.glTranslatef(0, 0, -6);
					
					sceneState.rotateModel(gl);
					
//					gl.glBindTexture(GL10.GL_TEXTURE_2D, texturesBuffer.get(sceneState.filter));
					gl.glEnable(GL10.GL_TEXTURE_2D);
					Engine.resource("Tex",Texture.class).bind();
					gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
					gl.glEnableClientState(GL10.GL_NORMAL_ARRAY);
					gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
					for (int i = 0; i < 6; i++) // draw each face
					{
						gl.glVertexPointer(3, GL10.GL_FLOAT, 0, cubeVertexBfr[i]);
						gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, cubeTextureBfr[i]);
						gl.glNormalPointer(GL10.GL_FLOAT, 0, cubeNormalBfr[i]);
						gl.glDrawArrays(GL10.GL_TRIANGLE_FAN, 0, 4);
					}
					gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
					gl.glDisableClientState(GL10.GL_NORMAL_ARRAY);
					gl.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
					
					// get current millis
					long currentMillis = System.currentTimeMillis();
					
					// update rotations
					if (lastMillis != 0) {
						sceneState.dx += sceneState.dxSpeed * delta;
						sceneState.dy += sceneState.dySpeed * delta;
						sceneState.dampenSpeed(delta);
					}
					
					// update millis
					lastMillis = currentMillis;
					gl.glDisable(GL10.GL_CULL_FACE);
					gl.glDisable(GL10.GL_LIGHTING);
					
					Engine.getSpriteBatch().begin();
					Engine.getDefaultFont().drawMultiLine(Engine.getSpriteBatch(), "GL_ONE\nGL_BLEND\n!GL_CULL_FACE",
							250, 250);
					Engine.getSpriteBatch().end();
					
				}
			
				@Override
				public InputProcessor getInputProcessor() {
					return new GestureDetector(new GestureAdapter(){
						@Override
						public boolean fling(float velocityX, float velocityY) {
							sceneState.dxSpeed = velocityX/10 ;
							sceneState.dySpeed = velocityY/10 ;
							return super.fling(velocityX, velocityY);
						};
					});
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
