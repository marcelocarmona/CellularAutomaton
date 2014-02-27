package ar.edu.unlp.CellularAutomaton.model;

import java.util.ArrayList;
import java.util.List;

public class GameOfLifeCell {
	
	public static CellState ALIVE = new Alive();
	public static CellState DEAD = new Dead();

	private List<GameOfLifeCell> neighbors;
	private byte aliveNeighbors;
	private CellState state;
	
	public GameOfLifeCell() {
		super();
		state = DEAD;
		neighbors = new ArrayList<GameOfLifeCell>();
		aliveNeighbors = 0;
	}

	public List<GameOfLifeCell> getNeighbors() {
		return neighbors;
	}

	public void setNeighbors(List<GameOfLifeCell> neighbors) {
		this.neighbors = neighbors;
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
	
	public void setStateAndUpdateNeighbors(CellState state) {
		this.state = state;
		this.state.updateNeighbors(this);
	}
	
	public void addNeighbor(){
		aliveNeighbors++;
	}
	
	public void subNeighbor() {
		aliveNeighbors--;
	}
	
	public void addNeighbor(GameOfLifeCell cell) {
		neighbors.add(cell);
	}
	
	public void updateAliveNeighbors(){
		aliveNeighbors=0;
		for (GameOfLifeCell cell : getNeighbors()) {
			if (cell.getState().isAlive())
				aliveNeighbors++;
		}
	}
	
	public void transitionFunction() {
		state.changeState(this);
	}
	
	public void switchState() {
		state.switchState(this);
		state.updateNeighbors(this);
	}
	
	public String toString(){
		return state.toString();
	}
}
