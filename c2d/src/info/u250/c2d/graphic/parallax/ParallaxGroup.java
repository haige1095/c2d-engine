package info.u250.c2d.graphic.parallax;


import info.u250.c2d.engine.C2dCamera;
import info.u250.c2d.engine.Engine;
import info.u250.c2d.engine.service.Renderable;
import info.u250.c2d.graphic.parallax.ParallaxLayer.ParallaxLayerResult;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.input.GestureDetector.GestureListener;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.Json.Serializer;
import com.badlogic.gdx.utils.ObjectMap;

/**
 *  This only make a group only via to the {@link SpriteParallaxLayerDrawable} .if you want to 
 * get full control , you can full access the background API.
 * Use json to config the background
 * example [the example have four layer and the bg do not move at all when the parallaxRatioX(Y) is zero]:
 * <pre>
 {
layers: [
	{
		layer: {
			region:bg,
			parallaxRatioX: 0,
			parallaxRatioY: 0,
			startPositionX: 0,
			startPositionY: 0,
			paddingX: 0,
			paddingY: 0
		}
	},
	{
		layer: {
			region:hill,
			parallaxRatioX: 0.1,
			parallaxRatioY: 0,
			startPositionX: 0,
			startPositionY: 150,
			paddingX: 0,
			paddingY: 500
		}
	},
	{
		layer: {
			region:rbg,
			parallaxRatioX: 1.0,
			parallaxRatioY: 1.0,
			startPositionX: 0,
			startPositionY: 0,
			paddingX: 0,
			paddingY: 500
		}
	},
	{
		layer: {
			region:fg,
			parallaxRatioX: 0.2,
			parallaxRatioY: 0,
			startPositionX: 0,
			startPositionY: 300,
			paddingX: 0,
			paddingY: 1000
		}
	}
],
name: first-map,
width:800,
height:480,
speedX:100,
speedY:0
}
  </pre>
 * manual install the layers 
 * <pre>
 ParallaxBackground rbg = new ParallaxBackground(width, height, speed, true);
		String name = "forest";
		// a custom drawable or use the core SpriteParallaxLayerDrawable 
		ParallaxLayerDrawable drawable = new ParallaxLayerDrawable() {
			@Override
			public void renderLayer(C2dCamera camera,
					ParallaxLayerResult parallaxLayerResult, ParallaxLayer parallaxLayer) {				
			}
			
			@Override
			public float obtainY() {
				return 0;
			}
			
			@Override
			public float obtainX() {
				return 0;
			}
			
			@Override
			public float obtainWidth() {
				return 0;
			}
			
			@Override
			public float obtainHeight() {
				return 0;
			}
		};
		ParallaxLayer layer = new ParallaxLayer(name, drawable, parallaxRatio, padding, loopX, loopY);
		rbg.add(layer);
 * </pre>
 *@author lycying@gmail.com
 */
public class ParallaxGroup extends Array<ParallaxLayer> implements Renderable{
	
	/** the background gesture listener use the core {@link GestureDetector} , a {@link ParallaxGroupGestureListener#update()} is added */
	public interface ParallaxGroupGestureListener extends GestureListener{
		public void update();
	}

	public static class DefaultParallaxGroupGestureListener implements ParallaxGroupGestureListener {
		float velX, velY;
		boolean flinging = false;
		float initialScale = 1;
		C2dCamera camera;

		public DefaultParallaxGroupGestureListener(C2dCamera camera) {
			this.camera = camera;
		}

		@Override
		public boolean touchDown(float x, float y, int pointer, int button) {
			flinging = false;
			initialScale = camera.position.z / (Engine.getEngineConfig().height / 2);
			return false;
		}
		@Override
		public boolean pan(float x, float y, float deltaX, float deltaY) {

			camera.position.add(
					-deltaX * camera.position.z
							/ (Engine.getEngineConfig().height / 2), deltaY
							* camera.position.z
							/ (Engine.getEngineConfig().height / 2), 0);

			return false;
		}

		@Override
		public boolean zoom(float originalDistance, float currentDistance) {

			float ratio = originalDistance / currentDistance;
			camera.position.z = initialScale * ratio
					* Engine.getEngineConfig().height / 2;
			return false;
		}

		@Override
		public boolean pinch(Vector2 initialFirstPointer,
				Vector2 initialSecondPointer, Vector2 firstPointer,
				Vector2 secondPointer) {
			return false;
		}

		@Override
		public void update() {

			if (flinging) {
				velX *= 0.98f;
				velY *= 0.98f;
				camera.position.add(-velX * Gdx.graphics.getDeltaTime(), velY
						* Gdx.graphics.getDeltaTime(), 0);
				if (Math.abs(velX) < 0.01f)
					velX = 0;
				if (Math.abs(velY) < 0.01f)
					velY = 0;
			}

		}


