package clueGame;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import javax.swing.*;

import cluePlayer.*;


/** Name: ebreikss
 *  Date: Oct 1, 2013
 *  Purpose:
 */

public class Board extends JPanel implements MouseListener {

	private ArrayList<BoardCell> cells; // Not yet implemented
	private int numRows;
	private int numColumns;
	private String layout, legend;
	private Map<Character, String> rooms; // from legend
	private static final int BLOCKSIZE = 20;

	// ------------------------------------------------------------------

	private int[] index;
	private int baseOfPath;
	private Map<Integer, LinkedList<Integer>> adjMap = new HashMap<Integer, LinkedList<Integer>>();
	private Map<Integer, Boolean> visited;
	// private Set<Integer> targets;
	private Set<BoardCell> targets; // Suggested
	private ArrayList<ComputerPlayer> cplayers;
	private HumanPlayer hplayer;
	private ClueGame game;

	// ------------------------------------------------------------------

	public Board() {
		super();
		// This contains stuff to deal with C.R.'s tests for adjacencies.  Likely needs to be commented out
		layout = "ClueLayout.csv";
		legend = "ClueLegend.txt";
	}
	protected void paintComponent(Graphics g){
		//loadConfigFiles();
		//System.out.println(cells.size());
		super.paintComponent(g);
		for(int i = 0; i < cells.size(); i++){
			cells.get(i).draw(g, this);
		}
		game.drawPlayers(g);
	}

	public Board(String layout, String legend, ClueGame game) {  
		super();
		this.layout = layout;
		this.legend = legend;
		this.game = game;
		addMouseListener(this);

	}

	public void loadRoomConfig() throws BadConfigFormatException {
		rooms = new HashMap<Character, String>();
		cells = new ArrayList<BoardCell>();
		try{
			FileReader legendReader = new FileReader(legend);
			Scanner legendIn = new Scanner(legendReader);
			int lineNumber = 0;

			while (legendIn.hasNextLine()) {
				lineNumber = lineNumber + 1;
				String legendLine = legendIn.nextLine();
				if (!legendLine.contains(","))
					throw new BadConfigFormatException(legend, ",", lineNumber);
				if (legendLine.indexOf(',')!=legendLine.lastIndexOf(','))
					throw new BadConfigFormatException(legend, "MULTIPLE ','", lineNumber);

				String[] splitLegendLine = legendLine.split(",");
				// Splits the line into two strings, the first being the initial, 
				//   the second being the name of the room   
				// Check if we actually have a character
				if (splitLegendLine[0].length() > 1) {
					throw new BadConfigFormatException(legend, splitLegendLine[0], lineNumber);
				} else {
					char tempInitial = splitLegendLine[0].toCharArray()[0];
					String tempRoomName = splitLegendLine[1].trim();
					rooms.put(tempInitial, tempRoomName);
				}
			}
		}
		catch(FileNotFoundException e){
			System.out.println(e.getLocalizedMessage());
			e.printStackTrace();
		}

	}

	public void loadBoardConfig() throws BadConfigFormatException {
		try{
			FileReader layoutReader = new FileReader(layout);
			Scanner layoutIn = new Scanner(layoutReader);

			// Store our cvs file into an ArrayList
			ArrayList<String[]> tempList = new ArrayList<String[]>();
			int colNum = 0;

			while (layoutIn.hasNextLine()) {
				// Create an array of string arrays
				String next = layoutIn.nextLine();
				// System.out.println(next);
				tempList.add(next.split(","));
			}

			// Check we have proper dimensionality and contents
			int testLength = tempList.get(0).length;
			for (int i = 0; i < tempList.size(); i++) {
				int tempLength = tempList.get(i).length;
				if (tempLength != testLength) {
					throw new BadConfigFormatException(layout, i);
				} else {
					for (String RoomInitial : tempList.get(i)) {
						if (RoomInitial.length() > 2) {
							throw new BadConfigFormatException(layout, RoomInitial, i);
						} else if (!rooms.containsKey(RoomInitial.charAt(0))) {
							throw new BadConfigFormatException(layout, RoomInitial, i);
						} else if (RoomInitial.length() > 1 && 
								(RoomInitial.charAt(1) != 'U' && RoomInitial.charAt(1) != 'D' && RoomInitial.charAt(1) != 'R' && RoomInitial.charAt(1) != 'L' && RoomInitial.charAt(1) != 'N')) {
							throw new BadConfigFormatException(layout, RoomInitial, i);
						} else {
							if (RoomInitial.equals("W"))
								cells.add(new Walkway(i, colNum));
							else {
								cells.add(new RoomCell(RoomInitial, i, colNum));
							}
						}
						colNum ++;
					}

				}
				colNum = 0;
			}

			numColumns = testLength;
			numRows = tempList.size();
		}
		catch(FileNotFoundException e){
			System.out.println(e.getLocalizedMessage());
			e.printStackTrace();
		}
	}

