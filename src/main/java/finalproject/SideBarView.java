package finalproject;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;

public class SideBarView {
	protected static final int SIDEBAR_WIDTH = Program.WIDTH / 8;
	protected static final int SIDEBAR_HEIGHT = Program.HEIGHT - (Program.HEIGHT/4);
	private static final int HORIZONTAL_OFFSET = Program.WIDTH/4;
	private static final int VERTICAL_OFFSET = 0;

	public void render(Graphics g) {
		FontMetrics fm = g.getFontMetrics();
		
		g.setColor(Color.white);
		g.drawString("Sidebar", (Program.WIDTH/2) + (HORIZONTAL_OFFSET) + (SIDEBAR_WIDTH/2)- fm.stringWidth("Sidebar")/2, (Program.HEIGHT/2) + VERTICAL_OFFSET + fm.getAscent()/2);
		g.drawRect(Program.WIDTH/2 + HORIZONTAL_OFFSET, Program.HEIGHT/2 - (SIDEBAR_HEIGHT / 2), SIDEBAR_WIDTH, SIDEBAR_HEIGHT);
	}
}
