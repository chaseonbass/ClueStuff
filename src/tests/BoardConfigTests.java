/** Name: ebreikss
 *  Date: Oct 2, 2013
 *  Purpose:
*/

package tests;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.BadConfigFormatException;
import clueGame.Board;
import clueGame.BoardCell;
import clueGame.RoomCell;
import experiment.IntBoard;
import clueGame.DoorDirection;
import cluePlayer.ClueGame;

public class BoardConfigTests {

	static Board board;
	static int NUM_ROWS = 22;
	static int NUM_COLUMNS = 23;
	static int NUM_ROOMS = 11;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		System.out.println("In @BeforeClass");
		board = new Board("boardLayout.csv", "legend", new ClueGame());
		board.loadConfigFiles();
		
	}

	// -----------------------------------------------------------------
	
	@Test
	public void testCorrectNumRooms() {
		// 9 rooms + 1 walkway + 1 closet = 11
		assertEquals(NUM_ROOMS,board.getRooms().size());
		
	}
	
	@Test
	public void testRoomMapping() {
		
		Map<Character, String> temp = board.getRooms();
		
		assertEquals(temp.get('X'),"Closet");
		assertEquals(temp.get('W'),"Walkway");
		assertEquals(temp.get('L'),"Library");
		assertEquals(temp.get('D'),"Dining Room");
		assertEquals(temp.get('S'),"Study");
		assertEquals(temp.get('B'),"Ballroom");
		assertEquals(temp.get('Q'),"Quidditch Room");
		assertEquals(temp.get('O'),"Lounge");
		assertEquals(temp.get('C'),"Conservatory");
		assertEquals(temp.get('R'),"Billiard Room");
		assertEquals(temp.get('K'),"Kitchen");
		
	}
	
	// -----------------------------------------------------------------
	
	@Test
	public void testDimensions() {
		assertEquals(NUM_COLUMNS,board.getNumColumns());
		assertEquals(NUM_ROWS,board.getNumRows());
	}
	
	@Test
	public void testDoorNumber() {
		int sum = 0;
		
		for (BoardCell b : board.getCells()) {
			if (b.isDoorway())
				sum++;
		}
		
		// System.out.println(sum);
		assertEquals(19,sum);
	}
	
	@Test
	public void testDoorDirection() {
		int sumUp = 0;
		int sumDown = 0;
		int sumLeft = 0;
		int sumRight = 0;
		int sumNone = 0;
		
		for (BoardCell b : board.getCells()) {
			if (b.isRoom()){
				//b = new RoomCell(b);
				RoomCell.DoorDirection d = ((RoomCell) b).getDoorDirection();
				// System.out.println(d);
				if (d.equals(RoomCell.DoorDirection.UP))
					sumUp++;
				else if (d.equals(RoomCell.DoorDirection.DOWN))
					sumDown++;
				else if (d.equals(RoomCell.DoorDirection.LEFT))
					sumLeft++;
				else if (d.equals(RoomCell.DoorDirection.RIGHT))
					sumRight++;
				else
					sumNone++; // None includes both rooms and walkways
			}
			else if (b.isWalkway()){
				sumNone++; // None includes both rooms and walkways
			}
		}
		
		assertEquals(3,sumUp);
		assertEquals(2,sumDown);
		assertEquals(4,sumLeft);
		assertEquals(10,sumRight);
		assertEquals(487,sumNone);
	}
	
	@Test
	public void testRoomInitial() {
		
		assertEquals('S',board.getRoomCellAt(1,1).getRoomType());
		assertEquals('K',board.getRoomCellAt(16,0).getRoomType());
		assertEquals('B',board.getRoomCellAt(9,1).getRoomType());
		assert(board.getRoomCellAt(3,2).isWalkway());
		assert(board.getRoomCellAt(12,3).isWalkway());
		assertEquals('K',board.getRoomCellAt(20,3).getRoomType());
		assertEquals('B',board.getRoomCellAt(6,4).getRoomType());
		assertEquals('B',board.getRoomCellAt(12,6).getRoomType());
		assertEquals('D',board.getRoomCellAt(3,9).getRoomType());
		assertEquals('X',board.getRoomCellAt(9,9).getRoomType());
		assert(board.getRoomCellAt(5,11).isWalkway());
		assertEquals('X',board.getRoomCellAt(12,11).getRoomType());
		assertEquals('C',board.getRoomCellAt(17,11).getRoomType());
		assertEquals('L',board.getRoomCellAt(1,15).getRoomType());
		assertEquals('C',board.getRoomCellAt(19,15).getRoomType());
		assertEquals('Q',board.getRoomCellAt(10,16).getRoomType());
		assertEquals('C',board.getRoomCellAt(20,16).getRoomType());
		assertEquals('Q',board.getRoomCellAt(13,17).getRoomType());
		assertEquals('O',board.getRoomCellAt(1,20).getRoomType());
		assertEquals('R',board.getRoomCellAt(19,20).getRoomType());
		assert(board.getRoomCellAt(13,21).isWalkway());
		
		System.out.println(board.getRoomCellAt(board.getCells().size()-1));
		
		System.out.println(board.getRoomCellAt(505).getRoomType());
//		System.out.println(board.getRoomCellAt(board.calcIndex(21, 22)));
//		System.out.println(board.calcIndex(21,22));
//		System.out.println(board.getCells().size() + "  subtract one");
		
		//System.out.println(board.calcIndex(10,22));
		///System.out.println(board.getCells().size());
		//System.out.println(board.getRoomCellAt(10,22));
		assertEquals('R',board.getRoomCellAt(21,22).getRoomType()); //endpoint
		
		
		//System.out.println(board.getRoomCellAt(22,22));
		
		assertNull(board.getRoomCellAt(22,22));
		assertNull(board.getRoomCellAt(12,23));
		
	}
	
	@Test
	public void testCalcIndex() {
		
		assertEquals(0,board.calcIndex(0,0));
		assertEquals(505,board.calcIndex(21,22));
		assertEquals(197,board.calcIndex(8,13));
		assertEquals(5,board.calcIndex(0,5));
		assertEquals(46,board.calcIndex(2,0));
		
	}
	
	//------------------------------------------------------------------
	@Test (expected = BadConfigFormatException.class)
	public void testBadColumns() throws BadConfigFormatException, FileNotFoundException {
		System.out.println("In @testBadColumns");
		Board b = new Board("OurClueLayoutBadColumns.csv", "legend", new ClueGame());
		b.loadConfigFiles();
	}
	
	@Test (expected = BadConfigFormatException.class)
	public void testBadRooms() throws BadConfigFormatException, FileNotFoundException {
		System.out.println("In @testBadRooms");
		Board b = new Board("OurClueLayoutBadRoom.csv", "legend", new ClueGame());
		b.loadConfigFiles();	
	}
	
	@Test (expected = BadConfigFormatException.class)
	public void testBadRoomFormat() throws BadConfigFormatException, FileNotFoundException {
		System.out.println("In @testBadRoomFormat");
		Board b = new Board("boardLayout.csv", "OurClueLegendBadFormat", new ClueGame());
		b.loadConfigFiles();
	}
	
	
	
	// -----------------------------------------------------------------
    

}
