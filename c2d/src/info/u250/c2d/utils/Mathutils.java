package info.u250.c2d.utils;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;

public class Mathutils
{

	public static void posToPlane(Vector3 result, Vector3 pos, Vector3 normal)
	{
		float dp_n_n = normal.dot(normal);
		if (dp_n_n == 0) {
			throw new IllegalArgumentException("Normal is too small!");
		}
		float dp_p_n = pos.dot(normal);
		result.set(normal);
		result.mul(-dp_p_n);
		result.div(dp_n_n);
		result.add(pos);
	}

	// Returns angle between two vectors. The result is thus [0 - 180]
	public static float angleBetweenVectors(Vector3 v1, Vector3 v2)
	{
		float v1_len = v1.len();
		if (v1_len == 0) return 0;
		float v2_len = v2.len();
		if (v2_len == 0) return 0;

		v3tmp1.set(v1);
		v3tmp2.set(v2);
		v3tmp1.div(v1_len);
		v3tmp2.div(v2_len);

		float dp_v1n_v2n = v3tmp1.dot(v3tmp2);

		float radians = (float)Math.acos(dp_v1n_v2n);
		if (Float.isNaN(radians)) {
			v3tmp1.add(v3tmp2);
			if (v3tmp1.len2() > 1) {
				return 0;
			} else {
				return 180;
			}
		}
		return MathUtils.radiansToDegrees * radians;
	}

	// Returns the amount of right hand rotation that needs to be added
	// to vector #1 so it would become vector #2 when the rotation is
	// done at given plane. The result is between [-180 - 180]. Note! Positions
	// do not need to be at the given plane, the result is always their real
	// angle in 3D space. The plane is used only to determine if the angle
	// should be negative or positive.
	public static float angleAtPlane(Vector3 v1, Vector3 v2, Vector3 normal)
	{
		// Get rotation between vectors in case where there is no plane
		float angle = angleBetweenVectors(v1, v2);
		// Check which side the rotation is
		v3tmp1.set(v1);
		v3tmp1.crs(v2);
		float dp = normal.dot(v3tmp1);
		if (dp < 0) {
			angle = -angle;
		}
		return angle;
	}

	public static float getAngle(float x, float y)
	{
		return MathUtils.atan2(y, x) * MathUtils.radiansToDegrees;
	}

	// Calculates average of angles and returns it in range [-180°, 180°]. If
	// no range can be calculated, then returns value that is bigger than 360°.
	public static float calculateAverageAngle(float[] angles)
	{
		int size = angles.length;
		assert size > 0;

		float angle;

		// Find minimum and maximum angles
		angle = fixAngle(angles[0]);
		float min_angle = angle;
		float max_angle = angle;
		for (int angle_id = 1; angle_id < size; angle_id++) {
			angle = fixAngle(angles[angle_id]);
			if (angle < min_angle) min_angle = angle;
			if (angle > max_angle) max_angle = angle;
		}

		// Check if all values should be turned 180°.
		boolean turn180;
		if (max_angle - min_angle > 180) {
			turn180 = true;
		} else {
			turn180 = false;
		}

		// Now calculate average angle, and turn 180° if necessary. Also
		// calculate new minimum and maximum. If again bigger than 180°
		// difference is got, then it is not possible to calculate result.
		if (turn180) {
			angle = fixAngle(angles[0] + 180);
		} else {
			angle = fixAngle(angles[0]);
		}
		min_angle = angle;
		max_angle = angle;
		float average_angle = angle;
		for (int angle_id = 1; angle_id < size; angle_id++) {
			if (turn180) {
				angle = fixAngle(angles[angle_id] + 180);
			} else {
				angle = fixAngle(angles[angle_id]);
			}
			if (angle < min_angle) min_angle = angle;
			if (angle > max_angle) max_angle = angle;
			average_angle += angle;
		}
		if (max_angle - min_angle > 180) {
			return 9999f;
		}

		average_angle /= size;

		if (turn180) {
			average_angle = fixAngle(average_angle + 180);
		}

		return average_angle;
	}

	// Makes angle to be at the range [-180°, 180°]
	public static float fixAngle(float angle)
	{
		if (angle > 180) {
			angle = ((angle + 180) % 360) - 180;
		} else if (angle < -180) {
			angle = ((angle - 180) % 360) + 180;
		}
		assert angle >= -180 && angle <= 180;
		return angle;
	}

	// Converts Field Of View from y to x. FOV is measure from top plane to
	// bottom plane, so max FOV is less than 180°.
	public static float fovYtoFovX(float fov_y, float screen_width, float screen_height)
	{
		float aspect_ratio = screen_width / screen_height;
		float viewplane_height = (float)(Math.tan(Math.toRadians(fov_y / 2)));
		float viewplane_width = viewplane_height * aspect_ratio;
		float fov_x = (float)Math.toDegrees(Math.atan(viewplane_width)) * 2;
		return fov_x;
	}

	// Temporary variables. These are used by multiple methods, so
	// do not expect that value is kept if you do internal call!
	// TODO: Get rid of these so things can be done in multiple threads!
	private static final Vector3 v3tmp1 = new Vector3();
	private static final Vector3 v3tmp2 = new Vector3();

}
