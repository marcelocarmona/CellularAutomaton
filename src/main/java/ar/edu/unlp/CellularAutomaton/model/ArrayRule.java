package ar.edu.unlp.CellularAutomaton.model;


/**
 * ArrayRule used to change state of the cells
 * @see Alive
 * @see Dead
 * @author mclo
 */
public class ArrayRule {

	/**
	 * neighborsCounter
	 * [0,1,2,3,4,5,6,7,8]
	 * 	| | | | | | | | |
	 *  | | | | | | | | +->boolean to represent eight neighbors cells
	 *  | | | | | | | +->boolean to represent seven neighbors cells
	 *  | | | | | | +->boolean to represent six neighbors cells
	 *  | | | | | +->boolean to represent five neighbors cells
	 *  | | | | +->boolean to represent four neighbors cells
	 *  | | | +->boolean to represent three neighbors cells
	 *  | | +->boolean to represent two neighbors cells
	 *  | +->boolean to represent one neighbors cells
	 *  +->boolean to represent zero neighbors cells
	 */
	private boolean[] neighborsCounter;
	
	/**
	 * size of neighborsCounter
	 */
	private int size;

	/**
	 * Initialize the neighborsCounter to false and put true the values of numOfNeighbors
	 * @param numOfNeighbors numbers that in the array will be true
	 */
	public ArrayRule(int... numOfNeighbors) {
		size = 9;
		this.neighborsCounter = new boolean[size];

		for (int i = 0; i < size; i++) {
			neighborsCounter[i] = false;
		}
		
		for (int i = 0; i < numOfNeighbors.length; i++) {
			this.neighborsCounter[numOfNeighbors[i]] = true;
		}
	}

	/**
	 * @param numberOfNeighbor position of the array
	 * @return boolean position
	 */
	public boolean getNeighborsCounter(int numberOfNeighbor) {
		return this.neighborsCounter[numberOfNeighbor];
	}	
	
	/**
	 * @param numberOfNeighbors position of the array
	 * @param value set value
	 */
	public void setNeighborsCounter(int numberOfNeighbor, boolean value) {
		this.neighborsCounter[numberOfNeighbor] = value;
	}

	/**
	 * @param numOfNeighbors
	 * @return if numOfNeighbors is include in the array
	 */
	public boolean include(int numOfNeighbors){
		return neighborsCounter[numOfNeighbors];
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString(){
		String name = "";
		for (int i = 0; i < size; i++) {
			if (neighborsCounter[i] == true) 
				name=name+i;
		}
		return name;
	}

}
