package aurelienribon.tweenengine.equations;

import aurelienribon.tweenengine.TweenEquation;

/**
 * Easing equation based on Robert Penner's work:
 * http://robertpenner.com/easing/
 * @author Aurelien Ribon | http://www.aurelienribon.com/
 */
public abstract class Quart extends TweenEquation {
	public static final Quart IN = new Quart() {
		@Override
		public final float compute(float t, float b, float c, float d) {
			return c*(t/=d)*t*t*t + b;
		}

		@Override
		public String toString() {
			return "Quart.IN";
		}
	};

	public static final Quart OUT = new Quart() {
		@Override
		public final float compute(float t, float b, float c, float d) {
			return -c * ((t=t/d-1)*t*t*t - 1) + b;
		}

		@Override
		public String toString() {
			return "Quart.OUT";
		}
	};

	public static final Quart INOUT = new Quart() {
		@Override
		public final float compute(float t, float b, float c, float d) {
			if ((t/=d/2) < 1) return c/2*t*t*t*t + b;
			return -c/2 * ((t-=2)*t*t*t - 2) + b;
		}

		@Override
		public String toString() {
			return "Quart.INOUT";
		}
	};
}