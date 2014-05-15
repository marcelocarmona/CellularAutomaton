package ar.edu.unlp.CellularAutomaton.swing.grid;

import java.awt.Color;

import ar.edu.unlp.CellularAutomaton.exception.ShapeException;
import ar.edu.unlp.CellularAutomaton.model.CellState;
import ar.edu.unlp.CellularAutomaton.model.GameGrid;
import ar.edu.unlp.CellularAutomaton.model.GameOfLifeCell;
import ar.edu.unlp.CellularAutomaton.model.Neighborhood;
import ar.edu.unlp.CellularAutomaton.util.Shape;

/**
 * GridModel -> model aplication
 * GameGrid  -> model domain
 * @author mclo
 */
public class GameGridModel extends GridModel implements GameGrid {

	private GameGrid grid;
	private CellState mouseDraggedState;

	public GameGridModel(GameGrid grid) {
		super();
		this.grid = grid;
	}

	//Actions
	public void mouseClickAction(int cellX, int cellY) {
		GameOfLifeCell cell = grid.getCell(cellX, cellY);
		cell.switchState();
		mouseDraggedState = cell.getState();
		fireStateChanged();
	}

	public void mouseDraggedAction(int cellX, int cellY) {
		grid.getCell(cellX, cellY).setState(mouseDraggedState);
		fireStateChanged();
	}

	public void componentResizedAction(int cols, int rows) {
		if (cols != this.getCols() || rows != this.getRows()) {
			resize(cols,rows);
		}
	}
	
	public Color getCellColor(int col, int row) {
		return new Color(grid.getCell(col, row).getColor());
	}
	
	//GameGrid adapter
	
	public int getRows() {
		return grid.getRows();
	}

	public int getCols() {
		return grid.getCols();
	}

	public Neighborhood getNeighborhood() {
		return grid.getNeighborhood();
	}

	public void setNeighborhood(Neighborhood neighborhood) {
		grid.setNeighborhood(neighborhood);
		fireStateChanged();
	}

	public int getGeneration() {
		return grid.getGeneration();
	}

	public void nextGeneration() {
		grid.nextGeneration();
		fireStateChanged();
	}

	public GameOfLifeCell getCell(int col, int row) {
		return grid.getCell(col, row);
	}

	public void resize(int width, int height) {
		grid.resize(width, height);
		fireStateChanged();
	}

	public void reset() {
		grid.reset();
		fireStateChanged();
	}

	public void loadShape(Shape shape) throws ShapeException {
			grid.loadShape(shape);
			fireStateChanged();
	}


}
