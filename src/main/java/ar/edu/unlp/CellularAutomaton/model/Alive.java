package ar.edu.unlp.CellularAutomaton.model;

public class Alive implements CellState {

	public boolean isAlive() {
		return true;
	}

	public boolean isDead() {
		return false;
	}

	public void changeState(GameOfLifeCell cell) {
		if (cell.getAliveNeighbors() < 2 || cell.getAliveNeighbors() > 3) {
			switchState(cell);
		}
	}

	public void switchState(GameOfLifeCell cell) {
		cell.setState(GameOfLifeCell.DEAD);
	}

	public void updateNeighbors(GameOfLifeCell cell) {
		for (GameOfLifeCell neighborCell : cell.getNeighbors()) {
			neighborCell.addNeighbor();
		}
	}

	public String toString() {
		return "1";
	}

}
