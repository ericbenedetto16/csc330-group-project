package finalproject;

import java.awt.Graphics;
import java.awt.Polygon;
import java.awt.Color;
import java.util.*;

public class CustomPolygon extends MapObject {
	Polygon customPolygon;
	
	public CustomPolygon(Map<String, int[]> verticies) {
		super(verticies, MapObjectType.POLYGON);
		
		init();
	}
	
	private void init() {
		this.customPolygon = new Polygon(this.getCoordinates().get("x"), this.getCoordinates().get("y"), this.getNumberOfPoints());
		int[] x = this.getCoordinates().get("x");
		int[] y = this.getCoordinates().get("y");
	}
	
	public int getNumberOfPoints() {
		return this.coordinates.get("x").length;
	}
	
	public void render(Graphics g) {
//		System.out.println(Arrays.toString(this.coordinates.get("x")));
		g.setColor(Color.red);
		g.drawPolygon(this.customPolygon.xpoints, this.customPolygon.ypoints, this.getNumberOfPoints());
	}
}
