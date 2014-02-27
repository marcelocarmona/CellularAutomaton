package ar.edu.unlp.CellularAutomaton.model;

public interface CellState {
	
	
	
	public boolean isAlive();
	public boolean isDead();
	
	public void changeState(GameOfLifeCell cell);
	public void switchState(GameOfLifeCell cell);
	public void updateNeighbors(GameOfLifeCell gameOfLifeCell);

}
