package info.u250.c2d;

import info.u250.c2d.engine.Engine;
import info.u250.c2d.tests.AdvanceSpriteShadowTest;
import info.u250.c2d.tests.AnalogTest;
import info.u250.c2d.tests.AnimationBlinkTest;
import info.u250.c2d.tests.AnimationEquationsTest;
import info.u250.c2d.tests.AnimationSimpleTimelineTest;
import info.u250.c2d.tests.AnimationSimpleTintTest;
import info.u250.c2d.tests.AnimationSimpleTweenTest;
import info.u250.c2d.tests.AnimationSpriteLoopTest;
import info.u250.c2d.tests.AnimationSpriteLoopWithTimesTest;
import info.u250.c2d.tests.Box2d_OutlineTest;
import info.u250.c2d.tests.Box2d_PhysicTrackLineTest;
import info.u250.c2d.tests.Box2d_SimpleObjectTest;
import info.u250.c2d.tests.Box2d_SoftBodyTest;
import info.u250.c2d.tests.EngineSimpleSfxTest;
import info.u250.c2d.tests.EventTest;
import info.u250.c2d.tests.Ext_luaTest;
import info.u250.c2d.tests.FollowableParticle;
import info.u250.c2d.tests.GestureRecognizerTest;
import info.u250.c2d.tests.IngameLoadingTest;
import info.u250.c2d.tests.LaserTest;
import info.u250.c2d.tests.Nehe04_Rotation;
import info.u250.c2d.tests.Nehe05_3dShapes;
import info.u250.c2d.tests.Nehe06_TextureMapping;
import info.u250.c2d.tests.Nehe07_TextureFilters_Lighting;
import info.u250.c2d.tests.Nehe08_Blending;
import info.u250.c2d.tests.Nehe09_BitmapAnimation;
import info.u250.c2d.tests.Nehe16_Fog;
import info.u250.c2d.tests.Nehe18_Quadrics;
import info.u250.c2d.tests.Nehe23_SphereMapping;
import info.u250.c2d.tests.ParallaxGroupCustomDrawableTest;
import info.u250.c2d.tests.ParallaxGroupEventsTest;
import info.u250.c2d.tests.ParallaxGroupGestureDetectorTest;
import info.u250.c2d.tests.RepeatTextureBackgroundTest;
import info.u250.c2d.tests.SimpleMeshBackgroundTest;
import info.u250.c2d.tests.SurfaceTest;
import info.u250.c2d.tests.SvgTest;
import info.u250.c2d.tests.TileTest;
import info.u250.c2d.tests.TinyWingsStripesTest;
import info.u250.c2d.tests.TransitionSceneTest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@SuppressWarnings("rawtypes")
public class C2dTests {
	
	public static final Class[] tests = {
		Box2d_SoftBodyTest.class,
		SimpleMeshBackgroundTest.class,
		SurfaceTest.class,
		Box2d_OutlineTest.class,
		FollowableParticle.class,
		Nehe23_SphereMapping.class,
		Nehe18_Quadrics.class,
		Nehe16_Fog.class,
		Nehe09_BitmapAnimation.class,
		Nehe08_Blending.class,
		Nehe07_TextureFilters_Lighting.class,
		Nehe06_TextureMapping.class,
		Nehe05_3dShapes.class,
		Nehe04_Rotation.class,
		LaserTest.class,
		ParallaxGroupEventsTest.class,
		AnalogTest.class,
		AnimationSpriteLoopTest.class,
		AnimationSimpleTweenTest.class,
		AnimationSimpleTimelineTest.class,
		AnimationEquationsTest.class,
		AnimationSimpleTintTest.class,
		AnimationBlinkTest.class,
		AnimationSpriteLoopWithTimesTest.class,
		Box2d_SimpleObjectTest.class,
		AdvanceSpriteShadowTest.class,
		ParallaxGroupGestureDetectorTest.class,
		ParallaxGroupCustomDrawableTest.class,
		EngineSimpleSfxTest.class,
		Box2d_PhysicTrackLineTest.class,
		TransitionSceneTest.class,
		Ext_luaTest.class,
		EventTest.class,
		GestureRecognizerTest.class,
		IngameLoadingTest.class,
		RepeatTextureBackgroundTest.class,
		SvgTest.class,
		TileTest.class,
		TinyWingsStripesTest.class,
	};

	public static String[] getNames () {
		List<String> names = new ArrayList<String>();
		for (Class clazz : tests)
			names.add(clazz.getSimpleName());
		Collections.sort(names);
		return names.toArray(new String[names.size()]);
	}
	public static Engine newTest (String testName) {
		try {
			Class clazz = Class.forName("info.u250.c2d.tests." + testName);
			return (Engine)clazz.newInstance();
		} catch (Exception e1) {
			e1.printStackTrace();
			return null;
		}
	}
	
}
