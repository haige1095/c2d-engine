package info.u250.c2d.tests.nehe;

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


public class Nehe18_Quadrics extends Engine {
	@Override
	protected EngineDrive onSetupEngineDrive() {
		return new EngineX();
	}
	@Override
	public void dispose () {
		super.dispose();
	}

	
	private final static float lightAmb[]= { 0.5f, 0.5f, 0.5f, 1.0f };
	private final static float lightDif[]= { 1.0f, 1.0f, 1.0f, 1.0f };
	private final static float lightPos[]= { 0.0f, 0.0f, 2.0f, 1.0f };
	
	private final static FloatBuffer lightAmbBfr;
	private final static FloatBuffer lightDifBfr;
	private final static FloatBuffer lightPosBfr;
	
	
	private static GlCube cube;
	private static GlCylinder cylinder;
	private static GlDisk disk;
	private static GlSphere sphere;
	private static GlCylinder cone;
	private static GlDisk partialDisk;

	static final SceneState sceneState;
	private long lastMillis;
	
	static {
		lightAmbBfr = BufferUtils.newFloatBuffer(lightAmb.length);
		lightAmbBfr.put(lightAmb);
		lightAmbBfr.rewind();
		lightDifBfr = BufferUtils.newFloatBuffer(lightDif.length);
		lightDifBfr.put(lightDif);
		lightDifBfr.rewind();
		lightPosBfr = BufferUtils.newFloatBuffer(lightPos.length);
		lightPosBfr.put(lightPos);
		lightPosBfr.rewind();

		cube = new GlCube(1.0f, true, true);
		cylinder = new GlCylinder(1.0f, 1.0f, 3.0f, 32, 4, true, true);
		disk = new GlDisk(0.5f, 1.5f, 32, 4, true, true);
		sphere = new GlSphere(1.3f, 24, 12, true, true);
		cone = new GlCylinder(1.0f, 0.0f, 3.0f, 32, 4, true, true);
		partialDisk = new GlDisk(0.5f, 1.5f, 32, 4, (float) (Math.PI / 4), (float) (7 * Math.PI / 4), true, true);

		sceneState = new SceneState();
	}
	private class EngineX implements EngineDrive{
		@Override
		public void onResourcesRegister(AliasResourceManager<String> reg) {
			reg.texture("Tex", "data/nehe/nehe_texture_wall.bmp");
		}
		@Override
		public void dispose() {}
		@Override
		public EngineOptions onSetupEngine() {
			final EngineOptions opt = new EngineOptions(new String[]{"data/nehe/nehe_texture_wall.bmp"},800,480);
			return opt;
		}

		int i = 0;
		
		@Override
		public void onLoadedResourcesCompleted() {
			GL10 gl = Gdx.gl10;
			gl.glShadeModel(GL10.GL_SMOOTH);
			gl.glClearColor(0, 0, 0, 0);

			gl.glClearDepthf(1.0f);
			gl.glEnable(GL10.GL_DEPTH_TEST);
			gl.glDepthFunc(GL10.GL_LEQUAL);
			
			gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_NICEST);
			
			gl.glCullFace(GL10.GL_BACK);
			
			// lighting
			gl.glEnable(GL10.GL_LIGHT0);
			gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_AMBIENT, lightAmbBfr);
			gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_DIFFUSE, lightDifBfr);
			gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_POSITION, lightPosBfr);

			
			Engine.getDefaultCamera().fieldOfView = 45;
			
			
			Engine.setMainScene(new Scene() {
				@Override
				public void render(float delta) {
					GL10 gl = Gdx.gl10;
					
					gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
					gl.glMatrixMode(GL10.GL_MODELVIEW);
					gl.glLoadIdentity();
					
					
					// update lighting
					gl.glEnable(GL10.GL_LIGHTING);
					// lighting
					gl.glEnable(GL10.GL_LIGHT0);
					gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_AMBIENT, lightAmbBfr);
					gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_DIFFUSE, lightDifBfr);
					gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_POSITION, lightPosBfr);
					
					// position object
					gl.glTranslatef(0, 0, -6);
					
					sceneState.rotateModel(gl);

					// draw object
					gl.glColor4f(0.5f, 0.5f, 0.5f, 1.0f);
