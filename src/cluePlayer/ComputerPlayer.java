package cluePlayer;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import clueGame.*;
import cluePlayer.*;
import cluePlayer.Card.CardType;

public class ComputerPlayer extends Player{
	RoomCell lastVisited;
	
	public ComputerPlayer(){
		super();
	}
	
	public ComputerPlayer(String name, String color, int row, int column, Board board) {
		super(name, color, row, column, board);
		if(board.getCellAt(board.calcIndex(row, column)).isRoom()){
		lastVisited = board.getRoomCellAt(board.calcIndex(row,column));
	}
		// TODO Auto-generated constructor stub
	}
	
	public BoardCell pickLocation(Set<BoardCell> targets){
		Random rand = new Random();
		int randNum = rand.nextInt(targets.size());
		int index = 0;
		if(lastVisited == null){
			for(BoardCell ind : targets){
				if(ind.isRoom()){
					lastVisited = (RoomCell) ind;
					return ind;
				}
			}
			for(BoardCell ind : targets){
				if(index == randNum)
					return ind;
				index++;
			}
		}
		else{
			for(BoardCell ind : targets){
				if(ind.isRoom() && !ind.equals(lastVisited)){
					lastVisited = (RoomCell) ind;
					return ind;
				}
			}
			for(BoardCell ind : targets){
				if(index == randNum)
					return ind;
				index++;
			}
				
		}
		return null;
	}
	public Suggestion createSuggestion(Set<Card> seenCards, Map <String, Card> cards, Map<Character, String> rooms){
		Card rCard =new Card("Name", Card.CardType.ROOM);
		Card pCard = null;
		Card wCard = null;
		Random rand = new Random();
		int randNum = rand.nextInt(cards.size());
		
		ArrayList <Card> unseenC = new ArrayList <Card>();
		for(String c : cards.keySet()){
			unseenC.add(cards.get(c));
		}
		
		for(Card c : seenCards){
			for(int i = 0; i < unseenC.size(); i++)
				if(unseenC.get(i).getName().equals(c.getName())){
					unseenC.remove(i);
				}	
		}
		String pName=null;
		String wName=null;
		randNum = rand.nextInt(unseenC.size());
		while(pName == null || wName == null){
			if(unseenC.get(randNum).getCartype() == CardType.PERSON){
				pName = unseenC.get(randNum).getName();
			}
			if(unseenC.get(randNum).getCartype().equals(Card.CardType.WEAPON)){
				wName = unseenC.get(randNum).getName();
			}
			randNum = rand.nextInt(unseenC.size());
		}

					
		rCard = cards.get(rooms.get(lastVisited.getRoomType()));
		Suggestion guess = new Suggestion(pName, wName, rCard.getName());
		return guess;
	}
	public void makeMove(){
		BoardCell chosen = pickLocation(board.getTargets());
		setColumn(chosen.getCol());
		setRow(chosen.getRow());
		setIndex(chosen.getRow(), chosen.getCol());
		board.repaint();
		
		// space for handling suggestions..
	}
	private void setIndex(int row, int column) {
		board.calcIndex(row, column);
		
	}

	public void draw(Graphics g, Board b){
		super.draw(g, b);
	}

	
	public void updateSeen(Card seen){
		
	}

}
