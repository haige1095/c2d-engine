package info.u250.c2d.graphic.background;

import info.u250.c2d.engine.Engine;
import info.u250.c2d.engine.service.Disposable;
import info.u250.c2d.engine.service.Renderable;
import info.u250.c2d.graphic.surfaces.TriangleSurfaces;
import info.u250.c2d.graphic.surfaces.data.SurfaceData;

import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

/**
 * This is a repeat background use the {@link TriangleSurfaces} 
 * You should supply a alias name of the texture .
 * @author lycying@gmail.com
 */
public class RepeatTextureBackground implements Renderable,Disposable{
	private TriangleSurfaces surface ;
	public RepeatTextureBackground(String textureName){
		SurfaceData data = new SurfaceData();
		data.points = new Array<Vector2>(){{
			add(new Vector2(0,0));
			add(new Vector2(0,Engine.getEngineConfig().height));
			add(new Vector2(Engine.getEngineConfig().width,0));
			add(new Vector2(Engine.getEngineConfig().width,Engine.getEngineConfig().height));
		}};
		data.primitiveType = GL10.GL_TRIANGLE_STRIP;
		data.texture = textureName;
		data.followCamera = false;
		surface = new TriangleSurfaces(data);
		Engine.getDefaultCamera().position.y -= 100;
		Engine.getDefaultCamera().position.x -= 100;
	}
	@Override
	public void dispose() {
		surface.dispose();
	}

	@Override
	public void render(float delta) {
		surface.render(delta);
	}

}
