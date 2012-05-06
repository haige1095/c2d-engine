package info.u250.c2d.physical.box2d.loader.cbt.serializers;

import info.u250.c2d.physical.box2d.loader.cbt.CbtWorldReader;
import info.u250.c2d.physical.box2d.loader.cbt.data.BoxData;
import info.u250.c2d.physical.box2d.loader.cbt.data.CircleData;
import info.u250.c2d.physical.box2d.loader.cbt.data.DistanceJointData;
import info.u250.c2d.physical.box2d.loader.cbt.data.FrictionJointData;
import info.u250.c2d.physical.box2d.loader.cbt.data.PolygonData;
import info.u250.c2d.physical.box2d.loader.cbt.data.PrismaticJointData;
import info.u250.c2d.physical.box2d.loader.cbt.data.PulleyJointData;
import info.u250.c2d.physical.box2d.loader.cbt.data.RevoluteJointData;
import info.u250.c2d.physical.box2d.loader.cbt.data.RopeJointData;
import info.u250.c2d.physical.box2d.loader.cbt.data.WeldJointData;
import info.u250.c2d.physical.box2d.loader.cbt.data.WheelJointData;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.ObjectMap;
/**
 * @author lycying@gmail.com
 */
public class CbtWorldSerializer implements Json.Serializer<CbtWorldReader>{
	CbtWorldReader worldData;
	public CbtWorldSerializer(CbtWorldReader worldData){
		this.worldData = worldData;
	}
	@SuppressWarnings({"rawtypes" })
	@Override
	public void write(Json json, CbtWorldReader object, Class knownType) {
		json.writeObjectStart();
		json.writeValue("bodys",object.bodyDatas);
		json.writeValue("joints",object.jointDatas);
		json.writeObjectEnd();
	}
	@SuppressWarnings({"rawtypes", "unchecked" })
	@Override
	public CbtWorldReader read(Json json, Object jsonData, Class type) {
		
		final Array<ObjectMap<String, ?>> bodysData = (Array<ObjectMap<String, ?>>) json.readValue("bodys", Array.class, jsonData);
		if(null!=bodysData)
		for(ObjectMap<String, ?> bodyData:bodysData){
			final String bodyType = json.readValue("type", String.class,bodyData);
			if(bodyType.equals("box"))
				worldData.bodyDatas.add(json.readValue(BoxData.class, bodyData));
			else if(bodyType.equals("circle"))
				worldData.bodyDatas.add(json.readValue(CircleData.class, bodyData));
			else if(bodyType.equals("polygon"))
				worldData.bodyDatas.add(json.readValue(PolygonData.class, bodyData));
		}

		final Array<ObjectMap<String, ?>> jointsData = (Array<ObjectMap<String, ?>>) json.readValue("joints", Array.class, jsonData);
		if(null!=jointsData)
		for(ObjectMap<String, ?> jointData:jointsData){
			final String jointType = json.readValue("type", String.class,jointData);
			if(jointType.equals("distance"))
				worldData.jointDatas.add(json.readValue(DistanceJointData.class, jointData));
			else if(jointType.equals("friction"))
				worldData.jointDatas.add(json.readValue(FrictionJointData.class, jointData));
			else if(jointType.equals("prismatic"))
				worldData.jointDatas.add(json.readValue(PrismaticJointData.class, jointData));
			else if(jointType.equals("pulley"))
				worldData.jointDatas.add(json.readValue(PulleyJointData.class, jointData));
			else if(jointType.equals("revolute"))
				worldData.jointDatas.add(json.readValue(RevoluteJointData.class, jointData));
			else if(jointType.equals("rope"))
				worldData.jointDatas.add(json.readValue(RopeJointData.class, jointData));
			else if(jointType.equals("weld"))
				worldData.jointDatas.add(json.readValue(WeldJointData.class, jointData));
			else if(jointType.equals("wheel"))
				worldData.jointDatas.add(json.readValue(WheelJointData.class, jointData));
		}
		return worldData;
	}
}
