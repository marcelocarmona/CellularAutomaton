package ar.edu.unlp.CellularAutomaton.model;

import java.util.Enumeration;

import ar.edu.unlp.CellularAutomaton.exception.ShapeException;
import ar.edu.unlp.CellularAutomaton.util.Shape;

/**
 * Contains all the cells
 * @author mclo
 */
public class GameOfLifeGrid implements GameGrid {

	private int generation;
	private int cols;
	private int rows;
	private GameOfLifeCell[][] cells;
	/**
	 * used to define the neighborhood 
	 * @see #countAliveNeighbors()
	 */
	private Rule rule;

	/**
	 * Constructor
	 * @param col matrix's column
	 * @param row matrix's row
	 */
	public GameOfLifeGrid(int cols, int rows,Rule rule) {
		super();
		this.generation = 0;
		this.cols = cols;
		this.rows = rows;
		this.rule = rule;
		cells = new GameOfLifeCell[cols][rows];
		
		
		//initialize the grid with cells
		for (int row = 0; row < rows; row++) {
			for (int col = 0; col < cols; col++) {
				cells[col][row] = new GameOfLifeCell();
			}
		}
	}
	
	

	/* (non-Javadoc)
	 * @see ar.edu.unlp.CellularAutomaton.model.GameGrid#getCols()
	 */
	@Override
	public int getCols(){
		return cols;
	}
	
	/* (non-Javadoc)
	 * @see ar.edu.unlp.CellularAutomaton.model.GameGrid#getRows()
	 */
	@Override
	public int getRows(){
		return rows;
	}
	
	/* (non-Javadoc)
	 * @see ar.edu.unlp.CellularAutomaton.model.GameGrid#getRule()
	 */
	@Override
	public Rule getRule() {
		return rule;
	}

	/* (non-Javadoc)
	 * @see ar.edu.unlp.CellularAutomaton.model.GameGrid#setRule(Rule rule)
	 */
	@Override
	public synchronized void setRule(Rule rule) {
		this.rule = rule;
	}

	/* (non-Javadoc)
	 * @see ar.edu.unlp.CellularAutomaton.model.GameGrid#getGeneration()
	 */
	@Override
	public int getGeneration() {
		return generation;
	}

	/* (non-Javadoc)
	 * @see ar.edu.unlp.CellularAutomaton.model.GameGrid#getCell(int, int)
	 */
	@Override
	public GameOfLifeCell getCell(int col, int row){
		return cells[col][row];
	}

	/* (non-Javadoc)
	 * @see ar.edu.unlp.CellularAutomaton.model.GameGrid#nextGeneration()
	 */
	@Override
	public synchronized void nextGeneration(){
		
		//count alive neighbors
		for (int row = 0; row < rows; row++) {
			for (int col = 0; col < cols; col++) {
				countNeighbors(col,row);
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
	 * count neighbors in the neighborhood
	 * @param col matrix's column
	 * @param row matrix's row
	 */
	private void countNeighbors(final int col, final int row){
		GameOfLifeCell cell = cells[col][row];
		cell.setAliveNeighbors(0);
		for (Neighbor neighbor : rule.getNeighbors()) {
			addNeighbor(cell, col+neighbor.getCol(), row+neighbor.getRow());
		}
	}
	
	/**
	 * Add neighbor to the counter of cell
	 * @param cell
	 * @param neighborCell
	 */
	private void addNeighbor(GameOfLifeCell cell, int col, int row){
		try {
			cell.addNeighbor(cells[ mod(col,cols)][mod(row, rows)]);
		} catch (ArrayIndexOutOfBoundsException e) {
			System.out.println(col+", "+row);
			//ignore border of the grid
		}
	}
	
	/**
	 * used for circular frontier
	 * @param i col
	 * @param j	row
	 * @return 
	 */
	private int mod(int i,int j){
		return ((i % j) + j) % j;
	}
	
	/* (non-Javadoc)
	 * @see ar.edu.unlp.CellularAutomaton.model.GameGrid#resize(int, int)
	 */
	@Override
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


	/* (non-Javadoc)
	 * @see ar.edu.unlp.CellularAutomaton.model.GameGrid#reset()
	 */
	@Override
	public synchronized void reset(){
		for (int row = 0; row < rows; row++) {
			for (int col = 0; col < cols; col++) {
				cells[col][row].setState(GameOfLifeCell.DEAD);
				cells[col][row].setAliveNeighbors((byte) 0);
			}
		}
		generation=0;
	}
	
	/* (non-Javadoc)
	 * @see ar.edu.unlp.CellularAutomaton.model.GameGrid#loadShape(ar.edu.unlp.CellularAutomaton.util.Shape)
	 */
	@Override
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
	 * @see ar.edu.unlp.CellularAutomaton.model.GameGrid#toString()
	 */
	@Override
	public String toString(){
		return "Grid("+cols+", "+rows+")";
	}
}
