package ar.edu.unlp.CellularAutomaton.model;

import java.util.Enumeration;

import ar.edu.unlp.CellularAutomaton.exception.ShapeException;
import ar.edu.unlp.CellularAutomaton.util.Shape;

/**
 * Contains all the cells
 * @author mclo
 */
public class GameOfLifeGrid {
	
	private int generation;
	private int cols;
	private int rows;
	private GameOfLifeCell[][] cells;

	/**
	 * Constructor
	 * @param col matrix's column
	 * @param row matrix's row
	 */
	public GameOfLifeGrid(int cols, int rows) {
		super();
		generation = 0;
		this.cols = cols;
		this.rows = rows;
		cells = new GameOfLifeCell[cols][rows];
		
		//initialize the grid with cells
		for (int row = 0; row < rows; row++) {
			for (int col = 0; col < cols; col++) {
				cells[col][row] = new GameOfLifeCell();
			}
		}
	}

	/**
	 * @return number of columns
	 */
	public int getCols(){
		return cols;
	}
	
	/**
	 * @return number of rows
	 */
	public int getRows(){
		return rows;
	}
	
	/**
	 * @return current generation
	 */
	public int getGeneration() {
		return generation;
	}

	/**
	 * @param col matrix's column
	 * @param row matrix's row
	 * @return a gameOfLifeCell
	 */
	public GameOfLifeCell getCell(int col, int row){
		return cells[col][row];
	}

	/**
	 * Create next generator of shape
	 */
	public synchronized void nextGeneration(){
		
		//count alive neighbors
		for (int row = 0; row < rows; row++) {
			for (int col = 0; col < cols; col++) {
				countAliveNeighbors(col,row);
			}
		}

		//Transition Function
		for (int row = 0; row < rows; row++) {
			for (int col = 0; col < cols; col++) {
				cells[col][row].transitionFunction();	
			}
		}

		generation++;
	}

	/**
	 * neighbors alive in the neighborhood
	 * @param col matrix's column
	 * @param row matrix's row
	 */
	private void countAliveNeighbors(final int col, final int row){
		GameOfLifeCell cell = cells[col][row];
		cell.setAliveNeighbors((byte) 0);
		addAliveNeighbor(cell, col-1, row-1);
		addAliveNeighbor(cell, col-1, row);
		addAliveNeighbor(cell, col-1, row+1);
		addAliveNeighbor(cell, col, row-1);
		addAliveNeighbor(cell, col, row+1);
		addAliveNeighbor(cell, col+1, row-1);
		addAliveNeighbor(cell, col+1, row);
		addAliveNeighbor(cell, col+1, row+1);
	}
	
	/**
	 * Add alive neighbor to the counter of cell
	 * @param cell
	 * @param neighborCell
	 */
	private void addAliveNeighbor(GameOfLifeCell cell, int col, int row){
		try {
			cell.addAliveNeighbor(cells[col][row]);
		} catch (ArrayIndexOutOfBoundsException e) {
			//ignore border of the grid
		}
	}
	
	/**
	 * Resize grid. Reuse existing cells.
	 * 
	 * @param cols of grid
	 * @param rows of grid
	 */
	public synchronized void resize(int width, int height) {
				
		// Create a new grid, reusing existing Cell's
		GameOfLifeCell[][] NewCells = new GameOfLifeCell[width][height];
		for (int row = 0; row < height; row++) {
			for (int col = 0; col < width; col++) {
				if (col < this.cols && row < this.rows)
					NewCells[col][row] = cells[col][row];
				else
					NewCells[col][row] = new GameOfLifeCell();
			}
		}
		
		// Copy new grid
		cells = NewCells;
		this.cols = width;
		this.rows = height;
	}


	/**
	 * Reset the grid
	 */
	public synchronized void reset(){
		for (int row = 0; row < rows; row++) {
			for (int col = 0; col < cols; col++) {
				cells[col][row].setState(GameOfLifeCell.DEAD);
				cells[col][row].setAliveNeighbors((byte) 0);
			}
		}
		generation=0;
	}
	
	/**
	 * Set shape in grid
	 * @param shape name of shape
	 * @throws ShapeException if shape does not fit on grid
	 */
	public synchronized void loadShape(Shape shape) throws ShapeException {
		
		//Shape Exception
		if(cols < shape.getWidth() || rows < shape.getHeight())
			throw new ShapeException("Shape doesn't fit on grid (grid: "+cols+"x"+rows+", shape: "+shape.getWidth()+"x"+shape.getHeight()+")");
		
		reset();
		
		//center the shape
		int xOffset = (cols - shape.getWidth())/2;
		int yOffset = (rows - shape.getHeight())/2;
		
		//set shape
		Enumeration<int[]> intCells = shape.getCells();
		while (intCells.hasMoreElements()){
			int[] elem = intCells.nextElement();
	
			int row = yOffset+elem[1];
			int col = xOffset+elem[0];
			
			cells[col][row].setState(GameOfLifeCell.ALIVE);
		}
	}
	
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString(){
		return "Grid("+cols+", "+rows+")";
	}
}
