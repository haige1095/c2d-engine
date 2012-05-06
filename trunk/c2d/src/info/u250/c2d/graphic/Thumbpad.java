package info.u250.c2d.graphic;

import info.u250.c2d.engine.Engine;
import info.u250.c2d.engine.service.Renderable;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.GLCommon;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;

/**
 * How to use: create :
 * 
 * <pre>
 * float radius = Math.min(Constants.WIDTH, Constants.HEIGHT) / 7;
 * float offset = radius / 4;
 * thumbpad = new Thumbpad((int) radius, new Vector2(radius + offset, radius
 * 		+ offset), 0.0f);
 * </pre>
 * 
 * make sure use these methods : {@link #touchDown(int, int, int)}
 * {@link #touchUp(int, int, int)} {@link #touchDragged(int, int, int)}
 * 
 * 
 * To obtain the value of the thumbpad-:
 * {@link #getAmount()}*{@link #getAngle()}*More
 *   @author lycying@gmail.com
 */
public class Thumbpad implements Renderable{

	int radius;

	Vector2 centre;

	float zDepthCoord;

	public Thumbpad(int r, // pixels
			Vector2 c, // relative to left-bottom corner of the display
			float zDepthCoord) {

		radius = r;
		centre = c;
		this.zDepthCoord = zDepthCoord;
		createMeshes();
	}

	boolean hitTest(int x, int y) {
		// mouse/touch coordinates: relative to top-left corner of the display
		int xMin = (int) centre.x - radius;
		int xMax = (int) centre.x + radius;
		int yMin =  (int) centre.y - radius;
		int yMax =  (int) centre.y + radius;
		if (x < xMin || x > xMax || y > yMax || y < yMin) {
			this.stick();
			return false;
		}
		this.unstick();
		return true;
	}

	public boolean touchDown(int x, int y, int index) {
		if (hitTest(x, y)) {
			touchIndex = index;
			touchDragged(x, y, index);
			return true;
		}
		return false;
	}

	final Vector2 distance = new Vector2(0, 0);

	public boolean touchDragged(int x, int y, int index) {
		if (touchIndex != -1) {
			distance.x = x - centre.x;
			distance.y = ( y) - centre.y;
			angle = (float) (Math.atan2(distance.y, distance.x));
			amount = distance.len() / radius;

			if (amount > 1) {
				amount = 1;
			}
		}
		return hitTest(x, y);
	}

	void reset() {
		amount = 0;
		angle = 0;
	}

	boolean sticky = false;

	void stick() {
		sticky = true;
	}

	void unstick() {
		sticky = false;
		if (touchIndex == -1)
			reset();
	}

	void toggleStick() {
		if (sticky)
			unstick();
		else
			stick();
	}

	public boolean touchUp(int x, int y, int index) {
		touchIndex = -1;
		if (!sticky) {
			reset();
		}
		return hitTest(x, y);
	}

	int touchIndex = -1; // the single touch index currently acquired

	public int touchIndex() {
		return touchIndex;
	}

	// in radians: 0 -> 2PI, -0 -> -2PI
	// equivalent degrees: 0 -> 180 and -0 -> -180
	float angle = 0;

	public float getAngle() {
		return angle;
	}

	// along the radius, normalized [0..1]
	// (capped to 1, which is on the circle circumference boundary)
	float amount = 0;

	public float getAmount() {
		return amount;
	}

	private static final int NVERTICES_OUTER = 50;
	private static final int NVERTICES_INNER = 15;
	Mesh meshOuter;
	Mesh meshInner;

	final int INNER_OUTER_RATIO = 3;

	public void createMeshes() {
		meshOuter = new Mesh(true, NVERTICES_OUTER, NVERTICES_OUTER,
				new VertexAttribute(Usage.Position, 3,
						ShaderProgram.POSITION_ATTRIBUTE));
		meshOuter.setVertices(createCircleVertices(NVERTICES_OUTER, radius,
				radius));
		//
		meshInner = new Mesh(false, NVERTICES_INNER, NVERTICES_INNER,
				new VertexAttribute(Usage.Position, 3,
						ShaderProgram.POSITION_ATTRIBUTE));
		meshInner.setVertices(createCircleVertices(NVERTICES_INNER, radius
				/ INNER_OUTER_RATIO, radius / INNER_OUTER_RATIO));
	}

	public float[] createCircleVertices(int nVertices, int radiusX, int radiusY) {
		float[] vertices = new float[nVertices * 3];
		for (int i = 0; i < nVertices; i++) {
			float angle = (float) (1 / (float) nVertices * i * Math.PI * 2);
			vertices[i * 3] = (float) Math.sin(angle) * radiusX;
			vertices[i * 3 + 1] = (float) Math.cos(angle) * radiusY;
			vertices[i * 3 + 2] = zDepthCoord;
		}
		return vertices;
	}

	Matrix4 shaderMatrix;

	final float alpha = 0.5f;

	@Override
	public void render(float delta) {
		if (shaderMatrix == null) {
			shaderMatrix = new Matrix4();
			shaderMatrix.idt();
		}

		GLCommon gl = Gdx.gl;
		gl.glActiveTexture(GL10.GL_TEXTURE0 + 0);
		gl.glDisable(GL10.GL_TEXTURE_2D);
		gl.glEnable(GL10.GL_BLEND);
		gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
		if (!Gdx.graphics.isGL20Available()) {
			Gdx.gl10.glColor4f(1, 1, 1, 1);
		}

		if (sticky) {
			if (!Gdx.graphics.isGL20Available()) {
				Gdx.gl10.glColor4f(0.5f, 0.5f, 0, alpha);
			}
		} else {
			if (!Gdx.graphics.isGL20Available()) {
				Gdx.gl10.glColor4f(1, 1, 1, alpha);
			}
		}

		Engine.getDefaultCamera().apply(Gdx.gl10);
		Gdx.gl10.glPushMatrix();
		// Gdx.gl10.glMatrixMode(GL10.GL_MODELVIEW);
		// Gdx.gl10.glLoadIdentity();
		Gdx.gl10.glTranslatef(centre.x, centre.y, 0);

		meshOuter.render(GL10.GL_TRIANGLE_FAN, 0, NVERTICES_OUTER); // GL_LINE_LOOP

		Gdx.gl10.glPopMatrix();

		if (sticky) {
			Gdx.gl10.glColor4f(1, 0, 0, alpha);

		} else {
			Gdx.gl10.glColor4f(0, 0, 1, alpha);

		}

		float centerX = amount * radius * (float) Math.cos(angle) + centre.x;
		float centerY = amount * radius * (float) Math.sin(angle) + centre.y;

		Engine.getDefaultCamera().apply(Gdx.gl10);

		Gdx.gl10.glPushMatrix();
		// Gdx.gl10.glMatrixMode(GL10.GL_MODELVIEW);
		// Gdx.gl10.glLoadIdentity();
		Gdx.gl10.glTranslatef(centerX, centerY, 0);

		meshInner.render(GL10.GL_TRIANGLE_FAN, 0, NVERTICES_INNER);

		Gdx.gl10.glPopMatrix();
	}

}