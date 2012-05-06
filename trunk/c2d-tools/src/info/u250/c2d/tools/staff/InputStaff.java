package info.u250.c2d.tools.staff;

import info.u250.c2d.engine.Engine;
import info.u250.c2d.engine.Scene;
import info.u250.c2d.engine.events.Event;
import info.u250.c2d.engine.events.EventListener;
import info.u250.c2d.tools.Events;
import info.u250.c2d.tools.scenes.SceneWorkTable;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public abstract class InputStaff implements Scene{

	protected abstract void resize(float width,float height);
	protected Stage ui ;
	protected Skin skin;
	protected SceneWorkTable editor;
	
	public InputStaff(){
		ui = new Stage(Engine.getEngineConfig().width, Engine.getEngineConfig().height, false);
		skin = Engine.resource("Skin");
		editor = Engine.resource("Editor");
		Engine.getEventManager().register(Events.RESIZE, new EventListener() {
			@Override
			public void onEvent(Event event) {
				ui.setViewport(Engine.getEngineConfig().width, Engine.getEngineConfig().height, false);
				resize(Engine.getEngineConfig().width, Engine.getEngineConfig().height);
			}
		});
	}
	@Override
	public void update(float delta) {
	}

	@Override
	public void render(float delta) {
		this.ui.act(delta);
		this.ui.draw();
	}

	@Override
	public void show() {}

	@Override
	public void hide() {}

	@Override
	public InputProcessor getInputProcessor() {
		return ui ;
	}
}