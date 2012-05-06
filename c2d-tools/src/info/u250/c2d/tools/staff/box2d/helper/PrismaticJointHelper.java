package info.u250.c2d.tools.staff.box2d.helper;

import info.u250.c2d.engine.Engine;
import info.u250.c2d.engine.Scene;
import info.u250.c2d.physical.box2d.loader.cbt.data.BodyData;
import info.u250.c2d.physical.box2d.loader.cbt.data.DebugColor;
import info.u250.c2d.physical.box2d.loader.cbt.data.PrismaticJointData;
import info.u250.c2d.tools.Events;
import info.u250.c2d.tools.scenes.SceneWorkTable;
import info.u250.c2d.tools.staff.box2d.Box2dAdapter;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;

public class PrismaticJointHelper implements Scene {
	final ShapeRenderer render;
	Box2dAdapter adapter;
	BodyData data1;
	BodyData data2;
	Vector2 firstPoint = new Vector2();
	Vector2 secondPoint = new Vector2();
	PrismaticJointData data ;
	
	public PrismaticJointHelper(Box2dAdapter adapter) {
		render = Engine.resource("Editor", SceneWorkTable.class).render;
		this.adapter = adapter;
	}

	@Override
	public void update(float delta) {
		
	}

	@Override
	public void render(float delta) {
		if(data1!=null){
			Gdx.gl.glEnable(GL10.GL_BLEND);
			render.setColor(DebugColor.COLOR_PrismaticJoint);
			render.begin(ShapeType.Line);
			render.line(data1.center.x, data1.center.y, firstPoint.x,firstPoint.y);
			render.line(secondPoint.x, secondPoint.y, firstPoint.x, firstPoint.y);
			render.end();
			Gdx.gl.glDisable(GL10.GL_BLEND);
		}
	}

	@Override
	public void show() {
	}

	@Override
	public void hide() {
	}

	Vector2 tmp = new Vector2();
	@Override
	public InputProcessor getInputProcessor() {
		return new InputAdapter() {
			@Override
			public boolean touchDown(int x, int y, int pointer, int button) {
				if(button == Buttons.LEFT){
					for(BodyData d:adapter.data.bodyDatas){
						if(d.isFocus(Engine.screenToWorld(x, y))){
							if(data1==null){
								data1 = d;
								firstPoint.set(Engine.screenToWorld(x, y));
								secondPoint.set(Engine.screenToWorld(x, y));
							}
							Engine.getEventManager().fire(Events.UPDATE_BOXED_PANEL,data1);
							break;
						}
					}
				}
				return true;
			}
			@Override
			public boolean touchDragged(int x, int y, int pointer) {
				secondPoint.set(Engine.screenToWorld(x, y));
				return true;
			}

			@Override
			public boolean touchUp(int x, int y, int pointer, int button) {
				for(BodyData d:adapter.data.bodyDatas){
					if(d.isFocus(Engine.screenToWorld(x, y))){
						if(data1!=null&&data1!=d){
							data2 = d;
							secondPoint.set(Engine.screenToWorld(x, y));
							data = new PrismaticJointData();
							data.bodyA = data1;
							data.bodyB = data2;
							data.localAnchorA.set(firstPoint).sub(data1.center);
							adapter.data.jointDatas.add(data);
							Engine.getEventManager().fire(Events.UPDATE_BOXED_PANEL,data);
						}	
						break;
					}
				}
				data1 = null;
				data2 = null;
				return super.touchUp(x, y, pointer, button);
			}
		};
	
	}

}
