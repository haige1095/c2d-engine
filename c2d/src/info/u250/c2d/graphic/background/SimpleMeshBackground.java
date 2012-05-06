package info.u250.c2d.graphic.background;


import info.u250.c2d.engine.Engine;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;
/**
 * this is a simple background for test or publish , 
 * it save a lot space of image just use a fews of codes . 
 * Note it only support OpenglEs 1.x currently .
 * @author lycying@gmail.com
 *
 */
public class SimpleMeshBackground {
	Mesh mesh;
	public SimpleMeshBackground(){
		this(new Color(1, 1, 1, 1f),new Color(152f/255f, 181f/255f, 249f/255f, 1));
	}
	public SimpleMeshBackground(Color color1,Color color2){
		if(Gdx.graphics.isGL20Available()){
			Gdx.app.error("C2d", "this method only support GL1.x ");
		}
		float WIDTH = Engine.getEngineConfig().width;
		float HEIGHT = Engine.getEngineConfig().height;
		float[] vertices = new float[] {
				0, 0, 				color1.r,color1.g,color1.b,color1.a,
				WIDTH, 0, 			color1.r,color1.g,color1.b,color1.a,
				WIDTH, HEIGHT, 		color2.r,color2.g,color2.b,color2.a,
				0, HEIGHT, 			color2.r,color2.g,color2.b,color2.a,
		};
		short[] indices = new short[] {0, 1, 2, 2, 3, 0};
		
		mesh = new Mesh(false, 4, 6, new VertexAttribute(Usage.Position, 2, "a_pos"), new VertexAttribute(Usage.Color, 4, "a_col"));
		mesh.setVertices(vertices);
		mesh.setIndices(indices);
	}
	
	
	public void render (float delta) {
		GL10 gl = Gdx.gl10;
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		mesh.render(GL10.GL_TRIANGLES);
	}
	
}
