package ControlGUI;

import java.awt.GridLayout;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;


public class DiePanel extends JPanel{
	public JTextField displayRoll;
	
	public DiePanel(int rollC){
		
		setBorder(new TitledBorder (new EtchedBorder(), "Die"));
		
		JLabel roll = new JLabel ("Roll");
		displayRoll = new JTextField(10);
		displayRoll.setEditable(false);
		displayRoll.setText(Integer.toString(rollC));
		add(roll);
		add(displayRoll);
	}
}
