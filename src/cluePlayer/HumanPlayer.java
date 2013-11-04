package cluePlayer;

import java.awt.Graphics;
import java.util.Set;

import clueGame.Board;
import clueGame.BoardCell;

public class HumanPlayer extends Player {

	public HumanPlayer(String name, String color, int row, int column, Board board) {
		super(name, color, row, column, board);
		// TODO Auto-generated constructor stub
	}
	public void makeMove(){
		//highlight targets
		
		//allow human to make suggestions
	}
	public void draw(Graphics g, Board b){
		super.draw(g, b);
	}

}
