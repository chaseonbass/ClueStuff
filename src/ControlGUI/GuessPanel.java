package ControlGUI;

import java.awt.GridLayout;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;


public class GuessPanel extends JPanel{
	private JTextField theGuess;
	public GuessPanel(){
	setLayout(new GridLayout(2,1));
	setBorder(new TitledBorder (new EtchedBorder(), "Guess"));
	JLabel guess= new JLabel("Guess");
	theGuess= new JTextField(20);
	add(guess);
	add(theGuess);
	}
}
