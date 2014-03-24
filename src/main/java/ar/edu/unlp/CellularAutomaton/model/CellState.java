package ar.edu.unlp.CellularAutomaton.model;

public interface CellState {

	/**
	 * Apply transition function depending on the state
	 * @param cell it is a cell to which is applied the function
	 */
	public void transitionFunction(GameOfLifeCell cell);

	/**
	 * @param cell to change its state
	 */
	public void switchState(GameOfLifeCell cell);

	/**
	 * @param gameOfLifeCell if gameOfLifeCell's state is alive add one
	 */
	public void addAliveNeighbor(GameOfLifeCell gameOfLifeCell);

	/**
	 * @return RGB color
	 */
	public int getColor();

}
