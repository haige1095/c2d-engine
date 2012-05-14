package info.u250.c2d.engine.load.startup;

import info.u250.c2d.engine.Engine;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
/**show a line to draw the precent of the load process 
 * @author lycying@gmail.com
 */
public class LineLoading extends StartupLoading{
	
	@Override
	public void finishLoadingCleanup() {
		
	}
	final static float OFFSET = 50;
	final static float HEIGHT = 50.00000f/480.000000000f;
	@Override
	protected void inLoadingRender(float delta) {
		Engine.getShapeRenderer().setProjectionMatrix(Engine.getDefaultCamera().combined);
		Engine.getShapeRenderer().setColor(Color.GRAY);
		Engine.getShapeRenderer().begin(ShapeType.FilledRectangle);
		Engine.getShapeRenderer().filledRect(OFFSET, Engine.getEngineConfig().height/2, Engine.getEngineConfig().width-OFFSET*2, HEIGHT*Engine.getEngineConfig().height);
		Engine.getShapeRenderer().end();
		Engine.getShapeRenderer().setColor(1,1,0,0.2f);
		Engine.getShapeRenderer().begin(ShapeType.FilledRectangle);
		Engine.getShapeRenderer().filledRect(OFFSET, Engine.getEngineConfig().height/2, (Engine.getEngineConfig().width-OFFSET*2)*this.percent(), HEIGHT*Engine.getEngineConfig().height);
		Engine.getShapeRenderer().end();
	}
}
