package ar.edu.unlp.CellularAutomaton.model;

import java.util.Enumeration;
import java.util.Observable;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

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

	public class ManagerOfThreads extends Observable{
		private boolean done,barrierDone,paused,pausedDone;
		private int sleepTime;
		private int numOfThreads;
		private Thread[] threads;
		private ReentrantLock pauseLock = new ReentrantLock();
		private Condition unpaused = pauseLock.newCondition();
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
						if(pausedDone) {
							pauseLock.lock();
							unpaused.await();
							pauseLock.unlock();}
						else {
							countAliveNeighborsMatrix(numOfThreads, currentThread);
							countAliveNeighborsBarrier.await();//CyclicBarrier await

							transitionFuncionMatrix(numOfThreads, currentThread);
							transitionFunctionBarrier.await();//CyclicBarrier await
						}

					
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
					else if (paused == true){
						pausedDone = true;
					}
						
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		
		
		public ManagerOfThreads(final int sleepTime, int numOfThreads) {
			super();
			this.done = false;
			this.barrierDone = false;
			this.paused = false;
			this.pausedDone = false;
			
			this.sleepTime = sleepTime;
			this.numOfThreads = numOfThreads;
			this.countAliveNeighborsBarrier = new CyclicBarrier(numOfThreads);
			this.transitionFunctionBarrier = new CyclicBarrier(numOfThreads,new BarrierAction());
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
			threads = new Thread[numOfThreads];
			for (int t = 0; t < numOfThreads; t++) {
				threads[t] = new Thread(new Worker(t),"thread: "+t);
				threads[t].start();
			}
		}

		public synchronized void stop() {
			done = true;
		}
		
		public void pause() {
			pauseLock.lock();
			try {
				paused = true;
			} finally {
				pauseLock.unlock();
			}
		}
		
		public void resume() {
			pauseLock.lock();
			try {
				paused = false;
				pausedDone = false;
				unpaused.signalAll();
			} finally {
				pauseLock.unlock();
			}
		}
	}

	
	private void countAliveNeighbors(final int col, final int row){
		cells[col][row].setAliveNeighbors((byte) 0);
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
	public synchronized void resize(int width, int height) {
		getManagerOfThreads().pause();
	    // Copy existing shape to center of new shape
//	    int colOffset = (cols-this.width)/2;
//	    int rowOffset = (rows-this.height)/2;
//	    System.out.println(colOffset);
				
		// Create a new grid, reusing existing Cell's
		GameOfLifeCell[][] NewCells = new GameOfLifeCell[width][height];
		for (int row = 0; row < height; row++) {
			for (int col = 0; col < width; col++) {
				if (col < this.cols && row < this.rows)
					NewCells[col][row] = cells[col][row];
				else
					NewCells[col][row] = new GameOfLifeCell();
			}
		}
		
		// Copy new grid
		cells = NewCells;
		this.cols = width;
		this.rows = height;
		
		getManagerOfThreads().resume();
	}


	/**
	 * Reset the grid
	 */
	public synchronized void reset(){
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
	
	
	private void countAliveNeighborsMatrix(int numOfThreads, int currentThread ){
		
		int threads = numOfThreads;
		int free = getRows() % threads;
		int firstRow=0;
		int firstCol=0;
		int lastRow;
		int lastCol=getCols();
		System.out.println(free);
		int freefirstRow = 0;
		int freelastRow = 0;
		
//		if(free>0)freelastRow++;

		int t = currentThread;

			

			System.out.println("free:"+free+" freefirstRow:"+freefirstRow+" freelastRow:"+freelastRow);
			
			firstRow = ((getRows() / threads) * t) + freefirstRow;
			firstCol = 0;
			lastRow = ((getRows() / threads) * (t + 1)) + freelastRow;
			lastCol = getCols();
			
			
			for (int row = firstRow; row < lastRow; row++) {
				for (int col = firstCol; col < lastCol; col++) {
					countAliveNeighbors(col,row);
//					try {Thread.sleep(1000);} catch (InterruptedException e) {e.printStackTrace();}
//					cells[col][row].setColor(25000*(t+1));
				}
			}
			
			
//			if(free>0){
//				freefirstRow++;
//				freelastRow++;
//				free--;
//			} if (free == 0 && (getRows() % threads) > 0) {
//				freelastRow--;
//				free--;}
		
	}
	
	private void transitionFuncionMatrix(int numOfThreads, int currentThread ){
		
		int threads = numOfThreads;
		int free = getRows() % threads;
		int firstRow=0;
		int firstCol=0;
		int lastRow;
		int lastCol=getCols();
		System.out.println(free);
		int freefirstRow = 0;
		int freelastRow = 0;
		
//		if(free>0)freelastRow++;

		int t = currentThread;

			

			System.out.println("free:"+free+" freefirstRow:"+freefirstRow+" freelastRow:"+freelastRow);
			
			firstRow = ((getRows() / threads) * t) + freefirstRow;
			firstCol = 0;
			lastRow = ((getRows() / threads) * (t + 1)) + freelastRow;
			lastCol = getCols();
			
			
			for (int row = firstRow; row < lastRow; row++) {
				for (int col = firstCol; col < lastCol; col++) {
					cells[col][row].transitionFunction();
//					try {Thread.sleep(1000);} catch (InterruptedException e) {e.printStackTrace();}
//					cells[col][row].setColor(25000*(t+1));
				}
			}
			
			
//			if(free>0){
//				freefirstRow++;
//				freelastRow++;
//				free--;
//			} if (free == 0 && (getRows() % threads) > 0) {
//				freelastRow--;
//				free--;}
		
	}
}
