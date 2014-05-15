package ar.edu.unlp.CellularAutomaton.model;


/**
 * Abtract state, getter and setter of rule and color
 * @see Alive
 * @see Dead
 * @author mclo
 */
public abstract class AbstractState implements CellState {
	private Rule rule;
	private int color;
 
	/**
	 * Constructor
	 * @param rule rule of the state
	 */
	public AbstractState(Rule rule) {
		super();
		this.rule = rule;
	}

	/* (non-Javadoc)
	 * @see ar.edu.unlp.CellularAutomaton.model.CellState#getRule()
	 */
	public Rule getRule() {
		return rule;
	}

	/* (non-Javadoc)
	 * @see ar.edu.unlp.CellularAutomaton.model.CellState#setRule(ar.edu.unlp.CellularAutomaton.model.ArrayRule)
	 */
	public void setRule(Rule rule) {
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
