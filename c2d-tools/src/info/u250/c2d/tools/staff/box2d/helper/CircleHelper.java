package info.u250.c2d.tools.staff.box2d.helper;

import info.u250.c2d.engine.Engine;
import info.u250.c2d.engine.Scene;
import info.u250.c2d.physical.box2d.loader.cbt.data.CircleData;
import info.u250.c2d.tools.Events;
import info.u250.c2d.tools.scenes.SceneWorkTable;
import info.u250.c2d.tools.staff.box2d.Box2dAdapter;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;

public class CircleHelper implements Scene {
	final ShapeRenderer render;
	CircleData data;
	final Vector2 firstPoint = new Vector2();
	final Vector2 secondPoint = new Vector2();
	int currentPoint = -1;
	Box2dAdapter adapter;

	public CircleHelper(Box2dAdapter adapter) {
		render = Engine.resource("Editor", SceneWorkTable.class).render;
		this.adapter = adapter;
	}

	@Override
	public void update(float delta) {
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glEnable(GL10.GL_BLEND);
		if (currentPoint == 1) {
			float dst = firstPoint.dst(secondPoint);
			if (dst > 0) {
				render.setColor(new Color(1, 1, 1, 0.4f));
				render.begin(ShapeType.FilledCircle);
				render.filledCircle(firstPoint.x, firstPoint.y, dst);
				render.end();
			}
		}
		Gdx.gl.glDisable(GL10.GL_BLEND);
	}

	@Override
	public void show() {
	}

	@Override
	public void hide() {
	}

	@Override
	public InputProcessor getInputProcessor() {
		return new InputAdapter() {
			@Override
			public boolean touchDown(int x, int y, int pointer, int button) {
				if(button == Buttons.LEFT){
					if (-1 == currentPoint) {
						firstPoint.set(Engine.screenToWorld(x, y));
						secondPoint.set(Engine.screenToWorld(x, y));
						data = new CircleData();
						Engine.getEventManager().fire(Events.UPDATE_BOXED_PANEL,data);
						currentPoint = 1;
						// do start
					} else if (1 == currentPoint) {
						secondPoint.set(Engine.screenToWorld(x, y));
						if (firstPoint.dst(secondPoint) > 0) {
							data.radius = firstPoint.dst(secondPoint);
							data.center.set(firstPoint);
							currentPoint = -1;
							adapter.data.bodyDatas.add(data);
							Engine.getEventManager().fire(Events.UPDATE_BOXED_PANEL,data);
							data = null;
							// do end
						}
					}
				}else{
					data = null;
				}
				return true;
			}

			@Override
			public boolean touchMoved(int x, int y) {
				if (1 == currentPoint) {
					// do move
					secondPoint.set(Engine.screenToWorld(x, y));
				}
				return super.touchMoved(x, y);
			}
			@Override
			public boolean keyDown(int keycode) {
				if(keycode == Keys.ESCAPE){
					currentPoint = -1;
					data = null;
				}
				return super.keyDown(keycode);
			}
			
			@Override
			public boolean touchUp(int x, int y, int pointer, int button) {
				return super.touchUp(x, y, pointer, button);
			}
		};
	}

}
