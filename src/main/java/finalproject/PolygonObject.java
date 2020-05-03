package finalproject;

import java.awt.Graphics;
import java.awt.Polygon;
import java.awt.Color;
import java.awt.Dimension;
import java.util.*;

import javax.swing.JComponent;

public class PolygonObject extends JComponent implements InteractiveMapObject{
	private static final long serialVersionUID = 1L;
	protected Polygon polygon;
	protected String title;
	
	public PolygonObject(Polygon polygon, String title) {
		super();
		this.polygon = polygon;
		this.title = title;
		setLocation(polygon.getBounds().x,polygon.getBounds().y);
		setBounds(polygon.getBounds());
		setToolTipText(title);
	}
	
	// When Overriding paintComponent Method
	// Polygons Render Twice. Couldn't Debug
	// Implemented Hacky WorkAround.
	public void customPaint(Graphics g) {
		g.setColor(Color.red);
		g.drawPolygon(polygon.xpoints, polygon.ypoints, polygon.npoints);
	}
}