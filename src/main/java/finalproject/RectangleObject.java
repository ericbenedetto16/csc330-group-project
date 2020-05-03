package finalproject;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import javax.swing.JComponent;

public class RectangleObject extends JComponent implements InteractiveMapObject {
	private static final long serialVersionUID = 1L;
	private Rectangle rect;
	private String label;
	
	public RectangleObject(Rectangle r, String l) {
		super();
		rect = r;
		label = l;
		setToolTipText(label);
		setBounds(rect);
		setVisible(true);
	}
	
	@Override
	public void customPaint(Graphics g) {
		g.setColor(Color.red);
		g.drawRect(rect.x, rect.y, rect.width, rect.height);
	}

}
