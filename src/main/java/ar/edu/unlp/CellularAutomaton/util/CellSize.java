package ar.edu.unlp.CellularAutomaton.util;

/**
 * Cell's size selected in the Window
 * @see Window
 * @author mclo
 */
public enum CellSize {

	BIG("Big", 20), 
	MEDIUM("Medium", 10), 
	SMALL("Small", 5);

	private String name;
	private int value;

	/**
	 * @param name name of the cell's size
	 * @param value value of the cell's size
	 */
	private CellSize(String name, int value) {
		this.name = name;
		this.value = value;
	}

	/**
	 * @return value of the cell's size
	 */
	public int getValue() {
		return value;
	}

	/* (non-Javadoc)
	 * @see java.lang.Enum#toString()
	 */
	public String toString() {
		return name;
	}

}
