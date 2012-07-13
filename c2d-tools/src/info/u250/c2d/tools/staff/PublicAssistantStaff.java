package info.u250.c2d.tools.staff;

import info.u250.c2d.engine.Engine;
import info.u250.c2d.engine.Scene;
import info.u250.c2d.tools.scenes.SceneWorkTable;

import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;

public class PublicAssistantStaff implements Scene{
	SceneWorkTable editor ;
	public PublicAssistantStaff(){
		this.editor = Engine.resource("Editor");
	}
	public Vector2 snapPoint = new Vector2();
	
	public void render(float delta){
		//draw the x,y axis
		editor.render.begin(ShapeType.Line);
		editor.render.setColor(Color.GREEN);
		editor.render.line(0, 0, Engine.getDefaultCamera().viewportWidth*Engine.getDefaultCamera().getZoom(),0 );
		editor.render.line(Engine.getDefaultCamera().viewportWidth*Engine.getDefaultCamera().getZoom() -10, -5, Engine.getDefaultCamera().viewportWidth*Engine.getDefaultCamera().getZoom(),0 );
		editor.render.line(Engine.getDefaultCamera().viewportWidth*Engine.getDefaultCamera().getZoom() -10, +5, Engine.getDefaultCamera().viewportWidth*Engine.getDefaultCamera().getZoom(),0 );
		
		
		editor.render.setColor(Color.RED);
		editor.render.line(0, 0, 0,Engine.getDefaultCamera().viewportHeight*Engine.getDefaultCamera().getZoom());
		editor.render.line(5, Engine.getDefaultCamera().viewportHeight*Engine.getDefaultCamera().getZoom() - 10, 0,Engine.getDefaultCamera().viewportHeight*Engine.getDefaultCamera().getZoom());
		editor.render.line(-5, Engine.getDefaultCamera().viewportHeight*Engine.getDefaultCamera().getZoom() - 10, 0,Engine.getDefaultCamera().viewportHeight*Engine.getDefaultCamera().getZoom());
		
		
		editor.render.end();
		
//		rbg.render(delta);
		editor.render.begin(ShapeType.Line);
		//800 480
		editor.render.setColor(Color.GRAY);
		editor.render.line(0, 480*Engine.getDefaultCamera().getZoom(), 800*Engine.getDefaultCamera().getZoom(),480*Engine.getDefaultCamera().getZoom()*Engine.getDefaultCamera().getZoom() );
		editor.render.line(800*Engine.getDefaultCamera().getZoom(), 0, 800*Engine.getDefaultCamera().getZoom(),480*Engine.getDefaultCamera().getZoom()*Engine.getDefaultCamera().getZoom() );
		editor.render.end();
		
		Engine.getSpriteBatch().begin();
		Engine.getDefaultFont().setColor(Color.WHITE);
		Engine.getDefaultFont().draw(Engine.getSpriteBatch(), "(0,0)", 0, 0+20);
		Engine.getSpriteBatch().end();
		//draw the rule
		//draw the x number
		editor.render.begin(ShapeType.FilledRectangle);
		editor.render.setColor(Color.DARK_GRAY);
		editor.render.filledRect(
				Engine.getDefaultCamera().position.x - Engine.getDefaultCamera().viewportWidth/2*Engine.getDefaultCamera().getZoom(), 
				Engine.getDefaultCamera().position.y - Engine.getDefaultCamera().viewportHeight/2*Engine.getDefaultCamera().getZoom(), Engine.getDefaultCamera().viewportWidth*Engine.getDefaultCamera().getZoom(), 10*Engine.getDefaultCamera().getZoom());
		editor.render.end();
		editor.render.begin(ShapeType.Line);
		editor.render.setColor(Color.WHITE);
		for(int i=0;i<(int)Engine.getDefaultCamera().viewportWidth/10*Engine.getDefaultCamera().getZoom()+3;i++){
			float x = Engine.getDefaultCamera().position.x - Engine.getDefaultCamera().viewportWidth/2*Engine.getDefaultCamera().getZoom()  + i*10;
			float y = Engine.getDefaultCamera().position.y - Engine.getDefaultCamera().viewportHeight/2*Engine.getDefaultCamera().getZoom();
			
			int length = 3;
			if(i>2){
				if(i%10==0){
					length = 10;
					Engine.getSpriteBatch().begin();
					Engine.getDefaultFont().setColor(Color.GRAY);
					Engine.getDefaultFont().draw(Engine.getSpriteBatch(), (int)x +"", x-10, y+25);
					Engine.getSpriteBatch().end();
				}else if(i%5==0){
					length = 7;
				}
				
				editor.render.line(x, y, x, y+length*Engine.getDefaultCamera().getZoom());
			}
		}
		editor.render.end();
		
		editor.render.begin(ShapeType.FilledRectangle);
		editor.render.setColor(Color.DARK_GRAY);
		editor.render.filledRect(
				Engine.getDefaultCamera().position.x - Engine.getDefaultCamera().viewportWidth/2*Engine.getDefaultCamera().getZoom(), 
				Engine.getDefaultCamera().position.y - Engine.getDefaultCamera().viewportHeight/2*Engine.getDefaultCamera().getZoom(), 10*Engine.getDefaultCamera().getZoom(), Engine.getDefaultCamera().viewportHeight*Engine.getDefaultCamera().getZoom());
		editor.render.end();
		//draw the y number
		editor.render.begin(ShapeType.Line);
		editor.render.setColor(Color.WHITE);
		for(int i=0;i<(int)Engine.getDefaultCamera().viewportHeight/10*Engine.getDefaultCamera().getZoom()+3;i++){
			float x = Engine.getDefaultCamera().position.x - Engine.getDefaultCamera().viewportWidth/2*Engine.getDefaultCamera().getZoom()  ;
			float y = Engine.getDefaultCamera().position.y - Engine.getDefaultCamera().viewportHeight/2*Engine.getDefaultCamera().getZoom() + i*10;
						
			int length = 3;
			if(i>2){
				if(i%10==0){
					length = 10;
					Engine.getSpriteBatch().begin();
					Engine.getDefaultFont().setColor(Color.GRAY);
					Engine.getDefaultFont().draw(Engine.getSpriteBatch(), (int)y +"", x+10, y+10);
					Engine.getSpriteBatch().end();
				}else if(i%5==0){
					length = 7;
				}
						
				editor.render.line(x, y, x+length*Engine.getDefaultCamera().getZoom(), y);
			}
		}		
		
		editor.render.end();
		
		
		//draw the grab point
		editor.render.setColor(Color.YELLOW);
		editor.render.begin(ShapeType.Line);
//		for(int i=0;i< (int)Engine.getDefaultCamera().viewportWidth*Engine.getDefaultCamera().zoom;i+=10){
//			editor.render.point( Engine.getDefaultCamera().position.x - Engine.getDefaultCamera().viewportWidth/2*Engine.getDefaultCamera().zoom +i, snapPoint.y, 0);
//		}
//		for(int i=0;i< (int)Engine.getDefaultCamera().viewportHeight*Engine.getDefaultCamera().zoom;i+=10){
//			editor.render.point( snapPoint.x,Engine.getDefaultCamera().position.y - Engine.getDefaultCamera().viewportHeight/2*Engine.getDefaultCamera().zoom +i, 0);
//		}
		editor.render.line(
				Engine.getDefaultCamera().position.x - Engine.getDefaultCamera().viewportWidth/2*Engine.getDefaultCamera().getZoom() , snapPoint.y,
				Engine.getDefaultCamera().position.x - Engine.getDefaultCamera().viewportWidth/2*Engine.getDefaultCamera().getZoom() + Engine.getDefaultCamera().viewportWidth*Engine.getDefaultCamera().getZoom(), snapPoint.y);
		editor.render.line(
				snapPoint.x, Engine.getDefaultCamera().position.y - Engine.getDefaultCamera().viewportHeight/2*Engine.getDefaultCamera().getZoom() , 
				snapPoint.x , Engine.getDefaultCamera().position.y - Engine.getDefaultCamera().viewportHeight/2*Engine.getDefaultCamera().getZoom() + Engine.getDefaultCamera().viewportHeight*Engine.getDefaultCamera().getZoom());
		editor.render.end();
	}

