package ar.edu.unlp.CellularAutomaton.model;


/**
 * Abtract state, getter and setter of stateRule and color
 * @see Alive
 * @see Dead
 * @author mclo
 */
public abstract class AbstractState implements CellState {
	private StateRule stateRule;
	private int color;
 
	/**
	 * Constructor
	 * @param stateRule stateRule of the state
	 */
	public AbstractState(StateRule rule) {
		super();
		this.stateRule = rule;
	}

	/* (non-Javadoc)
	 * @see ar.edu.unlp.CellularAutomaton.model.CellState#getRule()
	 */
	public StateRule getStateRule() {
		return stateRule;
	}

	/* (non-Javadoc)
	 * @see ar.edu.unlp.CellularAutomaton.model.CellState#setRule(ar.edu.unlp.CellularAutomaton.model.ArrayRule)
	 */
	public void setStateRule(StateRule rule) {
		this.stateRule = rule;
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
	
	public String toString(){
		return "["+this.getClass().getName()+" stateRule=["+stateRule+"]]";
	}

}
