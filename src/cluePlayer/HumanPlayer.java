package cluePlayer;

import java.awt.Graphics;

import clueGame.Board;

public class HumanPlayer extends Player {

	public HumanPlayer(String name, String color, int row, int column, Board board) {
		super(name, color, row, column, board);
		// TODO Auto-generated constructor stub
	}
	public void makeMove(){
		//highlight targets
		
	}
	public void draw(Graphics g, Board b){
		super.draw(g, b);
	}

}
