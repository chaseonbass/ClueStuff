package DetectiveNotesGUI;
import java.awt.Dimension;
import java.util.Map;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import cluePlayer.Card;
import cluePlayer.Card.CardType;


public class PersonGuessPanel extends JPanel{
	private JComboBox gPerson;
	private Map<String, Card> cards;
	public PersonGuessPanel(Map <String, Card> cards){
		this.cards = cards;
		setBorder(new TitledBorder(new EtchedBorder(), "Person Guess"));
		gPerson= createPersonCombo();
		add(gPerson);

	}
	private JComboBox createPersonCombo()
	{
		JComboBox<String> combo = new JComboBox<String>();
		combo.addItem("Unsure");
		for(String key : cards.keySet()){
			if(cards.get(key).getCartype() == CardType.PERSON){
				combo.addItem(key);
			}
		}
		return combo;

	}

}
