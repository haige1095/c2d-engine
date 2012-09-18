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
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
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
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.esotericsoftware.tablelayout.BaseTableLayout;


public final class PublicInputStaff extends InputStaff{
	//new staff window , open a dialog to input sth and make it in use of 
	final class NewStaffWindow extends Window{
		public NewStaffWindow(){
			super("New Tool Window", skin.get(WindowStyle.class));
			
			//the type of the editor.
			final SelectBox type = new SelectBox(new String[] {
					"Box2d",
					"Surface", 
					},
					skin.get(SelectBoxStyle.class));
			final TextField nameField = new TextField("",  skin);
			final Button ok = new TextButton("Ok", skin.get(TextButtonStyle.class));
			ok.addListener(new ClickListener(){
				@Override
				public void clicked(InputEvent event, float x, float y) {
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
					newStaffWindow.remove();
				}
			});
			
			final Button cancel = new TextButton("Cancel", skin.get(TextButtonStyle.class));
			cancel.addListener(new ClickListener(){
				@Override
				public void clicked(InputEvent event, float x, float y) {
					nameField.setText("");
					newStaffWindow.remove();
				}
			});
			
			
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
			super("Layers", skin);
		}
		private void close(){
			for(SceneLayer layer:editor.layers){
				layer.inputStaff.buildStaff();
			}
			staffListWindow.remove();
		}
		public void refresh(){
			this.clear();
			
			this.defaults().spaceBottom(10).space(20);		
			
			for(final SceneLayer layer:editor.layers){
				Table table = layer.inputStaff.layoutInfo();
				TextButton button = new TextButton("Active",skin);
				table.add(button);
				button.addListener(new ClickListener(){
					@Override
					public void clicked(InputEvent event, float x, float y) {
						editor.active(layer);
					}
				});
				this.add(table);
				this.row();
			}
			
			Button closeBtn = new Button(new Image(atlas.findRegion("tools-close")), skin);
			closeBtn.addListener(new ClickListener(){
				@Override
				public void clicked(InputEvent event, float x, float y) {
					close();
				}
			});
			this.add(closeBtn).align(BaseTableLayout.CENTER);
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
		Button newStaffBtn = new TextButton("New", skin);
		newStaffBtn.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				newStaffWindow.remove();
				ui.addActor(newStaffWindow);
			}
		});
		Button staffListBtn = new TextButton("Tasks", skin);
		staffListBtn.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				staffListWindow.refresh();
			}
		});
	
		
		
		
		toolbar = new Table();
		toolbar.add(newStaffBtn);
		toolbar.add(staffListBtn);
		toolbar.pack();
		
		final TextureRegionDrawable pauseRegion = new TextureRegionDrawable(atlas.findRegion("tools-pause"));
		final TextureRegionDrawable playRegion = new TextureRegionDrawable(atlas.findRegion("tools-run"));
		tools_run = new Image(atlas.findRegion("tools-pause"));
		tools_run.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if(tools_run.getDrawable() == playRegion){
					Cb2World.getInstance().dispose();
					Cb2World.getInstance().installDefaultWorld();
					
					tools_run.setDrawable(pauseRegion);
					for(SceneLayer layer:editor.layers){
						layer.start();
					}
					
					InputMultiplexer mul = new InputMultiplexer();
					mul.addProcessor(Gdx.input.getInputProcessor());
					mul.addProcessor(new PhysicalFingerInput(Cb2World.getInstance().createScreenBox()));
					Gdx.input.setInputProcessor(mul);
				}else{
					Cb2World.getInstance().dispose();
					
					tools_run.setDrawable(playRegion);
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
			}
		});
		
		
		tools_run.setDrawable(playRegion);
		UiUtils.centerActor(tools_run);
		tools_run.setY( Engine.getEngineConfig().height - tools_run.getHeight());
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