package ar.edu.unlp.CellularAutomaton.model;

public class Rule {
	private String name;
	private String description;
	private Neighborhood neighborhood;
	private StateRule aliveStateRule;
	private StateRule deadStateRule;
	
	public Rule(String name, String description, Neighborhood neighborhood,
			StateRule aliveStateRule, StateRule deadStateRule) {
		super();
		this.name = name;
		this.description = description;
		this.neighborhood = neighborhood;
		this.aliveStateRule = aliveStateRule;
		this.deadStateRule = deadStateRule;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Neighborhood getNeighborhood() {
		return neighborhood;
	}
	public void setNeighborhood(Neighborhood neighborhood) {
		this.neighborhood = neighborhood;
	}
	public StateRule getAliveStateRule() {
		return aliveStateRule;
	}
	public void setAliveStateRule(StateRule aliveStateRule) {
		this.aliveStateRule = aliveStateRule;
	}
	public StateRule getDeadStateRule() {
		return deadStateRule;
	}
	public void setDeadStateRule(StateRule deadStateRule) {
		this.deadStateRule = deadStateRule;
	}
	
	public void addNeighbor(int col, int row){
		neighborhood.add(col, row);
		aliveStateRule.addLast();
		deadStateRule.addLast();
	}
	
	public void removeNeighbor(int col, int row){
		neighborhood.remove(col, row);
		aliveStateRule.removeLast();
		deadStateRule.removeLast();
	}

}
