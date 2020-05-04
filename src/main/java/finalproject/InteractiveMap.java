package finalproject;

import java.awt.Polygon;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Arrays;

import net.sf.geographiclib.Geodesic;
import net.sf.geographiclib.PolygonArea;
import net.sf.geographiclib.PolygonResult;

public class InteractiveMap {
	private State state = State.None;
	
	public static final int MAP_WIDTH = 524;
	public static final int MAP_HEIGHT = 661;
	
	public static final double minLng = -74.15552;
	public static final double maxLng = -74.14424;
	public static final double maxLat = 40.59493;
	public static final double minLat = 40.60826;
	
	private ArrayList<InteractiveMapObject> baseShapes = new ArrayList<InteractiveMapObject>();
	private ArrayList<InteractiveMapObject> userShapes = new ArrayList<InteractiveMapObject>();
	
	public InteractiveMap() {
//		double top = 40.6030;
//		double bottom = 40.6019;
//		double left = -74.1525;
//		double right = -74.1512;
		
		double top = 40.6030;
		double bottom = 40.6019;
		double left = -74.1525;
		double right = -74.1512;
		
		// To get the coordinate do the percentage of how far
		// it is between the smallest and biggest ranges and multiply
		// that value by the width of the window
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
				buildToolTipText("Baseball Field", calcCenter(top,bottom), calcCenter(left, right))
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
				buildToolTipText("5N", calcCenter(lat1,lat3), calcCenter(lng1,lng4))
				);
		
