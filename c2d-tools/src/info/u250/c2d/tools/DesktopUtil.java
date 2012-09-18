package info.u250.c2d.tools;

import javax.swing.JFileChooser;

public class DesktopUtil {
	public static String getFile() throws Exception{
		JFileChooser file = new JFileChooser();
		file.showOpenDialog(ToolsDesktop.frame);
		return file.getSelectedFile().getAbsolutePath();
	}
	public static String save() throws Exception{
		JFileChooser file = new JFileChooser();
		file.showSaveDialog(ToolsDesktop.frame);
		return file.getSelectedFile().getAbsolutePath();
	}
}
