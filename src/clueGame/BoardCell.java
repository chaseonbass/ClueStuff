package clueGame;

import java.awt.Graphics;

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

	// These are used later for draw()
	protected int row;
	protected int col;
	public abstract void draw(Graphics g, Board b);
	
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
