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
	private ArrayList<SideButton> buttons;
	private SpringLayout layout;
	private Container contentPane;
	
	public SideBarView(InteractiveMapController listener, SpringLayout l, Container cp) {
		super();
		buttons = new ArrayList<SideButton>();
		actionListener = listener;
		mouseListener = listener;
		layout = l;
		contentPane = cp;
		
		guiInit();
	}
	
	private void guiInit() {
		setLayout(new GridLayout(4,2));
		setDoubleBuffered(true);
		setPreferredSize(new Dimension(Program.WINDOW_WIDTH/3, Program.WINDOW_HEIGHT));
		
		buttonsInit();
		
		layout.putConstraint(SpringLayout.NORTH, this, 0, SpringLayout.NORTH, contentPane);
		layout.putConstraint(SpringLayout.EAST, this, 0, SpringLayout.EAST, contentPane);
		layout.putConstraint(SpringLayout.SOUTH, this, 0, SpringLayout.SOUTH, contentPane);
		
		setVisible(true);
	}
	
	private void buttonsInit() {
		buttons.add(new SideButton(BL_MARKER));
		buttons.add(new SideButton(BL_DISTANCE));
		buttons.add(new SideButton(BL_POLYGON));
		buttons.add(new SideButton(BL_RECTANGLE));
		buttons.add(new SideButton(BL_TRIANGLE));
		buttons.add(new SideButton(BL_Ellipse));
		buttons.add(new SideButton(BL_CLEAR));
		buttons.add(new SideButton(BL_EXIT));
		
		addListeners();
	}
	
	private void addListeners() {
		for(JButton btn : buttons) {
			btn.addActionListener(actionListener);
			btn.addMouseListener(mouseListener);
			add(btn);
		}
	}

	public void disableButtons(String button) {
		for(SideButton btn : buttons) {
			if(!btn.getText().equals(button)) {
				btn.setEnabled(false);
				btn.disableAction();
			}
		}
	}
	
	public void enableButtons(String button) {
		for(SideButton btn : buttons) {
			if(!btn.getText().equals(button)) {
				btn.setEnabled(true);
				btn.enableAction();
			}
		}
	}
	
	public void changeCursor(Cursor c) {
		setCursor(c);
	}
}
