package finalproject;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.WindowListener;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SpringLayout;

public class Window extends JFrame {

	private static final long serialVersionUID = -4219692561193881812L;
	private  SpringLayout layout;
	private JPanel sidePanel;
	private JPanel mapPanel;
	private Container contentPane;
	private WindowListener windowListener;
	private Dimension dim;
	
	public Window(int width, int height) {
		dim = new Dimension(width, height);
		layout = new SpringLayout();
		contentPane = getContentPane();
		contentPane.setLayout(layout);
		
		InteractiveMapController listener = new InteractiveMapController();
		windowListener = listener;
		
		sidePanel = new SideBarView(listener, layout, contentPane);
		mapPanel = new InteractiveMapView(listener, layout, contentPane);
		
		layout.putConstraint(SpringLayout.EAST, mapPanel, -5, SpringLayout.WEST, sidePanel);
		
		listener.addView((SideBarView)sidePanel);
		listener.addView((InteractiveMapView)mapPanel);
		listener.refresh();
		addWindowListener(windowListener);
		
		contentPane.add(sidePanel);
		contentPane.add(mapPanel);

		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setPreferredSize(dim);
		setMinimumSize(new Dimension(width, height));
		setMaximumSize(new Dimension(width, height));
		
		setResizable(false);
		setLocationRelativeTo(null);

		pack();
		setVisible(true);	
	}
}
