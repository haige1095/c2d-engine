package info.u250.c2d.graphic.surfaces;

import info.u250.c2d.engine.Engine;
import info.u250.c2d.graphic.surfaces.data.SurfaceData;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.GLCommon;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

/**
 * default drawable of the mesh
 *  ChangeLog : 
 *  2012/02/29  support opengl2.0,use the shader language 
 *  
 * @author lycying@gmail.com
 */
public class TriangleSurfaces extends CurveSurfaces {
	ShaderProgram shader = null;
	
	private ShaderProgram createShader (){
			ShaderProgram shader = new ShaderProgram(
					Gdx.files.classpath("info/u250/c2d/graphic/surfaces/surface.vertex.glsl"), 
					Gdx.files.classpath("info/u250/c2d/graphic/surfaces/surface.fragment.glsl"));
			return shader;
		
	}

	protected TriangleSurfaces(){}
	public TriangleSurfaces(SurfaceData data) {
		super(data);
		if(Gdx.graphics.isGL20Available()) shader = createShader();
	}

	@Override
	protected void doRender(float delta) {
		if (null != mesh) {
			if(Gdx.graphics.isGL20Available()) {
				GLCommon gl = Gdx.gl20;
				gl.glActiveTexture(GL10.GL_TEXTURE0 + 0);
				gl.glEnable(GL10.GL_TEXTURE_2D);
	
				gl.glDisable(GL10.GL_BLEND);
				gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
				texture.bind();
				shader.begin();
				shader.setUniformMatrix("u_projectionViewMatrix", Engine.getDefaultCamera().combined);
				shader.setUniformf("u_color", 1, 1,1, 1);
				shader.setUniformi("u_texture" + 0, 0);
				mesh.render(shader,data.primitiveType);
				shader.end();
			} else {
				GL10 gl = Gdx.gl10;
				gl.glEnable(GL10.GL_TEXTURE_2D);
				texture.bind();
				mesh.render(data.primitiveType);
				gl.glDisable(GL10.GL_TEXTURE_2D);
			}
		}
	}

	@Override
	protected void doBuild() {
		mesh = new Mesh(true, data.points.size, data.points.size, 
				new VertexAttribute(Usage.Position, 3, ShaderProgram.POSITION_ATTRIBUTE), 
				new VertexAttribute(Usage.TextureCoordinates, 2, ShaderProgram.TEXCOORD_ATTRIBUTE+"0"));
		mesh.setVertices(verticesForUnscaledTexture(texture, data.points));
		short[] indices = new short[data.points.size];
		for (int i = 0; i < data.points.size; i++) {
			indices[i] = (short) i;
		}
		mesh.setIndices(indices);
	}

	@Override
	public void dispose() {
		super.dispose();
		if(null!=shader)shader.dispose();
	}
	protected float[] verticesForUnscaledTexture(Texture texture,
			Array<Vector2> vertices) {
		float[] result = new float[5 * vertices.size];
		float u = ((float) 1 / texture.getWidth());
		float v = ((float) 1 / texture.getHeight());
		for (int i = 0; i < vertices.size; i++) {
			result[5 * i + 0] = vertices.get(i).x;
			result[5 * i + 1] = vertices.get(i).y;
			result[5 * i + 2] = 0;
			result[5 * i + 3] = +u * vertices.get(i).x;
			result[5 * i + 4] = -v * vertices.get(i).y;
		}
		return result;
	}
}
