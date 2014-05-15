package ar.edu.unlp.CellularAutomaton.model;

import ar.edu.unlp.CellularAutomaton.exception.ShapeException;
import ar.edu.unlp.CellularAutomaton.util.Shape;

public interface GameGrid {

	/**
	 * @return number of columns
	 */
	public abstract int getCols();

	/**
	 * @return number of rows
	 */
	public abstract int getRows();

	/**
	 * @return Set of Neighboors
	 */
	public abstract Neighborhood getNeighborhood();

	/**
	 * @param neighborhood Set of Neighboors
	 */
	public abstract void setNeighborhood(Neighborhood neighborhood);

	/**
	 * @return current generation
	 */
	public abstract int getGeneration();

	/**
	 * @param col matrix's column
	 * @param row matrix's row
	 * @return a gameOfLifeCell
	 */
	public abstract GameOfLifeCell getCell(int col, int row);

	/**
	 * Create next generator of shape
	 */
	public abstract void nextGeneration();

	/**
	 * Resize grid. Reuse existing cells.
	 * 
	 * @param cols of grid
	 * @param rows of grid
	 */
	public abstract void resize(int width, int height);

	/**
	 * Reset the grid
	 */
	public abstract void reset();

	/**
	 * Set shape in grid
	 * @param shape name of shape
	 * @throws ShapeException if shape does not fit on grid
	 */
	public abstract void loadShape(Shape shape) throws ShapeException;

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public abstract String toString();

}