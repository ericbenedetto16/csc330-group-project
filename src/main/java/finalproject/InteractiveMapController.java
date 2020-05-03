package finalproject;

import java.awt.Cursor;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import finalproject.InteractiveMap.STATE;

public class InteractiveMapController implements ActionListener, MouseListener, WindowListener {
	private InteractiveMap map;
	private InteractiveMapView mapView;
	private SideBarView sideView;
	
	private String activeButton;
	private int maxPoints;
	private int numPoints;
	private String label;
	private ArrayList<MarkerObject> points;
	
	public InteractiveMapController() {
		map = new InteractiveMap();
		points = new ArrayList<MarkerObject>();
	}
	
	public void addView(InteractiveMapView view) {
		mapView = view;
	}
	
	public void addView(SideBarView view) {
		sideView = view;
	}
	
	protected void refresh() {
		if(!map.getState().equals(STATE.Marker)) {
			for(MarkerObject point : points) {
				map.removeShape(point);
			}
		}
		
		map.setState(STATE.None);
		numPoints = 0;
		maxPoints = 0;
		points.clear();
		mapView.refresh(map.getShapes());
	}

	private void sideBarAction(JButton button) {
		String name = button.getText();
		STATE tool = map.getState(name);
	
		if(name.equals(SideBarView.BL_EXIT)) { showClosingConfirmation(); return; }
		
		if(name.equals(SideBarView.BL_CLEAR)) { clearGUI(); return; }
		
		if(map.toolActive(tool)) {
			mapView.changeCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			refresh();
			sideView.enableButtons(name);
			return;
		}
		
		map.setState(tool);
		setParameters(tool, name);
		mapView.changeCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));
		sideView.disableButtons(name);
	}

	private void handleInput(Point p) {
		if(map.getState().equals(STATE.None)) { return; }
		
		MarkerObject marker = new MarkerObject(p.x,p.y);
		if(numPoints + 1 == maxPoints) {
			points.add(marker);
			numPoints++;
			label = promptForLabelText();
			mapAction(marker);
			refresh();
			mapView.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			sideView.enableButtons(activeButton);
			System.out.println(label);
			return;
		}
		
		if(numPoints < maxPoints) {
			points.add(marker);
			numPoints++;
			mapAction(marker);
			return;
		}
	}
	
	private void mapAction(MarkerObject obj) {
		System.out.println("You placed the point at: " + obj.getLocation());
		if(map.getState().equals(STATE.Marker) && numPoints == maxPoints) {
			MarkerObject p = points.get(0);
			MarkerObject o = new MarkerObject(p, label);
			
			map.removeShape(p);
			
			map.addShape(o);
			mapView.refresh(map.getShapes());
			return;	
		}
		
		if(map.getState().equals(STATE.Distance) && numPoints == maxPoints) {
			MarkerObject p1 = points.get(0);
			MarkerObject p2 = points.get(1);
			
			LineObject l = new LineObject(points.get(0), points.get(1), label);
			
			map.removeShape(p1);
			map.removeShape(p2);
			
			map.addShape(l);
			mapView.refresh(map.getShapes());
			return;
		}
		
		if(map.getState().equals(STATE.Polygon) && numPoints == maxPoints) {
			int[] x = new int[points.size()];
			int[] y = new int[points.size()];

			for(int i = 0; i < points.size(); i++) {
				x[i] = points.get(i).getX();
				y[i] = points.get(i).getY();
			}

			PolygonObject p = new PolygonObject(new Polygon(x,y, points.size()), label);
			map.addShape(p);
			
			mapView.refresh(map.getShapes());
			
			return;
		}
		
		if(map.getState().equals(STATE.Rectangle) && numPoints == maxPoints) {
			MarkerObject p1 = points.get(0);
			MarkerObject p2 = points.get(1);
			
			int x = p1.getX() < p2.getX() ? p1.getX() : p2.getX();
			int y = p1.getY() < p2.getY() ? p1.getY() : p2.getY();
			
			int w = Math.abs(p1.getX() - p2.getX());
			int h = Math.abs(p1.getY() - p2.getY());
			
			Rectangle r = new Rectangle(x,y,w,h);
			RectangleObject o = new RectangleObject(r, label);
			
			map.addShape(o);
			
			mapView.refresh(map.getShapes());
			return;
		}
		
		
		if(map.getState().equals(STATE.Triangle) && numPoints == maxPoints) {
			int[] x = new int[points.size()];
			int[] y = new int[points.size()];
			
			for(int i = 0; i < points.size(); i++) {
				x[i] = points.get(i).getX();
				y[i] = points.get(i).getY();
			}
			
			PolygonObject t = new PolygonObject(new Polygon(x,y,points.size()),label);
			map.addShape(t);

		}
		
		if(map.getState().equals(STATE.Circle) && numPoints == maxPoints) {
			MarkerObject p1 = points.get(0);
			MarkerObject p2 = points.get(1);
			
			CircleObject c = new CircleObject(p1,p2,label);
			
			map.addShape(c);
			mapView.refresh(map.getShapes());
			return;
		}
		
			map.addShape(obj);
			mapView.refresh(map.getShapes());
	}
	
	private void setParameters(STATE state, String btn) {
		activeButton = btn;
		switch(state) {
		case Marker:
			numPoints = 0;
			maxPoints = 1;
			break;
		case Distance:
			numPoints = 0;
			maxPoints = 2;
			break;
		case Triangle:
			numPoints = 0;
			maxPoints = 3;
			break;
		case Circle:
			numPoints = 0;
			maxPoints = 2;
			break;
		case Polygon:
			numPoints = 0;
			maxPoints = 6;
			break;
		case Rectangle:
			numPoints = 0;
			maxPoints = 2;
			break;
		default:
			break;
		}
	}
	
	private void clearGUI() {
		map.clearShapes();
		mapView.refresh(map.getShapes());
	}
	
	private String promptForLabelText() {
		String options[] = {"Ok"};
		JPanel panel = new JPanel();
		JLabel lbl = new JLabel("Enter a Label: ");
		JTextField txt = new JTextField(10);
		panel.add(lbl);
		panel.add(txt);
		
		JOptionPane.showOptionDialog(null,panel,
				"Label Maker", JOptionPane.NO_OPTION, 
				JOptionPane.QUESTION_MESSAGE,null,
				options,null
				);
		
		return txt.getText();
	}
	
	private void showClosingConfirmation() {
		String options[] = {"Yes","No"};
		
	    int confirmation = JOptionPane.showOptionDialog(null, 
	        "Are You Sure You Want to Close?", "CSI Interactive Map", 
	        JOptionPane.DEFAULT_OPTION, JOptionPane.YES_NO_OPTION, null, 
	        options,options[1]);

	    if(confirmation == 0)   
	    	System.exit(0);
	}
	
	// Overridden Methods
	@Override
	public void mouseClicked(MouseEvent e) {
		if(!e.getComponent().isValid()) { return; }
		
		Object source = e.getSource().getClass();
		
		
		if(SwingUtilities.isRightMouseButton(e)) {
			if(InteractiveMapObject.class.isAssignableFrom(e.getSource().getClass())) {
				System.out.println(e.getSource());
		
				InteractiveMapObject m = (InteractiveMapObject)e.getSource();
				
				if(!map.getState().equals(STATE.None)) {
					points.remove(m);
					numPoints--;
				}
				
				map.removeShape(m);
				mapView.refresh(map.getShapes());
			}
			return;
		}
		
		if(source.equals(InteractiveMapView.class)) {
			handleInput(e.getPoint());
			return;
		}
		
		if(source.equals(PolygonObject.class)) {
			int cX = e.getComponent().getX();
			int cY = e.getComponent().getY();
			handleInput(new Point((int)(cX+e.getPoint().getX()),(int)(cY+e.getPoint().getY())));
			return;
		}
		
		if(source.equals(MarkerObject.class)) {
			if(map.getState().equals(STATE.Polygon)) {
				if(points.size() < 3) { return; }
	
				MarkerObject m = (MarkerObject)e.getSource();
	
				if(points.get(0).equals(m)) {
					int[] x = new int[points.size()];
					int[] y = new int[points.size()];
	
					for(int i = 0; i < points.size(); i++) {
						x[i] = points.get(i).getX();
						y[i] = points.get(i).getY();
					}
	
					PolygonObject p = new PolygonObject(new Polygon(x,y, points.size()), "Polygon");
					map.addShape(p);
					mapView.refresh(map.getShapes());
					
					map.setState(STATE.None);
					mapView.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
					refresh();
					sideView.enableButtons(SideBarView.BL_POLYGON);
				}
			}
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		
		if(source.getClass().equals(JButton.class)) {
			JButton button = (JButton) source;

			sideBarAction(button);
		}
	}
	
	@Override
	public void windowClosing(WindowEvent e) {
		showClosingConfirmation();
	}
	
	@Override
	public void mouseReleased(MouseEvent e) {}

	@Override
	public void mouseEntered(MouseEvent e) {
		Object source = e.getSource();
		if(source.getClass().equals(JButton.class)) {
			JButton c = (JButton)e.getComponent();
			if(c.isEnabled()) {

			sideView.changeCursor(new Cursor(Cursor.HAND_CURSOR));
			}
		}
	}

	@Override
	public void mouseExited(MouseEvent e) {
		Object source = e.getSource();
		
		if(source.getClass().equals(JButton.class)) {
			sideView.changeCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		}
	}
	
	@Override
	public void windowOpened(WindowEvent e) {}

	@Override
	public void windowClosed(WindowEvent e) {}

	@Override
	public void windowIconified(WindowEvent e) {}

	@Override
	public void windowDeiconified(WindowEvent e) {}

	@Override
	public void windowActivated(WindowEvent e) {}

	@Override
	public void windowDeactivated(WindowEvent e) {}
}
