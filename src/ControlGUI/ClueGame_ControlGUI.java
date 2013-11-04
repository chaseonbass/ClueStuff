package ControlGUI;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.*;

import javax.swing.*;
import javax.swing.plaf.basic.BasicButtonListener;
import cluePlayer.*;


public class ClueGame_ControlGUI extends JPanel{
	ClueGame cg;
	
	public Component createNorthLayout(){
		JPanel panel= new JPanel();
		panel.setLayout(new GridLayout(1,3));
		whoseTurnPanel wtPanel= new whoseTurnPanel(cg.getCurrentPlayer().getName());
		panel.add(wtPanel);
		buttonsPanel bPanel= new buttonsPanel();
		panel.add(bPanel);
		return panel;
		
	}
	public Component createSouthLayout(){
		JPanel panel= new JPanel();
		panel.setLayout(new GridLayout(3,1));
		DiePanel dPanel= new DiePanel(cg.getRoll());
		panel.add(dPanel);
		GuessPanel gPanel= new GuessPanel();
		panel.add(gPanel);
		GuessResultPanel gResult= new GuessResultPanel();
		panel.add(gResult);
		return panel;
	}
	public ClueGame_ControlGUI(ClueGame cg){
		this.cg = cg;

		setLayout(new GridLayout(2,3));
		
		whoseTurnPanel wtPanel= new whoseTurnPanel(cg.getCurrentPlayer().getName());
		//buttonsPanel bPanel= new buttonsPanel();
		//JPanel blankPanel = new JPanel();
		JButton nextPlayerButton = new JButton("Next Player");
		nextPlayerButton.addActionListener(new ButtonListener());
		JButton accusationButton = new JButton("Make Accusation");
		accusationButton.addActionListener(new ButtonListener());
		DiePanel dPanel= new DiePanel(cg.getRoll());
		GuessPanel gPanel= new GuessPanel();
		GuessResultPanel gResult= new GuessResultPanel();
		
		add(wtPanel);
		add(nextPlayerButton);
		add(accusationButton);
		add(dPanel);
		add(gPanel);
		add(gResult);
		
		
		//add(createNorthLayout(), BorderLayout.NORTH);
		//add(createSouthLayout(), BorderLayout.SOUTH);
		
		
		
	}
	private class ButtonListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			cg.move();
			System.out.println("Button pressed");
			
		}
	}
	/*public static void main(String[] args) {
		ClueGame_ControlGUI gui= new ClueGame_ControlGUI();
		
		gui.setVisible(true);
	}*/

}