		@Override
		public boolean fling(float velocityX, float velocityY, int button) {
			flinging = true;
			velX = camera.position.z / (Engine.getEngineConfig().height / 2)
					* velocityX * 0.5f;
			velY = camera.position.z / (Engine.getEngineConfig().height / 2)
					* velocityY * 0.5f;
			return false;
		}
		@Override
		public boolean tap(float x, float y, int count, int button) {
			return false;
		}
		@Override
		public boolean longPress(float x, float y) {
			return false;
		}
	}
	/** the default camera controller */
	private class DefaultCameraController implements ParallaxGroupGestureListener {
		float velX, velY;
		boolean flinging = false;
		float initialScale = 1;
		@Override
		public boolean touchDown(float x, float y, int pointer, int button) {
			flinging = false;
			initialScale = camera.position.z/(Engine.getEngineConfig().height/2);
			return false;
		}
		@Override
		public boolean longPress (float x, float y) {
			return false;
		}

		@Override
		public boolean pinch (Vector2 initialFirstPointer, Vector2 initialSecondPointer, Vector2 firstPointer, Vector2 secondPointer) {
			return false;
		}
		

		@Override
		public boolean pan (float x, float y, float deltaX, float deltaY) {
			camera.position.add(-deltaX * camera.position.z/(Engine.getEngineConfig().height/2), deltaY *camera.position.z/(Engine.getEngineConfig().height/2), 0);
			return false;
		}

		@Override
		public boolean zoom (float originalDistance, float currentDistance) {
			float ratio = originalDistance / currentDistance;
			camera.position.z = initialScale * ratio * Engine.getEngineConfig().height/2;
			return false;
		}

		public void update () {
			if (flinging) {
				velX *= 0.98f;
				velY *= 0.98f;
				camera.position.add(-velX * Gdx.graphics.getDeltaTime(), velY * Gdx.graphics.getDeltaTime(), 0);
				if (Math.abs(velX) < 0.01f) velX = 0;
				if (Math.abs(velY) < 0.01f) velY = 0;
			}
		}
		@Override
		public boolean tap(float x, float y, int count, int button) {
			return false;
		}
		@Override
		public boolean fling(float velocityX, float velocityY, int button) {
			flinging = true;
			velX = camera.position.z/(Engine.getEngineConfig().height/2) * velocityX * 0.5f;
			velY = camera.position.z/(Engine.getEngineConfig().height/2) * velocityY * 0.5f;
			return false;
		}

	}
	
	
	public static ParallaxGroup  load(TextureAtlas atlas,FileHandle parallaxFile) {
		return getJsonLoader(atlas,false).fromJson(ParallaxGroup.class,parallaxFile);
	}
	public static ParallaxGroup  load(TextureAtlas atlas,FileHandle parallaxFile,boolean useSystemCamera) {
		return getJsonLoader(atlas,useSystemCamera).fromJson(ParallaxGroup.class,parallaxFile);
	}
	/**
	 * @param width	The screenWith 
	 * @param height The screenHeight
	 * @param speed A Vector2 attribute to point out the x and y speed
	 */
	public ParallaxGroup(float width,float height,Vector2 speed,boolean useSystemCamera){
		this.speed.set(speed);
		if(useSystemCamera){
			camera = Engine.getDefaultCamera();
		}else{
			camera = new C2dCamera(width,height);
		}
	}
	
	private C2dCamera camera;
	private Vector2 speed = new Vector2();
	private GestureDetector gestureDetector;
	private ParallaxGroupGestureListener controller;
	
	public GestureDetector getGestureDetector(){
		return this.gestureDetector;
	}
	public ParallaxGroup enableGestureDetector(){
		controller = new DefaultCameraController();
		gestureDetector = new GestureDetector(20, 0.5f, 2, 0.15f,controller );
		return this;
	}
	public ParallaxGroup enableGeBackground(GestureDetector gestureDetector,ParallaxGroupGestureListener controller){
		this.gestureDetector = gestureDetector;
		this.controller=controller;
		return this;
	}
	
