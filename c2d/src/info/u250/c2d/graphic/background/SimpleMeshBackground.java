package info.u250.c2d.graphic.background;


import info.u250.c2d.engine.Engine;
import info.u250.c2d.engine.service.Disposable;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
/**
 * this is a simple background for test or publish , 
 * it save a lot space of image just use a fews of codes . 
 * Works on opengl1.x and 2.x use the default shader .
 * Do not forgot to dispose it 
 * @author lycying@gmail.com
 *
 */
public class SimpleMeshBackground implements Disposable{
	Mesh mesh;
	ShaderProgram shader;
	public SimpleMeshBackground(){
		this(new Color(1, 1, 1, 1f),new Color(152f/255f, 181f/255f, 249f/255f, 1));
	}
	public SimpleMeshBackground(Color color1,Color color2){
		float WIDTH = Engine.getWidth();
		float HEIGHT = Engine.getHeight();
		float[] vertices = new float[] {
				0, 0, 				color1.r,color1.g,color1.b,color1.a,
				WIDTH, 0, 			color1.r,color1.g,color1.b,color1.a,
				WIDTH, HEIGHT, 		color2.r,color2.g,color2.b,color2.a,
				0, HEIGHT, 			color2.r,color2.g,color2.b,color2.a,
		};
		short[] indices = new short[] {0, 1, 2, 2, 3, 0};
		
		mesh = new Mesh(false, 4, 6, new VertexAttribute(Usage.Position, 2, ShaderProgram.POSITION_ATTRIBUTE), new VertexAttribute(Usage.Color, 4,ShaderProgram.COLOR_ATTRIBUTE));
		mesh.setVertices(vertices);
		mesh.setIndices(indices);
		if(Gdx.graphics.isGL20Available()){
			shader = SpriteBatch.createDefaultShader();
		}
	}
	
	
	public void render (float delta) {
		if(Gdx.graphics.isGL20Available()){
			shader.begin();
			shader.setUniformMatrix("u_projTrans", Engine.getDefaultCamera().combined);
			mesh.render(shader, GL10.GL_TRIANGLES);
			shader.end();
		}else{
			GL10 gl = Gdx.gl10;
			gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
			mesh.render(GL10.GL_TRIANGLES);
		}
	}
	@Override
	public void dispose() {
		if(Gdx.graphics.isGL20Available()){
			if(null!=shader){
				shader.dispose();
			}
		}
	}
}
