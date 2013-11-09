package makeDialogs;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;


import DetectiveNotesGUI.PersonGuessPanel;
import DetectiveNotesGUI.RoomGuessPanel;
import DetectiveNotesGUI.WeaponGuessPanel;
import clueGame.Board;
import clueGame.RoomCell;
import cluePlayer.Card;
import cluePlayer.ClueGame;
import cluePlayer.HumanPlayer;
import cluePlayer.Suggestion;

public class MakeAccusationDialog extends JDialog {
	private Map <String, Card> cards;
	private String theRoom, thePerson, theWeapon;
	ClueGame cg;
	HumanPlayer p;
	
	RoomGuessPanel room;
	PersonGuessPanel people;
	WeaponGuessPanel weapons;
	
	JButton submitButton; 
	JButton cancelButton; 
	
	public MakeAccusationDialog(Map <String, Card> cards, ClueGame cg, HumanPlayer p, Board board) {
		this.cg = cg;
		this.p = p;
		this.cards = cards;
		setLayout(new GridLayout(4,2));
		setSize(new Dimension(300,300));
		setTitle("Make an Accusation");
		
		room = new RoomGuessPanel(cards);
		people = new PersonGuessPanel(cards);
		weapons = new WeaponGuessPanel(cards);
		
		add(new JLabel("Room"));
		add(room);
		add(new JLabel("Person"));
		add(people);
		add(new JLabel("Weapon"));
		add(weapons);
		
		submitButton = new JButton("Submit");
		submitButton.addActionListener(new SubmitButtonListener());
		cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(new CancelButtonListener());
		
		add(submitButton);
		add(cancelButton);
		
		setModal(true);
		setVisible(true);
		
	}
	
	public Suggestion returnSuggestion() {
		return new Suggestion(thePerson, theWeapon, theRoom);
	}
	
	private class SubmitButtonListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			// this is called when we are ready to pull information
			theRoom = (String) room.gRoom.getSelectedItem();
			thePerson = (String) people.gPerson.getSelectedItem();
			theWeapon = (String) weapons.gWeapon.getSelectedItem();
			
			if (cg.checkAccusation(thePerson, theWeapon, theRoom)) {
				JOptionPane.showMessageDialog(null, "That is correct!!!!  You WIN!!!! OMG!!!", "Winner!", 
						JOptionPane.INFORMATION_MESSAGE);
				System.exit(0);
			} else {
				JOptionPane.showMessageDialog(null, "Wrong, your turn is over.", "Nope!", 
						JOptionPane.INFORMATION_MESSAGE);
				cg.getCurrentPlayer().setMustFinish(false);
			}
			
			setVisible(false);
			
		}
	}
	
	private class CancelButtonListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			setVisible(false);
		}
	}
}
