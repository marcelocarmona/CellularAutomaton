package ar.edu.unlp.CellularAutomaton.swing.grid;

import java.awt.Color;

import javax.swing.event.ChangeEvent;
import javax.swing.event.EventListenerList;



public abstract class GridModel {

	/**
	 * cell sizes
	 */
	private static final int MAX_CELL_SIZE = 30;
	private static final int MIN_CELL_SIZE = 5;
	private static final int DEFAULT_CELL_SIZE = 20;
	
	/**
	 * cell figures
	 */
	public static final int SQUARE_FIGURE  = 0;
	public static final int CIRCLE_FIGURE  = 1;
	public static final int TRIANGLE_FIGURE  = 2;
	public static final int RHOMBUS_FIGURE  = 3;
	public static final int HEXAGON_FIGURE  = 4;

	/** The listeners waiting for model changes. */
	protected EventListenerList listenerList = new EventListenerList();

	/**
	 * figure and cell size
	 */
	private int cellSize = DEFAULT_CELL_SIZE;
	private int cellFigure = SQUARE_FIGURE;
	

	/**
	 * Adds a <code>GridListener</code> to the model.
	 * 
	 * @param l
	 *            the listener to add
	 */
	public void addGridListener(GridListener l) {
		listenerList.add(GridListener.class, l);
	}

	/**
	 * Removes a <code>ChangeListener</code> from the model.
	 * 
	 * @param l
	 *            the listener to remove
	 */
	public void removeGridListener(GridListener l) {
		listenerList.remove(GridListener.class, l);
	}

	/**
	 * Runs each <code>GridListener</code>'s <code>stateChanged</code> method.
	 */
	protected void fireStateChanged() {
		ChangeEvent event = new ChangeEvent(this);
		Object[] listeners = listenerList.getListenerList();
		for (int i = listeners.length - 2; i >= 0; i -= 2) {
			if (listeners[i] == GridListener.class) {
				((GridListener) listeners[i + 1]).stateChanged(event);
			}
		}
	}
	
	/**
	 * Runs each <code>GridListener</code>'s <code>cellSizeChanged</code> method.
	 */
	protected void fireCellSizeChanged() {
		ChangeEvent event = new ChangeEvent(this);
		Object[] listeners = listenerList.getListenerList();
		for (int i = listeners.length - 2; i >= 0; i -= 2) {
			if (listeners[i] == GridListener.class) {
				((GridListener) listeners[i + 1]).cellSizeChanged(event);
			}
		}
	}

	public int getCellSize() {
		return cellSize;
	}

	public void setCellSize(int cellSize) {
		this.cellSize = cellSize;
		fireCellSizeChanged();
	}

	public int getCellFigure() {
		return cellFigure;
	}

	public void setCellFigure(int cellFigure) {
		this.cellFigure = cellFigure;
		fireStateChanged();
	}

	public abstract int getRows();

	public abstract int getCols();

	public abstract Color getCellColor(int col, int row);

	public abstract void mouseClickAction(int cellX, int cellY);

	public abstract void mouseDraggedAction(int cellX, int cellY);

	public void mouseWheelAction(int rotation){
		if (getCellSize() + rotation >= MIN_CELL_SIZE && getCellSize() + rotation <= MAX_CELL_SIZE) {
			setCellSize(getCellSize()+rotation);
		}
	};

	public abstract void componentResizedAction(int cols, int rows);

}
