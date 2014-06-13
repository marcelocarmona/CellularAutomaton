package ar.edu.unlp.CellularAutomaton.model;


/**
 * Represents the dead state
 * @author mclo
 */
public class Dead extends AbstractState {
	
	public Dead(StateRule rule) {
		super(rule);
		setColor(0x333333);
	}

	/* (non-Javadoc)
	 * @see ar.edu.unlp.CellularAutomaton.model.CellState#transitionFunction(ar.edu.unlp.CellularAutomaton.model.GameOfLifeCell)
	 */
	public void transitionFunction(GameOfLifeCell cell) {
		if (getRule().include(cell.getAliveNeighbors())) {
			switchState(cell);
		}
	}

	/* (non-Javadoc)
	 * @see ar.edu.unlp.CellularAutomaton.model.CellState#switchState(ar.edu.unlp.CellularAutomaton.model.GameOfLifeCell)
	 */
	public void switchState(GameOfLifeCell cell) {
		cell.setState(GameOfLifeCell.ALIVE);
	}

	/* (non-Javadoc)
	 * @see ar.edu.unlp.CellularAutomaton.model.CellState#addAliveNeighbor(ar.edu.unlp.CellularAutomaton.model.GameOfLifeCell)
	 */
	public void addAliveNeighbor(GameOfLifeCell gameOfLifeCell) {
		// this state is dead, not add
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return "Dead";
	}

}
