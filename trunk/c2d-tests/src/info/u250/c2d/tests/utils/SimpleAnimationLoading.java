package info.u250.c2d.tests.utils;

import info.u250.c2d.engine.Engine;
import info.u250.c2d.engine.load.startup.StartupLoading;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class SimpleAnimationLoading extends StartupLoading{
	Sprite fg ;
	TextureAtlas atlas;
	Sprite bg ;
	Sprite mask ;
	public SimpleAnimationLoading(){
		atlas = new TextureAtlas(Gdx.files.internal("loading/pack"));
		fg = atlas.createSprite("fg");
		fg.setPosition(
				(Engine.getEngineConfig().width-fg.getWidth())/2, 
				(Engine.getEngineConfig().height-fg.getHeight())/2);
		bg = atlas.createSprite("bg");
		bg.setPosition(
				(Engine.getEngineConfig().width-bg.getWidth())/2, 
				(Engine.getEngineConfig().height-bg.getHeight())/2);
		mask = atlas.createSprite("mask");
		mask.setPosition(
				(Engine.getEngineConfig().width-mask.getWidth())/2, 
				(Engine.getEngineConfig().height-mask.getHeight())/2);
	}
	@Override
	public void finishLoadingCleanup() {
		if(null!=atlas){
			atlas.dispose();
			fg = null;
		}
	}

	@Override
	protected void inLoadingRender(float delta) {
		Gdx.gl.glClearColor(52/255f, 138/255f, 152/255f, 1);
		fg.setPosition(
				(Engine.getEngineConfig().width-357)/2  - fg.getWidth() + 357*this.percent() , 
				fg.getY());
		Engine.getSpriteBatch().begin();
		mask.draw(Engine.getSpriteBatch());
		fg.draw(Engine.getSpriteBatch());
		bg.draw(Engine.getSpriteBatch());
		Engine.getSpriteBatch().end();
	}
}