	// --------------------------------------------------------------------

	public void loadConfigFiles() throws BadConfigFormatException {
		loadRoomConfig();
		loadBoardConfig();
	}

	// --------------------------------------------------------------------

	public int calcIndex(int rowNum, int columnNum) {
		return numColumns*rowNum + columnNum;
	}

	public RoomCell getRoomCellAt(int rowNum, int columnNum) {
		// probably uses calcIndex
		if (rowNum < 0|| columnNum < 0 || rowNum >= numRows || columnNum >= numColumns){
			return null;
		} else if (cells.get(calcIndex(rowNum, columnNum)) instanceof Walkway) {
			return new RoomCell();
		}

		if (cells.get(calcIndex(rowNum, columnNum)) instanceof RoomCell)
			return (RoomCell) cells.get(calcIndex(rowNum, columnNum));
		return new RoomCell("XN", rowNum, columnNum);
	}

	//overloaded getRoomCellAt
	public RoomCell getRoomCellAt(int index){
		if(index < 0 || index > calcIndex(numRows-1,numColumns-1)){
			return null;
		}  else if (cells.get(index) instanceof Walkway) {
			return new RoomCell();
		}

		if (cells.get(index) instanceof RoomCell)
			return (RoomCell) cells.get(index);
		return new RoomCell("XN", index/numColumns, index%numColumns);
	}

	// Alternate getCell function, cuz the one above is annoying!

	public BoardCell getCellAt(int rowNum, int columnNum) {
		if (rowNum < 0|| columnNum < 0 || rowNum >= numRows || columnNum >= numColumns){
			return null;
		} else {
			return cells.get(calcIndex(rowNum, columnNum));
		}
	}

	public BoardCell getCellAt(int index) {
		if (index < 0 || index > calcIndex(numRows-1,numColumns-1)){
			return null;
		} else {
			return cells.get(index);
		}
	}

	// -----------------------------------------------------------------
	// ADJ AND TARGET FUNCTIONS

	public void initBoard(int rows, int cols) {
		// likely calls everything else
		index = new int[numRows*numColumns];
		targets = new HashSet<BoardCell>(); // Reset targets

		// Initialize maps
		visited = new HashMap<Integer, Boolean>();
		for (int i = 0; i < index.length; i++){
			index[i] = i;
			visited.put(i, false);
		}
		//calcAdjacencies();
	}

	public boolean isWalkable(int i){
		// helper function
		if (getCells().get(i) instanceof RoomCell){
			if (getCells().get(i).isDoorway())
				return true;
		}
		else if (getCells().get(i) instanceof Walkway){
			return true;
		}
		return false;
	}

	public boolean bothSameRoomDoorways(int doorA, int doorB){
		// helper function just for adjacencies
		BoardCell a = getCells().get(doorA);
		BoardCell b = getCells().get(doorB);
		if (a instanceof RoomCell && b instanceof RoomCell)
			if (a.isDoorway() && b.isDoorway()){
				if (((RoomCell) a).getRoomType() == ((RoomCell) b).getRoomType())
					return true;
			}
		return false;
	}

