package ar.edu.unlp.CellularAutomaton.model;

public interface CellState {
	
	
	
	public boolean isAlive();
	public boolean isDead();
	
	public void transitionFunction(GameOfLifeCell cell);
	public void switchState(GameOfLifeCell cell);
	public void addAliveNeighbor(GameOfLifeCell gameOfLifeCell);
	
	/**
	 * @return RGB color
	 */
	public int getColor();
	
	

}
