package info.u250.c2d.physical.box2d.loader.cbt.serializers;

import info.u250.c2d.physical.box2d.loader.cbt.CbtWorldReader;
import info.u250.c2d.physical.box2d.loader.cbt.data.PulleyJointData;

import com.badlogic.gdx.utils.Json;
/**
 * @author lycying@gmail.com
 */
public class PulleyJointSerializer implements Json.Serializer<PulleyJointData>{
	CbtWorldReader worldData;
	public PulleyJointSerializer(CbtWorldReader worldData){
		this.worldData = worldData;
	}
	@SuppressWarnings({"rawtypes" })
	@Override
	public void write(Json json, PulleyJointData object,  Class knownType) {
		json.writeObjectStart();
		json.writeValue("localAnchorAx", object.localAnchorA.x);
		json.writeValue("localAnchorAy", object.localAnchorA.y);
		json.writeValue("localAnchorBx", object.localAnchorB.x);
		json.writeValue("localAnchorBy", object.localAnchorB.y);
		json.writeValue("groundAnchorAx", object.groundAnchorA.x);
		json.writeValue("groundAnchorAy", object.groundAnchorA.y);
		json.writeValue("groundAnchorBx", object.groundAnchorB.x);
		json.writeValue("groundAnchorBy", object.groundAnchorB.y);
		json.writeValue("ratio", object.ratio);
		json.writeValue("type", "pulley");
		json.writeValue("bodyA", object.bodyA.name);
		json.writeValue("bodyB", object.bodyB.name);
		json.writeValue("name", object.name);
		json.writeObjectEnd();
	}

	@SuppressWarnings({ "rawtypes" })
	@Override
	public PulleyJointData read(Json json, Object jsonData, Class type) {
		final float localAnchorAx = json.readValue("localAnchorAx", Float.class,jsonData);
		final float localAnchorAy = json.readValue("localAnchorAy", Float.class,jsonData);
		final float localAnchorBx = json.readValue("localAnchorBx", Float.class,jsonData);
		final float localAnchorBy = json.readValue("localAnchorBy", Float.class,jsonData);
		final float groundAnchorAx = json.readValue("groundAnchorAx", Float.class,jsonData);
		final float groundAnchorAy = json.readValue("groundAnchorAy", Float.class,jsonData);
		final float groundAnchorBx = json.readValue("groundAnchorBx", Float.class,jsonData);
		final float groundAnchorBy = json.readValue("groundAnchorBy", Float.class,jsonData);
		final float ratio = json.readValue("ratio", Float.class,jsonData);
		final String bodyA = json.readValue("bodyA", String.class,jsonData);
		final String bodyB = json.readValue("bodyB", String.class,jsonData);
		final String name = json.readValue("name", String.class,jsonData);
		
		PulleyJointData data = new PulleyJointData();
		data.localAnchorA.set(localAnchorAx,localAnchorAy);
		data.localAnchorB.set(localAnchorBx,localAnchorBy);
		data.groundAnchorA.set(groundAnchorAx,groundAnchorAy);
		data.groundAnchorB.set(groundAnchorBx,groundAnchorBy);
		data.ratio = ratio;
		data.bodyA = worldData.findBodyDataByName(bodyA);
		data.bodyB = worldData.findBodyDataByName(bodyB);
		data.name = name;
		
		return data;
	}
}