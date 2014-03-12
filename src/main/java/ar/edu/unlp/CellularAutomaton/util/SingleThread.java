package ar.edu.unlp.CellularAutomaton.util;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

import ar.edu.unlp.CellularAutomaton.model.GameOfLifeGrid;
import ar.edu.unlp.CellularAutomaton.model.GameOfLifeGrid.Worker;
import ar.edu.unlp.CellularAutomaton.view.Window;


public class SingleThread extends Thread {

	private volatile boolean done;
	private int sleepTime;
	private GameOfLifeGrid grid;
	private CyclicBarrier countAliveNeighborsBarrier;
	private CyclicBarrier transitionFunctionBarrier;
	private Window window;

	public SingleThread(GameOfLifeGrid grid, int sleepTime,CyclicBarrier countAliveNeighborsBarrier,CyclicBarrier transitionFunctionBarrier) {
		super();
		this.done = false;
		this.sleepTime = sleepTime;
		this.grid = grid;
		this.countAliveNeighborsBarrier = countAliveNeighborsBarrier;
		this.transitionFunctionBarrier = transitionFunctionBarrier;
	}

	public SingleThread(Window window, GameOfLifeGrid grid, int sleepTime,CyclicBarrier countAliveNeighborsBarrier,CyclicBarrier transitionFunctionBarrier) {
		this(grid, sleepTime,countAliveNeighborsBarrier,transitionFunctionBarrier);
		this.window = window;
	}

	public void setSleepTime(int sleepTime) {
		this.sleepTime = sleepTime;
	}

	public void finish() {
		window.done = true;
		countAliveNeighborsBarrier.reset();
	}

	@Override
	public void run() {

//		Worker w1 = grid.getWorker(0,0,grid.getRows()/2,grid.getCols()/2,countAliveNeighborsBarrier,transitionFunctionBarrier);
//		Worker w2 = grid.getWorker(grid.getRows()/2,grid.getCols()/2,grid.getRows(),grid.getCols(),countAliveNeighborsBarrier,transitionFunctionBarrier);
		
		
		while (!window.done) {
			try {
	    		//count alive neighbors
	    		for (int row = 0; row < grid.getRows()/2; row++) {
	    			for (int col = 0; col < grid.getCols(); col++) {
	    				grid.countAliveNeighbors(col,row);
	    			}
	    		}
	    		System.out.println("1 - init count");
	    		countAliveNeighborsBarrier.await();
	    		System.out.println("1 - finish count");
	    		
	    		//Transition Function
	    		for (int row = 0; row < grid.getRows()/2; row++) {
	    			for (int col = 0; col < grid.getCols(); col++) {
	    				grid.getCell(col, row).transitionFunction();	
	    			}
	    		}
	    		System.out.println("1 - init trans");
	    		transitionFunctionBarrier.await();
	    		System.out.println("1 - finish trans");
	    		System.out.println(grid.getGeneration());
				
			} catch (InterruptedException | BrokenBarrierException e) {e.printStackTrace();}
		}
		window.done = false;
		System.out.println("1 - Fin");
	}
}