//					gl.glBindTexture(GL10.GL_TEXTURE_2D, texturesBuffer.get(sceneState.filter));
					gl.glEnable(GL10.GL_TEXTURE_2D);
					Engine.resource("Tex",Texture.class).bind();
					switch (i) {
					case 0:
						gl.glEnable(GL10.GL_CULL_FACE);
						gl.glLightModelf(GL10.GL_LIGHT_MODEL_TWO_SIDE, GL10.GL_FALSE);
						cube.draw(gl);
						break;
					case 1:
						gl.glDisable(GL10.GL_CULL_FACE);
						gl.glLightModelf(GL10.GL_LIGHT_MODEL_TWO_SIDE, GL10.GL_TRUE);
						cylinder.draw(gl);
						break;
					case 2:
						gl.glDisable(GL10.GL_CULL_FACE);
						gl.glLightModelf(GL10.GL_LIGHT_MODEL_TWO_SIDE, GL10.GL_TRUE);
						disk.draw(gl);
						break;
					case 3:
						gl.glEnable(GL10.GL_CULL_FACE);
						gl.glLightModelf(GL10.GL_LIGHT_MODEL_TWO_SIDE, GL10.GL_FALSE);
						sphere.draw(gl);
						break;
					case 4:
						gl.glDisable(GL10.GL_CULL_FACE);
						gl.glLightModelf(GL10.GL_LIGHT_MODEL_TWO_SIDE, GL10.GL_TRUE);
						cone.draw(gl);
						break;
					case 5:
						gl.glDisable(GL10.GL_CULL_FACE);
						gl.glLightModelf(GL10.GL_LIGHT_MODEL_TWO_SIDE, GL10.GL_TRUE);
						partialDisk.draw(gl);
						break;
					}
					
					
					
					// get current millis
					long currentMillis = System.currentTimeMillis();
					
					// update rotations
					if (lastMillis != 0) {
//						long delta = currentMillis - lastMillis;
						sceneState.dx += sceneState.dxSpeed * delta;
						sceneState.dy += sceneState.dySpeed * delta;
						sceneState.dampenSpeed(delta);
					}
					
					// update millis
					lastMillis = currentMillis;
					
					
					gl.glDisable(GL10.GL_LIGHTING);
					gl.glDisable(GL10.GL_CULL_FACE);
					Engine.getSpriteBatch().begin();
					Engine.getDefaultFont().drawMultiLine(Engine.getSpriteBatch(), "Fling to rotate\nTap to switch",
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
						@Override
						public boolean tap(float x, float y, int count) {
							i++;
							if(i==6){
								i = 0;
							}
							return super.tap(x, y, count);
						}
						
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
	
	
	static abstract class GlObject {
		public abstract void draw(GL10 gl);
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
	//cube
	static class GlCube extends GlObject {
		
		private final static float[][] cubeVertexCoordsTemplate = new float[][] {
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

		private final static FloatBuffer[] cubeNormalBfr;
		private final static FloatBuffer[] cubeTextureBfr;
		
		static {
			cubeNormalBfr = new FloatBuffer[6];
			cubeTextureBfr = new FloatBuffer[6];
			for (int i = 0; i < 6; i++)
			{
				cubeNormalBfr[i] = BufferUtils.newFloatBuffer(cubeNormalCoords[i].length);
				cubeNormalBfr[i].put(cubeNormalCoords[i]);
				cubeNormalBfr[i].rewind();
				cubeTextureBfr[i] = BufferUtils.newFloatBuffer(cubeTextureCoords[i].length);
				cubeTextureBfr[i].put(cubeTextureCoords[i]);
				cubeTextureBfr[i].rewind();
//				cubeNormalBfr[i] = FloatBuffer.wrap(cubeNormalCoords[i]);
//				cubeTextureBfr[i] = FloatBuffer.wrap(cubeTextureCoords[i]);
			}
		}
		
		private float size;
		
		private boolean useNormals;
		private boolean useTexCoords;
		
		private FloatBuffer[] cubeVertexBfr;
		
		public GlCube(float size, boolean useNormals, boolean useTexCoords) {
			this.size = size;
			this.useNormals = useNormals;
			this.useTexCoords = useTexCoords;
			generateData();
		}
		
		private void generateData() {
			cubeVertexBfr = new FloatBuffer[cubeVertexCoordsTemplate.length];
			
			float[] vertices;
			for (int i = 0; i < cubeVertexCoordsTemplate.length; i++)
			{
				vertices = new float[cubeVertexCoordsTemplate[i].length];
				for (int j = 0; j < cubeVertexCoordsTemplate[i].length; j++)
				{
					vertices[j] = cubeVertexCoordsTemplate[i][j] * size;
				}
				cubeVertexBfr[i] = BufferUtils.newFloatBuffer(vertices.length);
				cubeVertexBfr[i] .put(vertices);
				cubeVertexBfr[i] .rewind();
//				cubeVertexBfr[i] = FloatBuffer.wrap(vertices);
			}
		}

		@Override
		public void draw(GL10 gl) {
			gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
			if (useNormals) {
				gl.glEnableClientState(GL10.GL_NORMAL_ARRAY);
			}
			if (useTexCoords) {
				gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
			}
			
			for (int i = 0; i < 6; i++) { // draw each face
				gl.glVertexPointer(3, GL10.GL_FLOAT, 0, cubeVertexBfr[i]);
				if (useNormals) {
					gl.glNormalPointer(GL10.GL_FLOAT, 0, cubeNormalBfr[i]);
				}
				if (useTexCoords) {
					gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, cubeTextureBfr[i]);
				}
				gl.glDrawArrays(GL10.GL_TRIANGLE_FAN, 0, 4);
			}
			
			gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
			if (useNormals) {
				gl.glDisableClientState(GL10.GL_NORMAL_ARRAY);
			}
			if (useTexCoords) {
				gl.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
			}
		}
	}
	//cylinder
	static class GlCylinder extends GlObject {
		
		float base;
		float top;
		float height;
		int slices;
		int stacks;
		
		private boolean normals;
		private boolean texCoords;
		
		private FloatBuffer[] slicesBuffers;
		private FloatBuffer[] normalsBuffers;
		private FloatBuffer[] texCoordsBuffers;
		
		public GlCylinder(float base, float top, float height, int slices, int stacks, boolean genNormals, boolean genTexCoords) {
			this.base = base;
			this.top = top;
			this.height = height;
			this.slices = slices;
			this.stacks = stacks;
			this.normals = genNormals;
			this.texCoords = genTexCoords;
			generateData();
		}
		
		private void generateData() {
			
			slicesBuffers = new FloatBuffer[slices];
			if (normals) {
				normalsBuffers = new FloatBuffer[slices];
			}
			if (texCoords) {
				texCoordsBuffers = new FloatBuffer[slices];
			}
			
			for (int i = 0; i < slices; i++) {
				
				float[] vertexCoords = new float[3 * 2 * (stacks + 1)];
				float[] normalCoords = new float[3 * 2 * (stacks + 1)];
				float[] textureCoords = new float[2 * 2 * (stacks + 1)];

				double alpha0 = (i + 0) * (2 * Math.PI) / slices;
				double alpha1 = (i + 1) * (2 * Math.PI) / slices;

				float cosAlpha0 = (float) Math.cos(alpha0);
				float sinAlpha0 = (float) Math.sin(alpha0);
				float cosAlpha1 = (float) Math.cos(alpha1);
				float sinAlpha1 = (float) Math.sin(alpha1);

				for (int j = 0; j <= stacks; j++) {

					float z = height * (0.5f - ((float)j) / stacks);
					float r = top + (base - top) * j / stacks;
					
					setXYZ(vertexCoords, 3 * 2 * j,
							r * cosAlpha1, r * sinAlpha1, z);

					setXYZ(vertexCoords, 3 * 2 * j + 3,
							r * cosAlpha0, r * sinAlpha0, z);
					
					if (normals) {
						setXYZn(normalCoords, 3 * 2 * j,
								height * cosAlpha1,
								height * sinAlpha1,
								base - top);
						setXYZn(normalCoords, 3 * 2 * j + 3,
								height * cosAlpha0,
								height * sinAlpha0,
								base - top);
					}

					if (texCoords) {
						textureCoords[2 * 2 * j + 0] = ((float) (i + 1)) / slices;
						textureCoords[2 * 2 * j + 1] = ((float) (j + 0)) / stacks;
						
						textureCoords[2 * 2 * j + 2] = ((float) (i + 0)) / slices;
						textureCoords[2 * 2 * j + 3] = ((float) (j + 0)) / stacks;
					}
				}
				
				slicesBuffers[i] = BufferUtils.newFloatBuffer(vertexCoords.length);
				slicesBuffers[i].put(vertexCoords);slicesBuffers[i].rewind();
				if (normals) {
					normalsBuffers[i] = BufferUtils.newFloatBuffer(normalCoords.length);
					normalsBuffers[i].put(normalCoords);
					normalsBuffers[i].rewind();
				}
				
				if (texCoords) {
					texCoordsBuffers[i] = BufferUtils.newFloatBuffer(textureCoords.length);
					texCoordsBuffers[i].put(textureCoords);
					texCoordsBuffers[i].rewind();
				}
			}
		}

		@Override
		public void draw(GL10 gl) {
			gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
			if (normals) {
				gl.glEnableClientState(GL10.GL_NORMAL_ARRAY);
			}
			if (texCoords) {
				gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
			}
			
			for (int i = 0; i < slices; i++) // draw each slice
			{
				gl.glVertexPointer(3, GL10.GL_FLOAT, 0, slicesBuffers[i]);
				if (normals) {
					gl.glNormalPointer(GL10.GL_FLOAT, 0, normalsBuffers[i]);
				}
				if (texCoords) {
					gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, texCoordsBuffers[i]);
				}
				gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, 2 * (stacks + 1));
			}
			
			gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
			if (normals) {
				gl.glDisableClientState(GL10.GL_NORMAL_ARRAY);
			}
			if (texCoords) {
				gl.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
			}
		}
	}
	//disk
	static class GlDisk extends GlObject {
		
		private float innerRadius;
		private float outerRadius;
		private  int slices;
		private int loops;
		private float startAngle;
		private float stopAngle;
		
		private boolean normals;
		private boolean texCoords;
		
		private FloatBuffer[] loopsBuffers;
		private FloatBuffer[] normalsBuffers;
		private FloatBuffer[] texCoordsBuffers;
		
		public GlDisk(float innerRadius, float outerRadius, int slices, int loops, boolean genNormals, boolean genTexCoords) {
			this(innerRadius, outerRadius, slices, loops, 0.0f, (float)(2 * Math.PI), genNormals, genTexCoords);
		}
		
		public GlDisk(float innerRadius, float outerRadius, int slices, int loops, float startAngle, float stopAngle, boolean genNormals, boolean genTexCoords) {
			this.innerRadius = innerRadius;
			this.outerRadius = outerRadius;
			this.slices = slices;
			this.loops = loops;
			this.startAngle = startAngle;
			this.stopAngle = stopAngle;
			this.normals = genNormals;
			this.texCoords = genTexCoords;
			generateData();
		}
		
		private void generateData() {
			
			loopsBuffers = new FloatBuffer[loops];
			if (normals) {
				normalsBuffers = new FloatBuffer[loops];
			}
			if (texCoords) {
				texCoordsBuffers = new FloatBuffer[loops];
			}
			
			for (int i = 0; i < loops; i++) {
				
				float[] vertexCoords = new float[3 * 2 * (slices + 1)];
				float[] normalCoords = new float[3 * 2 * (slices + 1)];
				float[] textureCoords = new float[2 * 2 * (slices + 1)];
				
				float r0 = innerRadius + (outerRadius - innerRadius) * i / loops;
				float r1 = innerRadius + (outerRadius - innerRadius) * (i + 1) / loops;
				
				for (int j = 0; j <= slices; j++) {
					
					double alpha = startAngle + (stopAngle - startAngle) * j / slices;
					
					float sinAlpha = (float) Math.sin(alpha);
					float cosAlpha = (float) Math.cos(alpha);
					
					setXYZ(vertexCoords, 6 * j,
							cosAlpha * r0, sinAlpha * r0, 0);
					setXYZ(vertexCoords, 6 * j + 3,
							cosAlpha * r1, sinAlpha * r1, 0);
					if (normals) {
						setXYZ(normalCoords, 6 * j,
								0, 0, 1);
						setXYZ(normalCoords, 6 * j + 3,
								0, 0, 1);
					}
					if (texCoords) {
						setXY(textureCoords, 4 * j,
								((float)j) / slices, ((float)i) / loops);
						setXY(textureCoords, 4 * j + 2,
								((float)j) / slices, ((float)i + 1) / loops);
					}
				}
				
				loopsBuffers[i] = BufferUtils.newFloatBuffer(vertexCoords.length);
				loopsBuffers[i].put(vertexCoords);
				loopsBuffers[i].rewind();
				
				if (normals) {
					normalsBuffers[i] = BufferUtils.newFloatBuffer(normalCoords.length);
					normalsBuffers[i].put(normalCoords);
					normalsBuffers[i].rewind();
				}
				if (texCoords) {
					texCoordsBuffers[i] = BufferUtils.newFloatBuffer(textureCoords.length);
					texCoordsBuffers[i].put(textureCoords);
					texCoordsBuffers[i].rewind();
				}
			}
		}
		
		
		@Override
		public void draw(GL10 gl) {
			gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
			if (normals) {
				gl.glEnableClientState(GL10.GL_NORMAL_ARRAY);
			}
			if (texCoords) {
				gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
			}
			
			for (int i = 0; i < loops; i++) { // draw each loop
				gl.glVertexPointer(3, GL10.GL_FLOAT, 0, loopsBuffers[i]);
				if (normals) {
					gl.glNormalPointer(GL10.GL_FLOAT, 0, normalsBuffers[i]);
				}
				if (texCoords) {
					gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, texCoordsBuffers[i]);
				}
				gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, 2 * (slices + 1));
			}
			
			gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
			if (normals) {
				gl.glDisableClientState(GL10.GL_NORMAL_ARRAY);
			}
			if (texCoords) {
				gl.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
			}
		}
	}
	//sphere
	static class GlSphere extends GlObject {
		
		private float radius;
		private int slices;
		private int stacks;
		
		private boolean useNormals;
		private boolean useTexCoords;
		
		private FloatBuffer[] slicesBuffers;
		private FloatBuffer[] normalsBuffers;
		private FloatBuffer[] texCoordsBuffers;
		
		public GlSphere(float radius, int slices, int stacks, boolean useNormals, boolean useTexCoords) {
			this.radius = radius;
			this.stacks = stacks;
			this.slices = slices;
			this.useNormals = useNormals;
			this.useTexCoords = useTexCoords;
			generateData();
		}
		
		private void generateData() {
			
			slicesBuffers = new FloatBuffer[slices];
			if (useNormals) {
				normalsBuffers = new FloatBuffer[slices];
			}
			if (useTexCoords) {
				texCoordsBuffers = new FloatBuffer[slices];
			}
			
			for (int i = 0; i < slices; i++) {
				
				float[] vertexCoords = new float[6 * (stacks + 1)];
				float[] normalCoords = new float[6 * (stacks + 1)];
				float[] textureCoords = new float[4 * (stacks + 1)];
				
				double alpha0 = i * (2 * Math.PI) / slices;
				double alpha1 = (i + 1) * (2 * Math.PI) / slices;
				
				float cosAlpha0 = (float) Math.cos(alpha0);
				float sinAlpha0 = (float) Math.sin(alpha0);
				float cosAlpha1 = (float) Math.cos(alpha1);
				float sinAlpha1 = (float) Math.sin(alpha1);

				for (int j = 0; j <= stacks; j++) {
					
					double beta = j * Math.PI / stacks - Math.PI / 2;
					
					float cosBeta = (float) Math.cos(beta);
					float sinBeta = (float) Math.sin(beta);
					
					setXYZ(vertexCoords, 6 * j,
							radius * cosBeta * cosAlpha1,
							radius * sinBeta,
							radius * cosBeta * sinAlpha1);
					setXYZ(vertexCoords, 6 * j + 3,
							radius * cosBeta * cosAlpha0,
							radius * sinBeta,
							radius * cosBeta * sinAlpha0);
					
					if (useNormals) {
						setXYZ(normalCoords, 6 * j,
								cosBeta * cosAlpha1,
								sinBeta,
								cosBeta * sinAlpha1);
						setXYZ(normalCoords, 6 * j + 3,
								cosBeta * cosAlpha0,
								sinBeta,
								cosBeta * sinAlpha0);
					}

					if (useTexCoords) {
						setXY(textureCoords, 4 * j,
								((float) (i + 1)) / slices,
								((float) j) / stacks);
						setXY(textureCoords, 4 * j + 2,
								((float) i) / slices,
								((float) j) / stacks);
					}
				}
				
				slicesBuffers[i] = BufferUtils.newFloatBuffer(vertexCoords.length);
				slicesBuffers[i].put(vertexCoords);slicesBuffers[i].rewind();
				if (useNormals) {
					normalsBuffers[i] = BufferUtils.newFloatBuffer(normalCoords.length);
					normalsBuffers[i].put(normalCoords);
					normalsBuffers[i].rewind();
				}
				
				if (useTexCoords) {
					texCoordsBuffers[i] = BufferUtils.newFloatBuffer(textureCoords.length);
					texCoordsBuffers[i].put(textureCoords);
					texCoordsBuffers[i].rewind();
				}
			}
		}
		
		@Override
		public void draw(GL10 gl) {
			gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
			if (useNormals) {
				gl.glEnableClientState(GL10.GL_NORMAL_ARRAY);
			}
			if (useTexCoords) {
				gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
			}
			
			for (int i = 0; i < slices; i++) { // draw each slice
				gl.glVertexPointer(3, GL10.GL_FLOAT, 0, slicesBuffers[i]);
				if (useNormals) {
					gl.glNormalPointer(GL10.GL_FLOAT, 0, normalsBuffers[i]);
				}
				if (useTexCoords) {
					gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, texCoordsBuffers[i]);
				}
				gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, 2 * (stacks + 1));
			}
			
			gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
			if (useNormals) {
				gl.glDisableClientState(GL10.GL_NORMAL_ARRAY);
			}
			if (useTexCoords) {
				gl.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
			}
		}
	}

	
	public static void setXYZ(float[] vector, int offset, float x, float y, float z) {
		vector[offset] = x;
		vector[offset + 1] = y;
		vector[offset + 2] = z;
	}

	public static void setXYZn(float[] vector, int offset, float x, float y, float z) {
		float r = (float)Math.sqrt(x * x + y * y + z * z);
		if (r == 0.0f) r = 1.0f;
		setXYZ(vector, offset, x / r, y / r, z / r);
	}
	
	public static void setXY(float[] vector, int offset, float x, float y) {
		vector[offset] = x;
		vector[offset + 1] = y;
	}
}
