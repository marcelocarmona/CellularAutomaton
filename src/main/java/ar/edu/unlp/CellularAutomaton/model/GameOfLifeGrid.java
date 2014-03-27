package ar.edu.unlp.CellularAutomaton.model;

import java.util.Observable;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class GameOfLifeGrid {
	
	private int generation;
	private int cols;
	private int rows;
	private GameOfLifeCell[][] cells;
	private ManagerOfThreads managerOfThreads;

	public GameOfLifeGrid(int cols, int rows) {
		super();
		generation = 0;
		this.cols = cols;
		this.rows = rows;
		cells = new GameOfLifeCell[cols][rows];
		managerOfThreads = new ManagerOfThreads(1,1);
		
		//initialize the grid with cells
		for (int row = 0; row < rows; row++) {
			for (int col = 0; col < cols; col++) {
				cells[col][row] = new GameOfLifeCell();
			}
		}
	}

	
	public int getGeneration() {
		return generation;
	}

	public ManagerOfThreads getManagerOfThreads() {
		return managerOfThreads;
	}

	public ManagerOfThreads newManagerOfThreads(int numOfThreads,int numOfGenerations){
		return managerOfThreads = new ManagerOfThreads(numOfThreads,numOfGenerations);
	}

	/**
	 * Creates and manages threads
	 */
	public class ManagerOfThreads extends Observable{
		private int numOfGenerations;
		private int numOfThreads;
		private CyclicBarrier countAliveNeighborsBarrier;
		private CyclicBarrier transitionFunctionBarrier;
		
		
		public class Worker implements Runnable{
			private int currentThread;
			
			public Worker(int currentThread) {
				super();
				this.currentThread = currentThread;
			}

			@Override
			public  void run() {
				for (int i = 0; i < numOfGenerations; i++) {
					try {
							countAliveNeighborsMatrix(numOfThreads, currentThread);
							countAliveNeighborsBarrier.await();//CyclicBarrier await

							transitionFuncionMatrix(numOfThreads, currentThread);
							transitionFunctionBarrier.await();//CyclicBarrier await
							
					} catch (InterruptedException | BrokenBarrierException e) {e.printStackTrace();}
				}
			}
		}

		public class BarrierAction implements Runnable {

			@Override
			public void run() {
					generation++;
					if (generation == numOfGenerations){
						setChanged();
						notifyObservers();
					}
			}
		}
		
		
		public ManagerOfThreads(int numOfThreads,int numOfGenerations) {
			super();
			this.numOfGenerations = numOfGenerations;
			this.numOfThreads = numOfThreads;
			this.countAliveNeighborsBarrier = new CyclicBarrier(numOfThreads);
			this.transitionFunctionBarrier = new CyclicBarrier(numOfThreads,new BarrierAction());
		}
		
		public int getNumOfThreads(){
			return numOfThreads;
		}
		
		public synchronized void setNumOfThreads(int numOfThreads) {
			this.numOfThreads = numOfThreads;
			this.countAliveNeighborsBarrier = new CyclicBarrier(numOfThreads);
			this.transitionFunctionBarrier = new CyclicBarrier(numOfThreads,new BarrierAction());
		}
		
		public synchronized void start(){
			for (int t = 0; t < numOfThreads; t++) {
				new Thread(new Worker(t),"thread: "+t).start();
			}
		}

	}

	private void countAliveNeighbors(final int col, final int row){
		try{cells[col][row].setAliveNeighbors((byte) 0);}catch(ArrayIndexOutOfBoundsException e){}
		try{cells[col][row].addAliveNeighbor(cells[col-1][row-1]);}catch(ArrayIndexOutOfBoundsException e){}
		try{cells[col][row].addAliveNeighbor(cells[col-1][row]);}catch(ArrayIndexOutOfBoundsException e){}
		try{cells[col][row].addAliveNeighbor(cells[col-1][row+1]);}catch(ArrayIndexOutOfBoundsException e){}
		try{cells[col][row].addAliveNeighbor(cells[col][row-1]);}catch(ArrayIndexOutOfBoundsException e){}
		try{cells[col][row].addAliveNeighbor(cells[col][row+1]);}catch(ArrayIndexOutOfBoundsException e){}
		try{cells[col][row].addAliveNeighbor(cells[col+1][row-1]);}catch(ArrayIndexOutOfBoundsException e){}
		try{cells[col][row].addAliveNeighbor(cells[col+1][row]);}catch(ArrayIndexOutOfBoundsException e){}
		try{cells[col][row].addAliveNeighbor(cells[col+1][row+1]);}catch(ArrayIndexOutOfBoundsException e){}
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString(){
		return "Grid("+cols+", "+rows+")";
	}
	
	/**
	 * Apply the function to a portion of the matrix
	 * @param numOfThreads number of threads of managerOfThreads
	 * @param currentThread one number from 0 to numOfThreads
	 */
	private void countAliveNeighborsMatrix(int numOfThreads, int currentThread) {
		int firstRow = ((rows / numOfThreads) * currentThread);
		int firstCol = 0;
		int lastRow = ((rows / numOfThreads) * (currentThread + 1));
		int lastCol = cols;

		for (int row = firstRow; row < lastRow; row++) {
			for (int col = firstCol; col < lastCol; col++) {
				countAliveNeighbors(col, row);
			}
		}
	}
	
	/**
	 * Apply the function to a portion of the matrix
	 * @param numOfThreads number of threads of managerOfThreads
	 * @param currentThread one number from 0 to numOfThreads
	 */
	private void transitionFuncionMatrix(int numOfThreads, int currentThread) {

		int firstRow = ((rows / numOfThreads) * currentThread);
		int firstCol = 0;
		int lastRow = ((rows / numOfThreads) * (currentThread + 1));
		int lastCol = cols;

		for (int row = firstRow; row < lastRow; row++) {
			for (int col = firstCol; col < lastCol; col++) {
				cells[col][row].transitionFunction();
			}
		}
	}
}
