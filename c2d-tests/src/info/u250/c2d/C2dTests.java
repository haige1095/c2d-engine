package info.u250.c2d;

import info.u250.c2d.engine.Engine;
import info.u250.c2d.tests.animations.ActionBLink;
import info.u250.c2d.tests.animations.ActionJump;
import info.u250.c2d.tests.animations.ActionMove;
import info.u250.c2d.tests.animations.ActionRotate;
import info.u250.c2d.tests.animations.ActionScale;
import info.u250.c2d.tests.animations.ActionShake;
import info.u250.c2d.tests.animations.ActionTint;
import info.u250.c2d.tests.animations.AdvanceSpriteShadowTest;
import info.u250.c2d.tests.animations.AnalogTest;
import info.u250.c2d.tests.animations.AnimationSpriteLoopTest;
import info.u250.c2d.tests.animations.AnimationSpriteLoopWithTimesTest;
import info.u250.c2d.tests.animations.LaserTest;
import info.u250.c2d.tests.box2d.KinematicBodiesTest;
import info.u250.c2d.tests.box2d.PhysicTrackLineTest;
import info.u250.c2d.tests.box2d.SimpleObjectTest;
import info.u250.c2d.tests.box2d.SoftBodyTest;
import info.u250.c2d.tests.box2d.WaterEffectTest;
import info.u250.c2d.tests.box2d.WebTest;
import info.u250.c2d.tests.mesh.GradientTest;
import info.u250.c2d.tests.mesh.JumpyLineTest;
import info.u250.c2d.tests.mesh.RepeatTextureBackgroundTest;
import info.u250.c2d.tests.mesh.SimpleMeshBackgroundTest;
import info.u250.c2d.tests.mesh.SurfaceTest;
import info.u250.c2d.tests.mesh.TinyWingsStripesTest;
import info.u250.c2d.tests.misc.CustomLoadingTest;
import info.u250.c2d.tests.misc.EventTest;
import info.u250.c2d.tests.misc.Ext_luaTest;
import info.u250.c2d.tests.misc.GestureRecognizerTest;
import info.u250.c2d.tests.misc.IngameLoadingTest;
import info.u250.c2d.tests.misc.SvgTest;
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
import info.u250.c2d.tests.tiled.Box2dTileTest;
import info.u250.c2d.tests.tools.MotionWelderTest;
import info.u250.c2d.tests.transitions.FadeTest;
import info.u250.c2d.tests.transitions.FadeWhiteTest;
import info.u250.c2d.tests.transitions.FlipHTest;
import info.u250.c2d.tests.transitions.FlipVTest;
import info.u250.c2d.tests.transitions.MoveInTest;
import info.u250.c2d.tests.transitions.RotateTest;
import info.u250.c2d.tests.transitions.RotateWithZoomInTest;
import info.u250.c2d.tests.transitions.SlideInTest;
import info.u250.c2d.tests.transitions.SplitTest;
import info.u250.c2d.tests.transitions.ZoomInTest;
import info.u250.c2d.tests.transitions.ZoomOutTest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings("rawtypes")
public class C2dTests {
	
