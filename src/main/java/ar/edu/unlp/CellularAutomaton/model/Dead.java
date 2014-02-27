package ar.edu.unlp.CellularAutomaton.model;

public class Dead implements CellState {

	public boolean isAlive() {
		return false;
	}

	public boolean isDead() {
		return true;
	}

	public void changeState(GameOfLifeCell cell) {
		if (cell.getAliveNeighbors() == 3) {
			switchState(cell);
		}
	}

	public void switchState(GameOfLifeCell cell) {
		cell.setState(GameOfLifeCell.ALIVE);
	}

	public void updateNeighbors(GameOfLifeCell cell) {
		for (GameOfLifeCell neighborCell : cell.getNeighbors()) {
			neighborCell.subNeighbor();
		}
	}

	public String toString() {
		return "0";
	}

}
