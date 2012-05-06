package info.u250.c2d.graphic.surfaces;

import info.u250.c2d.graphic.surfaces.data.SurfaceData;
import info.u250.c2d.graphic.surfaces.serializers.SurfaceSerializers;
import info.u250.c2d.graphic.surfaces.serializers.SurfaceWorldSerializers;
import info.u250.c2d.graphic.surfaces.serializers.Vector2Serializers;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;

public class SurfaceWorldReader {
	public Array<SurfaceData> surfaces = new Array<SurfaceData>();
	private final  Json json;
	public void read(FileHandle file){
		json.fromJson(SurfaceWorldReader.class, file);
	}
	public void write(FileHandle file){
		json.toJson(this, file);
	}
	public SurfaceWorldReader(){
		json = new Json();
		json.setTypeName(null);
		json.setUsePrototypes(false);
		
		json.setSerializer(SurfaceWorldReader.class, new SurfaceWorldSerializers(this));
		json.setSerializer(SurfaceData.class, new SurfaceSerializers());
		json.setSerializer(Vector2.class, new Vector2Serializers());
	}
}