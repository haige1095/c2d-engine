package info.u250.c2d.tests;

import info.u250.c2d.engine.Engine;
import info.u250.c2d.engine.EngineDrive;
import info.u250.c2d.engine.Scene;
import info.u250.c2d.engine.resources.AliasResourceManager;
import info.u250.c2d.graphic.AdvanceSprite;
import info.u250.c2d.input.PhysicalFingerInput;
import info.u250.c2d.physical.box2d.Cb2World;
import info.u250.c2d.physical.box2d.Cb2Object;
import info.u250.c2d.physical.box2d.loader.cbt.data.PolygonData;
import info.u250.c2d.tests.outline.BayazitDecomposer;
import info.u250.c2d.tests.outline.TextureConverter;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Blending;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;


public class Box2d_OutlineTest extends Engine {
	Array<Vector2> create_sprite_OUTLINE(FileHandle file) {
		Pixmap.setBlending(Blending.None);
		Pixmap pixmap = new Pixmap(file);
		int size = pixmap.getWidth() * pixmap.getHeight();
		int[] array = new int[size];
		for (int y = 0; y < pixmap.getHeight(); y++) {
			for (int x = 0; x < pixmap.getWidth(); x++) {
				int color = pixmap.getPixel(x, y);
				array[x + y * pixmap.getWidth()] = color;
			}
		}
		int w = pixmap.getWidth();
		int h = pixmap.getHeight();
		// pixmap.getPixels().array()
		pixmap.dispose();
		pixmap = null;
		Array<Vector2> outline = null;
		try {
			outline = TextureConverter.createPolygon(array, w, h);
		} catch (Exception e) {
			Gdx.app.log("Debug", e.getMessage());
		} finally {
			//
		}


		if (!BayazitDecomposer.IsCounterClockWise(outline)) {
			// Normally, doesn't happen :)
			Gdx.app.log(
					"DEBUG",
					"Clockwise outline vertices (after TextureConverter.createPolygon), reversing...");
			// LibGDXPlayground.reverse(outline);
			outline.reverse();

			if (!BayazitDecomposer.IsCounterClockWise(outline)) {
				Gdx.app.log("Debug", "Clockwise outline vertices ?! WTF ? ");
			}
		}

		return outline;
	}
//	short checkBodyPolygonsAreaAndAdd(Array<Array<Vector2>> listPolygons) {
//		float totalArea = 0;
//		for (int j = 0; j < listPolygons.size; j++) {
//			Array<Vector2> poly = listPolygons.get(j);
//
//			float area = BayazitDecomposer.GetSignedArea(poly);
//			area = Math.abs(area);
//			totalArea += area;
//			if (area <= 1.0f) {
//				Gdx.app.log("Debug",
//						"Polygon area too small (removing body polygon) ["
//								+ area + "]");
//				listPolygons.removeIndex(j);
//
//				if (listPolygons.size == 0) {
//					return -1;
//				}
//
//				continue;
//			}
//		}
//
//		if (totalArea <= minArea) {
//			Gdx.app.log("Debug",
//					"Total area too small (registering for delayed destroy) ["
//							+ totalArea + "/" + minArea + "]");
//
//			return 0;
//		}
//		return 1;
//	}
//	final float MIN_AREA_STAGE1 = 1000.0f;
//	final float MIN_AREA_STAGE2 = 2000.0f;
//	final float MIN_AREA_STAGE3 = 3000.0f;
//	final float MIN_AREA_STAGE4 = 4000.0f;
//	float minArea = MIN_AREA_STAGE1;
//	
	Array<Array<Vector2>> create_sprite_POLYGONS(Array<Vector2> outline) {

		if (!BayazitDecomposer.IsCounterClockWise(outline)) {
			Gdx.app.log("Debug",
					"Clockwise outline vertices (passed in create_sprite_POLYGONS), reversing... ");
			// LibGDXPlayground.reverse(outline);
			outline.reverse();

			if (!BayazitDecomposer.IsCounterClockWise(outline)) {
				Gdx.app.log("Debug", "Clockwise outline vertices ?! WTF ? ");
			}
		}

		Array<Array<Vector2>> polygons = BayazitDecomposer
				.ConvexPartition(outline); // outline must be CounterClockWise
											// (although it gets reversed inside
											// function ...)

		for (int j = 0; j < polygons.size; j++) {
			Array<Vector2> polygon = polygons.get(j);
			if (BayazitDecomposer.IsCounterClockWise(polygon)) {
				Gdx.app.log(
						"DEBUG",
						"Counter Clockwise body polygon vertices (after BayazitDecomposer.ConvexPartition), reversing... ");
				// LibGDXPlayground.reverse(list);
				polygon.reverse();
				if (BayazitDecomposer.IsCounterClockWise(polygon)) {
					Gdx.app.log("Debug",
							"Counter Clockwise body polygon vertices ?! WTF ?");
				}
			} 
		}
		return polygons;
	}
	
