package finalproject;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;

public class ToolView {

	public void render(Graphics g) {
		FontMetrics fm = g.getFontMetrics();
		
		g.setColor(Color.pink);
		g.drawString("Tool Overlay", Program.WIDTH/2 - fm.stringWidth("Tool Overlay")/2, Program.HEIGHT/2 + fm.getAscent()/2 + 40);
		g.drawRect(Program.WIDTH/2 - MapView.MAP_WIDTH/2, Program.HEIGHT/2 - MapView.MAP_HEIGHT/2, MapView.MAP_WIDTH, MapView.MAP_HEIGHT);
	}
}
