package ar.edu.unlp.CellularAutomaton.model;


public class GameOfLifeCellGrid {

	private long generation = 0;
	private long population = 0;
	private long x;
	private long y;
	//private GameOfLifeCell[][] gameOfLifeCells;
	private long[][] gameOfLifeCells;

	public GameOfLifeCellGrid(long x, long y) {
			this.x=x;
			this.y=y;
			//mas 2 por la frontera
			gameOfLifeCells = new long[(int) (x+2)][(int) (y+2)];

			for (int row = 0; row <= x+1; row++) {
				for (int colum = 0; colum <= y+1; colum++) {
				gameOfLifeCells[row][colum] = 0;
			}
		}
	}
	
	
	public void nextGenerator(){
		long[][] gridAux = new long[(int) (x+2)][(int) (y+2)];
		for (int row = 1; row <= x; row++) {
			for (int colum = 1; colum <= y; colum++) {
				gridAux[row][colum]= transitionFunction(row,colum);
//				gameOfLifeCells[row][colum].transitionFunction();
				generation++;
			}
		}
		
		for (int row = 1; row <= x; row++) {
			for (int colum = 1; colum <= y; colum++) {
				gameOfLifeCells[row][colum] = gridAux[row][colum];
			}
		}
	}
	
	public int transitionFunction(int x, int y) {
		int aliveNeighbors = aliveNeighbors(x,y);
		//System.out.print(aliveNeighbors);
		if(gameOfLifeCells[x][y] == 0){
			if(aliveNeighbors == 3){
				population++;
				return 1;
			} else return 0;
		}else{
			if(aliveNeighbors < 2 || aliveNeighbors > 3){
				return 0;
			}else return 1;
		}
	}
	
	public int aliveNeighbors(long x, long y) {
		int x1 = (int) x;
		int y1 = (int) y;
		
		return 	(int) 	(gameOfLifeCells[x1-1][y1-1]+
						gameOfLifeCells[x1-1][y1]+
						gameOfLifeCells[x1-1][y1+1]+
						gameOfLifeCells[x1][y1-1]+
						gameOfLifeCells[x1][y1+1]+
						gameOfLifeCells[x1+1][y1-1]+
						gameOfLifeCells[x1+1][y1]+
						gameOfLifeCells[x1+1][y1+1]);
	}
	
	public String toString(){
		String s ="\n";
		for (long[] row : gameOfLifeCells) {
			for (long cell : row) {
				s+= cell;
			}
			s+="\n";
		}
		return s;
	}
		
	public static void main (String [ ] args) throws InterruptedException {
		GameOfLifeCellGrid g = new GameOfLifeCellGrid(5,5);
		g.gameOfLifeCells[2][1]=1;
		g.gameOfLifeCells[2][2]=1;
		g.gameOfLifeCells[2][3]=1;
		g.gameOfLifeCells[3][2]=1;
		g.gameOfLifeCells[3][3]=1;
		g.gameOfLifeCells[3][4]=1;
		for (int i = 0; i < 10; i++) {
			System.out.print("generacion: "+i);
			System.out.print(g);
			g.nextGenerator();Thread.sleep(1000);
		}
		
		
	}
}
