package ar.edu.unlp.CellularAutomaton.util;

/**
 * Speed time selected in the Window
 * @see Window
 * @author mclo
 */
public enum SpeedTime {
	
	 SLOW ("Slow",500),
	 FAST ("Fast",100),
	 HYPER ("Hyper",30);
	 
	 private String name;
	 private int value;
	 
	/**
	 * @param name name of the speed time
	 * @param value value of the speed time
	 */
	private SpeedTime(String name, int value) {
		this.name = name;
		this.value = value;
	}

	/**
	 * @returnv alue of the speed time
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
