package DetectiveNotesGUI;
import java.awt.Dimension;
import java.util.Map;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import cluePlayer.Card;
import cluePlayer.Card.CardType;
public class RoomGuessPanel extends JPanel{
	private JComboBox gRoom;
	private Map <String, Card> cards;
	public RoomGuessPanel(Map <String, Card> cards){
		this.cards = cards;
		setBorder(new TitledBorder(new EtchedBorder(), "Room Guess"));
		gRoom = createRoomCombo();
		add(gRoom);
	}
	private JComboBox<String> createRoomCombo()
	{
		JComboBox<String> combo = new JComboBox<String>();
		combo.addItem("Unsure");
		for(String key : cards.keySet()){
			if(cards.get(key).getCartype() == CardType.ROOM){
				combo.addItem(key);
			}
		}

		return combo;
	}

}
