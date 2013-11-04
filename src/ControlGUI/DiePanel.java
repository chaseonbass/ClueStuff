package ControlGUI;

import java.awt.GridLayout;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;


public class DiePanel extends JPanel{
	private JTextField displayRoll;
	
	public DiePanel(){
		
		setBorder(new TitledBorder (new EtchedBorder(), "Die"));
		
		JLabel roll = new JLabel ("Roll");
		displayRoll = new JTextField(10);
		add(roll);
		add(displayRoll);
	}
}
