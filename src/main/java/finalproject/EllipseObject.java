package finalproject;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;

import javax.swing.JComponent;

public class EllipseObject extends JComponent implements InteractiveMapObject {
	private static final long serialVersionUID = 1L;
	private Point p1;
	private Point p2;
	private Rectangle bounds;
	String label;
	
	public EllipseObject(MarkerObject o1, MarkerObject o2, String l) {
		super();
		p1 = o1.getLocation();
		p2 = o2.getLocation();

		int x = (int) (p1.getX() < p2.getX() ? p1.getX() : p2.getX());
		int y = (int) (p1.getY() < p2.getY() ? p1.getY() : p2.getY());
		
		int w = (int) Math.abs(p1.x - p2.x), h = (int) Math.abs(p1.y - p2.y);
		
		bounds = new Rectangle(x,y,w,h);
		setBounds(bounds);
		setToolTipText(l);
		setVisible(true);
	}

	@Override
	public void customPaint(Graphics g) {
		g.setColor(Color.red);
		g.drawOval(bounds.x, bounds.y, bounds.width, bounds.height);
	}

}
