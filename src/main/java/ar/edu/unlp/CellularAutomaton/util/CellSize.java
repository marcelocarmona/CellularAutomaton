package ar.edu.unlp.CellularAutomaton.util;

public enum CellSize {

	BIG("Big", 20), 
	MEDIUM("Medium", 10), 
	SMALL("Small", 5);

	private String name;
	private int value;

	private CellSize(String name, int value) {
		this.name = name;
		this.value = value;
	}

	public int getValue() {
		return value;
	}

	@Override
	public String toString() {
		return name;
	}

}
