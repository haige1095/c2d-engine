package info.u250.c2d.updatable;

import info.u250.c2d.graphic.parallax.ParallaxGroup;

import com.badlogic.gdx.math.Vector2;

/**
 * The camera move upwards or backwards , to show different content of the camera.
 * @author lycying@gmail.com
 */
public class SpeedCameraEvent extends PeriodUpdatable {
	public SpeedCameraEvent(ParallaxGroup background,float keepDuration, Vector2 speed) {
		this.duration = keepDuration;
		this.speed = speed;
		this.background = background;
	}

	private Vector2 speed;
	protected float keepDurationDelta;

	protected ParallaxGroup background;
	
	@Override
	public void begin() {
	}

	@Override
	public void end() {
	}

	@Override
	public void render(float delta) {
	}

	@Override
	public void go() {
		this.start = true;
		this.background.setSpeed(this.speed.x, this.speed.y);
		this.begin();
	}

	protected void setSpeed(float xSpeed, float ySpeed) {
		this.background.setSpeed(xSpeed, ySpeed);
	}

	@Override
	public void update(float delta) {
		if (this.start) {
			if (keepDurationDelta < duration) {
				render(delta);
				keepDurationDelta += delta;
			} else {
				this.start = false;
				this.keepDurationDelta = 0;
				this.end();
			}
		}
	}

	@Override
	public String toString() {
		return "SpeedCameraEvent [speed=" + speed + ", keepDurationDelta="
				+ keepDurationDelta + ", duration=" + duration + ", start="
				+ start + "]";
	}
	
}