	public void calcAdjacencies(){
		initBoard(numRows,numColumns);

		for (int i = 0; i < index.length; i++) {
			Point temp = indexToCoord(index[i]);
			// NOTE: I fixed this function to return (r,c) instead of (c,r)
			int row = temp.x;
			int column = temp.y;
			LinkedList<Integer> tempAdj = new LinkedList<Integer>();

			// The if statements below still need to check for walls and doors
			//sorta checked, helper function right above called isWalkable(int index)

			//if you can walk on it (Walkway OR Room door)
			//check direction

			// Check if in a Door
			if (cells.get(i).isDoorway()) {
				
				if (row > 0)
					if (isWalkable(calcIndex(row-1, column)) && ((RoomCell) cells.get(i)).getDoorDirection().equals(RoomCell.DoorDirection.UP))
						tempAdj.add(calcIndex(row-1, column));
				if (row < numRows - 1)
					if (isWalkable(calcIndex(row+1, column)) && ((RoomCell) cells.get(i)).getDoorDirection().equals(RoomCell.DoorDirection.DOWN)) 
						tempAdj.add(calcIndex(row+1, column));
				if (column > 0)
					if (isWalkable(calcIndex(row, column-1)) && ((RoomCell) cells.get(i)).getDoorDirection().equals(RoomCell.DoorDirection.LEFT))
						tempAdj.add(calcIndex(row, column-1));
				if (column < numColumns - 1) 	
					if (isWalkable(calcIndex(row, column+1)) && ((RoomCell) cells.get(i)).getDoorDirection().equals(RoomCell.DoorDirection.RIGHT))
						tempAdj.add(calcIndex(row, column+1));
					

			} else if (isWalkable(i)) { // everything else
				if (column > 0) { // left
					if (isWalkable(calcIndex(row, column - 1)) && !bothSameRoomDoorways(i, calcIndex(row, column - 1)))
						if (cells.get(calcIndex(row, column - 1)) instanceof Walkway){
							tempAdj.add(calcIndex(row, column - 1)); 
						}
						else if (((RoomCell) cells.get(calcIndex(row, column - 1))).getDoorDirection() == RoomCell.DoorDirection.RIGHT)
							tempAdj.add(calcIndex(row, column - 1)); 
				}
				if (column < numColumns - 1) { // right
					if (isWalkable(calcIndex(row, column + 1)) && !bothSameRoomDoorways(i, calcIndex(row, column + 1)))
						if (cells.get(calcIndex(row, column + 1)) instanceof Walkway){
							tempAdj.add(calcIndex(row, column + 1)); 
						}
						else if (((RoomCell) cells.get(calcIndex(row, column + 1))).getDoorDirection() == RoomCell.DoorDirection.LEFT)
							tempAdj.add(calcIndex(row, column + 1)); 
				}
				if (row > 0) { // up
					if (isWalkable(calcIndex(row - 1, column)) && !bothSameRoomDoorways(i, calcIndex(row - 1, column)))
						if (cells.get(calcIndex(row - 1, column)) instanceof Walkway){
							tempAdj.add(calcIndex(row - 1, column)); 
						}
						else if (((RoomCell) cells.get(calcIndex(row - 1, column))).getDoorDirection() == RoomCell.DoorDirection.DOWN)
							tempAdj.add(calcIndex(row - 1, column)); 
				}
				if (row < numRows - 1) { // down
					if (isWalkable(calcIndex(row + 1, column)) && !bothSameRoomDoorways(i, calcIndex(row + 1, column)))
						if (cells.get(calcIndex(row + 1, column)) instanceof Walkway){
							tempAdj.add(calcIndex(row + 1, column)); 
						}
						else if (((RoomCell) cells.get(calcIndex(row + 1, column))).getDoorDirection() == RoomCell.DoorDirection.UP)
							tempAdj.add(calcIndex(row + 1, column)); 
				}
			}

			adjMap.put(i, tempAdj);	
		}
	}

