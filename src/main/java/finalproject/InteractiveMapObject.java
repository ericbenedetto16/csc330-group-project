package finalproject;

import java.awt.Graphics;
import java.util.*;

public interface InteractiveMapObject {
//	protected Map<String, int[]> coordinates;
//	protected InteractiveMapObjectType type;
//	
//	public InteractiveMapObject(Map<String, int[]> coordinates, InteractiveMapObjectType type) {
//		this.coordinates = coordinates;
//		this.type = type;
//	}
//	
//	public void setVerticies(Map<String,int[]> coordinates) {
//		this.coordinates = coordinates;
//	}
//	
//	public void setType(InteractiveMapObjectType type) {
//		this.type = type;
//	}
//	
//	public Map<String,int[]> getCoordinates() {
//		return this.coordinates;
//	}
//	
//	public InteractiveMapObjectType getType() {
//		return this.type;
//	}
	
	public void customPaint(Graphics g);
}
