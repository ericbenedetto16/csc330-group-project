package finalproject;

import java.awt.Cursor;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import org.json.simple.parser.ParseException;

import finalproject.State;

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
		try {
			map = new InteractiveMap();
		} catch (IOException | ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		points = new ArrayList<MarkerObject>();
	}
	
	public void addView(InteractiveMapView view) {
		mapView = view;
	}
	
	public void addView(SideBarView view) {
		sideView = view;
	}
	
	protected void refresh() {
		if(!map.getState().equals(State.Marker)) {
			for(MarkerObject point : points) {
				map.removeShape(point);
			}
		}
		
		map.setState(State.None);
		numPoints = 0;
		maxPoints = 0;
		points.clear();
		mapView.refresh(map.getShapes());
	}

	private void sideBarAction(JButton button) {
		String name = button.getText();
		State tool = map.getState(name);
	
		if(name.equals(SideBarView.BL_EXIT)) { showClosingConfirmation(); return; }
		
		if(name.equals(SideBarView.BL_CLEAR)) { clearGUI(); return; }
		
		if(map.toolActive(tool)) {
			deactivateTool(name);
		}else {
			activateTool(name, tool);
		}
	}
	
	private void activateTool(String name, State tool) {
		map.setState(tool);
		mapView.changeCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));
		setParameters(tool, name);
		sideView.disableButtons(name);
	}
	
	private void deactivateTool(String name) {
		mapView.changeCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		refresh();
		sideView.enableButtons(name);
	}

	private void handleInput(Point p) {
		if(map.getState().equals(State.None)) { return; }
		if(numPoints > maxPoints) { return; }
		
		MarkerObject marker = new MarkerObject(p.x,p.y);
		
		// If Last Point
		if(numPoints + 1 == maxPoints) {
			addPoint(marker, true);
			deactivateTool(activeButton);
		} else {
			addPoint(marker, false);
		}
	}
	
	private void addPoint(MarkerObject o, boolean last) {
		points.add(o);
		numPoints++;
		
		if(last) label = promptForLabelText();
		
		mapAction(o);
	}
	
	private void removePoint(InteractiveMapObject o) {
		points.remove(o);
		numPoints--;
	}
	
	private void popShape(InteractiveMapObject o) {
		map.removeShape(o);
		mapView.refresh(map.getShapes());
	}
	
 	private void forceClosePolygon(MouseEvent e) {
 		if(points.size() < 3) { return; }

		MarkerObject m = (MarkerObject)e.getSource();

		if(points.get(0).equals(m)) {
			maxPoints = points.size();
			label = promptForLabelText();
			mapAction(points.get(0));
			deactivateTool(SideBarView.BL_POLYGON);					
		}
		
	}
	
	private Point pointRelativetoMap(MouseEvent e) {
		int cX = e.getComponent().getX(), cY = e.getComponent().getY();
		return new Point((int)(cX+e.getPoint().getX()),(int)(cY+e.getPoint().getY()));
	}
 	
	private void pushShape(InteractiveMapObject o) {
		map.addShape(o);
		mapView.refresh(map.getShapes());
	}
	
	private void mapAction(MarkerObject obj) {
		System.out.println("You placed the point at: " + obj.getLocation());
		System.out.println("Point at " + map.convertToLng(obj.getX()) + ", " + map.convertToLat(obj.getY()));
		
		if(numPoints != maxPoints) { pushShape(obj); return; }
		
		if(map.getState().equals(State.Marker)) {
			MarkerObject p = points.get(0);
			
			double center[] = map.getCenter(p);
			
			MarkerObject o = new MarkerObject(p, map.buildToolTipText(label, center[0], center[1]));
			
			map.removeShape(p);
			pushShape(o);
		}
		
		if(map.getState().equals(State.Distance)) {
			MarkerObject p1 = points.get(0);
			MarkerObject p2 = points.get(1);
			
			double miles = map.haversine(p1, p2);
			double feet = map.toFeet(miles);

			double[] center = map.getCenter(p1, p2);

			LineObject l = new LineObject(p1, p2, map.buildToolTipText(label, center[0], center[1], miles, feet));
			
			map.removeShape(p1);
			map.removeShape(p2);
			pushShape(l);
		}
		
		if(map.getState().equals(State.Rectangle)) {
			MarkerObject p1 = points.get(0);
			MarkerObject p2 = points.get(1);
	
			Rectangle r = map.initRectangle(p1, p2);
			double area = map.calcArea(r);
			double center[] = map.getCenter(r);
			
			RectangleObject o = new RectangleObject(r, map.buildToolTipText(label, center[0], center[1], area));
			
			pushShape(o);
		}
		
		if(map.getState().equals(State.Triangle) ^ map.getState().equals(State.Polygon)) {
			ArrayList<int[]> xy = map.initPolygon(points);
			int[] x = xy.get(0),  y = xy.get(1);
			
			Polygon poly = new Polygon(x,y, points.size());
			
			double[] center = map.getCenter(poly);
			double area = map.calcArea(poly);
			
			PolygonObject p = new PolygonObject(poly, map.buildToolTipText(label, center[0], center[1], area));
			
			pushShape(p);
		}
		
		if(map.getState().equals(State.Circle)) {
			MarkerObject p1 = points.get(0), p2 = points.get(1);

			double[] center = map.getCenter(p1,p2);
			double area = map.calcArea(new EllipseObject(p1,p2,null));
			
			EllipseObject c = new EllipseObject(p1,p2,map.buildToolTipText(label, center[0], center[1], area));
			
			pushShape(c);
		}
	}
	
	private void setParameters(State state, String btn) {
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
	
	@Override
	public void mouseClicked(MouseEvent e) {
		if(!e.getComponent().isValid()) { return; }
		
		Object source = e.getSource().getClass();
		
		if(SwingUtilities.isRightMouseButton(e)) {
			if(!InteractiveMapObject.class.isAssignableFrom(e.getSource().getClass())) {return;}
			
			InteractiveMapObject m = (InteractiveMapObject)e.getSource();
			
			if(!map.getState().equals(State.None) && e.getSource().getClass().equals(MarkerObject.class)) removePoint(m);
			
			popShape(m);
		} else {
			if(source.equals(InteractiveMapView.class)) {
				handleInput(e.getPoint());
			}
	
			if(InteractiveMapObject.class.isAssignableFrom(e.getSource().getClass())) {
				if(source.equals(MarkerObject.class) && map.getState().equals(State.Polygon)) {
					forceClosePolygon(e);
				} else {
					handleInput(pointRelativetoMap(e));
				}
			}
		}
	}
	
	@Override
	public void mousePressed(MouseEvent e) {}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		
		if(source.getClass().equals(SideButton.class)) {
			SideButton button = (SideButton) source;

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
		if(source.getClass().equals(SideButton.class)) {
			SideButton c = (SideButton)e.getComponent();
			if(c.isEnabled()) {
				sideView.changeCursor(new Cursor(Cursor.HAND_CURSOR));
				c.hover();
			}
		}
	}

	@Override
	public void mouseExited(MouseEvent e) {
		Object source = e.getSource();
		
		if(source.getClass().equals(SideButton.class)) {
			SideButton c = (SideButton)e.getComponent();
			sideView.changeCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			if(c.isEnabled()) {
				c.hoverDismiss();
			}
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
