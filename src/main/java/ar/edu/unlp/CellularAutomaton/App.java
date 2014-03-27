package ar.edu.unlp.CellularAutomaton;

import java.util.Observable;
import java.util.Observer;

import ar.edu.unlp.CellularAutomaton.model.GameOfLifeGrid;
import ar.edu.unlp.CellularAutomaton.model.GameOfLifeGrid.ManagerOfThreads;


/**
 * Main Class
 *@author mclo
 */
public class App 
{
	/**
	 * Launch the application.
	 * @param args args[0] colums, args[1] rows, args[2] generations, args[3] workers
	 */
	public static void main(String[] args) {
		int cols = Integer.valueOf(args[0]);
		int rows = Integer.valueOf(args[1]);
		int generations  = Integer.valueOf(args[2]);
		int workers  = Integer.valueOf(args[3]);
		final long time_start;
		
		//check for errors
		if ((rows % workers) != 0) {
			System.out.println("ERROR: El número de columnas tiene que ser multiplo de la cantidad de workers");
			return;
		}
		
		time_start = System.currentTimeMillis();
		final GameOfLifeGrid grid = new GameOfLifeGrid(cols, rows);
		final ManagerOfThreads managerOfThreads = grid.newManagerOfThreads(workers, generations);
		
		//when finish all threads
		managerOfThreads.addObserver(new Observer() {
			@Override
			public void update(Observable o, Object arg) {
				long time_end = System.currentTimeMillis();
				System.out.println(grid);
				System.out.println("generations: "+grid.getGeneration());
				System.out.println("Workers: "+ managerOfThreads.getNumOfThreads());
				System.out.println("The task has taken "+ ( time_end - time_start ) +" milliseconds");
			}
		});
		
		managerOfThreads.start();
	}
}
