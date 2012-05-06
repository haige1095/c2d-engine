package info.u250.c2d.engine;

import info.u250.c2d.accessors.C2dCameraAccessor;
import info.u250.c2d.accessors.MeshMaskAccessor;
import info.u250.c2d.accessors.Cb2ObjectAccessor;
import info.u250.c2d.accessors.SpriteAccessor;
import info.u250.c2d.engine.CoreProvider.CoreEvents;
import info.u250.c2d.engine.CoreProvider.TransitionType;
import info.u250.c2d.engine.EngineDrive.EngineOptions;
import info.u250.c2d.engine.events.EventManager;
import info.u250.c2d.engine.events.EventManagerImpl;
import info.u250.c2d.engine.load.Loading.LoadingComplete;
import info.u250.c2d.engine.load.in.InGameLoading;
import info.u250.c2d.engine.load.startup.StartupLoading;
import info.u250.c2d.engine.resources.AliasResourceManager;
import info.u250.c2d.engine.resources.MusicManager;
import info.u250.c2d.engine.resources.SoundManager;
import info.u250.c2d.engine.service.Updatable;
import info.u250.c2d.engine.transitions.TransitionFactory;
import info.u250.c2d.graphic.C2dFps;
import info.u250.c2d.graphic.Mask;
import info.u250.c2d.physical.box2d.Cb2Object;
import info.u250.c2d.updatable.PeriodUpdatable;

import java.util.HashMap;
import java.util.Map;

import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenManager;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Plane;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.Ray;
/**
 * The main game engine . it supply many usefully static method to access the managers of the game.
 * you game should begin here , and simply extends this.
 * @author lycying@gmail.com
 */
public abstract class Engine extends ApplicationAdapter{
	private static Engine instance = null;
	public Engine(){
		this.engineDrive = this.onSetupEngineDrive();
		this.engineConfig = engineDrive.onSetupEngine();
		instance = this;
	}
	@SuppressWarnings("unchecked")
	public static final <T extends Engine> T get(){
		return (T)instance;
	}
	/** the game logic */
	private final EngineDrive engineDrive;
	/** the game configure */
	private final EngineOptions engineConfig;
	/** the event manager */
	private EventManager eventManager;
	/** the core asset manager of Libgdx */
	private AssetManager assetManager ;
	/** default bitmap font */
	private BitmapFont defaultFont;
	/** default sprite batch */
	private SpriteBatch spriteBatch ;
	/** default camera */
	private C2dCamera defaultCamera;
	/** the loadding screen */
	private StartupLoading startupLoading ;
	/** the in game loading */
	private InGameLoading ingameLoading;
	/** the music manager */
	private MusicManager musicManager;
	/** the sound manager */
	private SoundManager soundManager;
	/** the resources alias manager */
	private AliasResourceManager<String> aliasResourceManager;
	/** the fps label */
	private C2dFps fps;
	/** the main game scene to show the graphic */
	private Scene mainScene;
	/** the transition scene to switch two scene */
	private Transition transitionScene;
	/** its a event manager that [Aurelien Ribon]  supply  */
	private TweenManager tweenManager;
	/** if the game is running */
	private boolean running = true; 
	/***/
	private Map<String,Updatable> updatables = new HashMap<String, Updatable>();
	/**
	 * This is one of the most important methods. 
	 * The typical, all the game logic and rendering entry will go from here. 
	 * We do this purpose, main is to let business operation independent, 
	 * and you may no longer care about many things such as resources destroyed, load control etc,
	 *  and to focus on the game itself.
	 */
	protected abstract EngineDrive onSetupEngineDrive();
	
	/**
	 * Set the main of operation, played the role of the page. You should never use this method
	 */
	protected final static void _setMainScene(Scene mainScene){
		instance.mainScene = mainScene;
	}
	
	/**
	 * When in the scene when switching between. 
	 * Will cause a series of gradual movement. 
	 * The last. New rendering methods will replace the old
	 */
	public final static void setMainScene(Scene mainScene){
		setMainScene(mainScene, TransitionType.Fade);
	}
	public final static void setMainScene(Scene mainScene,TransitionType type){
		Engine.setMainScene(mainScene, type, 500);
	}
	public final static void setMainScene(Scene mainScene,TransitionType type,int halfDurationMillis){
		Engine.doResume();
		if(instance.transitionScene.isTransiting()) return;
		if(instance.mainScene == mainScene ) return ;
		instance.transitionScene = TransitionFactory.getTransitionScene(type);
		Gdx.input.setInputProcessor(null);
		instance.transitionScene.transition(instance.mainScene, mainScene,halfDurationMillis);
	}
	/**
	 * You may want to know what's the main scene . Call this
	 */
	public final static Scene getMainScene(){
		return Engine.instance.mainScene;
	}
	
