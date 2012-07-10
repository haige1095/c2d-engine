package info.u250.c2d.tiled;

import info.u250.c2d.graphic.AnimationSpriteImage;

import com.badlogic.gdx.graphics.g2d.tiled.TiledObject;
import com.badlogic.gdx.scenes.scene2d.Group;

public class TiledObjectDrawable extends Group{
	private AnimationSpriteImage actor;
	public AnimationSpriteImage getAnimationSpriteImage(){
		return actor;
	}
	public TiledObjectDrawable(TileMapRenderer map,TiledObject object,AnimationSpriteImage actor){
		this.addActor(actor);
		this.setSize(actor.getWidth(), actor.getHeight());
		this.setPosition(object.x,map.getHeight()-object.y);
	}	
}
