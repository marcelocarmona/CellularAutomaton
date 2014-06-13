package ar.edu.unlp.CellularAutomaton.model;

import java.util.Set;

public class Rule {

	
	private String name;
	private String description;
	private Neighborhood neighborhood;
	private CellState aliveState;
	private CellState deadState;
	
	public Rule(String name, String description, Neighborhood neighborhood,
			CellState aliveState, CellState deadState) {
		super();
		this.name = name;
		this.description = description;
		this.neighborhood = neighborhood;
		this.aliveState = aliveState;
		this.deadState = deadState;
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
	public CellState getAliveState() {
		return aliveState;
	}
	public void setAliveStateRule(CellState aliveStateRule) {
		this.aliveState = aliveStateRule;
	}
	public CellState getDeadState() {
		return deadState;
	}
	public void setDeadStateRule(CellState deadStateRule) {
		this.deadState = deadStateRule;
	}
	
	public synchronized void addNeighbor(int col, int row){
		neighborhood.add(col, row);
		aliveState.getStateRule().addLast();
		deadState.getStateRule().addLast();
	}
	
	public synchronized void removeNeighbor(int col, int row){
		neighborhood.remove(col, row);
		aliveState.getStateRule().removeLast();
		deadState.getStateRule().removeLast();
	}
	public synchronized  Set<Neighbor> getNeighbors() {
		return neighborhood.getNeighbors();
	}
	@Override
	public String toString() {
		return "Rule [name=" + name + ", description=" + description
				+ ", neighborhood=" + neighborhood + ", aliveState="
				+ aliveState + ", deadState=" + deadState + "]";
	}
	public void loadNeighborhood(Neighborhood neighborhood2) {
		neighborhood.getNeighbors().clear();
		for (Neighbor n : neighborhood.getNeighbors()) {
			neighborhood.getNeighbors().add(n);
		}
		
		// TODO Auto-generated method stub
		
	}
	


}
