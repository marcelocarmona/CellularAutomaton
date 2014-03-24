package ar.edu.unlp.CellularAutomaton.model;

/**
 * Represents the alive state
 * @author mclo
 */
public class Alive implements CellState {

	/* (non-Javadoc)
	 * @see ar.edu.unlp.CellularAutomaton.model.CellState#transitionFunction(ar.edu.unlp.CellularAutomaton.model.GameOfLifeCell)
	 */
	public void transitionFunction(GameOfLifeCell cell) {
		if (cell.getAliveNeighbors() < 2 || cell.getAliveNeighbors() > 3) {
			switchState(cell);
		}
	}

	/* (non-Javadoc)
	 * @see ar.edu.unlp.CellularAutomaton.model.CellState#switchState(ar.edu.unlp.CellularAutomaton.model.GameOfLifeCell)
	 */
	public void switchState(GameOfLifeCell cell) {
		cell.setState(GameOfLifeCell.DEAD);
	}

	/* (non-Javadoc)
	 * @see ar.edu.unlp.CellularAutomaton.model.CellState#addAliveNeighbor(ar.edu.unlp.CellularAutomaton.model.GameOfLifeCell)
	 */
	public void addAliveNeighbor(GameOfLifeCell cell) {
		cell.addNeighbor();
	}

	/* (non-Javadoc)
	 * @see ar.edu.unlp.CellularAutomaton.model.CellState#getColor()
	 */
	public int getColor() {
		return 0xffffff;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return "Alive";
	}

}
