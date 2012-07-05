package info.u250.c2d.engine.transitions;

import info.u250.c2d.engine.Engine;
import info.u250.c2d.engine.Transition;
import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.ScreenUtils;
/**  The odd rows goes to the left while the even rows goes to the right.
 * @author lycying@gmail.com
 */
final class TransitionSceneSplitRows extends Transition{	
	SplitTextureRegion splitWindow ;
	
	public TransitionSceneSplitRows(){
		Tween.registerAccessor(SplitTextureRegion.class, new SplitAccessor());
	}
	private int halfDurationMillis;
	
	@Override
	protected void doTransition(final int halfDurationMillis) {
		this.halfDurationMillis = halfDurationMillis;
	}
	void doUnglyTransition(){
		outgoing.hide();
		Tween
		.to(splitWindow, SplitAccessor.SplitAmount, halfDurationMillis*2).target(Engine.getEngineConfig().width)
		.setCallback(new TweenCallback() {
			@Override
			public void onEvent(int type, BaseTween<?> source) {
				doSetMainScene(incoming);
				
				Gdx.input.setInputProcessor(incoming.getInputProcessor());
				incoming.show();
				reset();
			}
		}).start(Engine.getTweenManager());
	}
	
	int loop = 0;
	TextureRegion incomingTextureRegion = null ;
	TextureRegion outgoingTextureRegion = null;
	@Override
	public void render(float delta) {
		if(loop == 0){
			outgoing.render(delta);
			outgoingTextureRegion = ScreenUtils.getFrameBufferTexture();
			loop++;
		}else if(loop == 1){
			incoming.render(delta);
			incomingTextureRegion = ScreenUtils.getFrameBufferTexture();
			loop++;
			splitWindow = new SplitTextureRegion(incomingTextureRegion, outgoingTextureRegion,true);
			doUnglyTransition();
		}else{
			Engine.getSpriteBatch().begin();
			splitWindow.render(delta);
			Engine.getSpriteBatch().end();
		}
	}
	
	@Override
	public void reset() {
		this.loop = 0;
		try{
			this.incomingTextureRegion.getTexture().dispose();
			this.outgoingTextureRegion.getTexture().dispose();
		}catch(Exception ex){
			//ignore
		}
		splitWindow = null;
		this.incomingTextureRegion = null;
		this.outgoingTextureRegion = null;
		super.reset();
	}
}