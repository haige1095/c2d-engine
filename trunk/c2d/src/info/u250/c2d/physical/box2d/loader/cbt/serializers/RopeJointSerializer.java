package info.u250.c2d.physical.box2d.loader.cbt.serializers;

import info.u250.c2d.physical.box2d.loader.cbt.CbtWorldReader;
import info.u250.c2d.physical.box2d.loader.cbt.data.RopeJointData;

import com.badlogic.gdx.utils.Json;
/**
 * @author lycying@gmail.com
 */
public class RopeJointSerializer implements Json.Serializer<RopeJointData>{
	CbtWorldReader worldData;
	public RopeJointSerializer(CbtWorldReader worldData){
		this.worldData = worldData;
	}
	@SuppressWarnings({"rawtypes" })
	@Override
	public void write(Json json, RopeJointData object,  Class knownType) {
		json.writeObjectStart();
		json.writeValue("localAnchorAx", object.localAnchorA.x);
		json.writeValue("localAnchorAy", object.localAnchorA.y);
		json.writeValue("localAnchorBx", object.localAnchorB.x);
		json.writeValue("localAnchorBy", object.localAnchorB.y);
		json.writeValue("maxLength", object.maxLength);
		json.writeValue("type", "rope");
		json.writeValue("bodyA", object.bodyA.name);
		json.writeValue("bodyB", object.bodyB.name);
		json.writeValue("name", object.name);
		json.writeObjectEnd();
	}

	@SuppressWarnings({ "rawtypes" })
	@Override
	public RopeJointData read(Json json, Object jsonData, Class type) {
		final float localAnchorAx = json.readValue("localAnchorAx", Float.class,jsonData);
		final float localAnchorAy = json.readValue("localAnchorAy", Float.class,jsonData);
		final float localAnchorBx = json.readValue("localAnchorBx", Float.class,jsonData);
		final float localAnchorBy = json.readValue("localAnchorBy", Float.class,jsonData);
		final float maxLength = json.readValue("maxLength", Float.class,jsonData);
		final String bodyA = json.readValue("bodyA", String.class,jsonData);
		final String bodyB = json.readValue("bodyB", String.class,jsonData);
		final String name = json.readValue("name", String.class,jsonData);
		
		RopeJointData data = new RopeJointData();
		data.localAnchorA.set(localAnchorAx,localAnchorAy);
		data.localAnchorB.set(localAnchorBx,localAnchorBy);
		data.maxLength = maxLength;
		data.bodyA = worldData.findBodyDataByName(bodyA);
		data.bodyB = worldData.findBodyDataByName(bodyB);
		data.name = name;
		
		return data;
	}
}