	public static final List<Map<String,Object>> tests = new ArrayList<Map<String,Object>>();
	static{
		{
			Map<String,Object> map = new HashMap<String, Object>();
			map.put("title", "Animations");
			map.put("desc", "the graphics animations");
			map.put("image", "drawable/animations");
			final List<Map<String,Object>> subs = new ArrayList<Map<String,Object>>();
			{
				Map<String,Object> sub = new HashMap<String, Object>();
				sub.put("title", "Action Tint");
				sub.put("desc", "a tint action for the animation sprite or the images.");
				sub.put("image", "drawable/item");
				sub.put("cls", ActionTint.class.getName());
				subs.add(sub);
			}
			{
				Map<String,Object> sub = new HashMap<String, Object>();
				sub.put("title", "Action Blink");
				sub.put("desc", "a blink action for the animation sprite or the images.");
				sub.put("image", "drawable/item");
				sub.put("cls", ActionBLink.class.getName());
				subs.add(sub);
			}
			{
				Map<String,Object> sub = new HashMap<String, Object>();
				sub.put("title", "Action Move");
				sub.put("desc", "a move action for the animation sprite or the images.");
				sub.put("image", "drawable/item");
				sub.put("cls", ActionMove.class.getName());
				subs.add(sub);
			}
			{
				Map<String,Object> sub = new HashMap<String, Object>();
				sub.put("title", "Action Shake");
				sub.put("desc", "a shake action for the animation sprite or the images.");
				sub.put("image", "drawable/item");
				sub.put("cls", ActionShake.class.getName());
				subs.add(sub);
			}
			{
				Map<String,Object> sub = new HashMap<String, Object>();
				sub.put("title", "Action Rotate");
				sub.put("desc", "a rotate action for the animation sprite or the images.");
				sub.put("image", "drawable/item");
				sub.put("cls", ActionRotate.class.getName());
				subs.add(sub);
			}
			{
				Map<String,Object> sub = new HashMap<String, Object>();
				sub.put("title", "Action Scale");
				sub.put("desc", "a scale action for the animation sprite or the images.");
				sub.put("image", "drawable/item");
				sub.put("cls", ActionScale.class.getName());
				subs.add(sub);
			}
			{
				Map<String,Object> sub = new HashMap<String, Object>();
				sub.put("title", "Action Jump");
				sub.put("desc", "a jump action for the animation sprite or the images.");
				sub.put("image", "drawable/item");
				sub.put("cls", ActionJump.class.getName());
				subs.add(sub);
			}
			{
				Map<String,Object> sub = new HashMap<String, Object>();
				sub.put("title", "Animation Sprite Loop Model");
				sub.put("desc", "Touch to stop/play the animations");
				sub.put("image", "drawable/item");
				sub.put("cls", AnimationSpriteLoopTest.class.getName());
				subs.add(sub);
			}
			{
				Map<String,Object> sub = new HashMap<String, Object>();
				sub.put("title", "Animation Sprite Loop With Time Model");
				sub.put("desc", "Touch to stop/play the animations. it has different frame durations");
				sub.put("image", "drawable/item");
				sub.put("cls", AnimationSpriteLoopWithTimesTest.class.getName());
				subs.add(sub);
			}
			{
				Map<String,Object> sub = new HashMap<String, Object>();
				sub.put("title", "AdvanceSprite Shadow");
				sub.put("desc", "Used a box2d body to make them moveable.");
				sub.put("image", "drawable/item");
				sub.put("cls", AdvanceSpriteShadowTest.class.getName());
				subs.add(sub);
			}
			{
				Map<String,Object> sub = new HashMap<String, Object>();
				sub.put("title", "Analog");
				sub.put("desc", "This is a simple analog widget , via this you can get the value and the directions");
				sub.put("image", "drawable/item");
				sub.put("cls", AnalogTest.class.getName());
				subs.add(sub);
			}
			{
				Map<String,Object> sub = new HashMap<String, Object>();
				sub.put("title", "Laser");
				sub.put("desc", "a simple laser effect");
				sub.put("image", "drawable/item");
				sub.put("cls", LaserTest.class.getName());
				subs.add(sub);
			}
			map.put("subs", subs);
			tests.add(map);
		}
		//
		{
			Map<String,Object> map = new HashMap<String, Object>();
			map.put("title", "Parallax Layer");
			map.put("desc", "Parallax texture or layer . fill the screen");
			map.put("image", "drawable/parallax");
			final List<Map<String,Object>> subs = new ArrayList<Map<String,Object>>();
			{
				Map<String,Object> sub = new HashMap<String,Object>();
				sub.put("title", "Custom Drawable");
				sub.put("desc", "Custom drawable of the layer use tiled render");
				sub.put("image", "drawable/item");
				sub.put("cls", CustomDrawableTest.class.getName());
				subs.add(sub);
			}
			{
				Map<String,Object> sub = new HashMap<String,Object>();
				sub.put("title", "Parallax Events");
				sub.put("desc", "Events suchas speed , day to night ,shake etc.");
				sub.put("image", "drawable/item");
				sub.put("cls", ParallaxGroupEventsTest.class.getName());
				subs.add(sub);
			}
			{
				Map<String,Object> sub = new HashMap<String,Object>();
				sub.put("title", "Parallax Gesture Detector");
				sub.put("desc", "Scroll the parallax layers. fling the screen .");
				sub.put("image", "drawable/item");
				sub.put("cls", ParallaxGroupGestureDetectorTest.class.getName());
				subs.add(sub);
			}
			map.put("subs", subs);
			tests.add(map);
		}
		//
		{
			Map<String,Object> map = new HashMap<String, Object>();
			map.put("title", "Box2d");
			map.put("desc", "Physical Engine . How to use the c2d box2d");
			map.put("image", "drawable/box2d");
			final List<Map<String,Object>> subs = new ArrayList<Map<String,Object>>();
			{
				Map<String,Object> sub = new HashMap<String,Object>();
				sub.put("title", "A Simple Example");
				sub.put("desc", "Just a simple to show the box and the circle");
				sub.put("image", "drawable/item");
				sub.put("cls", SimpleObjectTest.class.getName());
				subs.add(sub);
			}
			{
				Map<String,Object> sub = new HashMap<String,Object>();
				sub.put("title", "Track Line");
				sub.put("desc", "How to make a track line like birds");
				sub.put("image", "drawable/item");
				sub.put("cls", PhysicTrackLineTest.class.getName());
				subs.add(sub);
			}
			{
				Map<String,Object> sub = new HashMap<String,Object>();
				sub.put("title", "Soft Body");
				sub.put("desc", "How to make a soft body use spring force");
				sub.put("image", "drawable/item");
				sub.put("cls", SoftBodyTest.class.getName());
				subs.add(sub);
			}
			{
				Map<String,Object> sub = new HashMap<String,Object>();
				sub.put("title", "Web");
				sub.put("desc", "there is a spring force between them");
				sub.put("image", "drawable/item");
				sub.put("cls", WebTest.class.getName());
				subs.add(sub);
			}
			{
				Map<String,Object> sub = new HashMap<String,Object>();
				sub.put("title", "Water effect");
				sub.put("desc", "the water effect use the box2d. can't use this tool with OpenGL ES 1.x ");
				sub.put("image", "drawable/item");
				sub.put("cls", WaterEffectTest.class.getName());
				subs.add(sub);
			}
			{
				Map<String,Object> sub = new HashMap<String,Object>();
				sub.put("title", "Kinematic Bodies");
				sub.put("desc", "A kinematic body is an hybrid body which is not affected by forces and collisions like a static body but can moved with a linear velocity like a dynamic body. ");
				sub.put("image", "drawable/item");
				sub.put("cls", KinematicBodiesTest.class.getName());
				subs.add(sub);
			}
			map.put("subs", subs);
			tests.add(map);
		}
		//
		{
			Map<String,Object> map = new HashMap<String, Object>();
			map.put("title", "Mesh");
			map.put("desc", "Some advance effect that use the mesh(OpenglES)");
			map.put("image", "drawable/mesh");
			final List<Map<String,Object>> subs = new ArrayList<Map<String,Object>>();
			{
				Map<String,Object> sub = new HashMap<String,Object>();
				sub.put("title", "Surface : Repeated");
				sub.put("desc", "Use a texture to fill the points .");
				sub.put("image", "drawable/item");
				sub.put("cls", SurfaceTest.class.getName());
				subs.add(sub);
			}
			{
				Map<String,Object> sub = new HashMap<String,Object>();
				sub.put("title", "TinyWings like Stripes");
				sub.put("desc", "draw stripes like tinywings");
				sub.put("image", "drawable/item");
				sub.put("cls", TinyWingsStripesTest.class.getName());
				subs.add(sub);
			}
			{
				Map<String,Object> sub = new HashMap<String, Object>();
				sub.put("title", "Repeat texture background");
				sub.put("desc", "A small texture to fill the screen");
				sub.put("image", "drawable/item");
				sub.put("cls", RepeatTextureBackgroundTest.class.getName());
				subs.add(sub);
			}
			{
				Map<String,Object> sub = new HashMap<String, Object>();
				sub.put("title", "Simple Mesh Background");
				sub.put("desc", "Rectangle mesh background .");
				sub.put("image", "drawable/item");
				sub.put("cls", SimpleMeshBackgroundTest.class.getName());
				subs.add(sub);
			}
			{
				Map<String,Object> sub = new HashMap<String, Object>();
				sub.put("title", "Gradient");
				sub.put("desc", "Gradient effect use the ImmediateModeRenderer .");
				sub.put("image", "drawable/item");
				sub.put("cls", GradientTest.class.getName());
				subs.add(sub);
			}
			{
				Map<String,Object> sub = new HashMap<String, Object>();
				sub.put("title", "Jumpy line");
				sub.put("desc", " A line that jump jump jump .");
				sub.put("image", "drawable/item");
				sub.put("cls", JumpyLineTest.class.getName());
				subs.add(sub);
			}
			map.put("subs", subs);
			tests.add(map);
		}
		//
		{
			Map<String,Object> map = new HashMap<String, Object>();
			map.put("title", "Particle");
			map.put("desc", "Some particle effects");
			map.put("image", "drawable/particle");
			final List<Map<String,Object>> subs = new ArrayList<Map<String,Object>>();
			{
				Map<String,Object> sub = new HashMap<String,Object>();
				sub.put("title", "Followable Particle");
				sub.put("desc", "The particle will follow your finger");
				sub.put("image", "drawable/item");
				sub.put("cls", FollowableParticle.class.getName());
				subs.add(sub);
			}
			map.put("subs", subs);
			tests.add(map);
		}
		//
		{
			Map<String,Object> map = new HashMap<String, Object>();
			map.put("title", "Tools");
			map.put("desc", "thrid tools that we supported");
			map.put("image", "drawable/tools");
			final List<Map<String,Object>> subs = new ArrayList<Map<String,Object>>();
			{
				Map<String,Object> sub = new HashMap<String,Object>();
				sub.put("title", "Motion Welder");
				sub.put("desc", "Motion Welder animation design tool");
				sub.put("image", "drawable/item");
				sub.put("cls", MotionWelderTest.class.getName());
				subs.add(sub);
			}
			map.put("subs", subs);
			tests.add(map);
		}
		//
		{
			Map<String,Object> map = new HashMap<String, Object>();
			map.put("title", "Sfx");
			map.put("desc", "Sounds and musics");
			map.put("image", "drawable/sfx");
			final List<Map<String,Object>> subs = new ArrayList<Map<String,Object>>();
			{
				Map<String,Object> sub = new HashMap<String,Object>();
				sub.put("title", "Simple Sfx");
				sub.put("desc", "Play a sound and a music");
				sub.put("image", "drawable/item");
				sub.put("cls", EngineSimpleSfxTest.class.getName());
				subs.add(sub);
			}
			map.put("subs", subs);
			tests.add(map);
		}
		//
		{
			Map<String,Object> map = new HashMap<String, Object>();
			map.put("title", "Transitions");
			map.put("desc", "Some transition effects to switch scenes");
			map.put("image", "drawable/transitions");
			final List<Map<String,Object>> subs = new ArrayList<Map<String,Object>>();
			{
				Map<String,Object> sub = new HashMap<String,Object>();
				sub.put("title", "Fade");
				sub.put("desc", "the first scene fade out , and then the second scene fade in");
				sub.put("image", "drawable/item");
				sub.put("cls", FadeTest.class.getName());
				subs.add(sub);
			}
			{
				Map<String,Object> sub = new HashMap<String,Object>();
				sub.put("title", "Fade White");
				sub.put("desc", "The  type 'Fade white'");
				sub.put("image", "drawable/item");
				sub.put("cls", FadeWhiteTest.class.getName());
				subs.add(sub);
			}
			{
				Map<String,Object> sub = new HashMap<String,Object>();
				sub.put("title", "Move in");
				sub.put("desc", "The  type 'move in' from different directions");
				sub.put("image", "drawable/item");
				sub.put("cls", MoveInTest.class.getName());
				subs.add(sub);
			}
			{
				Map<String,Object> sub = new HashMap<String,Object>();
				sub.put("title", "Slide in");
				sub.put("desc", "The  type 'slide in' from different directions");
				sub.put("image", "drawable/item");
				sub.put("cls", SlideInTest.class.getName());
				subs.add(sub);
			}
			{
				Map<String,Object> sub = new HashMap<String,Object>();
				sub.put("title", "Rotate");
				sub.put("desc", "The  type 'rotate'");
				sub.put("image", "drawable/item");
				sub.put("cls", RotateTest.class.getName());
				subs.add(sub);
			}
			{
				Map<String,Object> sub = new HashMap<String,Object>();
				sub.put("title", "Zoom out");
				sub.put("desc", "The  type 'zoom out'");
				sub.put("image", "drawable/item");
				sub.put("cls", ZoomOutTest.class.getName());
				subs.add(sub);
			}
			{
				Map<String,Object> sub = new HashMap<String,Object>();
				sub.put("title", "Zoom in");
				sub.put("desc", "the first scene zoom in , and then the second scene zoom out");
				sub.put("image", "drawable/item");
				sub.put("cls", ZoomInTest.class.getName());
				subs.add(sub);
			}
			{
				Map<String,Object> sub = new HashMap<String,Object>();
				sub.put("title", "Zoom in with rotate");
				sub.put("desc", "The  type 'zoom in with rotate'");
				sub.put("image", "drawable/item");
				sub.put("cls", RotateWithZoomInTest.class.getName());
				subs.add(sub);
			}
			{
				Map<String,Object> sub = new HashMap<String,Object>();
				sub.put("title", "Flip Horizontal");
				sub.put("desc", "Transition Scene Flip Horizontal");
				sub.put("image", "drawable/item");
				sub.put("cls", FlipHTest.class.getName());
				subs.add(sub);
			}
			{
				Map<String,Object> sub = new HashMap<String,Object>();
				sub.put("title", "Flip Vertical");
				sub.put("desc", "Transition Scene Flip Vertical");
				sub.put("image", "drawable/item");
				sub.put("cls", FlipVTest.class.getName());
				subs.add(sub);
			}
			{
				Map<String,Object> sub = new HashMap<String,Object>();
				sub.put("title", "Split");
				sub.put("desc", "split the first scene rows or clos and move them out , finally show the second scene");
				sub.put("image", "drawable/item");
				sub.put("cls", SplitTest.class.getName());
				subs.add(sub);
			}
			map.put("subs", subs);
			tests.add(map);
		}
		//
		{
			Map<String,Object> map = new HashMap<String, Object>();
			map.put("title", "The Tiledmap");
			map.put("desc", "tiledmap");
			map.put("image", "drawable/tiled");
			final List<Map<String,Object>> subs = new ArrayList<Map<String,Object>>();
			{
				Map<String,Object> sub = new HashMap<String,Object>();
				sub.put("title", "Box2d TiledMap");
				sub.put("desc", "example: how to make a pysicalable tiledmap");
				sub.put("image", "drawable/item");
				sub.put("cls", Box2dTileTest.class.getName());
				subs.add(sub);
			}
			map.put("subs", subs);
			tests.add(map);
		}
		//
		{
			Map<String,Object> map = new HashMap<String, Object>();
			map.put("title", "Other");
			map.put("desc", "Other misc . Some may not run on your device");
			map.put("image", "drawable/item");
			final List<Map<String,Object>> subs = new ArrayList<Map<String,Object>>();
			{
				Map<String,Object> sub = new HashMap<String,Object>();
				sub.put("title", "Custom Loading");
				sub.put("desc", "a example : how to make a custom loading screen, it's simple");
				sub.put("image", "drawable/item");
				sub.put("cls", CustomLoadingTest.class.getName());
				subs.add(sub);
			}
			{
				Map<String,Object> sub = new HashMap<String,Object>();
				sub.put("title", "InGame Loading");
				sub.put("desc", "how to load other resources after start the game and replace some resources");
				sub.put("image", "drawable/item");
				sub.put("cls", IngameLoadingTest.class.getName());
				subs.add(sub);
			}
			{
				Map<String,Object> sub = new HashMap<String,Object>();
				sub.put("title", "Ext Lua");
				sub.put("desc", "load lua script and execute it");
				sub.put("image", "drawable/item");
				sub.put("cls", Ext_luaTest.class.getName());
				subs.add(sub);
			}
			{
				Map<String,Object> sub = new HashMap<String,Object>();
				sub.put("title", "Ext SVG");
				sub.put("desc", "load svg file and render it . this may take some times to gen the image");
				sub.put("image", "drawable/item");
				sub.put("cls", SvgTest.class.getName());
				subs.add(sub);
			}
			{
				Map<String,Object> sub = new HashMap<String,Object>();
				sub.put("title", "Event");
				sub.put("desc", "how to use the simple built in event system . ");
				sub.put("image", "drawable/item");
				sub.put("cls", EventTest.class.getName());
				subs.add(sub);
			}
			{
				Map<String,Object> sub = new HashMap<String,Object>();
				sub.put("title", "Gesture Recognizer");
				sub.put("desc", "load a json file  ");
				sub.put("image", "drawable/item");
				sub.put("cls", GestureRecognizerTest.class.getName());
				subs.add(sub);
			}
			map.put("subs", subs);
			tests.add(map);
		}
		//
		{
			Map<String,Object> map = new HashMap<String, Object>();
			map.put("title", "Nehe OpenglES");
			map.put("desc", "A port of the NeHe tutorials for the Libgdx platform.");
			map.put("image", "drawable/nehe");
			final List<Map<String,Object>> subs = new ArrayList<Map<String,Object>>();
			{
				Map<String,Object> sub = new HashMap<String,Object>();
				sub.put("title", "Rotation");
				sub.put("desc", "Add animation to the scene using rotations. The two objects rotate on different axes.");
				sub.put("image", "drawable/item");
				sub.put("cls", Nehe04_Rotation.class.getName());
				subs.add(sub);
			}
			{
				Map<String,Object> sub = new HashMap<String,Object>();
				sub.put("title", "3dShapes");
				sub.put("desc", "Go solid by turning the triangle into a pyramid and the square into a cube.");
				sub.put("image", "drawable/item");
				sub.put("cls", Nehe05_3dShapes.class.getName());
				subs.add(sub);
			}
			{
				Map<String,Object> sub = new HashMap<String,Object>();
				sub.put("title", "TextureMapping");
				sub.put("desc", "Render a cube textured with a bitmap image.");
				sub.put("image", "drawable/item");
				sub.put("cls", Nehe06_TextureMapping.class.getName());
				subs.add(sub);
			}
			{
				Map<String,Object> sub = new HashMap<String,Object>();
				sub.put("title", "TextureFilters Lighting");
				sub.put("desc", "Build textures with different types of filtering: nearest, linear and mipmap. Uses simple lighting.");
				sub.put("image", "drawable/item");
				sub.put("cls", Nehe07_TextureFilters_Lighting.class.getName());
				subs.add(sub);
			}
			{
				Map<String,Object> sub = new HashMap<String,Object>();
				sub.put("title", "Blending");
				sub.put("desc", "Achieve the effect of transparency using blending. Introducing the 4th color component: 'alpha'.");
				sub.put("image", "drawable/item");
				sub.put("cls", Nehe08_Blending.class.getName());
				subs.add(sub);
			}
			{
				Map<String,Object> sub = new HashMap<String,Object>();
				sub.put("title", "Bitmap Animation");
				sub.put("desc", "Simple animation using blended 2D sprites. Makes heavier use of model-view transformations.");
				sub.put("image", "drawable/item");
				sub.put("cls", Nehe09_BitmapAnimation.class.getName());
				subs.add(sub);
			}
			{
				Map<String,Object> sub = new HashMap<String,Object>();
				sub.put("title", "Fog");
				sub.put("desc", "One of the simplest, yet most impressive OpenGL effects. Illustrates 3 different fog filters.");
				sub.put("image", "drawable/item");
				sub.put("cls", Nehe16_Fog.class.getName());
				subs.add(sub);
			}
			{
				Map<String,Object> sub = new HashMap<String,Object>();
				sub.put("title", "Quadrics");
				sub.put("desc", "Draw complex objects like spheres, cylinders, disks and cones. Some math is used behind this.");
				sub.put("image", "drawable/item");
				sub.put("cls", Nehe18_Quadrics.class.getName());
				subs.add(sub);
			}
			{
				Map<String,Object> sub = new HashMap<String,Object>();
				sub.put("title", "Sphere Mapping");
				sub.put("desc", "Add reflections to the quadrics from lesson 18 by using sphere mapping. Some tricky math here too.");
				sub.put("image", "drawable/item");
				sub.put("cls", Nehe23_SphereMapping.class.getName());
				subs.add(sub);
			}
			map.put("subs", subs);
			tests.add(map);
		}
	}
	
	@SuppressWarnings("unchecked")
	public static String[] getNames() {
		List<String> names = new ArrayList<String>();
		for (Map<String,Object> group : tests) {
			for (Map<String, Object> item : (List<Map<String,Object>>)group.get("subs")) {
				names.add(item.get("cls").toString().replace("info.u250.c2d.tests.", ""));
			}
		}

		Collections.sort(names);
		return names.toArray(new String[names.size()]);
	}

	public static Engine newTest(String testName) {
		return newTestFullName("info.u250.c2d.tests." + testName);
	}
	public static Engine newTestFullName(String testName) {
		try {
			Class clazz = Class.forName(testName);
			return (Engine) clazz.newInstance();
		} catch (Exception e1) {
			e1.printStackTrace();
			return null;
		}
	}

}
