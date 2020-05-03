package finalproject;

import java.awt.Polygon;
import java.util.ArrayList;

public class InteractiveMap {
	private STATE state = STATE.None;
	
	public static final int MAP_WIDTH = 524;
	public static final int MAP_HEIGHT = 661;
	public static final double minLat = -74.15552;
	public static final double maxLat = -74.14424;
	public static final double maxLng = 40.59493;
	public static final double minLng = 40.60826;
	
	private ArrayList<InteractiveMapObject> baseShapes = new ArrayList<InteractiveMapObject>();
	private ArrayList<InteractiveMapObject> userShapes = new ArrayList<InteractiveMapObject>();
	
	public InteractiveMap() {
		double top = 40.6030;
		double bottom = 40.6019;
		double left = -74.1525;
		double right = -74.1512;
		
		// To get the coordinate do the percentage of how far
		// it is between the smallest and biggest ranges and multiply
		// that value by the width of the window
		int[] bx = new int[] {
				(int)scaleWidth(left),
				(int)scaleWidth(right),
				(int)scaleWidth(right),
				(int)scaleWidth(left)
				};
		int[] by = new int[] {
				(int)scaleHeight(top),
				(int)scaleHeight(top),
				(int)scaleHeight(bottom),
				(int)scaleHeight(bottom)
				};

		PolygonObject baseballField = new PolygonObject(
				new Polygon(new int[] {
						(int)scaleWidth(left),
						(int)scaleWidth(right),
						(int)scaleWidth(right),
						(int)scaleWidth(left)
						}, 
				new int[] {
						(int)scaleHeight(top),
						(int)scaleHeight(top),
						(int)scaleHeight(bottom),
						(int)scaleHeight(bottom)
						},4),
				"Title Test"
				);
		
		double lat1 = 40.6031;
		double lat2 = 40.6027;
		double lat3 = 40.6029;
		
		double lng1 = -74.1481;
		double lng2 = -74.1473;
		double lng3 = -74.1475;
		double lng4 = -74.1479;
		
		PolygonObject Building5N = new PolygonObject(
				new Polygon(new int[] {
							(int)scaleWidth(lng1),
							(int)scaleWidth(lng2),
							(int)scaleWidth(lng2),
							(int)scaleWidth(lng3),
							(int)scaleWidth(lng3),
							(int)scaleWidth(lng4),
							(int)scaleWidth(lng4),
							(int)scaleWidth(lng1)
						},
						new int[] {
							(int)scaleHeight(lat1),
							(int)scaleHeight(lat1),
							(int)scaleHeight(lat2),
							(int)scaleHeight(lat2),
							(int)scaleHeight(lat3),
							(int)scaleHeight(lat3),
							(int)scaleHeight(lat2),
							(int)scaleHeight(lat2)
						},
						8),
				"Title Test 2"
				);
		
		baseShapes.add(baseballField);
		baseShapes.add(Building5N);
	}
	
	private double scaleWidth(double x) {
		return (0*(1-((x-minLat)/(maxLat-minLat))) + (MAP_WIDTH*((x-minLat)/(maxLat-minLat))));
	}
	
	private double scaleHeight(double y) {
		return (0*(1-((y-minLng)/(maxLng-minLng))) + (MAP_HEIGHT*((y-minLng)/(maxLng-minLng))));
	}
	
	public enum STATE {
		None,
		Marker,
		Distance,
		Polygon,
		Rectangle,
		Circle,
		Triangle
	}
	
	public void addShape(InteractiveMapObject o) {
		userShapes.add(o);
	}
	
	public void removeShape(InteractiveMapObject o) {
		userShapes.remove(o);
	}
	
	public void clearShapes() {
		userShapes.clear();
	}
	
	public void calcDistance() {
		// Take all the Points in the Shape and Calc
		
	}
	
	public ArrayList<InteractiveMapObject> getShapes() {
		ArrayList<InteractiveMapObject> allShapes = new ArrayList<InteractiveMapObject>();
		allShapes.addAll(baseShapes);
		allShapes.addAll(userShapes);
		return allShapes;
	}
	
	public void exit() {
		System.exit(0);
	}

	public void clear() {
		userShapes.clear();
	}
	
	public void setState(STATE s) {
		this.state = s;
	}
	
	public STATE getState(String s) {
		switch(s) {
		case SideBarView.BL_MARKER:
			return STATE.Marker;
		case SideBarView.BL_DISTANCE:
			return STATE.Distance;
		case SideBarView.BL_POLYGON:
			return STATE.Polygon;
		case SideBarView.BL_CIRCLE:
			return STATE.Circle;
		case SideBarView.BL_RECTANGLE:
			return STATE.Rectangle;
		case SideBarView.BL_TRIANGLE:
			return STATE.Triangle;
		default:
			return STATE.None;
		}
	}
	
	public STATE getState() {
		return this.state;
	}

	public boolean toolActive(STATE s) {
		return this.state.equals(s);
	}
}
