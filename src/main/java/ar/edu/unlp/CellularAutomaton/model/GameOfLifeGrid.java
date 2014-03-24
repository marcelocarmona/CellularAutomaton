package ar.edu.unlp.CellularAutomaton.model;

import java.util.Enumeration;
import java.util.Observable;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

import ar.edu.unlp.CellularAutomaton.exception.ShapeException;
import ar.edu.unlp.CellularAutomaton.util.Shape;

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
		managerOfThreads = new ManagerOfThreads(200, 1);
		
		//initialize the grid with cells
		for (int row = 0; row < rows; row++) {
			for (int col = 0; col < cols; col++) {
				cells[col][row] = new GameOfLifeCell();
			}
		}
	}
	
	public GameOfLifeGrid() {
		generation = 0;
		//resize is executing
	}

	public int getCols(){
		return cols;
	}
	
	public int getRows(){
		return rows;
	}
	
	public int getGeneration() {
		return generation;
	}

	public GameOfLifeCell getCell(int col, int row){
		return cells[col][row];
	}

	/**
	 * Create next generator of shape
	 */
	public synchronized void nextGeneration(){
		
		//count alive neighbors
		for (int row = 0; row < rows; row++) {
			for (int col = 0; col < cols; col++) {
				countAliveNeighbors(col,row);
			}
		}

		//Transition Function
		for (int row = 0; row < rows; row++) {
			for (int col = 0; col < cols; col++) {
				cells[col][row].transitionFunction();	
			}
		}

		generation++;
	}
	

	public ManagerOfThreads getManagerOfThreads() {
		return managerOfThreads;
	}


	public ManagerOfThreads newManagerOfThreads(final int speedTime, int numOfThreads){
		return managerOfThreads = new ManagerOfThreads(speedTime,numOfThreads);
	}

	/**
	 * Creates and manages threads
	 */
	public class ManagerOfThreads extends Observable{
		private boolean done,barrierDone;
		private int sleepTime;
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
				while (!barrierDone) {
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
				try {
					Thread.sleep(sleepTime);
					generation++;
					setChanged();
					notifyObservers();
					//finish the loop and paused threads
					if (done == true)
						barrierDone = true;

				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		
		
		public ManagerOfThreads(final int sleepTime, int numOfThreads) {
			super();
			this.done = true;
			this.barrierDone = false;
			this.sleepTime = sleepTime;
			this.numOfThreads = numOfThreads;
			this.countAliveNeighborsBarrier = new CyclicBarrier(numOfThreads);
			this.transitionFunctionBarrier = new CyclicBarrier(numOfThreads,new BarrierAction());
		}
		
		public int getNumOfThreads(){
			return numOfThreads;
		}
		
		public int getSleepTime(){
			return sleepTime;
		}

		public synchronized void setSleepTime(int sleepTime) {
			this.sleepTime = sleepTime;
		}
		
		public synchronized void setNumOfThreads(int numOfThreads) {
			this.numOfThreads = numOfThreads;
			this.countAliveNeighborsBarrier = new CyclicBarrier(numOfThreads);
			this.transitionFunctionBarrier = new CyclicBarrier(numOfThreads,new BarrierAction());
		}
		
		public synchronized void start(){
			done = false;
			for (int t = 0; t < numOfThreads; t++) {
				new Thread(new Worker(t),"thread: "+t).start();
			}
		}

		public synchronized void stop() {
			done = true;
		}
		
		public synchronized boolean isFinish(){
			return done == true;
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
	
	/**
	 * Resize grid. Reuse existing cells.
	 * 
	 * @param cols of grid
	 * @param rows of grid
	 */
	public synchronized void resize(int cols, int rows) {
		
		//if all threads are not running
		if (managerOfThreads.isFinish()){

		// Create a new grid, reusing existing Cell's
		GameOfLifeCell[][] NewCells = new GameOfLifeCell[cols][rows];
		for (int row = 0; row < rows; row++) {
			for (int col = 0; col < cols; col++) {
				if (col < this.cols && row < this.rows)
					NewCells[col][row] = cells[col][row];
				else
					NewCells[col][row] = new GameOfLifeCell();
			}
		}
		
		// Copy new grid
		cells = NewCells;
		this.cols = cols;
		this.rows = rows;
		}
	}


	/**
	 * Reset the grid
	 */
	private synchronized void reset(){
		for (int row = 0; row < rows; row++) {
			for (int col = 0; col < cols; col++) {
				cells[col][row].setState(GameOfLifeCell.DEAD);
				cells[col][row].setAliveNeighbors((byte) 0);
			}
		}
		generation=0;
	}
	
	/**
	 * Set shape in grid
	 * @param shape name of shape
	 * @throws ShapeException if shape does not fit on grid
	 */
	public synchronized void loadShape(Shape shape) throws ShapeException {

		//Shape Exception
		if(cols < shape.getWidth() || rows < shape.getHeight())
			throw new ShapeException("Shape doesn't fit on grid(grid: "+cols+"x"+rows+", shape: "+shape.getWidth()+"x"+shape.getHeight()+")");
		
		reset();
		
		//center the shape
		int xOffset = (cols - shape.getWidth())/2;
		int yOffset = (rows - shape.getHeight())/2;
		
		//set shape
		Enumeration<int[]> intCells = shape.getCells();
		while (intCells.hasMoreElements()){
			int[] elem= intCells.nextElement();
	
			int row = yOffset+elem[1];
			int col = xOffset+elem[0];
			
			cells[col][row].setState(GameOfLifeCell.ALIVE);
		}

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
		int firstRow = ((getRows() / numOfThreads) * currentThread);
		int firstCol = 0;
		int lastRow = ((getRows() / numOfThreads) * (currentThread + 1));
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

		int firstRow = ((getRows() / numOfThreads) * currentThread);
		int firstCol = 0;
		int lastRow = ((getRows() / numOfThreads) * (currentThread + 1));
		int lastCol = cols;

		for (int row = firstRow; row < lastRow; row++) {
			for (int col = firstCol; col < lastCol; col++) {
				cells[col][row].transitionFunction();
			}
		}
	}
}
