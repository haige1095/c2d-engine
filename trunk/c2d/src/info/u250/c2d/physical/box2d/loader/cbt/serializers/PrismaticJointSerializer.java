package info.u250.c2d.physical.box2d.loader.cbt.serializers;

import info.u250.c2d.physical.box2d.loader.cbt.CbtWorldReader;
import info.u250.c2d.physical.box2d.loader.cbt.data.PrismaticJointData;

import com.badlogic.gdx.utils.Json;
/**
 * @author lycying@gmail.com
 */
public class PrismaticJointSerializer implements Json.Serializer<PrismaticJointData>{
	CbtWorldReader worldData;
	public PrismaticJointSerializer(CbtWorldReader worldData){
		this.worldData = worldData;
	}
	@SuppressWarnings({"rawtypes" })
	@Override
	public void write(Json json, PrismaticJointData object,  Class knownType) {
		json.writeObjectStart();
		json.writeValue("localAnchorAx", object.localAnchorA.x);
		json.writeValue("localAnchorAy", object.localAnchorA.y);
		json.writeValue("localAxisAx", object.localAxisA.x);
		json.writeValue("localAxisAy", object.localAxisA.y);
	
		json.writeValue("lowerTranslation", object.lowerTranslation);
		json.writeValue("upperTranslation", object.upperTranslation);
		
		json.writeValue("maxMotorForce", object.maxMotorForce);
		json.writeValue("motorSpeed", object.motorSpeed);
		
		json.writeValue("enableLimit", object.enableLimit);
		json.writeValue("enableMotor", object.enableMotor);
		
		json.writeValue("type", "prismatic");
		json.writeValue("bodyA", object.bodyA.name);
		json.writeValue("bodyB", object.bodyB.name);
		json.writeValue("name", object.name);
		json.writeObjectEnd();
	}

	@SuppressWarnings({ "rawtypes" })
	@Override
	public PrismaticJointData read(Json json, Object jsonData, Class type) {
		final float localAnchorAx = json.readValue("localAnchorAx", Float.class,jsonData);
		final float localAnchorAy = json.readValue("localAnchorAy", Float.class,jsonData);
		
		final float localAxisAx = json.readValue("localAxisAx", Float.class,jsonData);
		final float localAxisAy = json.readValue("localAxisAy", Float.class,jsonData);
		
		final float upperTranslation = json.readValue("upperTranslation", Float.class,jsonData);
		final float lowerTranslation = json.readValue("lowerTranslation", Float.class,jsonData);
		
		final float maxMotorForce = json.readValue("maxMotorForce", Float.class,jsonData);
		final float motorSpeed = json.readValue("motorSpeed", Float.class,jsonData);
		
		final boolean enableLimit = json.readValue("enableLimit", Boolean.class,jsonData);
		final boolean enableMotor = json.readValue("enableMotor", Boolean.class,jsonData);
		
		
		final String bodyA = json.readValue("bodyA", String.class,jsonData);
		final String bodyB = json.readValue("bodyB", String.class,jsonData);
		final String name = json.readValue("name", String.class,jsonData);
		
		PrismaticJointData data = new PrismaticJointData();
		data.localAnchorA.set(localAnchorAx,localAnchorAy);
		data.localAxisA.set(localAxisAx,localAxisAy);
		data.enableLimit = enableLimit;
		data.enableMotor = enableMotor;
		data.maxMotorForce = maxMotorForce;
		data.motorSpeed = motorSpeed;
		data.upperTranslation = upperTranslation;
		data.lowerTranslation = lowerTranslation;
		data.bodyA = worldData.findBodyDataByName(bodyA);
		data.bodyB = worldData.findBodyDataByName(bodyB);
		data.name = name;
		
		return data;
	}
}
