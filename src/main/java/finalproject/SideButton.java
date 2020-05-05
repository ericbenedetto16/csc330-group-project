package finalproject;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.border.LineBorder;

public class SideButton extends JButton {
	private static final long serialVersionUID = 1L;

	public SideButton(String title) {
		super(title);
		setFont(new Font("Arial", Font.BOLD, 14));
		setBorder(new LineBorder(Color.black));
		setBackground(Color.GRAY);
		setForeground(Color.WHITE);
	}
	
	public void enableAction() {
		setBackground(Color.GRAY);
	}
	
	public void disableAction() {
		setBackground(Color.LIGHT_GRAY);
	}
	
	public void hover() {
		setBackground(Color.DARK_GRAY);	
	}
	
	public void hoverDismiss() {
		setBackground(Color.GRAY);
	}
}
