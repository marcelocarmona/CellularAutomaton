package ar.edu.unlp.CellularAutomaton.model;

public class Dead implements CellState {
	private int color = 0x404040;

	public boolean isAlive() {
		return false;
	}

	public boolean isDead() {
		return true;
	}

	public void transitionFunction(GameOfLifeCell cell) {
		if (cell.getAliveNeighbors() == 3) {
			switchState(cell);
		}
	}

	public void switchState(GameOfLifeCell cell) {
		cell.setState(GameOfLifeCell.ALIVE);
	}
	
	public void addAliveNeighbor(GameOfLifeCell gameOfLifeCell) {
		// this state is dead, not add	
	}
	
	public int getColor(){
		return color;
	}

	public String toString() {
		return "Dead";
	}

	@Override
	public void setColor(int rGB) {
		color=rGB;
		
	}
}
