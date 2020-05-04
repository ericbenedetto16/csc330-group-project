package finalproject;

import java.awt.Graphics;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.Color;
import java.awt.Dimension;
import java.util.*;

import javax.swing.JComponent;

public class PolygonObject extends JComponent implements InteractiveMapObject{
	private static final long serialVersionUID = 1L;
	private Polygon polygon;
	private String title;
	private Rectangle bounds;
	
	public PolygonObject(Polygon poly, String l) {
		super();
		polygon = poly;
		title = l;
		bounds = polygon.getBounds();
		setLocation(bounds.x,bounds.y);
		setBounds(polygon.getBounds());
		setToolTipText(title);
	}
	
	// When Overriding paintComponent Method
	// Polygons Render Twice. Couldn't Debug
	// Implemented Hacky WorkAround.
	@Override
	public void customPaint(Graphics g) {
		g.setColor(Color.red);
		g.drawPolygon(polygon.xpoints, polygon.ypoints, polygon.npoints);
	}
}