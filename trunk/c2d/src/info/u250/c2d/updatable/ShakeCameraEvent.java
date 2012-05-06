package info.u250.c2d.updatable;

import info.u250.c2d.engine.C2dCamera;

import com.badlogic.gdx.Gdx;

/** 
 * In many games, you may see some shaking effects, 
 * such as earthquakes, such as a bomb explosion. This event is to achieve this effect.
 *  You can provide cycle amplitude and frequency of vibration and other variables 
 *  to get these kind of effects 
 *@author lycying@gmail.com
 */
public  class ShakeCameraEvent extends PeriodUpdatable{
	protected float shakeDurationCounter = 0;
	private int shakeTimes = 0;
	protected float amplitude;
	protected float period ;
	protected C2dCamera camera;
	private float syncAmplitude = 0;
	/**
	 * @param amplitude  1-10 is better if your device is 800x480
	 * @param frequency  1-10 is better
	 * @param shakeDuration how many seconds to keep shaking
	 */
	public ShakeCameraEvent(C2dCamera camera,float amplitude,float frequency,float shakeDuration){
		if(frequency%2!=0){
			Gdx.app.log("ERROR", "frequency must be div by 2");
			System.exit(0);
		}
		this.camera = camera;
		this.amplitude = amplitude;
		this.duration = shakeDuration;
		this.period = frequency;
	}
	
	
	@Override
	public  void update(float delta){

		if(this.start){
			
			if(this.shakeDurationCounter<=duration){
				shakeTimes++;
				if(shakeTimes%period>=period/2){
					this.camera.position.x -= this.amplitude;
					this.camera.position.y -= this.amplitude;
					
					syncAmplitude -= amplitude;
				}else{
					this.camera.position.x += this.amplitude;
					this.camera.position.y += this.amplitude;
					
					syncAmplitude += amplitude;
				}
				
				shakeDurationCounter+=delta;
				
				this.render(delta);
			}else{
				this.start = false;
				this.shakeDurationCounter = 0;
				this.camera.position.x -= this.syncAmplitude;
				this.camera.position.y -= this.syncAmplitude;
				this.syncAmplitude = 0;
				this.end();
			}
			
		}
	}
	
	@Override
	public void go(){
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
		return "ShakeCameraEvent [duration=" + duration
				+ ", shakeDurationCounter=" + shakeDurationCounter
				+ ", shakeTimes=" + shakeTimes + ", amplitude=" + amplitude
				+ ", period=" + period + ", camera=" + camera
				+ ", syncAmplitude=" + syncAmplitude + ", start=" + start + "]";
	}

	
	
	
}

