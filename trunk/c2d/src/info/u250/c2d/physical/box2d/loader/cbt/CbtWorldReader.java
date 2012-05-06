package info.u250.c2d.physical.box2d.loader.cbt;

import info.u250.c2d.physical.box2d.loader.cbt.data.BodyData;
import info.u250.c2d.physical.box2d.loader.cbt.data.BoxData;
import info.u250.c2d.physical.box2d.loader.cbt.data.CircleData;
import info.u250.c2d.physical.box2d.loader.cbt.data.DistanceJointData;
import info.u250.c2d.physical.box2d.loader.cbt.data.FrictionJointData;
import info.u250.c2d.physical.box2d.loader.cbt.data.JointData;
import info.u250.c2d.physical.box2d.loader.cbt.data.PolygonData;
import info.u250.c2d.physical.box2d.loader.cbt.data.PrismaticJointData;
import info.u250.c2d.physical.box2d.loader.cbt.data.PulleyJointData;
import info.u250.c2d.physical.box2d.loader.cbt.data.RevoluteJointData;
import info.u250.c2d.physical.box2d.loader.cbt.data.RopeJointData;
import info.u250.c2d.physical.box2d.loader.cbt.data.WeldJointData;
import info.u250.c2d.physical.box2d.loader.cbt.data.WheelJointData;
import info.u250.c2d.physical.box2d.loader.cbt.serializers.BoxSerializer;
import info.u250.c2d.physical.box2d.loader.cbt.serializers.CbtWorldSerializer;
import info.u250.c2d.physical.box2d.loader.cbt.serializers.CircleSerializer;
import info.u250.c2d.physical.box2d.loader.cbt.serializers.DistanceJointSerializer;
import info.u250.c2d.physical.box2d.loader.cbt.serializers.FrictionJointSerializer;
import info.u250.c2d.physical.box2d.loader.cbt.serializers.PolygonSerializer;
import info.u250.c2d.physical.box2d.loader.cbt.serializers.PrismaticJointSerializer;
import info.u250.c2d.physical.box2d.loader.cbt.serializers.PulleyJointSerializer;
import info.u250.c2d.physical.box2d.loader.cbt.serializers.RevoluteointSerializer;
import info.u250.c2d.physical.box2d.loader.cbt.serializers.RopeJointSerializer;
import info.u250.c2d.physical.box2d.loader.cbt.serializers.WeldJointSerializer;
import info.u250.c2d.physical.box2d.loader.cbt.serializers.WheelJointSerializer;

import java.util.Iterator;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;
/**
 * @author lycying@gmail.com
 */
public class CbtWorldReader {
	private final  Json json;
	public String name;
	public Array<BodyData> bodyDatas = new Array<BodyData>();
	public Array<JointData> jointDatas = new Array<JointData>();
	public CbtWorldReader(){
		json = new Json();
		json.setTypeName(null);
		json.setUsePrototypes(false);
		
		json.setSerializer(CbtWorldReader.class, new CbtWorldSerializer(this));
		
		json.setSerializer(BoxData.class, new BoxSerializer());
		json.setSerializer(CircleData.class, new CircleSerializer());
		json.setSerializer(PolygonData.class, new PolygonSerializer());
		
		json.setSerializer(DistanceJointData.class, new DistanceJointSerializer(this));
		json.setSerializer(FrictionJointData.class, new FrictionJointSerializer(this));
		json.setSerializer(PrismaticJointData.class, new PrismaticJointSerializer(this));
		json.setSerializer(PulleyJointData.class, new PulleyJointSerializer(this));
		json.setSerializer(RevoluteJointData.class, new RevoluteointSerializer(this));
		json.setSerializer(RopeJointData.class, new RopeJointSerializer(this));
		json.setSerializer(WeldJointData.class, new WeldJointSerializer(this));
		json.setSerializer(WheelJointData.class, new WheelJointSerializer(this));
	}
	public void spawn(){
		Iterator<JointData> itj = jointDatas.iterator();
		while(itj.hasNext())itj.next().dispose();
		Iterator<BodyData> it = bodyDatas.iterator();
		while(it.hasNext())it.next().dispose();
	}
	public void rebuild(){
		Iterator<BodyData> it = bodyDatas.iterator();
		while(it.hasNext())it.next().build();
		Iterator<JointData> itj = jointDatas.iterator();
		while(itj.hasNext())itj.next().build();
	}
	public void read(FileHandle file){
		json.fromJson(CbtWorldReader.class, file);
	}
	public void write(FileHandle file){
		json.toJson(this, file);
	}
	
	
	public BodyData findBodyDataByName(String name){
		for(BodyData data:this.bodyDatas){
			if(data.name.equals(name))return data;
		}
		return null;
	}
}
