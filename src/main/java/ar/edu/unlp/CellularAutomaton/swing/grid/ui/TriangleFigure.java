package ar.edu.unlp.CellularAutomaton.swing.grid.ui;

import java.awt.Color;
import java.awt.Graphics;

/**
 * Paints a cell like a triangle
 * @author mclo
 */
public class TriangleFigure implements CellFigure {

	/* (non-Javadoc)
	 * @see ar.edu.unlp.CellularAutomaton.util.CellFigure#paint(java.awt.Graphics, int, int, int)
	 */
	public void paint(Graphics g, int cellSize, int col, int row) {
		int coordX[ ] = { col * cellSize+(cellSize/2),col * cellSize+1,col * cellSize+cellSize-1};
		int coordY[ ] = { row * cellSize+2,row * cellSize+cellSize,row * cellSize+cellSize}; 
		g.fillPolygon(coordX, coordY, 3);
		g.setColor(Color.BLACK);
		g.drawPolygon(coordX, coordY, 3);
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return "Triangle";
	}

}
