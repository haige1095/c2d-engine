package info.u250.c2d.tools.staff.surface;

import info.u250.c2d.engine.Engine;
import info.u250.c2d.tools.Constants;
import info.u250.c2d.tools.DesktopUtil;
import info.u250.c2d.tools.staff.SceneLayerInputStaff;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox.CheckBoxStyle;
import com.badlogic.gdx.scenes.scene2d.ui.ClickListener;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane.ScrollPaneStyle;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox.SelectBoxStyle;
import com.badlogic.gdx.scenes.scene2d.ui.SelectionListener;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.TextField.TextFieldListener;
import com.badlogic.gdx.scenes.scene2d.ui.TextField.TextFieldStyle;
import com.badlogic.gdx.scenes.scene2d.ui.tablelayout.Table;

public class SurfaceInputStaff extends SceneLayerInputStaff{

	ScrollPane pane;
	@Override
	protected void resize(float width, float height) {
		this.buildStaff();
	}
	@Override
	public void buildStaff() {
		this.ui.removeActor(pane);
		
		final SurfaceAdapter sAdapter = SurfaceAdapter.class.cast(this.layer.adapter);
		
		final CheckBox showAll = new CheckBox("", skin.getStyle(CheckBoxStyle.class), "showAll");
		final CheckBox showSnape = new CheckBox("", skin.getStyle(CheckBoxStyle.class), "showSnape");
		final CheckBox showLine= new CheckBox("", skin.getStyle(CheckBoxStyle.class), "showLine");
		final CheckBox showNumber  = new CheckBox("", skin.getStyle(CheckBoxStyle.class), "showNumber");
		final TextField nameField = new TextField("", "", skin);
		nameField.setTextFieldListener(new TextFieldListener() {
			@Override
			public void keyTyped(TextField textField, char key) {
				if(key==10){
					layer.setName(nameField.getText());
				}
			}
		});
		
		final Label currentTextureName = new Label(sAdapter.getTextureName(), skin);
		currentTextureName.setColor(Color.YELLOW);
		//the type of the surface.
		final SelectBox type = new SelectBox(new String[] {
				"TRIANGLE_FAN", 
				"TRIANGLE_STRIP", 
				},
				skin.getStyle(SelectBoxStyle.class), "type");
		type.setSelectionListener(new SelectionListener() {
			public void selected(Actor actor, int index, String value) {
				final String sel = type.getSelection();
				if(sel.equals("TRIANGLE_FAN")){
					sAdapter.setPrimitiveType(GL10.GL_TRIANGLE_FAN);
				}else if(sel.equals("TRIANGLE_STRIP")){
					sAdapter.setPrimitiveType(GL10.GL_TRIANGLE_STRIP);
				}
			}
		});
		if(sAdapter.getPrimitiveType() == GL10.GL_TRIANGLE_FAN)type.setSelection("TRIANGLE_FAN");
		else if(sAdapter.getPrimitiveType() == GL10.GL_TRIANGLE_STRIP)type.setSelection("TRIANGLE_STRIP");
		
		showAll.setChecked(sAdapter.isShowAll());
		showSnape.setChecked(sAdapter.isShowSnape());
		showNumber.setChecked(sAdapter.isShowNumbers());
		showLine.setChecked(sAdapter.isShowLines());
		
		showAll.setClickListener(new ClickListener() {
			public void click(Actor actor, float x, float y) {
				final SurfaceAdapter sAdapter = SurfaceAdapter.class.cast(layer.adapter);
				sAdapter.setShowAll(showAll.isChecked());
			}
		});
		showSnape.setClickListener(new ClickListener() {
			public void click(Actor actor, float x, float y) {
				final SurfaceAdapter sAdapter = SurfaceAdapter.class.cast(layer.adapter);
				sAdapter.setShowSnape(showSnape.isChecked());
			}
		});
		showLine.setClickListener(new ClickListener() {
			public void click(Actor actor, float x, float y) {
				final SurfaceAdapter sAdapter = SurfaceAdapter.class.cast(layer.adapter);
				sAdapter.setShowLines(showLine.isChecked());
			}
		});
		showNumber.setClickListener(new ClickListener() {
			public void click(Actor actor, float x, float y) {
				final SurfaceAdapter sAdapter = SurfaceAdapter.class.cast(layer.adapter);
				sAdapter.setShowNumbers(showNumber.isChecked());
			}
		});
	
		final Table table = new Table();
		table.defaults().spaceBottom(0).spaceRight(10);
		table.add(new Label("Layer", skin));
		TextFieldStyle style = new TextFieldStyle(
				nameField.getStyle().font, Color.RED, 
				nameField.getStyle().messageFont, Color.RED, 
				nameField.getStyle().cursor,
				nameField.getStyle().selection, 
				nameField.getStyle().background);
		nameField.setText(this.layer.getName());
		nameField.setStyle(style);
		table.add(nameField);
		table.row();
		
		table.add(new Label("Type", skin));
		table.add(type);
		table.row();
		
		table.add(new Label("Show", skin));
		table.add(showAll);
		table.row();
		
		table.add(new Label("Snap", skin));
		table.add(showSnape);
		table.row();
		
		table.add(new Label("Number", skin));
		table.add(showNumber);
		table.row();
		
		table.add(new Label("Line", skin));
		table.add(showLine);
		table.row();
		
		final Image tools_open = new Image(skin.getRegion("tools-open"));
		final Image tools_save = new Image(skin.getRegion("tools-save"));
		tools_open.setClickListener(new ClickListener() {
			public void click(Actor actor, float x, float y) {
				try{
					String file = DesktopUtil.getFile();
					SurfaceAdapter.file = Gdx.files.absolute(file);
					sAdapter.read();
				}catch(Exception ex){
					ex.printStackTrace();
					SurfaceAdapter.file = null;
				}
			}
		});
		tools_save.setClickListener(new ClickListener() {
			public void click(Actor actor, float x, float y) {
				sAdapter.save();
			}
		});
		Table openSave = new Table();
		openSave.add(tools_open);
		openSave.add(tools_save);
		openSave.row();
		openSave.pack();
		table.add(openSave);
		
		table.add(new Label("Current", skin));
		table.add(currentTextureName);
		table.row();
		
		for(final String res:Constants.TextureNames){
			final Image texture = new Image(Engine.resource(res,Texture.class));
			texture.width = texture.height  = 128;
			texture.setClickListener(new ClickListener() {
				@Override
				public void click(Actor actor, float x, float y) {
					sAdapter.setTexture(res);
					currentTextureName.setText(res);
				}
			});
			table.add(texture).colspan(2);
			table.row();
			table.add(new Label(res, skin)).colspan(2);
			table.row();
		}
		
		table.pack();
		
		pane = new ScrollPane(table, skin.getStyle(ScrollPaneStyle.class)){
			@Override
			public boolean touchDown(float x, float y, int pointer) {
				super.touchDown(x, y, pointer);
				return true;
			}
		};
		
		
		pane.width = 250;
		pane.height = Engine.getEngineConfig().height;
		pane.x = Engine.getEngineConfig().width - pane.width;
		this.ui.addActor(pane);
	}
	@Override
	public Table layoutInfo() {
		final CheckBox showAll = new CheckBox("", skin.getStyle(CheckBoxStyle.class), "showAll");
		final CheckBox showSnape = new CheckBox("", skin.getStyle(CheckBoxStyle.class), "showSnape");
		final CheckBox showLine= new CheckBox("", skin.getStyle(CheckBoxStyle.class), "showLine");
		final CheckBox showNumber  = new CheckBox("", skin.getStyle(CheckBoxStyle.class), "showNumber");
		
		
		final SurfaceAdapter sAdapter = SurfaceAdapter.class.cast(this.layer.adapter);
		
		showAll.setChecked(sAdapter.isShowAll());
		showSnape.setChecked(sAdapter.isShowSnape());
		showNumber.setChecked(sAdapter.isShowNumbers());
		showLine.setChecked(sAdapter.isShowLines());
		
		showAll.setClickListener(new ClickListener() {
			public void click(Actor actor, float x, float y) {
				final SurfaceAdapter sAdapter = SurfaceAdapter.class.cast(layer.adapter);
				sAdapter.setShowAll(showAll.isChecked());
			}
		});
		showSnape.setClickListener(new ClickListener() {
			public void click(Actor actor, float x, float y) {
				final SurfaceAdapter sAdapter = SurfaceAdapter.class.cast(layer.adapter);
				sAdapter.setShowSnape(showSnape.isChecked());
			}
		});
		showLine.setClickListener(new ClickListener() {
			public void click(Actor actor, float x, float y) {
				final SurfaceAdapter sAdapter = SurfaceAdapter.class.cast(layer.adapter);
				sAdapter.setShowLines(showLine.isChecked());
			}
		});
		showNumber.setClickListener(new ClickListener() {
			public void click(Actor actor, float x, float y) {
				final SurfaceAdapter sAdapter = SurfaceAdapter.class.cast(layer.adapter);
				sAdapter.setShowNumbers(showNumber.isChecked());
			}
		});
	
		final Table table = new Table();
		table.defaults().spaceBottom(0).spaceRight(10);
		table.add(new Label("Layer", skin));
		table.add(new Label("Show", skin));
		table.add(new Label("Snap", skin));
		table.add(new Label("Number", skin));
		table.add(new Label("Line", skin));
		table.add(new Label("Up", skin));
		table.add(new Label("Down", skin));
		table.row().expandX();
		final Label nameLabel = new Label(this.layer.getName(), skin);
		nameLabel.setColor(Color.RED);
		table.add(nameLabel);
		table.add(showAll);
		table.add(showSnape);
		table.add(showNumber);
		table.add(showLine);
		table.add(new Image(skin.getRegion("tools-up")));
		table.add(new Image(skin.getRegion("tools-down")));
		table.pack();
		return table;
	}
}
