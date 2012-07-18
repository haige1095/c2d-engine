package info.u250.c2d;

import info.u250.c2d.engine.Engine;
import info.u250.c2d.tests.AnalogTest;
import info.u250.c2d.tests.AnimationBlinkTest;
import info.u250.c2d.tests.AnimationEquationsTest;
import info.u250.c2d.tests.AnimationSimpleTimelineTest;
import info.u250.c2d.tests.AnimationSimpleTintTest;
import info.u250.c2d.tests.AnimationSimpleTweenTest;
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
import info.u250.c2d.tests.animations.AdvanceSpriteShadowTest;
import info.u250.c2d.tests.animations.AnimationSpriteLoopTest;
import info.u250.c2d.tests.nehe.Nehe04_Rotation;
import info.u250.c2d.tests.nehe.Nehe05_3dShapes;
import info.u250.c2d.tests.nehe.Nehe06_TextureMapping;
import info.u250.c2d.tests.nehe.Nehe07_TextureFilters_Lighting;
import info.u250.c2d.tests.nehe.Nehe08_Blending;
import info.u250.c2d.tests.nehe.Nehe09_BitmapAnimation;
import info.u250.c2d.tests.nehe.Nehe16_Fog;
import info.u250.c2d.tests.nehe.Nehe18_Quadrics;
import info.u250.c2d.tests.nehe.Nehe23_SphereMapping;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@SuppressWarnings("rawtypes")
public class C2dTests {
	public static class TestItem {
		public TestItem(Class cls, String title, String desc, String icon) {
			super();
			this.cls = cls;
			this.title = title;
			this.desc = desc;
			this.icon = icon;
		}

		public Class cls;
		public String title;
		public String desc;
		public String icon;
	}

	public static class TestGroup {
		public TestGroup(String title, String desc, TestItem[] items) {
			super();
			this.title = title;
			this.desc = desc;
			this.items = items;
		}

		public String title;
		public String desc;
		public TestItem[] items;
	}

	public static final TestGroup[] testss = new TestGroup[] {
			new TestGroup("Animations", "the graphics animations",
					new TestItem[] {
							new TestItem(AnimationSpriteLoopTest.class,
									"Animation Sprite Loop Model",
									"Touch to stop/play the animations",
									"default"),
							new TestItem(AdvanceSpriteShadowTest.class,
									"AdvanceSprite Shadow",
									"Used a box2d body to make them moveable.",
									"default"), }),
			new TestGroup(
					"Nehe OpenglES",
					"A port of the NeHe tutorials for the Libgdx platform.",
					new TestItem[] {
							new TestItem(
									Nehe04_Rotation.class,
									"Rotation",
									"Add animation to the scene using rotations. The two objects rotate on different axes.",
									"default"),
							new TestItem(
									Nehe05_3dShapes.class,
									"3dShapes",
									"Go solid by turning the triangle into a pyramid and the square into a cube.",
									"default"),
							new TestItem(
									Nehe06_TextureMapping.class,
									"TextureMapping",
									"Render a cube textured with a bitmap image.",
									"default"),
							new TestItem(
									Nehe07_TextureFilters_Lighting.class,
									"TextureFilters Lighting",
									"Build textures with different types of filtering: nearest, linear and mipmap. Uses simple lighting.",
									"default"),
							new TestItem(
									Nehe08_Blending.class,
									"Blending",
									"Achieve the effect of transparency using blending. Introducing the 4th color component: 'alpha'.",
									"default"),
							new TestItem(
									Nehe09_BitmapAnimation.class,
									"Bitmap Animation",
									"Simple animation using blended 2D sprites. Makes heavier use of model-view transformations.",
									"default"),
							new TestItem(
									Nehe16_Fog.class,
									"Fog",
									"One of the simplest, yet most impressive OpenGL effects. Illustrates 3 different fog filters.",
									"default"),
							new TestItem(
									Nehe18_Quadrics.class,
									"Quadrics",
									"Draw complex objects like spheres, cylinders, disks and cones. Some math is used behind this.",
									"default"),
							new TestItem(
									Nehe23_SphereMapping.class,
									"Sphere Mapping",
									"Add reflections to the quadrics from lesson 18 by using sphere mapping. Some tricky math here too.",
									"default"), }), };

	public static final Class[] tests = { Box2d_SoftBodyTest.class,
			SimpleMeshBackgroundTest.class, SurfaceTest.class,
			Box2d_OutlineTest.class, FollowableParticle.class,
			Nehe23_SphereMapping.class, Nehe18_Quadrics.class,
			Nehe16_Fog.class, Nehe09_BitmapAnimation.class,
			Nehe08_Blending.class, Nehe07_TextureFilters_Lighting.class,
			Nehe06_TextureMapping.class, Nehe05_3dShapes.class,
			Nehe04_Rotation.class, LaserTest.class,
			ParallaxGroupEventsTest.class, AnalogTest.class,
			AnimationSpriteLoopTest.class, AnimationSimpleTweenTest.class,
			AnimationSimpleTimelineTest.class, AnimationEquationsTest.class,
			AnimationSimpleTintTest.class, AnimationBlinkTest.class,
			AnimationSpriteLoopWithTimesTest.class,
			Box2d_SimpleObjectTest.class, AdvanceSpriteShadowTest.class,
			ParallaxGroupGestureDetectorTest.class,
			ParallaxGroupCustomDrawableTest.class, EngineSimpleSfxTest.class,
			Box2d_PhysicTrackLineTest.class, TransitionSceneTest.class,
			Ext_luaTest.class, EventTest.class, GestureRecognizerTest.class,
			IngameLoadingTest.class, RepeatTextureBackgroundTest.class,
			SvgTest.class, TileTest.class, TinyWingsStripesTest.class, };

	public static String[] getNames() {
		List<String> names = new ArrayList<String>();
		for (TestGroup group : testss) {
			for (TestItem item : group.items) {
				Class clazz = item.cls;
				names.add(clazz.getName().replace("info.u250.c2d.tests.", ""));
			}
		}

		Collections.sort(names);
		return names.toArray(new String[names.size()]);
	}

	public static Engine newTest(String testName) {
		try {
			Class clazz = Class.forName("info.u250.c2d.tests." + testName);
			return (Engine) clazz.newInstance();
		} catch (Exception e1) {
			e1.printStackTrace();
			return null;
		}
	}

}
