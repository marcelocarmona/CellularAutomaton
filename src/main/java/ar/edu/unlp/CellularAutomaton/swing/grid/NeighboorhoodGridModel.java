package ar.edu.unlp.CellularAutomaton.swing.grid;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import javax.swing.event.ChangeEvent;

import ar.edu.unlp.CellularAutomaton.model.Neighbor;
import ar.edu.unlp.CellularAutomaton.swing.checkList.RuleCheckListModel;

public class NeighboorhoodGridModel extends GridModel {

	/**
	 * min size
	 */
	private static final int MIN_COLS = 5;
	private static final int MIN_ROWS = 5;
	
	private int cols = MIN_COLS;
	private int rows = MIN_ROWS;
	private int center_col = cols / 2;
	private int center_row = rows / 2;
	
	private Cell[][] neighborhoodGrid = new Cell[cols][rows];
	private NeighboorhoodState mouseDraggedState;
	
	/**
	 * Models
	 */
	private List<RuleCheckListModel> checkListModels = new ArrayList<>();
	private GameGridModel gameGridModel;
	
	/**
	 * Neighboor States
	 */
	private final NeighboorState NEIGHBOOR_STATE = new NeighboorState();
	private final NotNeighboorState NOT_NEIGHBOOR_STATE = new NotNeighboorState();
	private final HomeState HOME_STATE = new HomeState();
	
	

	public NeighboorhoodGridModel(List<RuleCheckListModel> checkListModels, GameGridModel gameGridModel) {
		super();
		this.checkListModels = checkListModels;
		this.gameGridModel = gameGridModel;
		
		// TODO Ver
		//listener update de neighborhood
		gameGridModel.addGridListener(new GridListener() {
			
			@Override
			public void stateChanged(ChangeEvent e) {
				init();
				fireStateChanged();
				
			}

			@Override
			public void cellSizeChanged(ChangeEvent e) {
			}
		});
		
		init();
	}

	@Override
	public int getRows() {
		return rows;
	}

	@Override
	public int getCols() {
		return cols;
	}

	@Override
	public Color getCellColor(int col, int row) {
		return neighborhoodGrid[col][row].getColor();
	}

	@Override
	public void mouseClickAction(int cellX, int cellY) {
		neighborhoodGrid[cellX][cellY].switchState();
		mouseDraggedState = neighborhoodGrid[cellX][cellY].getState();
		fireStateChanged();
	}

	@Override
	public void mouseDraggedAction(int cellX, int cellY) {
		if(mouseDraggedState != neighborhoodGrid[cellX][cellY].getState())
			neighborhoodGrid[cellX][cellY].switchState();
		fireStateChanged();
	}

	@Override
	public void componentResizedAction(int cols, int rows) {
		if (cols > getGridWidth() && rows > getGridHeight()) {
		 		this.cols = cols;
		 		this.rows = rows;
				center_col = cols / 2;
				center_row = rows / 2;
				init();
				
		 	}
		fireStateChanged();
	}
	
	/**
	 * Initialize grid
	 */
	private void  init(){
		neighborhoodGrid= new Cell[cols][rows];
		
		//initialize grid
		for (int row = 0; row < rows; row++) {
			for (int col = 0; col < cols; col++) {
				neighborhoodGrid[col][row] = new Cell(col-center_col,row-center_row, NOT_NEIGHBOOR_STATE);
			}
		}
		
		//initialize neighboors
		for (Neighbor n : gameGridModel.getNeighborhood().getNeighbors()) {
			neighborhoodGrid[center_col+n.getCol()][center_row+n.getRow()].setState(NEIGHBOOR_STATE);
		}
		
		//initialize home
		neighborhoodGrid[center_col][center_row].setState(HOME_STATE);
	}
	
	/**
	 * @return The width of the grid's neighborhood
	 */
	private int getGridWidth(){
		int minWidth = Integer.MAX_VALUE;
		int maxWidth = Integer.MIN_VALUE;
		int width = MIN_COLS;
		for (int row = 0; row < rows; row++) {
			for (int col = 0; col < cols; col++) {
				if (neighborhoodGrid[col][row].isNeighboor()){
					if(col < minWidth) minWidth = col;
					if(col > maxWidth) maxWidth = col;
				}	
			}
		}
		
		if(minWidth > center_col)
			if(maxWidth > center_col)
				width = maxWidth - center_col;
			else
				return MIN_COLS;
		else
			if(maxWidth > center_col)
				width = Math.max(maxWidth - center_col, center_col - minWidth);
			else
				width = center_col - minWidth;
		
		return width * 2 + 1;
	}
	
	/**
	 * @return The height of the grid's neighborhood
	 */
	private int getGridHeight(){
		int minHeight = Integer.MAX_VALUE;
		int maxHeight = Integer.MIN_VALUE;
		int height = MIN_ROWS;
		for (int row = 0; row < rows; row++) {
			for (int col = 0; col < cols; col++) {
				if (neighborhoodGrid[col][row].isNeighboor()){
					if(row < minHeight) minHeight = row;
					if(row > maxHeight) maxHeight = row;
				}	
			}
		}
		
		if(minHeight > center_row)
			if(maxHeight > center_row)
				height = maxHeight - center_row;
			else
				return MIN_ROWS;
		else
			if(maxHeight > center_row)
				height = Math.max(maxHeight - center_row, center_row - minHeight);
			else{System.out.println("aca"+(center_row - minHeight));
				height = center_row - minHeight;}
		
		return height * 2 + 1;
	}
	
	
	private class Cell {
		
		private int col;
		private int row;
		private NeighboorhoodState state;

		public Cell(int col, int row,NeighboorhoodState state) {
			super();
			this.col = col;
			this.row = row;
			this.state = state;
		}

		public boolean isNeighboor() {
			return state.isNeigboor();
		}

		public int getCol() {
			return col;
		}

		public int getRow() {
			return row;
		}

		public void switchState() {
			state.switchState(this);
		}

		public Color getColor() {
			return state.getColor();
		}
		
		public NeighboorhoodState getState() {
			return state;
		}

		public void setState(NeighboorhoodState state) {
			this.state = state;
		}
		

	}

	private interface NeighboorhoodState {
		public Color getColor();

		public boolean isNeigboor();

		public void switchState(Cell cell);
	}

	private class NeighboorState implements NeighboorhoodState {

		public Color getColor() {
			return Color.RED;
		}

		public boolean isNeigboor() {
			return true;
		}
		
		public void switchState(Cell cell) {
			cell.setState(NOT_NEIGHBOOR_STATE);
			gameGridModel.getNeighborhood().remove(cell.getCol(), cell.getRow());
			for (RuleCheckListModel checkListModel : checkListModels) {
				checkListModel.removeLast();
			}
		}

	}

	private class NotNeighboorState implements NeighboorhoodState {

		public Color getColor() {
			return Color.DARK_GRAY;
		}

		public boolean isNeigboor() {
			return false;
		}
		
		public void switchState(Cell cell) {
			cell.setState(NEIGHBOOR_STATE);	
			gameGridModel.getNeighborhood().add(cell.getCol(), cell.getRow());
			for (RuleCheckListModel checkListModel : checkListModels) {
				checkListModel.addLast();
			}
		}

	}

	private class HomeState implements NeighboorhoodState {

		public Color getColor() {
			return Color.BLUE;
		}

		public boolean isNeigboor() {
			return false;
		}
		
		public void switchState(Cell cell) {
			// ignore
		}
	}

}
