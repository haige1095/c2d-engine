package info.u250.c2d.engine.transitions;

import info.u250.c2d.accessors.C2dCameraAccessor;
import info.u250.c2d.engine.Engine;
import info.u250.c2d.engine.Transition;
import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;

import com.badlogic.gdx.Gdx;
/**
 * Transition Scene Flip Horizontal
 * @author lycying@gmail.com
 */
final class TransitionSceneFlipHorizontal extends Transition{	
	public TransitionSceneFlipHorizontal(){
	}
	
	@Override
	protected void doTransition(final int halfDurationMillis) {
		outgoing.hide();
		Tween
		.to(Engine.getDefaultCamera(), C2dCameraAccessor.ROTATION_Y, halfDurationMillis).target(90)
		.setCallback(new TweenCallback() {
			@Override
			public void onEvent(int type, BaseTween<?> source) {
				Engine.getDefaultCamera().setAngleY(-90);
				doSetMainScene(incoming);
				Tween
				.to(Engine.getDefaultCamera(), C2dCameraAccessor.ROTATION_Y, halfDurationMillis).target(0)
				.setCallback(new TweenCallback() {
					@Override
					public void onEvent(int type, BaseTween<?> source) {
						incoming.show();
						Gdx.input.setInputProcessor(incoming.getInputProcessor());
						reset();
					}

				}).start(Engine.getTweenManager());
			}
		}).start(Engine.getTweenManager());
	}
	@Override
	public void render(float delta) {
		Engine.getMainScene().render(delta);
	}
}