		baseShapes.add(baseballField);
		baseShapes.add(Building5N);
	}
	
	public double calcArea(Polygon o) {
		PolygonArea pa = new PolygonArea(Geodesic.WGS84, false);

        for(int i = 0; i < o.npoints; i++) {
        	double lat = convertToLat(o.ypoints[i]), lng = convertToLng(o.xpoints[i]);
        	pa.AddPoint(lat,lng);
        }
        
        PolygonResult r = pa.Compute();
        
        System.out.println("Area (Sq. Feet): " + r.area * 10.764);
        System.out.println("Area (Sq. Miles): " + r.area / 2590000 );
		
		
		return toSqFeetMeters(Math.abs(r.area));
	}
	
	public ArrayList<int[]> initPolygon(ArrayList<MarkerObject> points) {
			int[] x = new int[points.size()];
			int[] y = new int[points.size()];
			
			for(int i = 0; i < points.size(); i++) {
				x[i] = points.get(i).getX();
				y[i] = points.get(i).getY();
			}
			
			return new ArrayList<int[]>(Arrays.asList(x,y));
	}
	
	public Rectangle initRectangle(MarkerObject p1, MarkerObject p2) {
		int x = p1.getX() < p2.getX() ? p1.getX() : p2.getX();
		int y = p1.getY() < p2.getY() ? p1.getY() : p2.getY();
		
		int w = Math.abs(p1.getX() - p2.getX());
		int h = Math.abs(p1.getY() - p2.getY());
		
		return new Rectangle(x,y,w,h);
	}
	
	public double[] getCenter(Polygon o) {
		Rectangle bounds = o.getBounds();
		double centerLat = convertToLat(bounds.getCenterY()), centerLng = convertToLng(bounds.getCenterX());
		return new double[] {centerLat, centerLng};
	}
	
	public double calcArea(Rectangle o) {
		// Get Haversine Distance Between
		// TL TR (MinX, MaxY) (MaxX, MaxY)
		double tlLat = convertToLat(o.getMaxY()), tlLng = convertToLng(o.getMinX());
		double trLat = convertToLat(o.getMaxY()), trLng = convertToLng(o.getMaxX());
		double haverW = haversine(tlLat, tlLng, trLat, trLng);
		
		// TL BL (MinX, MaxY) (MinX, MinY)
		double blLat = convertToLat(o.getMinY()), blLng = convertToLng(o.getMinX());
		double haverH = haversine(tlLat, tlLng, blLat, blLng);
		
		// Multiply W X H
		double sqMiles = haverW * haverH;
		return toSqFeetMiles(sqMiles);
	}
	
	public double calcArea(CircleObject o) {
		Rectangle bounds = o.getBounds();
		double lng1 = bounds.getMinX();
		double lng2 = bounds.getCenterX();
		double latH = bounds.getCenterY();

		double r = haversine(convertToLat(latH), convertToLng(lng1), convertToLat(latH), convertToLng(lng2));
		double area = Math.PI *(r*r);
		return toSqFeetMiles(area);
	}
	
	public double[] getCenter(MarkerObject o) {
		double lat = convertToLat(o.getY()), lng = convertToLng(o.getY());
		return new double[] {lat,lng};
	}
	
	public double scaleWidth(double x) {
		return (0*(1-((x-minLng)/(maxLng-minLng))) + (MAP_WIDTH*((x-minLng)/(maxLng-minLng))));
	}
	
	public double scaleHeight(double y) {
		return (0*(1-((y-minLat)/(maxLat-minLat))) + (MAP_HEIGHT*((y-minLat)/(maxLat-minLat))));
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
	
	public double toSqFeetMeters(double meters) {
		return meters * 10.764;
	}
	
	public double toSqFeetMiles(double miles) {
		return miles * 27880000;
	}
	
	public double toSqMilesFeet(double feet) {
		return feet / 2590000;
	}
	
	public String buildToolTipText(String name) {
		return "<html>"
				+ "<p><strong>Name:</strong> " + name + "</p>"
				+ "</html>";
	}
	
	public String buildToolTipText(String name, double lat, double lng) {
		return "<html>"
				+ "<p><strong>Name:</strong> " + name + "</p>"
				+ "<p><strong>Center:</strong> " + String.format("%.4f", lat) + ", " + String.format("%.4f", lng) + "</p>"
				+ "</html>";
	}
	
	// Use toFeet() so there is no 4th Parameter to allow Overloading for area vs. Distance
	public String buildToolTipText(String name, double lat, double lng, double area) {
		return "<html>"
				+ "<p><strong>Name:</strong> " + name + "</p>"
				+ "<p><strong>Area (Sq. Feet):</strong> " + String.format("%.3f", area) + "</p>"
				+ "<p><strong>Area (Sq. Miles):</strong> " + String.format("%.5f", toSqMilesFeet(area)) + "</p>"
				+ "<p><strong>Center:</strong> " + String.format("%.4f", lat) + ", " + String.format("%.4f", lng) + "</p>"
				+ "</html>";
	}
	
	public String buildToolTipText(String name, double lat, double lng, double miles, double feet) {
		return "<html>"
				+ "<p><strong>Name:</strong> " + name + "</p>"
				+ "<p><strong>Distance (miles):</strong> " + String.format("%.2f", miles) + "</p>"
				+ "<p><strong>Distance (feet):</strong> " + String.format("%.2f", feet) + "</p>"
				+ "<p><strong>Center:</strong> " + String.format("%.4f", lat) + ", " + String.format("%.4f", lng) + "</p>"
				+ "</html>";
	}
	
	public double calcDistance(MarkerObject p1, MarkerObject p2) {
		// Take all the Points in the Shape and Calc
		double x1 = p1.getLocation().x, x2 = p2.getLocation().x;
		double y1 = p1.getLocation().y, y2 = p2.getLocation().y;
		
		return Math.sqrt((y2-y1) * (y2-y1) + (x2 -x1) * (x2-x1));
	}
	
	public double convertToLng(double x) {
		double decPerPx = (maxLng - minLng)/MAP_WIDTH;
		return (x * decPerPx) + minLng;
	}
		
	public double convertToLat(double y) {
		double decPerPx = (maxLat - minLat)/MAP_HEIGHT;
		return (y * decPerPx) + minLat;
	}
	
	public double[] getCenter(MarkerObject p1, MarkerObject p2) {
		double lat1 = convertToLng(p1.getX()), lat2 = convertToLng(p2.getX());
		double lng1 = convertToLat(p1.getY()), lng2 = convertToLat(p2.getY());
		
		return new double[] { calcCenter(lng1,lng2), calcCenter(lat1,lat2) };

	}
	
	public double calcCenter(double first, double second) {
		return first + ((second - first)/2);
	}
	
	public double haversine(MarkerObject p1, MarkerObject p2) {
		double x1 = convertToLng(p1.getX()), x2 = convertToLng(p2.getX());
		double y1 = convertToLat(p1.getY()), y2 = convertToLat(p2.getY());

		return haversine(y1, x1, y2, x2);
	}
	
	// Returns Distance in Miles between 2 Points
	public double haversine(double y1, double x1, double y2, double x2) {
		int earth_radius = 3961;
		double dLat = Math.toRadians(y2 - y1);
		double dLng = Math.toRadians(x2 - x1);
		
		double a = Math.sin(dLat/2) * Math.sin(dLat/2) + Math.cos(Math.toRadians(y2)) * Math.cos(Math.toRadians(y2)) * Math.sin(dLng/2) * Math.sin(dLng/2);
		double c = 2 * Math.asin(Math.sqrt(a));
		double d = earth_radius * c;
		
		return d;
	}
	
	public double toFeet(double miles) {
		return miles * 5280;
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
	
	public void setState(State s) {
		this.state = s;
	}
	
	public State getState(String s) {
		switch(s) {
		case SideBarView.BL_MARKER:
			return State.Marker;
		case SideBarView.BL_DISTANCE:
			return State.Distance;
		case SideBarView.BL_POLYGON:
			return State.Polygon;
		case SideBarView.BL_CIRCLE:
			return State.Circle;
		case SideBarView.BL_RECTANGLE:
			return State.Rectangle;
		case SideBarView.BL_TRIANGLE:
			return State.Triangle;
		default:
			return State.None;
		}
	}
	
	public State getState() {
		return this.state;
	}

	public boolean toolActive(State s) {
		return this.state.equals(s);
	}
}
