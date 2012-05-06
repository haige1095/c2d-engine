package info.u250.c2d.tools.scenes;

import info.u250.c2d.engine.Engine;
import info.u250.c2d.engine.Scene;
import info.u250.c2d.physical.box2d.Cb2World;
import info.u250.c2d.tools.staff.PublicAssistantStaff;
import info.u250.c2d.tools.staff.PublicInputStaff;
import info.u250.c2d.tools.staff.SceneLayer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Array;

public class SceneWorkTable implements Scene{
	private PublicInputStaff publicInputStaff;
	private PublicAssistantStaff assistant ;
	public Array<SceneLayer> layers; 
	public ShapeRenderer render;
	private InputMultiplexer mul = new InputMultiplexer();
	public SceneWorkTable(){
		//it important ,but it is ugly code
		Engine.getAliasResourceManager().object("Editor", this);
		
		this.layers = new Array<SceneLayer>();
		this.publicInputStaff = new PublicInputStaff();
		this.render = new ShapeRenderer();
		this.assistant = new PublicAssistantStaff();
	}
	@Override
	public InputProcessor getInputProcessor() {
		mul.clear();
		mul.addProcessor(this.assistant.getInputProcessor());
		mul.addProcessor(this.publicInputStaff.getInputProcessor());
		return mul;
	}
	@Override
	public void update(float delta) {
		Cb2World.getInstance().update(delta);
	}
	
	@Override
	public void render(float delta) {
		render.setProjectionMatrix(Engine.getDefaultCamera().combined);
		for(SceneLayer layer:this.layers){
			layer.render(delta);
		}
		assistant.render(delta);
		publicInputStaff.render(delta);
	}
	public void active(SceneLayer layer){
		for(SceneLayer _layer:this.layers){
			_layer.setActive(false);
		}
		layer.setActive(true);
		
		mul.clear();
		mul.addProcessor(this.assistant.getInputProcessor());
		mul.addProcessor(this.publicInputStaff.getInputProcessor());
		mul.addProcessor(layer.getInputProcessor());
		Gdx.input.setInputProcessor(mul);
		layer.inputStaff.buildStaff();
	}
	
	@Override
	public void show() {}
	@Override
	public void hide() {}
}
