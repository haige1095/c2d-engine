# Introduction #

C2d has a build  in animation sprite class to achive Animation . The source is https://code.google.com/p/c2d-engine/source/browse/trunk/c2d/src/info/u250/c2d/graphic/AnimationSprite.java

Including the specified uniform frame and rate animation and animation. To use this animation, you have to provide a time series-time and animation sequences. The sequences can be NULL if you need , but the first frame can not be NULL


# How to use #

  * load the resources:
```
@Override
public EngineOptions onSetupEngine() {
	return new EngineOptions(new String[]{"data/animationsprite/"},480,320);
}
@Override
public void onResourcesRegister(AliasResourceManager<String> reg) {
	reg.textureAtlas("ParrotAtlas",  "data/animationsprite/pack/pack");
}
```

## common animation ##
  * create the animation , which has a frameDuration **0.05f**, and will use the texureRegions that the name begin with **parrot000**
```
sprite = new AnimationSprite(0.05f, Engine.resource("ParrotAtlas",TextureAtlas.class),"parrot000");
```
  * draw the sprite to the spritebatch.
```
Engine.getSpriteBatch().begin();
sprite.render( delta);
Engine.getSpriteBatch().end();
```
## with loop times ##
  * every frame has different duration . this animation will only loop **2** times , and when it stop , the waiting frame is **4(parrot0005)**
```
TextureAtlas atlas = Engine.resource("ParrotAtlas");
sprite = new AnimationSprite(new float[]{
				0.1f,
				0.1f,
				0.02f,
				0.05f,
				0.04f,
				0.05f,
				0.09f,
				0.01f,
				0.02f,
		}, new TextureRegion[]{
				atlas.findRegion("parrot0001"),
				atlas.findRegion("parrot0002"),
				atlas.findRegion("parrot0003"),
				atlas.findRegion("parrot0004"),
				atlas.findRegion("parrot0005"),
				atlas.findRegion("parrot0006"),
				atlas.findRegion("parrot0007"),
				atlas.findRegion("parrot0008"),
				atlas.findRegion("parrot0009"),
				atlas.findRegion("parrot0010"),
		});
sprite.setLoopTimes(2);
sprite.setWaitingIndex(4);
```
## more operations ##
  * play or replay the animation
```
sprite.play();
sprite.replay();
```
  * stop the animation
```
sprite.stop();
```
  * set the alpha value of the animation's color
```
sprite.setAlphaModulation(0.2f);
```
  * change the frame duration at runtime
```
changeFrameDuration(float frameDuration)
```
or
```
changeFrameDuration(float[] frameDurations)
```
  * set the on complete listener
```
setAnimationSpriteListener(l)
```
# resources #
exmple files:
  1. https://code.google.com/p/c2d-engine/source/browse/trunk/c2d-tests/src/info/u250/c2d/tests/AnimationSpriteLoopTest.java
  1. https://code.google.com/p/c2d-engine/source/browse/trunk/c2d-tests/src/info/u250/c2d/tests/AnimationSpriteLoopWithTimesTest.java

The resources used :

http://c2d-engine.googlecode.com/svn/trunk/c2d-tests-android/assets/data/animationsprite/turkey.atlas

![http://c2d-engine.googlecode.com/svn/trunk/c2d-tests-android/assets/data/animationsprite//turkey.png](http://c2d-engine.googlecode.com/svn/trunk/c2d-tests-android/assets/data/animationsprite//turkey.png)