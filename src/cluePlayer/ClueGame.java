
package cluePlayer;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

import javax.swing.*;

import playerCards.PlayerCardsPanel;
import ControlGUI.ClueGame_ControlGUI;
import DetectiveNotesGUI.DetectiveNotesGUI;
import clueGame.BadConfigFormatException;
import clueGame.Board;
import clueGame.BoardCell;
import clueGame.RoomCell;
import cluePlayer.Card.CardType;

public class ClueGame extends JFrame {
	private Set<Card> seenCards;
	public Map<String, Player> players;
	public Map <String, Card> cards;
	private Solution solution;
	public Board board;
	
	private ClueGame_ControlGUI controlGUI;
	private static final int EXTENTION = 230;
	private PlayerCardsPanel playerCards;
	
	//File Menu
	private DetectiveNotesGUI dnotes;
	
	private JMenu createFileMenu(){
	  JMenu menu = new JMenu("File"); 
	  menu.add(createFileNotesItem());
	  menu.add(createFileExitItem());

	  return menu;
	}
	private JMenuItem createFileExitItem(){
	  JMenuItem item = new JMenuItem("Exit");
	  class MenuItemListener implements ActionListener {
	    public void actionPerformed(ActionEvent e)
	    {
	       System.exit(0);
	    }
	  }
	  item.addActionListener(new MenuItemListener());
	  return item;
	}
	private JMenuItem createFileNotesItem(){
		  JMenuItem yournotes = new JMenuItem("Detective Notes");
		  class MenuItemListener implements ActionListener {
		    public void actionPerformed(ActionEvent e)
		    {
		       dnotes.setVisible(true);
		    }
		  }
		 yournotes.addActionListener(new MenuItemListener());
		  return yournotes;
		}
	
	// ------------- CONSTRUCTOR -------------------------------------------
	public ClueGame(String boardFile, String legendFile, String peopleFile, String weaponFile){
		// HashMaps are ordered randomly.  The first person read from the file is Batman and the human player is set as such
		
		JOptionPane.showMessageDialog(null, "You are the Joker. Press 'Next Player' to begin!", "Welcome to Clue", 
				JOptionPane.INFORMATION_MESSAGE);
		
		board = new Board(boardFile, legendFile, this);
		board.loadConfigFiles();
		board.calcAdjacencies();
		loadConfigFiles(legendFile, weaponFile, peopleFile);
		deal();
		currentPlayer = players.get("Batman");
		pickNextPlayer(currentPlayer);
		roll();
		board.calcTargets(currentPlayer.getIndex(), roll);
		board.highlightTargets();
		board.repaint();
		System.out.println(board.getTargets().size());
		((HumanPlayer)currentPlayer).makeMove();
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle ("Clue Game");
		setSize (board.getNumColumns()*board.getBlockSize()+EXTENTION+50, board.getNumRows()*board.getBlockSize()+EXTENTION);
		
		//File menu
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		menuBar.add(createFileMenu());
		dnotes = new DetectiveNotesGUI(cards);
		
		controlGUI = new ClueGame_ControlGUI(this);
		
		for (String p : players.keySet())
			if (players.get(p) instanceof HumanPlayer)
				playerCards = new PlayerCardsPanel(players.get(p).getCards());

		add(createCenterLayout(), BorderLayout.CENTER);
		add(controlGUI, BorderLayout.SOUTH);
		
		add(playerCards, BorderLayout.EAST);
		
		seenCards = new HashSet<Card>();
	}
	
	private HumanPlayer hplayer;
	private ArrayList<ComputerPlayer> cplayers;
	
	public ClueGame(){
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle ("Clue Game");
		setSize (700,200);
		
		add(createCenterLayout(), BorderLayout.CENTER);
		seenCards = new HashSet<Card>();
	}
	public Component createCenterLayout(){
		JPanel panel= new JPanel();
		panel.setLayout(new GridLayout(1,3));
		panel.add(board);
		return panel;
		
	}
	
