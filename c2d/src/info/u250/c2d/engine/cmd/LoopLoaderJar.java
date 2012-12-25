package info.u250.c2d.engine.cmd;

import info.u250.c2d.engine.resources.AliasResourceManager;
import info.u250.c2d.engine.resources.looper.LoopLoader;

import java.io.File;
import java.net.URL;
import java.security.CodeSource;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import com.badlogic.gdx.Gdx;

class LoopLoaderJar extends LoopLoader {

	@Override
	public void loadResource(String dataDir) {
		if(runningFromJar()){
			loadJars(dataDir);
		}else{
			loadDesktop(dataDir);
		}
	}

	private void loadJars(String dataDir){
		try{
			CodeSource src = AliasResourceManager.class.getProtectionDomain().getCodeSource();
			if (src != null) {
			  URL jar = src.getLocation();
			  ZipInputStream zip = new ZipInputStream(jar.openStream());
			  ZipEntry zipEntry = null;
			  while ((zipEntry = zip.getNextEntry()) != null) {
				  String name = zipEntry.getName();
				  if(name.startsWith(dataDir)){
					  if (zipEntry.isDirectory()) {
							 
					  }else{
						  loadFile(Gdx.files.internal(name));
					  }  
				  }
			  }
			  zip.close();
			}else {
			  /* Fail... */
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	private void loadDesktop(String dataDir){
		File file = new File( "bin/"+dataDir );
		if(file.isDirectory()){
			for(String f:file.list()){
				loadDesktop((dataDir.endsWith("/")?dataDir:(dataDir+"/"))+f);
			}
		}else{
			loadFile(Gdx.files.internal(dataDir));
		}
	}
	
	private boolean runningFromJar() {
		   String className = this.getClass().getName().replace('.', '/');
		   String classJar =  this.getClass().getResource("/" + className + ".class").toString();
		   if (classJar.startsWith("jar:")) {
		     return true;
		   }
		   return false;
	}
	
}
