package DetectiveNotesGUI;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.Map;

import javax.swing.*;

import cluePlayer.Card;
import cluePlayer.Card.CardType;
public class DetectiveNotesGUI extends JDialog{
	private Map <String, Card> cards;
	
	public Component createNorthLayout(){
		JPanel panel= new JPanel();
		panel.setLayout(new GridLayout(0,2));
		PeoplePanel pPanel= new PeoplePanel(cards);
		PersonGuessPanel pgPanel= new PersonGuessPanel(cards);
		panel.add(pPanel);
		panel.add(pgPanel);
		return panel;
	}
	public Component createCenterLayout(){
		JPanel panel= new JPanel();
		panel.setLayout(new GridLayout(0,2));
		RoomsPanel rPanel= new RoomsPanel(cards);
		RoomGuessPanel rgPanel= new RoomGuessPanel(cards);
		panel.add(rPanel);
		panel.add(rgPanel);
		return panel;
	}
	public Component createSouthLayout(){
		JPanel panel= new JPanel();
		panel.setLayout(new GridLayout(0,2));
		WeaponsPanel wPanel= new WeaponsPanel(cards);
		WeaponGuessPanel wgPanel= new WeaponGuessPanel(cards);
	
		panel.add(wPanel);
		panel.add(wgPanel);
		return panel;
	}
	public DetectiveNotesGUI(Map <String, Card> cards){
		this.cards = cards;
		setSize(new Dimension(600,600));
		setTitle("Detective Notes");
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		add(createNorthLayout(), BorderLayout.NORTH);
		add(createCenterLayout(), BorderLayout.CENTER);
		add(createSouthLayout(), BorderLayout.SOUTH);
		
	}

}
