package info.u250.c2d.tests.animations;

import info.u250.c2d.engine.Engine;
import info.u250.c2d.engine.EngineDrive;
import info.u250.c2d.engine.Scene;
import info.u250.c2d.engine.resources.AliasResourceManager;
import info.u250.c2d.graphic.AdvanceSprite;
import info.u250.c2d.physical.box2d.Cb2Object;
import info.u250.c2d.physical.box2d.Cb2Object.Cb2ObjectSetupCallback;
import info.u250.c2d.physical.box2d.Cb2ObjectGroup;
import info.u250.c2d.physical.box2d.Cb2World;

import java.util.Random;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;

public class AdvanceSpriteShadowTest extends Engine {

	@Override
	protected EngineDrive onSetupEngineDrive() {
		return new EngineX();
	}

	private class EngineX implements EngineDrive {
		@Override
		public void onResourcesRegister(AliasResourceManager<String> reg) {
			reg.texture("red", "data/red.png");
			reg.texture("blue", "data/blue.png");
			reg.texture("green", "data/green.png");
			reg.texture("yellow", "data/yellow.png");
		}

		@Override
		public void dispose() {
		}

		@Override
		public EngineOptions onSetupEngine() {
			return new EngineOptions(new String[] { "data/red.png","data/yellow.png","data/blue.png","data/green.png" }, 800,480);
		}

		Cb2ObjectGroup group = new Cb2ObjectGroup();
		@Override
		public void onLoadedResourcesCompleted() {
			final Box2DDebugRenderer box2dRender = new Box2DDebugRenderer(true, true, true, true, true);

			Cb2World.getInstance().installDefaultWorld().createScreenBox();

			{
				AdvanceSprite sprite = new AdvanceSprite(Engine.resource("red",Texture.class)).enableShadow();
				sprite.setScale(0.5f);
	
				group.add(new Cb2Object(sprite,true,new Cb2ObjectSetupCallback() {
					@Override
					public void before(Cb2Object obj) {
						obj.data.restitution = 1;
						obj.data.friction = 0;
					}
					@Override
					public void after(Cb2Object obj) {
						obj.setPosition(400, 400);
						obj.data.body.setLinearVelocity(
								new Random().nextFloat() * 5,
								new Random().nextFloat() * 5);
					}
				}));
			}
			{
				AdvanceSprite sprite = new AdvanceSprite(Engine.resource("yellow",
						Texture.class)).enableShadow();
				sprite.setScale(1f);
	
				group.add(new Cb2Object(sprite,true,new Cb2ObjectSetupCallback() {
					@Override
					public void before(Cb2Object obj) {
						obj.data.restitution = 1;
						obj.data.friction = 0;
					}
					@Override
					public void after(Cb2Object obj) {
						obj.setPosition(400, 400);
						obj.data.body.setLinearVelocity(
								new Random().nextFloat() * 5,
								new Random().nextFloat() * 5);
					}
				}));
			}
			
			{
				AdvanceSprite sprite = new AdvanceSprite(Engine.resource("green",
						Texture.class)).enableShadow();
				sprite.setShadowNumber(32);
				sprite.setShadowInterval(0.00005f);
				sprite.setScale(0.8f);
	
				group.add(new Cb2Object(sprite,true,new Cb2ObjectSetupCallback() {
					@Override
					public void before(Cb2Object obj) {
						obj.data.restitution = 1;
						obj.data.friction = 0;
					}
					@Override
					public void after(Cb2Object obj) {
						obj.setPosition(400, 400);
						obj.data.body.setLinearVelocity(
								new Random().nextFloat() * 5,
								new Random().nextFloat() * 5);
					}
				}));
			}
			
			{
				AdvanceSprite sprite = new AdvanceSprite(Engine.resource("blue",
						Texture.class)).enableShadow();
				sprite.setScale(3f);
	
				group.add(new Cb2Object(sprite,true,new Cb2ObjectSetupCallback() {
					@Override
					public void before(Cb2Object obj) {
						obj.data.restitution = 1;
						obj.data.friction = 0;
					}
					@Override
					public void after(Cb2Object obj) {
						obj.setPosition(400, 200);
						obj.data.body.setLinearVelocity(
								new Random().nextFloat() * 5,
								new Random().nextFloat() * 5);
					}
				}));
			}

			Engine.setMainScene(new Scene() {
				@Override
				public void update(float delta) {
					Cb2World.getInstance().update(delta);
				}

				@Override
				public void hide() {

				}

				@Override
				public void show() {

				}

				@Override
				public void render(float delta) {
					Engine.getSpriteBatch().begin();
					group.render(delta);
					Engine.getSpriteBatch().end();
					box2dRender.render(Cb2World.getInstance().world(), Engine.getDefaultCamera().combined.scl(Cb2World.RADIO));
					Engine.debugInfo("The physical object also the animation draw the shadows");

				}

				@Override
				public InputProcessor getInputProcessor() {

					return null;
				}
			});
		}
	}

	@Override
	public void dispose() {
		Cb2World.getInstance().dispose();
		super.dispose();
	}

}
