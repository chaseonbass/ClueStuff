package makeDialogs;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

import javax.swing.*;

import DetectiveNotesGUI.PeoplePanel;
import DetectiveNotesGUI.PersonGuessPanel;
import DetectiveNotesGUI.WeaponGuessPanel;
import DetectiveNotesGUI.WeaponsPanel;
import clueGame.Board;
import clueGame.RoomCell;
import cluePlayer.Card;
import cluePlayer.ClueGame;
import cluePlayer.HumanPlayer;
import cluePlayer.Suggestion;

public class MakeGuessDialog extends JDialog {
	private Map <String, Card> cards;
	private String theRoom, thePerson, theWeapon;
	ClueGame cg;
	HumanPlayer p;

	PersonGuessPanel people;
	WeaponGuessPanel weapons;

	JButton submitButton; 
	JButton cancelButton; 

	public MakeGuessDialog(Map <String, Card> cards, ClueGame cg, HumanPlayer p, Board board) {
		this.cg = cg;
		this.p = p;
		this.cards = cards;
		setLayout(new GridLayout(4,2));
		setSize(new Dimension(300,300));
		setTitle("Make a Guess");

		char tempRoomChar = ((RoomCell) board.getCells().get(p.getIndex())).getRoomType();

		JTextField room = new JTextField( board.getRooms().get(tempRoomChar) );
		room.setEditable(false);
		theRoom = room.getText();

		people = new PersonGuessPanel(cards);
		weapons = new WeaponGuessPanel(cards);

		add(new JLabel("Your room"));
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

			if (((String) people.gPerson.getSelectedItem()).equals("Unsure") || ((String) weapons.gWeapon.getSelectedItem()).equals("Unsure"))
				JOptionPane.showMessageDialog(null, "'Unsure' is, like, totaly uncool... Try again.", "Invalid Selection", 
						JOptionPane.INFORMATION_MESSAGE);
			else {
				// this is called when we are ready to pull information
				thePerson = (String) people.gPerson.getSelectedItem();
				theWeapon = (String) weapons.gWeapon.getSelectedItem();
				
				setVisible(false);
			}

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
