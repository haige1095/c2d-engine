package info.u250.c2d.input;

import info.u250.c2d.engine.C2dCamera;
import info.u250.c2d.engine.Engine;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector2;

/**
 * 
 * @author beanu
 * 
 *         CameraControllerInRangeAdapter adapter=new
 *         CameraControllerInRangeAdapter(camera); GestureDetector processor=
 *         new CameraControllerInRange(adapter);
 *         Gdx.input.setInputProcessor(processor);
 * 
 */
public class CameraControllerInRange extends GestureDetector {

	public CameraControllerInRange(GestureListener listener) {
		super(listener);
	}

	public CameraControllerInRange(int halfTapSquareSize, float tapCountInterval, float longPressDuration, float maxFlingDelay,
			GestureListener listener){
		super(halfTapSquareSize, tapCountInterval, longPressDuration, maxFlingDelay, listener);
	}
	/**
	 * 
	 * @author beanu update() will be render
	 */
	public interface CameraGestureListener extends GestureListener {
		public void update();
	}

	public static class CameraControllerInRangeAdapter implements
			CameraGestureListener {
		float velX, velY;
		boolean flinging = false;
		float initialScale = 1;
		C2dCamera camera;

		public CameraControllerInRangeAdapter(C2dCamera camera) {
			this.camera = camera;
		}

		@Override
		public boolean touchDown(float x, float y, int pointer) {
			flinging = false;
			initialScale = camera.position.z
					/ (Engine.getEngineConfig().height / 2);
			return false;
		}

		@Override
		public boolean pan(float x, float y, float deltaX, float deltaY) {

			camera.position.add(
					-deltaX * camera.position.z
							/ (Engine.getEngineConfig().height / 2), deltaY
							* camera.position.z
							/ (Engine.getEngineConfig().height / 2), 0);

			return false;
		}

		@Override
		public boolean zoom(float originalDistance, float currentDistance) {

			float ratio = originalDistance / currentDistance;
			camera.position.z = initialScale * ratio* Engine.getEngineConfig().height / 2;
			return false;
		}

		@Override
		public boolean pinch(Vector2 initialFirstPointer,
				Vector2 initialSecondPointer, Vector2 firstPointer,
				Vector2 secondPointer) {
			return false;
		}

		@Override
		public void update() {

			if (flinging) {
				velX *= 0.98f;
				velY *= 0.98f;
				camera.position.add(-velX * Gdx.graphics.getDeltaTime(), velY* Gdx.graphics.getDeltaTime(), 0);
				if (Math.abs(velX) < 0.01f)
					velX = 0;
				if (Math.abs(velY) < 0.01f)
					velY = 0;
			}

		}

		@Override
		public boolean tap(float x, float y, int count, int pointer, int button) {
			return false;
		}

		@Override
		public boolean fling(float velocityX, float velocityY, int pointer,
				int button) {
			flinging = true;
			velX = camera.position.z / (Engine.getEngineConfig().height / 2)
					* velocityX * 0.5f;
			velY = camera.position.z / (Engine.getEngineConfig().height / 2)
					* velocityY * 0.5f;
			return false;
		}

		@Override
		public boolean longPress(float x, float y) {
			return false;
		}
	}

}
