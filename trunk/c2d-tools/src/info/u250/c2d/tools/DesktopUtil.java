package info.u250.c2d.tools;

import javax.swing.JFileChooser;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.jogl.JoglApplication;

public class DesktopUtil {
	public static String getFile() throws Exception{
		JFileChooser file = new JFileChooser();
		file.showOpenDialog(((JoglApplication)Gdx.app).getJFrame());
		return file.getSelectedFile().getAbsolutePath();
	}
	public static String save() throws Exception{
		JFileChooser file = new JFileChooser();
		file.showSaveDialog(((JoglApplication)Gdx.app).getJFrame());
		return file.getSelectedFile().getAbsolutePath();
	}
}
