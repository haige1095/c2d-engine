package info.u250.c2d.graphic;

import info.u250.c2d.engine.Engine;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

 /** 
 *  @author lycying@gmail.com
 */
public class Mask {
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

	private static Sprite sprite = null;
	private static Pixmap pixmap = null;
	public Mask(Color color) {
		this.color = color ;
		if(pixmap!=null){
			pixmap.dispose();
		}
		if(null!=sprite){
			sprite.getTexture().dispose();
		}
		pixmap = new Pixmap(4, 4, Format.RGBA8888);
		pixmap.setColor(color);
		pixmap.fillRectangle(0, 0, 4, 4);
		sprite = new Sprite(new Texture(pixmap));
		sprite.setSize(Engine.getEngineConfig().width, Engine.getEngineConfig().height);

	}

	public void render(float delta) {
		Engine.getSpriteBatch().begin();
		sprite.setColor(color);
		sprite.draw(Engine.getSpriteBatch(), transparency);
		Engine.getSpriteBatch().end();
	}
}
