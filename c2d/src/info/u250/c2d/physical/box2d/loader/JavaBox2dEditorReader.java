package info.u250.c2d.physical.box2d.loader;


import info.u250.c2d.physical.box2d.Cb2World;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.utils.Array;


/**
 * This class load the resources produced by Box2dEditor . Its url is :
 * https://code.google.com/p/box2d-editor/
 * 
 * 
 * If you load this, you must supply 2 params: the shape's width and the shape's height ;
 * like this:  
 * Body body = loader.getBodies().get("gfx\\test01.png").build(sprite.getWidth(),sprite.getHeight());
 * 
 * @see info.u250.c2d.physical.loader.JavaBox2dEditorReader.JavaBox2dEditorReaderModel
 * 
 * @author lycying@gmail.com
 */
public class JavaBox2dEditorReader extends AbstractBox2dPhysicalObjectLoader{
	
	
	public void read(String path) {
		this.bodies.clear();
		try{
			this.importFromFile(Gdx.files.internal(path).read());
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	private void importFromFile(InputStream stream) {
		DataInputStream is = null;

		try {
			is = new DataInputStream(stream);
			while (is.available() > 0) {
				String name = is.readUTF();
				readVecss(is);
				Vector2[][] polygons = readVecss(is);				
				JavaBox2dEditorReaderModel adapter = new JavaBox2dEditorReaderModel();
				Array<Vector2[]> vv = new Array<Vector2[]>();
				for(Vector2[] polygon:polygons){
					for(Vector2 v:polygon){
						v.mul(1/Cb2World.RADIO);
					}
					vv.add(polygon);
				}
				adapter.polygons = vv;
				this.bodies.put(name, adapter);
			}

		} catch (IOException ex) {
			throw new RuntimeException(ex.getMessage());

		} finally {
			if (is != null)
				try { is.close(); } catch (IOException ex) {}
		}
	}
	
	private Vector2 readVec(DataInputStream is) throws IOException {
		Vector2 v = new Vector2();
		v.x = is.readFloat();
		v.y = is.readFloat();
		return v;
	}

	private Vector2[] readVecs(DataInputStream is) throws IOException {
		int len = is.readInt();
		Vector2[] vs = new Vector2[len];
		for (int i=0; i<len; i++)
			vs[i] = readVec(is);
		return vs;
	}

	private Vector2[][] readVecss(DataInputStream is) throws IOException {
		int len = is.readInt();
		Vector2[][] vss = new Vector2[len][];
		for (int i=0; i<len; i++)
			vss[i] = readVecs(is);
		return vss;
	}
	
	/**
	 * @author lycying@gmail.com
	 */
	private class JavaBox2dEditorReaderModel extends AbstractBox2dPhysicalObjectModel{
		@Override
		public  Body build(float width,float height) {
			try{
				
				for(Vector2[] polygon:polygons){
					for(Vector2 v:polygon){
						v.x *= width/100;
						v.y *= height/100;
						v.x -=width/Cb2World.RADIO/2;
						v.y -=height/Cb2World.RADIO/2;
					}
				}
				FixtureDef def = new FixtureDef();
				PolygonShape shape = new PolygonShape();
				BodyDef bodyDef = new BodyDef();
				bodyDef.type = BodyType.DynamicBody;
				
				Body body =Cb2World.getInstance().world().createBody(bodyDef);
				for(Vector2[] polygon:polygons){
					def.density = density;
					def.friction = friction;
					def.restitution = def.restitution;
					shape.set(polygon);
					def.shape = shape;
					body.createFixture(def);
				}
				shape.dispose();
				return body;
			}catch(Exception ex){
				Gdx.app.log("ERROR", "you must supply the shape's full width and height .");
			}
			return null;
			
		}
	}
}

