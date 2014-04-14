package ar.edu.unlp.CellularAutomaton.view;

import java.awt.Color;
import java.awt.Graphics;

/**
 * Paints a cell like a circle
 * @author mclo
 */
public class CircleFigure implements CellFigure {

	/* (non-Javadoc)
	 * @see ar.edu.unlp.CellularAutomaton.util.CellFigure#paint(java.awt.Graphics, int, int, int)
	 */
	public void paint(Graphics g, int cellSize, int col, int row) {
		g.fillOval(col * cellSize, row * cellSize, cellSize, cellSize);
		g.setColor(Color.BLACK);
		g.drawOval(col * cellSize, row * cellSize, cellSize, cellSize);
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return "Circle";
	}

}
