package clueGame;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;

/** Name: ebreikss
 *  Date: Oct 1, 2013
 *  Purpose:
 */

public abstract class BoardCell {


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BoardCell other = (BoardCell) obj;
		if (col != other.col)
			return false;
		if (row != other.row)
			return false;
		return true;
	}
	// verifies if the boardcell was clicked
	
	public boolean containsClick(int mouseX, int mouseY, Board b) {
		
		Rectangle rect = new Rectangle(col*b.getBlockSize(), row*b.getBlockSize() , b.getBlockSize(), b.getBlockSize());
		if (rect.contains(new Point(mouseX, mouseY)))
			return true;
		return false;
	}


	// These are used later for draw()
	protected int row;
	protected int col;
	protected Color cellColor, doorColor;
	public abstract void draw(Graphics g, Board b);
	public int getRow(){
		return row;
	}
	public void highlight(){  // used for highlighting targets.
		cellColor = new Color(0,100,0);
		doorColor = new Color(0,100,0);
		//System.out.println("switched to " + cellColor);
		
	}
	public void clearHighlights(){  // used to get rid of highlights
		cellColor = new Color(100,200,250);
		doorColor = new Color(100,0,250);
	}
	public int getCol(){
		return col;
	}

	public boolean isWalkway() {
		return false;
	}

	public boolean isRoom() {
		return false;
	}

	public boolean isDoorway() {
		return false;
	}
}
