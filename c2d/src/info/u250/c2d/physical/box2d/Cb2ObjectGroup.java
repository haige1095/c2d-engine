package info.u250.c2d.physical.box2d;

import info.u250.c2d.engine.service.Renderable;

import java.util.Iterator;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.utils.Array;
/**
 * @author lycying@gmail.com
 */
public class Cb2ObjectGroup extends Array<Cb2Object> implements Renderable{
	@Override
	public void render(float delta) {
		final Iterator<Cb2Object> it = this.iterator();
		while(it.hasNext())
			it.next().render(delta);
	}

	public Cb2Object findByBody(Body body){
		final Iterator<Cb2Object> it = this.iterator();
		while(it.hasNext()){
			Cb2Object obj = it.next();
			if(obj.data.body == body ) return obj;
		}
		return null;
	}
}
