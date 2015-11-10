# The concept #

Currently no jar file is supplied , So You need the source code of libgdx and c2d , all begin from the sources .

# checkout the code #

  1. check out the latest libgdx  source code:
```xml

git clone https://github.com/libgdx/libgdx.git
```
> Or [Download](https://github.com/libgdx/libgdx/archive/master.tar.gz) it
  1. check out the latest c2d  source code:
```xml

svn checkout http://c2d-engine.googlecode.com/svn/trunk/ c2d-engine-read-only
```

# import the projects(Eclipse) #

  1. select File->Impor->Existing Projects into Workspace
  1. import below projects as you need
![http://c2d-engine.googlecode.com/svn/wiki/img/setup-project-import-projects.png](http://c2d-engine.googlecode.com/svn/wiki/img/setup-project-import-projects.png)

> # setup a new project #
The most directly, you can build a project like the c2d-test .
> You need create three project
  * create new java project  called "c2d-project" . Your game logic will be here. The required projects:
![http://c2d-engine.googlecode.com/svn/wiki/img/337206A6-6959-4B00-A506-34F7764A3E63.png](http://c2d-engine.googlecode.com/svn/wiki/img/337206A6-6959-4B00-A506-34F7764A3E63.png)
  * then you will create an android project called "c2d-project-android" .  The required projects:
![http://c2d-engine.googlecode.com/svn/wiki/img/641ED7FF-0B7E-4268-ACC1-5BEDDBD5E9CD.png](http://c2d-engine.googlecode.com/svn/wiki/img/641ED7FF-0B7E-4268-ACC1-5BEDDBD5E9CD.png)
  * finnaly the jogl java project was created . The required projects:
![http://c2d-engine.googlecode.com/svn/wiki/img/23FAAA76-2FBF-4405-9BE1-2FC2EAD015F3.png](http://c2d-engine.googlecode.com/svn/wiki/img/23FAAA76-2FBF-4405-9BE1-2FC2EAD015F3.png)

Next make a link of the assets, you can share the resources . Your resources should be at android side.
![http://c2d-engine.googlecode.com/svn/wiki/img/DDA26955-419F-4D10-BF17-661398337063.png](http://c2d-engine.googlecode.com/svn/wiki/img/DDA26955-419F-4D10-BF17-661398337063.png)

![http://c2d-engine.googlecode.com/svn/wiki/img/F3620897-9736-412A-B8D2-98647BD735E3.png](http://c2d-engine.googlecode.com/svn/wiki/img/F3620897-9736-412A-B8D2-98647BD735E3.png)
> # Java Classes #
  * create a new engine .
```
package info.u250.c2d.project;

import info.u250.c2d.engine.Engine;
import info.u250.c2d.engine.EngineDrive;

public class C2dProjectInstance extends Engine {
	@Override
	protected EngineDrive onSetupEngineDrive() {
		return new C2dEngineDrive();
	}

}
```
  * create a engine driver , you can manager many scenes here. Use the method **Engine.setMainScene(sceneTest);** set the main scene.
```
package info.u250.c2d.project;

import info.u250.c2d.engine.Engine;
import info.u250.c2d.engine.EngineDrive;
import info.u250.c2d.engine.resources.AliasResourceManager;
import info.u250.c2d.project.scenes.SceneTest;

public class C2dEngineDrive implements EngineDrive {

	@Override
	public EngineOptions onSetupEngine() {
		EngineOptions opt = new EngineOptions(new String[]{},800,480);
		return opt;
	}

	SceneTest sceneTest ;
	@Override
	public void onLoadedResourcesCompleted() {
		sceneTest = new SceneTest();

		Engine.setMainScene(sceneTest);
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onResourcesRegister(AliasResourceManager<String> reg) {
		// TODO Auto-generated method stub

	}

}
```
  * create a new scene ,where the logic put here. We just print "hello world"
```
package info.u250.c2d.project.scenes;

import com.badlogic.gdx.InputProcessor;

import info.u250.c2d.engine.Engine;
import info.u250.c2d.engine.Scene;

public class SceneTest implements Scene {

	@Override
	public void update(float delta) {}

	@Override
	public void render(float delta) {
		Engine.debugInfo("hello world");
	}

	@Override
	public void show() {}

	@Override
	public void hide() {}

	@Override
	public InputProcessor getInputProcessor() {
		return null;
	}

}
```
  * At your jogl project , now you can create a main class to run the project.
```
package info.u250.c2d.project;

import info.u250.c2d.engine.Engine;

import com.badlogic.gdx.backends.jogl.JoglApplication;
import com.badlogic.gdx.backends.jogl.JoglApplicationConfiguration;

public class JoglDesktopStart {

	
	public static void main(String[] args) {
		C2dProjectInstance instance = new C2dProjectInstance();
		JoglApplicationConfiguration config = new JoglApplicationConfiguration();
		config.width = (int)Engine.getEngineConfig().width;
		config.height = (int)Engine.getEngineConfig().height;
		config.useGL20 = Engine.getEngineConfig().gl20Enable;
		new JoglApplication(instance, config);
	}

}

```

Right click the JoglDesktopStart.java , then select Run As->Java Application.

You can download the project at  https://c2d-engine.googlecode.com/files/c2dproject.tar.gz