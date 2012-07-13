package info.u250.c2d.tools.staff.surface;

import info.u250.c2d.engine.Engine;

import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.math.Vector2;

class SurfaceInput extends  InputAdapter {
	SurfaceAdapter model ;
	public SurfaceInput(SurfaceAdapter model){
		this.model = model;
	}
	
	@Override
	public boolean touchDown(int x, int y, int pointer, int button) {
		
		if(button == Buttons.LEFT){
			if(model.snapPoint == null){
				Vector2 newPoint = new Vector2(Engine.screenToWorld(x, y));
				model.snapPoint = newPoint;
				model.addPoint(newPoint);
			}
		}else if(button == Buttons.RIGHT){
			model.removeSnapePoint();
		}
		
		return false;
	}


	@Override
	public boolean touchDragged(int x, int y, int pointer) {
		if(null!=model.snapPoint){
			for(Vector2 v:this.model.data.points){
				if(Engine.screenToWorld(x, y).dst(v)<10){
					return false;
				}
			}
			model.snapPoint.set(Engine.screenToWorld(x, y));
			
			if(model.snapPoint.x>Engine.getDefaultCamera().position.x + Engine.getDefaultCamera().viewportWidth/2){
				Engine.getDefaultCamera().position.x = model.snapPoint.x-Engine.getDefaultCamera().viewportWidth/2;
			}else if(model.snapPoint.x<Engine.getDefaultCamera().position.x - Engine.getDefaultCamera().viewportWidth/2){
				Engine.getDefaultCamera().position.x = model.snapPoint.x+Engine.getDefaultCamera().viewportWidth/2;
			}
			if(model.snapPoint.y>Engine.getDefaultCamera().position.y + Engine.getDefaultCamera().viewportHeight/2){
				Engine.getDefaultCamera().position.y = model.snapPoint.y-Engine.getDefaultCamera().viewportHeight/2;
			}else if(model.snapPoint.y<Engine.getDefaultCamera().position.y - Engine.getDefaultCamera().viewportHeight/2){
				Engine.getDefaultCamera().position.y = model.snapPoint.y+Engine.getDefaultCamera().viewportHeight/2;
			}
			this.model.rebuild();
			model.save();
		}
		return false;
	}

	@Override
	public boolean mouseMoved(int x, int y) {
		model.snapPoint = null;
		for(Vector2 v:this.model.data.points){
			if(Engine.screenToWorld(x, y).dst(v)<10){
				model.snapPoint = v;
				break;
			}
		}
		return super.mouseMoved(x, y);
	}
}
