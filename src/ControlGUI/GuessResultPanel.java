package ControlGUI;

import java.awt.GridLayout;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;


public class GuessResultPanel extends JPanel{
	public JTextField displayResult;
	public GuessResultPanel() {
		setLayout(new GridLayout(2,1));
		displayResult= new JTextField(20);
		displayResult.setEditable(false);
		setBorder(new TitledBorder (new EtchedBorder(), "Guess Result"));
		JLabel gResult= new JLabel("Response");
		add(gResult);
		add(displayResult);
	}

}
