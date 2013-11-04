package clueGame;

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
	public boolean containsClick(int mouseX, int mouseY, Board b) {
		
		Rectangle rect = new Rectangle(col*b.getBlockSize(), row*b.getBlockSize() , b.getBlockSize(), b.getBlockSize());
		if (rect.contains(new Point(mouseX, mouseY))) 
			return true;
		return false;
	}


	// These are used later for draw()
	protected int row;
	protected int col;
	public abstract void draw(Graphics g, Board b);
	public int getRow(){
		return row;
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
