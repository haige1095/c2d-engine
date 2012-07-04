package info.u250.c2d.tests;

import info.u250.c2d.engine.Engine;
import info.u250.c2d.engine.EngineDrive;
import info.u250.c2d.engine.Scene;
import info.u250.c2d.engine.resources.AliasResourceManager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.ParticleEmitter;
import com.badlogic.gdx.math.Vector2;


public class FollowableParticle extends Engine {
	@Override
	protected EngineDrive onSetupEngineDrive() {
		return new EngineX();
	}
	@Override
	public void dispose () {
		super.dispose();
	}
	
	private class EngineX implements EngineDrive{
		@Override
		public void onResourcesRegister(AliasResourceManager<String> reg) {
		}
		@Override
		public void dispose() {}
		@Override
		public EngineOptions onSetupEngine() {
			final EngineOptions opt = new EngineOptions(new String[]{},800,480);
			return opt;
		}

		@Override
		public void onLoadedResourcesCompleted() {
			final ParticleEffect particleEffect = new ParticleEffect();
			particleEffect.load(Gdx.files.internal("data/particles/ThrustEffect"), Gdx.files.internal("data/particles/"));
			final ParticleEmitter particleEmitter = particleEffect.findEmitter("Thrust");
			Engine.setMainScene(new Scene() {
				@Override
				public void render(float delta) {
					Engine.getSpriteBatch().begin();
					particleEmitter.draw(Engine.getSpriteBatch(),delta);
					Engine.getSpriteBatch().end();
					
					Engine.debugInfo("Move your finger or your mouse to make the sprite follow");
				}
				@Override
				public InputProcessor getInputProcessor() {
					return new InputAdapter(){
						@Override
						public boolean mouseMoved(int x, int y) {
							particleEmitter.addParticles(10);
							Vector2 tmp = Engine.screenToWorld(x, y);
//							particleEmitter.getAngle().setLow(sprite_ship1.getRotation() - 4,mainGame.sprite_ship1.getRotation() + 4); laserPEmitters.get(i).getAngle().
//									setHigh(sprite_ship1.getRotation() - 4, sprite_ship1.getRotation() + 4);
							particleEmitter.start();
							
							particleEmitter.setPosition(tmp.x, tmp.y);
							return super.mouseMoved(x, y);
						}
					};
				}
				@Override
				public void update(float delta) {	
				}
				@Override
				public void hide() {	
				}
				@Override
				public void show() {
				}
			});	
		}
	}
}
