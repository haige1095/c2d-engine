package info.u250.c2d.tests.animations;

import info.u250.c2d.engine.Engine;
import info.u250.c2d.engine.EngineDrive;
import info.u250.c2d.engine.Scene;
import info.u250.c2d.engine.resources.AliasResourceManager;
import info.u250.c2d.graphic.AnimationSprite;
import info.u250.c2d.graphic.AnimationSpriteImage;
import info.u250.c2d.graphic.C2dStage;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;

public class ActionJump extends Engine {
	@Override
	protected EngineDrive onSetupEngineDrive() {
		return new EngineX();
	}

	@Override
	public void dispose() {
		super.dispose();
	}

	private class EngineX implements EngineDrive {
		@Override
		public void onResourcesRegister(AliasResourceManager<String> reg) {
			reg.textureAtlas("Anim", "data/animationsprite/turkey.atlas");
		}

		@Override
		public void dispose() {
		}

		@Override
		public EngineOptions onSetupEngine() {
			final EngineOptions opt = new EngineOptions(
					new String[] { "data/animationsprite/" }, 800, 480);
			return opt;
		}

		@Override
		public void onLoadedResourcesCompleted() {
			Engine.setMainScene(new MainScene());
		}
	}

	private static class MainScene extends C2dStage implements Scene {
		final AnimationSpriteImage actor;

		public MainScene(){
			actor = new AnimationSpriteImage(new AnimationSprite(0.05f, Engine.resource("Anim",TextureAtlas.class),"fly"));
			actor.setPosition(200, 20);
			actor.setRotation(90);
			actor.setOrigin(actor.getWidth()/2, actor.getHeight()/2);
			actor.addAction(Actions.forever(
					Actions.sequence(
							Actions.parallel(
									Actions.moveBy(0, 300,0.5f,Interpolation.pow2Out),
									Actions.moveBy(200, 0,0.5f),
									Actions.scaleTo(0.7f, 0.7f, 0.5f)
									),
							Actions.parallel(
									Actions.moveBy(0, -300,0.5f,Interpolation.pow2In),
									Actions.moveBy(200, 0,0.5f),
									Actions.scaleTo(1f, 1f, 0.5f)
									),
							Actions.parallel(
									Actions.moveBy(0, 300,0.5f,Interpolation.pow2Out),
									Actions.moveBy(-200, 0,0.5f),
									Actions.scaleTo(0.7f, 0.7f, 0.5f)
									),
							Actions.parallel(
									Actions.moveBy(0, -300,0.5f,Interpolation.pow2In),
									Actions.moveBy(-200, 0,0.5f),
									Actions.scaleTo(1f, 1f, 0.5f)
									)
							
							)
					));
			this.addActor(actor);
		}

		@Override
		public void render(float delta) {
			this.act(delta);
			this.draw();
		}

		@Override
		public InputProcessor getInputProcessor() {
			return null;
		}

		@Override
		public void update(float delta) {
		}

		@Override
		public void hide() {
		}

		@Override
		public void show() {
		}
	}
}
