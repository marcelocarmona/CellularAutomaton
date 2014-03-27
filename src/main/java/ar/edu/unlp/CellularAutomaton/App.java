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
	 * @param args args[0] colums, args[1] rows, args[2] generations
	 */
	public static void main(String[] args) {
		
		int cols = Integer.valueOf(args[0]);
		int rows = Integer.valueOf(args[1]);
		int generations  = Integer.valueOf(args[2]);
		long time_start, time_end;
		
		time_start = System.currentTimeMillis();
		
		GameOfLifeGrid grid = new GameOfLifeGrid(cols, rows);
		
		for (int i = 0; i < generations; i++) {
			grid.nextGeneration();
		}
		
		time_end = System.currentTimeMillis();
		System.out.println(grid);
		System.out.println("generations: "+grid.getGeneration());
		System.out.println("The task has taken "+ ( time_end - time_start ) +" milliseconds");
	}
}
