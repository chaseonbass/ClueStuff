package ControlGUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;


public class buttonsPanel extends JPanel{
	//Buttons
	public buttonsPanel(){
			JButton nextPlayer= new JButton("Next Player");
			nextPlayer.setPreferredSize(new Dimension(150,70));
			JButton mkAccusation= new JButton("Make an Accusation");
			mkAccusation.setPreferredSize(new Dimension(150,70));
			JPanel button= new JPanel();
			button.setLayout(new GridLayout(1,3));
			button.add(nextPlayer);
			button.add(mkAccusation);
			add(nextPlayer);
			add(mkAccusation);
	}			
}
