package clueGame;

import java.awt.Color;
import java.awt.Graphics;

/** Name: ebreikss
 *  Date: Oct 1, 2013
 *  Purpose:
 */

public class Walkway extends BoardCell {

	public Walkway(int row, int col) {
		this.col = col;
		this.row = row;
	}
	
	public boolean isWalkway() {
		return true;
	}
	
	public String toString(){
		return "I am a walkway\n";
	}

	public void draw(Graphics g, Board b) {
		Color c = new Color(100,200,250);
		Color l = new Color(0,0,0);
		g.setColor(c);
		g.fillRect(col*b.getBlockSize(), row*b.getBlockSize(), b.getBlockSize(), b.getBlockSize());
		g.setColor(l);
		g.drawRect(col*b.getBlockSize(), row*b.getBlockSize(), b.getBlockSize(), b.getBlockSize());
		
	}

}
