package info.u250.c2d.graphic;

import info.u250.c2d.engine.Engine;
import info.u250.c2d.graphic.AnimationSprite;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class AnimationSpriteImage extends Actor {
	final AnimationSprite sprite ;
	public AnimationSprite getSprite() {
		return sprite;
	}
	public AnimationSpriteImage(AnimationSprite sprite){
		this.sprite = sprite;
		this.setWidth(this.sprite.getWidth());
		this.setHeight( this.sprite.getHeight() );
	}
	@Override
	public void draw(SpriteBatch batch, float parentAlpha) {
		this.sprite.setScale(this.getScaleX(), this.getScaleY());
		this.sprite.setRotation(this.getRotation());
		this.sprite.setPosition(this.getX(), this.getY());
		this.sprite.render(Engine.getDeltaTime());
	}
}
