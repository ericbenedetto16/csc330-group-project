package finalproject;

import java.awt.Component;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import javax.swing.JPanel;
import javax.swing.SpringLayout;

public class InteractiveMapView extends JPanel {
	private static final long serialVersionUID = 1L;
	
	private MouseListener mouseListener;
	private Container contentPane;
	private ArrayList<InteractiveMapObject> shapes = new ArrayList<InteractiveMapObject>();
	private SpringLayout layout;
	
	public InteractiveMapView(InteractiveMapController listener, SpringLayout l, Container cp) {
		super();
		mouseListener = listener;
		layout = l;
		contentPane = cp;
		
		guiInit();

	}
	
	private void guiInit() {
		setPreferredSize(new Dimension(Program.WINDOW_WIDTH*2/3, Program.WINDOW_HEIGHT));
		setLayout(null);
		
		addListeners();
		
		layout.putConstraint(SpringLayout.NORTH, this, 5, SpringLayout.NORTH, contentPane);
		layout.putConstraint(SpringLayout.WEST, this, 5, SpringLayout.WEST, contentPane);
		layout.putConstraint(SpringLayout.SOUTH, this, -5, SpringLayout.SOUTH, contentPane);
		
		setVisible(true);
	}
	
	private void addListeners() {
		addMouseListener(mouseListener);
	}
	
	public void refresh(ArrayList<InteractiveMapObject> l) {
		setLayout(null);
		removeAll();
		shapes.clear();
		shapes.addAll(l);
		
		for(InteractiveMapObject obj : shapes) {
			add((Component) obj);
			((Component) obj).addMouseListener(mouseListener);
		}
		
		int counter = shapes.size() - 1;
		for(InteractiveMapObject obj : shapes) {
			setComponentZOrder((Component)obj, counter);
			counter--;
		}
		
		revalidate();
		repaint();
	}
	
	public void changeCursor(Cursor c) {
		setCursor(c);
	}
	
	public void clear() {		
		removeAll();	
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		for(InteractiveMapObject obj : shapes) {
			((InteractiveMapObject) obj).customPaint(g);
		}
	}
}
