package info.u250.c2d.engine.transitions;

import info.u250.c2d.engine.CoreProvider.TransitionType;
import info.u250.c2d.engine.Transition;

public class TransitionFactory {
	
	public static Transition getTransitionScene(TransitionType type){
		switch(type){
		case RotateWithZoomIn:
			return new TransitionSceneRotateWithZoomIn();
		case FlipHorizontal:
			return new TransitionSceneFlipHorizontal();
		case FlipVertical:
			return new TransitionSceneFlipVertical();
		case ZoomIn:
			return new TransitionSceneZoomIn();
		case Rotate:
			return new TransitionSceneRotate();
		case ZoomOut:
			return new TransitionSceneZoomOut();
		case Fade:
			return new TransitionSceneFade();
		case SlideInLeft:
			return new TransitionSceneSlideInLeft();
		case SlideInRight:
			return new TransitionSceneSlideInRight();
		case SlideInTop:
			return new TransitionSceneSlideInTop();
		case SlideInBottom:
			return new TransitionSceneSlideInBottom();
		case MoveInLeft:
			return new TransitionSceneMoveInLeft();
		case MoveInRight:
			return new TransitionSceneMoveInRight();
		case MoveInTop:
			return new TransitionSceneMoveInTop();
		case MoveInBottom:
			return new TransitionSceneMoveInBottom();
		case SplitRows:
			return new TransitionSceneSplitRows();
		case SplitCols:
			return new TransitionSceneSplitCols();
		case FadeWhite:
			return new TransitionSceneFadeWhite();
		default:
			return new TransitionSceneFade();
		}
	}
}
