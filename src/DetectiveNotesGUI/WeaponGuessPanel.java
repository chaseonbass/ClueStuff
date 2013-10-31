package DetectiveNotesGUI;
import java.awt.Dimension;
import java.util.Map;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import cluePlayer.Card;
import cluePlayer.Card.CardType;
public class WeaponGuessPanel extends JPanel{
	private JComboBox gWeapon;
	private Map<String, Card> cards;
	public WeaponGuessPanel(Map <String, Card> cards){
		this.cards = cards;
		setBorder(new TitledBorder(new EtchedBorder(), "Weapon Guess"));
		gWeapon= createWeaponCombo();
		add(gWeapon);

	}
	private JComboBox<String> createWeaponCombo()
	{
		JComboBox<String> combo = new JComboBox<String>();
		combo.addItem("Unsure");
		for(String key : cards.keySet()){
			if(cards.get(key).getCartype() == CardType.WEAPON){
				combo.addItem(key);
			}
		}
		return combo;
	}

}
