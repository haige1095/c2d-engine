package info.u250.c2d.utils;

import info.u250.c2d.engine.Engine;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

/**
 * @author lycying@gmail.com
 */
public final  class SpriteUtils {
	public  static final  Vector2 centerSprite(Sprite sprite){
		Vector2 tmp = new Vector2((Engine.getEngineConfig().width-sprite.getWidth())/2, (Engine.getEngineConfig().height-sprite.getHeight())/2);
		sprite.setPosition(tmp.x ,tmp.y);
		return tmp;
	}
	public static final Vector2 rightTopSprite(Sprite sprite){
		Vector2 tmp = new Vector2(Engine.getEngineConfig().width-sprite.getWidth(), Engine.getEngineConfig().height-sprite.getHeight());
		sprite.setPosition(tmp.x , tmp.y );
		return tmp;
	}
	public static final Vector2 rightBottomSprite(Sprite sprite){
		Vector2 tmp = new Vector2(Engine.getEngineConfig().width-sprite.getWidth(), 0);
		sprite.setPosition(tmp.x ,tmp.y);
		return tmp;
	}
	public static final Vector2 leftBottomSprite(Sprite sprite){
		Vector2 tmp = new Vector2(0, 0);
		sprite.setPosition(tmp.x ,tmp.y);
		return tmp;
	}
	public static final Vector2 leftTopSprite(Sprite sprite){
		Vector2 tmp = new Vector2(0, Engine.getEngineConfig().height-sprite.getHeight());
		sprite.setPosition(tmp.x ,tmp.y);
		return tmp;
	}
}
