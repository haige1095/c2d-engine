package info.u250.c2d.tools;

import info.u250.c2d.engine.Engine;
import info.u250.c2d.engine.EngineDrive;

import com.badlogic.gdx.backends.jogl.JoglApplication;
import com.badlogic.gdx.backends.jogl.JoglApplicationConfiguration;

public class ToolsJogl {

	static class ToolEngine extends Engine {
		@Override
		protected EngineDrive onSetupEngineDrive() {
			return new ToolEngineDrive();
		}

		@Override
		public void resize(int width, int height) {
			super.resize(width, height);
			Engine.getEventManager().fire(Events.RESIZE);
		}
	}
	public static void main(String[] args) {
		final Engine engine = new ToolEngine();
		final JoglApplicationConfiguration config = new JoglApplicationConfiguration();
		config.useGL20 = Engine.getEngineConfig().gl20Enable;
		config.width = (int) 1024;
		config.height= (int) 600;
		new JoglApplication(engine, config);
	}
}
