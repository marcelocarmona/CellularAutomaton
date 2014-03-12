package ar.edu.unlp.CellularAutomaton.util;

public enum SpeedTime {
	
	 SLOW ("Slow",500),
	 FAST ("Fast",100),
	 HYPER ("Hyper",30);
	 
	 private String name;
	 private int value;
	 
	private SpeedTime(String name, int value) {
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
