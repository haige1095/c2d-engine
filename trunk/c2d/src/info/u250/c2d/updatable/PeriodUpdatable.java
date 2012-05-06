package info.u250.c2d.updatable;

import info.u250.c2d.engine.service.Renderable;
import info.u250.c2d.engine.service.Updatable;


/**
 * This interface is prepare for muti control of the camara 
 * @author lycying@gmail.com
 */
public abstract class PeriodUpdatable implements Updatable,Renderable{
	protected float duration;
	/**
	 * @return  if the event is invoke or not , when it has stopped , will be false
	 */
	protected boolean start = false;
	/**
	 * When the event begin , will call this method
	 */
	public abstract void begin();
	/**
	 * When the event reach the end , will call this method
	 */
	public abstract void end();
	/**
	 * Every frame during the event , this method will be called ! its no use in this state , 
	 * but you can override it at the sub class to archive some sync effect 
	 * invoke the event 
	 * @param delta
	 */
	public abstract void update(float delta);
	
	public abstract void go();
	
	public boolean isStart() {
		return start;
	}
	public void setStart(boolean start) {
		this.start = start;
	}
}
