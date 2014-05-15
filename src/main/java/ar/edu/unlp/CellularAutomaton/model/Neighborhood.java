package ar.edu.unlp.CellularAutomaton.model;

import java.util.Arrays;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * 
 * @author mclo
 */
public class Neighborhood {
	
	public static final  Neighborhood MOORE = new Neighborhood(new CopyOnWriteArraySet<Neighbor>(Arrays.asList(new Neighbor(-1,-1),new Neighbor(-1,0),new Neighbor(-1,1),new Neighbor(0,-1),new Neighbor(0,1),new Neighbor(1,-1),new Neighbor(1,0),new Neighbor(1,1))));
	
	private CopyOnWriteArraySet<Neighbor> neighbors;
	
	public Neighborhood() {
		super();
		this.neighbors = new CopyOnWriteArraySet<Neighbor>(Arrays.asList(new Neighbor(-1,-1),new Neighbor(-1,0),new Neighbor(-1,1),new Neighbor(0,-1),new Neighbor(0,1),new Neighbor(1,-1),new Neighbor(1,0),new Neighbor(1,1)));
	}


	/** a copy
	 * @param neighborhood
	 */
	public Neighborhood(Neighborhood neighborhood) {
		CopyOnWriteArraySet<Neighbor> hsn = new CopyOnWriteArraySet <Neighbor> ();
		for (Neighbor n : neighborhood.getNeighbors()) {
			hsn.add(n);
		}
		this.neighbors = hsn;
	}
	

	public Neighborhood(CopyOnWriteArraySet<Neighbor> neighbors) {
		super();
		this.neighbors = neighbors;
	}


	public int getSize(){
		return neighbors.size();
	}

	public Set<Neighbor> getNeighbors() {
		return neighbors;
	}

	public void setNeighborhood(CopyOnWriteArraySet<Neighbor> neighborhood) {
		this.neighbors = neighborhood;
	}

	public void remove(int col, int row) {
		this.neighbors.remove(new Neighbor(col, row));
		
	}

	public void add(int col, int row) {
		this.neighbors.add(new Neighbor(col, row));
	}

	@Override
	public String toString() {
		return neighbors.toString();
	}

	

}