	public static boolean areVerticesClockwise(Vector2[] vertices) {
		float area = 0;
		for (int i = 0; i < vertices.length; i++) {
			final Vector2 p1 = vertices[i];
			final Vector2 p2 = vertices[(i + 1) % vertices.length];
			area += p1.x * p2.y - p2.x * p1.y;
		}
		if (area < 0) {
			return true;
		} else {
			return false;
		}
	}
	
	@Override
	protected EngineDrive onSetupEngineDrive() {
		return new EngineX();
	}
	@Override
	public void dispose () {
		super.dispose();
	}
	
	private class EngineX implements EngineDrive{
		@Override
		public void onResourcesRegister(AliasResourceManager<String> reg) {
		}
		@Override
		public void dispose() {}
		@Override
		public EngineOptions onSetupEngine() {
			final EngineOptions opt = new EngineOptions(new String[]{},800,480);
			opt.gl20Enable = true;
			return opt;
		}

		PhysicalFingerInput input;
		ShapeRenderer render ;
		@Override
		public void onLoadedResourcesCompleted() {
			final Texture texture = new Texture(Gdx.files.internal("data/box2d/outline.png"));
			input = new PhysicalFingerInput(
					Cb2World.getInstance().installDefaultWorld()
					.createScreenBox());
			render = new ShapeRenderer();
			final Array<Vector2> outlines = create_sprite_OUTLINE(Gdx.files.internal("data/box2d/outline.png"));
			final Array<Array<Vector2>> polygons = create_sprite_POLYGONS(outlines);
			final PolygonData data = new PolygonData();
			Array<Vector2[]> box2d = new Array<Vector2[]>();
			for (int j = 0; j < polygons.size; j++) {
				Vector2 bodySize = new Vector2(texture.getWidth(),texture.getHeight());
				Array<Vector2> vertices_original = polygons.get(j);
				if (BayazitDecomposer.IsCounterClockWise(vertices_original)) {
					Gdx.app.log("Debug","Counter Clockwise body polygon vertices (ORIGINAL) for Box2D ?! WTF ?");
				}
				Vector2[] vertices_adjusted = new Vector2[vertices_original.size];
				for (int t = 0; t < vertices_original.size; t++) {
					Vector2 v = vertices_original.get(t).cpy();
					v.x -= (bodySize.x / 2); 
					v.y = (bodySize.y / 2) - v.y; 
					vertices_adjusted[t] = v;
				}
				if (!BayazitDecomposer
						.IsCounterClockWise(vertices_adjusted)) {
					Gdx.app.log("Debug",
							"Clockwise body polygon vertices (ADJUSTED) for Box2D ?! WTF ?");
				}
				if (areVerticesClockwise(vertices_adjusted)) {
					Gdx.app.log("Debug",
							"_ Clockwise body polygon vertices (ADJUSTED) for Box2D ?! WTF ? ");
				}
				box2d.add(vertices_adjusted);
				
			}
		
			data.polygons = box2d;
			data.isDynamic = true;
			final Cb2Object obj = new Cb2Object(data, new AdvanceSprite(texture));
			Engine.setMainScene(new Scene() {
				@Override
				public void render(float delta) {
					float angle = -60;
					float scale = 1.5f;
					render.setProjectionMatrix(Engine.getDefaultCamera().combined);
					for(Vector2[] vv:data.polygons){
						for(int i=1;i<vv.length;i++){
							render.setColor(Color.YELLOW);
							render.begin(ShapeType.Line);
							float len1 = vv[i-1].len();
							float angle1 = vv[i-1].angle();
							float angle2 = vv[i].angle();
							float len2 = vv[i].len();
							render.line(
									(texture.getWidth()/2+len1*MathUtils.cosDeg(angle+angle1))*scale,
									(texture.getHeight()/2+len1*MathUtils.sinDeg(angle+angle1))*scale , 
									(texture.getWidth()/2+len2*MathUtils.cosDeg(angle+angle2))*scale,
									(texture.getHeight()/2+len2*MathUtils.sinDeg(angle+angle2))*scale);
							render.end();
						}
					}
					Cb2World.getInstance().update(delta);
					Engine.getSpriteBatch().begin();
					obj.render(delta);
					Engine.getSpriteBatch().end();
					obj.data.debug(render);
				}
				@Override
				public InputProcessor getInputProcessor() {
					return input;
				}
				@Override
				public void update(float delta) {	
				}
				@Override
				public void hide() {	
				}
				@Override
				public void show() {
				}
			});	
		}
	}
}
