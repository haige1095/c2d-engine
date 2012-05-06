package info.u250.c2d.physical.box2d.loader.cbt.serializers;

import info.u250.c2d.physical.box2d.loader.cbt.CbtWorldReader;
import info.u250.c2d.physical.box2d.loader.cbt.data.RevoluteJointData;

import com.badlogic.gdx.utils.Json;
/**
 * @author lycying@gmail.com
 */
public class RevoluteointSerializer implements Json.Serializer<RevoluteJointData>{
	CbtWorldReader worldData;
	public RevoluteointSerializer(CbtWorldReader worldData){
		this.worldData = worldData;
	}
	@SuppressWarnings({"rawtypes" })
	@Override
	public void write(Json json, RevoluteJointData object,  Class knownType) {
		json.writeObjectStart();
		json.writeValue("localAnchorAx", object.localAnchorA.x);
		json.writeValue("localAnchorAy", object.localAnchorA.y);	
	
		json.writeValue("lowerAngle", object.lowerAngle);
		json.writeValue("upperAngle", object.upperAngle);
		
		json.writeValue("maxMotorTorque", object.maxMotorTorque);
		json.writeValue("motorSpeed", object.motorSpeed);
		
		json.writeValue("enableLimit", object.enableLimit);
		json.writeValue("enableMotor", object.enableMotor);
		
		json.writeValue("type", "revolute");
		json.writeValue("bodyA", object.bodyA.name);
		json.writeValue("bodyB", object.bodyB.name);
		json.writeValue("name", object.name);
		json.writeObjectEnd();
	}

	@SuppressWarnings({ "rawtypes" })
	@Override
	public RevoluteJointData read(Json json, Object jsonData, Class type) {
		final float localAnchorAx = json.readValue("localAnchorAx", Float.class,jsonData);
		final float localAnchorAy = json.readValue("localAnchorAy", Float.class,jsonData);
		
		
		final float upperAngle = json.readValue("upperAngle", Float.class,jsonData);
		final float lowerAngle = json.readValue("lowerAngle", Float.class,jsonData);
		
		final float maxMotorTorque = json.readValue("maxMotorTorque", Float.class,jsonData);
		final float motorSpeed = json.readValue("motorSpeed", Float.class,jsonData);
		
		final boolean enableLimit = json.readValue("enableLimit", Boolean.class,jsonData);
		final boolean enableMotor = json.readValue("enableMotor", Boolean.class,jsonData);
		
		
		final String bodyA = json.readValue("bodyA", String.class,jsonData);
		final String bodyB = json.readValue("bodyB", String.class,jsonData);
		final String name = json.readValue("name", String.class,jsonData);
		
		RevoluteJointData data = new RevoluteJointData();
		data.localAnchorA.set(localAnchorAx,localAnchorAy);
		data.enableLimit = enableLimit;
		data.enableMotor = enableMotor;
		data.maxMotorTorque = maxMotorTorque;
		data.motorSpeed = motorSpeed;
		data.upperAngle = upperAngle;
		data.lowerAngle = lowerAngle;
		data.bodyA = worldData.findBodyDataByName(bodyA);
		data.bodyB = worldData.findBodyDataByName(bodyB);
		data.name = name;
		
		return data;
	}
}
