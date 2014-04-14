package ar.edu.unlp.CellularAutomaton.model;

/**
 * Cell's state
 * @see GameOfLifeCell
 * @author mclo
 */
public interface CellState {
	
	/**
	 * @return the rule to change state
	 */
	public ArrayRule getArrayRule();

	/**
	 * @param rule the rule to change state
	 */
	public void setArrayRule(ArrayRule arrayRule);

	/**
	 * @return RGB color
	 */
	public int getColor();

	/**
	 * @param color RGB color
	 */
	public void setColor(int color);

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
	public void addAliveNeighbor(GameOfLifeCell cell);

}
