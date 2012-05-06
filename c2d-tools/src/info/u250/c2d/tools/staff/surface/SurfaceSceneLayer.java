package info.u250.c2d.tools.staff.surface;

import info.u250.c2d.graphic.surfaces.data.SurfaceData;
import info.u250.c2d.physical.box2d.Cb2TriangleSurfaces;
import info.u250.c2d.tools.staff.SceneLayer;

public class SurfaceSceneLayer extends SceneLayer {
	
	public SurfaceSceneLayer(String name){
		super(name,new SurfaceInputStaff(),new SurfaceAdapter());
	}

	public void setData(SurfaceData data){
		SurfaceAdapter.class.cast(this.adapter).data = data;
		SurfaceAdapter.class.cast(this.adapter).build();
	}
	public SurfaceData getData(){
		return SurfaceAdapter.class.cast(this.adapter).data;
	}
	
	@Override
	public void start() {
		new Cb2TriangleSurfaces(this.getData());
	}

	@Override
	public void stop() {
		
	}
}
