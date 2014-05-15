package ar.edu.unlp.CellularAutomaton.swing.grid.ui;

import java.awt.Color;
import java.awt.Graphics;

/**
 * Paints a cell like a square
 * @author mclo
 */
public class SquareFigure implements CellFigure {

	/* (non-Javadoc)
	 * @see ar.edu.unlp.CellularAutomaton.util.CellFigure#paint(java.awt.Graphics, int, int, int)
	 */
	public void paint(Graphics g, int cellSize, int col, int row) {
		g.fillRect(col * cellSize, row * cellSize, cellSize, cellSize);
		g.setColor(Color.BLACK);
		g.drawRect(col * cellSize, row * cellSize, cellSize, cellSize);
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return "Square";
	}

}
