package info.u250.c2d.engine.load.startup;

import info.u250.c2d.engine.Engine;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
/**show a line bock groups  to draw the precent of the load process 
 * @author lycying@gmail.com
 */
public class LineBlocksLoading extends StartupLoading{
	ShapeRenderer renderer ;
	public LineBlocksLoading(){
		renderer = new ShapeRenderer();
	}
	@Override
	public void finishLoadingCleanup() {
		renderer.dispose();
	}
	final static float OFFSET = 50;
	final static float HEIGHT = 10.00000f/480.000000000f;
	final static float BLOCK = 5.00000f/480.0000000f;
	@Override
	protected void inLoadingRender(float delta) {
		renderer.setProjectionMatrix(Engine.getDefaultCamera().combined);

		final float width = (Engine.getEngineConfig().width-OFFSET*2)*this.percent();
		renderer.setColor(Color.WHITE);
		renderer.begin(ShapeType.FilledRectangle);
		for(int i=0;i*BLOCK*Engine.getEngineConfig().width<width;i++){
			if(i%2==0){
				renderer.filledRect(OFFSET+i*BLOCK*Engine.getEngineConfig().width, Engine.getEngineConfig().height/2, BLOCK*Engine.getEngineConfig().width , HEIGHT*Engine.getEngineConfig().height);
			}
		}
		renderer.end();
		
	}
}
