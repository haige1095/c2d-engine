# Introduction #
https://code.google.com/p/c2d-engine/source/browse/trunk/c2d/src/info/u250/c2d/engine/EngineDrive.java

Like many engine do, c2d also supply some options to custom the game.

You can directly access these attributes via **Engine.getEngineConfig()**

# Details #

  * **loading** the loading screen , use the java reflect to make a new instance , you can custom a loading screen.
  * **ingameLoading** the in game loading layer, you also can custom it .
  * **catchBackKey** whether to catch the back button , use libgdx's setCatchBackKey (boolean catchBack) method.
  * **assets** the resources to scan,  all the resources will be load automatic
  * **width** the game with (px)
  * **height** the game height(px)
  * **debug** if show the fps debug label
  * **autoResume** auto resume manager the Engine's running state . If you set it false , you must call  {@link info.u250.c2d.engine.Engine#doResume() } manually to resume the game or it will keep pause
  * **resizeSync** if set true , we will reset the engine's {@link #width} and {@link #height}  when  the resize method called
  * **configFile** config file to save attribues.
  * **gl20Enable** if use the opengl2.x