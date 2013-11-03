package ControlGUI;

import java.awt.BorderLayout;
import java.awt.ComponentOrientation;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.TextField;

import javax.swing.*;


public class whoseTurnPanel extends JPanel{
	private JTextField whose_turn;
	public whoseTurnPanel(){
		setLayout(new GridLayout(2,1));
		JLabel turnLabel= new JLabel("                Whose Turn?");
		whose_turn = new JTextField(10);
		whose_turn.setFont(new Font("Time New Roman", Font.BOLD, 12));
		add(turnLabel);
		add(whose_turn);
	}

}
