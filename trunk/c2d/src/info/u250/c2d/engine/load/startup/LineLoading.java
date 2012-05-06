package info.u250.c2d.engine.load.startup;

import info.u250.c2d.engine.Engine;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
/**show a line to draw the precent of the load process 
 * @author lycying@gmail.com
 */
public class LineLoading extends StartupLoading{
	ShapeRenderer renderer ;
	public LineLoading(){
		renderer = new ShapeRenderer();
	}
	@Override
	public void finishLoadingCleanup() {
		renderer.dispose();
	}
	final static float OFFSET = 50;
	final static float HEIGHT = 50.00000f/480.000000000f;
	@Override
	protected void inLoadingRender(float delta) {
		renderer.setProjectionMatrix(Engine.getDefaultCamera().combined);
		
		renderer.setColor(Color.GRAY);
		renderer.begin(ShapeType.FilledRectangle);
		renderer.filledRect(OFFSET, Engine.getEngineConfig().height/2, Engine.getEngineConfig().width-OFFSET*2, HEIGHT*Engine.getEngineConfig().height);
		renderer.end();
		renderer.setColor(1,1,0,0.2f);
		renderer.begin(ShapeType.FilledRectangle);
		renderer.filledRect(OFFSET, Engine.getEngineConfig().height/2, (Engine.getEngineConfig().width-OFFSET*2)*this.percent(), HEIGHT*Engine.getEngineConfig().height);
		renderer.end();
	}
}
