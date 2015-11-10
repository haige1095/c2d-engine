# Placement #
If you want use the resources loaded . You must follow the engine's design .

The placement of  the resources .
  * **BitmapFont**  must be endwith **.fnt**  ,such as "data/test.fnt"
  * **Music** must be endwith "ogg","wav","mp3",you must put the music at "music" or "musics" folder, such as "data/music/abc.ogg" or "data/help/musics/helpbg.mp3"
  * **Sound** must be endwith "ogg","wav","mp3",you must put the sounds at "sound" or "sounds" folder, such as "data/sounds/abc.ogg" or "data/help/sound/buttonClick.wav"
  * **Texture** must be endwith "jpg","png","bmp", such as "data/textures/aaa.png","data/haha.jpg"
  * ~~TextureAtlas must be named "pack" and the texture file must at the same folder of “pack" , such as "data/pack"~~
  * **TextureAtlas**  must be endwith "atlas" and the texture file must at the same folder of “.atlas" , such as "data/bird.atlas"
  * **Skin** endwith "json" and the path contains "skin" , such as "data/sk/uiskin.json"

# Alias #
Before you can use the resources , you must give a alias of it inside method:
```
public void onResourcesRegister(AliasResourceManager<String> reg) {
}
```
## Use the resources ##
If you want use the resources . You can use the method
```
Engine. resource("TheKey")
```
Or give a type to cast
```
Engine.resource(String key,Class<T> type)
```

### play a sound ###
```
Engine.getSoundManager.playSound("Key")
```
### play a music ###
```
Engine.getMusicManager.playMusic("Key")
```
Engine support bellow types:
  1. **texture** register a texture
  1. **font** register a bitmapfont
  1. **textureAtlas** register a textureAtlas
  1. **sound** register a sound
  1. **music** register a music
  1. **skin** register a Skin
  1. **object** any type `[Donnot use this because it can make codes complex]`

# A exmaple #
Load the resources.
```
        @Override
	public EngineOptions onSetupEngine() {
		EngineOptions opt = new EngineOptions(new String[]{
				"data/blosics/pack",
				"data/blosics/music/",
				"data/blosics/sound/",
				"data/blosics/textures/",
				"data/fonts"
				}, 800, 480);
		opt.gl20Enable = false;
		opt.autoResume = true;
		opt.fps = false;
		opt.configFile = "info.u250.hitme.conf";
		opt.catchBackKey = true;
		opt.loading = LoadDing.class.getName();
		return opt;
	}
```

