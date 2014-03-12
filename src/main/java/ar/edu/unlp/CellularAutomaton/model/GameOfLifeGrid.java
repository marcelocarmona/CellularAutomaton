package ar.edu.unlp.CellularAutomaton.model;

import java.util.Enumeration;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

import ar.edu.unlp.CellularAutomaton.exception.ShapeException;
import ar.edu.unlp.CellularAutomaton.util.Shape;

public class GameOfLifeGrid {
	
	private int generation;
	private int cols;
	private int rows;
	private GameOfLifeCell[][] cells;

	public GameOfLifeGrid(int cols, int rows) {
		super();
		generation = 0;
		this.cols = cols;
		this.rows = rows;
		cells = new GameOfLifeCell[cols][rows];
		
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
	
	
	public synchronized void nextGeneration2(){
		
		final CyclicBarrier countAliveNeighborsBarrier = new CyclicBarrier(2);
		final CyclicBarrier transitionFunctionBarrier = new CyclicBarrier(2);
		
		Thread hilo1 = new Thread() {
            public void run() {
            	for (int i = 0; i < 10; i++) {
					
	        		//count alive neighbors
	        		for (int row = 0; row < rows/2; row++) {
	        			for (int col = 0; col < cols; col++) {
	        				countAliveNeighbors(col,row);
	        			}
	        		}
	        		try {countAliveNeighborsBarrier.await();} catch (InterruptedException | BrokenBarrierException e) {e.printStackTrace();}
	        		
	        		//Transition Function
	        		for (int row = 0; row < rows/2; row++) {
	        			for (int col = 0; col < cols; col++) {
	        				cells[col][row].transitionFunction();	
	        			}
	        		}
	        		try {transitionFunctionBarrier.await();} catch (InterruptedException | BrokenBarrierException e) {e.printStackTrace();}
				}
            }
		};
		
		Thread hilo2 = new Thread() {
            public void run() {
            	for (int i = 0; i < 10; i++) {
            		
	        		//count alive neighbors
	        		for (int row = rows/2; row < rows; row++) {
	        			for (int col = 0; col < cols; col++) {
	        				countAliveNeighbors(col,row);
	        			}
	        		}
	        		try {countAliveNeighborsBarrier.await();} catch (InterruptedException | BrokenBarrierException e) {e.printStackTrace();}
	        		
	        		//Transition Function
	        		for (int row = rows/2; row < rows; row++) {
	        			for (int col = 0; col < cols; col++) {
	        				cells[col][row].transitionFunction();	
	        			}
	        		}
	        		try {transitionFunctionBarrier.await();} catch (InterruptedException | BrokenBarrierException e) {e.printStackTrace();}
            	}
            }
		};
		
//		Thread hilo3 = new Thread() {
//            public void run() {
//            	try {countAliveNeighborsBarrier.await();
//        		for (int row = 0; row < rows/2; row++) {
//        			for (int col = 0; col < cols; col++) {
//        				cells[col][row].transitionFunction();
//        			}
//        		}
//        		transitionFunctionBarrier.await();} catch (InterruptedException | BrokenBarrierException e) {e.printStackTrace();}
//            }
//		};
//		
//		Thread hilo4 = new Thread() {
//            public void run() {
//            	try {countAliveNeighborsBarrier.await();} catch (InterruptedException | BrokenBarrierException e) {e.printStackTrace();}
//        		for (int row = rows/2; row < rows; row++) {
//        			for (int col = 0; col < cols; col++) {
//        				cells[col][row].transitionFunction();
//        			}
//        		}
//        		try {transitionFunctionBarrier.await();} catch (InterruptedException | BrokenBarrierException e) {e.printStackTrace();}
//            }
//		};
		
		hilo1.start();
		hilo2.start();
//		hilo3.start();
//		hilo4.start();


		generation++;
	}
	
	/**
	 * Ejecuta un pedazo de la sigiente generación de la matriz
	 *
	 */
	public Worker getWorker(int firstRow, int firstCol, int lastRow, int lastCol,CyclicBarrier countAliveNeighborsBarrier,CyclicBarrier transitionFunctionBarrier){
		return new Worker(firstRow, firstCol, lastRow, lastCol,countAliveNeighborsBarrier,transitionFunctionBarrier);
	}
	
	public class Worker implements Runnable{
		private int firstRow;
		private int firstCol;
		private int lastRow;
		private int lastCol;
		private CyclicBarrier countAliveNeighborsBarrier;
		private CyclicBarrier transitionFunctionBarrier;

		public Worker(){}
		
		public Worker(int firstRow, int firstCol, int lastRow, int lastCol,CyclicBarrier countAliveNeighborsBarrier,CyclicBarrier transitionFunctionBarrier) {
			super();
			this.firstRow = firstRow;
			this.firstCol = firstCol;
			this.lastRow = lastRow;
			this.lastCol = lastCol;
			this.countAliveNeighborsBarrier = countAliveNeighborsBarrier;
			this.transitionFunctionBarrier = transitionFunctionBarrier;
		}

		@Override
		public void run() {
			try {
				//count alive neighbors
				for (int row = firstRow; row < lastRow; row++) {
					for (int col = firstCol; col < lastCol; col++) {
						countAliveNeighbors(col,row);
					}
				}
				countAliveNeighborsBarrier.await(); //CyclicBarrier await
				System.out.println("paso");
				//Transition Function
				for (int row = firstRow; row < lastRow; row++) {
					for (int col = firstCol; col < lastCol; col++) {
						cells[col][row].transitionFunction();	
					}
				}
				transitionFunctionBarrier.await(); //CyclicBarrier await
				
			} catch (InterruptedException | BrokenBarrierException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void countAliveNeighbors(final int col, final int row){
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
	
	
	public String toString(){
		return "Grid("+cols+", "+rows+")";
	}


}
