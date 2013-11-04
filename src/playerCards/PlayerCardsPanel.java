package playerCards;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import cluePlayer.Card;

public class PlayerCardsPanel extends JPanel {
	
	public PlayerCardsPanel(Map<String, Card> map) {
		
		setLayout(new GridLayout(4,1));
		
		add(new JLabel("My Cards"));
		add(new PeoplePanel(map));
		add(new RoomsPanel(map));
		add(new WeaponsPanel(map));
		
	}
	
	public class PeoplePanel extends JPanel {
		public PeoplePanel(Map<String , Card> cards) {
			setBorder(new TitledBorder (new EtchedBorder(), "People"));
			setLayout(new GridLayout(3,1));
			for (String c : cards.keySet()) 
				if (cards.get(c).getCartype().equals(Card.CardType.PERSON)) 
					add(new JTextField(c,10));
		}
	}
	
	public class RoomsPanel extends JPanel {
		public RoomsPanel(Map<String , Card> cards) {
			setBorder(new TitledBorder (new EtchedBorder(), "Rooms"));
			setLayout(new GridLayout(3,1));
			for (String c : cards.keySet()) 
				if (cards.get(c).getCartype().equals(Card.CardType.ROOM)) 
					add(new JTextField(c,10));
		}
	}
	
	public class WeaponsPanel extends JPanel {
		public WeaponsPanel(Map<String , Card> cards) {
			setBorder(new TitledBorder (new EtchedBorder(), "Weapons"));
			setLayout(new GridLayout(3,1));
			for (String c : cards.keySet()) 
				if (cards.get(c).getCartype().equals(Card.CardType.WEAPON))
					add(new JTextField(c,10));
		}
	}
}
