package finalproject;

import java.awt.Graphics;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.util.*;

import javax.swing.JComponent;

public class PolygonObject extends JComponent implements InteractiveMapObject{
	private static final long serialVersionUID = 1L;
	private Polygon polygon;
	private String title;
	private Rectangle bounds;
	private String baseName;
	private double[] center;
	private boolean baseShape;
	private boolean grass;
	
	public PolygonObject(Polygon poly, String l) {
		super();
		baseShape = false;
		grass = false;
		polygon = poly;
		title = l;
		bounds = polygon.getBounds();
		setLocation(bounds.x,bounds.y);
		setBounds(polygon.getBounds());
		setToolTipText(title);
	}
	
	public PolygonObject(Polygon poly, String l, double[] c, String b) {
		super();
		baseShape = true;
		grass = false;
		polygon = poly;
		title = l;
		baseName = b;
		center = c;
		bounds = polygon.getBounds();
		setLocation(bounds.x,bounds.y);
		setBounds(polygon.getBounds());
		setToolTipText(title);
	}
	
	public PolygonObject(Polygon poly, boolean g) {
		super();
		baseShape = true;
		grass= true;
		polygon = poly;
		bounds = polygon.getBounds();
		setLocation(bounds.x,bounds.y);
		setBounds(polygon.getBounds());
	}

	// When Overriding paintComponent Method
	// Polygons Render Twice. Couldn't Debug
	// Implemented Hacky WorkAround.
	@Override
	public void customPaint(Graphics g) {
		g.setColor(Color.black);
		if(!baseShape) g.setColor(Color.red);
		if(grass) g.setColor(Color.green);
		if(baseShape || grass) g.fillPolygon(polygon.xpoints, polygon.ypoints, polygon.npoints);
		g.drawPolygon(polygon.xpoints, polygon.ypoints, polygon.npoints);
		if(baseShape && !Objects.isNull(baseName)) {
			Font fnt = new Font("arial", Font.BOLD, 10);
			FontMetrics fm = g.getFontMetrics(fnt);
			g.setFont(fnt);
			g.setColor(Color.white);
		    g.drawString(baseName, (int)center[1] - (fm.stringWidth(baseName)/2) , (int)center[0] + fm.getAscent() / 2);
		}
	}
}