package info.u250.c2d.tools.staff.surface;

import info.u250.c2d.engine.Engine;
import info.u250.c2d.graphic.surfaces.SurfaceWorldReader;
import info.u250.c2d.graphic.surfaces.TriangleSurfaces;
import info.u250.c2d.graphic.surfaces.data.SurfaceData;
import info.u250.c2d.tools.DesktopUtil;
import info.u250.c2d.tools.scenes.SceneWorkTable;
import info.u250.c2d.tools.staff.SceneAdapter;
import info.u250.c2d.tools.staff.SceneLayer;

import java.util.Iterator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;

public class SurfaceAdapter  extends TriangleSurfaces implements SceneAdapter{
	SceneWorkTable editor ;
	static FileHandle file ;
	public static SurfaceWorldReader reader ;
	static{
		reader = new SurfaceWorldReader();
	}
	public SurfaceAdapter() {
		SurfaceData data = new SurfaceData();
		data.texture = "dirt.png";
		this.data = data;
//		this.build();
		this.setbRender(bRender);
		this.editor = Engine.resource("Editor");
	}
	protected Vector2 snapPoint = null;
	protected void rebuild(){
		this.dispose();
		this.build();
		StringBuffer buffer = new StringBuffer();
		
		buffer.append("\n\nnew Array<Vector2>(){\n{");
		for(Vector2 v : this.data.points){
			buffer.append("add(new Vector2("+v.x+"f,"+v.y+"f));\n");
		}
		buffer.append("}}\n\n");
		Gdx.app.log("Result:", buffer.toString());
	}
	
	
	
	public void addPoint(Vector2 v){
		this.data.points.add(v);
		this.rebuild();
	}
	public void removeSnapePoint(){
		if(null!=this.snapPoint){
			this.data.points.removeValue(this.snapPoint,false);
			this.rebuild();
			this.snapPoint = null;
		}
	}
	
	
	public CurveSurfacesRender bRender = new CurveSurfacesRender() {
		
		@Override
		public void preRender(float delta) {
		}
		
		@Override
		public void postRender(float delta) {
			if(showLines){
				for(int i=0;i<data.points.size-1;i++){
					editor.render.begin(ShapeType.Line);
					editor.render.setColor(Color.RED);
					editor.render.line(data.points.get(i).x, data.points.get(i).y, data.points.get(i+1).x, data.points.get(i+1).y);
					editor.render.end();
				}
				if(data.points.size>=2){
					editor.render.begin(ShapeType.Line);
					editor.render.setColor(Color.RED);
					editor.render.line(data.points.get(0).x, data.points.get(0).y, data.points.get(data.points.size-1).x, data.points.get(data.points.size-1).y);
					editor.render.end();
				}
				
			}
			
			if(showSnape){
				final float SPACE = 5;
				for(int i=0;i<data.points.size;i++){
					editor.render.begin(ShapeType.Rectangle);
					editor.render.setColor(Color.BLUE);
					editor.render.rect(data.points.get(i).x-SPACE, data.points.get(i).y-SPACE, 2*SPACE, 2*SPACE);
					editor.render.end();
				}
				if(null!=snapPoint){
					editor.render.begin(ShapeType.FilledRectangle);
					editor.render.setColor(Color.BLUE);
					editor.render.filledRect(snapPoint.x-SPACE, snapPoint.y-SPACE, 2*SPACE, 2*SPACE);
					editor.render.end();
				}
				
				if(showNumbers){
					Engine.getSpriteBatch().begin();
					for(int i=0;i<data.points.size;i++){
						Engine.getDefaultFont().setColor(Color.YELLOW);
						Engine.getDefaultFont().draw(Engine.getSpriteBatch(), ""+(i+1), data.points.get(i).x, data.points.get(i).y);
					}
					Engine.getSpriteBatch().end();
				}
			}
		}
	};
//	protected List<Vector2> editorPoints = new ArrayList<Vector2>();
	
	private boolean showAll = true;
	private boolean showLines = true;
	private boolean showNumbers = true;
	private boolean showSnape = true;

	
	
	@Override
	public void update(float delta) {
	}
	@Override
	public void show() {}
	@Override
	public void hide() {}
	@Override
	public InputProcessor getInputProcessor() {
		return new SurfaceInput(this);
	}
	
	public void render(float delta){
		if(showAll)super.render(delta);
	}
	
	public boolean isShowAll() {
		return showAll;
	}

	public void setShowAll(boolean showAll) {
		this.showAll = showAll;
	}

	public boolean isShowSnape() {
		return showSnape;
	}
	public void setShowSnape(boolean showSnape) {
		this.showSnape = showSnape;
	}
	
	public boolean isShowLines() {
		return showLines;
	}
	public void setShowLines(boolean showLines) {
		this.showLines = showLines;
	}
	public boolean isShowNumbers() {
		return showNumbers;
	}
	public void setShowNumbers(boolean showNumbers) {
		this.showNumbers = showNumbers;
	}
	
	
	public int getPrimitiveType(){
		return data.primitiveType;
	}
	public void setPrimitiveType(int primitiveType){
		data.primitiveType = primitiveType;
		this.rebuild();
	}
	public void setTexture(String textureName){
		data.texture = textureName;
		this.rebuild();
	}
	public String getTextureName(){
		return data.texture;
	}

	@Override
	public void save() {
		try{
			if(file == null)
				file = Gdx.files.absolute(DesktopUtil.save());
			reader.write(file);
		}catch(Exception ex){
			ex.printStackTrace();
			file = null;
		}
	}

	@Override
	public void read() {
		reader.surfaces.clear();
		reader.read(file);
		Iterator<SceneLayer> it = editor.layers.iterator();
		while(it.hasNext()){
			SceneLayer layer = it.next();
			if(layer instanceof SurfaceSceneLayer){
				editor.layers.removeValue(layer, true);
			}
		}
		for(SurfaceData data:reader.surfaces){
				SurfaceSceneLayer layer = new SurfaceSceneLayer(data.name);
				layer.setData(data);
				editor.layers.add(layer);
				editor.active(layer);
		}
	}
}
