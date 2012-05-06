package info.u250.c2d.engine.load.startup;

import info.u250.c2d.engine.Engine;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
/**
 * WindmillLoading
 * @author lycying@gmail.com
 */
public class WindmillLoading extends StartupLoading{
	ShapeRenderer renderer ;
	public WindmillLoading(){
		renderer = new ShapeRenderer();
	}
	@Override
	public void finishLoadingCleanup() {
		renderer.dispose();
	}
	float deltaAppend  = 0f;
	@Override
	protected void inLoadingRender(float delta) {
		deltaAppend+=delta;
		renderer.identity();
		renderer.translate(Engine.getEngineConfig().width/2, Engine.getEngineConfig().height/2, 0);
		renderer.rotate(0, 0, 1, deltaAppend*50);
		renderer.setProjectionMatrix(Engine.getDefaultCamera().combined);
		renderer.setColor(Color.YELLOW);
		renderer.begin(ShapeType.FilledTriangle);
		renderer.filledTriangle(
				0,0,
				-50,	100, 
				50, 	100);
		renderer.end();
		renderer.setColor(Color.RED);
		renderer.begin(ShapeType.FilledTriangle);
		renderer.filledTriangle(
				0,0,
				-50,	-100, 
				50, 	-100);
		renderer.end();
		renderer.setColor(Color.BLUE);
		renderer.begin(ShapeType.FilledTriangle);
		renderer.filledTriangle(
				0,0,
				-100,	-50, 
				-100, 	50);
		renderer.end();
		renderer.setColor(Color.GREEN);
		renderer.begin(ShapeType.FilledTriangle);
		renderer.filledTriangle(
				0,0,
				100,	-50, 
				100, 	50);
		renderer.end();
		renderer.setColor(Color.WHITE);
		renderer.begin(ShapeType.FilledCircle);
		renderer.filledCircle(0, 0, 15);
		renderer.end();
		renderer.setColor(Color.ORANGE);
		renderer.begin(ShapeType.FilledCircle);
		renderer.filledCircle(0, 0, 10);
		renderer.end();
	}
}
