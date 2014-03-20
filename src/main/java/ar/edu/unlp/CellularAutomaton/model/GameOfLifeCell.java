package ar.edu.unlp.CellularAutomaton.model;

public class GameOfLifeCell {
	
	public static CellState ALIVE = new Alive();
	public static CellState DEAD = new Dead();

	private byte aliveNeighbors;
	private CellState state;
	
	public GameOfLifeCell() {
		super();
		state = new Dead();
		aliveNeighbors = 0;
	}

	public byte getAliveNeighbors() {
		return aliveNeighbors;
	}

	public void setAliveNeighbors(byte aliveNeighbors) {
		this.aliveNeighbors = aliveNeighbors;
	}

	public CellState getState() {
		return state;
	}

	public void setState(CellState state) {
		this.state = state;
	}
	
	public void addNeighbor(){
		aliveNeighbors++;
	}
	
	public void subNeighbor() {
		aliveNeighbors--;
	}
	
	public void addAliveNeighbor(GameOfLifeCell cell) {
		cell.getState().addAliveNeighbor(this);
	}
	
	public void transitionFunction() {
		state.transitionFunction(this);
	}
	
	public void switchState() {
		state.switchState(this);
	}
	
	/**
	 * @return RGB color of the state
	 */
	public int getColor(){
		return state.getColor();
	}
	
	public void setColor(int RGB){
		state.setColor(RGB);
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString(){
		return "Cell ["+state.toString()+" ,"
					   +Byte.toString(aliveNeighbors)+"]";
	}
}
