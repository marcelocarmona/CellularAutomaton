package ar.edu.unlp.CellularAutomaton.model;

/**
 * Abtract state, getter and setter of rule and color
 * @see Alive
 * @see Dead
 * @author mclo
 */
public abstract class AbstractState implements CellState {
	private ArrayRule rule;
	private int color;

	/* (non-Javadoc)
	 * @see ar.edu.unlp.CellularAutomaton.model.CellState#getRule()
	 */
	public ArrayRule getArrayRule() {
		return rule;
	}

	/* (non-Javadoc)
	 * @see ar.edu.unlp.CellularAutomaton.model.CellState#setRule(ar.edu.unlp.CellularAutomaton.model.ArrayRule)
	 */
	public void setArrayRule(ArrayRule rule) {
		this.rule = rule;
	}

	/* (non-Javadoc)
	 * @see ar.edu.unlp.CellularAutomaton.model.CellState#getColor()
	 */
	public int getColor() {
		return color;
	}

	/* (non-Javadoc)
	 * @see ar.edu.unlp.CellularAutomaton.model.CellState#setColor(int)
	 */
	public void setColor(int color) {
		this.color = color;
	}

}
