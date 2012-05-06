package info.u250.c2d.graphic;

import info.u250.c2d.engine.Engine;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
/**
 * @author lycying@gmail.com
 * 
 * the c2d stage use the engine's camera and the engine's spritebatch, 
 * so it can be move or rotate along with the engine's camera .
 */
public class C2dStage extends Stage{
	public C2dStage(){
		super(Engine.getEngineConfig().width, Engine.getEngineConfig().height, true, Engine.getSpriteBatch());
		this.setCamera(Engine.getDefaultCamera());
	}
	
	/**
	 * it is important to change the method to stage coordinate
	 */
	@Override
	public void toStageCoordinates(int x, int y, Vector2 out) {
		out.set(Engine.screenToWorld(x, y));
	}
}
