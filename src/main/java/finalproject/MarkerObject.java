package finalproject;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

import javax.swing.JComponent;

public class MarkerObject extends JComponent implements InteractiveMapObject {
	private static final long serialVersionUID = 1L;
	private Point p;
	
	public MarkerObject(int x, int y) {
		super();
		p = new Point(x,y);
		setBounds(this.p.x,this.p.y,10,10);
		setLocation(p);
		setVisible(true);
	}
	


	public MarkerObject(MarkerObject markerObject, String l) {
		super();
		p = markerObject.getLocation();
		setBounds(this.p.x,this.p.y,10,10);
		setLocation(p);
		setToolTipText(l);
		setVisible(true);
	}

	@Override
	public void customPaint(Graphics g) {
		super.paintComponent(g);
		g.setColor(Color.red);
		g.fillOval(p.x, p.y, 5, 5);
	}
}
