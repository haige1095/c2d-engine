package info.u250.c2d.graphic.surfaces.serializers;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Json;
@SuppressWarnings("rawtypes")
public class Vector2Serializers implements Json.Serializer<Vector2>{
	@Override
	public void write(Json json, Vector2 object, Class knownType) {
		json.writeObjectStart();
		json.writeValue("x", object.x);
		json.writeValue("y", object.y);
		json.writeObjectEnd();
	}

	@Override
	public Vector2 read(Json json, Object jsonData, Class type) {
		final int x = json.readValue("x",Float.class,jsonData).intValue();
		final int y = json.readValue("y",Float.class,jsonData).intValue();
		final Vector2 data = new Vector2();
		data.x = x;
		data.y = y;
		return data;
	}

}