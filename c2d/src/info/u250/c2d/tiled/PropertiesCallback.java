package info.u250.c2d.tiled;

public interface PropertiesCallback {
	public enum PropertiesType{
		MapProperties,
		LayerProperties,
		TiledCellProperties,
	}
	public void callback(PropertiesType type);
}