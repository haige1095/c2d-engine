package info.u250.c2d.graphic;

import info.u250.c2d.engine.Engine;
import info.u250.c2d.engine.service.Renderable;

import com.badlogic.gdx.Gdx;

/**The FPS label was shown on top of all the scenes . Which show the performance of your game
 * @author lycying@gmail.com*/
public class C2dFps implements Renderable{
	@Override
	public void render(float delta){
		String text = "FPS:"+Gdx.graphics.getFramesPerSecond();
		Engine.getSpriteBatch().begin();
		Engine.getDefaultFont().draw(Engine.getSpriteBatch(), text, 
				Engine.getEngineConfig().width-100,
				50f);
		Engine.getSpriteBatch().end();
	}
}
