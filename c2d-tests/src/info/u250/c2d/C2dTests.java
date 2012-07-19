package info.u250.c2d;

import info.u250.c2d.engine.Engine;
import info.u250.c2d.tests.animations.AdvanceSpriteShadowTest;
import info.u250.c2d.tests.animations.AnimationSpriteLoopTest;
import info.u250.c2d.tests.backgrounds.RepeatTextureBackgroundTest;
import info.u250.c2d.tests.backgrounds.SimpleMeshBackgroundTest;
import info.u250.c2d.tests.box2d.OutlineTest;
import info.u250.c2d.tests.box2d.PhysicTrackLineTest;
import info.u250.c2d.tests.box2d.SimpleObjectTest;
import info.u250.c2d.tests.box2d.SoftBodyTest;
import info.u250.c2d.tests.mesh.SurfaceTest;
import info.u250.c2d.tests.mesh.TinyWingsStripesTest;
import info.u250.c2d.tests.nehe.Nehe04_Rotation;
import info.u250.c2d.tests.nehe.Nehe05_3dShapes;
import info.u250.c2d.tests.nehe.Nehe06_TextureMapping;
import info.u250.c2d.tests.nehe.Nehe07_TextureFilters_Lighting;
import info.u250.c2d.tests.nehe.Nehe08_Blending;
import info.u250.c2d.tests.nehe.Nehe09_BitmapAnimation;
import info.u250.c2d.tests.nehe.Nehe16_Fog;
import info.u250.c2d.tests.nehe.Nehe18_Quadrics;
import info.u250.c2d.tests.nehe.Nehe23_SphereMapping;
import info.u250.c2d.tests.parallax.CustomDrawableTest;
import info.u250.c2d.tests.parallax.ParallaxGroupEventsTest;
import info.u250.c2d.tests.parallax.ParallaxGroupGestureDetectorTest;
import info.u250.c2d.tests.particle.FollowableParticle;
import info.u250.c2d.tests.sfx.EngineSimpleSfxTest;

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

	public static final TestGroup[] tests = new TestGroup[] {
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
					"Backgrounds",
					"Simple background that not texture",
					new TestItem[] {
							new TestItem(RepeatTextureBackgroundTest.class,
									"Repeat texture background",
									"A small texture to fill the screen",
									"default"),
							new TestItem(SimpleMeshBackgroundTest.class,
									"Simple Mesh Background",
									"Rectangle mesh background .", "default"), }),
			new TestGroup(
					"Parallax Layer",
					"Parallax texture or layer . fill the screen",
					new TestItem[] {
							new TestItem(
									CustomDrawableTest.class,
									"Custom Drawable",
									"Custom drawable of the layer use tiled render",
									"default"),
							new TestItem(
									ParallaxGroupEventsTest.class,
									"Parallax Events",
									"Events suchas speed , day to night ,shake etc.",
									"default"),
							new TestItem(
									ParallaxGroupGestureDetectorTest.class,
									"Parallax Gesture Detector",
									"Scroll the parallax layers. fling the screen .",
									"default"), }),
			new TestGroup(
					"Box2d",
					"Physical Engine . How to use the c2d box2d",
					new TestItem[] {
							new TestItem(
									SimpleObjectTest.class,
									"A Simple",
									"Just a simple to show the box and the circle",
									"default"),
							new TestItem(PhysicTrackLineTest.class,
									"Track Line",
									"How to make a track line like birds",
									"default"),
							new TestItem(OutlineTest.class, "Texture Outline",
									"Texture Outline", "default"),
							new TestItem(SoftBodyTest.class, "Soft Body",
									"How to make a soft body use spring force",
									"default"), }),
			new TestGroup(
					"Mesh",
					"Some advance effect that use the mesh(OpenglES)",
					new TestItem[] {
							new TestItem(
									SurfaceTest.class,
									"Surface : Repeated",
									"Use a texture to fill the points .",
									"default"),
							new TestItem(TinyWingsStripesTest.class,
									"TinyWings like Stripes",
									"draw stripes like tinywings",
									"default"),
							new TestItem(OutlineTest.class, "Texture Outline",
									"Texture Outline", "default"),
							new TestItem(SoftBodyTest.class, "Soft Body",
									"How to make a soft body use spring force",
									"default"), }),
			new TestGroup(
					"Particle",
					"Some particle effects",
					new TestItem[] { new TestItem(FollowableParticle.class,
							"Followable Particle",
							"The particle will follow your finger", "default"), }),
			new TestGroup(
					"Sfx",
					"Sounds and musics",
					new TestItem[] { new TestItem(EngineSimpleSfxTest.class,
							"Simple Sfx", "Play a sound and a music", "default"), }),
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
									"default"), }),

			new TestGroup("Other",
					"Other misc . Some may not run on your device",
					new TestItem[] {
							new TestItem(AnimationSpriteLoopTest.class,
									"Animation Sprite Loop Model",
									"Touch to stop/play the animations",
									"default"),
							new TestItem(AdvanceSpriteShadowTest.class,
									"AdvanceSprite Shadow",
									"Used a box2d body to make them moveable.",
									"default"), }), };

	public static String[] getNames() {
		List<String> names = new ArrayList<String>();
		for (TestGroup group : tests) {
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
