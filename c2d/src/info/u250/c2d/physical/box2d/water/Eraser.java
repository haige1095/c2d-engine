package info.u250.c2d.physical.box2d.water;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;

public class Eraser {

	static final String vertexShader = "attribute vec4 "
				+ ShaderProgram.POSITION_ATTRIBUTE
				+ ";\n" //
			+ "attribute vec4 " + ShaderProgram.COLOR_ATTRIBUTE
				+ ";\n" //
			+ "attribute vec2 " + ShaderProgram.TEXCOORD_ATTRIBUTE
				+ "0;\n" //
			+ "uniform mat4 u_projTrans;\n" //
			+ "varying vec4 v_color;\n" //
			+ "varying vec2 v_texCoords;\n" //
			+ "\n" //
			+ "void main()\n" //
			+ "{\n" //
			+ "   v_color = " + ShaderProgram.COLOR_ATTRIBUTE
				+ ";\n" //
			+ "   v_texCoords = " + ShaderProgram.TEXCOORD_ATTRIBUTE
				+ "0;\n" //
			+ "   gl_Position =  u_projTrans * "
				+ ShaderProgram.POSITION_ATTRIBUTE + ";\n" //
			+ "}\n";
	static final String fragmentShader = "#ifdef GL_ES\n" //
			+ "#define LOWP lowp\n"
				+ "precision mediump float;\n" //
			+ "#else\n"
				+ "#define LOWP \n"
				+ "#endif\n" //
			+ "varying LOWP vec4 v_color;\n" //
			+ "varying vec2 v_texCoords;\n" //
			+ "uniform sampler2D u_texture;\n" //
			+ "void main()\n"//
			+ "{\n" //
			+ "vec4 v_c = texture2D(u_texture, v_texCoords);\n" //
			+ "if (v_c.a < 0.15)\n"
				+ "   discard;\n"
				+ "v_c.a = v_c.a*0.75;\n"
				+ "gl_FragColor = v_c;\n"
				+ "}\n";

	static ShaderProgram shader;

	static public ShaderProgram createShader() {
		if (shader != null)
			return shader;

		ShaderProgram.pedantic = false;
		shader = new ShaderProgram(vertexShader,
					fragmentShader);
		if (shader.isCompiled() == false) {
			Gdx.app.log("shader compilication ERROR", shader.getLog());
		}
		return shader;
	}
}
