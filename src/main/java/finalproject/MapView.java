package finalproject;

import java.awt.Color;
import java.awt.Graphics;
import java.io.IOException;
import java.awt.FontMetrics;
import java.util.*;

public class MapView {
	protected static final int MAP_WIDTH = 550;
	protected static final int MAP_HEIGHT = 710;
	private static final int HORIZONTAL_OFFSET = (Program.WIDTH - MAP_WIDTH)/2;
	private static final int VERTICAL_OFFSET = (Program.HEIGHT - MAP_HEIGHT)/2;
	
	private static final double minLat = -74.15552;
	private static final double maxLat = -74.14424;
	private static final double maxLng = 40.59493;
	private static final double minLng = 40.60826;
	
	// To get the coordinate do the percentage of how far
	// it is between the smallest and biggest ranges and multiply
	// that value by the width of the window
	
	double top = 40.6030;
	double bottom = 40.6019;
	double left = -74.1525;
	double right = -74.1512;
	
	PolygonObject baseballField = new PolygonObject(
			Map.of("x",new int[] {
					(int)scaleWidth(left),
					(int)scaleWidth(right),
					(int)scaleWidth(right),
					(int)scaleWidth(left)
					}, 
					"y", new int[] {
					(int)scaleHeight(top),
					(int)scaleHeight(top),
					(int)scaleHeight(bottom),
					(int)scaleHeight(bottom)
					}
					));
	
	double lat1 = 40.6031;
	double lat2 = 40.6027;
	double lat3 = 40.6029;
	
	double lng1 = -74.1481;
	double lng2 = -74.1473;
	double lng3 = -74.1475;
	double lng4 = -74.1479;
	
	PolygonObject Building5N = new PolygonObject(
			Map.of(
				"x", new int[] {
					(int)scaleWidth(lng1),
					(int)scaleWidth(lng2),
					(int)scaleWidth(lng2),
					(int)scaleWidth(lng3),
					(int)scaleWidth(lng3),
					(int)scaleWidth(lng4),
					(int)scaleWidth(lng4),
					(int)scaleWidth(lng1)
				},
				"y", new int[] {
					(int)scaleHeight(lat1),
					(int)scaleHeight(lat1),
					(int)scaleHeight(lat2),
					(int)scaleHeight(lat2),
					(int)scaleHeight(lat3),
					(int)scaleHeight(lat3),
					(int)scaleHeight(lat2),
					(int)scaleHeight(lat2)
				}
			));

	private PolygonObject[] polygons = {baseballField, Building5N};
	private MarkerObject[] markers;
	private PointObject[] points;
	
	private double scaleWidth(double x) {
		return (0*(1-((x-minLat)/(maxLat-minLat))) + (MAP_WIDTH*((x-minLat)/(maxLat-minLat)))) + HORIZONTAL_OFFSET;
	}
	
	private double scaleHeight(double y) {
		return (0*(1-((y-minLng)/(maxLng-minLng))) + (MAP_HEIGHT*((y-minLng)/(maxLng-minLng)))) + VERTICAL_OFFSET;
	}
	
	public MapView() {
		/*
		 *  Displays the polygons which are passed in as
		 *  data to the processor and the renderer.
		 */
//		this.polygons = processor.getPolygons();
//		this.markers = processor.getMarkers();
//		this.points = processor.getPoints();
	}
	
	public void render(Graphics g) {
		FontMetrics fm = g.getFontMetrics();
		for(PolygonObject polygon : polygons) {
			polygon.render(g);
		}
		
//		for(Marker marker : markers) {
//			marker.render(g);
//		}
//		
//		for(Point point : points) {
//			point.render(g);
//		}
		
		g.setColor(Color.white);
		g.drawString("Map", Program.WIDTH/2 - fm.stringWidth("Map")/2, Program.HEIGHT/2 + fm.getAscent()/2);
	}
}
