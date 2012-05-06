package info.u250.c2d.engine.transitions;

import info.u250.c2d.engine.Engine;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

import aurelienribon.tweenengine.TweenAccessor;

/** change some attributes of the split texture region
 * @author lycying@gmail.com */
class SplitAccessor implements TweenAccessor<SplitTextureRegion>{

	public final static int SplitAmount = 0;	
	@Override
	public int getValues(SplitTextureRegion target, int tweenType, float[] returnValues) {
		switch(tweenType){
		case SplitAmount:
			returnValues[0] = target.split ;
			return 1;
		default: assert false; return -1;
		}
	}

	@Override
	public void setValues(SplitTextureRegion target, int tweenType, float[] newValues) {
		switch(tweenType){
		case SplitAmount:
			target.split = newValues[0];
			break;
		default: assert false;
		}
	}
}
class SplitTextureRegion{
	final static int  NUMBER = 6;
	float split = 0;
	TextureRegion[] incomingRegionOdd = new TextureRegion[NUMBER/2];
	TextureRegion[] incomingRegionEven = new TextureRegion[NUMBER/2];
	
	final int h;
	final int w;
	final boolean row;
	SplitTextureRegion(final TextureRegion incomingRegion,final TextureRegion outgoingRegion,boolean row){
		h = (int)(Engine.getEngineConfig().height/NUMBER);
		w = (int)(Engine.getEngineConfig().width/NUMBER);
		this.row = row;
		
		for(int i=0;i<NUMBER;i++){
			if(i%2==0){
				if(row)this.incomingRegionOdd[i/2] =  new TextureRegion(outgoingRegion.getTexture(),0,i*h, (int)Engine.getEngineConfig().width,h);
				else   this.incomingRegionOdd[i/2] =  new TextureRegion(outgoingRegion.getTexture(),i*w,0,w,(int)Engine.getEngineConfig().height);
				this.incomingRegionOdd[i/2].flip(false, true);
			}else{
				if(row)this.incomingRegionEven[i/2] =  new TextureRegion(outgoingRegion.getTexture(),0,h*i, (int)Engine.getEngineConfig().width,h);
				else   this.incomingRegionEven[i/2] =  new TextureRegion(outgoingRegion.getTexture(),i*w,0,w,(int)Engine.getEngineConfig().height);
				this.incomingRegionEven[i/2].flip(false, true);
			}
		}
		
	}
	public void render(float delta){
		for(int i=0;i<incomingRegionOdd.length;i++){
			if(row)Engine.getSpriteBatch().draw(incomingRegionOdd[i], split , (2*i)*h);
			else   Engine.getSpriteBatch().draw(incomingRegionOdd[i], (2*i)*w , split);
		}
		for(int i=0;i<incomingRegionEven.length;i++){
			if(row)Engine.getSpriteBatch().draw(incomingRegionEven[i], -split , (2*i+1)*h);
			else   Engine.getSpriteBatch().draw(incomingRegionEven[i], (2*i+1)*w,-split);
		}
	}
}