the alias
```
             @Override
	public void onResourcesRegister(AliasResourceManager<String> reg) {
                 reg.font("GameFont", "data/fonts/purisa.fnt");
		
		reg.texture("DirtGrass.png", "data/blosics/textures/DirtGrass.png");
		reg.texture("RockLayered.jpg", "data/blosics/textures/RockLayered.jpg");
		reg.texture("DSRT.png", "data/blosics/textures/DSRT.png");
		reg.texture("S.png", "data/blosics/textures/S.png");
		reg.texture("LoamWalls.jpg", "data/blosics/textures/LoamWalls.jpg");
		
		reg.textureAtlas("BL", "data/blosics/pack");
		
		reg.music("Blosics_bg_music", "data/blosics/music/bg.ogg");
		
		reg.sound("Blosics_puzzle_swap", "data/blosics/sound/puzzle-swap.wav");
		reg.sound("Blosics_puzzle_coin", "data/blosics/sound/puzzle-coin.wav");
		reg.sound("Blosics_puzzle_hit", "data/blosics/sound/puzzle-hit.wav");
		reg.sound("Blosics_puzzle_clear1", "data/blosics/sound/coinpickup1.ogg");
		reg.sound("Blosics_puzzle_clear2", "data/blosics/sound/coinpickup2.ogg");
		reg.sound("Blosics_puzzle_clear3", "data/blosics/sound/coinpickup3.ogg");
		reg.sound("Blosics_puzzle_clear4", "data/blosics/sound/coinpickup4.ogg");
		reg.sound("Blosics_puzzle_clear5", "data/blosics/sound/coinpickup5.ogg");
		reg.sound("Blosics_puzzle_clear6", "data/blosics/sound/coinpickup6.ogg");
		reg.sound("Blosics_puzzle_clear7", "data/blosics/sound/coinpickup7.ogg");
		reg.sound("Blosics_puzzle_clear8", "data/blosics/sound/coinpickup8.ogg");
		reg.sound("Blosics_puzzle_clear9", "data/blosics/sound/coinpickup9.ogg");
		
		reg.sound("Blosics_win", "data/blosics/sound/win.wav");
		reg.sound("Blosics_nopoints", "data/blosics/sound/nopoints.wav");
		reg.sound("Blosics_fire1", "data/blosics/sound/shoot1.wav");
		reg.sound("Blosics_fire2", "data/blosics/sound/shoot2.wav");
		reg.sound("Blosics_fire3", "data/blosics/sound/shoot3.wav");
		reg.sound("Blosics_fire4", "data/blosics/sound/shoot4.wav");
		reg.sound("Blosics_fire5", "data/blosics/sound/shoot5.wav");
		reg.sound("Blosics_btn", "data/blosics/sound/btn.wav");
		reg.sound("Blosics_hit_stone", "data/blosics/sound/hit-stone.wav");
		reg.sound("Blosics_hit_platfom", "data/blosics/sound/hit-platform.wav");
		reg.sound("Blosics_dispose", "data/blosics/sound/dispose.wav");
		reg.sound("Blosics_dispose_pet", "data/blosics/sound/dispose-pet.wav");
		reg.sound("Blosics_touchdown", "data/blosics/sound/holder.ogg");
		reg.sound("Blosics_Scroll", "data/blosics/sound/scroll.wav");

		reg.sound("Blosics_normal_1", "data/blosics/sound/klocki_normal_01.wav");
		reg.sound("Blosics_normal_2", "data/blosics/sound/klocki_normal_02.wav");
		reg.sound("Blosics_normal_3", "data/blosics/sound/klocki_normal_03.wav");
		reg.sound("Blosics_normal_4", "data/blosics/sound/klocki_normal_04.wav");
		reg.sound("Blosics_normal_5", "data/blosics/sound/klocki_normal_05.wav");
		reg.sound("Blosics_normal_6", "data/blosics/sound/klocki_normal_06.wav");
		reg.sound("Blosics_normal_7", "data/blosics/sound/klocki_normal_07.wav");
		reg.sound("Blosics_normal_8", "data/blosics/sound/klocki_normal_08.wav");
		reg.sound("Blosics_normal_9", "data/blosics/sound/klocki_normal_09.wav");
		reg.sound("Blosics_normal_10", "data/blosics/sound/klocki_normal_10.wav");
		reg.sound("Blosics_normal_11", "data/blosics/sound/kulki_normal_01.wav");
		reg.sound("Blosics_normal_12", "data/blosics/sound/kulki_normal_02.wav");
		reg.sound("Blosics_normal_13", "data/blosics/sound/kulki_normal_03.wav");
		reg.sound("Blosics_normal_14", "data/blosics/sound/kulki_normal_04.wav");
		reg.sound("Blosics_normal_15", "data/blosics/sound/kulki_normal_05.wav");
		reg.sound("Blosics_normal_16", "data/blosics/sound/kulki_multi_01.wav");
		reg.sound("Blosics_normal_17", "data/blosics/sound/kulki_multi_02.wav");
		reg.sound("Blosics_normal_18", "data/blosics/sound/kulki_multi_03.wav");
}
```


# in game loading #
You may want unload some resource or just replace it .  See the exmaple :

https://code.google.com/p/c2d-engine/source/browse/trunk/c2d-tests/src/info/u250/c2d/tests/IngameLoadingTest.java

The important method is **Engine.load(**
```
final InputProcessor preInputProcess = Gdx.input.getInputProcessor();
Gdx.input.setInputProcessor(null);
Engine.load(new String[]{"data/"},new LoadingComplete() {
       @Override
        public void onReady(AliasResourceManager<String> reg) {
                           reg.unload("AAA");
                           reg.texture("AAA", "data/textures/default.png");
                           Gdx.input.setInputProcessor(preInputProcess);
                }
        });
```