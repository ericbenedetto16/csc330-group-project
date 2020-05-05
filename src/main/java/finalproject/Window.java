package finalproject;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.WindowListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SpringLayout;

public class Window extends JFrame {

	private static final long serialVersionUID = -4219692561193881812L;
	private  SpringLayout layout;
	private JPanel sidePanel;
	private JPanel mapPanel;
	private Container contentPane;
	private InteractiveMapController listener;
	private Dimension dim;
	private ImageIcon icon;
	private String title;
	
	public Window(int width, int height, String t, String iconPath) {
		dim = new Dimension(width, height);
		title = t;
		icon = new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/" + iconPath)));
		guiInit();
	}
	
	private void guiInit() {
		layout = new SpringLayout();
		contentPane = getContentPane();
		contentPane.setLayout(layout);
		
		createListener();
		sidePanel = new SideBarView(listener, layout, contentPane);
		mapPanel = new InteractiveMapView(listener, layout, contentPane);
		
		layout.putConstraint(SpringLayout.EAST, mapPanel, -5, SpringLayout.WEST, sidePanel);
		
		addListeners();
		addPanels();
		
		setTitle(title);
		setIconImage(icon.getImage());
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setPreferredSize(dim);
		setMinimumSize(dim);
		setMaximumSize(dim);
		
		setResizable(false);
		setLocationRelativeTo(null);

		pack();
		setVisible(true);
	}
	
	private void createListener() {
		InteractiveMapController l = new InteractiveMapController();
		listener = l;
	}
	
	private void addListeners() {
		listener.addView((SideBarView)sidePanel);
		listener.addView((InteractiveMapView)mapPanel);
		listener.refresh();
		addWindowListener(listener);
	}
	
	private void addPanels() {
		contentPane.add(sidePanel);
		contentPane.add(mapPanel);
	}
}
