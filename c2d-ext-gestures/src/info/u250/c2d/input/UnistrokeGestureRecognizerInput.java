package info.u250.c2d.input;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.madroid.input.gestures.MatchingGesture;
import com.madroid.input.gestures.ProtractorGestureRecognizer;

public class UnistrokeGestureRecognizerInput extends GestureRecognizerInput {
	private ProtractorGestureRecognizer recognizer;
	
	public UnistrokeGestureRecognizerInput(String[] gestureFiles,
			GestureRecognizerListener gestureRecognizerListener) {
		super(gestureRecognizerListener);
		recognizer = new ProtractorGestureRecognizer();
		for(String file:gestureFiles){
			recognizer.addGestureFromFile(Gdx.files.internal(file));
		}
	}

	@Override
	protected GestureRecognizerResult result(ArrayList<Vector2> originalPath) {
		MatchingGesture match = recognizer.Recognize(originalPath);
		GestureRecognizerResult rs = new GestureRecognizerResult(); 
		if(null!=match){
			rs.name = match.getGesture().getName();
			rs.score = match.getScore();
		}
		return rs;
	}

}
