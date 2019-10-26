package ticaTacToe.socketsProject.arbani.Window;

import java.awt.Color;
import java.io.Serializable;
import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class Square extends JPanel implements Serializable {

	/**
	 * 
	*/
	private static final long serialVersionUID = 1L;
	JLabel label = new JLabel((Icon) null);

	public Square() {
		setBackground(Color.white);
		add(label);
	}

	public void setIcon(Icon icon) {
		label.setIcon(icon);
	}

}