	/**
	 * get the background's camera. So you can get the position of the camera and its attributes
	 */
	public C2dCamera getCamera(){
		return this.camera;
	}
	/**
	 * get the full speed of the background , include the x and y axis 
	 */
	public Vector2 getSpeed(){
		return this.speed;
	}
	/**
	 * Dynamic change the speed 
	 * @param xSpeed
	 * @param ySpeed
	 */
	public void setSpeed(float xSpeed,float ySpeed){
		this.speed.set(xSpeed, ySpeed);
	}
	/**
	 * the image layers of the background . Cannot change dynamic 
	 */
	public Array<ParallaxLayer> getLayers(){
		return this;
	}
	
	
	private final ParallaxLayerResult tempParallaxLayerResult  = new ParallaxLayerResult();
	public void render(float delta){
		if(null!=this.controller){
			this.controller.update();
		}
		this.camera.update();
	
		for(ParallaxLayer layer:this){
			
			float currentX = - (camera.position.x-camera.viewportWidth/2)*layer.parallaxRatio.x % ( layer.drawable.obtainWidth() + layer.padding.x) ;
			boolean drawX = true;
			boolean drawY = true;
			
			if(layer.loopX!=-1){
				if( this.camera.position.x- camera.viewportWidth/2 > 0 ){
					if((camera.position.x- camera.viewportWidth/2)*layer.parallaxRatio.x > layer.drawable.obtainWidth()*layer.loopX){
						drawX = false;
					}
				}
				//TODO
			}
			
			if(layer.loopY!=-1){
				if((camera.position.y-camera.viewportHeight/2)*layer.parallaxRatio.y > layer.drawable.obtainHeight()*layer.loopY){
					drawY = false;
				}
			}
			if(drawX){
				if( this.camera.position.x- camera.viewportWidth/2 < 0 ){
					currentX += -( layer.drawable.obtainWidth() + layer.padding.x);
				}
				do{
					if(drawY){
						float currentY = - (camera.position.y-camera.viewportHeight/2)*layer.parallaxRatio.y % ( layer.drawable.obtainHeight() + layer.padding.y) ;
						if( this.camera.position.y-camera.viewportHeight/2  < 0 )currentY += - (layer.drawable.obtainHeight()+layer.padding.y);
						do{
							tempParallaxLayerResult.resultX = camera.position.x - this.camera.viewportWidth/2+currentX + layer.startPosition.x;
							tempParallaxLayerResult.resultY = camera.position.y - this.camera.viewportHeight/2 + currentY +layer.startPosition.y;
							layer.drawable.renderLayer(delta,camera, tempParallaxLayerResult, layer);
							currentY += ( layer.drawable.obtainHeight() + layer.padding.y );
						}while( currentY < camera.viewportHeight);
					}
					currentX += ( layer.drawable.obtainWidth()+ layer.padding.x);
				}while( currentX < camera.viewportWidth);
			}
			
		}
		if(!Engine.isPause()){
			this.camera.position.add(speed.x*delta,speed.y*delta, 0);
			this.camera.position.z = this.camera.getZoom() * this.camera.viewportHeight/2;
		}
	}
	private static Json getJsonLoader(final TextureAtlas atlas,final boolean useSystemCamera) {
		Json json = new Json();
		json.setTypeName(null);
		json.setUsePrototypes(false);

		json.setSerializer(ParallaxGroup.class, new Serializer<ParallaxGroup>() {
			@SuppressWarnings("rawtypes")
			@Override
			public void write(Json json, ParallaxGroup object,  Class knownType) {
			}

			@SuppressWarnings({ "unchecked", "rawtypes" })
			@Override
			public ParallaxGroup read(Json json, Object jsonData, Class type) {
				final int width = json.readValue("width", Integer.class,jsonData);
				final int height = json.readValue("height", Integer.class,jsonData);
				final Array<ObjectMap<String, ?>> array = (Array<ObjectMap<String, ?>>) json.readValue("layers", Array.class, jsonData);
				final Vector2 speed = new Vector2(json.readValue("speedX", float.class, jsonData),json.readValue("speedY", float.class, jsonData));
				
				
				ParallaxGroup rbg = new ParallaxGroup(width, height , speed ,useSystemCamera);

				for (ObjectMap<String, ?> layer_data : array) { 
					rbg.add(json.readValue("layer",ParallaxLayer.class, layer_data));
				}
				return rbg;
			}
		});

		json.setSerializer(ParallaxLayer.class, new Serializer<ParallaxLayer>() {
			@SuppressWarnings("rawtypes")
			@Override
			public void write(Json json, ParallaxLayer object, Class type) {
			}
			@SuppressWarnings("rawtypes")
			@Override
			public ParallaxLayer read(Json json, Object jsonData, Class type) {
				String name = json.readValue("region", String.class, jsonData);
				final TextureRegion region = atlas.findRegion(name); 
				final Vector2 parallaxRatio = new Vector2(json.readValue("parallaxRatioX", float.class, jsonData),json.readValue("parallaxRatioY", float.class, jsonData));
				final Vector2 startPosition = new Vector2(json.readValue("startPositionX", float.class, jsonData),json.readValue("startPositionY", float.class, jsonData));
				final Vector2 padding = new Vector2(json.readValue("paddingX", float.class, jsonData),json.readValue("paddingY", float.class, jsonData));
				final int loopX = json.readValue("loopX", int.class, jsonData);
				final int loopY = json.readValue("loopY", int.class, jsonData);
				final ParallaxLayer ret = new ParallaxLayer(name,new SpriteParallaxLayerDrawable(new Sprite(region)),parallaxRatio,padding,loopX,loopY,startPosition);
				return ret;
			}
		});

		return json;
	}
	
}
