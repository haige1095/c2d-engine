package info.u250.c2d.engine;

import info.u250.c2d.updatable.ShakeCameraEvent;

import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.math.Vector3;
/**
 * @author lycying@gmail.com
 */
public class C2dCamera extends PerspectiveCamera {
	private float angleX = 0;
	private float angleY = 0;
	private float angleZ = 0;
	private Vector3 orbitReturnVector = new Vector3();
	private Vector3 lookAt = new Vector3();
	
	
	public C2dCamera(float width,float height){
		this.viewportWidth = width;
		this.viewportHeight = height;
		this.position.set(width/2, height/2,height/2);
		this.near = 1;
		this.far = height;
		this.lookAt.set(width/2,height/2,0);
		this.lookAt(lookAt.x, lookAt.y, lookAt.z);
		this.fieldOfView = 90;//not 67 now
	}
	
	public void resize(float width,float height){
		this.viewportWidth = width;
		this.viewportHeight = height;
		this.position.set(width/2, height/2,height/2);
		this.near = 1;
		this.far = height;
		this.lookAt.set(width/2,height/2,0);
		this.lookAt(lookAt.x, lookAt.y, lookAt.z);
		this.fieldOfView = 90;//not 67 now
		
		final float zoom = this.getZoom();
		this.setZoom(zoom);
	}
	
	public void setZoom(float zoom){
		this.position.z = this.viewportHeight/2*zoom;
//		this.fieldOfView = 90/zoom;
	}
	public float getZoom(){
		return  this.position.z/(this.viewportHeight/2);
//		return 90f/this.fieldOfView;
	}
	
	public float getAngleX() {
		return angleX;
	}
	public void setAngleX(float angleX) {
		float orbitRadius = lookAt.dst(this.position);
		this.position.set(lookAt);
		this.rotate( angleX-this.angleX, 1, 0, 0);
		orbitReturnVector.set(this.direction.tmp().mul(-orbitRadius));
		this.translate(orbitReturnVector.x, orbitReturnVector.y, orbitReturnVector.z);
	
		this.angleX = angleX;
	}
	public float getAngleY() {
		return angleY;
	}
	public void setAngleY(float angleY) {
		 float orbitRadius = lookAt.dst(this.position);
		 this.position.set(lookAt);
		 this.rotate(angleY-this.angleY, 0, 1, 0);
		 orbitReturnVector.set(this.direction.tmp().mul(-orbitRadius));
		 this.translate(orbitReturnVector.x, orbitReturnVector.y, orbitReturnVector.z);

		this.angleY = angleY;
	}
	public float getAngleZ() {
		return angleZ;
	}
	public void setAngleZ(float angleZ) {	
		float orbitRadius = lookAt.dst(this.position);
		this.position.set(lookAt);
		this.rotate( angleZ-this.angleZ, 0, 0, 1);
		orbitReturnVector.set(this.direction.tmp().mul(-orbitRadius));
		this.translate(orbitReturnVector.x, orbitReturnVector.y, orbitReturnVector.z);
	
		this.angleZ = angleZ;
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
