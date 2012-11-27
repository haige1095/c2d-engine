package info.u250.c2d.updatable;

import info.u250.c2d.engine.C2dCamera;

import com.badlogic.gdx.Gdx;

/**
 * In some scenarios, you may need to enlarge or shrink your entire field of vision. 
 * This event is to achieve this effect.
 * @see {@link com.badlogic.gdx.graphics.OrthographicCamera#zoom}
 * @author lycying@gmail.com
 */
public  class ZoomCameraEvent extends PeriodUpdatable{
	/**
	 * in the libgdx , if zoomOutRadio is less than 1 , it means zoom out
	 * but at this zoomOutRadio must lager than 1
	 */
	protected float zoomOutRadio = 1;
	
	/**
	 * how much zoom step every delta time 
	 */
	private float zoomStep = 0;
	
	protected C2dCamera camera;


	/**
	 * @param zoomOutRadio  this is large than 1 , such as you give 5 
	 * @param duration how long to reach the zoom radio {@link #zoomOutRadio}
	 */
	public ZoomCameraEvent(C2dCamera camera,float zoomOutRadio,float duration){
		if(zoomOutRadio<1){
			Gdx.app.log("ERROR", "not allowed to zoomin,zoomOutRadio must lager than 1 ");
			System.exit(0);
		}
		this.camera = camera;
		this.zoomOutRadio = 1/zoomOutRadio;
		this.duration = duration;
		
	}

	@Override
	public void update(float delta){
		if(start){
			if( (this.zoomStep>=0 && this.zoomOutRadio<=this.camera.zoom )
					|| this.zoomStep<=0 && this.zoomOutRadio>=this.camera.zoom){
				this.camera.zoom = this.camera.zoom - this.zoomStep*delta;
				this.render(delta);
			}else{
				//disable it
				start = false;
				this.end();
			}
		}
	}


	@Override
	public void go(){
		if(this.duration!=0){
			this.zoomStep = ( this.camera.zoom - this.zoomOutRadio )/this.duration;
		}else{
			this.zoomStep = 0;
		}
		this.start = true;
		
		this.begin();
	}
	

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
	public String toString() {
		return "ZoomCameraEvent [zoomOutRadio=" + zoomOutRadio + ", zoomStep="
				+ zoomStep + ", duration=" + duration + ", start=" + start
				+ "]";
	}
}

