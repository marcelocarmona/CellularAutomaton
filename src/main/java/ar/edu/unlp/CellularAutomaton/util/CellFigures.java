package ar.edu.unlp.CellularAutomaton.util;

import ar.edu.unlp.CellularAutomaton.swing.grid.ui.CellFigure;
import ar.edu.unlp.CellularAutomaton.swing.grid.ui.CircleFigure;
import ar.edu.unlp.CellularAutomaton.swing.grid.ui.HexagonFigure;
import ar.edu.unlp.CellularAutomaton.swing.grid.ui.RhombusFigure;
import ar.edu.unlp.CellularAutomaton.swing.grid.ui.SquareFigure;
import ar.edu.unlp.CellularAutomaton.swing.grid.ui.TriangleFigure;

public enum CellFigures {
	SQUARE(0, new SquareFigure()), 
	CIRCLE(1, new CircleFigure()), 
	TRIANGLE(2, new TriangleFigure()), 
	RHOMBUS(3, new RhombusFigure()), 
	HEXAGON(4, new HexagonFigure());

	private int id;
	private CellFigure cellFigure;
	
	private CellFigures(int id, CellFigure cellFigure) {
		this.setId(id);
		this.cellFigure = cellFigure;
	}

	public String toString(){
		return cellFigure.toString();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

}
