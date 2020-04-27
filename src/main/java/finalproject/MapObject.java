package finalproject;

import java.util.*;

public class MapObject {
	protected Map<String, int[]> coordinates;
	protected MapObjectType type;
	
	public MapObject(Map<String, int[]> coordinates, MapObjectType type) {
		this.coordinates = coordinates;
		this.type = type;
	}
	
	public void setVerticies(Map<String,int[]> coordinates) {
		this.coordinates = coordinates;
	}
	
	public void setType(MapObjectType type) {
		this.type = type;
	}
	
	public Map<String,int[]> getCoordinates() {
		return this.coordinates;
	}
	
	public MapObjectType getType() {
		return this.type;
	}
}
