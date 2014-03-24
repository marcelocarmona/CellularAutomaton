package ar.edu.unlp.CellularAutomaton;

import ar.edu.unlp.CellularAutomaton.model.GameOfLifeGrid;


/**
 * Main Class
 * @author mclo
 */
public class App 
{
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		
		int cols = Integer.valueOf(args[0]);
		int rows = Integer.valueOf(args[1]);
		int generations  = Integer.valueOf(args[2]);
		
		GameOfLifeGrid grid = new GameOfLifeGrid(cols, rows);
		
		for (int i = 0; i < generations; i++) {
			grid.nextGeneration();
			System.out.println(grid.getGeneration());
		}
	}
}
