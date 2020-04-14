package finalproject;

import java.awt.Canvas;
import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Window extends Canvas {

	private static final long serialVersionUID = -4219692561193881812L;
	
	public Window(int width, int height, String title, Program program) {
		Dimension d = new Dimension(width, height);
		
		JFrame frame = new JFrame(title);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setPreferredSize(d);
		frame.setMinimumSize(d);
		frame.setMaximumSize(d);
		frame.setResizable(true);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		frame.add(program);
		frame.pack();
		
		program.start();

	}
}