	/**
	 * First of all, load resources. 
	 * When resource loading was finished,
	 *  we  will enter the game initialization operation.
	 */
	@Override
	public final void create () {
		try{
			//set up the FPS
			if(engineConfig.fps)this.fps = new C2dFps();
			if(engineConfig.catchBackKey)Gdx.input.setCatchBackKey(true);
			//set up the TweenEngine 
			this.setupTweenEngine();
			//set up the camera 
			this.setupCamera();
			//the resource manager
			this.assetManager = new AssetManager();
			this.aliasResourceManager = new AliasResourceManager<String>();
			this.soundManager = new SoundManager();
			this.musicManager = new MusicManager();
			//the event manager
			this.eventManager = new EventManagerImpl();
			//set up the sprite batch
			this.spriteBatch = new SpriteBatch();
			//set up the default font
			this.defaultFont = new BitmapFont();
			this.preLoad();
			//loading screen
			this.setupLoading();
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	protected void preLoad(){
		
	}
	private void setupLoading() throws InstantiationException, IllegalAccessException, ClassNotFoundException{
		this.startupLoading = (StartupLoading)Class.forName(engineConfig.loading).newInstance();
		this.ingameLoading = (InGameLoading)Class.forName(engineConfig.ingameLoading).newInstance();
		this.startupLoading.setLoaded(false);
		this.ingameLoading.setLoaded(true);
		this.startupLoading.setLoadingComplete(new LoadingComplete() {
			@Override
			public void onReady(AliasResourceManager<String> alias) {
				transitionScene = TransitionFactory.getTransitionScene(TransitionType.Fade);
				engineDrive.onResourcesRegister(aliasResourceManager);
				engineDrive.onLoadedResourcesCompleted();
			}
		});
		for(final String path:engineConfig.assets){
			aliasResourceManager.load(path);
		}
	}
	
	private void setupTweenEngine(){
//		Tween.enablePooling(true);
		this.tweenManager = new TweenManager();
		Tween.registerAccessor(Sprite.class,new SpriteAccessor());
		Tween.registerAccessor(Cb2Object.class, new Cb2ObjectAccessor());
		Tween.registerAccessor(C2dCamera.class, new C2dCameraAccessor());
		Tween.registerAccessor(Mask.class, new MeshMaskAccessor());
	}
	private void setupCamera(){
		this.defaultCamera = new C2dCamera(this.engineConfig.width,this.engineConfig.height);
	}
	@Override
	public final void pause() {
		doPause();
		eventManager.fire(CoreEvents.SystemPause, this);
		super.pause();
	}
	@Override
	public final void resume() {
		if(engineConfig.autoResume)
			doResume();
		eventManager.fire(CoreEvents.SystemResume, this);
		super.resume();
	}
	
	
	public final static void doPause() {
		instance.running = false;
		instance.tweenManager.pause();
	}
	public final static void doResume() {
		instance.running = true;
		instance.tweenManager.resume();
	}
	public final static boolean isPause(){
		return !instance.running;
	}
	
	@Override
	public final void render() {
		if(Gdx.graphics.isGL20Available()){
			Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		}else{
			Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		}
		final float delta = Gdx.graphics.getDeltaTime();
		this.defaultCamera.update();
		if(Gdx.graphics.isGL11Available()){
			this.defaultCamera.apply(Gdx.graphics.getGL11());
		}
		this.spriteBatch.setProjectionMatrix(this.defaultCamera.combined);
		if(startupLoading.isLoaded()){
			if(running){
				tweenManager.update(delta*1000);
				for(Updatable updatable: updatables.values()){
					updatable.update(delta);
				}
				mainScene.update(delta);
			}
			eventManager.update(delta);
			if(transitionScene.isTransiting()){
				transitionScene.render(delta);
			}else{
				mainScene.render(delta);
			}
			//the in game loading
			if(!ingameLoading.isLoaded()){
				ingameLoading.render(delta);
			}
			
			if(engineConfig.fps)fps.render(delta);
		}else{
			startupLoading.render(delta);
		}
	}

	/**
	 * In game load.
	 */
	public final static void load(String[] assets,LoadingComplete loadingComplete){
		if(null == assets || 0 == assets.length) return ;
	
		instance.ingameLoading.setLoadingComplete(loadingComplete);
		instance.ingameLoading.setLoaded(false);
		for(final String path:assets){
			instance.aliasResourceManager.load(path);
		}
	}
	
	public final static <T> T resource(String key){
		return instance.aliasResourceManager.get(key);
	}
	public final static <T> T resource(String key,Class<T> type){
		return instance.aliasResourceManager.get(key);
	}
	
	private static Ray ray = null;
	private final static Plane xzPlane = new Plane(new Vector3(0, 1, 0), new Vector3(1, 0, 0),new Vector3(1, 1, 0));
	private final static Vector3 intersection = new Vector3();
	public final static Vector2 screenToWorld(int x, int y) {	
		ray = instance.defaultCamera.getPickRay(x, y);
		Intersector.intersectRayPlane(ray, xzPlane, intersection);
		return new Vector2(intersection.x,intersection.y);
	}
	
	
	public final static void debugInfo(String str){
		getSpriteBatch().begin();
		getDefaultFont().drawMultiLine(getSpriteBatch(), str, 0, getEngineConfig().height);
		getSpriteBatch().end();
	}
	public final static float getDeltaTime(){
		return Gdx.graphics.getDeltaTime();
	}

	public final static MusicManager getMusicManager() {
		return instance.musicManager;
	}

	/**
	 * ZoomOut the background and give the correct action
	 * shake the background ,etc
	 */
	public final static void  addUpdatable(String name,Updatable updatable) {
		Object evt = instance.updatables.get(name);
		PeriodUpdatable object = null;
		if(null!=evt){
			if(evt instanceof PeriodUpdatable){
				object = PeriodUpdatable.class.cast(evt);
				if(!object.isStart()){
					object.go();
				}
			}
		}else{
			if(updatable instanceof PeriodUpdatable){
				object = PeriodUpdatable.class.cast(updatable);
				if(!object.isStart()){
					object.go();
				}
			}
		}
		instance.updatables.put(name,object);
	}
	
	public final static void  removeUpdatable(String name,Updatable updatable) {
		instance.updatables.remove(name);
	}
	
	public final static AliasResourceManager<String> getAliasResourceManager(){
		return instance.aliasResourceManager;
	}
	public final static SoundManager getSoundManager() {
		return instance.soundManager;
	}

	/** you should never use this method , its only needed in Engine */
	public final static AssetManager getAssetManager() {
		return instance.assetManager;
	}
	
	
	public final static SpriteBatch getSpriteBatch() {
		return instance.spriteBatch;
	}

	public final static C2dCamera getDefaultCamera() {
		return instance.defaultCamera;
	}
	
	public final static EngineOptions getEngineConfig(){
		return instance.engineConfig;
	}
	
	public final static BitmapFont getDefaultFont(){
		return instance.defaultFont;
	}
	
	public final static EventManager getEventManager(){
		return instance.eventManager;
	}
	public final static TweenManager getTweenManager(){
		return instance.tweenManager;
	}
	
	@Override
	public void resize(int width, int height) {
		if(this.engineConfig.resizeSync){
			super.resize(width, height);
			this.engineConfig.width = width;
			this.engineConfig.height = height;
			this.defaultCamera.resize(width, height);
		}
	}
	
	@Override
	public void dispose() {
		try{
			this.tweenManager.killAll();
			engineDrive.dispose();
			if(null!=defaultFont){
				defaultFont.dispose();
			}
			if(null!=soundManager){
				soundManager.dispose();
			}
			if(null!=musicManager){
				musicManager.dispose();
			}
			if(startupLoading!=null){
				if(!startupLoading.finished()){
					this.assetManager.finishLoading();
				}
				startupLoading.dispose();
			}
			if(null!=spriteBatch){
				this.spriteBatch.dispose();
			}
			
			Texture.invalidateAllTextures(Gdx.app);
			this.assetManager = null;
			this.defaultCamera = null;
			instance = null;
			super.dispose();
		}catch (Exception ex){
			//ignore
		}
	}
}