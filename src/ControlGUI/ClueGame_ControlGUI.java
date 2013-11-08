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
	
	private int theRoll;
	
	public whoseTurnPanel wtPanel;
	public JButton nextPlayerButton;
	public JButton accusationButton;
	public DiePanel dPanel;
	public GuessPanel gPanel;
	public GuessResultPanel gResult;
	
	public void setRoll(int newRoll) {
		theRoll = newRoll;
	}
	
	public ClueGame_ControlGUI(ClueGame cg){
		this.cg = cg;
		
		theRoll = cg.getRoll();

		setLayout(new GridLayout(2,3));
		
		wtPanel= new whoseTurnPanel(cg.getCurrentPlayer().getName());
		//buttonsPanel bPanel= new buttonsPanel();
		//JPanel blankPanel = new JPanel();
		nextPlayerButton = new JButton("Next Player");
		nextPlayerButton.addActionListener(new NextPlayerButtonListener());
		accusationButton = new JButton("Make Accusation");
		accusationButton.addActionListener(new AccusationButtonListener());
		dPanel= new DiePanel(theRoll);
		gPanel= new GuessPanel();
		gResult= new GuessResultPanel();
		
		add(wtPanel);
		add(nextPlayerButton);
		add(accusationButton);
		add(dPanel);
		add(gPanel);
		add(gResult);
		
	}
	private class NextPlayerButtonListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			cg.move();
			
		}
	}
	
	private class AccusationButtonListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			// do other stuff, like accusing
			
		}
	}
	

}
