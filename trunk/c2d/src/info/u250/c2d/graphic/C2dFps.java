package info.u250.c2d.graphic;

import info.u250.c2d.engine.Engine;
import info.u250.c2d.engine.service.Renderable;

import com.badlogic.gdx.Gdx;

/**The FPS label was shown on top of all the scenes . Which show the performance of your game
 * @author lycying@gmail.com*/
public class C2dFps implements Renderable{
	@Override
	public void render(float delta){
		String text = 
				"FPS:"+Gdx.graphics.getFramesPerSecond()
				+"\nJHeap:"+Gdx.app.getJavaHeap()/1024/1204+"M"
				+"\nNHeap:"+Gdx.app.getNativeHeap()/1024/1024+"M";
		Engine.getSpriteBatch().begin();
		//don not follow the main camera . 
		Engine.getDefaultFont().drawMultiLine(
				Engine.getSpriteBatch(), text, 
				Engine.getEngineConfig().width-120 + Engine.getDefaultCamera().position.x - Engine.getEngineConfig().width/2 ,
				60f + Engine.getDefaultCamera().position.y - Engine.getEngineConfig().height/2);
		Engine.getSpriteBatch().end();
	}
}
