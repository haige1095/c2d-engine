package info.u250.c2d.tests;

import info.u250.c2d.engine.CoreProvider.TransitionType;
import info.u250.c2d.engine.Engine;
import info.u250.c2d.engine.EngineDrive;
import info.u250.c2d.engine.Scene;
import info.u250.c2d.engine.resources.AliasResourceManager;

import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;

public class TransitionSceneTest extends Engine {
	@Override
	protected EngineDrive onSetupEngineDrive() {
		return new EngineX();
	}

	@Override
	public void dispose() {
		super.dispose();
	}

	Scene1 scene1 = new Scene1();
	Scene2 scene2 = new Scene2();
	
	int index = 0;

	TransitionType getTransition() {
		index++;
		if (index > TransitionType.values().length - 1) {
			index = 0;
		}
		return TransitionType.values()[index];
	}

	private class Scene1 implements Scene {

		@Override
		public void update(float delta) {

		}

		@Override
		public void render(float delta) {
			Engine.getSpriteBatch().begin();
			Engine.getSpriteBatch().draw(Engine.resource("BG1",Texture.class), 0, 0);
			Engine.getSpriteBatch().end();

			Engine.debugInfo("Current:" + TransitionType.values()[index]);
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
					Engine.setMainScene(scene2, getTransition());
					return super.touchDown(x, y, pointer, button);
				}
			};
		}
	}

	private class Scene2 implements Scene {

		@Override
		public void update(float delta) {

		}

		@Override
		public void render(float delta) {
			Engine.getSpriteBatch().begin();
			Engine.getSpriteBatch().draw(Engine.resource("BG2",Texture.class), 0, 0);
			Engine.getSpriteBatch().end();

			Engine.getDefaultFont().setColor(Color.BLACK);
			Engine.debugInfo("Current:" + TransitionType.values()[index]);
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
					Engine.setMainScene(scene1, getTransition());
					return super.touchDown(x, y, pointer, button);
				}
			};
		}
	}

	private class EngineX implements EngineDrive {
		@Override
		public void onResourcesRegister(AliasResourceManager<String> reg) {
			reg.texture("BG1", "data/background-1.png");
			reg.texture("BG2", "data/background-2.png");
		}

		@Override
		public void dispose() {
		}

		@Override
		public EngineOptions onSetupEngine() {
			return new EngineOptions(new String[] { "data/background-1.png",
					"data/background-2.png" }, 800, 480);
		}

		@Override
		public void onLoadedResourcesCompleted() {
			Engine.setMainScene(scene1);
		}
	}
}
