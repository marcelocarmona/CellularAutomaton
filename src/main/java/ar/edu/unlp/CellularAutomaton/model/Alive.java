package ar.edu.unlp.CellularAutomaton.model;


/**
 * Represents the alive state
 * @author mclo
 */
public class Alive extends AbstractState {

	public Alive(StateRule rule) {
		super(rule);
		setColor(0xffffff);
	}

	/* (non-Javadoc)
	 * @see ar.edu.unlp.CellularAutomaton.model.CellState#transitionFunction(ar.edu.unlp.CellularAutomaton.model.GameOfLifeCell)
	 */
	public void transitionFunction(GameOfLifeCell cell) {
		if (!getRule().include(cell.getAliveNeighbors())) {
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
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return "Alive";
	}

}
