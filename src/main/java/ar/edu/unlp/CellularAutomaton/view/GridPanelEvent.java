package ar.edu.unlp.CellularAutomaton.view;

import java.awt.Event;

public class GridPanelEvent extends Event {
	
	private int cols;
	private int rows;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public GridPanelEvent(Object target) {
		super(target, 0, null);
		// TODO Auto-generated constructor stub
	}
	
	public static GridPanelEvent getSizeChangedEvent(Object target, int cols, int rows){
		GridPanelEvent gridPanelEvent= new GridPanelEvent(target);
		gridPanelEvent.setCols(cols);
		gridPanelEvent.setRows(rows);
		return gridPanelEvent;
	}

	public int getCols() {
		return cols;
	}

	public void setCols(int cols) {
		this.cols = cols;
	}

	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

}
