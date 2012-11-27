package info.u250.c2d.engine;

import info.u250.c2d.updatable.ShakeCameraEvent;

import com.badlogic.gdx.graphics.OrthographicCamera;
/**
 * @author lycying@gmail.com
 */
public class C2dCamera extends OrthographicCamera {
	private float rotate;
	
	public float getRotate() {
		return rotate;
	}

	public void setRotate(float rotate) {
		this.rotate = rotate;
	}

	public C2dCamera(float width,float height){
		super(width, height);
		this.position.set(width/2, height/2,0);
	}
	
	public void resize(float width,float height){
		this.viewportWidth = width;
		this.viewportHeight = height;
		this.position.set(width/2, height/2,0);
		this.update();
	}
	
	private static final String UPDATE_SHAKE_NAME = "_c2dCamera_shake_";
	/** 
	 * In many games, you may see some shaking effects, 
	 * such as earthquakes, such as a bomb explosion. This event is to achieve this effect.
	 *  You can provide cycle amplitude and frequency of vibration and other variables 
	 *  to get these kind of effects 
	 */
	public void shake(){
		Engine.addUpdatable(UPDATE_SHAKE_NAME, new ShakeCameraEvent(this,4, 4, 0.2f));
	}

}