	@Override
	public void update(float delta) {}

	@Override
	public void show() {}

	@Override
	public void hide() {}

	@Override
	public InputProcessor getInputProcessor() {
		return new InputAdapter(){
			boolean moveCam = false;
			Vector2  beginPosition = new Vector2();
			@Override
			public boolean mouseMoved(int x, int y) {
				snapPoint = Engine.screenToWorld(x, y);
				return super.mouseMoved(x, y);
			}

			@Override
			public boolean touchDragged(int x, int y, int pointer) {
//				callback.xy(screenToWorld(x, y));
				snapPoint = Engine.screenToWorld(x, y);
				if(moveCam){
					Vector2 now = Engine.screenToWorld(x, y);
					Vector2 v = beginPosition.cpy().sub(now);
					beginPosition = now;
					Engine.getDefaultCamera().position.x += v.x;
					Engine.getDefaultCamera().position.y += v.y;
//					callback.updateCameraInfo();
				}
				return super.touchDragged(x, y, pointer);
			}

			@Override
			public boolean touchDown(int x, int y, int pointer, int button) {
				if(button == Buttons.RIGHT){
					beginPosition.set(Engine.screenToWorld(x, y));
					moveCam = true;
				}
				return super.touchDown(x, y, pointer, button);
			}

			@Override
			public boolean touchUp(int x, int y, int pointer, int button) {
				moveCam = false;
				
				return super.touchUp(x, y, pointer, button);
			}
		};
	}
}
