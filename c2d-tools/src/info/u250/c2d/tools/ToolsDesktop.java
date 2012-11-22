package info.u250.c2d.tools;

import info.u250.c2d.engine.Engine;
import info.u250.c2d.engine.EngineDrive;

import java.awt.Canvas;

import javax.swing.JFrame;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class ToolsDesktop {

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
	public static JFrame frame = null ;
	public static void main(String[] args) {
		frame = new JFrame("C2d Box2d");
        frame.setSize(1024,600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Canvas canvas = new Canvas();
        frame.add(canvas);
        frame.setVisible(true);
        
        final Engine engine = new ToolEngine();
		final LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.useGL20 = Engine.useGL20();
		config.width = (int) 1024;
		config.height= (int) 600;
        new LwjglApplication(engine, config,canvas);
        
       
	}
}
