package ar.edu.unlp.CellularAutomaton.swing.grid;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * Listener interface for JGrid
 * @see JGrid
 * @author mclo
 */
public interface GridListener extends ChangeListener {

	/**
	 * The cell size of the grid size is changed
	 * @param event
	 */
	public void cellSizeChanged(ChangeEvent e);
}
