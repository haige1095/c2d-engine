package info.u250.c2d.updatable;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;

/**  
 * NOTICE: this use the the @see com.badlogic.gdx.graphics.GL10#GL_LIGHT0, 
 * if you override this, will have no effect of course<br/>
 * 
 * NOTICE: you must keep set the opengles's light on to enable this effect at you render method , 
 * that is:<br/>
 * <pre>
 * // turns on lighting
	gl.glEnable(GL10.GL_LIGHTING);
 * </pre>
 * if you just want to affect the effect of the background , you should turn off this attribute, looks like this:
 * <pre>
 * gl.glDisable(GL10.GL_LIGHTING);
 * </pre>
 * 
 * <p>Use of GL_DIFFUSE light, to achieve the result that from day into night. 
 * The role of this event is from dawn to noon, from noon to night. 
 * In order to avoid too dark to see the clear rendering of the object, 
 * you need to set the value to control a lowerLight the lowest brightness. 
 * In addition, if the loop is set to true, then this process will always be going
 * </p>
 * @see {@link com.badlogic.gdx.graphics.GL10#GL_LIGHT0}
 * @see {@link com.badlogic.gdx.graphics.GL10#GL_DIFFUSE}
 * 
 * @author lycying@gmail.com
 */
public  class Day2NightEvent extends PeriodUpdatable{
	public Day2NightEvent
	(float keepDuration,float lowerLight,boolean loop){
		this.duration = 1/keepDuration;
		this.lowerLight   = lowerLight;
		this.keepDurationDelta = 1;
		this.loop = loop ;
	}
	
	private float lowerLight;
	private float keepDurationDelta;
	private boolean loop = true;
	
	@Override
	public void begin() {
		
	}
	@Override
	public void end() {
		
	}
	private boolean up = true;
	
	@Override
	public void go() {
		this.start = true;
		this.begin();
	}
	@Override
	public void update(float delta) {
		if(this.start){
			if(up){
				if(keepDurationDelta>=1){
					this.up = false;
				}
				keepDurationDelta += delta*this.duration;
			}else{
				if(this.keepDurationDelta<this.lowerLight){
					this.up = true;
					if(!loop){
						this.start = false;
					}
					this.end();
				}
				keepDurationDelta -= delta*this.duration;
			}
			
			if(Gdx.graphics.isGL20Available()){
				//TODO light gl20
			}else{
				GL10 gl = Gdx.gl10;
				gl.glEnable(GL10.GL_LIGHT0);
				gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_DIFFUSE, new float[]{keepDurationDelta, keepDurationDelta, keepDurationDelta, 1f}, 0);
			}
			
			this.render(delta);
		}
	}
	@Override
	public void render(float delta) {
	
	}
	@Override
	public String toString() {
		return "Day2NightEvent [lowerLight=" + lowerLight
				+ ", keepDurationDelta=" + keepDurationDelta + ", loop=" + loop
				+ ", up=" + up + ", duration=" + duration + ", start=" + start
				+ "]";
	}
	
}

