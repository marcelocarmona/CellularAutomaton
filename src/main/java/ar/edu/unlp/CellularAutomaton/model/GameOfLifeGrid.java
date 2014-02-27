package ar.edu.unlp.CellularAutomaton.model;

public class GameOfLifeGrid {
	
	private int generation;
	private int x;
	private int y;
	private GameOfLifeCell[][] cells;

	public GameOfLifeGrid(int x, int y) {
		super();
		generation = 0;
		this.x = x;
		this.y = y;
		cells = new GameOfLifeCell[x+2][y+2];
		
		//initialize the grid with cells
		for (int row = 0; row <= x+1; row++) {
			for (int colum = 0; colum <= y+1; colum++) {
				cells[row][colum] = new GameOfLifeCell();
			}
		}
		
		//initialize neighborhood
		for (int row = 1; row <= x; row++) {
			for (int colum = 1; colum <= y; colum++) {
				cells[row][colum].addNeighbor(cells[row-1][colum-1]);
				cells[row][colum].addNeighbor(cells[row-1][colum]);
				cells[row][colum].addNeighbor(cells[row-1][colum+1]);
				cells[row][colum].addNeighbor(cells[row][colum-1]);
				cells[row][colum].addNeighbor(cells[row][colum+1]);
				cells[row][colum].addNeighbor(cells[row+1][colum-1]);
				cells[row][colum].addNeighbor(cells[row+1][colum]);
				cells[row][colum].addNeighbor(cells[row+1][colum+1]);		
			}
		}
	}
	
	
	public int getGeneration() {
		return generation;
	}

	public void setGeneration(int generation) {
		this.generation = generation;
	}

	public void nextGeneration(){

		for (int row = 1; row <= x; row++) {
			for (int colum = 1; colum <= y; colum++) {
				cells[row][colum].transitionFunction();	
			}
		}

		//count alive neighbors
		for (int row = 1; row <= x; row++) {
			for (int colum = 1; colum <= y; colum++) {
				cells[row][colum].updateAliveNeighbors();
			}
		}
		generation++;
	}
	
	public GameOfLifeCell getCell(int x, int y){
		return cells[x][y];
	}
	
	
	public String toString(){
		String s ="\n";
		for (GameOfLifeCell[] row : cells) {
			for (GameOfLifeCell cell : row) {
				s+= cell;
			}
			s+="\n";
		}
		return s;
	}
	
	
	public static void main(String[] args) {
		GameOfLifeGrid gameOfLifeCellGrid = new GameOfLifeGrid(5, 5);
		
		gameOfLifeCellGrid.cells[2][1].setStateAndUpdateNeighbors(GameOfLifeCell.ALIVE);
		gameOfLifeCellGrid.cells[2][2].setStateAndUpdateNeighbors(GameOfLifeCell.ALIVE);
		gameOfLifeCellGrid.cells[2][3].setStateAndUpdateNeighbors(GameOfLifeCell.ALIVE);
		gameOfLifeCellGrid.cells[3][2].setStateAndUpdateNeighbors(GameOfLifeCell.ALIVE);
		gameOfLifeCellGrid.cells[3][3].setStateAndUpdateNeighbors(GameOfLifeCell.ALIVE);
		gameOfLifeCellGrid.cells[3][4].setStateAndUpdateNeighbors(GameOfLifeCell.ALIVE);
		

		for (int i = 0; i < 5; i++) {
			System.out.println(gameOfLifeCellGrid.generation);
			System.out.println(gameOfLifeCellGrid);
			gameOfLifeCellGrid.nextGeneration();
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
