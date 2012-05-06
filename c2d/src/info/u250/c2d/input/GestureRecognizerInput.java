package info.u250.c2d.input;

import java.util.ArrayList;

import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.math.Vector2;
/**
 * Current we use the codes from : https://github.com/makism/DollarRecognizerLibGdx<br/>
 * more information at : http://depts.washington.edu/aimgroup/proj/dollar/<br/>
 * where the codes lies at c2d-ext-gestures
 * <br/>
 * Usage:
 * <pre>
 * 
 final GestureRecognizerInput input = new UnistrokeGestureRecognizerInput(
					new String[]{
							"data/gestures/rectangle.json",
							"data/gestures/triangle.json",
							"data/gestures/x.json",
					},new GestureRecognizerListener() {
						public void onMatch(String name, float score) {
							result = "Name:"+name+",Score:"+score;
						}
					}); 
 * </pre>
 * @author lycying@gmail.com
 */
public abstract class GestureRecognizerInput extends InputAdapter {
	public interface GestureRecognizerListener{
		public void onMatch(String name,float score);
	}
	public static class GestureRecognizerResult{
		public String name;
		public float score;
	}
	private GestureRecognizerListener  gestureRecognizerListener;
	private ArrayList<Vector2> originalPath;
	
	public GestureRecognizerInput(GestureRecognizerListener gestureRecognizerListener) {
		super();
		this.gestureRecognizerListener = gestureRecognizerListener;
		originalPath = new ArrayList<Vector2>();
	}
	
	@Override public boolean touchDown(int x, int y, int pointer, int button) {
		originalPath.add(new Vector2(x, y));

		return false;
	}

	@Override public boolean touchDragged(int x, int y, int pointer) {
		originalPath.add(new Vector2(x, y));

		return false;
	}

	@Override public boolean touchUp(int x, int y, int pointer, int button) {
		if (originalPath.size() >= 10) {
			originalPath.add(new Vector2(x, y));
			GestureRecognizerResult rs = this.result(originalPath);
			gestureRecognizerListener.onMatch(rs.name,rs.score);
		}

		originalPath.clear();

		return false;
	}
	
	protected abstract GestureRecognizerResult result(ArrayList<Vector2> originalPath);
	
}
