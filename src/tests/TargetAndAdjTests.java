/** Name: ebreikss
 *  Date: Oct 7, 2013
 *  Purpose:
*/

package tests;

import static org.junit.Assert.*;

import java.util.LinkedList;
import java.util.Set;

import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.Board;
import clueGame.BoardCell;
import cluePlayer.ClueGame;

public class TargetAndAdjTests {

	static Board board;
	static int NUM_ROWS = 22;
	static int NUM_COLUMNS = 23;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		//System.out.println("In @BeforeClass");
		board = new Board("BreikssBoard.csv", "legend", new ClueGame());
		board.loadConfigFiles();
		
		//board.initBoard(board.getNumRows(),board.getNumColumns());
		board.calcAdjacencies();
		// startTargets() is called inside the individual tests
	}

	// -----------------------------------------------------------------

	// Adjacency Tests
		@Test
		public void testOnlyAdjWalkways() {
			LinkedList<Integer> testList = board.getAdjList(board.calcIndex(15,7));
			assertTrue(testList.contains(board.calcIndex(14, 7)));
			assertTrue(testList.contains(board.calcIndex(16, 7)));
			assertTrue(testList.contains(board.calcIndex(15, 6)));
			assertTrue(testList.contains(board.calcIndex(15, 8)));
			assertEquals(4, testList.size());
		}
		
		@Test
		public void testEdgeAdj() {
			LinkedList<Integer> testList0 = board.getAdjList(board.calcIndex(21,7));
			LinkedList<Integer> testList1 = board.getAdjList(board.calcIndex(9,19));
			// Edge of a room ought to return an empty adj list
			LinkedList<Integer> testList2 = board.getAdjList(board.calcIndex(8,22));
			LinkedList<Integer> testList3 = board.getAdjList(board.calcIndex(11,0));
			LinkedList<Integer> testList4 = board.getAdjList(board.calcIndex(0,17));
			
			assertTrue(testList0.contains(board.calcIndex(21, 6)));
			assertTrue(testList0.contains(board.calcIndex(21, 8)));
			assertTrue(testList0.contains(board.calcIndex(20, 7)));
			assertEquals(3, testList0.size());
			
			assertEquals(0, testList1.size());
			
			assertTrue(testList2.contains(board.calcIndex(8, 21)));
			assertTrue(testList2.contains(board.calcIndex(7, 22)));
			assertEquals(2, testList2.size());
			
			assertTrue(testList3.contains(board.calcIndex(10, 0)));
			assertTrue(testList3.contains(board.calcIndex(11, 1)));
			assertEquals(2, testList3.size());
			
			assertTrue(testList4.contains(board.calcIndex(0, 16)));
			assertTrue(testList4.contains(board.calcIndex(0, 18)));
			assertEquals(2, testList4.size());
		}
		
		@Test
		public void testNextToRoomAdj() {
			LinkedList<Integer> testList0 = board.getAdjList(board.calcIndex(5,7));
			LinkedList<Integer> testList1 = board.getAdjList(board.calcIndex(16,14));
			
			assertTrue(testList0.contains(board.calcIndex(4, 7)));
			assertTrue(testList0.contains(board.calcIndex(5, 8)));
			assertTrue(testList0.contains(board.calcIndex(6, 7)));
			assertEquals(3, testList0.size());
			
			assertTrue(testList1.contains(board.calcIndex(16, 15)));
			assertTrue(testList1.contains(board.calcIndex(15, 14)));
			assertEquals(2, testList1.size());
		}
		
		@Test
		public void testNextToDoorwayAdj() {
			LinkedList<Integer> testList0 = board.getAdjList(board.calcIndex(8,7));
			LinkedList<Integer> testList1 = board.getAdjList(board.calcIndex(0,15));	
			assertTrue(testList0.contains(board.calcIndex(9, 7)));
			assertTrue(testList0.contains(board.calcIndex(7, 7)));
			assertTrue(testList0.contains(board.calcIndex(8, 8)));
			assertTrue(testList0.contains(board.calcIndex(8, 6)));
			assertEquals(4, testList0.size());		
			
			assertTrue(testList1.contains(board.calcIndex(0, 14)));
			assertTrue(testList1.contains(board.calcIndex(0, 16)));
			assertTrue(testList1.contains(board.calcIndex(1, 15)));
			assertEquals(3, testList1.size());
		}
		
		@Test
		public void testAdjOfDoors() {
			LinkedList<Integer> testList0 = board.getAdjList(board.calcIndex(8,6));
			LinkedList<Integer> testList1 = board.getAdjList(board.calcIndex(3,20));
			
			assertTrue(testList0.contains(board.calcIndex(8, 7)));
			assertEquals(1, testList0.size());
			
			assertTrue(testList1.contains(board.calcIndex(3, 19)));
			
			assertEquals(1, testList1.size());
		}
		
		// -----------------------------------------------------------------
		// Target Tests
		
		// WE MIGHT NEED TO CHANGE HOW THESE TESTS WORK...?
		// Probably needs to be 'assertTrue(targets0.contains(board.getRoomCellAt(3,0)));
		@Test
		public void testWalkwayTargets() {
			board.startTargets(4, 0, 1);
			Set<BoardCell> targets0= board.getTargets();
			assertEquals(2, targets0.size());
			assertTrue(targets0.contains(board.getCellAt(3,0)));
			assertTrue(targets0.contains(board.getCellAt(4,1)));
			
			board.startTargets(15, 8, 3);
			Set<BoardCell> targets1= board.getTargets();
			assertEquals(15, targets1.size());
			assertTrue(targets1.contains(board.getCellAt(12, 8)));
			assertTrue(targets1.contains(board.getCellAt(14, 8)));
			assertTrue(targets1.contains(board.getCellAt(16, 8)));
			assertTrue(targets1.contains(board.getCellAt(18, 8)));
			assertTrue(targets1.contains(board.getCellAt(13, 7)));
			assertTrue(targets1.contains(board.getCellAt(15, 7)));
			assertTrue(targets1.contains(board.getCellAt(17, 7)));
			assertTrue(targets1.contains(board.getCellAt(14, 6)));
			assertTrue(targets1.contains(board.getCellAt(16, 6)));
			assertTrue(targets1.contains(board.getCellAt(15, 5)));
			assertTrue(targets1.contains(board.getCellAt(13, 9)));
			assertTrue(targets1.contains(board.getCellAt(15, 9)));
			assertTrue(targets1.contains(board.getCellAt(14, 10)));
			assertTrue(targets1.contains(board.getCellAt(16, 10)));
			assertTrue(targets1.contains(board.getCellAt(15, 11)));
			
			board.startTargets(17,17,2);
			Set<BoardCell> targets2= board.getTargets();
			assertEquals(5, targets2.size());
			assertTrue(targets2.contains(board.getCellAt(17, 15)));
			assertTrue(targets2.contains(board.getCellAt(16, 16)));
			assertTrue(targets2.contains(board.getCellAt(16, 18)));
			assertTrue(targets2.contains(board.getCellAt(18, 18)));
			assertTrue(targets2.contains(board.getCellAt(19, 17)));
			
			board.startTargets(7, 18, 6);
			Set<BoardCell> targets3= board.getTargets();
			assertEquals(16, targets3.size());
			assertTrue(targets3.contains(board.getCellAt(7, 12)));
			assertTrue(targets3.contains(board.getCellAt(6, 13)));
			assertTrue(targets3.contains(board.getCellAt(7, 14)));
			assertTrue(targets3.contains(board.getCellAt(9, 14)));
			assertTrue(targets3.contains(board.getCellAt(10, 15)));
			assertTrue(targets3.contains(board.getCellAt(8, 15)));
			assertTrue(targets3.contains(board.getCellAt(5, 18)));
			assertTrue(targets3.contains(board.getCellAt(3, 18)));
			assertTrue(targets3.contains(board.getCellAt(1, 18)));
			assertTrue(targets3.contains(board.getCellAt(4, 19)));
			assertTrue(targets3.contains(board.getCellAt(6, 19)));
			assertTrue(targets3.contains(board.getCellAt(8, 19)));
			assertTrue(targets3.contains(board.getCellAt(7, 20)));
			assertTrue(targets3.contains(board.getCellAt(8, 21)));
			assertTrue(targets3.contains(board.getCellAt(7, 22)));
			assertTrue(targets3.contains(board.getCellAt(3, 20))); // a room!!!
			
		}
		
		@Test
		public void testTargetsThatIncludeRooms() {
			// Just lists the doors here
			board.startTargets(14, 3, 2);
			Set<BoardCell> targets0= board.getTargets();
			assertTrue(targets0.contains(board.getRoomCellAt(14, 2)));
			
			board.startTargets(0, 13, 3);
			Set<BoardCell> targets1= board.getTargets();
			assertTrue(targets1.contains(board.getRoomCellAt(0, 12)));
			assertTrue(targets1.contains(board.getRoomCellAt(1, 15)));
			
		}
		
		@Test
		public void testTargetsFromARoom() {
			board.startTargets(20, 9, 3);
			Set<BoardCell> targets0= board.getTargets();
			//System.out.println("Targets for (20,9): ");
			//System.out.println(targets0);
			assertEquals(6, targets0.size());
			assertTrue(targets0.contains(board.getCellAt(21, 7)));
			assertTrue(targets0.contains(board.getCellAt(20, 6)));
			assertTrue(targets0.contains(board.getCellAt(19, 7)));
			assertTrue(targets0.contains(board.getCellAt(21, 9)));
			assertTrue(targets0.contains(board.getCellAt(18, 8)));
			assertTrue(targets0.contains(board.getCellAt(19, 9)));
			
			board.startTargets(15, 22, 2);
			Set<BoardCell> targets1= board.getTargets();
			assertEquals(2, targets1.size());
			assertTrue(targets1.contains(board.getCellAt(13, 22)));
			assertTrue(targets1.contains(board.getCellAt(14, 21)));
		}

}
