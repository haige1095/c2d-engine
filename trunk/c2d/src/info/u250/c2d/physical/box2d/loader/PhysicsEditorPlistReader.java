package info.u250.c2d.physical.box2d.loader;

import info.u250.c2d.physical.box2d.Cb2World;
import info.u250.c2d.utils.PList;

import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape.Type;
import com.badlogic.gdx.utils.Array;

/**
 * This class load the resources produced by PysicalEditor: its url:
 * http://www.physicseditor.de/
 * @author lycying@gmail.com
 */

public class PhysicsEditorPlistReader extends AbstractBox2dPhysicalObjectLoader{
	@SuppressWarnings("unchecked")
	public void read(String plist) {
		this.bodies.clear();
		try{
			final Map<String, Object> bodiesMap = (Map<String, Object>)PList.parse(Gdx.files.internal(plist)).get("bodies");
			for(String key:bodiesMap.keySet()){
				final Map<String, Object> bodyMap = (Map<String, Object>)bodiesMap.get(key);
				final Box2dPhysicalObjectAdapter adapter = new Box2dPhysicalObjectAdapter();
				
				adapter.anchorpoint=parseVector2(bodyMap.get("anchorpoint")+"");
				
				final Array<Map<String, Object>> fixturesMapList = (Array<Map<String, Object>>)bodyMap.get("fixtures");
				for(final Map<String, Object> fixturesMap:fixturesMapList){
					adapter.friction = Float.parseFloat(fixturesMap.get("friction")+"");
					adapter.restitution = Float.parseFloat(fixturesMap.get("restitution")+"");
					adapter.density = Float.parseFloat(fixturesMap.get("density")+"");
					
					adapter.filter_groupIndex = Integer.parseInt(fixturesMap.get("filter_groupIndex")+"");
					adapter.filter_maskBits = Integer.parseInt(fixturesMap.get("filter_maskBits")+"");
					adapter.filter_categoryBits = Integer.parseInt(fixturesMap.get("filter_categoryBits")+"");
					
					adapter.isSensor = Boolean.parseBoolean(fixturesMap.get("isSensor")+"");
					
					adapter.fixture_type = parseType(fixturesMap.get("fixture_type")+"");
					
					Array<Array<String>> polygons = (Array<Array<String>>)fixturesMap.get("polygons");
					for(Array<String> polygon:polygons){
						final Vector2[] vv = new Vector2[polygon.size];
						for(int i=0;i<polygon.size;i++){
							vv[i] = parseVector2(polygon.get(i));
						}
						
						adapter.polygons.add(vv);
					}
				}
				
				this.bodies.put(key, adapter);
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	private Vector2 parseVector2(String v){
		String[] strs = v.replace("{", "").replace("}", "").split(",");
		return new Vector2(Float.parseFloat(strs[0]),Float.parseFloat(strs[1])).mul(1/Cb2World.RADIO);
	}
	private Type parseType(String t){
		return Type.Polygon;
	}
	/**
	 * @author lycying@gmail.com
	 */
	private class Box2dPhysicalObjectAdapter extends AbstractBox2dPhysicalObjectModel{

		@Override
		public  Body build(float width,float height) {
			FixtureDef def = new FixtureDef();
			PolygonShape shape = new PolygonShape();
			BodyDef bodyDef = new BodyDef();
			bodyDef.type = BodyType.DynamicBody;
			
			Body body =Cb2World.getInstance().world().createBody(bodyDef);
			for(Vector2[] polygon:polygons){
				for(Vector2 v:polygon){
//					v.x -=width/Box2dObject.RADIO/2;
					v.y +=height/Cb2World.RADIO/2;
				}
				
				def.density = density;
				def.friction = friction;
				def.restitution = def.restitution;
				
				shape.set(polygon);
				def.shape = shape;
				body.createFixture(def);
			}
			shape.dispose();
			return body;
		}
	}

}

