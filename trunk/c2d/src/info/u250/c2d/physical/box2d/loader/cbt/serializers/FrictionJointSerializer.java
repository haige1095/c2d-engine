package info.u250.c2d.physical.box2d.loader.cbt.serializers;

import info.u250.c2d.physical.box2d.loader.cbt.CbtWorldReader;
import info.u250.c2d.physical.box2d.loader.cbt.data.FrictionJointData;

import com.badlogic.gdx.utils.Json;
/**
 * @author lycying@gmail.com
 */
public class FrictionJointSerializer implements Json.Serializer<FrictionJointData>{
	CbtWorldReader worldData;
	public FrictionJointSerializer(CbtWorldReader worldData){
		this.worldData = worldData;
	}
	@SuppressWarnings({"rawtypes" })
	@Override
	public void write(Json json, FrictionJointData object,  Class knownType) {
		json.writeObjectStart();
		json.writeValue("localAnchorAx", object.localAnchorA.x);
		json.writeValue("localAnchorAy", object.localAnchorA.y);
		
		json.writeValue("maxForce", object.maxForce);
		json.writeValue("maxTorque", object.maxTorque);
		
		json.writeValue("type", "friction");
		json.writeValue("bodyA", object.bodyA.name);
		json.writeValue("bodyB", object.bodyB.name);
		json.writeValue("name", object.name);
		json.writeObjectEnd();
	}

	@SuppressWarnings({ "rawtypes" })
	@Override
	public FrictionJointData read(Json json, Object jsonData, Class type) {
		final float localAnchorAx = json.readValue("localAnchorAx", Float.class,jsonData);
		final float localAnchorAy = json.readValue("localAnchorAy", Float.class,jsonData);
		
		final float maxForce = json.readValue("maxForce", Float.class,jsonData);
		final float maxTorque = json.readValue("maxTorque", Float.class,jsonData);
		final String bodyA = json.readValue("bodyA", String.class,jsonData);
		final String bodyB = json.readValue("bodyB", String.class,jsonData);
		final String name = json.readValue("name", String.class,jsonData);
		
		FrictionJointData data = new FrictionJointData();
		data.localAnchorA.set(localAnchorAx,localAnchorAy);
		
		data.maxForce = maxForce;
		data.maxTorque = maxTorque;
		data.bodyA = worldData.findBodyDataByName(bodyA);
		data.bodyB = worldData.findBodyDataByName(bodyB);
		data.name = name;
		
		return data;
	}
}
