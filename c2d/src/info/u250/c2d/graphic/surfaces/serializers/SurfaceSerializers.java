package info.u250.c2d.graphic.surfaces.serializers;

import info.u250.c2d.graphic.surfaces.data.SurfaceData;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.ObjectMap;
@SuppressWarnings("rawtypes")
public class SurfaceSerializers implements Json.Serializer<SurfaceData>{
	@Override
	public void write(Json json, SurfaceData object, Class knownType) {
		json.writeObjectStart();
		json.writeValue("name", object.name);
		json.writeValue("texture", object.texture);
		json.writeValue("primitiveType", object.primitiveType);
		json.writeValue("points", object.points);
		json.writeObjectEnd();
	}

	@SuppressWarnings("unchecked")
	@Override
	public SurfaceData read(Json json, Object jsonData, Class type) {
		final String name = json.readValue("name",String.class,jsonData);
		final String texture = json.readValue("texture",String.class,jsonData);
		final int primitiveType = json.readValue("primitiveType",Float.class,jsonData).intValue();
		final SurfaceData data = new SurfaceData();
		data.name = name;
		data.texture = texture;
		data.primitiveType = primitiveType;
		Array<ObjectMap<String, ?>> bodysData = (Array<ObjectMap<String, ?>>)json.readValue("points",Array.class, jsonData);
		for(ObjectMap<String, ?> bodyData:bodysData){
			data.points.add(json.readValue(Vector2.class, bodyData));
		}
		return data;
	}

}