	public void startTargets(int row, int col, int steps){
		int index = calcIndex(row,col);
		baseOfPath = index; // For the even-roll cases
		targets = new HashSet<BoardCell>(); // reset targets
		visited.put(index, true);
		calcTargets(index,steps);
	}

	public void calcTargets(int index, int steps){
		// called inside startTargets()
		LinkedList<Integer> temp = new LinkedList<Integer>();

		temp = adjMap.get(index);  // tempo is a LinkedList<Integer>	
		temp = (LinkedList<Integer>) temp.clone();

		for (int adj : temp) {
			if(visited.get(adj)){
				visited.put(adj, false);
			} else {
				visited.put(adj, true);
				if (steps == 1 || cells.get(adj).isDoorway()) {
					if (adj != baseOfPath)
						targets.add(cells.get(adj)); 
				} else {
					calcTargets(adj,steps-1);
					visited.put(adj, false);
				}
				visited.put(adj, false);
			}
		}
	}

	public Set<BoardCell> getTargets(){
		// returns a hashSet
		return targets;
	}

	public LinkedList<Integer> getAdjList(int index){
		// returns the adjacency list for one cell
		return adjMap.get(index);
	}
	public void highlightTargets(){
		ArrayList <BoardCell> targs = new ArrayList<BoardCell>();
		targs.addAll(targets);
		for(int i = 0; i < targs.size(); i++){
			targs.get(i).highlight();
		}
		repaint();
	}

	public void unHighlightTargets(){
		//ArrayList <BoardCell> targs = new ArrayList<BoardCell>();
		//targs.addAll(targets);
		for(int i = 0; i < cells.size(); i++){
			cells.get(i).clearHighlights();
		}
		repaint();
	}

	// Helper function
	public Point indexToCoord(int index) {
		// opposite of calcIndex()
		// Helper function, does the opposite of calcIndex
		int y = (int) (index / numColumns);
		int x;
		if (index < numColumns)
			x = index;
		else
			x = (index - numColumns) % numColumns;  

		return new Point(y,x);
	}


	// -----------------------------------------------------------------
	// GETTERS 
	public ArrayList<BoardCell> getCells() {
		return cells;
	}

	public int getNumRows() {
		return numRows;
	}

	public int getNumColumns() {
		return numColumns;
	}

	public Map<Character, String> getRooms() {
		return rooms;
	}
	public int getBlockSize(){
		return BLOCKSIZE;
	}

	// -----------------------------------------------------------------
	// Mouse Listener-------------------------------

	@Override
	public void mouseClicked(MouseEvent e) {
		if(game.getCurrentPlayer().getName().equals(game.getHumanPlayer().getName()) 
				&& game.getCurrentPlayer().getMustFinish()){
			
			boolean valid = false;
			boolean suggest = false;
			ArrayList <BoardCell> targs = new ArrayList <BoardCell>();
			targs.addAll(targets);
			BoardCell clicked = new Walkway(0,0);
			for(int i = 0; i < targs.size(); i++){
				if(targs.get(i).containsClick(e.getX(), e.getY(), game.board)){
					valid = true;
					clicked = targs.get(i);
					if (clicked instanceof RoomCell)
						suggest = true;
					System.out.println(suggest); // I don't think I understand how Modality works...
				}
			}
			if(valid){
				
				if (suggest) {
					game.makeDialog();
					suggest = false;
				}
				
				unHighlightTargets();
				game.getCurrentPlayer().setColumn(clicked.getCol());
				game.getCurrentPlayer().setRow(clicked.getRow());
				game.getCurrentPlayer().setMustFinish(false);
				repaint();
				valid = false;
			}
			else{
				JOptionPane.showMessageDialog(null, "That is an invalid choice, try again.", "OOPS!!", 
						JOptionPane.INFORMATION_MESSAGE);
			}
		}

	}
	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}
	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}
	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}
	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}
}
