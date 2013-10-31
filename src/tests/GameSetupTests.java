package tests;

import static org.junit.Assert.*;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import javax.swing.text.html.HTMLDocument.Iterator;

import org.junit.*;
import cluePlayer.*;
import clueGame.*;

public class GameSetupTests {  
	public  static ClueGame cg;
	@BeforeClass
	public static void configGame(){  // obviously the setup needed for the tests
		cg = new ClueGame("BoardLayout.csv", "legend.txt", "Players.txt", "Weapons.txt");
		cg.loadConfigFiles("legend", "Weapons.txt", "Players.txt");
		
	}
	
	
	@Test
	public void testLoadPlayers(){ 
		int expected = 6; //amount of players in file
		int actual = cg.players.size();  // gets the size of the Map that contains the players loaded
		Assert.assertEquals(expected, actual); //testing that amount of players is correct
		Player b = new Player("Batman", "Black", 6, 3, cg.board);  // creating players with appropriate creds
		Player c = new Player("Joker", "Green", 15, 1, cg.board);
		Player d = new Player("Penguin", "White", 8, 0, cg.board);
		Assert.assertTrue(cg.players.containsKey("Batman"));  //ensure Batman is in the map
		Assert.assertTrue(cg.players.containsKey("Joker"));  // ensure his friends are there too
		Assert.assertTrue(cg.players.containsKey("Penguin"));
		Assert.assertTrue(cg.players.get("Batman").getColor().equalsIgnoreCase("Black")); //Batman color
		Assert.assertTrue(cg.players.get("Joker").getColor().equalsIgnoreCase("Green")); //Joker color
		Assert.assertTrue(cg.players.get("Penguin").getColor().equalsIgnoreCase("White")); //penguin color
		Assert.assertTrue(cg.players.get("Batman").getRow() == 6);  // ensuring the start locations are correct
		Assert.assertTrue(cg.players.get("Joker").getRow() == 15);
		Assert.assertTrue(cg.players.get("Penguin").getRow() == 8);
		Assert.assertTrue(cg.players.get("Batman").getColumn() == 3);
		Assert.assertTrue(cg.players.get("Joker").getColumn() == 1);
		Assert.assertTrue(cg.players.get("Penguin").getColumn() == 0);
	}
	@Test
	public void testLoadCards(){
		int expected = 21;  // amount of cards expected
		int actual = cg.cards.size();
		Assert.assertEquals(expected, actual);  // tests that size of cards map is the same as amount of cards loaded
		int numWeapons = 0;
		int numRooms = 0;
		int numPeople = 0;
		for(String key : cg.cards.keySet()){  // gathers info for the amount of each cardType present
			if(cg.cards.get(key).getCartype() == Card.CardType.WEAPON)  
				numWeapons ++;
			else if(cg.cards.get(key).getCartype() == Card.CardType.PERSON)
				numPeople ++;
			else if(cg.cards.get(key).getCartype() == Card.CardType.ROOM)
				numRooms ++;
		}
		//Test that there are 6 weapons, 6 people, and 9 rooms
		Assert.assertTrue(numWeapons == 6); 
		Assert.assertTrue(numRooms == 9);
		Assert.assertTrue(numPeople == 6);
		boolean expecto = true;
		Card b = new Card("Batman", Card.CardType.PERSON);  
		boolean actualo = cg.cards.containsKey("Batman");
		Assert.assertTrue(cg.cards.containsKey("Batman")); //tests there is a card for a person 
		Assert.assertTrue(cg.cards.containsKey("Conservatory")); // and a room 
		Assert.assertTrue(cg.cards.containsKey("Kitten"));      // and a weapon

	}
	@Test
		//test that the cards are dealt correctly and it is equally distributed.
		//noone can have more than 1 card more or more than 1 card less than other players.
	public void testDealtCards(){
		cg.deal();
		ArrayList<Player> aPlayers = new ArrayList<Player>();  // created to iterate through a little easier
		int remaining = cg.cards.size();
		for(String key : cg.players.keySet()){
			aPlayers.add(cg.players.get(key));   // adds all players in the map to a new array list
		}										// done to compare the amount of cards the person ahead of current has
		for(int i = 0; i < aPlayers.size(); i++){  
			if(i < aPlayers.size() -1){
				Assert.assertTrue(aPlayers.get(i).getCards().size() == aPlayers.get(i+1).getCards().size() || 
						aPlayers.get(i).getCards().size() == aPlayers.get(i+1).getCards().size() + 1	||
								aPlayers.get(i).getCards().size() == aPlayers.get(i+1).getCards().size() -1);
			}
			remaining = remaining - aPlayers.get(i).getCards().size(); 
		}
		int expected = 0;
		int actual = remaining;
		Assert.assertEquals(expected, actual);
	}
	
	@Test
	public void testNoDuplicateCards(){
		cg.deal();
		Set <Card> cCards = new HashSet<Card>();  // by adding all the cards to a set we ensure that 
		for(String key : cg.players.keySet()){    // there are no duplicate cards
			for(String pKey : cg.players.get(key).getCards().keySet()){
				cCards.add(cg.players.get(key).getCards().get(pKey));
			}
		}
		Assert.assertTrue(cg.cards.size() == cCards.size());
	}
	
	@Test
	
	//test to make sure the right accusations is made.
	public void testAccusations(){
		//this set the solution
		Solution correctoMundo = new Solution ("Batman", "Kitten", "Library");
		cg.setSolution(correctoMundo);
		
		// this is an example of a correct solution which means all three fields has to match the solution
		Assert.assertTrue(cg.checkAccusation("Batman", "Kitten", "Library"));
		
		// these are example of the ones that not suppose to be the solution
	
		//wrong person
		Assert.assertFalse(cg.checkAccusation("Joker", "Kitten", "Library"));
		//wrong weapon
		Assert.assertFalse(cg.checkAccusation("Batman", "Batarang", "Library"));
		//wrong weapon, wrong room
		Assert.assertFalse(cg.checkAccusation("Batman", "Batarang", "Conservatory"));
		
		
		

	}


}
