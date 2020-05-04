package finalproject;

import java.awt.Container;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.SpringLayout;

public class SideBarView extends JPanel {
	private static final long serialVersionUID = 1L;
	public static final String BL_MARKER = "Marker";
	public static final String BL_DISTANCE = "Distance";
	public static final String BL_POLYGON = "Polygon";
	public static final String BL_RECTANGLE = "Rectangle";
	public static final String BL_TRIANGLE = "Triangle";
	public static final String BL_Ellipse = "Ellipse";
	public static final String BL_CLEAR = "Clear";
	public static final String BL_EXIT = "Exit";
	
	private ActionListener actionListener;
	private MouseListener mouseListener;
	private ArrayList<JButton> buttons;
	
	public SideBarView(InteractiveMapController listener, SpringLayout layout, Container contentPane) {
		buttons = new ArrayList<JButton>();
		actionListener = listener;
		mouseListener = listener;
		
		setLayout(new GridLayout(4,2));
		setDoubleBuffered(true);
		setPreferredSize(new Dimension(Program.WINDOW_WIDTH/3, Program.WINDOW_HEIGHT));
		
		buttons.add(new JButton(BL_MARKER));
		buttons.add(new JButton(BL_DISTANCE));
		buttons.add(new JButton(BL_POLYGON));
		buttons.add(new JButton(BL_RECTANGLE));
		buttons.add(new JButton(BL_TRIANGLE));
		buttons.add(new JButton(BL_Ellipse));
		buttons.add(new JButton(BL_CLEAR));
		buttons.add(new JButton(BL_EXIT));
		
		for(JButton btn : buttons) {
			btn.addActionListener(actionListener);
			btn.addMouseListener(mouseListener);
			add(btn);
		}

		layout.putConstraint(SpringLayout.NORTH, this, 0, SpringLayout.NORTH, contentPane);
		layout.putConstraint(SpringLayout.EAST, this, 0, SpringLayout.EAST, contentPane);
		layout.putConstraint(SpringLayout.SOUTH, this, 0, SpringLayout.SOUTH, contentPane);
		
		setVisible(true);

	}

	public void disableButtons(String button) {
		for(JButton btn : buttons) {
			if(!btn.getText().equals(button)) {
				btn.setEnabled(false);
			}
		}
	}
	
	public void enableButtons(String button) {
		for(JButton btn : buttons) {
			if(!btn.getText().equals(button)) {
				btn.setEnabled(true);
			}
		}
	}
	
	public void changeCursor(Cursor c) {
		setCursor(c);
	}
}
