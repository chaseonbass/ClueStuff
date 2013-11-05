/** Name: ebreikss
 *  Date: Sep 27, 2013
 *  Purpose:
*/

package tests;

import static org.junit.Assert.*;

import java.util.LinkedList;
import java.util.Set;

import org.junit.BeforeClass;
import org.junit.Test;

import experiment.IntBoard;

public class IntBoardTests {
	static IntBoard board;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		System.out.println("In @BeforeClass");
		board = new IntBoard(4,4);
		
	}
	
	@Test
	public void testCalcIndex() {
		
		assertEquals(15,board.calcIndex(3,3));
		assertEquals(10,board.calcIndex(2,2));
		assertEquals(13,board.calcIndex(1,3));
		assertEquals(0,board.calcIndex(0,0));
		assertEquals(2,board.calcIndex(2,0));
	}
	
	//-----------------------------------------------------------
	
	@Test
	public void testAdjacency0(){
		LinkedList<Integer> testList = board.getAdjList(0);
		assertTrue(testList.contains(4));
		assertTrue(testList.contains(1));
		assertEquals(2, testList.size());
	}
	
	@Test
	public void testAdjacency3(){
		LinkedList<Integer> testList = board.getAdjList(3);
		assertTrue(testList.contains(2));
		assertTrue(testList.contains(7));
		assertEquals(2, testList.size());
	}
	
	@Test
	public void testAdjacency15(){
		LinkedList<Integer> testList = board.getAdjList(15);
		assertTrue(testList.contains(14));
		assertTrue(testList.contains(11));
		assertEquals(2, testList.size());
	}
	
	@Test
	public void testAdjacency7(){
		LinkedList<Integer> testList = board.getAdjList(7);
		assertTrue(testList.contains(3));
		assertTrue(testList.contains(11));
		assertTrue(testList.contains(6));
		assertEquals(3, testList.size());
	}
	
	@Test
	public void testAdjacency8(){
		LinkedList<Integer> testList = board.getAdjList(8);
		assertTrue(testList.contains(4));
		assertTrue(testList.contains(9));
		assertTrue(testList.contains(12));
		assertEquals(3, testList.size());
	}
	
	@Test
	public void testAdjacency10(){
		LinkedList<Integer> testList = board.getAdjList(10);
		assertTrue(testList.contains(14));
		assertTrue(testList.contains(11));
		assertTrue(testList.contains(9));
		assertTrue(testList.contains(6));
		assertEquals(4, testList.size());
	}
	
	@Test
	public void testAdjacency5(){
		LinkedList<Integer> testList = board.getAdjList(5);
		assertTrue(testList.contains(4));
		assertTrue(testList.contains(1));
		assertTrue(testList.contains(9));
		assertTrue(testList.contains(6));
		assertEquals(4, testList.size());
	}
	
	//-------------------------------------------------------------
	
	@Test
	public void testTargets0_0_3(){
		board.startTargets(0, 0, 3);
		Set<Integer> targets= board.getTargets();
		//System.out.println("0: " + targets);
		assertEquals(6, targets.size());
		assertTrue(targets.contains(12));
		assertTrue(targets.contains(9));
		assertTrue(targets.contains(1));
		assertTrue(targets.contains(6));
		assertTrue(targets.contains(3));
		assertTrue(targets.contains(4));
	}
	
	@Test
	public void testTargets3_3_4(){
		board.startTargets(3, 3, 4);
		Set<Integer> targets= board.getTargets();
		//System.out.println("15: " + targets);
		assertEquals(6, targets.size());
		assertTrue(targets.contains(2));
		assertTrue(targets.contains(5));
		assertTrue(targets.contains(8));
		assertTrue(targets.contains(7));
		assertTrue(targets.contains(10));
		assertTrue(targets.contains(13));
	}
	
	@Test
	public void testTargets3_1_2(){
		board.startTargets(3, 1, 2);
		Set<Integer> targets= board.getTargets();
		//System.out.println("7: " +targets);
		assertEquals(4, targets.size());
		assertTrue(targets.contains(2));
		assertTrue(targets.contains(5));
		assertTrue(targets.contains(10));
		assertTrue(targets.contains(15));
	}
	
	@Test
	public void testTargets0_2_1(){
		board.startTargets(0, 2, 1);
		Set<Integer> targets= board.getTargets();
		//System.out.println("8: " +targets);
		assertEquals(3, targets.size());
		assertTrue(targets.contains(12));
		assertTrue(targets.contains(9));
		assertTrue(targets.contains(4));
	}
	
	@Test
	public void testTargets1_1_3(){
		board.startTargets(1, 1, 3);
		Set<Integer> targets= board.getTargets();
		//System.out.println("5: " +targets);
		assertEquals(8, targets.size());
		assertTrue(targets.contains(1));
		assertTrue(targets.contains(3));
		assertTrue(targets.contains(4));
		assertTrue(targets.contains(6));
		assertTrue(targets.contains(9));
		assertTrue(targets.contains(11));
		assertTrue(targets.contains(12));
		assertTrue(targets.contains(14));
	}
	
	@Test
	public void testTargets2_2_6(){
		board.startTargets(2, 2, 6);
		Set<Integer> targets= board.getTargets();
		//System.out.println("10: " +targets);
		assertEquals(7, targets.size());
		assertTrue(targets.contains(0));
		assertTrue(targets.contains(2));
		assertTrue(targets.contains(5));
		assertTrue(targets.contains(7));
		assertTrue(targets.contains(8));
		assertTrue(targets.contains(13));
		assertTrue(targets.contains(15));
	}
	
	@Test
	public void testTargets2_2_5(){
		board.startTargets(2, 2, 5);
		Set<Integer> targets= board.getTargets();
		//System.out.println("10: " +targets);
		assertEquals(8, targets.size());
		assertTrue(targets.contains(1));
		assertTrue(targets.contains(3));
		assertTrue(targets.contains(4));
		assertTrue(targets.contains(6));
		assertTrue(targets.contains(9));
		assertTrue(targets.contains(11));
		assertTrue(targets.contains(12));
		assertTrue(targets.contains(14));
	}

}
