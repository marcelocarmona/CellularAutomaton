package ar.edu.unlp.CellularAutomaton.view;

import java.awt.Color;
import java.awt.Component;
import java.awt.GridLayout;

import javax.swing.JPanel;

import ar.edu.unlp.CellularAutomaton.model.GameOfLifeCell;
import ar.edu.unlp.CellularAutomaton.model.GameOfLifeGrid;

public class GridPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private GameOfLifeGrid grid;

	/**
	 * Create the GridPanel.
	 */
	public GridPanel(final int rows, final int cols) {

		grid = new GameOfLifeGrid(rows, cols);

		setLayout(new GridLayout(rows, cols, 0, 0));

		for (int x = 1; x <= rows; x++) {
			for (int y = 1; y <= cols; y++) {
				GameOfLifeCell cell = grid.getCell(x, y);
				JPanel panel = new CellPanel(cell);
				add(panel);
			}
		}
	}

	public void reset() {
		for (Component panel : getComponents()) {
			panel.setBackground(Color.DARK_GRAY);
		}
	}

	/**
	 * REFACTORING
	 */
	public void next() {
		grid.nextGeneration();
		for (Component cellPanel : getComponents()) {
			((CellPanel) cellPanel).updateBackground();
		}
	}
	
	public String getGeneration(){
		return Integer.toString(grid.getGeneration());
	}
}
