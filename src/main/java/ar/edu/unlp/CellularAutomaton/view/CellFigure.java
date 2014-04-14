package ar.edu.unlp.CellularAutomaton.view;

import java.awt.Graphics;


/**
 * Cell figure to paint in the PanelGrid
 * @see GridPanel
 * @author mclo
 */
public interface CellFigure {

	/**
	 * Paint a cell
	 * @param g Graphics
	 * @param cellSize cell's size
	 * @param col column of the grid
	 * @param row row of the grid
	 */
	void paint(Graphics g, int cellSize, int col, int row);

}
