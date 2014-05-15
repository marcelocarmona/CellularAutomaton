package ar.edu.unlp.CellularAutomaton.model;

/**
 * The cell has a state that it can be alive o dead
 * Every cell in the grid is a GameOfLifeCell
 * 
 * @author mclo
 */
public class GameOfLifeCell {
	
	/**
	 * States of cells 
	 */
	public static final CellState ALIVE = new Alive(new Rule(8,"Survivals",2,3));
	public static final CellState DEAD = new Dead(new Rule(8,"Births",3));
	public static final CellState[] STATES = {ALIVE, DEAD};
	
	/**
	 * Number of neighbors with alive state
	 * Used to determines the next state
	 */
	private int aliveNeighbors;
	
	
	/**
	 * Can be a Dead or Alive object
	 */
	private CellState state;
	
	/**
	 * Constructor
	 */
	public GameOfLifeCell() {
		super();
		state = DEAD;
		aliveNeighbors = 0;
	}

	/**
	 * @return number of alive neighbors
	 */
	public int getAliveNeighbors() {
		return aliveNeighbors;
	}

	/**
	 * @param aliveNeighbors number of alive neighbors
	 */
	public void setAliveNeighbors(int aliveNeighbors) {
		this.aliveNeighbors = aliveNeighbors;
	}

	/**
	 * @return Dead or Alive object
	 */
	public CellState getState() {
		return state;
	}

	/**
	 * @param state Dead or Alive object
	 */
	public void setState(CellState state) {
		this.state = state;
	}
	
	/**
	 * Add a live neighbor
	 */
	public void addNeighbor(){
		aliveNeighbors++;
	}
	
	/**
	 * Subtract a live neighbor
	 */
	public void subNeighbor() {
		aliveNeighbors--;
	}
	
	/**
	 * @param cell if cell's state is alive add one
	 */
	public void addAliveNeighbor(GameOfLifeCell cell) {
		cell.getState().addAliveNeighbor(this);
	}
	
	/**
	 * Apply transition function depending on the state
	 */
	public void transitionFunction() {
		state.transitionFunction(this);
	}
	
	/**
	 * From live to dead and viceversa
	 */
	public void switchState() {
		state.switchState(this);
	}
	
	/**
	 * @return RGB color of the state
	 */
	public int getColor(){
		return state.getColor();
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString(){
		return "Cell ["+state.toString()+" ,"
					   +Integer.toString(aliveNeighbors)+"]";
	}
}
