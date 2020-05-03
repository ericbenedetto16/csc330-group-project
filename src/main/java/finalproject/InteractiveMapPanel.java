package finalproject;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

public class InteractiveMapPanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void paintComponent(Graphics g) {
		g.setColor(Color.blue);
		g.fillRect(0, 0, 100, 100);
	}
}
