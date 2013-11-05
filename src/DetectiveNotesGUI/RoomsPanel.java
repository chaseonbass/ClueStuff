package DetectiveNotesGUI;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.HashMap;
import java.util.Map;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import cluePlayer.Card;
import cluePlayer.Card.CardType;
public class RoomsPanel extends JPanel{
	private Map<String, JCheckBox> jBoxes;
	public RoomsPanel(Map <String, Card> cards){
		jBoxes = new HashMap<String, JCheckBox>();
		for(String key : cards.keySet()){
			if(cards.get(key).getCartype() == CardType.ROOM){
				jBoxes.put(key, new JCheckBox(key));
			}
		}
		setBorder(new TitledBorder(new EtchedBorder(), "Rooms"));

		setLayout(new GridLayout(0, 2));
		setPreferredSize(new Dimension(150,150));
		for(String key : jBoxes.keySet()){
			add(jBoxes.get(key));
		}

	}

}
