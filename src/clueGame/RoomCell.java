package clueGame;

import java.awt.Color;
import java.awt.Graphics;

/** Name: ebreikss
 *  Date: Oct 1, 2013
 *  Purpose:
 */

public class RoomCell extends BoardCell {

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		RoomCell other = (RoomCell) obj;
		if (doorDirection != other.doorDirection)
			return false;
		if (roomType != other.roomType)
			return false;
		return true;
	}

	public enum DoorDirection {
		
		RIGHT ("Right"),
		LEFT ("Left"),
		UP ("Up"),
		DOWN ("Down"),
		NONE ("None");
		
		private String value;
		
		DoorDirection (String aValue) {
			value = aValue;
		}
		
		public String toString() {
			return value;
		}
	}

	// enumerated type
	private DoorDirection doorDirection;
	private char roomType;
	
	public RoomCell() {
		// Default constructor for dealing with returning Walkways as RoomCells
		doorDirection = DoorDirection.NONE;
		roomType = 'W';
	}
	
	public RoomCell(String config, int row, int column) {
		// break up 'config' and set direction
		if (config.length() > 1){
			String dir = config.substring(1,2);
			if (dir.equals("U"))
				doorDirection = DoorDirection.UP;
			else if (dir.equals("D"))
				doorDirection = DoorDirection.DOWN;
			else if (dir.equals("R"))
				doorDirection = DoorDirection.RIGHT;
			else if (dir.equals("L"))
				doorDirection = DoorDirection.LEFT;
			else
				doorDirection = DoorDirection.NONE;
		} else {
			doorDirection = DoorDirection.NONE;
		}
		
		// Now grab first letter
		roomType = config.toCharArray()[0];
		this.row = row;
		this.col = column;
	}
	
	public RoomCell(RoomCell aRoomCell) {
		
	}
	
	public char getRoomType() {
		return roomType;
	}
	
	public boolean isRoom() {
		return true;
	}

	public DoorDirection getDoorDirection() {
		return doorDirection;
	}

	public boolean isDoorway() {
		if (doorDirection.equals(DoorDirection.NONE)){
			return false;
		}
		return true;
	}
	
	public String toString(){
		return "Room Initial: " + roomType + ". DoorDirection: " + doorDirection + "\n";
	}
	int doorWayLineThickness = 10;

	@Override
	public void draw(Graphics g, Board b) {
		Color c = new Color(100,100,250);
		if(isDoorway()){
			if(doorDirection == DoorDirection.DOWN){
				g.setColor(c);
				g.fillRect(col*b.getBlockSize(), row*b.getBlockSize()+b.getBlockSize()-doorWayLineThickness, b.getBlockSize(), doorWayLineThickness);
			}
			else if(doorDirection == DoorDirection.UP){
				g.setColor(c);
				g.fillRect(col*b.getBlockSize(), row*b.getBlockSize(), b.getBlockSize(), doorWayLineThickness);
			}
			else if(doorDirection == DoorDirection.RIGHT){
				g.setColor(c);
				g.fillRect(col*b.getBlockSize()+b.getBlockSize()-doorWayLineThickness, row*b.getBlockSize(), doorWayLineThickness, b.getBlockSize());
			}
			else if(doorDirection == DoorDirection.LEFT){
				g.setColor(c);
				g.fillRect(col*b.getBlockSize(), row*b.getBlockSize(), doorWayLineThickness, b.getBlockSize());
			}
		}
		
		// Draw the room names
		g.setColor(c);
		if(row == 3 && col == 1)
			g.drawString(b.getRooms().get(roomType), col*b.getBlockSize() ,row*b.getBlockSize());
		else if(row == 3 && col == 9)
			g.drawString(b.getRooms().get(roomType), col*b.getBlockSize() ,row*b.getBlockSize());
		else if(row == 3 && col == 15)
			g.drawString(b.getRooms().get(roomType), col*b.getBlockSize() ,row*b.getBlockSize());
		else if(row == 9 && col == 2)
			g.drawString(b.getRooms().get(roomType), col*b.getBlockSize() ,row*b.getBlockSize());
		else if(row == 9 && col == 17)
			g.drawString(b.getRooms().get(roomType), col*b.getBlockSize() ,row*b.getBlockSize());
		else if(row == 20 && col == 1)
			g.drawString(b.getRooms().get(roomType), col*b.getBlockSize() ,row*b.getBlockSize());
		else if(row == 20 && col == 11)
			g.drawString(b.getRooms().get(roomType), col*b.getBlockSize() ,row*b.getBlockSize());
		else if(row == 20 && col == 18)
			g.drawString(b.getRooms().get(roomType), col*b.getBlockSize() ,row*b.getBlockSize());
		else if(row == 16 && col == 8)
			g.drawString(b.getRooms().get(roomType), col*b.getBlockSize() ,row*b.getBlockSize());
	}


}
