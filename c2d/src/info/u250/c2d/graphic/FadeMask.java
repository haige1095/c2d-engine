package info.u250.c2d.graphic;

import info.u250.c2d.engine.Engine;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

 /** 
 *  @author lycying@gmail.com
 */
public class FadeMask {
	private float transparency = 0.6f;
	private Color color = Color.BLACK;

	public Color getColor() {
		return color;
	}
	public float getTransparency() {
		return transparency;
	}
	public void setTransparency(float transparency) {
		this.transparency = transparency;
	}

	public FadeMask(Color color) {
		this.color = color;
	}

	public void render(float delta) {
		this.color.a = transparency;
		Gdx.gl.glEnable(GL10.GL_BLEND);
		Gdx.gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
		Engine.getShapeRenderer().setProjectionMatrix(Engine.getDefaultCamera().combined);
		Engine.getShapeRenderer().begin(ShapeType.FilledRectangle);
		Engine.getShapeRenderer().setColor(color);
		Engine.getShapeRenderer().filledRect(0, 0, Engine.getEngineConfig().width, Engine.getEngineConfig().height);
		Engine.getShapeRenderer().end();
		Gdx.gl.glDisable(GL10.GL_BLEND);
	}
}
