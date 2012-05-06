package info.u250.c2d.physical.box2d.loader.cbt.serializers;

import info.u250.c2d.physical.box2d.loader.cbt.CbtWorldReader;
import info.u250.c2d.physical.box2d.loader.cbt.data.WheelJointData;

import com.badlogic.gdx.utils.Json;
/**
 * @author lycying@gmail.com
 */
public class WheelJointSerializer implements Json.Serializer<WheelJointData>{
	CbtWorldReader worldData;
	public WheelJointSerializer(CbtWorldReader worldData){
		this.worldData = worldData;
	}
	@SuppressWarnings({"rawtypes" })
	@Override
	public void write(Json json, WheelJointData object,  Class knownType) {
		json.writeObjectStart();
		json.writeValue("localAnchorAx", object.localAnchorA.x);
		json.writeValue("localAnchorAy", object.localAnchorA.y);
		json.writeValue("localAxisAx", object.localAxisA.x);
		json.writeValue("localAxisAy", object.localAxisA.y);

		json.writeValue("frequencyHz", object.frequencyHz);
		json.writeValue("dampingRatio", object.dampingRatio);
		
		json.writeValue("maxMotorTorque", object.maxMotorTorque);
		json.writeValue("motorSpeed", object.motorSpeed);
		
		json.writeValue("enableMotor", object.enableMotor);
		
		json.writeValue("type", "wheel");
		json.writeValue("bodyA", object.bodyA.name);
		json.writeValue("bodyB", object.bodyB.name);
		json.writeValue("name", object.name);
		json.writeObjectEnd();
	}

	@SuppressWarnings({ "rawtypes" })
	@Override
	public WheelJointData read(Json json, Object jsonData, Class type) {
		final float localAnchorAx = json.readValue("localAnchorAx", Float.class,jsonData);
		final float localAnchorAy = json.readValue("localAnchorAy", Float.class,jsonData);
		
		final float localAxisAx = json.readValue("localAxisAx", Float.class,jsonData);
		final float localAxisAy = json.readValue("localAxisAy", Float.class,jsonData);
		
		final float frequencyHz = json.readValue("frequencyHz", Float.class,jsonData);
		final float dampingRatio = json.readValue("dampingRatio", Float.class,jsonData);
		
		final float maxMotorTorque = json.readValue("maxMotorTorque", Float.class,jsonData);
		final float motorSpeed = json.readValue("motorSpeed", Float.class,jsonData);
		
		final boolean enableMotor = json.readValue("enableMotor", Boolean.class,jsonData);
		
		
		final String bodyA = json.readValue("bodyA", String.class,jsonData);
		final String bodyB = json.readValue("bodyB", String.class,jsonData);
		final String name = json.readValue("name", String.class,jsonData);
		
		WheelJointData data = new WheelJointData();
		data.localAnchorA.set(localAnchorAx,localAnchorAy);
		data.localAxisA.set(localAxisAx,localAxisAy);
		data.enableMotor = enableMotor;
		data.maxMotorTorque = maxMotorTorque;
		data.motorSpeed = motorSpeed;
		data.frequencyHz = frequencyHz;
		data.dampingRatio = dampingRatio;
		data.bodyA = worldData.findBodyDataByName(bodyA);
		data.bodyB = worldData.findBodyDataByName(bodyB);
		data.name = name;
		
		return data;
	}
}
