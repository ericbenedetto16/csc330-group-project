package finalproject;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;

import javax.swing.JComponent;

public class LineObject extends JComponent implements InteractiveMapObject{
	private static final long serialVersionUID = 1L;
	private MarkerObject p1, p2;
	private Rectangle bounds;
	private String label;
	
	public LineObject(MarkerObject obj1, MarkerObject obj2, String t) {
		super();
		p1 = (obj1.getY() < obj2.getY()) ? obj1 : obj2;
		p2 = (obj1.getY() < obj2.getY()) ? obj2 : obj1;
		label = t;
		
		bounds = new Rectangle(p1.getX(), p1.getY(), 10, 10);
        
		setToolTipText(label);
		setBounds(bounds);
	}
	
	public void customPaint(Graphics g) {
		super.paintComponent(g);
		
		g.setColor(Color.red);
		g.fillRect(bounds.x,bounds.y, bounds.width/2, bounds.height/2);
		
		g.drawLine(p1.getX(), p1.getY(), p2.getX(), p2.getY());
	}

}
