package info.u250.c2d.tools.staff;

import info.u250.c2d.engine.Engine;
import info.u250.c2d.input.PhysicalFingerInput;
import info.u250.c2d.physical.box2d.Cb2World;
import info.u250.c2d.tools.staff.box2d.Box2dSceneLayer;
import info.u250.c2d.tools.staff.surface.SurfaceAdapter;
import info.u250.c2d.tools.staff.surface.SurfaceSceneLayer;
import info.u250.c2d.utils.UiUtils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Button.ButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox.SelectBoxStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.esotericsoftware.tablelayout.BaseTableLayout;


public final class PublicInputStaff extends InputStaff{
	//new staff window , open a dialog to input sth and make it in use of 
	final class NewStaffWindow extends Window{
		public NewStaffWindow(){
			super("New Tool Window", skin.getStyle(WindowStyle.class), "newStaffWindow");
			
			//the type of the editor.
			final SelectBox type = new SelectBox(new String[] {
					"Box2d",
					"Surface", 
					},
					skin.getStyle(SelectBoxStyle.class), "type");
			final TextField nameField = new TextField("", "", skin);
			final Button ok = new TextButton("Ok", skin.getStyle(TextButtonStyle.class), "button-sl"){
				@Override
				public boolean touchDown(float x, float y, int pointer) {
					String _type = type.getSelection();
					String _name = nameField.getText().trim();
					if(_type.equals("Surface") && !_name.equals("")){
						final SurfaceSceneLayer layer = new SurfaceSceneLayer(_name);
						editor.layers.add(layer);
						editor.active(layer);
						SurfaceAdapter.reader.surfaces.add(layer.getData());
					}else if(_type.equals("Box2d")){
						final Box2dSceneLayer layer = new Box2dSceneLayer(_name);
						editor.layers.add(layer);
						editor.active(layer);
					}
					ui.removeActor(newStaffWindow);
					return true;
				}
			};
			final Button cancel = new TextButton("Cancel", skin.getStyle(TextButtonStyle.class), "button-sl"){
				@Override
				public boolean touchDown(float x, float y, int pointer) {
					nameField.setText("");
					ui.removeActor(newStaffWindow);
					return true;
				}
			};
			
			
			this.defaults().spaceBottom(10).space(20);
			this.add(new Label("", skin));
			this.row().fill().expandX();
			this.add(new Label("The name of the layer:", skin));
			this.add(nameField);
			this.row().fill().expandX();
			this.add(new Label("Type:", skin));
			this.add(type);
			this.row().fill().expandX();
			this.add(ok);
			this.add(cancel);
			this.pack();
			
		}
	}
	
	class StaffListWindow extends Window{
		public StaffListWindow(){
			super("Layers", skin.getStyle(WindowStyle.class), "StaffListWindow");
		}
		private void close(){
			for(SceneLayer layer:editor.layers){
				layer.inputStaff.buildStaff();
			}
			ui.removeActor(staffListWindow);
		}
		public void refresh(){
			this.clear();
			
			this.defaults().spaceBottom(10).space(20);		
			
			for(final SceneLayer layer:editor.layers){
				Table table = layer.inputStaff.layoutInfo();
				TextButton button = new TextButton("Active",skin);
				table.add(button);
				button.setClickListener(new ClickListener() {
					@Override
					public void click(Actor actor, float x, float y) {
						editor.active(layer);
					}
				});
				this.add(table);
				this.row();
			}
			this.add(new Button(new Image(skin.getRegion("tools-close")), skin.getStyle(ButtonStyle.class)){
				@Override
				public boolean touchDown(float x, float y, int pointer) {
					close();
					return true;
				}
			}).align(BaseTableLayout.CENTER);
			this.pack();
			UiUtils.centerActor(this);
			
			if(editor.layers.size>0)ui.addActor(this);
		}
	}
	
	Table toolbar ;
	NewStaffWindow newStaffWindow ;
	StaffListWindow staffListWindow ;
	Image tools_run;
	
	//the main game input staff 
	public PublicInputStaff(){
		Button newStaffBtn = new TextButton("New", skin.getStyle(TextButtonStyle.class), "button-sl"){
			@Override
			public boolean touchDown(float x, float y, int pointer) {
				ui.addActor(newStaffWindow);
				return true;
			}
		};
		Button staffListBtn = new TextButton("Tasks", skin.getStyle(TextButtonStyle.class), "button-sl"){
			@Override
			public boolean touchDown(float x, float y, int pointer) {
				staffListWindow.refresh();
				return true;
			}
		};
		
		
		toolbar = new Table();
		toolbar.add(newStaffBtn);
		toolbar.add(staffListBtn);
		toolbar.pack();
		
		final TextureRegion pauseRegion = skin.getRegion("tools-pause");
		final TextureRegion playRegion = skin.getRegion("tools-run");
		tools_run = new Image(skin.getRegion("tools-pause")){
			@Override
			public boolean touchDown(float x, float y, int pointer) {
				if(this.getRegion() == playRegion){
					Cb2World.getInstance().dispose();
					Cb2World.getInstance().installDefaultWorld();
					
					this.setRegion(pauseRegion);
					for(SceneLayer layer:editor.layers){
						layer.start();
					}
					
					InputMultiplexer mul = new InputMultiplexer();
					mul.addProcessor(Gdx.input.getInputProcessor());
					mul.addProcessor(new PhysicalFingerInput(Cb2World.getInstance().createScreenBox()));
					Gdx.input.setInputProcessor(mul);
				}else{
					Cb2World.getInstance().dispose();
					
					this.setRegion(playRegion);
					for(SceneLayer layer:editor.layers){
						layer.stop();
					}
					
					if(Gdx.input.getInputProcessor() instanceof InputMultiplexer){
						InputMultiplexer mul = InputMultiplexer.class.cast(Gdx.input.getInputProcessor());
						for(InputProcessor input:mul.getProcessors()){
							if(input instanceof PhysicalFingerInput){
								mul.removeProcessor(input);
							}
						}
					}
				}
				return true;
			}
		};
		
		tools_run.setRegion(playRegion);
		UiUtils.centerActor(tools_run);
		tools_run.y = Engine.getEngineConfig().height - tools_run.height;
		this.ui.addActor(tools_run);
		
		newStaffWindow = new NewStaffWindow();
		staffListWindow = new StaffListWindow();
	
		this.ui.addActor(toolbar);
		
	}
	//the positon of the window may be put here .
	@Override
	protected void resize(float width, float height) {
		this.toolbar.setPosition(0,  height - this.toolbar.getHeight());
		UiUtils.centerActor(tools_run);
		tools_run.setY(Engine.getEngineConfig().height - tools_run.getHeight());
		UiUtils.centerActor(newStaffWindow);
		UiUtils.centerActor(staffListWindow);
	}
	
}