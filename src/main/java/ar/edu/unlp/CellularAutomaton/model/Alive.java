package ar.edu.unlp.CellularAutomaton.model;

public class Alive implements CellState {

	public boolean isAlive() {
		return true;
	}

	public boolean isDead() {
		return false;
	}

	public void transitionFunction(GameOfLifeCell cell) {
		if (cell.getAliveNeighbors() < 2 || cell.getAliveNeighbors() > 3) {
			switchState(cell);
		}
	}

	public void switchState(GameOfLifeCell cell) {
		cell.setState(GameOfLifeCell.DEAD);
	}
	
	public void addAliveNeighbor(GameOfLifeCell cell) {
		cell.addNeighbor();
	}
	
	public int getColor(){
		return 0xffffff;
	}

	public String toString() {
		return "Alive";
	}

}
