/** Name: ebreikss, Kelly Masuda, Brendan Stuart
 *  Date: Sep 27, 2013
 *  Purpose:
 */

package experiment;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

public class IntBoard {
	private  int x_length;
	private int y_length;
	private int[] index;
	private int baseOfPath;
	private Map<Integer, LinkedList<Integer>> adjMap = new HashMap<Integer, LinkedList<Integer>>();
	private Map<Integer, Boolean> visited;
	private Set<Integer> targets; 


	public IntBoard(int x, int y) {
		x_length = x;
		y_length = y;
		index = new int[x_length*y_length];		// Test on a 4x4 board

		targets = new HashSet<Integer>();

		// Initialize maps
		visited = new HashMap<Integer, Boolean>();
		for (int i = 0; i < index.length; i++){
			index[i] = i;
			visited.put(i, false);
		}
		
		calcAdjacencies();
	}

	public void calcAdjacencies() {
		for (int i : index){
			Point temp = indexToCoord(index[i]);
			int x = temp.x;
			int y = temp.y;

			LinkedList<Integer> tempAdj = new LinkedList<Integer>();

			if (y > 0) // up
				tempAdj.add(calcIndex(x, y-1));
			if (y < y_length - 1) // down
				tempAdj.add(calcIndex(x, y+1));
			if (x > 0) // left
				tempAdj.add(calcIndex(x-1, y));
			if (x < x_length - 1) // right
				tempAdj.add(calcIndex(x+1, y));

			adjMap.put(i, tempAdj);	
		}

	}

	public void startTargets(int x, int y, int steps) {
		int index = calcIndex(x,y);
		baseOfPath = index; // For the even-roll cases
		targets = new HashSet<Integer>(); // reset targets
		visited.put(index, true);
		calcTargets(index,steps);
	}

	public void calcTargets(int index, int steps) {
		// called inside startTargets()
		LinkedList<Integer> temp =  getAdjList(index);
		for (int adj : temp) {
			visited.put(adj, true);
			if (steps == 1) {
				if (adj != baseOfPath)
					targets.add(adj);
			} else {
				calcTargets(adj,steps-1);
				visited.put(adj, false);
			}

		}
	}

	public Set<Integer> getTargets() {
		// use a hashSet
		return targets;
	}

	public LinkedList<Integer> getAdjList(int index) {
		// returns the adjacency list for one cell
		return adjMap.get(index);
	}

	public int calcIndex(int x, int y) {
		return y_length*y + x;
	}

	public Point indexToCoord(int index) {
		// Helper function, does the opposite of calcIndex
		int y = (int) (index / x_length);
		int x;
		if (index < x_length)
			x = index;
		else
			x = (index - x_length) % x_length;  

		return new Point(x,y);
	}

}
