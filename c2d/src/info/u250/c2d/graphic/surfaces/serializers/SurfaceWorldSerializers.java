package info.u250.c2d.graphic.surfaces.serializers;

import info.u250.c2d.graphic.surfaces.SurfaceWorldReader;
import info.u250.c2d.graphic.surfaces.data.SurfaceData;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.ObjectMap;
@SuppressWarnings("rawtypes")
public class SurfaceWorldSerializers implements Json.Serializer<SurfaceWorldReader>{

	SurfaceWorldReader surfaceWorldReader;
	public SurfaceWorldSerializers(SurfaceWorldReader surfaceWorldReader){
		this.surfaceWorldReader = surfaceWorldReader;
	}
	@Override
	public void write(Json json, SurfaceWorldReader object, Class knownType) {
		json.writeObjectStart();
		json.writeValue("surfaces",object.surfaces);
		json.writeObjectEnd();
	}

	@SuppressWarnings("unchecked")
	@Override
	public SurfaceWorldReader read(Json json, Object jsonData, Class type) {
		final Array<ObjectMap<String, ?>> bodysData = (Array<ObjectMap<String, ?>>) json.readValue("surfaces", Array.class, jsonData);
		if(null!=bodysData)
			for(ObjectMap<String, ?> bodyData:bodysData){
				surfaceWorldReader.surfaces.add(json.readValue(SurfaceData.class, bodyData));
			}
		return surfaceWorldReader;
	}

}
