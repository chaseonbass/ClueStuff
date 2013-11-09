package ControlGUI;

import java.awt.GridLayout;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;


public class GuessPanel extends JPanel{
	public JTextField theGuess;
	public GuessPanel(){
	setLayout(new GridLayout(2,1));
	setBorder(new TitledBorder (new EtchedBorder(), "Guess"));
	JLabel guess= new JLabel("Guess");
	theGuess= new JTextField(20);
	theGuess.setEditable(false);
	add(guess);
	add(theGuess);
	}
}
