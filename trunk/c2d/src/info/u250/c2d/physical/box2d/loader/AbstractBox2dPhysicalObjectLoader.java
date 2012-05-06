package info.u250.c2d.physical.box2d.loader;

import java.util.HashMap;
import java.util.Map;
/**
 * the abstract box2d physical object loader . which contains a bodies map {@link #bodies} and the read method is implement at the subclass . 
 * you can obtain all the bodies by {@link #getBodies()}
 * @author lycying@gmail.com
 */
public abstract class AbstractBox2dPhysicalObjectLoader {
	/** the read method , will read the file and put all physical objects to the {@link #bodies}*/
	public abstract void read(String file);
	
	protected Map<String, AbstractBox2dPhysicalObjectModel> bodies = new HashMap<String, AbstractBox2dPhysicalObjectModel>();
	public Map<String, AbstractBox2dPhysicalObjectModel> getBodies(){
		return bodies;
	} 
}
