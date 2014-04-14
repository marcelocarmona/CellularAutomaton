package ar.edu.unlp.CellularAutomaton.view;

import java.awt.Color;
import java.awt.Graphics;

/**
 * Paints a cell like a hexagon
 * @author mclo
 */
public class HexagonFigure implements CellFigure {

	/* (non-Javadoc)
	 * @see ar.edu.unlp.CellularAutomaton.util.CellFigure#paint(java.awt.Graphics, int, int, int)
	 */
	public void paint(Graphics g, int cellSize, int col, int row) {
		int coordX[] = { col * cellSize + (cellSize / 4),
						 col * cellSize + (cellSize / 4)*3,
						 col * cellSize + cellSize, 
						 col * cellSize + (cellSize / 4)*3,
						 col * cellSize + (cellSize / 4),
						 col * cellSize };
		int coordY[] = { row * cellSize, 
						 row * cellSize, 
						 row * cellSize + (cellSize / 2),
						 row * cellSize + cellSize,
						 row * cellSize + cellSize, 
						 row * cellSize + (cellSize / 2) };
		g.fillPolygon(coordX, coordY, 6);
		g.setColor(Color.BLACK);
		g.drawPolygon(coordX, coordY, 6);
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return "Hexagon";
	}

}