	public void addSeenCards(Card c){
		seenCards.add(c);
	}
	public Set<Card> getSeenCards(){
		return seenCards;
	}
	
	public void deal(){  // deals cards to players
		ArrayList <Card> aCards = new ArrayList<Card>();  // created to make things easier
		ArrayList <Player> aPlayers = new ArrayList<Player>();  // makes things easier
		for(String playKey : players.keySet()){
			aPlayers.add(players.get(playKey));
		}
		for(String key : cards.keySet()){
			aCards.add(cards.get(key));
		}
		for(int i = 0; i < aCards.size(); i++){
			aPlayers.get((i)%aPlayers.size()).addCard(aCards.get(i));  
		}
		
		
	}
	public void loadRoomCards(String legend){  // reads rooms and adds to cards
		cards = new HashMap <String, Card>();
		try{
		FileReader legendReader = new FileReader(legend);
		Scanner legendIn = new Scanner(legendReader);
		int lineNumber = 0;
		
		while (legendIn.hasNextLine()) {
			lineNumber = lineNumber + 1;
			String legendLine = legendIn.nextLine();
			if (!legendLine.contains(","))
				throw new BadConfigFormatException(legend, ",", lineNumber);
			if (legendLine.indexOf(',')!=legendLine.lastIndexOf(','))
				throw new BadConfigFormatException(legend, "MULTIPLE ','", lineNumber);
			
			String[] splitLegendLine = legendLine.split(",");
			// Splits the line into two strings, the first being the initial, 
			//   the second being the name of the room   
			// Check if we actually have a character
			if (splitLegendLine[0].length() > 1) {
				throw new BadConfigFormatException(legend, splitLegendLine[0], lineNumber);
			} else {
				char tempInitial = splitLegendLine[0].toCharArray()[0];
				String tempRoomName = splitLegendLine[1].trim();
				if(!tempRoomName.equals("Closet") && !tempRoomName.equals("Walkway")){
					Card c = new Card(tempRoomName, CardType.ROOM);
					cards.put(tempRoomName, c);
				}
			}
		}
		}
		catch(FileNotFoundException e){
			System.out.println(e.getLocalizedMessage());
			e.printStackTrace();
		}
	}
	public void loadConfigFiles(String legend, String weaponFile, String peopleFile){  // loads all the files with one function
		loadRoomCards(legend);
		loadWeaponCards(weaponFile);
		loadPeopleCards(peopleFile);
	}
	
	public void loadWeaponCards(String weaponFile) {  // adds weapons to the cards
		try{
			FileReader legendReader = new FileReader(weaponFile);
			Scanner legendIn = new Scanner(legendReader);
			String weaponName;
			while(legendIn.hasNextLine()){
				weaponName = legendIn.nextLine();
				Card c = new Card(weaponName, CardType.WEAPON);
				cards.put(weaponName, c);
			}
		}
		catch(FileNotFoundException e){
			System.out.println(e.getMessage());
		}
	}
	public void loadPeopleCards(String peopleFile){  // adds each person as a card and as a player
		players = new HashMap<String, Player>();
		try{
			String name;
			String[] line;
			FileReader legendReader = new FileReader(peopleFile);
			Scanner legendIn = new Scanner(legendReader);
			int lineNumber = 0;
			while(legendIn.hasNext()){
				line = legendIn.nextLine().split(", ");
				name = line[0];
				if(lineNumber == 0){
					Player p = new HumanPlayer(line[0], line[1], Integer.parseInt(line[2]), Integer.parseInt(line[3]), board);
					players.put(line[0], p);
					hplayer = (HumanPlayer) p;
				}
				else{
					Player p = new ComputerPlayer(line[0], line[1], Integer.parseInt(line[2]), Integer.parseInt(line[3]), board);
					players.put(line[0],p);  // adds the player
				}
				Card c = new Card(line[0], CardType.PERSON);
				cards.put(line[0], c);  // adds as a card
				lineNumber ++;
			}
		}
		catch(FileNotFoundException e){
			System.out.println(e.getMessage());
		}
	}

