package finalproject;

import java.awt.Canvas;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Window extends Canvas {

	private static final long serialVersionUID = -4219692561193881812L;
	JFrame frame;
	
	public Window(int width, int height, String title, Program program) {
		Dimension d = new Dimension(width, height);
		
		JFrame frame = new JFrame(title);
		this.frame = frame;
		this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.frame.setPreferredSize(d);
		this.frame.setMinimumSize(d);
		this.frame.setMaximumSize(d);
		this.frame.setResizable(false);
		this.frame.setLocationRelativeTo(null);
		this.frame.setVisible(true);

		this.frame.add(program);
		this.frame.pack();
		
		program.start();

	}
}