	public void  selectAnswer(){  //empty

	}
	public Card handleSuggestion(String person, String room, String weapon, Player accusingPerson){
		Card suggestion= new Card();
		suggestion= null;
		for (Player player1 : getComputerPlayers()) // Why are we only looping through computer players???
			if (!player1.equals(accusingPerson)){
				 suggestion = player1.disproveSuggestion(person, room, weapon);
				System.out.println("this is suggestion");
				if (suggestion!=null){
					seenCards.add(suggestion);
					return suggestion;
				}
			}
		return null;
	}

	
	public boolean checkAccusation(String person, String weapon, String room){
		if (person.equals(solution.getPerson()) && weapon.equals(solution.getWeapon()) && room.equals(solution.getRoom())){
			return true;
		}
		
		else{
		return false;
		}
	}
	

	public Solution getSolution(){ 
		return solution;
	}
	public void setSolution(Solution solution){  // done for testing purposes
		this.solution= solution;
	}
	
	public void setComputerPlayer(ArrayList<ComputerPlayer> cplayers){
		this.cplayers= cplayers;
	}
	public void setHumanPlayer(HumanPlayer hplayer){
		this.hplayer= hplayer;
	}
	public ArrayList<ComputerPlayer> getComputerPlayers(){
		return cplayers;
	}
	public HumanPlayer getHumanPlayer(){
		return hplayer;
	}
	public void drawPlayers(Graphics g){
		for(String name : players.keySet()){
			g.setColor(players.get(name).convertColor(players.get(name).getColor()));
			players.get(name).draw(g, board);
			
		}
	}
	public void pickNextPlayer(Player currentPlayer){
		ArrayList <Player>plyrs = new ArrayList<Player>();
		
		for(String key : players.keySet()){
			plyrs.add(players.get(key));
		}
		for(int i = 0; i < plyrs.size(); i++){
			if(plyrs.get(i).getName().equals(currentPlayer.getName())){
				if(i == plyrs.size()-1){
					nextPlayer = plyrs.get(0);
				}
				else{
					nextPlayer = plyrs.get(i+1);
				}
			}
		}
		
	}
	// moving the players functions
		private Player currentPlayer;
		private Player nextPlayer;
		private int roll;
		
		public void roll(){
			Random rand = new Random();
			roll = rand.nextInt(6)+1;
		}
		
		public void move(){
			if(currentPlayer.getName().equals(hplayer.getName()) && currentPlayer.getMustFinish()){
				JOptionPane.showMessageDialog(null, "You must make a valid move before moving forward!", "OOPS!!", 
						JOptionPane.INFORMATION_MESSAGE);
			}
			else{
				currentPlayer.setMustFinish(true);
				currentPlayer = nextPlayer;  // this is messing things up a bit
// need to SET currentPlayer to nextPlayer not set the player currentPlayer is pointing to to nextPlayer
				System.out.println(currentPlayer.getName());
				pickNextPlayer(currentPlayer);
				roll();
				//Update control panel display
				board.calcTargets(currentPlayer.getIndex(), roll);
				// make board show targets
				if(currentPlayer.getName().equals(hplayer.getName())){
					((HumanPlayer) currentPlayer).makeMove();
				}
				else{
					// computer player pick and move to target
					((ComputerPlayer) currentPlayer).makeMove();
				}
			}
		}
		public Player getCurrentPlayer(){
			return currentPlayer;
		}
		public int getRoll(){
			return roll;
		}
	public static void main(String args[]){
		ClueGame gui= new ClueGame("BoardLayout.csv", "legend.txt", "Players.txt", "Weapons.txt");
		gui.setVisible (true);
		
	}
	
	
}

